package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionAnchorsCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IBorderItemLocator;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;

/**
 * This edit policy adjusts anchors for links from and to host
 * {@link IBorderItemEditPart}. Adjustment ensures that the anchors are always
 * located on the side which is parallel to the actual side of the parent this
 * border item is affixed to.
 * <p/>
 * 
 * @since 3.3
 */
public class AdjustBorderItemAnchorsEditPolicy extends
		AdjustAbsoluteBendpointsEditPolicyBase {

	/**
	 * Default role for registering this edit policy.
	 * <p/>
	 * The value is prefixed by class FQN in order to avoid conflicts, but the
	 * literal should NOT be used anywhere.
	 */
	public static final String ROLE = AdjustBorderItemAnchorsEditPolicy.class
			.getName() + ":Role";

	@Override
	protected Command getAdjustLinksCommand(ChangeBoundsRequest req) {
		if (getHost() instanceof IBorderItemEditPart
				&& getHost() instanceof INodeEditPart) {
			return getAdjustAnchorsCommand(req);
		}
		return null;
	}

	protected Command getAdjustAnchorsCommand(ChangeBoundsRequest req) {
		IBorderItemEditPart host = (IBorderItemEditPart) getHost();
		final IBorderItemLocator locator = host.getBorderItemLocator();
		if (locator == null) {
			return null;
		}

		Rectangle bounds;
		if (host.getFigure() instanceof HandleBounds) {
			bounds = ((HandleBounds) host.getFigure()).getHandleBounds();
		} else {
			bounds = host.getFigure().getBounds();
		}
		PrecisionRectangle rect = new PrecisionRectangle(bounds);
		getHostFigure().translateToAbsolute(rect);
		rect.translate(req.getMoveDelta());
		rect.resize(req.getSizeDelta());

		getHostFigure().translateToRelative(rect);
		Rectangle realLocation = locator.getValidLocation(rect.getCopy(),
				host.getFigure());

		int projectedSide = BorderItemLocator.findClosestSideOfParent(
				realLocation, ((GraphicalEditPart) host.getParent())
						.getFigure().getBounds());
		int currentSide = locator.getCurrentSideOfParent();

		int curIndex = getIndexForSide(currentSide);
		int projectedIndex = getIndexForSide(projectedSide);

		if ((projectedSide & currentSide) != 0) {
			return null;
		}

		int rotation = projectedIndex - curIndex;
		if (rotation < 0) {
			rotation += 4;
		}
		if (rotation == 0) {
			// weird
			return null;
		}

		ICommand result = null;
		TransactionalEditingDomain domain = getHost().getEditingDomain();
		for (Object next : getHost().getSourceConnections()) {
			if (next instanceof ConnectionEditPart) {
				ConnectionEditPart nextLink = (ConnectionEditPart) next;
				ConnectionAnchor anchor = nextLink.getConnectionFigure()
						.getSourceAnchor();
				if (anchor == null) {
					continue;
				}
				PrecisionPoint newRefPoint = rotateAnchorLocation(anchor,
						rotation);
				String newTerminal = composeTerminalString(newRefPoint);

				SetConnectionAnchorsCommand nextCommand = new SetConnectionAnchorsCommand(
						domain, "Adjusting source anchors");
				nextCommand.setEdgeAdaptor(new EObjectAdapter(nextLink
						.getNotationView()));
				nextCommand.setNewSourceTerminal(newTerminal);

				result = result == null ? nextCommand : result
						.compose(nextCommand);
			}
		}

		for (Object next : getHost().getTargetConnections()) {
			if (next instanceof ConnectionEditPart) {
				ConnectionEditPart nextLink = (ConnectionEditPart) next;
				ConnectionAnchor anchor = nextLink.getConnectionFigure()
						.getTargetAnchor();
				if (anchor == null) {
					continue;
				}
				PrecisionPoint newRefPoint = rotateAnchorLocation(anchor,
						rotation);
				String newTerminal = composeTerminalString(newRefPoint);

				SetConnectionAnchorsCommand nextCommand = new SetConnectionAnchorsCommand(
						domain, "Adjusting target anchors");
				nextCommand.setEdgeAdaptor(new EObjectAdapter(nextLink
						.getNotationView()));
				nextCommand.setNewTargetTerminal(newTerminal);

				result = result == null ? nextCommand : result
						.compose(nextCommand);
			}
		}

		return result == null ? null : new ICommandProxy(result);
	}

	private static int getIndexForSide(int side) {
		if (hasBits(side, PositionConstants.NORTH)) {
			return 0;
		}
		if (hasBits(side, PositionConstants.EAST)) {
			return 1;
		}
		if (hasBits(side, PositionConstants.SOUTH)) {
			return 2;
		}
		if (hasBits(side, PositionConstants.WEST)) {
			return 3;
		}
		return 0;
	}

	protected static String position2string(int position) {
		if (position == PositionConstants.NONE) {
			return "NONE";
		}
		StringBuffer result = new StringBuffer();
		if (hasBits(position, PositionConstants.NORTH)) {
			result.append("N");
		}
		if (hasBits(position, PositionConstants.SOUTH)) {
			result.append("S");
		}
		if (hasBits(position, PositionConstants.WEST)) {
			result.append("W");
		}
		if (hasBits(position, PositionConstants.EAST)) {
			result.append("E");
		}

		return result.toString();
	}

	protected static boolean hasBits(int value, int mask) {
		return ((value & mask) != 0);
	}

	protected PrecisionPoint rotateAnchorLocation(ConnectionAnchor anchor,
			int quarters) {
		String terminal = ((BaseSlidableAnchor) anchor).getTerminal();
		PrecisionPoint result;
		if (terminal.length() == 0) {
			result = new PrecisionPoint(0.5, 0.5);
		} else {
			result = BaseSlidableAnchor
				.parseTerminalString(terminal);
		}
		for (int i = 0; i < quarters; i++) {
			double newX = 1. - result.preciseY();
			double newY = result.preciseX();
			result.setPreciseLocation(newX, newY);
		}
		return result;
	}

	/**
	 * [GMFRT] make protected in {@link BaseSlidableAnchor}
	 * <p/>
	 * Creates a terminal string for any reference point passed in the format
	 * understandable by slidable anchors
	 * 
	 * @param p
	 *            - a <Code>PrecisionPoint</Code> that must be represented as a
	 *            unique <Code>String</Code>, namely as "(preciseX,preciseY)"
	 * @return <code>String</code> terminal composed from specified
	 *         <code>PrecisionPoint</code>
	 * @deprecated copy pasted from {@link BaseSlidableAnchor}
	 */
	@Deprecated
	private String composeTerminalString(PrecisionPoint p) {
		StringBuffer s = new StringBuffer(24);
		s.append('('); // 1 char
		s.append(p.preciseX()); // 10 chars
		s.append(','); // 1 char
		s.append(p.preciseY()); // 10 chars
		s.append(')'); // 1 char
		return s.toString(); // 24 chars max (+1 for safety, i.e. for string
								// termination)
	}

}
