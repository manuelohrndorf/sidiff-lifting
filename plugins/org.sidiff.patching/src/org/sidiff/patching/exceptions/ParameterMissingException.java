package org.sidiff.patching.exceptions;

import java.util.Arrays;

public class ParameterMissingException extends Exception {
	private static final long serialVersionUID = -3421800600604095093L;
	private String operationName;
	private String[] parameterNames;

	public ParameterMissingException(String operationName, String... parameterNames) {
		super(operationName);
		this.parameterNames = parameterNames;
		this.operationName = operationName;
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	@Override
	public String getMessage() {
		return Arrays.toString(parameterNames) + " not set in " + operationName + "!";
	}

}
