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

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemsAwareFreeFormLayer;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;

/**
 * Base class for diagram root edit part that want to install custom rectilinear
 * routing.
 * 
 * @since 3.3
 */
public abstract class CustomRoutersDiagramRootEditPart extends
		RenderedDiagramRootEditPart {

	private CustomRoutersConnectionLayer myConnectionLayer;

	public CustomRoutersDiagramRootEditPart(MeasurementUnit mUnit) {
		super(mUnit);
	}

	/**
	 * Overridden to allow customization of the routers installed into the
	 * connection layers.
	 * 
	 * @see #createConnectionLayer()
	 */
	@Override
	protected LayeredPane createPrintableLayers() {
		FreeformLayeredPane layeredPane = new FreeformLayeredPane();

		layeredPane.add(new BorderItemsAwareFreeFormLayer(), PRIMARY_LAYER);
		layeredPane.add(myConnectionLayer = createConnectionLayer(),
				CONNECTION_LAYER);
		layeredPane.add(new FreeformLayer(), DECORATION_PRINTABLE_LAYER);

		return layeredPane;
	}

	protected CustomRoutersConnectionLayer createConnectionLayer() {
		return new CustomRoutersConnectionLayer();
	}

	/**
	 * Passes an active diagram {@link EditPartViewer} down to the connection
	 * layer and to its {@link SnapToGridRouter}s.
	 * 
	 * @see SnapToGridRouter
	 */
	@Override
	protected void register() {
		super.register();
		myConnectionLayer.setEditPartViewer(getViewer());
	}

	@Override
	protected void unregister() {
		myConnectionLayer.setEditPartViewer(null);
		super.unregister();
	}

}
