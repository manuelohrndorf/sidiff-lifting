package org.sidiff.integration.editor.highlighting.gmf.internal;

import org.eclipse.draw2d.PolylineConnection;
import org.sidiff.integration.editor.highlighting.StyledObject;

public class SelectionEdgeDecorationFigure extends PolylineConnection {

	public SelectionEdgeDecorationFigure(PolylineConnection originalFigure, StyledObject styledObject) {
		setLayoutManager(originalFigure.getLayoutManager());
		setConnectionRouter(originalFigure.getConnectionRouter());
		setOpaque(false);
		setLineAttributes(originalFigure.getLineAttributes());
		setPoints(originalFigure.getPoints());
		setSourceAnchor(originalFigure.getSourceAnchor());
		setTargetAnchor(originalFigure.getTargetAnchor());
		setRoutingConstraint(originalFigure.getRoutingConstraint());
		setForegroundColor(styledObject.getColor());
		setLineWidth(2);
	}
}
