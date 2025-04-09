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

import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.router.CustomRoutersConnectionLayer;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.router.CustomRoutersDiagramRootEditPart;

/**
 * Implementation of the {@link CustomRoutersDiagramRootEditPart} which installs
 * customized {@link LinkLFRectilinearRouter} as a rectilinear router for given
 * diagram.
 * 
 * @since 3.3
 */
public class LinkLFDiagramRootEditPart extends CustomRoutersDiagramRootEditPart {

	public LinkLFDiagramRootEditPart(MeasurementUnit mUnit) {
		super(mUnit);
	}

	@Override
	protected CustomRoutersConnectionLayer createConnectionLayer() {
		return new LinkLFConnectionLayer();
	}

}
