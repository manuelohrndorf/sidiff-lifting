package org.sidiff.patching.ui.jobs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.exceptions.SiDiffRuntimeException;
import org.sidiff.common.logging.StatusWrapper;
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.profiles.handler.DifferenceProfileHandlerUtil;
import org.sidiff.difference.profiles.handler.IDifferenceProfileHandler;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchMode;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.animation.GMFAnimation;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.patching.ui.perspective.SiLiftPerspective;
import org.sidiff.patching.ui.view.OperationExplorerView;
import org.sidiff.patching.ui.view.ReportView;

public class ApplyAsymmetricDifferenceJob extends Job {

	private final AsymmetricDifference asymmetricDifference;
	private final URI targetURI;
	private final PatchingSettings settings;

	// transient fields
	private Resource targetResource;
	private EditingDomain editingDomain;
	private IEditorIntegration domainEditor;
	private URI modelFileURI;
	private URI diagramFileUri;
	private boolean useDiagramEditor;
	private PatchEngine patchEngine;
	private ModelAdapter modelAdapter;

	public ApplyAsymmetricDifferenceJob(AsymmetricDifference asymmetricDifference, URI targetURI, PatchingSettings settings) {
		super("Applying patch to model");
		this.asymmetricDifference = asymmetricDifference;
		this.targetURI = targetURI;
		this.settings = settings;
		if(settings.getPatchMode() == null) {
			throw new IllegalArgumentException("Settings must have PatchMode set to configure this job");
		}
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		return StatusWrapper.wrap(() -> {
			copyTargetResources();
			openTargetResourceEditor();
			modelAdapter = new ModelAdapter(targetResource);
			initPatchingSettings();
			initPatchEngine();
			openPerspectiveAndViews();
			return Status.OK_STATUS;
		});
	}

	protected void copyTargetResources() {
		SiDiffResourceSet resourceSet = SiDiffResourceSet.create();
		targetResource = resourceSet.getResource(targetURI, true);
		domainEditor = IntegrationEditorAccess.getInstance().getIntegrationEditorForModel(targetResource);
		boolean domainEditorSupportsDiagramming = domainEditor.supportsDiagramming(targetResource);
		URI outputURI = targetURI.trimSegments(1).appendSegment(
				settings.getPatchMode() == PatchMode.PATCHING ? "patched" : "merged");
		modelFileURI = outputURI.appendSegment(targetURI.lastSegment());
		resourceSet.saveResourceAs(targetResource, modelFileURI);
		if (domainEditorSupportsDiagramming) {
			try {
				diagramFileUri = domainEditor.copyDiagram(targetURI, outputURI);
			} catch (FileNotFoundException e) {
				diagramFileUri = null;
			}
		} else {
			diagramFileUri = null;
		}
		useDiagramEditor = diagramFileUri != null
				&& domainEditor.supportsGMFAnimation(diagramFileUri)
				&& domainEditor.isDiagramEditorPresent();
	}

	protected void openTargetResourceEditor() {
		Display.getDefault().syncExec(() -> {
			IEditorPart editorPart = null;
			if (useDiagramEditor) {
				editorPart = domainEditor.openDiagram(diagramFileUri);
			} else if (domainEditor.isDefaultEditorPresent()) {
				editorPart = domainEditor.openModelInDefaultEditor(modelFileURI);
			}
			if (editorPart == null) {
				MessageDialogUtil.showErrorDialog("Opening editor failed",
					"Could not open editor for target model, continuing without editor integration.");
				return;
			}
			editingDomain = domainEditor.getEditingDomain(editorPart);
			Resource editorResource = domainEditor.getResource(editorPart);
			if (editorResource == null || editingDomain == null) {
				MessageDialogUtil.showErrorDialog("Unsupported editor",
					"The editor did not provide a resource and editing domain.");
				return;
			}
			if (useDiagramEditor && domainEditor.supportsGMFAnimation(diagramFileUri)) {
				GMFAnimation.enableAnimation(editorResource, false, GMFAnimation.MODE_TRIGGER);
			}
			targetResource = editorResource;
		});
	}

