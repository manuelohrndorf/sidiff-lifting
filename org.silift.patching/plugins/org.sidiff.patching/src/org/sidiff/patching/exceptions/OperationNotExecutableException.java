package org.sidiff.patching.exceptions;

public class OperationNotExecutableException extends Exception {
	private static final long serialVersionUID = -9089308748645477009L;

	public OperationNotExecutableException(String operationName) {
		super(operationName);
	}

}
