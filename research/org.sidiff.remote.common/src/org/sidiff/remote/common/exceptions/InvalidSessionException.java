package org.sidiff.remote.common.exceptions;

public class InvalidSessionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1620414145165675219L;

	private Exception exception;
	
	public InvalidSessionException() {
		// TODO Auto-generated constructor stub
	}
	
	public InvalidSessionException(Exception exception){
		this.exception = exception;
	}
	
	@Override
	public String getMessage() {
		return "Invalid session: " + exception == null ?  "no session file found!" : exception.getMessage();
	}
}
