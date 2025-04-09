package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.labels;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Extends default GMF Runtime {@link LabelEditPart} with custom
 * {@link LinkLFLabelOffsetConvention}
 * <p/>
 * 
 * @since 3.3
 */
public class LinkLFLabelEditPart extends LabelEditPart {

	public LinkLFLabelEditPart(View view) {
		super(view);
	}

	@Override
	public Point getReferencePoint() {
		if (getParent() instanceof AbstractConnectionEditPart) {
			int percents = LinkLFLabelOffsetConvention
					.getPercentageOffsetAmongTheLineForAlignment(getKeyPoint());
			return calculateRefPoint2(percents);
		}
		return getParent().getFigure().getBounds().getTopLeft();
	}

	@Override
	public AbstractGraphicalEditPart getParent() {
		return (AbstractGraphicalEditPart) super.getParent();
	}

	/**
	 * [GMFRT] make protected
	 * <p/>
	 * Calculates a point located at a percentage of the connection
	 * 
	 * @param percent
	 * @return the point
	 * @deprecated copy pasted from super class where it is private
	 */
	@Deprecated
	protected final Point calculateRefPoint2(int percent) {
		if (getParent() instanceof AbstractConnectionEditPart) {
			PointList ptList = ((Connection) ((ConnectionEditPart) getParent())
					.getFigure()).getPoints();
			Point refPoint = PointListUtilities.calculatePointRelativeToLine(
					ptList, 0, percent, true);
			return refPoint;
		} else if (getParent() instanceof GraphicalEditPart) {
			return ((AbstractGraphicalEditPart) getParent()).getFigure()
					.getBounds().getTopLeft();
		}
		return null;
	}

	public void refreshBounds() {
		if (isResizable()) {
			handleResizableRefreshBounds();
		} else {
			handleNonResizableRefreshBounds();
		}
	}

	/**
	 * [GMFRT] make protected
	 * <p/>
	 * check if the edit part had a resizable edit policy installed or not
	 * 
	 * @return true is resizable edit policy is installed otherwise false
	 * @deprecated copy pasted from super class where it is private
	 */
	@Deprecated
	protected boolean isResizable() {
		EditPolicy editPolicy = getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
		return editPolicy instanceof ResizableEditPolicy;
	}

	/**
	 * [GMFRT] make protected, then override
	 * <p/>
	 * handles non resizable label refresh bounds
	 * 
	 * @deprecated copy pasted AND MODIFIED from super class where it is
	 *             private: {@link LinkLFLabelLocator}
	 */
	@Deprecated
	protected void handleNonResizableRefreshBounds() {
		int dx = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
				.getLocation_X())).intValue();
		int dy = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
				.getLocation_Y())).intValue();
		Point offset = new Point(dx, dy);
		if (getParent() instanceof AbstractConnectionEditPart) {
			((AbstractGraphicalEditPart) getParent()).setLayoutConstraint(
					this,
					getFigure(),
					new LinkLFLabelLocator(
							((AbstractConnectionEditPart) getParent())
									.getConnectionFigure(), offset,
							getKeyPoint()));
		} else {
			getFigure().getParent().setConstraint(
					getFigure(),
					new LinkLFLabelLocator(getFigure().getParent(), offset,
							getKeyPoint()));
		}

	}

	/**
	 * [GMFRT] make protected, then override
	 * <p/>
	 * handles resizable lable refresh bounds
	 * 
	 * @deprecated copy pasted AND MODIFIED from super class where it is
	 *             private: {@link LinkLFResizableLabelLocator}
	 */
	@Deprecated
	protected void handleResizableRefreshBounds() {
		int dx = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
				.getLocation_X())).intValue();
		int dy = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
				.getLocation_Y())).intValue();
		int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
				.getSize_Width())).intValue();
		int height = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
				.getSize_Height())).intValue();
		Rectangle rectangle = new Rectangle(dx, dy, width, height);
		if (getParent() instanceof AbstractConnectionEditPart) {
			((AbstractGraphicalEditPart) getParent()).setLayoutConstraint(
					this,
					getFigure(),
					new LinkLFResizableLabelLocator(
							((AbstractConnectionEditPart) getParent())
									.getConnectionFigure(), rectangle,
							getKeyPoint()));
		} else {
			getFigure().getParent().setConstraint(
					getFigure(),
					new LinkLFResizableLabelLocator(getFigure().getParent(),
							rectangle, getKeyPoint()));
		}
	}

}
