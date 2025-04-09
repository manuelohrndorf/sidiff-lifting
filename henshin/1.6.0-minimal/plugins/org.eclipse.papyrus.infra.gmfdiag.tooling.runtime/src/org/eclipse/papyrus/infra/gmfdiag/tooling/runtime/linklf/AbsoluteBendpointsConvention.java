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
package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;

/**
 * This class defines when to enable the enhanced link anchors and bendpoints
 * behavior, which implementation is based on storing the fixed bendpoints
 * coordinates instead of their relative locations to anchors.
 * 
 * @since 3.3
 */
public abstract class AbsoluteBendpointsConvention {

	private static AbsoluteBendpointsConvention ourInstance;

	public static AbsoluteBendpointsConvention getInstance() {
		if (ourInstance == null) {
			// ourInstance = new OnlyForNewLinks();
			ourInstance = new ForAllLinks();
		}
		return ourInstance;
	}

	public abstract RelativeBendpoint createAbsoluteBendpointStoredAsRelative(
			Point point);

	public abstract boolean isAbsoluteStoredAsRelative(RelativeBendpoint modelBP);

	public abstract Bendpoint d2dBendpoint(RelativeBendpoint modelBP,
			Connection connection, float weight);

	public abstract boolean hasAbsoluteStoredAsRelativeBendpoints(Edge edge);

	public abstract PointList getPointList(Edge edge, Object linkConstraint);

	private abstract static class ConventionBase extends
			AbsoluteBendpointsConvention {

		private static final int MAGIC = -643984;

		@Override
		public RelativeBendpoint createAbsoluteBendpointStoredAsRelative(
				Point point) {
			return new RelativeBendpoint(point.x, point.y, MAGIC, MAGIC);
		}

		@Override
		public boolean isAbsoluteStoredAsRelative(RelativeBendpoint modelBP) {
			return modelBP.getTargetX() == MAGIC
					&& modelBP.getTargetY() == MAGIC;
		}

		@Override
		public Bendpoint d2dBendpoint(RelativeBendpoint modelBP,
				Connection connection, float weight) {
			if (isAbsoluteStoredAsRelative(modelBP)) {
				return new AbsoluteBendpoint(modelBP.getSourceX(),
						modelBP.getSourceY());
			}
			return null;
		}

		@Override
		public PointList getPointList(Edge edge, Object linkConstraint) {
			PointList result = new PointList();
			List<?> d2dBendpoints = linkConstraint instanceof List<?> ? (List<?>) linkConstraint
					: Collections.emptyList();
			RelativeBendpoints allModelBendpoints = (RelativeBendpoints) edge
					.getBendpoints();
			@SuppressWarnings("unchecked")
			List<RelativeBendpoint> modelBendpoints = allModelBendpoints
					.getPoints();
			for (int i = 0; i < modelBendpoints.size(); i++) {
				RelativeBendpoint nextModel = modelBendpoints.get(i);
				Object nextD2d = d2dBendpoints.size() > i ? d2dBendpoints
						.get(i) : null;

				Point nextPoint = getLocation(nextModel, nextD2d);
				if (nextPoint == null) {
					throw new IllegalStateException(
							"Can't extract location: modelBP: " + nextModel
									+ ", d2dBP: " + nextD2d);
				}
				result.addPoint(nextPoint);
			}
			return result;
		}

		protected static org.eclipse.draw2d.RelativeBendpoint newRelativeBendpointD2d(
				RelativeBendpoint modelBP, Connection connection, float weight) {
			org.eclipse.draw2d.RelativeBendpoint rbp = new org.eclipse.draw2d.RelativeBendpoint(
					connection);
			rbp.setRelativeDimensions(new Dimension(modelBP.getSourceX(),
					modelBP.getSourceY()), //
					new Dimension(modelBP.getTargetX(), modelBP.getTargetY()));
			rbp.setWeight(weight);
			return rbp;
		}

		protected abstract Point getLocation(RelativeBendpoint modelBendpoint,
				Object d2dbendpoint);

	}

	protected static class OnlyForNewLinks extends ConventionBase {

		@Override
		public Bendpoint d2dBendpoint(RelativeBendpoint modelBP,
				Connection connection, float weight) {
			Bendpoint result = super.d2dBendpoint(modelBP, connection, weight);
			if (result == null) {
				result = newRelativeBendpointD2d(modelBP, connection, weight);
			}
			return result;
		}

