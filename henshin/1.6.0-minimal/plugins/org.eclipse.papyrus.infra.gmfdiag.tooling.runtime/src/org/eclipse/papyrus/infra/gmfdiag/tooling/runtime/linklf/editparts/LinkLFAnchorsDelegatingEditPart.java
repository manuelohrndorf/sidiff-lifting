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
package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;

/**
 * Mixin interface for all LinkLF edit parts that allows to delegate anchors
 * handling to {@link ConnectionAnchorDelegate}
 * 
 * @since 3.3
 */
public interface LinkLFAnchorsDelegatingEditPart {

	/**
	 * Allows to externally change {@link ConnectionAnchorDelegate} and thus
	 * enable or disable LinkLF mode.
	 * 
	 * @param anchorDelegate
	 *            delegate or <code>null</code> to reset to default GMF Runtime
	 *            implementation
	 */
	public void setAnchorDelegate(ConnectionAnchorDelegate anchorDelegate);

	public NodeFigure getNodeFigure();

	/**
	 * Common interface for creation of the {@link ConnectionAnchor} for host
	 * {@link NodeEditPart}s.
	 * <p/>
	 * Implemented as a delegate to be easily installed to different
	 * {@link NodeEditPart} sub-classes that does not share common
	 * diagram-specific base class.
	 * <p/>
	 * 
	 * @see NodeEditPart
	 * @since 3.3
	 */
	public interface ConnectionAnchorDelegate {

		public ConnectionAnchor getSourceConnectionAnchor(Request request);

		public ConnectionAnchor getTargetConnectionAnchor(Request request);

	}

}
