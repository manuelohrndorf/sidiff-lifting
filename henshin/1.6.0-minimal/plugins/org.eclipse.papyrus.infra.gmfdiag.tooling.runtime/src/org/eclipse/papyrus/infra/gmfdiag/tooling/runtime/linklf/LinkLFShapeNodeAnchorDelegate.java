package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouter;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.editparts.LinkLFAnchorsDelegatingEditPart.ConnectionAnchorDelegate;

/**
 * Shared delegate for creation of the {@link SlidableSnapToGridAnchor} for host
 * {@link INodeEditPart}s.
 * <p/>
 * Implemented as a delegate to be easily installed to different
 * {@link NodeEditPart} sub-classes that does not share common diagram-specific
 * base class.
 * <p/>
 * 
 * @since 3.3
 */
public class LinkLFShapeNodeAnchorDelegate implements ConnectionAnchorDelegate {

	/**
	 * The {@link LinkLFShapeNodeAnchorDelegate} performs additional routing to
	 * adjust anchor's position for {@link ReconnectRequest}. In some cases,
	 * when active router is {@link OrthogonalRouter}, adjusted anchor position
	 * requires also changing the other link bendpoints. However, GEF/GMF
	 * Runtime' API does not allow to return the already recomputed bendpoints
	 * directly.
	 * <p/>
	 * The only way we found to pass them to {@link GraphicalNodeEditPolicy} is
	 * to put them into the {@link Request#getExtendedData()}.
	 * <p/>
	 * and This constant will be used as a key for the adjusted link bendpoints
	 * after rerouting.
	 */
	public static final String KEY_ROUTED_LINK_POINTS = LinkLFShapeNodeAnchorDelegate.class
			.getName() + ":RoutedPoints";

	private NodeFigure myNodeFigure;

	public LinkLFShapeNodeAnchorDelegate(NodeFigure nodeFigure) {
		myNodeFigure = nodeFigure;
	}

	public NodeFigure getNodeFigure() {
		return myNodeFigure;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		Point fromRequest = safeGetPointFromLinkRequest(request);
		ConnectionAnchor result = getNodeFigure().getSourceConnectionAnchorAt(
				fromRequest);
		if (request instanceof ReconnectRequest) {
			ReconnectRequest reqImpl = (ReconnectRequest) request;
			AbstractConnectionEditPart linkEP = (AbstractConnectionEditPart) reqImpl
					.getConnectionEditPart();
			Connection conn = linkEP.getConnectionFigure();
			ConnectionRouter router = conn.getConnectionRouter();
			PointList pointsBefore = conn.getPoints().getCopy();
			ConnectionAnchor oldSourceAnchor = conn.getSourceAnchor();
			conn.setSourceAnchor(result);
			router.route(conn);
			PointList pointsAfter = conn.getPoints().getCopy();
			Point routedLocation = pointsAfter.getFirstPoint();
			conn.translateToAbsolute(routedLocation);
			result = getNodeFigure()
					.getSourceConnectionAnchorAt(routedLocation);
			if (!Arrays.equals(pointsAfter.toIntArray(),
					pointsBefore.toIntArray())) {
				conn.setPoints(pointsBefore);
				if (router instanceof OrthogonalRouter) {
					@SuppressWarnings("unchecked")
					Map<String, Object> extData = request.getExtendedData();
					extData.put(KEY_ROUTED_LINK_POINTS, pointsAfter);
				}
			}
			conn.setSourceAnchor(oldSourceAnchor);
		}
		return result;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		Point fromRequestAbs = safeGetPointFromLinkRequest(request);
		ConnectionAnchor result = getNodeFigure().getTargetConnectionAnchorAt(
				fromRequestAbs);
		if (request instanceof ReconnectRequest) {
			ReconnectRequest reqImpl = (ReconnectRequest) request;
			AbstractConnectionEditPart linkEP = (AbstractConnectionEditPart) reqImpl
					.getConnectionEditPart();
			Connection conn = linkEP.getConnectionFigure();
			ConnectionRouter router = conn.getConnectionRouter();
			PointList pointsBefore = conn.getPoints().getCopy();
			ConnectionAnchor oldTargetAnchor = conn.getTargetAnchor();
			conn.setTargetAnchor(result);
			router.route(conn);
			PointList pointsAfter = conn.getPoints().getCopy();
			Point routedTarget = pointsAfter.getLastPoint();
			conn.translateToAbsolute(routedTarget);
			result = getNodeFigure().getTargetConnectionAnchorAt(routedTarget);
			if (!Arrays.equals(pointsAfter.toIntArray(),
					pointsBefore.toIntArray())) {
				conn.setPoints(pointsBefore);
				if (router instanceof OrthogonalRouter) {
					@SuppressWarnings("unchecked")
					Map<String, Object> extData = request.getExtendedData();
					extData.put(KEY_ROUTED_LINK_POINTS, pointsAfter);
				}
			}
			conn.setTargetAnchor(oldTargetAnchor);
		}
		return result;
	}

	private Point safeGetPointFromLinkRequest(Request request) {
		Point result = null;
		if (request instanceof DropRequest) {
			result = ((DropRequest) request).getLocation();
		}
		// additional copy for ReconnectRequest, dont know why,
		// but see, e.g, ShapeNodeEditPart#getSourceConnectionAnchor
		if (result != null && request instanceof ReconnectRequest) {
			result = result.getCopy();
		}
		return result;
	}

}
