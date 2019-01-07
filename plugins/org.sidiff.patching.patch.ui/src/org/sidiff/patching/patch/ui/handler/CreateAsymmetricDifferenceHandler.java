package org.sidiff.patching.patch.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.patch.ui.wizard.CreateAsymmetricDifferenceWizard;

public class CreateAsymmetricDifferenceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object selectedObjects[] = HandlerUtil.getCurrentStructuredSelection(event).toArray();
		if (selectedObjects.length != 2) {
			return null;
		}
		Display.getDefault().asyncExec(() -> {
			InputModels inputModels = new InputModels((IFile)selectedObjects[0], (IFile)selectedObjects[1]);			
			if (inputModels.haveSameDocumentType()) {
				WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(), new CreateAsymmetricDifferenceWizard(inputModels));
				wizardDialog.open();
			} else {
				MessageDialog.openError(UIUtil.getActiveShell(), "File Input Error",
						"The input files must have the same document type!");
			}
		});
		return null;
	}

}
