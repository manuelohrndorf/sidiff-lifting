package org.sidiff.patching.ui.view;

import java.util.Arrays;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.OperationInvocationStatus;
import org.sidiff.patching.OperationInvocationStatusManager;
import org.sidiff.patching.OperationInvocationWrapper;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.ParameterMissingException;
import org.sidiff.patching.ui.Activator;

public class PatchLabelProvider extends ColumnLabelProvider {

	private OperationInvocationStatusManager statusManager;

	private final Image checked = Activator.getImageDescriptor("16px-checkbox-checked.png").createImage();
	private final Image unchecked = Activator.getImageDescriptor("16px-checkbox-unchecked.png").createImage();
	private final Image op_in = Activator.getImageDescriptor("ObjectParameterBinding_in.gif").createImage();
	private final Image op_in2 = Activator.getImageDescriptor("ObjectParameterBinding_in2.gif").createImage();
	private final Image op_out = Activator.getImageDescriptor("ObjectParameterBinding_out.gif").createImage();
	private final Image vp = Activator.getImageDescriptor("ValueParameterBinding.gif").createImage();

	public void init(OperationInvocationStatusManager statusManager) {
		this.statusManager = statusManager;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof OperationInvocation) {
			OperationInvocation invocation = (OperationInvocation) element;
			if (invocation.isApply()) {
				return checked;
			} else {
				return unchecked;
			}
		} else if (element instanceof ObjectParameterBinding) {
			ObjectParameterBinding parameterBinding = (ObjectParameterBinding) element;
			if (parameterBinding.getFormalParameter().getDirection() == ParameterDirection.IN) {
				if (parameterBinding.getActualA() == null) {
					return op_in2;
				} else {
					return op_in;
				}
			} else {
				return op_out;
			}
		} else if (element instanceof ValueParameterBinding) {
			return vp;
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof OperationInvocation) {
			OperationInvocation operationInvocation = (OperationInvocation) element;
			return operationInvocation.getChangeSet().getName();
		} else if (element instanceof ParameterBinding) {
			ParameterBinding substitution = (ParameterBinding) element;
			return substitution.getFormalName();
		}
		return element.toString();
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof OperationInvocation) {
			Display display = Activator.getDefault().getWorkbench().getDisplay();
			OperationInvocation operationInvocation = (OperationInvocation) element;
			OperationInvocationWrapper opWrapper = statusManager.getStatusWrapper(operationInvocation);

			if (opWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				return new Color(display, 0, 200, 0);
			} else if (opWrapper.getStatus() == OperationInvocationStatus.FAILED) {
				return new Color(display, 200, 0, 0);
			} else {
				return super.getForeground(element);
			}
		}
		return super.getForeground(element);
	}

	@Override
	public String getToolTipText(Object element) {
		if (element instanceof OperationInvocation) {
			OperationInvocation invocation = (OperationInvocation) element;
			return getFormatedChangeSetInfo(invocation);
		}
		return null;
	}

	private String getFormatedChangeSetInfo(OperationInvocation operationInvocation) {
		String info = operationInvocation.getChangeSet().getName();
		OperationInvocationWrapper opWrapper = statusManager.getStatusWrapper(operationInvocation);

		if (opWrapper.getStatus() == OperationInvocationStatus.FAILED) {
			Exception exception = opWrapper.getExecutionError();
			if (exception instanceof ParameterMissingException) {
				ParameterMissingException parameterMissingException = (ParameterMissingException) exception;
				String[] parameterNames = parameterMissingException.getParameterNames();
				info += "\n\nMissing parameter" + (parameterNames.length > 1 ? "s: " : ": ")
						+ Arrays.toString(parameterNames);
			} else if (exception instanceof OperationNotExecutableException) {
				info += "\n\nTransformator could not apply!";
			}
		}

		return info;
	}

}
