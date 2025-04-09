package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.XYAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy;
import org.eclipse.gef.handles.ConnectionEndpointHandle;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * A selection handle policy for placing handles at the two ends of a
 * ConnectionEditPart. All ConnectionEditParts should have one of these, even if
 * the ends of the connection aren't draggable, because this is the primary
 * SelectionEditPolicy for showing focus.
 * <P>
 * A connection can receive focus but not selection by pressing
 * <code>Control+/</code> on the keyboard.
 * 
 * @since 3.3
 */
public class LinksLFConnectionEndPointEditPolicy extends
		SelectionHandlesEditPolicy {

	private ConnectionAnchor originalAnchor;

	private FeedbackHelperEx feedbackHelper;

	private ConnectionFocus focus;

	class ConnectionFocus extends Polygon implements PropertyChangeListener {

		AncestorListener ancestorListener = new AncestorListener.Stub() {

			public void ancestorMoved(IFigure ancestor) {
				revalidate();
			}
		};

		ConnectionFocus() {
			setFill(false);
			setForegroundColor(ColorConstants.red);
			setXOR(true);
			setOutline(true);
		}

		public void addNotify() {
			super.addNotify();
			getConnection().addPropertyChangeListener(
					Connection.PROPERTY_POINTS, this);
			getConnection().addAncestorListener(ancestorListener);
		}

		protected void outlineShape(Graphics g) {
			g.setLineDash(new int[] { 1, 1 });
			super.outlineShape(g);
		}

		public void propertyChange(PropertyChangeEvent evt) {
			revalidate();
		}

		public void removeNotify() {
			getConnection().removePropertyChangeListener(
					Connection.PROPERTY_POINTS, this);
			getConnection().removeAncestorListener(ancestorListener);
			super.removeNotify();
		}

		public void validate() {
			if (isValid())
				return;
			PointList points = getConnection().getPoints().getCopy();
			getConnection().translateToAbsolute(points);
			points = StrokePointListEx.strokeList(points, 5);
			translateToRelative(points);
			setPoints(points);
		}
	}

	/**
	 * @see org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy#createSelectionHandles()
	 */
	protected List createSelectionHandles() {
		List list = new ArrayList();
		list.add(new ConnectionEndpointHandle((ConnectionEditPart) getHost(),
				ConnectionLocator.SOURCE));
		list.add(new ConnectionEndpointHandle((ConnectionEditPart) getHost(),
				ConnectionLocator.TARGET));
		return list;
	}

	/**
	 * Erases connection move feedback. This method is called when a
	 * ReconnectRequest is received.
	 * 
	 * @param request
	 *            the reconnect request.
	 */
	protected void eraseConnectionMoveFeedback(ReconnectRequest request) {
		if (originalAnchor == null)
			return;
		if (request.isMovingStartAnchor())
			getConnection().setSourceAnchor(originalAnchor);
		else
			getConnection().setTargetAnchor(originalAnchor);
		originalAnchor = null;
		feedbackHelper = null;
	}

	/**
	 * @see org.eclipse.gef.EditPolicy#eraseSourceFeedback(org.eclipse.gef.Request)
	 */
	public void eraseSourceFeedback(Request request) {
		if (REQ_RECONNECT_TARGET.equals(request.getType())
				|| REQ_RECONNECT_SOURCE.equals(request.getType()))
			eraseConnectionMoveFeedback((ReconnectRequest) request);
	}

	/**
	 * @see org.eclipse.gef.EditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	public Command getCommand(Request request) {
		return null;
	}

	/**
	 * Convenience method for obtaining the host's <code>Connection</code>
	 * figure.
	 * 
	 * @return the Connection figure
	 */
	protected Connection getConnection() {
		return (Connection) ((GraphicalEditPart) getHost()).getFigure();
	}

	/**
	 * Lazily creates and returns the feedback helper for the given request. The
	 * helper will be configured as either moving the source or target end of
	 * the connection.
	 * 
	 * @param request
	 *            the reconnect request
	 * @return the feedback helper
	 */
	protected FeedbackHelperEx getFeedbackHelper(ReconnectRequest request) {
		if (feedbackHelper == null) {
			feedbackHelper = new FeedbackHelperEx();
			feedbackHelper.setConnection(getConnection());
			feedbackHelper.setMovingStartAnchor(request.isMovingStartAnchor());
		}
		return feedbackHelper;
	}

	/**
	 * Hides the focus indicator. The focus indicator is a dotted outline around
	 * the connection.
	 * 
	 * @see #showFocus()
	 * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#hideFocus()
	 */
	protected void hideFocus() {
		if (focus != null) {
			removeFeedback(focus);
			focus = null;
		}
	}

	/**
	 * Shows or updates connection move feedback. Called whenever a show
	 * feedback request is received for reconnection.
	 * 
	 * @param request
	 *            the reconnect request
	 */
	protected void showConnectionMoveFeedback(ReconnectRequest request) {
		NodeEditPart node = null;
		if (request.getTarget() instanceof NodeEditPart)
			node = (NodeEditPart) request.getTarget();
		if (originalAnchor == null) {
			if (request.isMovingStartAnchor())
				originalAnchor = getConnection().getSourceAnchor();
			else
				originalAnchor = getConnection().getTargetAnchor();
		}
		ConnectionAnchor anchor = null;
		if (node != null) {
			if (request.isMovingStartAnchor())
				anchor = node.getSourceConnectionAnchor(request);
			else
				anchor = node.getTargetConnectionAnchor(request);
		}
		FeedbackHelperEx helper = getFeedbackHelper(request);
		helper.update(anchor, request.getLocation());
	}

	/**
	 * Shows focus around the connection.
	 * 
	 * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#showFocus()
	 */
	protected void showFocus() {
		if (focus == null) {
			focus = new ConnectionFocus();
			addFeedback(focus);
		}
	}

	/**
	 * @see org.eclipse.gef.EditPolicy#showSourceFeedback(org.eclipse.gef.Request)
	 */
	public void showSourceFeedback(Request request) {
		if (REQ_RECONNECT_SOURCE.equals(request.getType())
				|| REQ_RECONNECT_TARGET.equals(request.getType()))
			showConnectionMoveFeedback((ReconnectRequest) request);
	}

	public static class StrokePointListEx {

		static float segment[] = new float[6];

		static PointList strokeList(PointList list, int offset) {
			GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO);

			Point p = list.getPoint(0);
			path.moveTo(p.x, p.y);
			for (int i = 1; i < list.size(); i++)
				path.lineTo((p = list.getPoint(i)).x, p.y);
			BasicStroke stroke = new BasicStroke(offset * 2,
					BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f);
			Shape stroked = stroke.createStrokedShape(path);
			Area area = new Area(stroked);
			PathIterator iter = area.getPathIterator(null, 10.0f);

			PointList currentSegment = null;
			PointList result = null;
			int largestSegmentSize = 0;

			while (!iter.isDone()) {
				if (currentSegment == null)
					currentSegment = new PointList(list.size() * 2);
				int type = iter.currentSegment(segment);
				currentSegment.addPoint(Math.round(segment[0]),
						Math.round(segment[1]));
				iter.next();
				if (type == PathIterator.SEG_CLOSE) {
					if (currentSegment.size() > largestSegmentSize) {
						result = currentSegment;
						largestSegmentSize = currentSegment.size();
						currentSegment = null;
					}
				}
			}

			return result;
		}

	}

	/**
	 * Helps display connection feedback during drags of the connection ends.
	 * This class is used internally by the provided EditPolicy implementation.
	 * 
	 * @author hudsonr
	 */
	public class FeedbackHelperEx {

		private Connection connection;

		private XYAnchorEx dummyAnchor;

		private boolean isMovingStartAnchor = false;

		/**
		 * Constructs a new FeedbackHelper.
		 */
		public FeedbackHelperEx() {
			dummyAnchor = new XYAnchorEx(new Point(10, 10));
		}

		/**
		 * Returns the connection being used to show feedback.
		 * 
		 * @return the connection
		 */
		protected Connection getConnection() {
			return connection;
		}

		/**
		 * Returns true is the feedback is moving the source anchor
		 * 
		 * @return <code>true</code> if moving start
		 */
		protected boolean isMovingStartAnchor() {
			return isMovingStartAnchor;
		}

		/**
		 * Sets the connection.
		 * 
		 * @param c
		 *            connection
		 */
		public void setConnection(Connection c) {
			connection = c;
		}

		/**
		 * Sets if moving start of connection.
		 * 
		 * @param value
		 *            <code>true</code> if the starts is being moved
		 */
		public void setMovingStartAnchor(boolean value) {
			isMovingStartAnchor = value;
		}

		/**
		 * Sets the anchor for the end being moved.
		 * 
		 * @param anchor
		 *            the new anchor
		 */
		protected void setAnchor(ConnectionAnchor anchor) {
			if (isMovingStartAnchor())
				getConnection().setSourceAnchor(anchor);
			else
				getConnection().setTargetAnchor(anchor);
		}

		/**
		 * Updates the feedback based on the given anchor or point. The anchor
		 * is used unless <code>null</code>. The point is used when there is no
		 * anchor.
		 * 
		 * @param anchor
		 *            <code>null</code> or an anchor
		 * @param p
		 *            the point to use when there is no anchor
		 */
		public void update(ConnectionAnchor anchor, Point p) {
			if (anchor != null)
				setAnchor(anchor);
			else {
				dummyAnchor.setLocation(p);
				setAnchor(dummyAnchor);
			}
		}

	}

	public static class XYAnchorEx extends XYAnchor {

		public XYAnchorEx(Point p) {
			super(p);
		}

		private IFigure myOwner;

		@Override
		public IFigure getOwner() {
			if (myOwner == null) {
				myOwner = new Figure();
				myOwner.setBounds(new Rectangle(getReferencePoint().x - 1,
						getReferencePoint().y - 1, 2, 2));
			}
			return myOwner;
		}
	}

}
