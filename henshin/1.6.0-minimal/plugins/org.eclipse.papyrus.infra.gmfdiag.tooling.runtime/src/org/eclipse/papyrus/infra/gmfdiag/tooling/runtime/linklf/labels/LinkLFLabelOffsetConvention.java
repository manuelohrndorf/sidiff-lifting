package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.labels;

import org.eclipse.draw2d.ConnectionLocator;

/**
 * This convention ensures that the changes of the link bend points do not
 * affect the position of the link labels at the ends.
 * <p/>
 * 
 * @since 3.3
 */
class LinkLFLabelOffsetConvention {

	public static int getPercentageOffsetAmongTheLineForAlignment(int alignment) {
		switch (alignment) {
		case ConnectionLocator.SOURCE:
			return 100;
		case ConnectionLocator.TARGET:
			return 0;
		case ConnectionLocator.MIDDLE:
		default:
			return 50;
		}
	}

}
