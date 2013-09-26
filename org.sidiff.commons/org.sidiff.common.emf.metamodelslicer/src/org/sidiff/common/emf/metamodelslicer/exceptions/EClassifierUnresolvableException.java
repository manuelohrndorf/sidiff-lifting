package org.sidiff.common.emf.metamodelslicer.exceptions;

@SuppressWarnings("serial")

public class EClassifierUnresolvableException extends Exception {
	
	public EClassifierUnresolvableException(String eClassifierName) {
		super("The EClass with the name'"+eClassifierName+"' couldn't be found in one of the active EPackages." +
				" \n Check for typos in ModelSlicer config and active plugins in the Run config.");
	}
}
