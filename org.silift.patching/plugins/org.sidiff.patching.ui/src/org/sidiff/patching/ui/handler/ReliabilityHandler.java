package org.sidiff.patching.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.patching.ui.view.PatchView;

public class ReliabilityHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			PatchView patchView = (PatchView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(PatchView.ID);
			patchView.toggleReliability(!HandlerUtil.toggleCommandState(event.getCommand()));
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
