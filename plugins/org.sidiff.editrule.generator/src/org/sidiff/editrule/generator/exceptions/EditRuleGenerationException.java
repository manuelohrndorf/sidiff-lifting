package org.sidiff.editrule.generator.exceptions;

public class EditRuleGenerationException extends Exception {

	private static final long serialVersionUID = -2553605012765905838L;

	public EditRuleGenerationException(String message) {
		super(message);
	}
	
	public EditRuleGenerationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
