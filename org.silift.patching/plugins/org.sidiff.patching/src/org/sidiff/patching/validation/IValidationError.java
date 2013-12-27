package org.sidiff.patching.validation;

public interface IValidationError {
	
	public Throwable getException();
	
	public String getMessage();
	
	public String getSource();
}
