package org.sidiff.slicer.rulebased.exceptions;

public class EmtpyModelSliceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8048327190263617761L;
	
	@Override
	public String getMessage() {
		return "Empty model slice";
	}

}
