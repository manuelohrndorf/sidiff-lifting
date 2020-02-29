package org.sidiff.patching.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
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
		Assert.isTrue(selection.size() == 1);
		IFile file = (IFile)selection.getFirstElement();
		Assert.isTrue(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT.equals(file.getFileExtension()));

		Display.getDefault().asyncExec(() -> {
			new WizardDialog(UIUtil.getActiveShell(),
					new ApplyAsymmetricDifferenceWizard(file)).open();
		});
		return null;
	}
}
