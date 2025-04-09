package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.router;

import java.util.IdentityHashMap;
import java.util.Map;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.figures.OrthogonalConnectionAnchor;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouterUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.RectilinearRouter;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.DiagramGridSpec;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.SlidableSnapToGridAnchor;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.router.SnapToGridRouter;

/**
 * Extends the standard GMF Runtime rectilinear router with following
 * improvements:
 * <ul>
 * <li>respects Snap to grid for the implicit bendpoints introduced when
 * switching from oblique to recti- routing</li>
 * <li>enforces that the last / first segment is always orthogonal to the node
 * side, avoiding the visual anchor position different to the user-set anchor
 * position. The latter happens with default RectilinearRouter when first or
 * last segment is parallel to (and in fact hidden by) the node side</li>
 * <li>routes the segments from/to affixed items orthogonal to the side of the
 * parent node to which affixed node is attached</li>
 * </ul>
 * 
 * @since 3.3
 */
public class LinkLFRectilinearRouter extends RectilinearRouter2 implements
		HintedOrthogonalRouter, SnapToGridRouter {

	private final Map<Connection, EndRoutingHint> myEndRoutingHints = new IdentityHashMap<Connection, EndRoutingHint>();

	private DiagramGridSpec myGridSpec;

	public LinkLFRectilinearRouter() {
		super();
	}

	public EndRoutingHint getDefaultEndRoutingStrategy() {
		return EndRoutingHint.FixAnchorMoveBendpoint;
	}

	@Override
	public void setEditPartViewer(EditPartViewer viewer) {
		if (myGridSpec != null && myGridSpec.getViewer() == viewer) {
			return;
		}
		if (myGridSpec != null) {
			myGridSpec.dispose();
		}
		myGridSpec = viewer == null ? null : new DiagramGridSpec(viewer);
	}

	@Override
	public void setEndRoutingHint(Connection conn, EndRoutingHint hint) {
		if (hint == getDefaultEndRoutingStrategy()) {
			hint = null;
		}
		if (hint == null) {
			myEndRoutingHints.remove(conn);
		} else {
			myEndRoutingHints.put(conn, hint);
		}
	}

	/**
	 * While {@link RectilinearRouter} claims to ignore the endpoints, it still
	 * passes them into the
	 * {@link #removePointsInViews(Connection, PointList, Point, Point)} method.
	 * 
	 * This leads to 2 problems: - the one described in the bug #451604 - even
	 * if we fix the bug above by providing more hints, this added middle point
	 * is not snapped when should be
	 * <p/>
	 * 
	 * So this class ensures that the endpoints are reset to anchor positions
	 * befor routing
	 */
	@Override
	public void routeLine(Connection conn, int nestedRoutingDepth,
			PointList newLine) {
		if (nestedRoutingDepth == 0 && newLine.size() >= 2) {
			Point sourceLoc = conn.getSourceAnchor().getReferencePoint().getCopy();
			Point targetLoc = conn.getTargetAnchor().getReferencePoint().getCopy();

			conn.translateToRelative(sourceLoc);
			conn.translateToRelative(targetLoc);

			newLine.setPoint(sourceLoc, 0);
			newLine.setPoint(targetLoc, newLine.size() - 1);
		}
		super.routeLine(conn, nestedRoutingDepth, newLine);
	}

	/**
	 * @see org.eclipse.gmf.runtime.draw2d.ui.internal.routers.RectilinearRouter#resetEndPointsToEdge(org.eclipse.draw2d.Connection,
	 *      org.eclipse.draw2d.geometry.PointList)
	 *
	 * @param conn
	 * @param line
	 */
	@Override
	protected void resetEndPointsToEdge(Connection conn, PointList line) {
		if (isReorienting(conn)) {
			/*
			 * If the connection doesn't have a shape as a source or target
			 * we'll let the oblique router to do the work. The connection
			 * doesn't need to be rectilinear at this point. There is no support
			 * for making a rectilinear connection for which one of the ends is
			 * not connected to anything.
			 */
			super.resetEndPointsToEdge(conn, line);
			return;
		}
		PrecisionRectangle source = sourceBoundsRelativeToConnection2(conn);
		PrecisionRectangle target = targetBoundsRelativeToConnection2(conn);
		int offSourceDirection = PositionConstants.NONE;
		int offTargetDirection = PositionConstants.NONE;
		int sourceAnchorRelativeLocation = PositionConstants.NONE;
		int targetAnchorRelativeLocation = PositionConstants.NONE;

		SnapToGrid snapper = getSnapper();

		if (line.size() == 0) {
			/*
			 * No valid bend points. We can't call super, so we will do the work
			 * from RouterHelper ourselves
			 */
			PrecisionPoint sourceReference = new PrecisionPoint(conn
					.getTargetAnchor().getReferencePoint());
			PrecisionPoint sourceAnchorPoint = new PrecisionPoint(conn
					.getSourceAnchor().getLocation(sourceReference));
			PrecisionPoint targetAnchorPoint = new PrecisionPoint(conn
					.getTargetAnchor().getLocation(sourceAnchorPoint));
			conn.translateToRelative(sourceAnchorPoint);
			conn.translateToRelative(targetAnchorPoint);

			line.addPoint(sourceAnchorPoint);
			line.addPoint(targetAnchorPoint);

			sourceAnchorRelativeLocation = getAnchorOffRectangleDirection2(
					sourceAnchorPoint, source);
			targetAnchorRelativeLocation = getAnchorOffRectangleDirection2(
					targetAnchorPoint, target);

			insertPointsProducingNotAlignedRectilinearSegments(conn, line,
					sourceAnchorRelativeLocation, targetAnchorRelativeLocation,
					snapper);

			offSourceDirection = getOffShapeDirection2(sourceAnchorRelativeLocation);
			offTargetDirection = getOffShapeDirection2(targetAnchorRelativeLocation);
		} else {
			if (conn.getSourceAnchor() instanceof OrthogonalConnectionAnchor) {
				addSegmentToSourceAnchor(line, conn,
						(OrthogonalConnectionAnchor) conn.getSourceAnchor());
			} else {
				/*
				 * If anchor is not supporting orthogonal connections we'll use
				 * the oblique connection anchors and then convert it to
				 * rectilinear.
				 */
				PrecisionPoint reference = new PrecisionPoint(
						line.getFirstPoint());
				conn.getSourceAnchor().getOwner()
						.translateToAbsolute(reference);
				PrecisionPoint anchorLocation = new PrecisionPoint(conn
						.getSourceAnchor().getLocation(reference));
				conn.translateToRelative(anchorLocation);
				line.insertPoint(anchorLocation, 0);
			}
			if (conn.getTargetAnchor() instanceof OrthogonalConnectionAnchor) {
				addSegmentToTargetAnchor(line, conn,
						(OrthogonalConnectionAnchor) conn.getTargetAnchor());
			} else {
				/*
				 * If anchor is not supporting orthogonal connections we'll use
				 * the oblique connection anchors and then convert it to
				 * rectilinear.
				 */
				PrecisionPoint reference = new PrecisionPoint(
						line.getLastPoint());
				conn.getSourceAnchor().getOwner()
						.translateToAbsolute(reference);
				PrecisionPoint anchorLocation = new PrecisionPoint(conn
						.getTargetAnchor().getLocation(reference));
				conn.translateToRelative(anchorLocation);
				line.addPoint(anchorLocation);
			}
			sourceAnchorRelativeLocation = getAnchorOffRectangleDirection2(
					line.getFirstPoint(), source);
			offSourceDirection = getOffShapeDirection2(sourceAnchorRelativeLocation);
			targetAnchorRelativeLocation = getAnchorOffRectangleDirection2(
					line.getLastPoint(), target);
			offTargetDirection = getOffShapeDirection2(targetAnchorRelativeLocation);
		}

		/*
		 * Convert the polyline to rectilinear. If the connection is rectilinear
		 * already then the connection will remain as it is.
		 */
		OrthogonalRouterUtilities.transformToOrthogonalPointList(line,
				offSourceDirection, offTargetDirection);
		removeRedundantPoints2(line);
	}

	/**
	 * We need to find two points offset from the source and target anchors
	 * outside the shapes such that when the polyline is converted to
	 * rectilinear from oblique we won't have rectilinear line segments alligned
	 * with source or target shapes edges.
	 * <p/>
	 * Copy-pasted from {@link RectilinearRouter} lines 416.
	 */
	@Deprecated
	public static void insertPointsProducingNotAlignedRectilinearSegments(
			PointList line, int sourceAnchorRelativeLocation,
			int targetAnchorRelativeLocation) {
		Point offStart = line.getFirstPoint();
		Point offEnd = line.getLastPoint();
		Dimension offsetDim = offStart.getDifference(offEnd).scale(0.5);
		offStart.translate(getTranslationValue2(sourceAnchorRelativeLocation,
				Math.abs(offsetDim.width), Math.abs(offsetDim.height)));
		offEnd.translate(getTranslationValue2(targetAnchorRelativeLocation,
				Math.abs(offsetDim.width), Math.abs(offsetDim.height)));
		line.insertPoint(offStart, 1);
		line.insertPoint(offEnd, 2);
	}

	public static void insertPointsProducingNotAlignedRectilinearSegments(
			Connection conn, PointList line, int sourceAnchorRelativeLocation,
			int targetAnchorRelativeLocation, SnapToGrid snapper) {
		insertPointsProducingNotAlignedRectilinearSegments(line,
				sourceAnchorRelativeLocation, targetAnchorRelativeLocation);
		if (asVerticalOrHorizontal(sourceAnchorRelativeLocation) != asVerticalOrHorizontal(targetAnchorRelativeLocation)) {
			// for "|_"-like routing we don't need snapping, the added bendpoint
			// is determined fully by anchors
			return;
		}
		// so we are in "_|~"-like routing
		if (snapper != null) {
			PrecisionPoint addedForSource = new PrecisionPoint(line.getPoint(1));
			PrecisionPoint addedForSourceAbs = makeAbsolute(conn,
					addedForSource.getPreciseCopy());
			PrecisionPoint snappedForSourceAbs = addedForSourceAbs
					.getPreciseCopy();

			PrecisionPoint addedForTarget = new PrecisionPoint(line.getPoint(2));
			PrecisionPoint addedForTargetAbs = makeAbsolute(conn,
					addedForTarget.getPreciseCopy());
			PrecisionPoint snappedForTargetAbs = addedForTargetAbs
					.getPreciseCopy();

			snapper.snapPoint(null,
					asVerticalOrHorizontal(sourceAnchorRelativeLocation),
					addedForSourceAbs, snappedForSourceAbs);
			snapper.snapPoint(null,
					asVerticalOrHorizontal(targetAnchorRelativeLocation),
					addedForTargetAbs, snappedForTargetAbs);

			PrecisionPoint snappedForSource = makeRelative(conn,
					snappedForSourceAbs.getPreciseCopy());
			PrecisionPoint snappedForTarget = makeRelative(conn,
					snappedForTargetAbs.getPreciseCopy());

			if (snappedForSource.getDistance(addedForSource) <= snappedForTarget
					.getDistance(addedForTarget)) {
				Dimension delta = snappedForSource
						.getDifference(addedForSource);
				line.setPoint(snappedForSource, 1);
				line.setPoint(addedForTarget.getTranslated(delta), 2);
			} else {
				Dimension delta = snappedForTarget
						.getDifference(addedForTarget);
				line.setPoint(addedForSource.getTranslated(delta), 1);
				line.setPoint(snappedForTarget, 2);
			}
		}
	}

	public static int asVerticalOrHorizontal(int direction) {
		return getOffShapeDirection2(direction);
	}

	protected SnapToGrid getSnapper() {
		if (myGridSpec == null || myGridSpec.getAbsoluteGridSpec() == null) {
			return null;
		}
		return new SnapToGrid((GraphicalEditPart) myGridSpec.getViewer()
				.getContents());
	}

	protected void addSegmentToSourceAnchor(PointList line, Connection conn,
			OrthogonalConnectionAnchor anchor) {
		Point firstBend = line.getFirstPoint();
		int prevSegmentOrientation = line.size() < 2 ? PositionConstants.NONE
				: getSegmentOrientation(firstBend, line.getPoint(1));
		Point[] prependSegment = computeFirstOrLastSegment(conn, anchor,
				firstBend, prevSegmentOrientation);
		for (int i = 0; i < prependSegment.length; i++) {
			Point next = prependSegment[i];
			if (i == prependSegment.length - 1 && next.equals(firstBend)) {
				continue; // actually break
			}
			line.insertPoint(next, i);
		}
	}

	protected void addSegmentToTargetAnchor(PointList line, Connection conn,
			OrthogonalConnectionAnchor anchor) {
		Point lastBend = line.getLastPoint();
		int prevSegmentOrientation = line.size() < 2 ? PositionConstants.NONE
				: getSegmentOrientation(lastBend,
						line.getPoint(line.size() - 2));
		Point[] appendSegment = computeFirstOrLastSegment(conn, anchor,
				lastBend, prevSegmentOrientation);
		for (int i = appendSegment.length - 1; i >= 0; i--) {
			Point next = appendSegment[i];
			if (i == appendSegment.length - 1 && next.equals(lastBend)) {
				continue;
			}
			line.addPoint(next);
		}
	}

	/**
	 * Returns the array of point that represents the routing from the point at
	 * figure bounds to the given first or last bendpoint. Result array is
	 * ordered from the figure bounds, so the 0-th index always represent the
	 * point at figure.
	 */
	protected Point[] computeFirstOrLastSegment(Connection conn,
			OrthogonalConnectionAnchor anchor, Point bendpoint,
			int prevSegmentOrientation) {
		LineSeg fromFigureToBendpoint = OrthogonalRouterUtilities
				.getOrthogonalLineSegToAnchorLoc(conn, anchor, bendpoint);
		Point figurePoint = fromFigureToBendpoint.getOrigin();
		Point syntheticBend = bendpoint.getCopy();
		EndRoutingHint hint = findEndRoutingHint(conn, anchor);
		if (hint == EndRoutingHint.FixAnchorMoveBendpoint) {
			Point bendpointInAbsCoordinates = bendpoint.getCopy();
			conn.translateToAbsolute(bendpointInAbsCoordinates);
			int orientation = getOrientationAgainst(anchor,
					bendpointInAbsCoordinates);
			if (orientation == PositionConstants.NONE) {
				orientation = getPreferredOutgoingDirection(conn, anchor);
			}
			if (orientation == PositionConstants.NONE) {
				orientation = flipOrientation(prevSegmentOrientation);
			}
			Point refPoint = anchor.getReferencePoint().getCopy();
			conn.translateToRelative(refPoint);
			switch (orientation) {
			case PositionConstants.VERTICAL:
				syntheticBend.setX(refPoint.x());
				break;
			case PositionConstants.HORIZONTAL:
				syntheticBend.setY(refPoint.y());
				break;
			default:
				// Activator.log.error(new
				// IllegalStateException("Unexpected orientation: " +
				// orientation + //
				// ", bendpoint-abs: " + bendpointInAnchorCoordinates +
				// ", bendpoint-link-rel:: [" + bendpoint + "], " + //
				// ", anchor: " + anchor.getReferencePoint()
				// ));
				break;
			}
			fromFigureToBendpoint = OrthogonalRouterUtilities
					.getOrthogonalLineSegToAnchorLoc(conn, anchor,
							syntheticBend);
			figurePoint = fromFigureToBendpoint.getOrigin();
		}

		Point[] result = new Point[] { figurePoint, syntheticBend };
		return result;
	}

	/**
	 * [GMFRT] extract to utility method
	 * 
	 * This code is partial copy of the
	 * {@link BaseSlidableAnchor#getOrthogonalLocation(Point)}.
	 * 
	 * @deprecated
	 */
	private int getOrientationAgainst(ConnectionAnchor anchor,
			Point orthoReference) {
		// Note that <code>ownReference</code> is not used besides call the
		// getClosestSide()
		// so in contrast to the original code in BaseSlidableAnchor, we have
		// commented out useless updates
		PrecisionPoint ownReference = new PrecisionPoint(
				anchor.getReferencePoint());
		PrecisionRectangle bounds = new PrecisionRectangle(
				FigureUtilities.getAnchorableFigureBounds(anchor.getOwner()));
		anchor.getOwner().translateToAbsolute(bounds);
		bounds.expand(0.000001, 0.000001);
		PrecisionPoint preciseOrthoReference = new PrecisionPoint(
				orthoReference);
		int orientation = PositionConstants.NONE;
		if (bounds.contains(preciseOrthoReference)) {
			int side = SlidableSnapToGridAnchor.getClosestSide2(ownReference,
					bounds);
			switch (side) {
			case PositionConstants.LEFT:
			case PositionConstants.RIGHT:
				// ownReference.preciseY = preciseOrthoReference.preciseY();
				orientation = PositionConstants.HORIZONTAL;
				break;
			case PositionConstants.TOP:
			case PositionConstants.BOTTOM:
				// ownReference.preciseX = preciseOrthoReference.preciseX();
				orientation = PositionConstants.VERTICAL;
				break;
			}
		} else if (preciseOrthoReference.preciseX >= bounds.preciseX
				&& preciseOrthoReference.preciseX <= bounds.preciseX
						+ bounds.preciseWidth) {
			// ownReference.preciseX = preciseOrthoReference.preciseX;
			orientation = PositionConstants.VERTICAL;
		} else if (preciseOrthoReference.preciseY >= bounds.preciseY
				&& preciseOrthoReference.preciseY <= bounds.preciseY
						+ bounds.preciseHeight) {
			// ownReference.preciseY = preciseOrthoReference.preciseY;
			orientation = PositionConstants.HORIZONTAL;
		}
		// ownReference.updateInts();
		return orientation;
	}

	protected EndRoutingHint findEndRoutingHint(Connection conn,
			OrthogonalConnectionAnchor anchor) {
		EndRoutingHint result = myEndRoutingHints.get(conn);
		if (result == null) {
			result = getDefaultEndRoutingStrategy();
		}
		return result;
	}

	protected static int flipOrientation(int verticalOrHorizontal) {
		switch (verticalOrHorizontal) {
		case PositionConstants.VERTICAL:
			return PositionConstants.HORIZONTAL;
		case PositionConstants.HORIZONTAL:
			return PositionConstants.VERTICAL;
		default:
			return PositionConstants.NONE;
		}
	}

	protected static int getSegmentOrientation(Point start, Point end) {
		if (start.x() == end.x()) {
			return PositionConstants.VERTICAL;
		}
		if (start.y() == end.y()) {
			return PositionConstants.HORIZONTAL;
		}
		return PositionConstants.NONE;
	}

	/**
	 * When anchor is placed directly at the anchorable bound of its owner, this
	 * method returns the direction orthogonal to the side this anchor is
	 * attached to. Returns {@link PositionConstants#NONE} in all other cases.
	 * 
	 * @return {@link PositionConstants#HORIZONTAL} or
	 *         {@link PositionConstants#VERTICAL} or
	 *         {@link PositionConstants#NONE}
	 */
	protected int getPreferredOutgoingDirection(Connection conn,
			ConnectionAnchor anchor) {
		final double TOLERANCE = 2;

		IFigure owner = anchor.getOwner();
		if (owner == null) {
			return PositionConstants.NONE;
		}
		PrecisionRectangle bounds = new PrecisionRectangle(
				FigureUtilities.getAnchorableFigureBounds(owner));
		owner.translateToAbsolute(bounds);
		conn.translateToRelative(bounds);

		PrecisionPoint anchorLoc = new PrecisionPoint(
				anchor.getReferencePoint());
		conn.translateToRelative(anchorLoc);

		if (Math.abs(anchorLoc.preciseX() - bounds.preciseX()) < TOLERANCE) {
			return PositionConstants.HORIZONTAL; // .WEST
		}
		if (Math.abs(anchorLoc.preciseX() - bounds.preciseRight()) < TOLERANCE) {
			return PositionConstants.HORIZONTAL; // .EAST
		}
		if (Math.abs(anchorLoc.preciseY() - bounds.preciseY()) < TOLERANCE) {
			return PositionConstants.VERTICAL; // .NORTH
		}
		if (Math.abs(anchorLoc.preciseY() - bounds.preciseBottom()) < TOLERANCE) {
			return PositionConstants.VERTICAL; // .SOUTH
		}
		return PositionConstants.NONE;
	}

}
