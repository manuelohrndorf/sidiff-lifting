package org.sidiff.integration.editor.highlighting.adapter.patching;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.ISelection;
import org.sidiff.integration.editor.highlighting.ISelectionHighlightingAdapter;
import org.sidiff.integration.editor.highlighting.StyledObject;
import org.sidiff.integration.editor.highlighting.adapter.editscript.EditscriptSelectionHighlightingAdapter;
import org.sidiff.patching.operation.OperationInvocationWrapper;

/**
 * 
 * @author Robert Müller, cpietsch
 */
public class PatchingSelectionHighlightingAdapter extends EditscriptSelectionHighlightingAdapter {

	@Override
	public Stream<StyledObject> getElements(ISelection selection) {
		Set<StyledObject> result = new HashSet<>();

		Object selectedObject = ISelectionHighlightingAdapter.getFirstElement(selection);

		if (selectedObject instanceof OperationInvocationWrapper) {
			collectOperationInvocationElements(((OperationInvocationWrapper) selectedObject).getOperationInvocation(), result);
		}

		return result.stream();
	}

}
