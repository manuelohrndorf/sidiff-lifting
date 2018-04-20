package org.sidiff.remote.application.connector.exception;

public class ConnectionExceptionWrapper extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -417548560913850919L;

	private Exception exception;

	public ConnectionExceptionWrapper(Exception exception) {
		super();
		this.exception = exception;
	}

	@Override
	public String getMessage() {
		return this.exception.getMessage();
	}
	
	
}
