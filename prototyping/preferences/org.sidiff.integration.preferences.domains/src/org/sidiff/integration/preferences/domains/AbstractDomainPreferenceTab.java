package org.sidiff.integration.preferences.domains;

import org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab;

/**
 * 
 * @author Robert Müller
 *
 */
public abstract class AbstractDomainPreferenceTab implements ISiDiffDomainPreferenceTab {

	private String documentType;

	public String getDocumentType() {
		return documentType;
	}

	@Override
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
}
