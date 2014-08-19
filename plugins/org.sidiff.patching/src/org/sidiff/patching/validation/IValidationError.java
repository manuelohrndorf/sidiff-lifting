package org.sidiff.patching.validation;

/**
 * Basic description of a validation error.
 * 
 * @author kehrer
 */
public interface IValidationError {

	public Throwable getException();

	public String getMessage();

	public String getSource();
}
