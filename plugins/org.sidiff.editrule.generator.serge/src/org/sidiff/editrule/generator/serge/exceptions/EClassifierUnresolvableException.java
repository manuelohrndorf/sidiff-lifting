package org.sidiff.editrule.generator.serge.exceptions;

@SuppressWarnings("serial")
public class EClassifierUnresolvableException extends Exception {

	public EClassifierUnresolvableException(String eClassifierName) {
		super("The EClassifier with the name'"+eClassifierName+"' couldn't be found in one of the active EPackages." +
				" \n Check for typos in SERGe config and active plugins in the Run config.");
	}
}
