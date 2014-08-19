package org.sidiff.difference.lifting.edit2recognition.exceptions;

import org.eclipse.emf.henshin.model.Module;

/**
 * Exception is thrown while transforming an edit rule into a recognition rule. It indicates
 * that there is no unit named 'mainUnit'.
 */
public class NoMainUnitFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoMainUnitFoundException(Module module) {
		super("\nNo Main Unit found in " + module);
	}
}
