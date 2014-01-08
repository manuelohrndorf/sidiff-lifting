package org.silift.merging.ui.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.sidiff.common.emf.EMFValidate;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffSettings;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.lifting.facade.LiftingSettings;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchEngine.ExecutionMode;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.arguments.InteractiveArgumentManager;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.patching.ui.view.PatchView;
import org.sidiff.patching.ui.view.ReportView;
import org.sidiff.patching.validation.ValidationMode;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.emf.Scope;
import org.silift.merging.ui.Activator;
import org.silift.merging.ui.util.MergeModels;
import org.silift.patching.patch.PatchCreator;

public class ThreeWayMergeWizard extends Wizard {

	private ThreeWayMergePage01 threeWayMergePage01;
	private ThreeWayMergePage02 threeWayMergePage02;

	private MergeModels mergeModels;
	private boolean validationState;

	//Later used Differencef
	Difference fullDiff = null;
	
	public ThreeWayMergeWizard(IFile fileMine, IFile fileTheirs, IFile fileBase) {
		this.setWindowTitle("Three-Way-Merge Wizard");
		
		this.mergeModels = new MergeModels(fileMine, fileTheirs, fileBase);
	}

	@Override
	public void addPages() {
		threeWayMergePage01 = new ThreeWayMergePage01(mergeModels,
				"ThreeWayMergePage", "Merge three models", getImageDescriptor("icon.png"));
		addPage(threeWayMergePage01);
		
		threeWayMergePage02 = new ThreeWayMergePage02(mergeModels,
				"ThreeWayMergePage", "Merge three models", getImageDescriptor("icon.png"));
		addPage(threeWayMergePage02);
	}

	@Override
	public boolean canFinish() {
		return threeWayMergePage01.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		
		try {
			getContainer().run(false, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
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

		//Gather all information
		final MergeModels configuredMergeModels = this.threeWayMergePage01.getMergeModelsWidget().getMergeModels();
		final Scope scope = this.threeWayMergePage01.getScopeWidget().getSelection();
		final ValidationMode validationMode = this.threeWayMergePage01.getValidationModeWidget().getSelection();
		final LiftingSettings liftingSettings  = readSettings();
		final Integer reliability = this.threeWayMergePage02.getReliabilityWidget().getReliability();
		final IMatcher matcher = this.threeWayMergePage02.getSelectedMatchingEngine();

		//First create a patch between BASE<->THEIRS
		final InputModels inputModels = new InputModels(mergeModels.getFileBase(), mergeModels.getFileTheirs());
		final Resource resourceA = inputModels.getResourceA();
		final Resource resourceB = inputModels.getResourceB();

		Job job = new Job("Merging Models") {
			private EditingDomain editingDomain;

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					monitor.beginTask("Merging", 120);
					monitor.subTask("Creating a patch between BASE<->THEIRS");

					try{
						fullDiff = AsymmetricDiffFacade.liftMeUp(resourceA, resourceB, new AsymmetricDiffSettings(liftingSettings));
						PipelineUtils.sortDifference(fullDiff.getSymmetric());

					}catch(InvalidModelException e){
						ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
					}
					
					monitor.worked(30);
					//Now apply that patch onto MINE
					final String separator = System.getProperty("file.separator");
					Resource targetResource = configuredMergeModels.getResourceMine();
					ResourceSet diagramResourceSet = PatchCreator.deriveDiagrammFile(targetResource);
					String savePath = EMFStorage.uriToPath(targetResource.getURI());
					savePath = savePath.replace(targetResource.getURI().lastSegment(), "merged");
					EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath + separator + targetResource.getURI().lastSegment()),
							targetResource.getContents().get(0), true);

					if (!diagramResourceSet.getResources().isEmpty()) {
						for (Resource resource : diagramResourceSet.getResources()) {
							EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath + separator + resource.getURI().lastSegment()),
									resource.getContents().get(0), false);
						}
					}

					final File fileToOpen = new File(savePath + separator + targetResource.getURI().lastSegment());

