package org.sidiff.patching.exceptions;

import org.sidiff.common.exceptions.SiDiffException;

public class OperationNotExecutableException extends SiDiffException {

	private static final long serialVersionUID = -9089308748645477009L;

	public OperationNotExecutableException(String operationName) {
		super("Operation could not be executed: " + operationName, "Could not execute operation");
	}

}
