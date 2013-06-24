package org.sidiff.serge.exceptions;

@SuppressWarnings("serial")
public class EClassUnresolvableException extends Exception {

	public EClassUnresolvableException(String eClassName) {
		super("The EClass with the name'"+eClassName+"' couldn't be found in one of the active EPackages." +
				" \n Check for typos in SERGe config and active plugins in the Run config.");
	}
}
