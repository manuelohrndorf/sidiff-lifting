package org.sidiff.remote.application.connector.exception;

public class InvalidProjectInfoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1620414145165675219L;

	private Exception exception;
	
	public InvalidProjectInfoException() {
		// TODO Auto-generated constructor stub
	}
	
	public InvalidProjectInfoException(Exception exception){
		this.exception = exception;
	}
	
	@Override
	public String getMessage() {
		return "Invalid project info: " + exception == null ?  "no project info file found!" : exception.getMessage();
	}
}
