package org.sidiff.vcmsintegration.preferences.interfaces;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;

/**
 * Interface for all Validation Preferences tabs.
 * All tabs of this interface are displayed in the validation section of the preferences.
 * @author Daniel Roedder
 *
 */
public interface ISiDiffValidationPreferenceTab extends ISiDiffOrderableTab {

	
	/**
	 * 
	 * @return The title of the tab.
	 */
	public String getTitle();
	
	/**
	 * Handling event if the value of a field in the tab is changed.
	 * @param event Event that occured.
	 */
	public void propertyChange(PropertyChangeEvent event);
	
	/**
	 * Returns an {@link Iterable} with all fields present in the tab.
	 * @return The {@link Iterable} with the fields.
	 */
	public Iterable<PreferenceField> getTabContent();
	
	/**
	 * Sets the {@link IPreferenceStore} for all fields in the tab.
	 * @param store The {@link IPreferenceStore} to use by the fields in the tab.
	 */
	public void setPreferenceStore(IPreferenceStore store);
}
