package org.sidiff.integration.preferences.tabs;

/**
 * Abstract superclass for domain specific preference tabs.
 * @author Robert M�ller
 *
 */
public abstract class AbstractDomainPreferenceTab extends AbstractPreferenceTab implements IPreferenceTab.DomainSpecific {

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
	
	protected String domainKey(String prefKey) {
		return prefKey + "[" + documentType + "]";
	}
}
