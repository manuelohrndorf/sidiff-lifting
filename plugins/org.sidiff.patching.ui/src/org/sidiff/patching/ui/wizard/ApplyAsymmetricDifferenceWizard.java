package org.sidiff.patching.ui.wizard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.profiles.handler.DifferenceProfileHandlerUtil;
import org.sidiff.difference.profiles.handler.IDifferenceProfileHandler;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.api.settings.ExecutionMode;
import org.sidiff.patching.api.settings.PatchMode;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.ValidationMode;
import org.sidiff.patching.api.util.PatchingUtils;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.animation.GMFAnimation;
import org.sidiff.patching.ui.arguments.InteractiveArgumentManager;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.patching.ui.perspective.SiLiftPerspective;
import org.sidiff.patching.ui.view.OperationExplorerView;
import org.sidiff.patching.ui.view.ReportView;

//TODO Migration: Test class

public class ApplyAsymmetricDifferenceWizard extends Wizard {

	private ApplyAsymmetricDifferencePage01 applyAsymmetricDifferencePage01;
	private ApplyAsymmetricDifferencePage02 applyAsymmetricDifferencePage02;

	private AsymmetricDifference asymmetricDifference;
	private String patchName;
	private boolean validationState;
	private PatchingSettings settings;

	public ApplyAsymmetricDifferenceWizard(AsymmetricDifference asymmetricDifference) {
		this.setWindowTitle("Apply Patch Wizard");
		this.asymmetricDifference = asymmetricDifference;
		this.patchName = asymmetricDifference.eResource().getURI().lastSegment();
		this.settings = new PatchingSettings();
	}

	@Override
	public void addPages() {
		applyAsymmetricDifferencePage01 = new ApplyAsymmetricDifferencePage01(asymmetricDifference,
				"ApplyAsymmetricDifferencePage01", "Apply Patch: " + patchName, settings);
		addPage(applyAsymmetricDifferencePage01);

		applyAsymmetricDifferencePage02 = new ApplyAsymmetricDifferencePage02(asymmetricDifference,
				"ApplyAsymmetricDifferencePage02", "Apply Patch: " + patchName, settings, applyAsymmetricDifferencePage01);
		addPage(applyAsymmetricDifferencePage02);
	}

	@Override
	public boolean canFinish() {
		return applyAsymmetricDifferencePage01.isPageComplete()
				&& applyAsymmetricDifferencePage02.isPageComplete();
	}

