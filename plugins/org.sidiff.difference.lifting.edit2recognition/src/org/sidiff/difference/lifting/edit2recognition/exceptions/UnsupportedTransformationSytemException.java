package org.sidiff.difference.lifting.edit2recognition.exceptions;

import org.eclipse.emf.henshin.model.Module;

/**
 * Exception is thrown while transforming an edit rule into a recognition rule. It indicates an
 * unsupported unit type in the edit rule.
 */
public class UnsupportedTransformationSytemException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnsupportedTransformationSytemException(Module module) {
		super("\nSupported units: " + module + "\n" + "  -> Amalgamation unit\n"
				+ "  -> Priority unit with single rule\n"
				+ "  -> Sequential unit with single rule\n");
	}
}
