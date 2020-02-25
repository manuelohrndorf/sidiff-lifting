package org.sidiff.integration.compare.structureview.actions;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.sidiff.integration.compare.SiLiftCompareConfiguration;
import org.sidiff.integration.compare.internal.CompareIntegrationPlugin;
import org.sidiff.patching.operation.OperationInvocationStatus;

/**
 * @author rmueller
 */
public class IgnoreOperationAction extends AbstractOperationWrapperAction {

	public IgnoreOperationAction(ISelectionProvider selectionProvider, SiLiftCompareConfiguration config) {
		super("Ignore operation", CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_IGNORED),
				selectionProvider, config);
	}

	@Override
	public void run() {
		getConfig().getDifferencer().ignoreOperation(getSelectedOperation().getOperationInvocation());
	}

	@Override
	public boolean isVisible() {
		return super.isVisible()
				&& getSelectedOperation().getStatus() != OperationInvocationStatus.PASSED
				&& getSelectedOperation().getStatus() != OperationInvocationStatus.IGNORED;
	}
}