	protected void initPatchingSettings() throws FileNotFoundException, IOException {
		settings.setExecutionMode(ExecutionMode.INTERACTIVE);

		// Use interactive argument manager
		IArgumentManager argumentManager = IArgumentManager.MANAGER.getArgumentManager(
				asymmetricDifference, targetResource, settings, settings.getExecutionMode());
		if(argumentManager == null) {
			throw new SiDiffRuntimeException("No suitable Argument Manager was found for the target model.", "No Argument Manager found");
		}
		settings.setArgumentManager(argumentManager);
		modelAdapter.addListener(new ModelChangeHandler(argumentManager));

		// Dialog Patch interrupt handler
		settings.setInterruptHandler(new DialogPatchInterruptHandler());

		String documentType = getDocumentType(targetResource);

		// Find transformation engine
		if(settings.getTransformationEngine() == null) {
			settings.setTransformationEngine(
				ITransformationEngine.MANAGER.getDefaultExtension(Collections.singleton(documentType))
					.orElseThrow(() -> new SiDiffRuntimeException(
							"No Transformation Engine was found for the target document type.", "No Transformation Engine found")));
		}

		// Get and init modified detector
		if(settings.getPatchMode() == PatchMode.MERGING) {
			if(settings.getModifiedDetector() == null) {
				settings.setModifiedDetector(
						IModifiedDetector.MANAGER.getDefaultExtension(Collections.singleton(documentType)).orElse(null));				
			}
			if(settings.getModifiedDetector() != null) {
				settings.getModifiedDetector().init(asymmetricDifference.getOriginModel(), targetResource, settings.getMatcher(), settings.getScope());
			}
		}

		settings.setCorrespondencesService(ICorrespondences.MANAGER.getDefaultExtension().orElseThrow());
	}

	protected void initPatchEngine() {
		patchEngine = new PatchEngine(asymmetricDifference, targetResource, settings);
		if (useDiagramEditor && domainEditor.supportsGMFAnimation(diagramFileUri)) {
			patchEngine.getPatchReportManager().addPatchReportListener(
				new IPatchReportListener() {
					@Override
					public void reportChanged() {
						GMFAnimation.trigger();
					}
					@Override
					public void pushReport(int i) {
					}
				});
		}
		patchEngine.setPatchedEditingDomain(editingDomain);
	}

	protected void openPerspectiveAndViews() {
		Display.getDefault().asyncExec(() -> {
			Exceptions.log(() -> {
				UIUtil.showPerspective(SiLiftPerspective.ID);

				// Opening and setting operation explorer view
				OperationExplorerView operationExplorerView =
						UIUtil.showView(OperationExplorerView.class, OperationExplorerView.ID);
				operationExplorerView.setPatchEngine(patchEngine);
				modelAdapter.addListener(operationExplorerView);

				// Opening and setting report view
				ReportView reportView = UIUtil.showView(ReportView.class, ReportView.ID);
				reportView.setPatchReportManager(patchEngine.getPatchReportManager());

				return Status.OK_STATUS;
			});
		});
	}

	protected String getDocumentType(Resource resource) {
		Set<String> documentTypes = EMFModelAccess.getDocumentTypes(resource, Scope.RESOURCE);

		IDifferenceProfileHandler profileHandler = null;
		for (String docType : documentTypes) {
			// at the moment there is only one profile handler available
			profileHandler = DifferenceProfileHandlerUtil.getDefaultDifferenceProfileHandler(docType);
			if (profileHandler != null) {
				break;
			}
		}
		if (profileHandler != null && profileHandler.isProfiled(resource)) {
			return profileHandler.getBaseType();
		}
		return EMFModelAccess.getCharacteristicDocumentType(resource);
	}

}
