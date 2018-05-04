package org.sidiff.remote.application.connector.exception;

public class ConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -417548560913850919L;

	private Exception exception;

	public ConnectionException(Exception exception) {
		super();
		this.exception = exception;
	}

	@Override
	public String getMessage() {
		return this.exception.getMessage();
	}
	
	public Exception getException() {
		return exception;
	}
	
}
