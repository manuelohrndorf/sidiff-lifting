package org.sidiff.integration.editor.highlighting.adapter.editscript;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.ISelection;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.integration.editor.highlighting.ISelectionHighlightingAdapter;
import org.sidiff.integration.editor.highlighting.StyledObject;
import org.sidiff.integration.editor.highlighting.adapter.difference.DifferenceSelectionHighlightingAdapter;

/**
 * 
 * @author Robert Müller, cpietsch
 */
public class EditscriptSelectionHighlightingAdapter extends DifferenceSelectionHighlightingAdapter {

	@Override
	public Stream<StyledObject> getElements(ISelection selection) {
		Set<StyledObject> result = new HashSet<>();

		Object selectedObject = ISelectionHighlightingAdapter.getFirstElement(selection);

		if (selectedObject instanceof OperationInvocation) {
			collectOperationInvocationElements((OperationInvocation) selectedObject, result);
		} else if (selectedObject instanceof ParameterBinding) {
			collectParameterBindingElements((ParameterBinding) selectedObject, result);
		}

		return result.stream();
	}

	protected void collectOperationInvocationElements(OperationInvocation operation, Set<StyledObject> result) {
		collectSemanticChangeSetElements(operation.getChangeSet(), result);
		for (ParameterBinding binding : operation.getParameterBindings()) {
			collectParameterBindingElements(binding, result);
		}
	}

	protected void collectParameterBindingElements(ParameterBinding selectedObject, Set<StyledObject> result) {
		if (selectedObject instanceof ObjectParameterBinding) {
			ObjectParameterBinding objParam = (ObjectParameterBinding) selectedObject;
			highlight(objParam.getActualA(), CHANGE, result);
			highlight(objParam.getActualB(), CHANGE, result);
		} else if (selectedObject instanceof ValueParameterBinding) {
			// there is nothing to highlight for value parameter bindings
		} else if (selectedObject instanceof MultiParameterBinding) {
			for (ParameterBinding binding : ((MultiParameterBinding) selectedObject).getParameterBindings()) {
				collectParameterBindingElements(binding, result);
			}
		}
	}

}
