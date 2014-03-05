package org.sidiff.patching.ui.view;

import java.util.Arrays;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.ParameterMissingException;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.ui.Activator;

public class OperationLabelProvider extends StyledCellLabelProvider {

	private final Image applied = Activator.getImageDescriptor("applied.gif").createImage();
	private final Image applicable = Activator.getImageDescriptor("applicable.gif").createImage();
	private final Image modified = Activator.getImageDescriptor("warning_16x16.gif").createImage();
	private final Image conflicting = Activator.getImageDescriptor("conflict.gif").createImage();

	@Override
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		Image image = null;
		String text = null;
		Display display = Activator.getDefault().getWorkbench().getDisplay();
		StyleRange styleRange = new StyleRange();
		if (element instanceof OperationInvocationWrapper) {
			OperationInvocationWrapper wrapper = (OperationInvocationWrapper) element;
			text = wrapper.getText();
			text += wrapper.getChangedArguments();
			if (wrapper.getStatus() == OperationInvocationStatus.PASSED) {
				image = applied;
				styleRange.strikeout = true;
				styleRange.foreground = new Color(display, 0, 200, 0);
			} else if (wrapper.getStatus() == OperationInvocationStatus.FAILED) {
				image = conflicting;
			} else if (wrapper.hasUnresolvedInArguments()) {
				image = conflicting;
			} else if (wrapper.hasModifiedInArguments()) {
				image = modified;
			} else {
				image = applicable;
			}
		}

		styleRange.start = 1;
		styleRange.length = text.length() + 1;
		StyleRange[] styleRanges = { styleRange };
		cell.setImage(image);
		cell.setText(" " + text);
		cell.setStyleRanges(styleRanges);
	}

	private String getFormatedChangeSetInfo(OperationInvocationWrapper opWrapper) {
		String info = opWrapper.getText();

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
