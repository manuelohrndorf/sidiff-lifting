package org.sidiff.vcmsintegration.preferences.domains.interfaces;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab;

/**
 * 
 * Interface for the settings tabs used in domain specific settings sections.
 * @author Daniel Roedder
 */
public interface ISiDiffDomainPreferenceTab extends ISiDiffOrderableTab{
	
	/**
	 * Returns the title of the tab
	 * @return The title of the tab
	 */
	public String getTitle();
	
	/**
	 * Handling procedure if an value of a field in the tab has changed.
	 * @param event Event that occured
	 */
	public void propertyChange(PropertyChangeEvent event);
	
	/**
	 * Returns all {@link PreferenceField}s in the tab
	 * @return All fields present in the tab as an {@link Iterable}
	 */
	public Iterable<PreferenceField> getTabContent();
	
	/**
	 * Used by the abstract tab classes to create the tab contents.
	 * @return The document type of the model to be processed.
	 * 
	 */
	public String getDocumentType();

}
