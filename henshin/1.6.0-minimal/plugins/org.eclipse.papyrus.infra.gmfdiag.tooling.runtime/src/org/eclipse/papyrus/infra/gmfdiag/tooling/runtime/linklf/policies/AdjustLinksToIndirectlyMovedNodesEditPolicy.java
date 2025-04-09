package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouter;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouterUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.RectilinearRouter;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.AbsoluteBendpointsConvention;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.DiagramGridSpec;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.router.LinkLFRectilinearRouter;

/**
 * @since 3.3
 */
public class AdjustLinksToIndirectlyMovedNodesEditPolicy extends
		AdjustAbsoluteBendpointsEditPolicyBase {

	/**
	 * Default role for registering this edit policy.
	 * <p/>
	 * The value is prefixed by class FQN in order to avoid conflicts, but the
	 * literal should NOT be used anywhere.
	 */
	public static final String ROLE = AdjustLinksToIndirectlyMovedNodesEditPolicy.class
			.getName() + ":Role";

	private WeakReference<ChangeBoundsRequest> myLastRequest;

	private WeakReference<Command> myLastCommand;

	@Override
	protected Command getAdjustLinksCommand(ChangeBoundsRequest req) {
		if (myLastRequest != null && myLastRequest.get() == req) {
			if (myLastCommand == null) {
				return null;
			}
			Command result = myLastCommand.get();
			if (result != null) {
				return result;
			}
		}
		myLastCommand = null;
		myLastRequest = null;
		Command result = createAdjustLinksCommand(req);
		myLastRequest = new WeakReference<ChangeBoundsRequest>(req);
		myLastCommand = result == null ? null : new WeakReference<Command>(
				result);
		return result;
	}

	@SuppressWarnings("unchecked")
	protected Command createAdjustLinksCommand(ChangeBoundsRequest req) {
		ICommand result = null;
		CachedEditPartsSet allMoved = getMovedEditPartsSet(req);
		LinkedList<GraphicalEditPart> queue = new LinkedList<GraphicalEditPart>();
		queue.addAll(getHost().getChildren());

		while (!queue.isEmpty()) {
			GraphicalEditPart cur = queue.removeFirst();
			for (EditPart link : (Collection<EditPart>) cur
					.getSourceConnections()) {
				ICommand nextOutgoingCommand = getAdjustOneLinkCommand(link,
						allMoved, req, true);
				result = compose(result, nextOutgoingCommand);
			}
			for (EditPart link : (Collection<EditPart>) cur
					.getTargetConnections()) {
				ICommand nextIncomingCommand = getAdjustOneLinkCommand(link,
						allMoved, req, false);
				result = compose(result, nextIncomingCommand);
			}
			queue.addAll((Collection<GraphicalEditPart>) cur.getChildren());
		}

		return result == null ? null : new ICommandProxy(result.reduce());
	}

	protected ICommand getAdjustOneLinkCommand(EditPart linkEP,
			CachedEditPartsSet allMoved, ChangeBoundsRequest req,
			boolean outgoing) {
		if (false == linkEP instanceof ConnectionEditPart) {
			return null;
		}
		ConnectionEditPart link = (ConnectionEditPart) linkEP;
		EditPart staticEnd = outgoing ? link.getTarget() : link.getSource();
		if (staticEnd == null) {
			return null;
		}
		MovedNodeKind kind = allMoved.isMoved(staticEnd);
		if (kind != MovedNodeKind.NO) {
			return null;
		}

		Connection conn = link.getConnectionFigure();
		if (conn == null) {
			return null;
		}
		if (!acceptAdjustingConnection(link, conn)) {
			return null;
		}

		GraphicalEditPart staticGateEP = (GraphicalEditPart) findHighestDifferentAncestor(
				staticEnd, getHost());
		if (staticGateEP == null) {
			return null;
		}

		PointList linkPoints = makeAbsolute(conn, conn.getPoints());

		Point hostGate = findGatePosition(linkPoints, getHostFigure());
		if (hostGate == null) {
			return null;
		}
		int hostGateSegment = PointListUtilities
				.findNearestLineSegIndexOfPoint(linkPoints, hostGate);

		Point staticGate;
		int staticGateSegment;
		if (staticGateEP == staticEnd) {
			staticGate = null;
			staticGateSegment = -1;
		} else {
			IFigure staticGateOwner = staticGateEP.getFigure();
			staticGate = findGatePosition(linkPoints, staticGateOwner);
			if (staticGate == null) {
				return null;
			}
			staticGateSegment = PointListUtilities
					.findNearestLineSegIndexOfPoint(linkPoints, staticGate);
		}

		// System.err.println("Need to adjust link: " + link + ", outgoing: " +
		// outgoing + ", other end: " + staticEnd);
		// System.err.println("\tFound dynamic gate: abs: " + hostGate +
		// ", rel: " + hostGateRel + ", segIndex: " + hostGateSegment);
		// System.err.println("\tFound static gate: abs: " + staticGate +
		// ", rel: " + staticGateRel + ", segIndex: " + staticGateSegment);

		PreserveGatesRequest preserveReq = new PreserveGatesRequest(link,
				linkPoints, outgoing, req);
		preserveReq.setMovingGate(hostGate, hostGateSegment);
		preserveReq.setStaticGate(staticGate, staticGateSegment);

		return createPreserveGatesCommand(preserveReq);
	}

	protected ICommand createPreserveGatesCommand(PreserveGatesRequest req) {
		return new PreserveGatesCommand(getDomain(), req);
	}

	/**
	 * Hook for subclasses, allows to decide whether to adjust links on
	 * instance-by-instance basis.
	 * <p/>
	 * In this implementation the link is accepted if it
	 * <ul>
	 * <li>is backed by some {@link Edge}</li>
	 * <li>has absolute bendpoints {@link AbsoluteBendpointsConvention} which
	 * has to be adjusted</li>
	 * <li>has an orthogonal router</li>
	 * </ul>
	 * 
	 * @param link
	 *            link editpart to check
	 * @param conn
	 *            its connection figure, known to be not <code>null</code>
	 * @return true if link gates should be preserved
	 */
	protected boolean acceptAdjustingConnection(ConnectionEditPart link,
			Connection conn) {
		Edge edge = (Edge) link.getNotationView();
		if (edge == null) {
			return false;
		}
		return AbsoluteBendpointsConvention.getInstance()
				.hasAbsoluteStoredAsRelativeBendpoints(edge) && //
				conn.getConnectionRouter() instanceof OrthogonalRouter;
	}

	/**
	 * Finds the gate, that is an intersection between link points and the given
	 * gate owner.
	 * 
	 * @param linkPointsAbs
	 *            absolute link point coordinates
	 * @param gateOwner
	 *            the figure which intersection with the link defines the gate
	 * 
	 * @return absolute position of the intersection, or <code>null</code> if
	 *         not found. If multiple intersections are found then only the
	 *         first is returned
	 */
	protected Point findGatePosition(PointList linkPointsAbs, IFigure gateOwner) {
		Rectangle ownerBounds = makeAbsolute(gateOwner, gateOwner.getBounds());
		PointList ownerPoints = PointListUtilities
				.createPointsFromRect(ownerBounds);

		PointList intersections = new PointList();
		PointList distances = new PointList();

		boolean computed = PointListUtilities.findIntersections(ownerPoints,
				linkPointsAbs, intersections, distances);
		if (!computed || intersections.size() == 0) {
			System.err.println("Can't compute intersections between:" + //
					" hostBounds: " + pointList2String(ownerPoints) + //
					" and link: " + pointList2String(linkPointsAbs));
			return null;
		}

		if (intersections.size() > 1) {
			System.err
					.println("Expected exactly one intersection between:"
							+ //
							" hostBounds: " + pointList2String(ownerPoints)
							+ //
							" and link: " + pointList2String(linkPointsAbs)
							+ //
							" actually: " + pointList2String(intersections)
							+ //
							" will use the first one: "
							+ intersections.getFirstPoint());
		}

		return intersections.getFirstPoint();
	}

	@Override
	public void deactivate() {
		myLastRequest = null;
		myLastCommand = null;
		super.deactivate();
	}

	/**
	 * Provides the highest (closest to the root) editpart which is ancestor of
	 * the subj editPart but is still not a common ancestor of some other
	 * editpart
	 * 
	 * @param subj
	 *            the editPart to compute ancestor for
	 * @param other
	 *            the editPart which ancestors should be avoided
	 * @return
	 */
	protected static EditPart findHighestDifferentAncestor(EditPart subj,
			EditPart other) {
		Set<EditPart> otherChainUp = new HashSet<EditPart>();
		EditPart cur = other;
		while (cur != null) {
			otherChainUp.add(cur);
			cur = cur.getParent();
		}

		EditPart result = subj;
		while (result != null) {
			EditPart parent = result.getParent();
			if (parent == null) {
				// weird, there should be at least common root edit part
				return null;
			}
			if (otherChainUp.contains(parent)) {
				return result;
			}
			result = parent;
		}
		throw new IllegalStateException();
	}

	protected static class PreserveGatesRequest extends Request {

		private final ConnectionEditPart myLink;

		private final PointList myLinkPoints;

		private final boolean myIsOutgoing;

		private final ChangeBoundsRequest myHostRequest;

		private Point myStaticGateAbs;

		private Point myMovingGateAbs;

		private int myStaticGateSegment;

		private int myMovingGateSegment;

		public PreserveGatesRequest(ConnectionEditPart link,
				PointList linkPointsAbs, boolean outgoing,
				ChangeBoundsRequest hostRequest) {
			myLink = link;
			myLinkPoints = linkPointsAbs;
			myIsOutgoing = outgoing;
			myHostRequest = hostRequest;
		}

		public void setMovingGate(Point movingGateAbs, int segIndex) {
			myMovingGateAbs = movingGateAbs;
			myMovingGateSegment = segIndex;
		}

		public void setStaticGate(Point staticGateAbs, int segIndex) {
			myStaticGateAbs = staticGateAbs;
			myStaticGateSegment = segIndex;
		}

		public Point getMovingGateAbs() {
			return myMovingGateAbs;
		}

		public Point getStaticGateAbs() {
			return myStaticGateAbs;
		}

		public int getMovingGateSegment() {
			return myMovingGateSegment;
		}

		public int getStaticGateSegment() {
			return myStaticGateSegment;
		}

		public ConnectionEditPart getLink() {
			return myLink;
		}

		public Edge getEdge() {
			return (Edge) myLink.getNotationView();
		}

		public PointList getLinkPointsBefore() {
			return myLinkPoints;
		}

		public boolean isOutgoing() {
			return myIsOutgoing;
		}

		public GraphicalEditPart getStaticLinkEnd() {
			return (GraphicalEditPart) (isOutgoing() ? myLink.getTarget()
					: myLink.getSource());
		}

		public GraphicalEditPart getMovingLinkEnd() {
			return (GraphicalEditPart) (isOutgoing() ? myLink.getSource()
					: myLink.getTarget());
		}

		public Point getMoveDelta() {
			return myHostRequest.getMoveDelta();
		}

		public SnapToGrid getSnapToGrid() {
			PrecisionRectangle gridSpec = DiagramGridSpec
					.getAbsoluteGridSpec(getLink().getViewer());
			return gridSpec == null ? null : new SnapToGrid(getLink());
		}
	}

	protected static class PreserveGatesCommand extends
			AbstractTransactionalCommand {

		private static final String LABEL = "Adjusting link preserving implicit gates";

		private final PreserveGatesRequest myRequest;

		public PreserveGatesCommand(TransactionalEditingDomain domain,
				PreserveGatesRequest req) {
			super(domain, LABEL, getWorkspaceFiles(req.getLink()
					.getNotationView()));
			myRequest = req;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
				IAdaptable info) throws ExecutionException {
			Point moveDelta = myRequest.getMoveDelta();
			if (moveDelta == null || moveDelta.x == 0 && moveDelta.y == 0) {
				// may be the case due to the caching: command was created when
				// moveDelta was here
				// but at the execution time request has changed
				return CommandResult.newOKCommandResult();
			}

			PointList oldPoints = myRequest.getLinkPointsBefore();
			// System.err.println("[1]: oldPoints: " +
			// pointList2String(oldPoints));
			PointList newPointsStart = new PointList(oldPoints.size());
			PointList newPointsEnd = new PointList(oldPoints.size() * 2);
			if (myRequest.isOutgoing()) {
				// segment indexes are 1-based, see
				// findNearestLineSegIndexOfPoint
				for (int i = 0; i < myRequest.getMovingGateSegment(); i++) {
					newPointsStart.addPoint(oldPoints.getPoint(i));
				}
				newPointsStart.addPoint(myRequest.getMovingGateAbs());
				newPointsStart.translate(moveDelta);

				if (myRequest.getStaticGateAbs() != null
						&& myRequest.getStaticGateSegment() == myRequest
								.getMovingGateSegment()) {
					newPointsEnd.addPoint(myRequest.getStaticGateAbs());
				}
				for (int i = myRequest.getMovingGateSegment(); i < oldPoints
						.size(); i++) {
					newPointsEnd.addPoint(oldPoints.getPoint(i));
				}
			} else {
				for (int i = 0; i < myRequest.getMovingGateSegment(); i++) {
					newPointsStart.addPoint(oldPoints.getPoint(i));
				}
				if (myRequest.getStaticGateAbs() != null
						&& myRequest.getMovingGateSegment() == myRequest
								.getStaticGateSegment()) {
					newPointsStart.addPoint(myRequest.getStaticGateAbs());
				}

				newPointsEnd.addPoint(myRequest.getMovingGateAbs());
				for (int i = myRequest.getMovingGateSegment(); i < oldPoints
						.size(); i++) {
					newPointsEnd.addPoint(oldPoints.getPoint(i));
				}
				newPointsEnd.translate(moveDelta);
			}

			Point routeStart = newPointsStart.getLastPoint();
			Point routeEnd = newPointsEnd.getFirstPoint();

			// System.err.println("[2]: newPointsStart: " +
			// pointList2String(newPointsStart));
			// System.err.println("[3]: newPointsEnd: " +
			// pointList2String(newPointsEnd));
			// System.err.println("[4]: routeStart: " + routeStart +
			// ", routeEnd:" + routeEnd);

			// Connection conn = ;
			// newPointsStart = makeRelative(conn, newPointsStart);
			// newPointsEnd = makeRelative(conn, newPointsEnd);
			// routeStart = makeRelative(conn, routeStart);
			// routeEnd = makeRelative(conn, routeEnd);

			// System.err.println("[5.1]: newPointsStart: (rel): " +
			// pointList2String(newPointsStart));
			// System.err.println("[5.2]: newPointsEnd: (rel): " +
			// pointList2String(newPointsEnd));
			// System.err.println("[5.3]: routeStart (rel): " + routeStart +
			// ", routeEnd (rel):" + routeEnd);

			int offSourceDirection = PreserveGatesUtil.getSegmentDirection(
					oldPoints, myRequest.getMovingGateSegment());
			int offTargetDirection = PreserveGatesUtil
					.getOppositeDirection(offSourceDirection);

			PointList middlePart = new PointList();
			middlePart.addPoint(routeStart);
			middlePart.addPoint(routeEnd);

			if (!OrthogonalRouterUtilities.isRectilinear(middlePart)) {
				SnapToGrid snapper = myRequest.getSnapToGrid();
				PreserveGatesUtil
						.insertPointsProducingNotAlignedRectilinearSegments(
								myRequest.getLink().getConnectionFigure(),
								middlePart, offSourceDirection,
								offTargetDirection, snapper);
				OrthogonalRouterUtilities.transformToOrthogonalPointList(
						middlePart, //
						PreserveGatesUtil
								.asVerticalOrHorizontal(offSourceDirection), //
						PreserveGatesUtil
								.asVerticalOrHorizontal(offTargetDirection));
			}

			if (middlePart.getFirstPoint()
					.equals(newPointsStart.getLastPoint())) {
				middlePart.removePoint(0);
			}
			if (middlePart.getLastPoint().equals(newPointsEnd.getFirstPoint())) {
				middlePart.removePoint(middlePart.size() - 1);
			}

			PointList newPoints = new PointList(newPointsStart.size()
					+ middlePart.size() + newPointsEnd.size());
			newPoints.addAll(newPointsStart);
			newPoints.addAll(middlePart);
			newPoints.addAll(newPointsEnd);

			PreserveGatesUtil.doRemoveRedundantPoints(newPoints);

			// System.err.println("Old link points: " +
			// pointList2String(oldPoints));
			// System.err.println("--> " + pointList2String(newPoints));

			PointList relPoints = newPoints.getCopy();
			myRequest.getLink().getConnectionFigure()
					.translateToRelative(relPoints);
			// System.err.println("== (rel:) " + pointList2String(relPoints));

			if (newPoints != null) {
				SetAbsoluteBendpointsCommand.setAbsoluteBendpoints(
						myRequest.getEdge(), relPoints);
			}

			return CommandResult.newOKCommandResult();
		}

	}

	private static class PreserveGatesUtil extends LinkLFRectilinearRouter {
		private static PreserveGatesUtil INSTANCE = new PreserveGatesUtil();

		/**
		 * Provides static access to effectvely static method in
		 * {@link LinkLFRectilinearRouter} which is historically defined as
		 * instance-method.
		 * 
		 * @see RectilinearRouter
		 */
		public static boolean doRemoveRedundantPoints(PointList line) {
			return INSTANCE.removeRedundantPoints2(line);
		}

		public static int getOppositeDirection(int direction) {
			int result = 0;
			if ((direction & PositionConstants.EAST) != 0) {
				result |= PositionConstants.WEST;
			}
			if ((direction & PositionConstants.WEST) != 0) {
				result |= PositionConstants.EAST;
			}
			if ((direction & PositionConstants.NORTH) != 0) {
				result |= PositionConstants.SOUTH;
			}
			if ((direction & PositionConstants.SOUTH) != 0) {
				result |= PositionConstants.NORTH;
			}
			return result;
		}

		/**
		 * @param points
		 *            input list of points
		 * @param segment
		 *            1-based segment index, valid for given points list
		 * @return
		 */
		public static int getSegmentDirection(PointList points, int segment) {
			Point start = points.getPoint(segment - 1);
			Point end = points.getPoint(segment);
			if (start.x == end.x) {
				return start.y < end.y ? PositionConstants.SOUTH
						: PositionConstants.NORTH;
			}
			if (start.y == end.y) {
				return start.x < end.x ? PositionConstants.EAST
						: PositionConstants.WEST;
			}
			return getOutisePointOffRectanglePosition2(start, new Rectangle(
					end.x, end.y, 0, 0));
		}

	}

}
