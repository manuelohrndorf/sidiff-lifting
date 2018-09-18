package org.sidiff.integration.structureview.actions;

import org.eclipse.emf.common.ui.action.ViewerFilterAction;
import org.eclipse.jface.viewers.Viewer;
import org.sidiff.integration.Activator;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;

/**
 * 
 * @author Robert Müller
 *
 */
public class OperationInvocationViewerFilterAction extends ViewerFilterAction {

	public OperationInvocationViewerFilterAction() {
		super("Hide Successful Operations", AS_CHECK_BOX);
		setToolTipText("Hide all succesfully executed, or ignored operations");
		setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_FILTER));
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(isChecked() && element instanceof OperationInvocationWrapper) {
			OperationInvocationWrapper operationWrapper = (OperationInvocationWrapper)element;
			return operationWrapper.getStatus() != OperationInvocationStatus.PASSED 
					&& operationWrapper.getStatus() != OperationInvocationStatus.IGNORED;
		}
		return true;
	}

}
