package org.sidiff.patching.ui.view;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.ui.internal.PatchingUiPlugin;

public class OperationLabelProvider extends StyledCellLabelProvider {

	private final Image applied = PatchingUiPlugin.getImageDescriptor("applied.gif").createImage();
	private final Image applicable = PatchingUiPlugin.getImageDescriptor("applicable.gif").createImage();
	private final Image modified = PatchingUiPlugin.getImageDescriptor("warning_16x16.gif").createImage();
	private final Image conflicting = PatchingUiPlugin.getImageDescriptor("conflict.gif").createImage();
	private final Image ignored = PatchingUiPlugin.getImageDescriptor("ignored.gif").createImage();

	@Override
	public void update(ViewerCell cell) {
		OperationInvocationWrapper wrapper = (OperationInvocationWrapper)cell.getElement();

		StyleRange styleRange = new StyleRange();
		Image image = null;
		if (wrapper.getStatus() == OperationInvocationStatus.PASSED) {
			image = applied;
			styleRange.strikeout = true;
			styleRange.foreground = new Color(Display.getDefault(), 0, 200, 0);
		} else if(wrapper.getStatus() == OperationInvocationStatus.IGNORED){
			image = ignored;
			styleRange.strikeout = true;
			styleRange.foreground = new Color(Display.getDefault(), 128, 128, 128);
		} else if (wrapper.getStatus() == OperationInvocationStatus.FAILED) {
			image = conflicting;
		} else if (wrapper.hasUnresolvedInArguments()) {
			image = conflicting;
		} else if (wrapper.hasModifiedInArguments()) {
			image = modified;
		} else {
			image = applicable;
		}
		cell.setImage(image);

		final String text = " " + wrapper.getText() + wrapper.getChangedArguments();
		cell.setText(text);

		styleRange.start = 1;
		styleRange.length = text.length();
		cell.setStyleRanges(new StyleRange[] { styleRange });
	}

	@Override
	public void dispose() {
		applied.dispose();
		applicable.dispose();
		modified.dispose();
		conflicting.dispose();
		ignored.dispose();

		super.dispose();
	}
}
