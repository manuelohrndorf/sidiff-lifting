package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Module;

public class CreateGenerator {
	
	/**
	 * Eingehender Containment Referenztyp (falls vorhanden) 
	 */
	private EReference containmentReference;
	
	/**
	 * Kontextklasse (Kann eine Subklasse der eigentlichen Quelle des
	 * Referenztyps containmentReference sein).
	 */
	private EClass contextClass;

	public CreateGenerator(EReference containmentReference, EClass contextClass) {
		assert(containmentReference.isContainment());
		
		this.containmentReference = containmentReference;
		this.contextClass = contextClass;
	}
	
	public Module generate(){
		// TODO
		return null;
	}
	
}
