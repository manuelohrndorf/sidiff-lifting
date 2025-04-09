/*****************************************************************************
 * Copyright (c) 2014 Montages AG and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Michael Golubev (Montages) - Initial API and implementation
 *  Anatoly Tishenko (ticher777@gmail.com) - Initial API and implementation
 */
package org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.AbsoluteBendpointsConvention;

/**
 * This edit policy adjusts bendpoints for links between the nodes moved at the
 * same time. Only absolute bendpoints (also possibly stored as relative, see
 * {@link AbsoluteBendpointsConvention}) require adjustment.
 * <p/>
 *
 * @since 3.3
 */
public class AdjustImplicitlyMovedLinksEditPolicy extends
		AdjustAbsoluteBendpointsEditPolicyBase {

	/**
	 * Default role for registering this edit policy.
	 * <p/>
	 * The value is prefixed by class FQN in order to avoid conflicts, but the
	 * literal should NOT be used anywhere.
	 */
	public static final String ROLE = AdjustImplicitlyMovedLinksEditPolicy.class
			.getName() + ":Role";

	@Override
	protected Command getAdjustLinksCommand(ChangeBoundsRequest req) {
		return getAdjustImplicitlyMovedLinksCommand(req);
	}

	/**
	 * Link is implicitly moved when its source and target are both moved
	 * (directly or indirectly). In this case, {@link RelativeBendpoints} should
	 * not be adjusted. But when the link has effectively absolute bendpoints
	 * (see {@link AbsoluteBendpointsConvention}) all of them must be also moved
	 * to the same {@link ChangeBoundsRequest#getMoveDelta()}
	 * <p/>
	 * Default implementation of this method will only affect the
	 * <strong>outgoing</strong> links from the host edit part to one of the
	 * edit parts being moved. This way all affected links will be modified only
	 * once.
	 */
	protected Command getAdjustImplicitlyMovedLinksCommand(
			ChangeBoundsRequest req) {
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
			for (Object nextLink : cur.getSourceConnections()) {
				if (nextLink instanceof ConnectionEditPart) {
					ConnectionEditPart nextLinkEP = (ConnectionEditPart) nextLink;
					EditPart target = nextLinkEP.getTarget();
					MovedNodeKind move = allMoved.isMoved(target);
					if (move == MovedNodeKind.DIRECTLY
							|| move == MovedNodeKind.INDIRECTLY) {
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
		SetAbsoluteBendpointsCommand result = null;
		Edge edge = (Edge) linkEP.getNotationView();
		if (AbsoluteBendpointsConvention.getInstance()
				.hasAbsoluteStoredAsRelativeBendpoints(edge)) {
			PointList newPoints = AbsoluteBendpointsConvention
					.getInstance()
					.getPointList(edge,
							linkEP.getConnectionFigure().getRoutingConstraint());
			newPoints.translate(req.getMoveDelta());

			result = new SetAbsoluteBendpointsCommand(getDomain());
			result.setEdgeAdapter(new EObjectAdapter(edge));
			result.setNewPointList(newPoints);
		}

		return result;
	}

}
