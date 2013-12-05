package org.sidiff.serge.generators.actions;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Module;

public class AddGenerator {

	/**
	 * Ausgehender non-containment Referenztyp
	 */
	private EReference outReference;

	/**
	 * Kontextklasse (Kann eine Subklasse der eigentlichen Quelle des
	 * Referenztyps outReference sein).
	 */
	private EClass contextClass;

	public AddGenerator(EReference outReference, EClass contextClass) {
		super();
		this.outReference = outReference;
		this.contextClass = contextClass;
	}
	
	public Module generate(){
		// TODO
		return null;
	}
}
