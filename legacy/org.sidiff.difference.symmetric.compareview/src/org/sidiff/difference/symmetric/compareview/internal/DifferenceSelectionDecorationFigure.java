package org.sidiff.difference.symmetric.compareview.internal;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

public class DifferenceSelectionDecorationFigure extends Figure {
	
	private static final Color NICE_RED = new Color(null, 232, 72, 72);

	public DifferenceSelectionDecorationFigure(){
		setLayoutManager(new ToolbarLayout());
		setOpaque(false);
	}
	
	
	@Override
	public void paint(Graphics graphics) {
		graphics.setForegroundColor(NICE_RED);
		graphics.setLineWidth(4);
		graphics.drawRectangle(getClientArea());
		super.paint(graphics);
	}

}
