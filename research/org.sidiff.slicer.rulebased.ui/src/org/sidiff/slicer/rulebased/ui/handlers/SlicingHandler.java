package org.sidiff.slicer.rulebased.ui.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;
import org.sidiff.slicer.rulebased.ui.RuleBasedSlicerUI;
import org.sidiff.slicer.rulebased.ui.views.SlicingCriteriaView;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SlicingHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object selection = HandlerUtil.getCurrentStructuredSelection(event).getFirstElement();
		Assert.isTrue(selection instanceof IFile);
		final IFile file = (IFile)selection;
		
		// Show a busy indicator while the runnable is executed
		BusyIndicator.showWhile(Display.getDefault(), () -> {
			try {
				SlicingCriteriaView view = (SlicingCriteriaView)
						HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(SlicingCriteriaView.ID);
				view.init(file);
			} catch (PartInitException | IOException e) {
				StatusManager.getManager().handle(
						new Status(IStatus.ERROR, RuleBasedSlicerUI.PLUGIN_ID, "Showing slicing criteria view failed", e),
						StatusManager.LOG|StatusManager.SHOW);
			}
		});
		return null;
	}
}
