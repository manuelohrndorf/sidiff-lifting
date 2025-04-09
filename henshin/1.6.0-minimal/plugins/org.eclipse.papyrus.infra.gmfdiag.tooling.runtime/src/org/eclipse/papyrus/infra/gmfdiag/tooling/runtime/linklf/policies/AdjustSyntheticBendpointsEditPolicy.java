package org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouter;
import org.eclipse.gmf.runtime.notation.Edge;

/**
 * This editpolicy persists synthetic bendpoints created by rectilinear router
 * and not yet serialized in the model. It should be registered before other
 * adjusting edit policies for the same node.
 * <p/>
 *
 * @since 3.3
 */
public class AdjustSyntheticBendpointsEditPolicy extends
		AdjustAbsoluteBendpointsEditPolicyBase {

	/**
	 * Default role for registering this edit policy.
	 * <p/>
	 * The value is prefixed by class FQN in order to avoid conflicts, but the
	 * literal should NOT be used anywhere.
	 */
	public static final String ROLE = AdjustSyntheticBendpointsEditPolicy.class
			.getName() + ":Role";

	@Override
	protected Command getAdjustLinksCommand(ChangeBoundsRequest req) {
		final Point moveDelta = req.getMoveDelta();
		if (moveDelta.x == 0 && moveDelta.y == 0) {
			return null;
		}

		CachedEditPartsSet allMoved = getMovedEditPartsSet(req);
		ICommand result = null;
		LinkedList<GraphicalEditPart> queue = new LinkedList<>();
		queue.add(getHost());

		while (!queue.isEmpty()) {
			GraphicalEditPart cur = queue.removeFirst();
			// we will adjust target (incoming) links only from the ends which
			// are not moved
			// and all outgoing (source) links
			// this way all links are processed only once
			for (Object nextLink : cur.getSourceConnections()) {
				if (nextLink instanceof ConnectionEditPart) {
					ConnectionEditPart nextLinkEP = (ConnectionEditPart) nextLink;
					ICommand nextAdjustment = getAdjustOneLinkCommand(
							nextLinkEP, req);
					result = compose(result, nextAdjustment);
				}
			}
			for (Object nextLink : cur.getTargetConnections()) {
				if (nextLink instanceof ConnectionEditPart) {
					ConnectionEditPart nextLinkEP = (ConnectionEditPart) nextLink;
					EditPart target = nextLinkEP.getSource();
					MovedNodeKind move = allMoved.isMoved(target);
					if (move == MovedNodeKind.NO) {
						ICommand nextAdjustment = getAdjustOneLinkCommand(
								nextLinkEP, req);
						result = compose(result, nextAdjustment);
					}
				}
			}

			Collection<? extends GraphicalEditPart> children = cur.getChildren();
			queue.addAll(children);
		}
		return result == null ? null : new ICommandProxy(result.reduce());
	}

	private ICommand getAdjustOneLinkCommand(ConnectionEditPart linkEP,
			ChangeBoundsRequest req) {
		Connection conn = linkEP.getConnectionFigure();
		ConnectionRouter router = conn.getConnectionRouter();
		if (false == router instanceof OrthogonalRouter) {
			// only ortho-routers may produce synthetic bend points
			return null;
		}
		if (false == linkEP.getNotationView() instanceof Edge) {
			return null;
		}

		PointList points = conn.getPoints();
		if (points.size() <= 2) { // source + target
			return null;
		}
		if (false == conn.getRoutingConstraint() instanceof List<?>) {
			return null;
		}
		List<?> d2dBendpoints = (List<?>) conn.getRoutingConstraint();
		if (d2dBendpoints.size() >= points.size()) {
			return null;
		}
		SetAbsoluteBendpointsCommand result = new SetAbsoluteBendpointsCommand(
				linkEP.getEditingDomain());
		result.setEdge(linkEP);
		result.setNewPointList(points);

		return result;
	}

}
