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

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.router.CustomRoutersDiagramRootEditPart;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.router.CustomRoutersDiagramRootEditPartProvider;

/**
 * LinkLF specific implementation of the
 * {@link CustomRoutersDiagramRootEditPart}.
 * <p/>
 * This provider will install LinkLF-specific {@link LinkLFConnectionLayer}
 * which in turn will use the customized {@link LinkLFRectilinearRouter} for
 * applicable diagram.
 * 
 * @since 3.3
 */
public class LinkLFDiagramRootEditPartProvider extends
		CustomRoutersDiagramRootEditPartProvider {

	@Override
	public CustomRoutersDiagramRootEditPart createRootEditPart(Diagram diagram) {
		return new LinkLFDiagramRootEditPart(diagram.getMeasurementUnit());
	}

}
