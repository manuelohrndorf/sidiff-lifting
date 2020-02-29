package org.sidiff.difference.lifting.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.ui.wizard.LiftDifferenceWizard;

public class LiftDifferenceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selectedObjects[] = HandlerUtil.getCurrentStructuredSelection(event).toArray();
		Assert.isLegal(selectedObjects.length == 1);
		Display.getDefault().asyncExec(() -> {
			new WizardDialog(UIUtil.getActiveShell(),
				new LiftDifferenceWizard((IFile)selectedObjects[0])).open();
		});
		return null;
	}
}
