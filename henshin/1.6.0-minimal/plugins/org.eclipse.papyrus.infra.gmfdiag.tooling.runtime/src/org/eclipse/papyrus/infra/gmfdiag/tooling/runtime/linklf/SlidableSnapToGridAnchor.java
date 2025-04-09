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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.SlidableAnchor;

/**
 * Extends {@link SlidableAnchor} with ability to snap to the intersection
 * points between the host anchorable bounds and the active diagram grid.
 * 
 * @since 3.3
 */
public class SlidableSnapToGridAnchor extends SlidableAnchor {

	private EditPartViewer myGridProvider;

	public SlidableSnapToGridAnchor(NodeFigure f, PrecisionPoint p) {
		super(f, p);
	}

	public void setEditPartViewer(EditPartViewer viewer) {
		myGridProvider = viewer;
	}

	@Override
	public Point getOrthogonalLocation(Point orthoReference) {
		Point result = getOrthogonalLocationNoSnap(orthoReference);
		if (result != null) {
			Rectangle gridSpecAbs = getAbsoluteGridSpec();
			LinkedList<Point> onVerticalGrid = new LinkedList<Point>();
			LinkedList<Point> onHorizontalGrid = new LinkedList<Point>();
			computeAnchorablePointsOnGrid(gridSpecAbs, onVerticalGrid,
					onHorizontalGrid);

			if (!onHorizontalGrid.isEmpty() || !onVerticalGrid.isEmpty()) {
				return pickClosestPointToSet(result, onHorizontalGrid,
						onVerticalGrid);
			}
		}
		return result;
	}

	private boolean myGetLocationReentryLock = false;

	@Override
	public Point getLocation(Point reference) {
		Rectangle gridSpecAbs = getAbsoluteGridSpec();
		if (gridSpecAbs != null && !myGetLocationReentryLock) {
			myGetLocationReentryLock = true;
			try {
				List<Point> onVerticalGrid = new LinkedList<Point>();
				List<Point> onHorizontalGrid = new LinkedList<Point>();

				computeAnchorablePointsOnGrid(gridSpecAbs, onVerticalGrid,
						onHorizontalGrid);

				if (!onVerticalGrid.isEmpty() || !onVerticalGrid.isEmpty()) {
					return pickClosestPointToSet(getReferencePoint(),
							onHorizontalGrid, onVerticalGrid);
				}
			} finally {
				myGetLocationReentryLock = false;
			}
		}

		Point result = super.getLocation(reference);
		return result;
	}

	/**
	 * @see bug #451604, we don't want anchor to move even if the resulting line
	 *      is close to straight
	 *      <p/>
	 *      So we will block the normalization for instances of this class.
	 */
	@Override
	protected Point normalizeToStraightlineTolerance(Point foreignReference,
			Point ownReference, int tolerance) {
		final int NO_TOLERANCE = 0;
		return super.normalizeToStraightlineTolerance(foreignReference,
				ownReference, NO_TOLERANCE);
	}

	@Override
	public String toString() {
		return "SSTGA:" + "terminal: " + getTerminal() + ", terminalLoc: "
				+ getReferencePoint() + ", box: " + getBox();
	}

	/**
	 * Returns the position of the closest edge of the rectangle closest to the
	 * point
	 * 
	 * @param p
	 *            the point
	 * @param r
	 *            the rectangle
	 * @return position of the closest edge
	 * @deprecated copy-pasted from super class, [GMFRT] make protected
	 */
	@Deprecated
	public static int getClosestSide2(Point p, Rectangle r) {
		double diff = Math.abs(r.preciseX() + r.preciseWidth() - p.preciseX());
		int side = PositionConstants.RIGHT;
		double currentDiff = Math.abs(r.preciseX() - p.preciseX());
		if (currentDiff < diff) {
			diff = currentDiff;
			side = PositionConstants.LEFT;
		}
		currentDiff = Math.abs(r.preciseY() + r.preciseHeight() - p.preciseY());
		if (currentDiff < diff) {
			diff = currentDiff;
			side = PositionConstants.BOTTOM;
		}
		currentDiff = Math.abs(r.preciseY() - p.preciseY());
		if (currentDiff < diff) {
			diff = currentDiff;
			side = PositionConstants.TOP;
		}
		return side;
	}

	/**
	 * If grid provider had been set up and has grid enabled then returns active
	 * grid specification in absolute coordinates. Otherwise returns null.
	 * 
	 * @return <code>null</code> if no active grid or grid provider had not been
	 *         set up.
	 */
	protected Rectangle getAbsoluteGridSpec() {
		return myGridProvider == null ? null : DiagramGridSpec
				.getAbsoluteGridSpec(myGridProvider);
	}

	protected static Point pickClosestPointToSet(Point source,
			Collection<? extends Point> set1, Collection<? extends Point> set2) {
		double bestDistSquared = Double.MAX_VALUE;
		Point result = null;
		for (Point next : set1) {
			double nextDistSquared = source.getDistance(next);
			if (nextDistSquared < bestDistSquared) {
				result = next;
				bestDistSquared = nextDistSquared;
			}
		}
		for (Point next : set2) {
			double nextDistSquared = source.getDistance(next);
			if (nextDistSquared < bestDistSquared) {
				result = next;
				bestDistSquared = nextDistSquared;
			}
		}
		return result;
	}

