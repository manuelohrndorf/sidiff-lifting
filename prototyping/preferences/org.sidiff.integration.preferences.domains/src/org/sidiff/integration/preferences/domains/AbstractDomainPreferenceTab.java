package org.sidiff.integration.preferences.domains;

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

	@Override
	public TabPage getPage() {
		return TabPage.DOMAINS;
	}
}
