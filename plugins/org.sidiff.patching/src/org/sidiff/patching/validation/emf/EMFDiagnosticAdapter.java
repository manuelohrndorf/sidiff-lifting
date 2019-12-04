package org.sidiff.patching.validation.emf;

import org.eclipse.emf.common.util.Diagnostic;
import org.sidiff.patching.validation.IValidationError;

/**
 * Encapsulates the EMF validation framework. Simply adapts a Diagnostic, which
 * is the error class in the EMF validation framework.
 * 
 * @author kehrer
 */
public class EMFDiagnosticAdapter implements IValidationError {

	private Diagnostic adaptee;

	EMFDiagnosticAdapter(Diagnostic diagnostic) {
		adaptee = diagnostic;
	}

	@Override
	public Throwable getException() {
		return adaptee.getException();
	}

	@Override
	public String getMessage() {
		return adaptee.getMessage();
	}

	@Override
	public String getSource() {
		return adaptee.getSource();
	}
}
