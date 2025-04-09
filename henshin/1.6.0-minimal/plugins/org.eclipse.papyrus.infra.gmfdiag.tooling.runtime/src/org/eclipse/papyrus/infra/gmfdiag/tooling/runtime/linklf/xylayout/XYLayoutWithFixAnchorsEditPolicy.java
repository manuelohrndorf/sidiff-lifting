/*****************************************************************************
 * Copyright (c) 2014 CEA LIST, Montages AG 
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Michael Golubev (Montages) - adaptation for GMF Tooling 
 *****************************************************************************/
package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.xylayout;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;

/**
 * This class enables use of {@link FixAnchorHelper} authored by Papyrus.
 * The code relevant to {@link FixAnchorHelper} had been partially copy-pasted from 
 * original Papyrus modification of {@link XYLayoutEditPolicy}.
 * 
 * @since 3.3
 */
public class XYLayoutWithFixAnchorsEditPolicy extends XYLayoutEditPolicy {

	private FixAnchorHelper myFixAnchorHelper;

	@Override
	public void activate() {
		super.activate();
		myFixAnchorHelper = new FixAnchorHelper(getHost().getEditingDomain());
	}

	@Override
	protected Command getChangeConstraintCommand(ChangeBoundsRequest request) {
		Command result = super.getChangeConstraintCommand(request);
		if (result != null && result.canExecute()) {
			//XXX: in Papyrus it is guarded by request.isConstrainedResize();
			boolean isConstrainedResize = true;
			@SuppressWarnings("unchecked")
			List<EditPart> editParts = (List<EditPart>) request.getEditParts();
			for (EditPart child : editParts) {
				//we add the command to fix the anchor
				if (isConstrainedResize && child instanceof INodeEditPart) {
					if (myFixAnchorHelper != null) {
						Command fixAnchorCommand = myFixAnchorHelper.getFixIdentityAnchorCommand( //
								(INodeEditPart) child, request.getMoveDelta(), request.getSizeDelta(), request.getResizeDirection());
						if (fixAnchorCommand != null) {
							result = result.chain(fixAnchorCommand);
						}
					}
				}
			}
		}
		return result;
	}

	public IGraphicalEditPart getHost() {
		return (IGraphicalEditPart) super.getHost();
	}

}
