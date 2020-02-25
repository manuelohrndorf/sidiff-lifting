package org.sidiff.integration.compare.structureview.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.sidiff.integration.compare.SiLiftCompareConfiguration;
import org.sidiff.patching.operation.OperationInvocationWrapper;

/**
 * @author rmueller
 */
public abstract class AbstractOperationWrapperAction extends AbstractAction {

	private OperationInvocationWrapper selectedOperation;

	public AbstractOperationWrapperAction(String text, ImageDescriptor image,
			ISelectionProvider selectionProvider, SiLiftCompareConfiguration config) {
		super(text, image, selectionProvider, config);
	}

	@Override
	public void handleSelectionChanged(Object object) {
		if(object instanceof OperationInvocationWrapper) {
			selectedOperation = (OperationInvocationWrapper)object;
		} else {
			selectedOperation = null;
		}
	}

	@Override
	public boolean isVisible() {
		return selectedOperation != null;
	}

	protected OperationInvocationWrapper getSelectedOperation() {
		return selectedOperation;
	}
}
