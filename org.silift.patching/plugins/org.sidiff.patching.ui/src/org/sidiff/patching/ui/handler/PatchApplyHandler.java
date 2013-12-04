package org.sidiff.patching.ui.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.jdom.Attribute;
import org.jdom.JDOMException;
import org.sidiff.common.emf.EMFValidate;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.patching.IPatchCorrespondence;
import org.sidiff.patching.ITransformationEngine;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchEngine.ExecutionMode;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.dialog.ChooseModelDialog;
import org.sidiff.patching.ui.view.PatchView;
import org.sidiff.patching.ui.view.ReportView;
import org.sidiff.patching.util.CorrespondenceUtil;
import org.sidiff.patching.util.PatchUtil;
import org.sidiff.patching.util.TransformatorUtil;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.file.FileOperations;
import org.silift.common.util.file.XMLUtil;

public class PatchApplyHandler extends AbstractHandler {
	private Logger LOGGER = Logger.getLogger(PatchApplyHandler.class.getName());
	public boolean validationState = true;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);

		if (selection.size() == 1) {
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IFile) {
				IFile iFile = (IFile) firstElement;
				if (!iFile.getFileExtension().equals(PatchUtil.PATCH_EXTENSION))
					return -1;

				String patchPath = PatchUtil.extractPatch(iFile.getLocation()).getAbsolutePath();
				String separator = System.getProperty("file.separator");
				
				File dir = new File(patchPath);
				File asymmetricDifference = null;
				String[] children = dir.list();
				for (int i=0; i<children.length; i++) {
					if(children[i].endsWith(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)){
						asymmetricDifference = new File(patchPath + separator + children[i]);
						break;
					}		
				}
				// Load AsymmetricDifference
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource patchResource = resourceSet.getResource(URI.createFileURI(asymmetricDifference.getAbsolutePath()), true);

				if (patchResource.getContents().size() == 0) {
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Error in patch model", "There is something wrong with this patch!");
					return null;
				}
				EObject root = patchResource.getContents().get(0);
				if (root instanceof AsymmetricDifference) {
					final AsymmetricDifference difference = (AsymmetricDifference) root;
					final ChooseModelDialog dialog = new ChooseModelDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ((IFile)firstElement).getParent().getLocation().toOSString());
					if (dialog.open() == Window.OK) {
						validationState = dialog.validate();
						String filename = dialog.getFile();
						final URI fileURI = URI.createFileURI(filename);
						Resource targetResource = EMFStorage.eLoad(fileURI).eResource();
						String lastSegment = targetResource.getURI().lastSegment();
						String[] name = lastSegment.split("\\.");
						String savePath = EMFStorage.uriToPath(targetResource.getURI());
						savePath = savePath.replace(targetResource.getURI().lastSegment(), name[0] + "_epatched" + "." +name[1]);
						//TODO Diagram
						EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath), targetResource.getContents().get(0), true);
						final float minReliability = dialog.getReliability();
						final File fileToOpen = new File(savePath);
						
//						File file = new File(ecoreDiag.toFileString());
						
//						URI ecoreDiag = fileURI.trimFileExtension().appendFileExtension("ecorediag");
//						if (file.exists()) {
//							fileToOpen = file;
//						} else {
//							fileToOpen = new File(fileURI.toFileString());
//						}

						Job job = new Job("Patching") {

							@Override
							protected IStatus run(IProgressMonitor monitor) {
								try {
									monitor.beginTask("Loading Patch", 100);
									monitor.subTask("Opening view and editor");
									final AtomicReference<Resource> resourceResult = new AtomicReference<Resource>();
									Display.getDefault().syncExec(new Runnable() {
										@Override
										public void run() {
											try {
												IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
												IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
												IEditorPart editorPart = IDE.openEditorOnFileStore(page, fileStore);
												Resource resource = null;
												if (editorPart instanceof EcoreDiagramEditor) {
													EcoreDiagramEditor editor = (EcoreDiagramEditor) editorPart;
													resource = editor.getDiagram().getElement().eResource();
												} else if (editorPart instanceof IEditingDomainProvider) {
													IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
													resource = editor.getEditingDomain().getResourceSet().getResources().get(0);
												}
												resourceResult.set(resource);
												if(validationState)
													EMFValidate.validateObject(resourceResult.get().getContents().get(0));
												validationState = true;
											} catch (PartInitException e) {
												e.printStackTrace();
											} catch (InvalidModelException e) {
												// TODO Auto-generated catch block
												ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
												validationState = false;
											}
										}
									});
									if(!validationState)
										return Status.CANCEL_STATUS;
									monitor.worked(20);

									// Find patch correspondence
									String documentType = null;
									if (EMFModelAccessEx.isProfiled(resourceResult.get())){
										documentType = EMFModelAccessEx.getBaseDocumentType(resourceResult.get());
									} else {
										documentType = EMFModelAccessEx.getCharacteristicDocumentType(resourceResult.get());
									}											
									IPatchCorrespondence correspondence = CorrespondenceUtil.getFirstPatchCorrespondence(documentType);
									if (correspondence == null) {
										LOGGER.log(Level.SEVERE, "No Correspondence Service found!");
										MessageDialog.openError(Display.getCurrent().getActiveShell(), "No Correspondence Service found!", "No suitable Correspondence Service found!");
										return Status.CANCEL_STATUS;
									}

									// Find transformation engine
									ITransformationEngine transformationEngine = TransformatorUtil.getFirstTransformationEngine(documentType);
									if (transformationEngine == null) {
										LOGGER.log(Level.SEVERE, "No Transformator Service found!");
										MessageDialog.openError(Display.getCurrent().getActiveShell(), "No Transformator Service found!", "No suitable Transformator Service found!");
										return Status.CANCEL_STATUS;
									}

									monitor.subTask("Initialize PatchEngine");
									correspondence.setMinReliability(minReliability);
									final PatchEngine patchEngine = new PatchEngine(difference, resourceResult.get(), correspondence, transformationEngine, ExecutionMode.INTERACTIVE);
									monitor.worked(40);

									monitor.subTask("Open Patch View");
									final AtomicReference<PatchView> viewResult = new AtomicReference<PatchView>();
									Display.getDefault().syncExec(new Runnable() {

										@Override
										public void run() {
											try {
												PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ReportView.ID);
												PatchView patchView = (PatchView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(PatchView.ID);
												patchView.setPatchEngine(patchEngine);
												viewResult.set(patchView);
											} catch (PartInitException e) {
												e.printStackTrace();
											}
										}});
									PatchView patchView = viewResult.get();
									monitor.worked(20);

									
									// ModelChangeHandler works independent
									monitor.subTask("Adding Modellistener");
									ModelAdapter adapter = new ModelAdapter(resourceResult.get());
									adapter.addListener(new ModelChangeHandler(correspondence));
									adapter.addListener(patchView);
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
				} else {
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "No patch model", "This is no patch model!");
				}
			}
		} else if (selection.size() == 2) {

		}
		return null;
	}
}
