package org.sidiff.patching.ui.wsupdate.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;
import org.sidiff.patching.ui.wsupdate.wizard.WorkspaceUpdateWizard;

public class WorkspaceUpdateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;
			if (selection.size() == 3) {
				// Show a busy indicator while the runnable is executed
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
					@Override
					public void run() {
						// Create a new difference
						Object files[] = selection.toArray();
						WSUModels mergeModels = new WSUModels((IFile)files[0], (IFile)files[1], (IFile)files[2]);

						if (mergeModels.haveSameDocumentType()) {
							WizardDialog wizardDialog = new WizardDialog(
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
									new WorkspaceUpdateWizard(mergeModels));
							wizardDialog.open();
						} else {
							MessageDialog.openError(
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
									"File Input Error", "The input files must have the same document type!");
						}
					}
				});
			}
		}
		return null;
	}

}
