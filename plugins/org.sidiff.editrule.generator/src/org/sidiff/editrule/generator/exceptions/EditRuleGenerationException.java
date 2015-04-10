package org.sidiff.editrule.generator.exceptions;

@SuppressWarnings("serial")
public class EditRuleGenerationException extends Exception {

	public EditRuleGenerationException(Exception e) {
		super("Error generating edit rules:\n" + e);
	}
	
}
