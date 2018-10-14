package org.sidiff.integration.editor.highlighting.internal.gmf;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.sidiff.integration.editor.highlighting.StyledObject;

public class SelectionNodeDecorationFigure extends Figure {

	public SelectionNodeDecorationFigure(IFigure originalFigure, StyledObject styledObject) {
		setLayoutManager(new ToolbarLayout());
		setOpaque(false);
		setSize(originalFigure.getSize());
		setForegroundColor(styledObject.getColor());
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		graphics.setLineWidth(4);
		graphics.drawRectangle(getClientArea());
	}
}
