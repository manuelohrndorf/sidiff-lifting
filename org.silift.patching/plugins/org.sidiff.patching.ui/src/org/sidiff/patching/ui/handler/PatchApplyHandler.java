package org.sidiff.patching.ui.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.domain.EditingDomain;
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
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.util.access.EMFModelAccessEx;
import org.sidiff.patching.IPatchCorrespondence;
import org.sidiff.patching.ITransformationEngine;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.ui.adapter.ModelAdapter;
import org.sidiff.patching.ui.adapter.ModelChangeHandler;
import org.sidiff.patching.ui.dialog.ChooseModelDialog;
import org.sidiff.patching.ui.view.PatchView;
import org.sidiff.patching.ui.view.ReportView;
import org.sidiff.patching.util.CorrespondenceUtil;
import org.sidiff.patching.util.PatchUtil;
import org.sidiff.patching.util.TransformatorUtil;

public class PatchApplyHandler extends AbstractHandler {
	private Logger LOGGER = Logger.getLogger(PatchApplyHandler.class.getName());

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);

		if (selection.size() == 1) {
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IFile) {
				IFile iFile = (IFile) firstElement;
				if (!iFile.getFileExtension().equals("asymmetric"))
					return -1;

				// Load Model
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource patchResource = resourceSet.getResource(URI.createFileURI(iFile.getLocation().toOSString()), true);

				if (patchResource.getContents().size() == 0) {
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Error in patch model", "There is something wrong with this patch!");
					return null;
				}
				EObject root = patchResource.getContents().get(0);
				if (root instanceof AsymmetricDifference) {
					final AsymmetricDifference difference = (AsymmetricDifference) root;
					ChooseModelDialog dialog = new ChooseModelDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
					if (dialog.open() == Window.OK) {
						String filename = dialog.getFile();
						final URI fileURI = URI.createFileURI(filename);
						final float minReliability = dialog.getReliability();
						final File fileToOpen;
						URI ecoreDiag = fileURI.trimFileExtension().appendFileExtension("ecorediag");
						File file = new File(ecoreDiag.toFileString());
						if (file.exists()) {
							fileToOpen = file;
						} else {
							fileToOpen = new File(fileURI.toFileString());
						}

						Job job = new Job("Loading Patch") {

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
											} catch (PartInitException e) {
												e.printStackTrace();
											}
										}
									});
									monitor.worked(10);
									
									// Copy resource for preview
									Resource resource = resourceResult.get();
									URI copyUri = PatchUtil.createURI(resource.getURI(), "patched");
									final Resource resourceToOpen = PatchUtil.copyWithId(resource, copyUri, true, new Copier());
									resourceToOpen.save(Collections.EMPTY_MAP);

									// Open preview editor
									monitor.subTask("Opening preview editor");
									final AtomicReference<Resource> previewResourceResult = new AtomicReference<Resource>();
									final AtomicReference<EditingDomain> previewEditingDomainResult = new AtomicReference<EditingDomain>();
									Display.getDefault().syncExec(new Runnable() {
										@Override
										public void run() {
											try {

												String file = fileToOpen.getParent() + "/" + resourceToOpen.getURI().lastSegment();
												IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
												IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(file));
												IEditorPart editorPart = IDE.openEditorOnFileStore(page, fileStore);
												Resource previewResource = null;
												EditingDomain editingDomain = null;
												if (editorPart instanceof IEditingDomainProvider) {
													IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
													editingDomain = editor.getEditingDomain();
													previewResource = editor.getEditingDomain().getResourceSet().getResources().get(0);
												}
												previewResourceResult.set(previewResource);
												previewEditingDomainResult.set(editingDomain);
											} catch (PartInitException e) {
												e.printStackTrace();
											}
										}
									});
									monitor.worked(10);

									// Find patch correspondence
									String documentType = null;
									if (EMFModelAccessEx.isProfiled(resource)){
										documentType = EMFModelAccessEx.getBaseDocumentType(resource);
									} else {
										documentType = EMFModelAccessEx.getCharacteristicDocumentType(resource);
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
									final PatchEngine patchEngine = new PatchEngine(difference, resource, correspondence, transformationEngine);
									patchEngine.setPreviewResources(previewEditingDomainResult.get(), previewResourceResult.get());
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
									ModelAdapter adapter = new ModelAdapter(resource);
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
