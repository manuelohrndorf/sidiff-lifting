package org.sidiff.serge.exceptions;

import org.sidiff.serge.core.Configuration.OperationType;

@SuppressWarnings("serial")
public class ModuleForInverseCreationRequiredException extends Exception{

	public ModuleForInverseCreationRequiredException(OperationType opType) {
		super("The targeted OperationType " + opType.toString() + " requires a base module for inverse module creation.");
	}
}
