package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Module;

public class CreateGenerator {
	
	/**
	 * Incoming containment reference type (if available) 
	 */
	private EReference containmentReference;
	
	/**
	 * Context class (Can be a sub type of the original type set in a reference).
	 */
	private EClassifier contextClassifier;

	public CreateGenerator(EReference containmentReference, EClassifier contextClassifier) {
		assert(containmentReference.isContainment());
		
		this.containmentReference = containmentReference;
		this.contextClassifier = contextClassifier;
	}
	
	public Module generate(){
		// TODO
		return null;
	}
	
}
