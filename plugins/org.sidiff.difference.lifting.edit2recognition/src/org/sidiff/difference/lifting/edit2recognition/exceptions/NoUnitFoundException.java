package org.sidiff.difference.lifting.edit2recognition.exceptions;

import org.eclipse.emf.henshin.model.Module;

/**
 * Exception is thrown while transforming an edit rule into a recognition rule. It indicates an
 * empty edit rule.
 */
public class NoUnitFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoUnitFoundException(Module module) {
		super("\nNo Transformation Unit found in " + module);
	}
}
