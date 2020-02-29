package org.sidiff.patching.ui.wsupdate.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;
import org.sidiff.patching.ui.wsupdate.wizard.WorkspaceUpdateWizard;

public class WorkspaceUpdateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		Display.getDefault().asyncExec(() -> {
			WSUModels mergeModels = WSUModels.wsuBuilder().assertSameDocumentType(true).addModels(selection).build();
			new WizardDialog(UIUtil.getActiveShell(),
				new WorkspaceUpdateWizard(mergeModels)).open();
		});
		return null;
	}
}
