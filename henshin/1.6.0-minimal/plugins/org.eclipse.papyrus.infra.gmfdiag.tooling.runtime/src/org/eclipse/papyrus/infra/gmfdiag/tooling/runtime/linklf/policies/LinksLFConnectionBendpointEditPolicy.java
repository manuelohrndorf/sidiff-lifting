package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies;

import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionAnchorsCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.SetAllBendpointRequest;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.gef.ui.internal.editpolicies.LineMode;
import org.eclipse.gmf.runtime.gef.ui.internal.tools.ConnectionBendpointTrackerEx;
import org.eclipse.gmf.runtime.gef.ui.internal.tools.SelectConnectionEditPartTracker;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.router.HintedOrthogonalRouter;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.router.HintedOrthogonalRouter.EndRoutingHint;

/**
 * @since 3.3
 */
public class LinksLFConnectionBendpointEditPolicy extends
		ConnectionBendpointEditPolicy3 {
	/**
	 * @see #getBendpointsChangedCommand(BendpointRequest)
	 */
	private static final String PARAM_CACHED_COMMAND_BASED_ON_FEEDBACK = LinksLFConnectionBendpointEditPolicy.class
			.getName() + ":CachedCommand";

	/**
	 * @see #getBendpointsChangedCommand(BendpointRequest)
	 */
	private boolean myIsShowingFeedback;

	public LinksLFConnectionBendpointEditPolicy(LineMode lineMode) {
		super(lineMode);
	}

	public LinksLFConnectionBendpointEditPolicy() {
		this(LineMode.OBLIQUE);
	}

	/**
	 * This method is overridden in order to workaround the bug #445681 for
	 * special rerouting of rectilinear links. There the routing is changed
	 * correctly when user moves (in some specific way) the single bendpoint,
	 * but does not work when user operates on the corresponding link segment.
	 * <p/>
	 * The difference is that the {@link ConnectionBendpointTrackerEx} (case of
	 * single bendpoint) and {@link SelectConnectionEditPartTracker} (case of
	 * segment) behave differently when the user wants to actually fire the
	 * change.
	 * <p/>
	 * The former computes the command when the connection feedback is shown,
	 * and then on buttonUp just executes the cached command. The latter (@see
	 * {@link SelectConnectionEditPartTracker#handleButtonUp(int)}) first erases
	 * the feedback and then asks the editpart to prepare the command.
	 * <p/>
	 * It breaks the idea of computing the command based on the feedback
	 * connection routing which is central for approach of this class and its
	 * super-classes. It ultilmately leads to the bug #445681, because at the
	 * time when the final command is computed, the feedback connection is gone
	 * and routing is wrong.
	 * <p/>
	 * To workaround this, we will cache the previously computed command and
	 * will use it as a one time replacement for the first call immediately
	 * after the erasing the feedback.
	 */
	@Override
	protected Command getBendpointsChangedCommand(BendpointRequest request) {
		// note: it is **removed** from cache below:
		Command cachedPreviousCommand = (Command) request.getExtendedData()
				.remove(PARAM_CACHED_COMMAND_BASED_ON_FEEDBACK);
		if (cachedPreviousCommand != null && !myIsShowingFeedback) {
			// one time, immediately after erasing the feedback we are going to
			// use the old command
			// which is based on the routing of the just erased feedback
			// to avoid breaking other things, we will remove it from cache and
			// won't use it beyond this one time
			return cachedPreviousCommand;
		}

		Command result = super.getBendpointsChangedCommand(request);
		if (myIsShowingFeedback) {
			// still showing feedback, cache the new result for later use
			request.getExtendedData().put(
					PARAM_CACHED_COMMAND_BASED_ON_FEEDBACK, result);
		}
		return result;
	}

	/**
	 * Method getBendpointsChangedCommand Different signature method that allows
	 * a command to constructed for changing the bendpoints without requiring
	 * the original Request.
	 * 
	 * @param connection
	 *            Connection to generate the bendpoints changed command from
	 * @param edge
	 *            notation element that the command will operate on.
	 * @return Command SetBendpointsCommand that contains the point changes for
	 *         the connection.
	 */
	protected Command getBendpointsChangedCommand(Connection connection,
			Edge edge) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
				.getEditingDomain();

		Point ptRef1 = connection.getSourceAnchor().getReferencePoint();
		getConnection().translateToRelative(ptRef1);
		SetConnectionAnchorsCommand srcAnchorUpdate = null;

		if (getHost().getSource() instanceof INodeEditPart
				&& connection.getPoints().size() > 1) {
			ptRef1 = connection.getPoints().getFirstPoint();
			INodeEditPart sourceEP = (INodeEditPart) getHost().getSource();
			ReconnectRequest reconnectSource = new ReconnectRequest(
					RequestConstants.REQ_RECONNECT_SOURCE);
			Point ptAbs1 = ptRef1.getCopy();
			getConnection().translateToAbsolute(ptAbs1);
			reconnectSource.setLocation(ptAbs1);
			reconnectSource.setConnectionEditPart(getHost());
			ConnectionAnchor newAnchor = sourceEP
					.getSourceConnectionAnchor(reconnectSource);
			String newTerminal = sourceEP
					.mapConnectionAnchorToTerminal(newAnchor);
			srcAnchorUpdate = new SetConnectionAnchorsCommand(editingDomain,
					"Updating source anchor");
			srcAnchorUpdate.setEdgeAdaptor(new EObjectAdapter(edge));
			srcAnchorUpdate.setNewSourceTerminal(newTerminal);
		}

		Point ptRef2 = connection.getTargetAnchor().getReferencePoint();
		getConnection().translateToRelative(ptRef2);
		SetConnectionAnchorsCommand trgAnchorUpdate = null;

		if (getHost().getTarget() instanceof INodeEditPart
				&& connection.getPoints().size() > 1) {
			ptRef2 = connection.getPoints().getLastPoint();
			INodeEditPart targetEP = (INodeEditPart) getHost().getTarget();
			ReconnectRequest reconnectTarget = new ReconnectRequest(
					RequestConstants.REQ_RECONNECT_TARGET);
			Point ptAbs2 = ptRef2.getCopy();
			getConnection().translateToAbsolute(ptAbs2);
			reconnectTarget.setLocation(ptAbs2);
			reconnectTarget.setConnectionEditPart(getHost());
			ConnectionAnchor newTargetAnchor = targetEP
					.getTargetConnectionAnchor(reconnectTarget);
			String newTerminal = targetEP
					.mapConnectionAnchorToTerminal(newTargetAnchor);
			trgAnchorUpdate = new SetConnectionAnchorsCommand(editingDomain,
					"Updating target anchor");
			trgAnchorUpdate.setEdgeAdaptor(new EObjectAdapter(edge));
			trgAnchorUpdate.setNewTargetTerminal(newTerminal);
		}

		SetAbsoluteBendpointsCommand sbbCommand = new SetAbsoluteBendpointsCommand(
				editingDomain);
		sbbCommand.setEdgeAdapter(new EObjectAdapter(edge));
		sbbCommand.setNewPointList(connection.getPoints());

		ICommand result = sbbCommand;
		if (srcAnchorUpdate != null) {
			result = result.compose(srcAnchorUpdate);
		}
		if (trgAnchorUpdate != null) {
			result = result.compose(trgAnchorUpdate);
		}

		return new ICommandProxy(result.reduce());
	}

	/**
	 * Method getSetBendpointCommand. This method returns a command that
	 * executes the REQ_SET_ALL_BENDPOINT request
	 * 
	 * @param request
	 *            SetAllBendpointRequest that stores the points to be set by the
	 *            command.
	 * @return Command to be executed.
	 */
	protected Command getSetBendpointCommand(SetAllBendpointRequest request) {
		Connection connection = getConnection();
		PointList newPoints = request.getPoints();

		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
				.getEditingDomain();
		SetAbsoluteBendpointsCommand sbbCommand = new SetAbsoluteBendpointsCommand(
				editingDomain);
		sbbCommand.setEdgeAdapter(new EObjectAdapter((Edge) getHost()
				.getModel()));

		// with SetAbsoluteBendpointsCommand we can use
		// setNewPointList(PointList) here
		// but I left warnings here to revisit what are
		// request.getSource/TargetReference() is
		// and how it is expected to affect the result here
		if (request.getSourceReference() != null
				&& request.getTargetReference() != null) {
			sbbCommand.setNewPointList(
					//
					newPoints, request.getSourceReference(),
					request.getTargetReference());
		} else {
			sbbCommand.setNewPointList(
					//
					newPoints, connection.getSourceAnchor(),
					connection.getTargetAnchor());
		}

		return new ICommandProxy(sbbCommand);
	}

	@Override
	public ConnectionEditPart getHost() {
		return (ConnectionEditPart) super.getHost();
	}

	@Override
	protected Connection getConnection() {
		return super.getConnection();
	}

	/**
	 * Overrides default behavior by additional snapping of the added point with
	 * grid when grid is active
	 */
	@Override
	protected void showOutsideSourceFeedback(LineSeg newLine, LineSeg moveLine,
			List constraint) {
		Connection conn = (Connection) getHostFigure();
		ConnectionAnchor anchor = conn.getSourceAnchor();

		PrecisionRectangle bounds = new PrecisionRectangle(anchor.getOwner()
				.getBounds());
		anchor.getOwner().translateToAbsolute(bounds);

		PrecisionPoint startPoint = new PrecisionPoint(anchor.getOwner()
				.getBounds().getCenter());
		anchor.getOwner().translateToAbsolute(startPoint);

		snapToGrid(startPoint, moveLine.isHorizontal(), bounds);

		conn.translateToRelative(startPoint);
		conn.translateToRelative(bounds);
		Point origin = new Point(newLine.getOrigin());
		if (moveLine.isHorizontal()) {
			origin.x = startPoint.x;
		} else {
			origin.y = startPoint.y;
		}
		newLine.setOrigin(origin);
		constraint.add(0, new AbsoluteBendpoint(startPoint));
	}

	/**
	 * Overrides default behavior by additional snapping of the added point with
	 * grid when grid is active
	 */
	@Override
	protected void showOutsideTargetFeedback(LineSeg newLine, LineSeg moveLine,
			List constraint) {
		Connection conn = (Connection) getHostFigure();
		ConnectionAnchor anchor = conn.getTargetAnchor();
		PrecisionRectangle bounds = new PrecisionRectangle(anchor.getOwner()
				.getBounds());
		anchor.getOwner().translateToAbsolute(bounds);
		PrecisionPoint endPoint = new PrecisionPoint(anchor.getOwner()
				.getBounds().getCenter());
		anchor.getOwner().translateToAbsolute(endPoint);

		snapToGrid(endPoint, moveLine.isHorizontal(), bounds);

		conn.translateToRelative(endPoint);
		conn.translateToRelative(bounds);
		Point terminus = new Point(newLine.getTerminus());
		if (moveLine.isHorizontal()) {
			terminus.x = endPoint.x;
		} else {
			terminus.y = endPoint.y;
		}
		newLine.setTerminus(terminus);
		constraint.add(new AbsoluteBendpoint(endPoint));
	}

	protected void snapToGrid(PrecisionPoint point, boolean horizontally,
			PrecisionRectangle limitingBounds) {
		SnapToHelper snapper = (SnapToHelper) getHost().getAdapter(
				SnapToHelper.class);
		if (snapper == null) {
			return;
		}

		PrecisionPoint snapped = point.getPreciseCopy();
		snapper.snapPoint(new LocationRequest(REQ_MOVE_BENDPOINT),
				horizontally ? PositionConstants.HORIZONTAL
						: PositionConstants.VERTICAL, point, snapped);
		point.setLocation(snapped);
	}

	@Override
	public void showSourceFeedback(Request request) {
		ConnectionRouter router = getConnection().getConnectionRouter();
		if (router instanceof HintedOrthogonalRouter) {
			HintedOrthogonalRouter routerImpl = (HintedOrthogonalRouter) router;
			EndRoutingHint hint = request instanceof BendpointRequest ? EndRoutingHint.FixBendpointMoveAnchor
					: EndRoutingHint.FixAnchorMoveBendpoint;
			routerImpl.setEndRoutingHint(getConnection(), hint);
		}
		super.showSourceFeedback(request);
	}

	@Override
	public void eraseSourceFeedback(Request request) {
		ConnectionRouter router = getConnection().getConnectionRouter();
		if (router instanceof HintedOrthogonalRouter) {
			((HintedOrthogonalRouter) router).setEndRoutingHint(
					getConnection(), null);
		}
		super.eraseSourceFeedback(request);
	}

	/**
	 * @see #getBendpointsChangedCommand(BendpointRequest)
	 */
	@Override
	protected void eraseConnectionFeedback(BendpointRequest request,
			boolean removeFeedbackFigure) {
		myIsShowingFeedback = false;
		super.eraseConnectionFeedback(request, removeFeedbackFigure);
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.linklf.editpolicies.notformars.ConnectionBendpointEditPolicy2#showMoveBendpointFeedback(org.eclipse.gef.requests.BendpointRequest)
	 *
	 * @param request
	 */
	@Override
	protected void showMoveBendpointFeedback(BendpointRequest request) {
		super.showMoveBendpointFeedback(request);
		myIsShowingFeedback = true;
	}

	@Override
	protected void showCreateBendpointFeedback(BendpointRequest request) {
		super.showCreateBendpointFeedback(request);
		myIsShowingFeedback = true;
	}

	@Override
	protected void showMoveLineSegFeedback(BendpointRequest request) {
		super.showMoveLineSegFeedback(request);
		myIsShowingFeedback = true;
	}

	@Override
	protected void showMoveOrthogonalBenspointFeedback(BendpointRequest request) {
		super.showMoveOrthogonalBenspointFeedback(request);
		myIsShowingFeedback = true;
	}

}
