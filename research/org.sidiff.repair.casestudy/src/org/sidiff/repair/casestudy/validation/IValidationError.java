package org.sidiff.repair.casestudy.validation;

/**
 * Basic description of a validation error.
 * 
 * @author kehrer
 */
public interface IValidationError {

	public static final String ERROR = "ERROR";
	
	public static final String WARNING = "WARNING";
	
	public Throwable getException();

	public String getMessage();

	public String getSource();
	
	public String getSeverity();
	
	public String getCharacterizingMessageFragment();
}
