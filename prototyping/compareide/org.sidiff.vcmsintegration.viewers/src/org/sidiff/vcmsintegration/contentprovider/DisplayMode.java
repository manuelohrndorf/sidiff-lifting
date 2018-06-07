package org.sidiff.vcmsintegration.contentprovider;

import org.eclipse.jface.viewers.TreeViewer;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * A display mode defines which type of input the
 * {@link SiLiftStructuredViewerContentProvider} provides to the
 * {@link TreeViewer}.
 * 
 * @author Adrian Bingener
 *
 */
public enum DisplayMode {
	/**
	 * When using this mode, the {@link SiLiftStructuredViewerContentProvider}
	 * will derive a {@link SymmetricDifference} and display it in the
	 * {@link TreeViewer}.
	 */
	SYMMETRIC_DIFFERENCE {
		@Override
		public String getName() {
			return "Symmetric Difference";
		}
	},
	/**
	 * When using this mode, the {@link SiLiftStructuredViewerContentProvider}
	 * will derive a {@link AsymmetricDifference} and display it in the
	 * {@link TreeViewer}.
	 */
	ASYMMETRIC_DIFFERENCE {
		@Override
		public String getName() {
			return "Asymmetric Difference";
		}
	};

	/**
	 * Returns the default display mode that should be used if no dispay mode
	 * was specified.
	 * 
	 * @return The default display mode
	 */
	public static DisplayMode getDefault() {
		return ASYMMETRIC_DIFFERENCE;
	}

	/**
	 * Returns the name of the display mode.
	 * 
	 * @return the name of the display mode
	 */
	public abstract String getName();
}
