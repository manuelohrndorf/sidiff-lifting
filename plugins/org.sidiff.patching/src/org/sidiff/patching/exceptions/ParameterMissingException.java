package org.sidiff.patching.exceptions;

import java.util.Collection;

public class ParameterMissingException extends Exception {

	private static final long serialVersionUID = -3421800600604095093L;

	private String operationName;
	private Collection<String> parameterNames;

	public ParameterMissingException(String operationName, Collection<String> parameterNames) {
		super(operationName);
		this.parameterNames = parameterNames;
		this.operationName = operationName;
	}

	public Collection<String> getParameterNames() {
		return parameterNames;
	}

	@Override
	public String getMessage() {
		return parameterNames + " not set in " + operationName + ".";
	}
}
