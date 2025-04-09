package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.editparts;

import org.eclipse.draw2d.Connection;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConnectionBendpointEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.editpolicies.ConnectionLineSegEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.editpolicies.TreeConnectionBendpointEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.util.EditPartUtil;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.ForestRouter;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouter;
import org.eclipse.gmf.runtime.gef.ui.internal.l10n.Cursors;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.View;

/**
 * This class is a workaround for a bunch of private methods dealing with the
 * default edit policies for the {@link ConnectionNodeEditPart}
 * <p/>
 * This class is expected to become obsolete and removed in the Mars+1 release,
 * once the corresponding methods made protected in the super-class
 * <p/>
 * [GMFR make protected]
 * 
 * @see OverridableConnectionNodeEditPart#installBendpointEditPolicy()
 * @since 3.3
 */
public abstract class OverridableConnectionNodeEditPart extends ConnectionNodeEditPart {

	public OverridableConnectionNodeEditPart(View view) {
		super(view);
	}

	/**
	 * This method is private in {@link ConnectionEditPart} while being called
	 * from the methods which have to be overridden in the LinkLF framework.
	 * <p/>
	 * This class does NOT change the logic from the super class, it only allows
	 * to override it in subclasses
	 */
	protected void installBendpointEditPolicy() {
		if (getConnectionFigure().getConnectionRouter() instanceof ForestRouter) {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new TreeConnectionBendpointEditPolicy());
		} else if (getConnectionFigure().getConnectionRouter() instanceof OrthogonalRouter) {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new ConnectionLineSegEditPolicy());
		} else {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new ConnectionBendpointEditPolicy());
		}
		refreshConnectionCursor();
	}

	/**
	 * This method is part of the private
	 * {@link ConnectionEditPart#installBendpointEditPolicy}, extracted to
	 * simplify the overriding code in the sub-classes.
	 * 
	 * @see OverridableConnectionNodeEditPart#installBendpointEditPolicy()
	 */
	protected void refreshConnectionCursor() {
		EditPartUtil.synchronizeRunnableToMainThread(this, new Runnable() {

			public void run() {
				if (getConnectionFigure().getConnectionRouter() instanceof ForestRouter) {
					getConnectionFigure().setCursor(Cursors.CURSOR_SEG_MOVE);
				} else if (getConnectionFigure().getConnectionRouter() instanceof OrthogonalRouter) {
					getConnectionFigure().setCursor(Cursors.CURSOR_SEG_MOVE);
				} else {
					getConnectionFigure().setCursor(Cursors.CURSOR_SEG_ADD);
				}
			};
		});
	}

	/**
	 * This method is copy-pasted from In contrast to super-class, this code
	 * calls the local version of {@link #installBendpointEditPolicy()} which
	 * can be overridden in sub-class
	 */
	@Override
	protected void refreshRouterChange() {
		refreshBendpoints();
		installBendpointEditPolicy();
	}

	/**
	 * This method is copy-pasted from the
	 * {@link ConnectionEditPart#refreshRoutingStyles}.
	 * <p/>
	 * In contrast to super-class, this code calls the local version of
	 * {@link #installBendpointEditPolicy()} which can be overridden in
	 * sub-class.
	 */
	@Override
	protected void refreshRoutingStyles() {
		Connection connection = getConnectionFigure();
		if (!(connection instanceof PolylineConnectionEx)) {
			return;
		}

		PolylineConnectionEx poly = (PolylineConnectionEx) connection;

		RoutingStyle style = (RoutingStyle) ((View) getModel())
				.getStyle(NotationPackage.Literals.ROUTING_STYLE);
		if (style != null) {

			boolean closestDistance = style.isClosestDistance();
			boolean avoidObstruction = style.isAvoidObstructions();

			poly.setRoutingStyles(closestDistance, avoidObstruction);

			if (avoidObstruction) {
				installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, null);
			} else {
				installBendpointEditPolicy();
			}
		}
	}

}
