package org.sidiff.integration.preferences.tabs;

/**
 * Abstract superclass for domain specific preference tabs.
 * @author Robert Müller
 *
 */
public abstract class AbstractDomainPreferenceTab implements IPreferenceTab, IPreferenceTab.DomainSpecific {

	private String documentType;

	/**
	 * Returns the document type of the current domain.
	 * @return the document type
	 */
	public String getDocumentType() {
		return documentType;
	}

	@Override
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
}
