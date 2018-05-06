package org.sidiff.integration.preferences.tabs;

import org.sidiff.integration.preferences.interfaces.IPreferenceTab;

/**
 * 
 * @author Robert Müller
 *
 */
public abstract class AbstractDomainPreferenceTab implements IPreferenceTab, IPreferenceTab.DomainSpecific {

	private String documentType;

	public String getDocumentType() {
		return documentType;
	}

	@Override
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
}
