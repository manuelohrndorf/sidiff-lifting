
package org.sidiff.difference.symmetric.compareview.ide.contentviewer;

import java.util.ResourceBundle;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class TestContentViewer extends ContentMergeViewer {
	
	Composite parent;
	
	Label testLabel;
	
	public TestContentViewer(Composite parent, int style, ResourceBundle bundle, CompareConfiguration cc) {
		super(style, bundle, cc);
		this.parent = parent;
	}
	
	@Override
	protected void createControls(Composite composite) {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void handleResizeAncestor(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void handleResizeLeftRight(int x, int y, int leftWidth, int centerWidth, int rightWidth, int height) {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void updateContent(Object ancestor, Object left, Object right) {
		System.out.println("ViewerTest.updateContent()");
		System.out.println(ancestor);
		System.out.println(left);
		System.out.println(right);
		
		// TEST:
		testLabel.setText("Hallo " + left + "!");
	}
	
	@Override
	protected void copy(boolean leftToRight) {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected byte[] getContents(boolean left) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Control getControl() {
		// TODO Auto-generated method stub
		if (testLabel == null) {
			testLabel = new Label(parent, SWT.FILL);
			testLabel.setText("Hallo?");
		}
		
		return testLabel;
	}
}
