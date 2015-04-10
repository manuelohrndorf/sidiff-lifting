package org.sidiff.editrule.generator.serge.exceptions;

public class NoEncapsulatedTypeInformationException extends Exception {

	public NoEncapsulatedTypeInformationException() {
		super("One masked classifier, as defined on the SERGe config, does not contain the defined EEnumLiterals as its encapsulated type information"
				+ "in meta-model");
	}
	
}
