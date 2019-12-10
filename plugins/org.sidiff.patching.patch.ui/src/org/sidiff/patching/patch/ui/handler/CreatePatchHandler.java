package org.sidiff.patching.patch.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.patching.patch.ui.wizard.CreatePatchWizard;

public class CreatePatchHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		Display.getDefault().asyncExec(() -> {
			InputModels inputModels =
				InputModels.builder()
					.assertNumModels(2)
					.assertSameDocumentType(true)
					.addModels(selection)
					.build();
			new WizardDialog(UIUtil.getActiveShell(), new CreatePatchWizard(inputModels)).open();
		});
		return null;
	}
}
