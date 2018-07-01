package org.sidiff.vcmsintegration.structureview.actions;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;

public class UnignoreOperationAction extends AbstractOperationWrapperAction {

	public UnignoreOperationAction(ISelectionProvider selectionProvider, SiLiftCompareConfiguration config) {
		super("Unignore operation", Activator.getImageDescriptor(Activator.IMAGE_UNIGNORE), selectionProvider, config);
	}

	@Override
	public void run() {
		getConfig().getDifferencer().unignoreOperation(getSelectedOperation().getOperationInvocation());
	}

	@Override
	public boolean isVisible() {
		return super.isVisible()
				&& getSelectedOperation().getStatus() == OperationInvocationStatus.IGNORED;
	}
}
