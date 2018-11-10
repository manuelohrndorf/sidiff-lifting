package org.sidiff.remote.common.exceptions;

public abstract class RemoteExceptionWrapper extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2103717451842962087L;

	public RemoteExceptionWrapper(Exception e) {
		super(e.getMessage(), e);
	}
}
