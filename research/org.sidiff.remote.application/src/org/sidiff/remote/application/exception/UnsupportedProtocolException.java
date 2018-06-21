package org.sidiff.remote.application.exception;

public class UnsupportedProtocolException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnsupportedProtocolException() {
		super("Wrong protocol!");
	}
}
