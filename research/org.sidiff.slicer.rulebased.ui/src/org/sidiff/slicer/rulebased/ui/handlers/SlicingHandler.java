package org.sidiff.slicer.rulebased.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.slicer.rulebased.ui.views.SlicingCriteriaView;
import org.sidiff.vcmsintegration.preferences.exceptions.InvalidSettingsException;
import org.sidiff.vcmsintegration.preferences.exceptions.UnsupportedFeatureLevelException;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SlicingHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;

			if (selection.size() == 1) {
				// Show a busy indicator while the runnable is executed
				BusyIndicator.showWhile(Display.getDefault(), new Runnable() {

					@Override
					public void run() {
						// Lift a technical difference:
						IFile file = (IFile) selection.getFirstElement();
						SlicingCriteriaView view;
						try {
							view = (SlicingCriteriaView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(SlicingCriteriaView.ID);
							view.init(file);
						} catch (PartInitException | InvalidSettingsException | UnsupportedFeatureLevelException e) {
							MessageDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Error", e.getMessage());
						}
						
					}
				});
			}
		}
		return null;
	}
}
