package org.sidiff.patching.ui.view;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.ParameterMissingException;
import org.sidiff.patching.report.PatchReport;
import org.sidiff.patching.report.PatchReport.Status;
import org.sidiff.patching.report.PatchReport.Type;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.ui.Activator;

public class PatchLabelProvider extends ColumnLabelProvider {
	private Image checked = Activator.getImageDescriptor("16px-checkbox-checked.png").createImage();
	private Image unchecked = Activator.getImageDescriptor("16px-checkbox-unchecked.png").createImage();
	private Image op_in = Activator.getImageDescriptor("ObjectParameterBinding_in.gif").createImage();
	private Image op_in2 = Activator.getImageDescriptor("ObjectParameterBinding_in2.gif").createImage();
	private Image op_out = Activator.getImageDescriptor("ObjectParameterBinding_out.gif").createImage();
	private Image vp = Activator.getImageDescriptor("ValueParameterBinding.gif").createImage();
	private PatchReport report;

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
		if (report != null && element instanceof OperationInvocation) {
			Display display = Activator.getDefault().getWorkbench().getDisplay();
			OperationInvocation operationInvocation = (OperationInvocation) element;
			
			List<ReportEntry> entries = report.getEntries(operationInvocation, Type.VALIDATION);
			Status status = null;
			if (!entries.isEmpty()) {
				status = entries.get(0).getStatus();
				switch(status){
				case FAILED:
					return new Color(display, 200, 0, 0);
				}
			}
			
			entries = report.getEntries(operationInvocation, Type.EXECUTION);
			if (entries.isEmpty()) {
				return super.getForeground(element);
			}
			status = entries.get(0).getStatus();
			switch (status) {

			case SKIPPED:
				return new Color(display, 150, 150, 150);

			case FAILED:
				return new Color(display, 200, 0, 0);
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
		List<ReportEntry> entries = report.getEntries(operationInvocation, Type.EXECUTION);
		if (entries.isEmpty()) {
			return info;
		}
		ReportEntry entry = entries.get(0);
		if (entry.getStatus() == Status.FAILED) {
			Exception exception = entry.getException();
			if (exception instanceof ParameterMissingException) {
				ParameterMissingException parameterMissingException = (ParameterMissingException) exception;
				String[] parameterNames = parameterMissingException.getParameterNames();
				info += "\n\nMissing parameter" + (parameterNames.length > 1 ? "s: " : ": ") + Arrays.toString(parameterNames);
			} else if (exception instanceof OperationNotExecutableException) {
				info += "\n\nTransformator could not apply!";
			} else {
				info += "\n\nError: " + entry.getDescription();
			}
		}
		return info;
	}

	public void setReport(PatchReport report) {
		this.report = report;
	}
}
