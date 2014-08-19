package org.sidiff.difference.lifting.edit2recognition.exceptions;

import org.eclipse.emf.henshin.model.Module;


public class NoRecognizableChangesInEditRule extends Exception  {
	private static final long serialVersionUID = 1L;

	public NoRecognizableChangesInEditRule(Module module) {
		super("\nFound no Recognizable changes in the Edit-Rule: " + module);
	}
}
