package org.sidiff.remote.common.exceptions;

public abstract class RemoteExceptionWrapper extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2103717451842962087L;
	
	private String message;
	
	public RemoteExceptionWrapper(Exception e) {
		this.message = e.getMessage();
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
