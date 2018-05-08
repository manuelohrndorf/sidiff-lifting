package org.sidiff.integration.preferences.interfaces;

import java.util.Set;

/**
 * Abstract implementation for {@link ISettingsAdapter}s.
 * @author Robert Müller
 *
 */
public abstract class AbstractSettingsAdapter implements ISettingsAdapter {

	private Set<String> documentTypes;

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
}
