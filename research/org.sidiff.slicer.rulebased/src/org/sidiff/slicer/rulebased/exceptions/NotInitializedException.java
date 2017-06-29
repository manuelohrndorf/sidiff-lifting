package org.sidiff.slicer.rulebased.exceptions;

public class NotInitializedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2736913208877632532L;

	@Override
	public String getMessage() {
		return "Slicer is not initialized!";
	}

}