		@Override
		public boolean hasAbsoluteStoredAsRelativeBendpoints(Edge edge) {
			List<?> bendpoints = ((RelativeBendpoints) edge.getBendpoints())
					.getPoints();
			for (Object o : bendpoints) {
				if (o instanceof RelativeBendpoint
						&& isAbsoluteStoredAsRelative((RelativeBendpoint) o)) {
					return true;
				}
			}
			return false;
		}

		@Override
		protected Point getLocation(RelativeBendpoint modelBendpoint,
				Object d2dbendpoint) {
			if (isAbsoluteStoredAsRelative(modelBendpoint)) {
				return new Point(modelBendpoint.getSourceX(),
						modelBendpoint.getSourceY());
			}
			if (d2dbendpoint instanceof Bendpoint) {
				return ((Bendpoint) d2dbendpoint).getLocation();
			}
			return null;
		}

	}

	protected static class ForAllLinks extends ConventionBase {

		@Override
		public Bendpoint d2dBendpoint(RelativeBendpoint modelBP,
				Connection connection, float weight) {
			Bendpoint result = super.d2dBendpoint(modelBP, connection, weight);
			if (result == null) {
				org.eclipse.draw2d.RelativeBendpoint rbp = newRelativeBendpointD2d(
						modelBP, connection, weight);
				// if(connection.getSourceAnchor() != null &&
				// connection.getTargetAnchor() != null) {
				result = new RelativeBendpointWrapper(rbp, connection);
				// }
			}
			return result;
		}

		@Override
		public boolean hasAbsoluteStoredAsRelativeBendpoints(Edge edge) {
			List<?> bendpoints = ((RelativeBendpoints) edge.getBendpoints())
					.getPoints();
			return !bendpoints.isEmpty();
		}

		@Override
		protected Point getLocation(RelativeBendpoint modelBendpoint,
				Object d2dBendpoint) {
			if (isAbsoluteStoredAsRelative(modelBendpoint)) {
				return new Point(modelBendpoint.getSourceX(),
						modelBendpoint.getSourceY());
			}
			if (d2dBendpoint instanceof AbsoluteBendpoint) {
				AbsoluteBendpoint wrapper = (AbsoluteBendpoint) d2dBendpoint;
				return wrapper.getLocation();
			}
			throw new IllegalStateException(
					"I had to create AbsoluteBendpointWrapper for this: "
							+ modelBendpoint + ", " + d2dBendpoint);
		}

		/**
		 * Provides implicit migration of the diagrams created before the
		 * LinksLF.
		 * <p/>
		 * Idea is to create the same "absolute" bendpoints for the old relative
		 * bendpoints created with previous version, and only update the
		 * persistence on the first modification of the link.
		 * <p/>
		 * However, positions of the {@link RelativeBendpoint} depends on the
		 * anchors and, more generally on the bounds of link ends, so they can't
		 * be computed immediately at the time of creation. This class
		 * introduced the deferred replacement, that is, once the
		 * {@link RelativeBendpoint} can compute its positions, their
		 * coordinates are saved and don't depend on the source or target
		 * anchors anymore.
		 */
		@SuppressWarnings("serial")
		private static class RelativeBendpointWrapper extends AbsoluteBendpoint {

			private Point myLocation = null;

			private org.eclipse.draw2d.RelativeBendpoint myRelativeBendpoint;

			private Connection myConnection;

			/**
			 * Wraps the {@link RelativeBendpoint} and defers computation of its
			 * positions until it is ready.
			 * 
			 * @param relativeBendpoint
			 * @param conn
			 */
			public RelativeBendpointWrapper(
					org.eclipse.draw2d.RelativeBendpoint relativeBendpoint,
					Connection conn) {
				super(new Point());
				myRelativeBendpoint = relativeBendpoint;
				myConnection = conn;
			}

			@Override
			public Point getLocation() {
				if (myLocation == null && isReadyToComputeLocation()) {
					myLocation = new Point(myRelativeBendpoint.getLocation());
					myRelativeBendpoint = null;
					myConnection = null;
				}
				return myLocation != null ? myLocation : myRelativeBendpoint
						.getLocation();
			}

			private boolean isReadyToComputeLocation() {
				if (myConnection == null) {
					return false;
				}
				ConnectionAnchor source = myConnection.getSourceAnchor();
				ConnectionAnchor target = myConnection.getTargetAnchor();
				if (source == null || target == null) {
					return false;
				}
				return hasLocation(source.getReferencePoint())
						&& hasLocation(target.getReferencePoint());
			}

			private boolean hasLocation(Point point) {
				return point != null && (point.x() != 0 || point.y() != 0);
			}

			@Override
			public int x() {
				return getLocation().x();
			}

			@Override
			public int y() {
				return getLocation().y();
			}
		}
	}
}
