package org.sidiff.patching.validation;

/**
 * Simple container class that implements {@link IValidationError}.
 *
 * @author kehrer
 */
public class ValidationError implements IValidationError {

	private Throwable exception;
	private String message;
	private String source;

	public ValidationError(Throwable exception, String message, String source) {
		super();
		this.exception = exception;
		this.message = message;
		this.source = source;
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
}
