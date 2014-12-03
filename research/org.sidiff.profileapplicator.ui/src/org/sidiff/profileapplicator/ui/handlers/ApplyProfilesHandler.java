package org.sidiff.profileapplicator.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.sidiff.profileapplicator.ui.ApplyProfilesWizard;

public class ApplyProfilesHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(),
				new ApplyProfilesWizard("D:\\Programme"));
		if (wizardDialog.open() == Window.OK) {
			System.out.println("Ok pressed");
		} else {
			System.out.println("Cancel pressed");
		}
		return null;
	}

}
