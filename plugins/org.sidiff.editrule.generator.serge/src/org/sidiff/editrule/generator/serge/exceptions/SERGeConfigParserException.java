package org.sidiff.editrule.generator.serge.exceptions;

@SuppressWarnings("serial")
public class SERGeConfigParserException extends Exception{

	public SERGeConfigParserException() {
		super("Error when parsing the SERGe-Configuration. Check well-formedness, validity and correct spelling.");
	}
}
