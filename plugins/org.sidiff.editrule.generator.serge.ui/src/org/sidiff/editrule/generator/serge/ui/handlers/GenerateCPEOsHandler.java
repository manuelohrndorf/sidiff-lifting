package org.sidiff.editrule.generator.serge.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.editrule.generator.serge.ui.wizards.SergeWizard;

public class GenerateCPEOsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get SERGe Config file
		IFile configFile = (IFile)HandlerUtil.getCurrentStructuredSelection(event).getFirstElement();
		if (configFile != null) {
			Display.getDefault().asyncExec(() -> {
				WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(), new SergeWizard(configFile));
				wizardDialog.open();
			});
		}
		return null;
	}
}
