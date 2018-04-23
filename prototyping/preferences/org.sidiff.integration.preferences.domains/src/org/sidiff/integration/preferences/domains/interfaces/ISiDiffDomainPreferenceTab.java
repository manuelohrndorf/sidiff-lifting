package org.sidiff.integration.preferences.domains.interfaces;

import org.sidiff.integration.preferences.interfaces.ISiDiffPreferenceTab;

/**
 * 
 * Interface for the settings tabs used in domain specific settings sections.
 * @author Daniel Roedder, Robert Müller
 */
public interface ISiDiffDomainPreferenceTab extends ISiDiffPreferenceTab {
	String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.domains.pipelineStep";

	/**
	 * Set the document type that this tab should return settings for.
	 * @param documentType the document type
	 */
	void setDocumentType(String documentType);
}
