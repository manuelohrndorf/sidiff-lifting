package org.sidiff.difference.lifting.edit2recognition.exceptions;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Unit;

/**
 * Exception is thrown while transforming an edit rule into a recognition rule. It indicates an
 * unsupported unit type in the edit rule.
 */
public class UnsupportedUnitException extends EditToRecognitionException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception: It indicates an unsupported unit type in the edit rule.
	 * 
	 * @param unit
	 *            The unsupported unit.
	 */
	public UnsupportedUnitException(Unit unit) {
		super("\nSupported units: " + EcoreUtil.getURI(unit) + "\n" 
				+ "  -> Sequential unit containing exactly one (multi-)rule\n"
				+ "  -> Priority unit containing exactly one (multi-)rule\n");
	}
}
