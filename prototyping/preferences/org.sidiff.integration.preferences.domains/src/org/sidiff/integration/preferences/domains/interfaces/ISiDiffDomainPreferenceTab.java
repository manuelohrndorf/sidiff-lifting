package org.sidiff.integration.preferences.domains.interfaces;

import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab;

/**
 * 
 * Interface for the settings tabs used in domain specific settings sections.
 * @author Daniel Roedder, Robert Müller
 */
public interface ISiDiffDomainPreferenceTab extends ISiDiffOrderableTab{

	String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.domains.pipelineStep";

	/**
	 * Returns the title of the tab
	 * @return The title of the tab
	 */
	public String getTitle();

	/**
	 * Returns all {@link PreferenceField}s in the tab
	 * @return All fields present in the tab as an {@link Iterable}
	 */
	public Iterable<PreferenceField> getTabContent();
	
	/**
	 * Set the document type that this tab should return settings for.
	 * @param documentType the document type
	 */
	public void setDocumentType(String documentType);

}
