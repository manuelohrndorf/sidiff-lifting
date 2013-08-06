package org.sidiff.patching.exceptions;

public class HenshinExecutionFailedException extends OperationNotExecutableException {
	private static final long serialVersionUID = 3948398636740804667L;

	
	public HenshinExecutionFailedException(String operationName) {
		super(operationName);
		
	}


}
