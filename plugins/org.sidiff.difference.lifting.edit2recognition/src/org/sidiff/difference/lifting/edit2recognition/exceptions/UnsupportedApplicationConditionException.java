package org.sidiff.difference.lifting.edit2recognition.exceptions;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Formula;

/**
 * Exception is thrown while transforming an edit rule into a recognition rule. It indicates an
 * unsupported application condition type in the edit rule.
 */
public class UnsupportedApplicationConditionException extends EditToRecognitionException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception: It indicates an unsupported application condition type in the
	 * edit rule.
	 * 
	 * @param formula
	 *            The unsupported formula.
	 */
	public UnsupportedApplicationConditionException(Formula formula) {
		super("Unsupported application condition: " + EcoreUtil.getURI(formula));
	}
}