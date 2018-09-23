package org.sidiff.slicer.rulebased.exceptions;

public class EmptySlicingCriteriaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8042009836260252129L;

	@Override
	public String getMessage() {
		return "Empty slicing criteria.";
	}
}