	/**
	 * Computes possible anchorable points on grid for given gridSpec. Results
	 * are pushed into the given lists, separately for points computed for
	 * vertical and horizontal grid.
	 * <p/>
	 * As results of this method depends only on the owner location and the
	 * gridspec, they may be cached to improve performance.
	 * 
	 * @param gridSpecAbs
	 *            absolute grid specification, if <code>null</code> then method
	 *            does nothing
	 * @param onVerticalGridLocs
	 *            output list to store points of vertical grid
	 * @param onHorizontalGridLocs
	 *            output list to store points on horizontal grid
	 */
	protected void computeAnchorablePointsOnGrid(Rectangle gridSpecAbs,
			List<Point> onVerticalGridLocs, List<Point> onHorizontalGridLocs) {
		if (gridSpecAbs == null) {
			return;
		}
		double gridX = gridSpecAbs.preciseWidth();
		double gridY = gridSpecAbs.preciseWidth();
		Point gridOrigin = gridSpecAbs.getLocation();

		PrecisionRectangle bounds = new PrecisionRectangle(
				FigureUtilities.getAnchorableFigureBounds(getOwner()));
		getOwner().translateToAbsolute(bounds);

		Point notOnGrid = bounds.getCenter();

		PrecisionPoint fakeRefAbove = new PrecisionPoint(notOnGrid);
		PrecisionPoint fakeRefBelow = new PrecisionPoint(notOnGrid);
		fakeRefAbove
				.setPreciseY(bounds.preciseY() - bounds.preciseHeight() / 2);
		fakeRefBelow.setPreciseY(bounds.preciseY() + bounds.preciseHeight() / 2
				+ bounds.preciseHeight());

		double reminderX = Math.IEEEremainder(
				notOnGrid.preciseX() - gridOrigin.preciseX(), gridX);
		if (reminderX < 0) {
			reminderX += gridX;
		}
		for (double nextX = notOnGrid.preciseX() - reminderX; nextX >= bounds
				.preciseX(); nextX -= gridX) {
			fakeRefAbove.setPreciseX(nextX);
			fakeRefBelow.setPreciseX(nextX);
			Point onGridForAboveRef = getOrthogonalLocationNoSnap(fakeRefAbove);
			Point onGridForBelowRef = getOrthogonalLocationNoSnap(fakeRefBelow);
			onVerticalGridLocs.add(onGridForAboveRef);
			if (!onGridForBelowRef.equals(onGridForAboveRef)) {
				onVerticalGridLocs.add(onGridForBelowRef);
			}
		}
		for (double nextX = gridX + notOnGrid.preciseX() - reminderX; nextX <= bounds
				.preciseX() + bounds.preciseWidth(); nextX += gridX) {
			fakeRefAbove.setPreciseX(nextX);
			fakeRefBelow.setPreciseX(nextX);
			Point onGridForAboveRef = getOrthogonalLocationNoSnap(fakeRefAbove);
			Point onGridForBelowRef = getOrthogonalLocationNoSnap(fakeRefBelow);
			onVerticalGridLocs.add(onGridForAboveRef);
			if (!onGridForBelowRef.equals(onGridForAboveRef)) {
				onVerticalGridLocs.add(onGridForBelowRef);
			}
		}

		PrecisionPoint fakeRefLeft = new PrecisionPoint(notOnGrid);
		PrecisionPoint fakeRefRight = new PrecisionPoint(notOnGrid);
		fakeRefLeft.setPreciseX(bounds.preciseX() - bounds.preciseWidth() / 2);
		fakeRefRight.setPreciseX(bounds.preciseX() + bounds.preciseWidth()
				+ bounds.preciseWidth() / 2);
		double reminderY = Math.IEEEremainder(
				notOnGrid.preciseY() - gridOrigin.preciseY(), gridY);
		if (reminderY < 0) {
			reminderY += gridY;
		}
		for (double nextY = notOnGrid.preciseY() - reminderY; nextY >= bounds
				.preciseY(); nextY -= gridY) {
			fakeRefLeft.setPreciseY(nextY);
			fakeRefRight.setPreciseY(nextY);
			Point onGridForLeftRef = getOrthogonalLocationNoSnap(fakeRefLeft);
			Point onGridForRightRef = getOrthogonalLocationNoSnap(fakeRefRight);
			onHorizontalGridLocs.add(onGridForLeftRef);
			if (!onGridForLeftRef.equals(onGridForRightRef)) {
				onHorizontalGridLocs.add(onGridForRightRef);
			}
		}
		for (double nextY = gridY + notOnGrid.preciseY() - reminderY; nextY <= bounds
				.preciseY() + bounds.preciseHeight(); nextY += gridY) {
			fakeRefLeft.setPreciseY(nextY);
			fakeRefRight.setPreciseY(nextY);
			Point onGridForLeftRef = getOrthogonalLocationNoSnap(fakeRefLeft);
			Point onGridForRightRef = getOrthogonalLocationNoSnap(fakeRefRight);
			onHorizontalGridLocs.add(onGridForLeftRef);
			if (!onGridForLeftRef.equals(onGridForRightRef)) {
				onHorizontalGridLocs.add(onGridForRightRef);
			}
		}

		// System.err.println("Found: on horizontal grid: " +
		// onHorizontalGridLocs);
		// System.err.println("Found: on vertical grid: " + onVerticalGridLocs);
	}

	protected Point getOrthogonalLocationNoSnap(Point refPoint) {
		return super.getOrthogonalLocation(refPoint);
	}

}
