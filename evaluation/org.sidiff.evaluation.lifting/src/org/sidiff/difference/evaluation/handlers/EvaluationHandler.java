package org.sidiff.difference.evaluation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.difference.evaluation.EvaluationRun;

/**
 * Handler to execute an evaluation run from an Eclipse context menu.
 * 
 * @author cpietsch, kehrer
 *
 */
public class EvaluationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;

			if (selection.getFirstElement() instanceof IFolder) {
				IFolder folder = (IFolder) selection.getFirstElement();
				EvaluationRun.run(folder.getLocation().toOSString());
			}
		}
			
		
		return null;
	}
}
