package org.sidiff.patching.ui.view.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;

public class ExecutedOperationsFilter extends ViewerFilter {
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof OperationInvocationWrapper) {
			OperationInvocationWrapper operationWrapper = (OperationInvocationWrapper) element;
			return operationWrapper.getStatus()!=OperationInvocationStatus.PASSED;

		}
		return true;
	}

}
