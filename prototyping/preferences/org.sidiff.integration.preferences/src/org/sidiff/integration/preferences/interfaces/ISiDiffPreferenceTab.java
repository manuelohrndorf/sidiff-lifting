package org.sidiff.integration.preferences.interfaces;

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;

/**
 * 
 * @author Daniel Roedder, Robert Müller
 *
 */
public interface ISiDiffPreferenceTab extends ISiDiffOrderableTab {
	/**
	 * 
	 * @return The title of the tab.
	 */
	String getTitle();

	/**
	 * Handling event if the value of a field in the tab is changed.
	 * @param event Event that occured.
	 */
	void propertyChange(PropertyChangeEvent event);

	/**
	 * Returns a {@link List} with all fields present in the tab.
	 * @return The {@link List} with the fields.
	 */
	List<PreferenceField> getTabContent();

	/**
	 * Sets the {@link IPreferenceStore} for all fields in the tab.
	 * @param store The {@link IPreferenceStore} to use by the fields in the tab.
	 */
	void setPreferenceStore(IPreferenceStore store);
}
