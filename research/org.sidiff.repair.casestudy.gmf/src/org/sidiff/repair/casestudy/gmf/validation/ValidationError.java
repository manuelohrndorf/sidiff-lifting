package org.sidiff.repair.casestudy.gmf.validation;

/**
 * Simple container class that implements {@link IValidationError}.
 * 
 * @author kehrer
 */
public class ValidationError implements IValidationError {
	
	private Throwable exception;

	private String message;

	private String source;

	private String severity;
	
	public ValidationError(Throwable exception, String message, String source, String severity) {
		super();
		this.exception = exception;
		this.message = message;
		this.source = source;
		this.severity = severity;
	}

	@Override
	public Throwable getException() {
		return exception;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getSource() {
		return source;
	}

	@Override
	public String getSeverity() {
		return severity;
	}
	
}