	@Override
	public boolean performFinish() {

		settings.setExecutionMode(ExecutionMode.INTERACTIVE);
		settings.setPatchMode(PatchMode.PATCHING);
		try {
			getContainer().run(false, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					finish();
				}
			});
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		return true;
	}

	private void finish() {

		// Gather all information
		final String separator = System.getProperty("file.separator");
		final String filename = this.applyAsymmetricDifferencePage01.getTargetWidget()
				.getFilename();
		final ValidationMode validationMode = settings.getValidationMode();

		final URI fileURI = URI.createFileURI(filename);
		Resource targetResource = EMFStorage.eLoad(fileURI).eResource();
		final IEditorIntegration domainEditor = IntegrationEditorAccess.getInstance()
				.getIntegrationEditorForModel(targetResource);
		final boolean domainEditorSupportsDiagramming = domainEditor
				.supportsDiagramming(targetResource);
		final URI originalModelUri = targetResource.getURI();
		String savePath = EMFStorage.uriToPath(targetResource.getURI());
		savePath = savePath.replace(targetResource.getURI().lastSegment(),
				"patched");
		EMFStorage.eSaveAs(
				EMFStorage.pathToUri(savePath + separator
						+ targetResource.getURI().lastSegment()),
				targetResource.getContents().get(0), true);
		final String modelFilePath = savePath + separator
				+ targetResource.getURI().lastSegment();
		final URI diagramFileUri;
		if (domainEditorSupportsDiagramming) {
			URI tmpDiagramFileUri;
			try {
				tmpDiagramFileUri = domainEditor.copyDiagram(originalModelUri,
						savePath);
			} catch (FileNotFoundException e) {
				tmpDiagramFileUri = null;
			}
			diagramFileUri = tmpDiagramFileUri;
		} else {
			diagramFileUri = null;
		}
		final boolean useDiagramEditor = (diagramFileUri != null
				&& domainEditor.supportsGMFAnimation(diagramFileUri) && domainEditor
				.isDiagramEditorPresent());

		Job job = new Job("Patching Model") {
			private EditingDomain editingDomain;

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					monitor.beginTask("Patching", 120);
					monitor.subTask("Opening editor for target resource");
					final AtomicReference<Resource> resourceResult = new AtomicReference<Resource>();
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							Resource resource = null;
							if (domainEditor != null) {
								IEditorPart editorPart;
								if (useDiagramEditor) {
									editorPart = domainEditor
											.openDiagram(diagramFileUri);
								} else if (domainEditor.isDefaultEditorPresent()) {
									editorPart = domainEditor.openModelInDefaultEditor(EMFStorage
											.pathToFileUri(modelFilePath));
								} else {
									MessageDialog.openError(Display
											.getCurrent().getActiveShell(),
											"Error", "No editor present");
									return;
								}
								if (editorPart == null) {
									MessageDialog.openError(Display
											.getCurrent().getActiveShell(),
											"Error", "No editor present");
									return;
								}
								editingDomain = domainEditor
										.getEditingDomain(editorPart);
								resource = domainEditor.getResource(editorPart);
								if (resource == null || editingDomain == null) {
									MessageDialog
											.openError(Display.getCurrent()
													.getActiveShell(), "Error",
													"Error getting resource or editing domain from the editor");
									return;
								}
								if (useDiagramEditor
										&& domainEditor
												.supportsGMFAnimation(diagramFileUri)) {
									GMFAnimation.enableAnimation(resource,
											false, GMFAnimation.MODE_TRIGGER);
								}
							}
							resourceResult.set(resource);
							if (validationMode != ValidationMode.NO_VALIDATION) {
								// TODO: Nur Multiplicity-Check (hat nichts
								// mit validationMode zu tun)
								// EMFValidate.validateObject(resourceResult.get().getContents().get(0));
							}
							validationState = true;
						}
					});
					if (!validationState) {
						return Status.CANCEL_STATUS;
					}
					monitor.worked(20);

					// Use interactive argument manager
					IArgumentManager argumentManager = PatchingUtils.getArgumentManager(("org.sidiff.patching.adapter.superimposition.ArgumentManager"));
					if(argumentManager == null || !argumentManager.canResolveArguments(asymmetricDifference, resourceResult.get())){
						argumentManager = new InteractiveArgumentManager(
								settings.getMatcher());
					}
					argumentManager.setMinReliability(settings
							.getMinReliability());
					settings.setArgumentManager(argumentManager);

					// Find transformation engine (no other available right now)
					String documentType = null;
					Set<String> documentTypes = EMFModelAccess
							.getDocumentTypes(resourceResult.get(),
									Scope.RESOURCE);
					IDifferenceProfileHandler profileHandler = null;
					for (String docType : documentTypes) {
						// at the moment there is only one profile handler
						// available
						profileHandler = DifferenceProfileHandlerUtil
								.getDefaultDifferenceProfileHandler(docType);
						if (profileHandler != null) {
							break;
						}
					}
					if (profileHandler != null
							&& profileHandler.isProfiled(resourceResult.get())) {
						documentType = profileHandler.getBaseType();
					} else {
						documentType = EMFModelAccess
								.getCharacteristicDocumentType(resourceResult.get());
					}
					ITransformationEngine transformationEngine = TransformationEngineUtil
							.getFirstTransformationEngine(documentType);
					if (transformationEngine == null) {
						MessageDialog.openError(Display.getCurrent()
								.getActiveShell(),
								"No Transformator Service found!",
								"No suitable Transformator Service found!");
						return Status.CANCEL_STATUS;
					}
					settings.setTransformationEngine(transformationEngine);

					// Patch interrupt handler
					IPatchInterruptHandler patchInterruptHandler = new DialogPatchInterruptHandler();
					settings.setInterruptHandler(patchInterruptHandler);
					settings.setCorrespondencesService(CorrespondencesUtil.getDefaultCorrespondencesService());
					monitor.subTask("Initialize PatchEngine");
					final PatchEngine patchEngine = new PatchEngine(
							asymmetricDifference,
							resourceResult.get(), PatchingUtils.convertSettingsCompat(settings));

					if (useDiagramEditor
							&& domainEditor
									.supportsGMFAnimation(diagramFileUri)) {
						patchEngine.getPatchReportManager()
								.addPatchReportListener(
										new IPatchReportListener() {

											@Override
											public void reportChanged() {
												GMFAnimation.trigger();
											}

											@Override
											public void pushReport(int i) {
												// TODO Auto-generated method
												// stub

											}
										});
					}
					patchEngine.setPatchedEditingDomain(editingDomain);
					monitor.worked(60);

					monitor.subTask("Opening SiLift Perspective");
					final AtomicReference<OperationExplorerView> operationExplorerViewReference = new AtomicReference<OperationExplorerView>();
					final AtomicReference<ReportView> reportViewReference = new AtomicReference<ReportView>();
					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							try {
								Activator
										.getDefault()
										.getWorkbench()
										.showPerspective(
												SiLiftPerspective.ID,
												PlatformUI
														.getWorkbench()
														.getActiveWorkbenchWindow());

								// Opening and setting operation explorer view
								OperationExplorerView operationExplorerView = (OperationExplorerView) PlatformUI
										.getWorkbench()
										.getActiveWorkbenchWindow()
										.getActivePage()
										.showView(OperationExplorerView.ID);
								operationExplorerView
										.setPatchEngine(patchEngine);
								operationExplorerViewReference
										.set(operationExplorerView);

								// Opening and setting report view
								ReportView reportView = (ReportView) PlatformUI
										.getWorkbench()
										.getActiveWorkbenchWindow()
										.getActivePage()
										.showView(ReportView.ID);
								reportView.setPatchReportManager(patchEngine
										.getPatchReportManager());
								reportViewReference.set(reportView);

							} catch (PartInitException e) {
								e.printStackTrace();
							} catch (WorkbenchException e) {
								e.printStackTrace();
							}
						}
					});
					OperationExplorerView operationExplorerView = operationExplorerViewReference.get();
					monitor.worked(20);

					// ModelChangeHandler works independent; PatchView is
					// interested in model changes
					monitor.subTask("Adding Modellistener");
					ModelAdapter adapter = new ModelAdapter(
							resourceResult.get());
					adapter.addListener(new ModelChangeHandler(argumentManager));
					adapter.addListener(operationExplorerView);
					monitor.worked(20);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					monitor.done();
				}

				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}
}
