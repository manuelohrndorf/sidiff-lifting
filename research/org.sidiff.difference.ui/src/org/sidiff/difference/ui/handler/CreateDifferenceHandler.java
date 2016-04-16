package org.sidiff.difference.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.ui.wizard.CreateDifferenceWizard;

public class CreateDifferenceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		WizardDialog wizardDialog = new WizardDialog(PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getShell(),
				new CreateDifferenceWizard());

		wizardDialog.open();
//		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
//
//		if (currentSelection instanceof IStructuredSelection) {
//			final IStructuredSelection selection = (IStructuredSelection) currentSelection;
//
//			if (selection.size() == 2) {
//				// Show a busy indicator while the runnable is executed
//				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
//
//					@Override
//					public void run() {
//						IFile fileA = (IFile) selection.toArray()[0];
//						IFile fileB = (IFile) selection.toArray()[1];
//						//TODO Wizard öffnen
//						
//					}
//				});
//			}
//		}

		return null;
	}
}
