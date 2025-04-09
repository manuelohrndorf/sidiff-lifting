/*****************************************************************************
 * Copyright (c) 2017, 2023 CEA LIST.
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
 *   Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 582075
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.AbsoluteBendpointsConvention;

/**
 * Diagrams that use {@link AbsoluteBendpointsConvention} are facing the need to
 * adjust now-absolute bendpoints on different move's. This class provides
 * boilerplate for edit policies that handles different aspects of this
 * adjustment.
 * <p/>
 *
 * @since 3.3
 */
public abstract class AdjustAbsoluteBendpointsEditPolicyBase extends
		GraphicalEditPolicy {

	/**
	 * The same {@link ChangeBoundsRequest} is sent to all moved edit parts, so
	 * we can cache the info about them in request potentially improving o(N^2)
	 * performance.
	 */
	private static final String PARAM_CACHED_EDIT_PARTS_SET = AdjustAbsoluteBendpointsEditPolicyBase.class
			.getName() + ":CachedMovedEPs";

	/**
	 * Tries to find the cached instance of {@link CachedEditPartsSet} in the
	 * request extended data map. If not found, initializes the new instance and
	 * caches it in request for other edit-policy instances.
	 *
	 * @param req
	 * @return never returns <code>null</code>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static CachedEditPartsSet getMovedEditPartsSet(
			ChangeBoundsRequest req) {
		Map extData = req.getExtendedData();
		CachedEditPartsSet set = (CachedEditPartsSet) extData
				.get(PARAM_CACHED_EDIT_PARTS_SET);
		if (set == null) {
			set = new CachedEditPartsSet(req.getEditParts());
			extData.put(PARAM_CACHED_EDIT_PARTS_SET, set);
		}
		return set;
	}

	protected abstract Command getAdjustLinksCommand(ChangeBoundsRequest req);

	@Override
	public boolean understandsRequest(Request req) {
		return req instanceof ChangeBoundsRequest
				&& REQ_MOVE.equals(req.getType());
	}

	@Override
	public Command getCommand(Request request) {
		if (understandsRequest(request)) {
			return getAdjustLinksCommand((ChangeBoundsRequest) request);
		}
		return null;
	}

	@Override
	public IGraphicalEditPart getHost() {
		return (IGraphicalEditPart) super.getHost();
	}

	protected TransactionalEditingDomain getDomain() {
		return getHost().getEditingDomain();
	}

	protected static class CachedEditPartsSet {

		private final Set<EditPart> myDirectlyMoved;

		private final Set<EditPart> myKnownIndirectlyYes;

		private final Set<EditPart> myKnownIndirectlyNo;

		public CachedEditPartsSet(List<? extends EditPart> directlyMoved) {
			myDirectlyMoved = new HashSet<>(directlyMoved);
			myKnownIndirectlyNo = new HashSet<>(
					directlyMoved.size() * 5 + 1);
			myKnownIndirectlyYes = new HashSet<>(
					directlyMoved.size() * 5 + 1);
		}

		public MovedNodeKind isMoved(EditPart ep) {
			List<EditPart> chainUp = new LinkedList<>();
			EditPart cur = ep;
			MovedNodeKind kind = null;
			while (cur != null) {
				kind = getKnownKind(cur);
				if (kind != null) {
					break;
				}
				chainUp.add(cur);
				cur = cur.getParent();
			}
			if (cur == null || kind == null) {
				kind = MovedNodeKind.NO;
			} else if (kind == MovedNodeKind.DIRECTLY && cur != ep) {
				kind = MovedNodeKind.INDIRECTLY;
			}

			Set<EditPart> forKind;
			switch (kind) {
			case DIRECTLY:
				forKind = myDirectlyMoved;
				break;
			case INDIRECTLY:
				forKind = myKnownIndirectlyYes;
				break;
			case NO:
				forKind = myKnownIndirectlyNo;
				break;
			default:
				throw new IllegalArgumentException("Wow: " + kind);
			}

			if (kind != MovedNodeKind.DIRECTLY) {
				forKind.addAll(chainUp);
			}

			return kind;
		}

		private MovedNodeKind getKnownKind(EditPart ep) {
			if (myDirectlyMoved.contains(ep)) {
				return MovedNodeKind.DIRECTLY;
			}
			if (myKnownIndirectlyYes.contains(ep)) {
				return MovedNodeKind.INDIRECTLY;
			}
			if (myKnownIndirectlyNo.contains(ep)) {
				return MovedNodeKind.NO;
			}
			return null;
		}

	}

	protected static enum MovedNodeKind {
		DIRECTLY, INDIRECTLY, NO
	}

	protected static ICommand compose(ICommand c1, ICommand c2) {
		return c1 == null ? c2 : c1.compose(c2);
	}

	protected static String pointList2String(PointList points) {
		return points == null ? "<null>" : Arrays.toString(points.toIntArray());
	}

	protected static Point makeRelative(IFigure f, Point point) {
		Point result = point.getCopy();
		f.translateToRelative(result);
		return result;
	}

	protected static PointList makeRelative(IFigure f, PointList points) {
		PointList result = points.getCopy();
		f.translateToRelative(result);
		return result;
	}

	protected static Rectangle makeRelative(IFigure f, Rectangle rect) {
		Rectangle result = rect.getCopy();
		f.translateToRelative(result);
		return result;
	}

	protected static Point makeAbsolute(IFigure f, Point point) {
		Point result = point.getCopy();
		f.translateToAbsolute(result);
		return result;
	}

	protected static PointList makeAbsolute(IFigure f, PointList points) {
		PointList result = points.getCopy();
		f.translateToAbsolute(result);
		return result;
	}

	protected static Rectangle makeAbsolute(IFigure f, Rectangle rect) {
		Rectangle result = rect.getCopy();
		f.translateToAbsolute(result);
		return result;
	}

}
