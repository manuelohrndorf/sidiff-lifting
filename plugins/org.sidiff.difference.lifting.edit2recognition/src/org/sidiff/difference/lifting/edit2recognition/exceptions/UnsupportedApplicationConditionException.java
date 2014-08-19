package org.sidiff.difference.lifting.edit2recognition.exceptions;

import org.eclipse.emf.henshin.model.Formula;

/**
 * Exception is thrown while transforming an edit rule into a recognition rule. It indicates an
 * unsupported application condition type in the edit rule.
 */
public class UnsupportedApplicationConditionException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnsupportedApplicationConditionException(Formula formula) {
		super("Unsupported application condition: " + formula);
	}
}