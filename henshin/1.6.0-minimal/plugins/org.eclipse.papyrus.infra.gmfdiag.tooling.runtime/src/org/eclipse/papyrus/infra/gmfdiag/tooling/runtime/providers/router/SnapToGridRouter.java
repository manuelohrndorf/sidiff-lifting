/*****************************************************************************
 * Copyright (c) 2014 CEA LIST, Montages AG and others.
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
package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.router;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.SnapToGrid;

/**
 * Interface for {@link ConnectionRouter} which respects active
 * {@link SnapToGrid} options of the host diagram.
 * <p/>
 * In order to get access to the {@link SnapToGrid} settings, this router should
 * be initialized with {@link EditPartViewer}.
 * 
 * @since 3.3
 * @see CustomRoutersDiagramRootEditPart#register
 */
public interface SnapToGridRouter extends ConnectionRouter {

	/**
	 * Initializes the router with active {@link EditPartViewer} which can be
	 * used to access the {@link SnapToGrid} settings
	 * 
	 * @param viewer
	 *            active edit part viewer or <code>null</code> indicating that
	 *            router is unregistered.
	 */
	public void setEditPartViewer(EditPartViewer viewer);

}
