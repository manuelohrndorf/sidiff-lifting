package org.sidiff.serge.exceptions;

@SuppressWarnings("serial")
public class OperationTypeNotImplementedException extends Exception{

	public OperationTypeNotImplementedException(String opTypeName) {
		super("The operation type '"+opTypeName+"' is not implemented completely");
	}
}
