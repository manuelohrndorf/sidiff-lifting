package org.sidiff.patching.ui.wizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
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
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.patch.animation.GMFAnimation;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.arguments.InteractiveArgumentManager;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.patching.ui.perspective.SiLiftPerspective;
import org.sidiff.patching.ui.view.OperationExplorerView;
import org.sidiff.patching.ui.view.ReportView;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;
import org.silift.patching.patch.Patch;
import org.silift.patching.patch.PatchCreator;
import org.silift.patching.settings.ExecutionMode;
import org.silift.patching.settings.PatchMode;
import org.silift.patching.settings.PatchingSettings;
import org.silift.patching.settings.PatchingSettings.ValidationMode;

public class ApplyPatchWizard extends Wizard {

	private ApplyPatchPage01 applyPatchPage01;
	private ApplyPatchPage02 applyPatchPage02;

	private IFile file;
	private Patch patch;
	private String patchName;
	private boolean validationState;
	private PatchingSettings settings;

	public ApplyPatchWizard(Patch patch, IFile file) {
		this.setWindowTitle("Apply Patch Wizard");
		this.file = file;
		this.patch = patch;
		this.patchName = file.getName();
		this.settings = new PatchingSettings();
	}

	@Override
	public void addPages() {
		applyPatchPage01 = new ApplyPatchPage01("ApplyPatchPage", "Apply Patch: " + patchName,
				getImageDescriptor("icon.png"), settings);
		applyPatchPage01.setFilterPath(file.getParent().getLocation().toString());
		

		applyPatchPage02 = new ApplyPatchPage02(patch, "ApplyPatchPage", "Apply Patch: " + patchName,
				getImageDescriptor("icon.png"),settings);
		addPage(applyPatchPage01);
		addPage(applyPatchPage02);
	}

	@Override
	public boolean canFinish() {
		return applyPatchPage01.isPageComplete() && applyPatchPage02.isPageComplete();
	}

	@Override
	 public IWizardPage getNextPage(IWizardPage page){
		if(page instanceof ApplyPatchPage01)
			((ApplyPatchPage01)page).updateSettings();
		else if (page instanceof ApplyPatchPage01)
			((ApplyPatchPage02)page).updateSettings();
		return super.getNextPage(page);
	 }
	
	@Override
	public IWizardPage getPreviousPage(IWizardPage page){
		if(page instanceof ApplyPatchPage01)
			((ApplyPatchPage01)page).updateSettings();
		else if (page instanceof ApplyPatchPage02)
			((ApplyPatchPage02)page).updateSettings();
		return super.getPreviousPage(page);
	}
	
	@Override
	public boolean performFinish() {

		applyPatchPage01.updateSettings();
		applyPatchPage02.updateSettings();
		settings.setExecutionMode(ExecutionMode.INTERACTIVE);
		settings.setPatchMode(PatchMode.PATCHING);
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

		// Gather all information
		final String separator = System.getProperty("file.separator");
		final String filename = this.applyPatchPage01.getTargetWidget().getFilename();
		final ValidationMode validationMode = settings.getValidationMode();
		final Integer reliability = settings.getMinReliability();
		final IMatcher matcher = settings.getMatcher();

		final URI fileURI = URI.createFileURI(filename);
		Resource targetResource = EMFStorage.eLoad(fileURI).eResource();
		ResourceSet diagramResourceSet = PatchCreator.deriveDiagrammFile(targetResource);
		String savePath = EMFStorage.uriToPath(targetResource.getURI());
		savePath = savePath.replace(targetResource.getURI().lastSegment(), "patched");
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
									//EMFValidate.validateObject(resourceResult.get().getContents().get(0));
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
					settings.setArgumentManager(argumentManager);
					
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
					settings.setTransformationEngine(transformationEngine);
					
					// Patch interrupt handler
					IPatchInterruptHandler patchInterruptHandler = new DialogPatchInterruptHandler();
					settings.setInterruptHandler(patchInterruptHandler);
					
					
					monitor.subTask("Initialize PatchEngine");			
					final PatchEngine patchEngine = new PatchEngine(patch.getDifference(), resourceResult.get(), settings);
				
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
					monitor.worked(60);

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


	protected ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
