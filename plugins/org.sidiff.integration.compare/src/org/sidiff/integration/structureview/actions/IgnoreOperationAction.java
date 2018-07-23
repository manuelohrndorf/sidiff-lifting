package org.sidiff.integration.structureview.actions;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.sidiff.integration.Activator;
import org.sidiff.integration.SiLiftCompareConfiguration;
import org.sidiff.patching.operation.OperationInvocationStatus;

/**
 * 
 * @author Robert Müller
 *
 */
public class IgnoreOperationAction extends AbstractOperationWrapperAction {

	public IgnoreOperationAction(ISelectionProvider selectionProvider, SiLiftCompareConfiguration config) {
		super("Ignore operation", Activator.getImageDescriptor(Activator.IMAGE_IGNORED), selectionProvider, config);
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