					monitor.subTask("Opening editor for target resource");
					final AtomicReference<Resource> resourceResult = new AtomicReference<Resource>();
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							try {
								IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
										.getActivePage();
								IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
								IEditorPart editorPart = IDE.openEditorOnFileStore(page, fileStore);
								Resource resource = null;
								if (editorPart instanceof EcoreDiagramEditor) {
									EcoreDiagramEditor editor = (EcoreDiagramEditor) editorPart;
									resource = editor.getDiagram().getElement().eResource();
									editingDomain = editor.getEditingDomain();

									// FIXME: Diagram animation:
									// GMFAnimation.enableAnimation(resource,false);
								} else if (editorPart instanceof IEditingDomainProvider) {
									IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
									resource = editor.getEditingDomain().getResourceSet().getResources().get(0);
									editingDomain = editor.getEditingDomain();
								}
								resourceResult.set(resource);
								if (validationMode != ValidationMode.NO)
									EMFValidate.validateObject(resourceResult.get().getContents().get(0));
								validationState = true;
							} catch (PartInitException e) {
								e.printStackTrace();
							} catch (InvalidModelException e) {
								ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
								validationState = false;
							}
						}
					});
					if (!validationState) {
						return Status.CANCEL_STATUS;
					}
					monitor.worked(20);

					// Use interactive argument manager
					IArgumentManager argumentManager = new InteractiveArgumentManager(matcher);
					argumentManager.setMinReliability(reliability);

					// Find transformation engine (no other available right now)
					String documentType = null;
					if (EMFModelAccessEx.isProfiled(resourceResult.get())) {
						documentType = EMFModelAccessEx.getBaseDocumentType(resourceResult.get());
					} else {
						documentType = EMFModelAccessEx.getCharacteristicDocumentType(resourceResult.get());
					}
					ITransformationEngine transformationEngine = TransformationEngineUtil
							.getFirstTransformationEngine(documentType);
					if (transformationEngine == null) {
						MessageDialog.openError(Display.getCurrent().getActiveShell(),
								"No Transformator Service found!", "No suitable Transformator Service found!");
						return Status.CANCEL_STATUS;
					}
					
					IPatchInterruptHandler patchInterruptHandler = new DialogPatchInterruptHandler();

					monitor.subTask("Initialize PatchEngine");					
					final PatchEngine patchEngine = new PatchEngine(fullDiff.getAsymmetric(), resourceResult.get(),
							argumentManager, transformationEngine, ExecutionMode.INTERACTIVE, validationMode,
							scope, matcher.canComputeReliability(), patchInterruptHandler);
					patchEngine.setPatchedEditingDomain(editingDomain);
					monitor.worked(30);

					monitor.subTask("Open Merge View");
					final AtomicReference<PatchView> patchViewReference = new AtomicReference<PatchView>();
					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							try {
								PatchView patchView = (PatchView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
										.getActivePage().showView(PatchView.ID);
								patchView.setPatchEngine(patchEngine);
								patchViewReference.set(patchView);
							} catch (PartInitException e) {
								e.printStackTrace();
							}
						}
					});
					PatchView patchView = patchViewReference.get();
					monitor.worked(20);

					monitor.subTask("Open Report View");
					final AtomicReference<ReportView> reportViewReference = new AtomicReference<ReportView>();
					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							try {
								ReportView reportView = (ReportView) PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getActivePage().showView(ReportView.ID);
								reportView.setPatchReportManager(patchEngine.getPatchReportManager());
								reportViewReference.set(reportView);
							} catch (PartInitException e) {
								e.printStackTrace();
							}
						}
					});
					ReportView reportView = reportViewReference.get();
					monitor.worked(10);

					// ModelChangeHandler works independent; PatchView is
					// interested in model changes
					monitor.subTask("Adding Modellistener");
					ModelAdapter adapter = new ModelAdapter(resourceResult.get());
					adapter.addListener(new ModelChangeHandler(argumentManager));
					adapter.addListener(patchView);
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

	public LiftingSettings readSettings() {

		/*
		 * Do lifting settings
		 */

		LiftingSettings liftingSettings = new LiftingSettings();

		liftingSettings.setValidate(threeWayMergePage01.isValidateModels());
		// Used matcher
		liftingSettings.setMatcher(threeWayMergePage02.getSelectedMatchingEngine());

		//Used technical difference builder
		liftingSettings.setTechnicalDifferenceBuilder(threeWayMergePage02.getSelectedTechnicalDifferenceBuilder());

		// Do lifting..?
		liftingSettings.setDoLifting(true);

		// Use Postprocessor..?
		liftingSettings.setPostProcess(true);

		// Used rulebases
		liftingSettings.setUsedRulebases(threeWayMergePage01.getSelectedRulebases());

		return liftingSettings;
	}

	protected ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
