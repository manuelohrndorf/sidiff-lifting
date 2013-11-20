package org.sidiff.patching.ui.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;

public class SelectionHandler implements ISelectionProvider, ISelectionChangedListener {
	
	private Collection<ISelectionChangedListener> selectionChangedListeners;
	private TreeViewer treeViewer;
	
	public SelectionHandler(TreeViewer treeViewer) {
		selectionChangedListeners = new ArrayList<ISelectionChangedListener>();
		this.treeViewer = treeViewer;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = getSelection();
		if (selection != null) {
			try{
			for (ISelectionChangedListener listener : selectionChangedListeners) {
				listener.selectionChanged(new SelectionChangedEvent(this, selection));
			}
			}
			catch(ConcurrentModificationException e){
				//TODO 
				//just catched
			}
		}
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	@Override
	public ISelection getSelection() {
		TreeSelection selection = (TreeSelection) treeViewer.getSelection();
		Object element = selection.getFirstElement();
		if (element instanceof OperationInvocation) {
			OperationInvocation invocation = (OperationInvocation) element;
			return new StructuredSelection(invocation.getChangeSet());
		} else if (element instanceof ObjectParameterBinding) {
			ObjectParameterBinding substitution = (ObjectParameterBinding) element;
			EObject eObject = substitution.getActualA();
			if (eObject != null)
				return new StructuredSelection(eObject);
			else
				return null;
		}
		return treeViewer.getSelection();
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		treeViewer.setSelection(selection);
	}

}
