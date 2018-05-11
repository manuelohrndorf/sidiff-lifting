package org.sidiff.remote.application.exception;

public class AuthenticationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8522413515076351376L;

	@Override
	public String getMessage() {
		return "Authentication failed";
	}
}
