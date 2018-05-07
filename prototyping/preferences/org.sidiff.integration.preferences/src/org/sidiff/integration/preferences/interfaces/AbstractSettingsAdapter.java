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

	public Set<String> getDocumentTypes() {
		return documentTypes;
	}
}
