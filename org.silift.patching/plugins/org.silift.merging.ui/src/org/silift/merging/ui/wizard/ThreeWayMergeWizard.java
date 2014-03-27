package org.silift.merging.ui.wizard;

import java.io.File;
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
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.ide.IDE;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.util.InputModels;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.patch.animation.GMFAnimation;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.arguments.InteractiveArgumentManager;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.patching.ui.perspective.SiLiftPerspective;
import org.sidiff.patching.ui.view.OperationExplorerView;
import org.sidiff.patching.ui.view.ReportView;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.emf.Scope;
import org.silift.difference.lifting.settings.LiftingSettings;
import org.silift.difference.lifting.settings.LiftingSettings.RecognitionEngineMode;
import org.silift.merging.ui.Activator;
import org.silift.merging.ui.util.MergeModels;
import org.silift.modifieddetector.IModifiedDetector;
import org.silift.modifieddetector.util.ModifiedDetectorUtil;
import org.silift.patching.patch.PatchCreator;
import org.silift.patching.settings.ExecutionMode;
import org.silift.patching.settings.PatchMode;
import org.silift.patching.settings.PatchingSettings;
import org.silift.patching.settings.PatchingSettings.ValidationMode;

public class ThreeWayMergeWizard extends Wizard {

	private ThreeWayMergePage01 threeWayMergePage01;
	private ThreeWayMergePage02 threeWayMergePage02;

	private MergeModels mergeModels;
	private boolean validationState;
	private PatchingSettings patchingSettings;

	//Later used Differencef
	Difference fullDiff = null;
	
	public ThreeWayMergeWizard(IFile fileMine, IFile fileTheirs, IFile fileBase) {
		this.setWindowTitle("Three-Way-Merge Wizard");
		
		this.mergeModels = new MergeModels(fileMine, fileTheirs, fileBase);
		this.patchingSettings = new PatchingSettings();
	}

	@Override
	public void addPages() {
		threeWayMergePage01 = new ThreeWayMergePage01(mergeModels,
				"ThreeWayMergePage", "Merge three models", getImageDescriptor("icon.png"), patchingSettings);
		addPage(threeWayMergePage01);
		
		threeWayMergePage02 = new ThreeWayMergePage02(mergeModels,
				"ThreeWayMergePage", "Merge three models", getImageDescriptor("icon.png"), patchingSettings);
		addPage(threeWayMergePage02);
	}

	@Override
	 public IWizardPage getNextPage(IWizardPage page){
		if(page instanceof ThreeWayMergePage01)
			((ThreeWayMergePage01)page).updateSettings();
		else if (page instanceof ThreeWayMergePage02)
			((ThreeWayMergePage02)page).updateSettings();
		return super.getNextPage(page);
	 }
	
	@Override
	public IWizardPage getPreviousPage(IWizardPage page){
		if(page instanceof ThreeWayMergePage01)
			((ThreeWayMergePage01)page).updateSettings();
		else if (page instanceof ThreeWayMergePage02)
			((ThreeWayMergePage02)page).updateSettings();
		return super.getPreviousPage(page);
	}
	
	@Override
	public boolean canFinish() {
		return threeWayMergePage01.isPageComplete() && threeWayMergePage02.isPageComplete() ;
	}
	
