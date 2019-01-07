package org.sidiff.patching.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.patching.ui.wizard.ApplyAsymmetricDifferenceWizard;

public class AsymmetricApplyHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		Object firstElement = selection.getFirstElement();
		if(selection.size() != 1 || !(firstElement instanceof IFile)) {
			return null;
		}
		IFile file = (IFile)firstElement;
		if (!file.getFileExtension().equals(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT)) {
			return null;					
		}

		Display.getDefault().asyncExec(() -> {
			WizardDialog wizardDialog = new WizardDialog(UIUtil.getActiveShell(),
					new ApplyAsymmetricDifferenceWizard(file));
			wizardDialog.open();
		});
		return null;
	}
}
