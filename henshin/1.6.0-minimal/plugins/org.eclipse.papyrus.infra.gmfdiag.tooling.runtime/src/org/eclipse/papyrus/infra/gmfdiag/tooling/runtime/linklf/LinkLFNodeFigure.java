/*****************************************************************************
 * Copyright (c) 2014-15 CEA LIST, Montages AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Golubev (Montages) - Initial API and implementation
 *   
 *****************************************************************************/
package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf;

import org.eclipse.draw2d.AbstractPointListShape;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ScalablePolygonShape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.SlidableAnchor;

/**
 * Extends {@link DefaultSizeNodeFigure} with ability to create custom
 * {@link SlidableAnchor} which are snapped to grid.
 * <p/>
 * 
 * @since 3.3
 */
public class LinkLFNodeFigure extends DefaultSizeNodeFigure {

	protected static final double AVOID_DEFAULT_ANCHOR_AREA = 1.0;

	private final GraphicalEditPart myHost;

	public LinkLFNodeFigure(GraphicalEditPart hostEP, int width, int height) {
		super(width, height);
		myHost = hostEP;
	}

	@Override
	protected ConnectionAnchor createAnchor(PrecisionPoint p) {
		if (p == null) {
			// If the old terminal for the connection anchor cannot be resolved
			// (by SlidableAnchor) a null
			// PrecisionPoint will passed in - this is handled here
			return createDefaultAnchor();
		}
		SlidableSnapToGridAnchor result = new SlidableSnapToGridAnchor(this, p);
		result.setEditPartViewer(myHost.getViewer());
		return result;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
		return super.getSourceConnectionAnchorAt(p);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
		return super.getTargetConnectionAnchorAt(p);
	}

	@Override
	protected double getSlidableAnchorArea() {
		return AVOID_DEFAULT_ANCHOR_AREA;
	}

	protected ConnectionAnchor createConnectionAnchor(Point p) {
		if (p == null) {
			return getConnectionAnchor(szAnchor);
		} else {
			Point temp = p.getCopy();
			translateToRelative(temp);
			PrecisionPoint pt = BaseSlidableAnchor.getAnchorRelativeLocation(
					temp, getBounds());
			if (isDefaultAnchorArea(pt)) {
				return getConnectionAnchor(szAnchor);
			}

			forceSideForBorderItemAnchorLocation(myHost, pt);

			return createAnchor(pt);
		}
	}

	@Override
	public PointList getPolygonPoints() {
		if (getChildren().size() == 1) {
			IFigure primaryShape = (IFigure) getChildren().get(0);
			if (primaryShape instanceof ScalablePolygonShape) {
				ScalablePolygonShape scalable = (ScalablePolygonShape) primaryShape;
				PointList result = scalable.getScaledPoints().getCopy();
				result.translate(scalable.getLocation());
				return result;
			}
			if (primaryShape instanceof AbstractPointListShape) {
				return ((AbstractPointListShape) primaryShape).getPoints()
						.getCopy();
			}
		}
		return super.getPolygonPoints();
	}

	/**
	 * Utility method which, in case if the host editpart is
	 * {@link IBorderItemEditPart} resets the anchor location to the side
	 * parallel to the parent side this affixed item is attached to.
	 */
	public static void forceSideForBorderItemAnchorLocation(EditPart hostEP,
			PrecisionPoint pt) {
		if (hostEP instanceof IBorderItemEditPart) {
			IBorderItemLocator locator = ((IBorderItemEditPart) hostEP)
					.getBorderItemLocator();
			switch (locator.getCurrentSideOfParent()) {
			case PositionConstants.WEST:
			case PositionConstants.EAST:
				pt.setPreciseX(pt.preciseX() > 0.5 ? 1.0 : 0.0);
				break;
			case PositionConstants.SOUTH:
			case PositionConstants.NORTH:
				pt.setPreciseY(pt.preciseY() > 0.5 ? 1.0 : 0.0);
				break;
			}
		}
	}

}
