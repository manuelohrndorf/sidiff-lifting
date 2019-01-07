package org.sidiff.patching.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.patch.patch.PatchCreator;
import org.sidiff.patching.ui.wizard.ApplyPatchWizard;

public class PatchApplyHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		Object firstElement = selection.getFirstElement();
		if(selection.size() != 1 || !(firstElement instanceof IFile)) {
			return null;
		}
		IFile file = (IFile)firstElement;
		if (!file.getFileExtension().equals(AsymmetricDiffFacade.PATCH_EXTENSION)) {
			return null;					
		}

		Display.getDefault().asyncExec(() -> {
			URI patchURI = URI.createHierarchicalURI("archive", EMFStorage.toPlatformURI(file).toString() + "!",
					null, new String[] { PatchCreator.PATCH_MANIFEST }, null, null);
			Patch patch = SiDiffResourceSet.create().loadEObject(patchURI, Patch.class);
			if (patch == null) {
				MessageDialogUtil.showErrorDialog("Error in patch file", "The selected file does not contain a valid patch.");
				return;
			}
			WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(), new ApplyPatchWizard(patch, file));
			wizardDialog.open();
		});
		return null;
	}
}
