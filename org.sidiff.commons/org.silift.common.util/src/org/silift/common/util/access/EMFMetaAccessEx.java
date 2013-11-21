package org.silift.common.util.access;

import org.eclipse.emf.ecore.EStructuralFeature;

public class EMFMetaAccessEx {
	
	/**
	 * (Meta-) attributes which are not changeable, derived or transient are
	 * unconsidered while model comparison.
	 * 
	 * @param structualFeatureType
	 *            The attribute to test.
	 * @return <code>true</code> if the attribute is unconsidered while model
	 *         comparison; <code>false</code> otherwise;
	 */
	public static boolean isUnconsideredStructualFeature(EStructuralFeature structualFeatureType) {
		if ((structualFeatureType.isChangeable() == false)
				|| (structualFeatureType.isDerived() == true)
				|| (structualFeatureType.isTransient() == true)) {
			return true;
		} else {
			return false;
		}
	}
}
