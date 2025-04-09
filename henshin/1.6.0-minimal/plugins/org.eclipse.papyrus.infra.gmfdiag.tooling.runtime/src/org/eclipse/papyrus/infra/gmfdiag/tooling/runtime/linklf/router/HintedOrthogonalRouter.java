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
package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.router;

import org.eclipse.draw2d.Connection;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouter;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.RectilinearRouter;

/**
 * When connection anchor and first/last bendpoint are not aligned vertically /
 * horizontally, the {@link OrthogonalRouter} has to decide whether the first /
 * last segment of link will go:
 * <ul>
 * <li>from bendpoint to some point different to anchor</li>
 * <li>from anchor to some point different to bendpoint</li>
 * </ul>
 * The default {@link RectilinearRouter} always prefers the first choice.
 * <p>
 * This router extends the {@link OrthogonalRouter} with ability to specify the
 * hint for this choice externally, on the per-connection basis.
 * 
 * @since 3.3
 */
public interface HintedOrthogonalRouter extends OrthogonalRouter {

	/**
	 * Specifies 2 possible strategy for the routing of the first / last link
	 * segment.
	 * <ul>
	 * <li> {@link EndRoutingHint#FixBendpointMoveAnchor} is default behavior. It
	 * will route the segment from bendpoint to some point different to anchor</li>
	 * <li> {@link EndRoutingHint#FixAnchorMoveBendpoint} will route from anchor
	 * to some point different to bendpoint</li>
	 * </ul>
	 */
	public static enum EndRoutingHint {
		FixAnchorMoveBendpoint, //
		FixBendpointMoveAnchor;
	}

	/**
	 * Returns default strategy for this router which will be used when hint is
	 * not specified.
	 */
	public EndRoutingHint getDefaultEndRoutingStrategy();

	/**
	 * Asks router to use specified strategy for given connection.
	 * 
	 * @param hint
	 *            desired hint or <code>null</code> to revert connection back to
	 *            default strategy.
	 */
	public void setEndRoutingHint(Connection conn, EndRoutingHint hint);

}
