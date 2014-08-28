package org.sidiff.editrule.generator.exceptions;

import org.sidiff.editrule.generator.types.OperationType;

@SuppressWarnings("serial")
public class ModuleForInverseCreationRequiredException extends Exception{

	public ModuleForInverseCreationRequiredException(OperationType opType) {
		super("The targeted OperationType " + opType.toString() + " requires a base module for inverse module creation.");
	}
}
