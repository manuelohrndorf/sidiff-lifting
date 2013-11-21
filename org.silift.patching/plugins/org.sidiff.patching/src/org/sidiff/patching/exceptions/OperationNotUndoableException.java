package org.sidiff.patching.exceptions;

public class OperationNotUndoableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4980559897849806738L;

	public OperationNotUndoableException(String operationName) {
		super(operationName);
	}

}
