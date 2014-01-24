package org.sidiff.common.emf.extensions.impl;

import org.eclipse.emf.ecore.EReference;

public class EReferenceInfo {

	public static boolean isBounded(EReference reference) {
		return reference.getUpperBound() > 0;
	}

	public static boolean isRequired(EReference reference) {
		return reference.getLowerBound() > 0;
	}

	public static boolean isFixed(EReference reference) {
		return (reference.getLowerBound() == reference.getUpperBound()) && (reference.getUpperBound() > 0);
	}

	public static boolean isContainmentRelated(EReference reference) {
		return (reference.isContainment())
				|| (reference.getEOpposite() != null && reference.getEOpposite().isContainment());
	}
}
