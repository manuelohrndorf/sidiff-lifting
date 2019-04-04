package org.sidiff.remote.application.connector.exception;

public class InvalidProjectInfoException extends Exception {

	private static final long serialVersionUID = 1620414145165675219L;

	public InvalidProjectInfoException() {
	}
	
	public InvalidProjectInfoException(String message) {
		super(message);
	}
	
	public InvalidProjectInfoException(Throwable exception){
		super("Invalid project info", exception);
	}
}
