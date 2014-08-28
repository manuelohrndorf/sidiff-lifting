package org.sidiff.editrule.generator.serge.exceptions;

import org.eclipse.emf.ecore.EClass;

@SuppressWarnings("serial")
public class EAttributeNotFoundException extends Exception {

	public EAttributeNotFoundException(String eAttribute, EClass eClass) {
		super("The EClass with the name'"+eClass.getName()+"' does not contain any EAttribute with the name '"+eAttribute+"'.");
	}
}
