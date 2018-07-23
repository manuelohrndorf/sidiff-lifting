package org.sidiff.integration.editor.highlighting.adapter.difference;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.integration.editor.highlighting.ISelectionHighlightingAdapter;
import org.sidiff.patching.operation.OperationInvocationWrapper;

/**
 * 
 * @author Robert Müller
 *
 */
public class DifferenceSelectionHighlightingAdapter implements ISelectionHighlightingAdapter {

	@Override
	public Iterator<? extends EObject> getElements(ISelection selection) {
		Set<EObject> result = new HashSet<EObject>();

		Object selectedObject = ISelectionHighlightingAdapter.getFirstElement(selection);
		if(selectedObject instanceof OperationInvocationWrapper) {
			collectOperationInvocationElements(((OperationInvocationWrapper)selectedObject).getOperationInvocation(), result);
		} else if(selectedObject instanceof OperationInvocation) {
			collectOperationInvocationElements((OperationInvocation)selectedObject, result);
		} else if(selectedObject instanceof SemanticChangeSet) {
			collectSemanticChangeSetElements((SemanticChangeSet)selectedObject, result);
		} else if(selectedObject instanceof Change) {
			collectChangeElements((Change)selectedObject, result);
		} else if(selectedObject instanceof ParameterBinding) {
			collectParameterBindingElements((ParameterBinding)selectedObject, result);
		}

		// A null-element might have been added before and
		// is now removed from the set. Removing it at the
		// end makes the other functions more readable.
		result.remove(null);

		return result.iterator();
	}

	private void collectOperationInvocationElements(OperationInvocation operation, Set<EObject> result) {
		collectSemanticChangeSetElements(operation.getChangeSet(), result);
		for(ParameterBinding binding : operation.getParameterBindings()) {
			collectParameterBindingElements(binding, result);
		}
	}

	private void collectSemanticChangeSetElements(SemanticChangeSet changeSet, Set<EObject> result) {
		for(Change change : changeSet.getChanges()) {
			collectChangeElements(change, result);
		}
	}

	private void collectChangeElements(Change change, Set<EObject> result) {
		if(change instanceof AddObject) {
			result.add(((AddObject)change).getObj());
		} else if(change instanceof RemoveObject) {
			result.add(((RemoveObject)change).getObj());
		} else if(change instanceof AddReference) {
			AddReference addRef = (AddReference)change;
			result.add(addRef.getSrc());
			result.add(addRef.getTgt());
			result.add(addRef.getType());
		} else if(change instanceof RemoveReference) {
			RemoveReference remRef = (RemoveReference)change;
			result.add(remRef.getSrc());
			result.add(remRef.getTgt());
			result.add(remRef.getType());
		} else if(change instanceof AttributeValueChange) {
			AttributeValueChange attrChange = (AttributeValueChange)change;
			result.add(attrChange.getObjA());
			result.add(attrChange.getObjB());
			result.add(attrChange.getType());
		}
	}

	private void collectParameterBindingElements(ParameterBinding selectedObject, Set<EObject> result) {
		if(selectedObject instanceof ObjectParameterBinding) {
			ObjectParameterBinding objParam = (ObjectParameterBinding)selectedObject;
			result.add(objParam.getActualA());
			result.add(objParam.getActualB());
		} else if(selectedObject instanceof ValueParameterBinding) {
			// there is nothing to highlight for value parameter bindings
		} else if(selectedObject instanceof MultiParameterBinding) {
			for(ParameterBinding binding : ((MultiParameterBinding)selectedObject).getParameterBindings()) {
				collectParameterBindingElements(binding, result);
			}
		}
	}
}
