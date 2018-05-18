package org.sidiff.integration.preferences.settingsadapter;

import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;

/**
 * Abstract implementation for {@link ISettingsAdapter}s.
 * @author Robert Müller
 *
 */
public abstract class AbstractSettingsAdapter implements ISettingsAdapter {

	private Set<String> documentTypes;
	private DiagnosticChain diagnosticChain;
	private BasicDiagnostic diagnosticGroup;

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
		this.diagnosticGroup = null;
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
		if(diagnosticGroup == null) {
			diagnosticGroup = getDiagnosticGroup();
			diagnosticChain.add(diagnosticGroup);
		}

		diagnosticGroup.add(new BasicDiagnostic(
				severity, diagnosticGroup.getSource(), 0,
				message, e == null ? null : new Object[] { e }));
	}

	/**
	 * Creates a {@link BasicDiagnostic} with source and message to group all {@link Diagnostic}s created by this adapter.
	 * The diagnostics created by this adapter will have the same source as this one. Will only be called once for an adapter.
	 * @return new diagnostic
	 */
	protected abstract BasicDiagnostic getDiagnosticGroup();
}
