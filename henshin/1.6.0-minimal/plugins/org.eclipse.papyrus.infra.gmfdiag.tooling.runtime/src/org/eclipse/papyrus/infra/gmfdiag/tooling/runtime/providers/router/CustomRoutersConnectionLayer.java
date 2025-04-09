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
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.ConnectionLayerEx;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.RectilinearRouter;

/**
 * Allows to redefine default GMF Runtime {@link RectilinearRouter} for given
 * diagram.
 * 
 * @since 3.3
 */
public class CustomRoutersConnectionLayer extends ConnectionLayerEx {

	private ConnectionRouter myCustomRectilinearRouter;

	private EditPartViewer myViewer;

	protected EditPartViewer getViewer() {
		return myViewer;
	}

	public void setEditPartViewer(EditPartViewer viewer) {
		myViewer = viewer;
		setupSnapToGrid(myCustomRectilinearRouter);
	}

	@Override
	public ConnectionRouter getRectilinearRouter() {
		if (myCustomRectilinearRouter == null) {
			myCustomRectilinearRouter = createRectilinearRouter();
			setupSnapToGrid(myCustomRectilinearRouter);
		}
		return myCustomRectilinearRouter;
	}

	protected void setupSnapToGrid(ConnectionRouter router) {
		if (router instanceof SnapToGridRouter) {
			((SnapToGridRouter) router).setEditPartViewer(myViewer);
		}
	}

	/**
	 * Creates rectilinear router for this layer. This implementation returns
	 * standard GMF Runtime {@link RectilinearRouter}, subclasses may override
	 * to produce custom rectilinear routing.
	 */
	protected ConnectionRouter createRectilinearRouter() {
		return new RectilinearRouter();
	}

}
