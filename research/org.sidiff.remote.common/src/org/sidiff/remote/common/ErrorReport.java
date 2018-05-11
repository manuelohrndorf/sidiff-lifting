package org.sidiff.remote.common;

import java.io.Serializable;

/**
 * 
 * @author cpietsch
 *
 */
public class ErrorReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -340102876043943841L;
	
	private String message;
	
	public ErrorReport(String message) {
		this.message = message;
	}
	
	public ErrorReport(Exception exception) {
		this.message = exception.getMessage();
	}
	
	public String getMessage() {
		return message;
	}
}
