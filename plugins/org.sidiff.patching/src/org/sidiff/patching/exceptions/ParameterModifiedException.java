package org.sidiff.patching.exceptions;

import java.util.Arrays;


public class ParameterModifiedException extends Exception {

	private static final long serialVersionUID = 8432836924185256207L;
	
	private String operationName;
	private String[] parameterNames;

	public ParameterModifiedException(String operationName, String... parameterNames) {
		super(operationName);
		this.parameterNames = parameterNames;
		this.operationName = operationName;
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	@Override
	public String getMessage() {
		return Arrays.toString(parameterNames) + " in "
				+ operationName + " has been modified!";
	}

}
