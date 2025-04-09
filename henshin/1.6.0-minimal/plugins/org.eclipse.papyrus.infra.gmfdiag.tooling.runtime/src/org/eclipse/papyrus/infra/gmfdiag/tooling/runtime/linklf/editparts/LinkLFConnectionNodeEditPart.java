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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.ForestRouter;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouter;
import org.eclipse.gmf.runtime.gef.ui.internal.editpolicies.LineMode;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.AbsoluteBendpointsConvention;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies.LinksLFConnectionBendpointEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies.LinksLFConnectionEndPointEditPolicy;

/**
 * This is a base class for LinkLF connection edit parts which allows to switch
 * LinkLF mode on / off. When the LinkLF is switched off, the link should behave
 * exactly like default GMF Runtime {@link ConnectionNodeEditPart}.
 * <p/>
 * 
 * @since 3.3
 */
public abstract class LinkLFConnectionNodeEditPart extends
		OverridableConnectionNodeEditPart {

	private boolean myLinkLFEnabled;
	private EditPolicy myOriginalEndPointsEditPolicy;

	public LinkLFConnectionNodeEditPart(View view) {
		super(view);
	}

	public void setLinkLFEnabled(boolean enabled) {
		if (enabled != myLinkLFEnabled) {
			myLinkLFEnabled = enabled;
			refreshRouterChange();
			refreshEndPointsEditPolicy();
		}
	}

	/**
	 * Called when LinkLF-mode for the link is changed. It is expected to happen
	 * at max once in the link lifetime.
	 */
	protected void refreshEndPointsEditPolicy() {
		if (myOriginalEndPointsEditPolicy == null) {
			myOriginalEndPointsEditPolicy = getEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE);
		}

		EditPolicy endPoints = myLinkLFEnabled ? createEndPointsEditPolicy()
				: myOriginalEndPointsEditPolicy;
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, endPoints);
	}

	/**
	 * Allows subclasses to redefine an edit policy for
	 * {@link EditPolicy#CONNECTION_ENDPOINTS_ROLE} role.
	 */
	protected EditPolicy createEndPointsEditPolicy() {
		// return new DebugShowConnectionEndPointsAndAnchorsEditPolicy();
		return new LinksLFConnectionEndPointEditPolicy();
	}

	@Override
	protected void refreshBendpoints() {
		if (!myLinkLFEnabled) {
			super.refreshBendpoints();
			return;
		}

		RelativeBendpoints bendpoints = (RelativeBendpoints) getEdge()
				.getBendpoints();
		List<?> modelConstraint = bendpoints.getPoints();
		List<Bendpoint> figureConstraint = new ArrayList<Bendpoint>();
		for (int i = 0; i < modelConstraint.size(); i++) {
			RelativeBendpoint wbp = (RelativeBendpoint) modelConstraint.get(i);
			float weight;
			if (modelConstraint.size() == 1) {
				weight = 0.5f;
			} else {
				weight = i / ((float) modelConstraint.size() - 1);
			}
			Bendpoint bp = AbsoluteBendpointsConvention.getInstance()
					.d2dBendpoint(wbp, getConnectionFigure(), weight);
			figureConstraint.add(bp);
		}
		getConnectionFigure().setRoutingConstraint(figureConstraint);
	}

	/**
	 * When in the LinkLF mode, installs LinkLF-versions of the corresponding
	 * {@link EditPolicy#CONNECTION_BENDPOINTS_ROLE} edit policy.
	 */
	protected final void installBendpointEditPolicy() {
		if (!myLinkLFEnabled) {
			super.installBendpointEditPolicy();
			return;
		}

		if (getConnectionFigure().getConnectionRouter() instanceof ForestRouter) {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new LinksLFConnectionBendpointEditPolicy(
							LineMode.ORTHOGONAL_CONSTRAINED));
		} else if (getConnectionFigure().getConnectionRouter() instanceof OrthogonalRouter) {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new LinksLFConnectionBendpointEditPolicy(
							LineMode.ORTHOGONAL_FREE));
		} else {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new LinksLFConnectionBendpointEditPolicy(LineMode.OBLIQUE));
		}
		refreshConnectionCursor();
	}

}