	@Override
	public boolean performFinish() {
		threeWayMergePage01.updateSettings();
		threeWayMergePage02.updateSettings();
		patchingSettings.setExecutionMode(ExecutionMode.INTERACTIVE);
		patchingSettings.setPatchMode(PatchMode.MERGING);
		//Gather all information
		final MergeModels configuredMergeModels = this.threeWayMergePage01.getMergeModelsWidget().getMergeModels();
		final Scope scope = patchingSettings.getScope();
		final ValidationMode validationMode = patchingSettings.getValidationMode();
		final LiftingSettings liftingSettings  = new LiftingSettings(patchingSettings.getScope(), patchingSettings.getRuleBases(), 
													patchingSettings.getMatcher(), patchingSettings.getTechBuilder(), 
													patchingSettings.getValidationMode()==ValidationMode.NO_VALIDATION?false:true,
													RecognitionEngineMode.POST_PROCESSED);
		final Integer reliability = patchingSettings.getMinReliability();
		final IMatcher matcher = patchingSettings.getMatcher();

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
						fullDiff = AsymmetricDiffFacade.liftMeUp(resourceA, resourceB, liftingSettings);
						PipelineUtils.sortDifference(fullDiff.getSymmetric());

					}catch(final InvalidModelException e){
						ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
						return Status.CANCEL_STATUS;
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
					
					String filePath = savePath + separator + targetResource.getURI().lastSegment();

					if (!diagramResourceSet.getResources().isEmpty()) {
						filePath+= "diag";			
						for (Resource resource : diagramResourceSet.getResources()) {
							EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath + separator + resource.getURI().lastSegment()),
									resource.getContents().get(0), false);
						}
					}
					
					final String finalFilePath = filePath;

					final File fileToOpen = new File(finalFilePath);

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

									if(finalFilePath.endsWith("diag")){
										GMFAnimation.enableAnimation(resource, false, GMFAnimation.MODE_TRIGGER);
									}
								} else if (editorPart instanceof IEditingDomainProvider) {
									IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
									resource = editor.getEditingDomain().getResourceSet().getResources().get(0);
									editingDomain = editor.getEditingDomain();
								}
								resourceResult.set(resource);
								if (validationMode != ValidationMode.NO_VALIDATION){
									//TODO: Nur Multiplicity-Check (hat nichts mit validationMode zu tun)
									// EMFValidate.validateObject(resourceResult.get().getContents().get(0));
								}
								validationState = true;								
							} catch (PartInitException e) {
								e.printStackTrace();
							} 
//							catch (InvalidModelException e) {
//								ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
//								validationState = false;
//							}
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
					
					// Patch interrupt handler
					IPatchInterruptHandler patchInterruptHandler = new DialogPatchInterruptHandler();
					
					// Get modified detector
					IModifiedDetector modifiedDetector = ModifiedDetectorUtil.getAvailableModifiedDetector(documentType);
					
					//Init detector if available
					if(modifiedDetector != null){
						modifiedDetector.init(fullDiff.getAsymmetric().getOriginModel(), resourceResult.get(), matcher, scope);
					}
					
					monitor.subTask("Initialize PatchEngine");					
					final PatchEngine patchEngine = new PatchEngine(fullDiff.getAsymmetric(), resourceResult.get(),
							argumentManager, transformationEngine, patchingSettings, patchInterruptHandler, modifiedDetector);

					if(finalFilePath.endsWith("diag")){
						patchEngine.getPatchReportManager().addPatchReportListener(new IPatchReportListener() {

							@Override
							public void reportChanged() {
								GMFAnimation.trigger();
							}

							@Override
							public void pushReport(int i) {
								// TODO Auto-generated method stub

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
								Activator.getDefault().getWorkbench().showPerspective(SiLiftPerspective.ID,  PlatformUI.getWorkbench().getActiveWorkbenchWindow()); 
								
								//Opening and setting operation explorer view
								OperationExplorerView operationExplorerView = (OperationExplorerView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(OperationExplorerView.ID);
								operationExplorerView.setPatchEngine(patchEngine);
								operationExplorerViewReference.set(operationExplorerView);
								
								//Opening and setting report view
								ReportView reportView = (ReportView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ReportView.ID);
								reportView.setPatchReportManager(patchEngine.getPatchReportManager());
								reportViewReference.set(reportView);
							} catch (PartInitException e) {
								e.printStackTrace();
							} catch (WorkbenchException e) {
								e.printStackTrace();
							}
						}
					});
					OperationExplorerView operationExplorerView = operationExplorerViewReference.get();
					ReportView reportView = reportViewReference.get();
					monitor.worked(20);

					// ModelChangeHandler works independent; PatchView is
					// interested in model changes
					monitor.subTask("Adding Modellistener");
					ModelAdapter adapter = new ModelAdapter(resourceResult.get());
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
		return true;
	}


	protected ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
