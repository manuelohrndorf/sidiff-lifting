package org.sidiff.integration.compare.structureview.actions;

import org.eclipse.emf.common.ui.action.ViewerFilterAction;
import org.eclipse.jface.viewers.Viewer;
import org.sidiff.integration.compare.internal.CompareIntegrationPlugin;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;

/**
 * @author rmueller
 */
public class OperationInvocationViewerFilterAction extends ViewerFilterAction {

	public OperationInvocationViewerFilterAction() {
		super("Hide Successful Operations", AS_CHECK_BOX);
		setToolTipText("Hide all succesfully executed, or ignored operations");
		setImageDescriptor(CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_FILTER));
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
