package org.sidiff.patching.exceptions;

public class PatchNotExecuteableException extends OperationNotExecutableException {
	
	private static final long serialVersionUID = 8227489893713685566L;

	public PatchNotExecuteableException(String message) {
		super(message);
	}

}
