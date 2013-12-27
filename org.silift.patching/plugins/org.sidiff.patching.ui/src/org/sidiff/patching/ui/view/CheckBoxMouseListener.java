package org.sidiff.patching.ui.view;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.ui.Activator;

public class CheckBoxMouseListener implements MouseListener {
	
	public interface ICheckBoxListener {
		public void itemChecked(OperationInvocation invocation, boolean checked);

		public Tree getTree();
	}

	private Image checked = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/16px-checkbox-checked.png").createImage();
	private Image unchecked = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/16px-checkbox-unchecked.png").createImage();
	private ICheckBoxListener listener;

	public CheckBoxMouseListener(ICheckBoxListener listener) {
		this.listener = listener;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {

	}

	@Override
	public void mouseDown(MouseEvent e) {
		for (TreeItem item : listener.getTree().getSelection()) {
			if (item.getImage() != null) {
				if ((e.x > item.getImageBounds(0).x)
						&& (e.x < (item.getImageBounds(0).x + item.getImage().getBounds().width))) {
					if ((e.y > item.getImageBounds(0).y)
							&& (e.y < (item.getImageBounds(0).y + item.getImage().getBounds().height))) {
						setChecked(item);
					}
				}
			}
		}

	}

	@Override
	public void mouseUp(MouseEvent e) {

	}

	private void setChecked(TreeItem item) {
		if (item.getData() instanceof OperationInvocation) {
			OperationInvocation invocation = (OperationInvocation) item.getData();
			if (invocation.isApply()) {
				listener.itemChecked(invocation, false);
				item.setImage(unchecked);
			} else {
				listener.itemChecked(invocation, true);
				item.setImage(checked);
			}
		}
	}

}
