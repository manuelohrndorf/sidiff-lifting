package org.sidiff.patching.ui.wsupdate.wizard;

import java.io.FileNotFoundException;
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
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.difference.profiles.handler.DifferenceProfileHandlerUtil;
import org.sidiff.difference.profiles.handler.IDifferenceProfileHandler;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.api.settings.ExecutionMode;
import org.sidiff.patching.api.settings.PatchMode;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.ValidationMode;
import org.sidiff.patching.api.util.PatchingUtils;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.animation.GMFAnimation;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.patching.ui.perspective.SiLiftPerspective;
import org.sidiff.patching.ui.view.OperationExplorerView;
import org.sidiff.patching.ui.view.ReportView;
import org.sidiff.patching.ui.wsupdate.Activator;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;

//TODO Migration: Test class
public class WorkspaceUpdateWizard extends Wizard {

	private WorkspaceUpdatePage01 threeWayMergePage01;
	private WorkspaceUpdatePage02 threeWayMergePage02;

	private WSUModels mergeModels;
	private boolean validationState;
	private PatchingSettings settings;

	// Symmetric and asymmetric difference:
	private Difference fullDiff = null;

	public WorkspaceUpdateWizard(WSUModels mergeModels) {
		this.setWindowTitle("Workspace Update Wizard");
		this.mergeModels = mergeModels;
		this.settings = new PatchingSettings(mergeModels.getDocumentTypes());
	}

	@Override
	public void addPages() {
		threeWayMergePage01 = new WorkspaceUpdatePage01(
				mergeModels, "ThreeWayMergePage01",
				"Workspace update", settings);
		addPage(threeWayMergePage01);

		threeWayMergePage02 = new WorkspaceUpdatePage02(
				mergeModels, "ThreeWayMergePage02",
				"Workspace update", settings, threeWayMergePage01);
		addPage(threeWayMergePage02);
	}

	@Override
	public boolean canFinish() {
		return threeWayMergePage01.isPageComplete()
				&& threeWayMergePage02.isPageComplete();
	}

	private void finish() {
		settings.setExecutionMode(ExecutionMode.INTERACTIVE);
		settings.setPatchMode(PatchMode.MERGING);

		// Gather all information
		final WSUModels configuredMergeModels = this.threeWayMergePage01
				.getMergeModelsWidget().getMergeModels();
		final Scope scope = settings.getScope();
		final ValidationMode validationMode = settings.getValidationMode();
		final IMatcher matcher = settings.getMatcher();

		// First create a patch between BASE<->THEIRS
		final InputModels inputModels = new InputModels(mergeModels.getFileBase(), mergeModels.getFileTheirs());
		final Resource resourceA = inputModels.getResources().get(0);
		final Resource resourceB = inputModels.getResources().get(1);

		Job job = new Job("Merging Models") {
			private EditingDomain editingDomain;

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					monitor.beginTask("Merging", 120);
					monitor.subTask("Creating a patch between BASE<->THEIRS");

					// Start difference calculations:
					fullDiff = calculateDifference(resourceA, resourceB);

					if (fullDiff == null) {
						return Status.CANCEL_STATUS;
					}
					monitor.worked(30);

					// Now apply that patch onto MINE
					final String separator = System
							.getProperty("file.separator");
					Resource targetResource = configuredMergeModels
							.getResourceMine();
					final IEditorIntegration domainEditor = IntegrationEditorAccess
							.getInstance().getIntegrationEditorForModel(resourceA);
					final boolean domainEditorSupportsDiagramming = domainEditor
							.supportsDiagramming(targetResource);
					final URI originalModelUri = targetResource.getURI();
					String savePath = EMFStorage.uriToPath(targetResource
							.getURI());
					savePath = savePath.replace(targetResource.getURI()
							.lastSegment(), "merged");
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
							tmpDiagramFileUri = domainEditor.copyDiagram(
									originalModelUri, savePath);
						} catch (FileNotFoundException e) {
							tmpDiagramFileUri = null;
						}
						diagramFileUri = tmpDiagramFileUri;
					} else {
						diagramFileUri = null;
					}
					final boolean useDiagramEditor = (diagramFileUri != null
							&& domainEditor
									.supportsGMFAnimation(diagramFileUri) && domainEditor
							.isDiagramEditorPresent());

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
								resource = domainEditor.getResource(editorPart);
								editingDomain = domainEditor
										.getEditingDomain(editorPart);
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
					IArgumentManager argumentManager = PatchingUtils.getArgumentManager(fullDiff.getAsymmetric(),
							resourceResult.get(), settings, IArgumentManager.Mode.INTERACTIVE);
					if(argumentManager == null) {
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								MessageDialog.openError(
										Display.getDefault().getActiveShell(),
										"No Argument Manager found!",
										"No suitable Argument Manager was found!");
							}
						});
						return Status.CANCEL_STATUS;
					}
					settings.setArgumentManager(argumentManager);

					// Dialog Patch interrupt handler
					settings.setInterruptHandler(new DialogPatchInterruptHandler());

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
								.getCharacteristicDocumentType(resourceResult
										.get());
					}
					
					ITransformationEngine transformationEngine = TransformationEngineUtil
							.getFirstTransformationEngine(documentType);
					if (transformationEngine == null) {
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								MessageDialog.openError(
										Display.getCurrent().getActiveShell(),
										"No Transformator Service found!",
										"No suitable Transformator Service found!");
							}
						});
						return Status.CANCEL_STATUS;
					}
					settings.setTransformationEngine(transformationEngine);

					// Get modified detector
					IModifiedDetector modifiedDetector = ModifiedDetectorUtil
							.getDefaultModifiedDetector(documentType);
					settings.setModifiedDetector(modifiedDetector);
					// Init detector if available
					if (modifiedDetector != null) {
						modifiedDetector.init(fullDiff.getAsymmetric()
								.getOriginModel(), resourceResult.get(),
								matcher, scope);
					}

					monitor.subTask("Initialize PatchEngine");
					final PatchEngine patchEngine = new PatchEngine(
							fullDiff.getAsymmetric(), resourceResult.get(),
							PatchingUtils.convertSettingsCompat(settings));

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
					monitor.worked(30);

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
					monitor.worked(10);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					monitor.done();
				}

				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	private Difference calculateDifference(Resource resourceA, Resource resourceB) {
		Difference fullDiff = null;

		// Init the lifting settings from the patching settings:
		settings.setCalculateEditRuleMatch(true);
		settings.setValidate(settings.getValidationMode() != ValidationMode.NO_VALIDATION);
		settings.setRecognitionEngineMode(RecognitionEngineMode.LIFTING_AND_POST_PROCESSING);

		try {
			fullDiff = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(resourceA, resourceB, settings);
		} catch (InvalidModelException e) {
			ValidateDialog validateDialog = new ValidateDialog();
			boolean skipValidation = validateDialog.openErrorDialog(
					Activator.PLUGIN_ID, e);

			if (skipValidation) {
				// Retry without validation:
				settings.setValidationMode(ValidationMode.NO_VALIDATION);
				fullDiff = calculateDifference(resourceA, resourceB);
			}
		} catch (NoCorrespondencesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fullDiff != null) {
			PipelineUtils.sortDifference(fullDiff.getSymmetric());
		}

		return fullDiff;
	}

	@Override
	public boolean performFinish() {
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
}
