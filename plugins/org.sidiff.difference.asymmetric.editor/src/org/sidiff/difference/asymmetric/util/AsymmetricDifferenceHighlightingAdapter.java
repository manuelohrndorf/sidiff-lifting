package org.sidiff.difference.asymmetric.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.compareview.ISelectionAdapter;

public class AsymmetricDifferenceHighlightingAdapter implements ISelectionAdapter {

	private static AsymmetricDifferenceHighlightingAdapter instance;
	
	public static AsymmetricDifferenceHighlightingAdapter getInstance() {
		
		if (instance == null) {
			instance = new AsymmetricDifferenceHighlightingAdapter();
		}
		
		return instance;
	}
	
	@Override
	public Iterator<Change> getChanges(ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			IStructuredSelection _selection = (IStructuredSelection) selection;
			
			if (_selection.getFirstElement() instanceof OperationInvocation) {
				OperationInvocation operationInvocation = (OperationInvocation) _selection.getFirstElement();
				SemanticChangeSet semanticChangeSet = operationInvocation.getChangeSet();
				return semanticChangeSet.getChanges().iterator();
			}
		}
		
		return ISelectionAdapter.EMPTY_CHANGE_ITERATOR;
	}

	@Override
	public Iterator<EObject> getHighlightableElements(ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			IStructuredSelection _selection = (IStructuredSelection) selection;
			
			if (_selection.getFirstElement() instanceof ObjectParameterBinding) {
				ObjectParameterBinding objectParameter = (ObjectParameterBinding) _selection.getFirstElement();
				List<EObject> parameterBinding = new ArrayList<EObject>(2);
				
				if (objectParameter.getActualA() != null) {
					parameterBinding.add(objectParameter.getActualA());
				}
				
				if (objectParameter.getActualB() != null) {
					parameterBinding.add(objectParameter.getActualB());
				}
				
				return parameterBinding.iterator();
			}
		}
		
		return ISelectionAdapter.EMPTY_EOBJECT_ITERATOR;
	}
}
