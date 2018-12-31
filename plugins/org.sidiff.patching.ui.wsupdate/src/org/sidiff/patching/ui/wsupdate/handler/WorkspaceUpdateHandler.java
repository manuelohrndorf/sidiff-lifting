package org.sidiff.patching.ui.wsupdate.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;
import org.sidiff.patching.ui.wsupdate.wizard.WorkspaceUpdateWizard;

public class WorkspaceUpdateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		if (selection.size() == 3) {
			Display.getDefault().asyncExec(() -> {
				// Create a new difference
				Object files[] = selection.toArray();
				WSUModels mergeModels = new WSUModels((IFile)files[0], (IFile)files[1], (IFile)files[2]);

				if (mergeModels.haveSameDocumentType()) {
					WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(), new WorkspaceUpdateWizard(mergeModels));
					wizardDialog.open();
				} else {
					MessageDialog.openError(UIUtil.getActiveShell(), "File Input Error",
							"The input files must have the same document type!");
				}
			});
		}
		return null;
	}

}
