package org.sidiff.patching.ui.handler;

import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.file.ZipUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.ui.wizard.ApplyAsymmetricDifferenceWizard;
import org.sidiff.patching.ui.wizard.ApplyPatchWizard;

public class PatchApplyHandler extends AbstractHandler {
	public static final String ARCHIVE_URI_PREFIX = "archive:file:///";
	public static final String ARCHIVE_SEPERATOR = "!/";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);

		if (selection.size() == 1) {
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IFile) {
				Display.getDefault().asyncExec(() -> {
					IFile iFile = (IFile) firstElement;
					if (!iFile.getFileExtension().equals(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)
							&& !iFile.getFileExtension().equals(AsymmetricDiffFacade.PATCH_EXTENSION)) {
						return;					
					}
					// Load Patch
					ResourceSet resourceSet = new ResourceSetImpl();
					Resource patchResource = null;
					
					if (iFile.getFileExtension().equals(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)) {
						patchResource = resourceSet.getResource(EMFStorage.pathToUri(iFile.getLocation().toOSString()), true);
					} else {
						String patchPath = iFile.getLocation().toOSString();
						// TODO own util class
						// Search manifest:
						try {
							for (String entry : ZipUtil.getEntries(Paths.get(patchPath))) {
								if (entry.endsWith("MF")) {
									URI uri_patch = URI.createURI(ARCHIVE_URI_PREFIX + patchPath + ARCHIVE_SEPERATOR + entry);
									patchResource = resourceSet.getResource(uri_patch, true);
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					if (patchResource == null || patchResource.getContents().size() == 0) {
						MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Error in patch model",
								"There is something wrong with this patch!");
						return;
					}
					
					if (iFile.getFileExtension().equals(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)) {
						WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(),
								new ApplyAsymmetricDifferenceWizard((AsymmetricDifference) patchResource.getContents().get(0)));
						wizardDialog.open();
					}else{
						
						EObject root = patchResource.getContents().get(0);
						if(root instanceof Patch){
							Patch patch = (Patch) root;
							WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(),
									new ApplyPatchWizard(patch,  (IFile) firstElement));
							wizardDialog.open();
						}
						else {
							MessageDialog.openError(
									UIUtil.getActiveShell(),
									"Patch Model Error","This is no patch model");
						}
					}
				});
			}
		}
		return null;
	}
}
