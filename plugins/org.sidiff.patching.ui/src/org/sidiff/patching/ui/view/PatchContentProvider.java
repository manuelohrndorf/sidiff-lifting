package org.sidiff.patching.ui.view;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.operation.OperationManager;

public class PatchContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object object) {
		return getChildren(object);
	}

	@Override
	public Object[] getChildren(Object object) {
		if (object instanceof OperationManager) {
			OperationManager manager = (OperationManager) object;
			return manager.getOrderedOperationWrappers().toArray();
		} else if (object instanceof OperationInvocationWrapper) {
			OperationInvocationWrapper wrapper = (OperationInvocationWrapper) object;
			return wrapper.getPredecessors().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object object) {
		return getChildren(object).length > 0;
	}
}
