package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.draw2d.labels;

import org.eclipse.gmf.runtime.diagram.ui.label.ILabelDelegate;

/**
 * Very limited set of ILabelDelegate methods is actually supported.
 */
public class VerticalLabelDelegate extends ILabelDelegate.Stub {

	private final VerticalLabel myLabel;

	public VerticalLabelDelegate(VerticalLabel label) {
		super();
		myLabel = label;
	}

	@Override
	public String getText() {
		return myLabel.getText();
	}

	@Override
	public void setText(String text) {
		myLabel.setText(text);
	}
}
