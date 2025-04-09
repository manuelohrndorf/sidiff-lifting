package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.swt.graphics.Color;

/**
 * @since 3.3
 */
public class DebugShowConnectionEndPointsAndAnchorsEditPolicy extends
		LinksLFConnectionEndPointEditPolicy {

	private FeedbackBase mySourceAnchor;

	private FeedbackBase myTargetAnchor;

	private List<FeedbackBase> myRoutingConstraint;

	protected void showAnchors() {
		if (mySourceAnchor == null) {
			mySourceAnchor = createSourceAnchorFocus();
		}
		addFeedback(mySourceAnchor);
		if (myTargetAnchor == null) {
			myTargetAnchor = createTargetAnchorFocus();
		}
		addFeedback(myTargetAnchor);
	}

	protected void showRouting() {
		if (myRoutingConstraint == null) {
			myRoutingConstraint = new LinkedList<FeedbackBase>();
			if (getConnection().getRoutingConstraint() instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<Bendpoint> bendpoints = (List<Bendpoint>) getConnection()
						.getRoutingConstraint();
				for (int i = 1; i < bendpoints.size() - 1; i++) {
					RoutingBendpointFeedback feedback = new RoutingBendpointFeedback(
							getConnection(), i);
					myRoutingConstraint.add(feedback);
					addFeedback(feedback);
				}
			}
		}
	}

	@Override
	protected void showSelection() {
		super.showSelection();
		showAnchors();
		showRouting();
	}

	@Override
	protected void hideSelection() {
		hideRouting();
		hideAnchors();
		super.hideSelection();
	}

	protected void hideAnchors() {
		if (mySourceAnchor != null) {
			removeFeedback(mySourceAnchor);
			mySourceAnchor = null;
		}
		if (myTargetAnchor != null) {
			removeFeedback(myTargetAnchor);
			myTargetAnchor = null;
		}
	}

	protected void hideRouting() {
		if (myRoutingConstraint != null) {
			for (FeedbackBase next : myRoutingConstraint) {
				removeFeedback(next);
			}
			myRoutingConstraint = null;
		}
	}

	private SourceAnchorFeedback createSourceAnchorFocus() {
		return new SourceAnchorFeedback(getConnection());
	}

	private TargetAnchorFeedback createTargetAnchorFocus() {
		return new TargetAnchorFeedback(getConnection());
	}

	public ConnectionEditPart getHost() {
		return (ConnectionEditPart) super.getHost();
	}

	private static abstract class FeedbackBase extends Ellipse {

		private AncestorListener myAncestorListener = new AncestorListener.Stub() {

			public void ancestorMoved(IFigure ancestor) {
				revalidate();
			}
		};

		private Connection myConnection;

		public FeedbackBase(Connection connection, Color color) {
			myConnection = connection;
			setSize(10, 10);
			setLineWidth(2);
			setFill(false);
			setOpaque(true);
			if (color != null) {
				setBackgroundColor(color);
				setForegroundColor(color);
			}
		}

		protected abstract void updateFeedback();

		@Override
		public void addNotify() {
			super.addNotify();
			myConnection.addAncestorListener(myAncestorListener);
		}

		@Override
		public void removeNotify() {
			myConnection.removeAncestorListener(myAncestorListener);
			super.removeNotify();
		}

		@Override
		public void validate() {
			if (isValid()) {
				return;
			}
			updateFeedback();
		}

		public void showAt(Point point) {
			Dimension halfSize = getSize().getScaled(-0.5);
			setLocation(point.getTranslated(halfSize));
		}

		protected Connection getConnection() {
			return myConnection;
		}
	}

	private static abstract class AnchorFeedback extends FeedbackBase {
		public AnchorFeedback(Connection connection, Color color) {
			super(connection, color);
		}

		protected abstract ConnectionAnchor getAnchor(Connection connection);

		@Override
		protected void updateFeedback() {
			ConnectionAnchor anchor = getAnchor(getConnection());
			Point refPoint = anchor.getReferencePoint().getCopy();
			translateToRelative(refPoint);
			showAt(refPoint);
		}

	}

	private static class SourceAnchorFeedback extends AnchorFeedback {

		public SourceAnchorFeedback(Connection connection) {
			super(connection, ColorConstants.blue);
		}

		@Override
		protected ConnectionAnchor getAnchor(Connection connection) {
			return connection.getSourceAnchor();
		}

	}

	private static class TargetAnchorFeedback extends AnchorFeedback {

		public TargetAnchorFeedback(Connection connection) {
			super(connection, ColorConstants.red);
		}

		@Override
		protected ConnectionAnchor getAnchor(Connection connection) {
			return connection.getTargetAnchor();
		}

	}

	private static class RoutingBendpointFeedback extends FeedbackBase {
		private int myIndex;

		public RoutingBendpointFeedback(Connection connection, int idx) {
			super(connection, ColorConstants.green);
			myIndex = idx;
		}

		@Override
		protected void updateFeedback() {
			@SuppressWarnings("unchecked")
			List<Bendpoint> routing = (List<Bendpoint>) getConnection()
					.getRoutingConstraint();
			if (routing == null) {
				routing = Collections.emptyList();
			}
			Bendpoint bendpoint = null;
			if (routing.size() > myIndex) {
				bendpoint = routing.get(myIndex);
			}

			if (bendpoint == null) {
				setVisible(false);
			} else {
				setVisible(true);
				Point point = bendpoint.getLocation().getCopy();
				getConnection().translateToAbsolute(point);
				translateToRelative(point);
				showAt(point);
			}
		}
	}

}
