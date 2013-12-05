package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

public class SetReferenceGenerator {

	private EReference reference;

	private EClass srcType;

	private EClass tgtType;

	public SetReferenceGenerator(EReference reference, EClass srcType,
			EClass tgtType) {
		
		assert (!reference.isContainment() && reference.getLowerBound() == 0 && reference
				.getUpperBound() == 1);

		this.reference = reference;
		this.srcType = srcType;
		this.tgtType = tgtType;
	}

}
