package org.sidiff.integration.preferences.settingsadapter;

import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;

/**
 * Abstract implementation for {@link ISettingsAdapter}s.
 * @author rmueller
 */
public abstract class AbstractSettingsAdapter implements ISettingsAdapter {

	private Set<String> documentTypes;
	private Set<Enum<?>> consideredSettings;
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
	public void setConsideredSettings(Set<Enum<?>> consideredSettings) {
		this.consideredSettings = consideredSettings;
	}

	/**
	 * Returns the settings items that are considered by this settings adapter.
	 * @return settings items that this adapter should adapt
	 */
	public Set<Enum<?>> getConsideredSettings() {
		return consideredSettings;
	}

	/**
	 * Returns whether the given item should be adapted by this settings adapter.
	 * @param item the settings item
	 * @return <code>true</code> if the item should be adapted, <code>false</code> otherwise
	 */
	public boolean isConsidered(Enum<?> item) {
		return consideredSettings.isEmpty() || consideredSettings.contains(item);
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

	protected String domainKey(String prefKey, String documentType) {
		return prefKey + "[" + documentType + "]";
	}
}
