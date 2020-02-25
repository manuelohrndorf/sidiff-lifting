package org.sidiff.integration.compare.structureview.actions;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.sidiff.integration.compare.SiLiftCompareConfiguration;
import org.sidiff.integration.compare.internal.CompareIntegrationPlugin;
import org.sidiff.patching.operation.OperationInvocationStatus;

/**
 * @author rmueller
 */
public class UnignoreOperationAction extends AbstractOperationWrapperAction {

	public UnignoreOperationAction(ISelectionProvider selectionProvider, SiLiftCompareConfiguration config) {
		super("Unignore operation", CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_UNIGNORE),
				selectionProvider, config);
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
