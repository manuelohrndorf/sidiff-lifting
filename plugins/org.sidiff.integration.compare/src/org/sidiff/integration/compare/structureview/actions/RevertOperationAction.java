package org.sidiff.integration.compare.structureview.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.integration.compare.SiLiftCompareConfiguration;
import org.sidiff.integration.compare.internal.CompareIntegrationPlugin;
import org.sidiff.patching.operation.OperationInvocationStatus;

/**
 * @author rmueller
 */
public class RevertOperationAction extends AbstractOperationWrapperAction {

	public RevertOperationAction(ISelectionProvider selectionProvider, SiLiftCompareConfiguration config) {
		super("Revert operation", CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_REVERT),
				selectionProvider, config);
	}

	@Override
	public void run() {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Reverting operation", IProgressMonitor.UNKNOWN);
				try {
					getConfig().getDifferencer().revertOperation(getSelectedOperation().getOperationInvocation());
				} catch (Exception e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		MessageDialogUtil.showProgressDialog(runnable);
	}

	@Override
	public boolean isVisible() {
		return super.isVisible()
				&& getSelectedOperation().getStatus() == OperationInvocationStatus.PASSED;
	}
}
