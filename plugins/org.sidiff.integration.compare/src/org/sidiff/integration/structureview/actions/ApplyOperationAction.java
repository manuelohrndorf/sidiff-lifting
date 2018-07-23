package org.sidiff.integration.structureview.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.sidiff.integration.Activator;
import org.sidiff.integration.SiLiftCompareConfiguration;
import org.sidiff.integration.util.MessageDialogUtil;
import org.sidiff.patching.operation.OperationInvocationStatus;

/**
 * 
 * @author Robert Müller
 *
 */
public class ApplyOperationAction extends AbstractOperationWrapperAction {

	public ApplyOperationAction(ISelectionProvider selectionProvider, SiLiftCompareConfiguration config) {
		super("Apply operation", Activator.getImageDescriptor(Activator.IMAGE_APPLY), selectionProvider, config);
	}

	@Override
	public void run() {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Applying operation", IProgressMonitor.UNKNOWN);
				try {
					getConfig().getDifferencer().applyOperation(getSelectedOperation().getOperationInvocation());
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
				&& getSelectedOperation().getStatus() != OperationInvocationStatus.PASSED
				&& getSelectedOperation().getStatus() != OperationInvocationStatus.IGNORED;
	}
}
