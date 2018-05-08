package org.sidiff.integration.preferences.interfaces;

import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.sidiff.integration.preferences.PreferencesPlugin;

/**
 * Abstract implementation for {@link ISettingsAdapter}s.
 * @author Robert Müller
 *
 */
public abstract class AbstractSettingsAdapter implements ISettingsAdapter {

	private Set<String> documentTypes;
	private DiagnosticChain diagnosticChain;

	@Override
	public void setDocumentTypes(Set<String> documentTypes) {
		this.documentTypes = documentTypes;
	}

	/**
	 * Returns the document types that were previously set by the framework.
	 * @return the document types that this settings adapter should use
	 */
	public Set<String> getDocumentTypes() {
		return documentTypes;
	}

	@Override
	public void setDiagnosticChain(DiagnosticChain diagnosticChain) {
		this.diagnosticChain = diagnosticChain;
	}

	public void addError(String message) {
		addDiagnostic(Diagnostic.ERROR, message, null);
	}

	public void addError(String message, Throwable e) {
		addDiagnostic(Diagnostic.ERROR, message, e);
	}

	public void addWarning(String message) {
		addDiagnostic(Diagnostic.WARNING, message, null);
	}

	public void addWarning(String message, Throwable e) {
		addDiagnostic(Diagnostic.WARNING, message, e);
	}

	private void addDiagnostic(int severity, String message, Throwable e) {
		diagnosticChain.add(new BasicDiagnostic(
				severity, PreferencesPlugin.PLUGIN_ID, 0,
				message, e == null ? null : new Object[] { e }));
	}
}
