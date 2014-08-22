package org.sidiff.editrule.generator.exceptions;

@SuppressWarnings("serial")
public class OperationTypeNotImplementedException extends Exception{

	public OperationTypeNotImplementedException(String opTypeName) {
		super("The operation type '"+opTypeName+"' is not implemented completely");
	}
}
