package org.sidiff.serge.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.serge.ui.wizards.SergeWizard;

public class GenerateCPEOsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);

		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;

			// Show a busy indicator while the runnable is executed
			BusyIndicator.showWhile(Display.getDefault(), new Runnable() {

				@Override
				public void run() {
					// Get SERGe Config file
					IFile configFile = (IFile) selection.getFirstElement();

					WizardDialog wizardDialog = new WizardDialog(PlatformUI
							.getWorkbench().getActiveWorkbenchWindow().getShell(),
							new SergeWizard(configFile));

					wizardDialog.open();
				}
			});
		}

		return null;

	}
}
