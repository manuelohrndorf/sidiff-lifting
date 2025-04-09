package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.labels;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.figures.LabelLocator;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;

/**
 * Extends default {@link LabelLocator} with different
 * {@link LinkLFLabelOffsetConvention}
 * <p/>
 * 
 * @since 3.3
 */
public class LinkLFLabelLocator extends LabelLocator {

	public LinkLFLabelLocator(IFigure parent, Rectangle bounds, int alignment) {
		super(parent, bounds, alignment);
	}

	public LinkLFLabelLocator(IFigure parent, Point offSet, int alignment) {
		super(parent, offSet, alignment);
	}

	/**
	 * Returns the reference point for the locator.
	 * 
	 * @return the reference point
	 */
	protected Point getReferencePoint() {
		if (parent instanceof Connection) {
			PointList ptList = ((Connection) parent).getPoints();
			return PointListUtilities.calculatePointRelativeToLine(ptList, 0,
					getLocationEx(), true);
		} else {
			return parent.getBounds().getLocation();
		}
	}

	protected int getLocationEx() {
		return LinkLFLabelOffsetConvention
				.getPercentageOffsetAmongTheLineForAlignment(getAlignment());
	}

}
