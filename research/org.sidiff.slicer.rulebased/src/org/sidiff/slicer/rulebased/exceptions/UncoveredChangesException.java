package org.sidiff.slicer.rulebased.exceptions;

public class UncoveredChangesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4869064693810354074L;

	@Override
	public String getMessage() {
		return "Corrupt model or incomplete rule set!";
	}
	
	

}
