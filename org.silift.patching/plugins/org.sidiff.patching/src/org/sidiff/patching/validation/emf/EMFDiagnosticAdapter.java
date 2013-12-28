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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EMFDiagnosticAdapter)) {
			return false;
		}

		Diagnostic d1 = this.adaptee;
		Diagnostic d2 = ((EMFDiagnosticAdapter) obj).adaptee;

		return d1.getMessage().equals(d2.getMessage()) && d1.getSource().equals(d2.getSource());
	}
}
