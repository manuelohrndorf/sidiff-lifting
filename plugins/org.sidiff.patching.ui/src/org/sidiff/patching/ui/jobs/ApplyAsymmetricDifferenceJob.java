package org.sidiff.patching.ui.jobs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.copiers.XmiIdCopier;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.exceptions.SiDiffRuntimeException;
import org.sidiff.common.logging.StatusWrapper;
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.profiles.handler.IDifferenceProfileHandler;
import org.sidiff.integration.editor.IEditorIntegration;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchMode;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.ui.animation.GMFAnimation;
import org.sidiff.patching.ui.interrupt.DialogPatchInterruptHandler;
import org.sidiff.patching.ui.perspective.SiLiftPatchingPerspective;
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
	private IEditorPart editorPart;

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
			initPatchingSettings();
			initPatchEngine();
			openPerspectiveAndViews();
			return Status.OK_STATUS;
		});
	}

	protected void copyTargetResources() {
		SiDiffResourceSet resourceSet = SiDiffResourceSet.create();
		Resource originalResource = resourceSet.getResource(targetURI, true);
		URI outputURI = targetURI.trimSegments(1).appendSegment(
				settings.getPatchMode() == PatchMode.PATCHING ? "patched" : "merged");
		modelFileURI = outputURI.appendSegment(targetURI.lastSegment());
		targetResource = resourceSet.createResource(modelFileURI);
		Copier copier = new XmiIdCopier();
		targetResource.getContents().addAll(copier.copyAll(originalResource.getContents()));
		copier.copyReferences();
		domainEditor = IEditorIntegration.MANAGER.getIntegrationEditorForModel(targetResource);
		boolean domainEditorSupportsDiagramming = domainEditor.supportsDiagramming(targetResource);
		resourceSet.saveResource(targetResource);
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

	protected void initPatchingSettings() throws IOException {
		settings.setExecutionMode(ExecutionMode.INTERACTIVE);

		// Use interactive argument manager
		IArgumentManager argumentManager = IArgumentManager.MANAGER.getArgumentManager(
				asymmetricDifference, targetResource, settings, settings.getExecutionMode());
		if(argumentManager == null) {
			throw new SiDiffRuntimeException("No suitable Argument Manager was found to apply " + asymmetricDifference
					+ " to the target model " + targetResource + " with " + settings, "No Argument Manager found");
		}
		settings.setArgumentManager(argumentManager);

		// Dialog Patch interrupt handler
		settings.setInterruptHandler(new DialogPatchInterruptHandler());

		Set<String> documentTypes = getDocumentTypes(targetResource);

		// Get and init modified detector
		if(settings.getPatchMode() == PatchMode.MERGING) {
			if(settings.getModifiedDetector() == null) {
				settings.setModifiedDetector(
						IModifiedDetector.MANAGER.getDefaultExtension(documentTypes).orElse(null));				
			}
			if(settings.getModifiedDetector() != null) {
				settings.getModifiedDetector().init(asymmetricDifference.getOriginModel(), targetResource, settings);
			}
		}

		settings.initDefaults(documentTypes);
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
		if(editingDomain instanceof AdapterFactoryEditingDomain) {
			// Set read only attribute in the editing domain,
			// else it will change the original models when saving.
			ResourceSet resourceSet = editingDomain.getResourceSet();
			Map<Resource,Boolean> readOnlyMap =
				Stream.of(asymmetricDifference.eResource(),
						asymmetricDifference.getOriginModel(),
						asymmetricDifference.getChangedModel())
					.filter(Objects::nonNull)
					.map(r -> resourceSet.getResource(r.getURI(), false)) // get actual resource of the editing domain
					.filter(Objects::nonNull)
					.collect(Collectors.toMap(Function.identity(), r -> Boolean.TRUE));
			((AdapterFactoryEditingDomain)editingDomain).setResourceToReadOnlyMap(readOnlyMap);
		}
	}

	protected void openPerspectiveAndViews() {
		Display.getDefault().asyncExec(() -> {
			Exceptions.log(() -> {
				UIUtil.showPerspective(SiLiftPatchingPerspective.ID);

				// Opening and setting operation explorer view
				OperationExplorerView operationExplorerView =
						UIUtil.showView(OperationExplorerView.class, OperationExplorerView.ID);
				operationExplorerView.setPatchEngine(patchEngine);
				operationExplorerView.setEditor(editorPart);

				// Opening and setting report view
				ReportView reportView = UIUtil.showView(ReportView.class, ReportView.ID);
				reportView.setPatchReportManager(patchEngine.getPatchReportManager());

				return Status.OK_STATUS;
			});
		});
	}

	protected Set<String> getDocumentTypes(Resource resource) {
		Set<String> documentTypes = EMFModelAccess.getDocumentTypes(resource, Scope.RESOURCE);

		IDifferenceProfileHandler.MANAGER.getDefaultExtension(documentTypes)
			.filter(profileHandler -> profileHandler.isProfiled(targetResource))
			.ifPresent(profileHandler -> documentTypes.addAll(profileHandler.getProfileTypes(targetResource)));

		return documentTypes;
	}

}
