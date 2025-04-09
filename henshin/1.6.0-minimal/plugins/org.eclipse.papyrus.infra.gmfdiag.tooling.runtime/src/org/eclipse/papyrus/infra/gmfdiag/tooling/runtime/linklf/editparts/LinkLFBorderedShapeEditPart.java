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
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Extends {@link AbstractBorderedShapeEditPart} with ability to delegate anchor
 * handling and creation to specific {@link ConnectionAnchorDelegate}.
 * <p/>
 * By default LinkLF mode is switched off, but may be enabled by setting
 * {@link LinkLFShapeNodeEditPart} delegate instance.
 * 
 * @since 3.3
 * @see LinkLFShapeNodeEditPart
 */
public abstract class LinkLFBorderedShapeEditPart extends
		AbstractBorderedShapeEditPart implements
		LinkLFAnchorsDelegatingEditPart {

	/**
	 * This is default delegate that just delegates to the super implementation
	 * in {@link AbstractBorderedShapeEditPart}
	 */
	private final ConnectionAnchorDelegate DELEGATE_TO_SUPER = new ConnectionAnchorDelegate() {

		@Override
		public ConnectionAnchor getTargetConnectionAnchor(Request request) {
			return LinkLFBorderedShapeEditPart.super
					.getTargetConnectionAnchor(request);
		}

		@Override
		public ConnectionAnchor getSourceConnectionAnchor(Request request) {
			return LinkLFBorderedShapeEditPart.super
					.getSourceConnectionAnchor(request);
		}
	};

	private ConnectionAnchorDelegate myAnchorDelegate = DELEGATE_TO_SUPER;

	public LinkLFBorderedShapeEditPart(View view) {
		super(view);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return myAnchorDelegate.getSourceConnectionAnchor(request);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return myAnchorDelegate.getTargetConnectionAnchor(request);
	}

	/**
	 * Allows to externally change {@link ConnectionAnchorDelegate} and thus
	 * enable or disable LinkLF mode.
	 * 
	 * @param anchorDelegate
	 *            delegate or <code>null</code> to reset to default GMF Runtime
	 *            implementation
	 */
	public void setAnchorDelegate(ConnectionAnchorDelegate anchorDelegate) {
		myAnchorDelegate = anchorDelegate;
		if (myAnchorDelegate == null) {
			myAnchorDelegate = DELEGATE_TO_SUPER;
		}
	}
	
	@Override
	public NodeFigure getNodeFigure() {
		return super.getNodeFigure();
	}

}
