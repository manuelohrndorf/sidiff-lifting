package org.sidiff.patching.ui.handler;

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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.patching.ui.wizard.ApplyPatchWizard;
import org.sidiff.patching.util.PatchUtil;
import org.silift.common.util.file.ZipUtil;
import org.silift.patching.patch.Patch;

public class PatchApplyHandler extends AbstractHandler {
	public static final String ARCHIVE_URI_PREFIX = "archive:file:///";
	public static final String ARCHIVE_SEPERATOR = "!/";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);

		if (selection.size() == 1) {
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IFile) {
				IFile iFile = (IFile) firstElement;
				if (!iFile.getFileExtension().equals(PatchUtil.PATCH_EXTENSION))
					return -1;

				String patchPath = iFile.getLocation().toOSString();

				//TODO own util class
				// Search asymmetric difference:
				URI uri = null;
				for (String entry : ZipUtil.getEntries(patchPath)) {
					if (entry.endsWith(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)) {
						uri = URI.createURI(ARCHIVE_URI_PREFIX + patchPath + ARCHIVE_SEPERATOR + entry);
					}
				}
				// Load AsymmetricDifference
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource patchResource = resourceSet.getResource(uri, true);

				if (patchResource.getContents().size() == 0) {
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Error in patch model", "There is something wrong with this patch!");
					return null;
				}
				EObject root = patchResource.getContents().get(0);
				if (root instanceof AsymmetricDifference) {
					final AsymmetricDifference difference = (AsymmetricDifference) root;
					final Patch patch = new Patch(difference);
					WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							new ApplyPatchWizard(patch, ((IFile) firstElement).getName()));
					wizardDialog.open();
				}
				else {
					MessageDialog.openError(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Patch Model Error","This is no patch model");
				}
			}
		}
		return null;
	}
}
