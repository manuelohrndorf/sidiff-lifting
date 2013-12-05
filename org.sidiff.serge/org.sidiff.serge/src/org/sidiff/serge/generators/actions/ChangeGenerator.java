package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Module;

public class ChangeGenerator {

	private EReference reference;

	private EClass contextClass;

	public ChangeGenerator(EReference reference, EClass contextClass) {
		assert(reference.getLowerBound() == 1 && reference.getUpperBound() == 1);
		
		this.reference = reference;
		this.contextClass = contextClass;
	}

	public Module generate() {
		return null;
	}
}
