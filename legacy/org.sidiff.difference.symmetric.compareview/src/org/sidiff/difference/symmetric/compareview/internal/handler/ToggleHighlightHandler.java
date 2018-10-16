package org.sidiff.difference.symmetric.compareview.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.sidiff.difference.symmetric.compareview.internal.DifferenceSelectionController;

public class ToggleHighlightHandler  extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		DifferenceSelectionController.getInstance().toggleHighlight();
		return null;
	}

}
