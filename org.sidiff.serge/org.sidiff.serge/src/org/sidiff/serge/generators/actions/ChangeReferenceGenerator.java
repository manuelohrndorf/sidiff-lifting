package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Module;

public class ChangeReferenceGenerator {

	private EReference reference;

	private EClass contextClass;

	public ChangeReferenceGenerator(EReference reference, EClass contextClass) {
		assert(reference.getLowerBound() == 1 && reference.getUpperBound() == 1);
		
		this.reference = reference;
		this.contextClass = contextClass;
	}

	public Module generate() {
		return null;
	}
}
