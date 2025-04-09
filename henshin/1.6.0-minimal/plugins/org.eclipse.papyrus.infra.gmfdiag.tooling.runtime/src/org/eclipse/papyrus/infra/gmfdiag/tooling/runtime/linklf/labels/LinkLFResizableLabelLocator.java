package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.labels;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.internal.figures.ResizableLabelLocator;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;

/**
 * Extends default GMF Runtime {@link ResizableLabelLocator} with custom
 * {@link LinkLFLabelOffsetConvention}
 * <p/>
 * 
 * @since 3.3
 */
public class LinkLFResizableLabelLocator extends ResizableLabelLocator {

	public LinkLFResizableLabelLocator(IFigure parent, Rectangle bounds,
			int alignment) {
		super(parent, bounds, alignment);
	}

	@Override
	protected Point getReferencePoint() {
		if (parent instanceof Connection) {
			PointList ptList = ((Connection) parent).getPoints();
			int percents = LinkLFLabelOffsetConvention
					.getPercentageOffsetAmongTheLineForAlignment(getAlignment());
			return PointListUtilities.calculatePointRelativeToLine(ptList, 0,
					percents, true);
		} else {
			return parent.getBounds().getLocation();
		}
	}

}
