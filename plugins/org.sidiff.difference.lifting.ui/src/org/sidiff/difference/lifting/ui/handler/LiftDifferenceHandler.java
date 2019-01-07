package org.sidiff.difference.lifting.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.ui.wizard.LiftDifferenceWizard;

public class LiftDifferenceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selectedObjects[] = HandlerUtil.getCurrentStructuredSelection(event).toArray();
		if(selectedObjects.length != 1) {
			return null;
		}
		Display.getDefault().asyncExec(() -> {
			// Lift a technical difference:
			WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(), new LiftDifferenceWizard((IFile)selectedObjects[0]));
			wizardDialog.open();
		});
		return null;
	}
}
