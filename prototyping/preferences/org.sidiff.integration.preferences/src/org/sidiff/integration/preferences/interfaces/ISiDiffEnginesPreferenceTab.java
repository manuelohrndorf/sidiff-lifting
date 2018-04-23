/**
 * @author Daniel Roedder
 */

package org.sidiff.integration.preferences.interfaces;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;

/**
 * Interface for all Engine preference tabs.
 * These tabs are displayed in the engine section.
 *
 * @author Daniel Roedder, Robert Müller
 */
public interface ISiDiffEnginesPreferenceTab extends ISiDiffOrderableTab{

	String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.enginePipelineStep";

	/**
	 * Method to get the title of the tab.
	 * @return The title of the tab.
	 */
	public String getTitle();

	/**
	 * Handling event if the value of a field in the tab is changed.
	 * @param event Event that occured.
	 */
	public void propertyChange(PropertyChangeEvent event);

	/**
	 * Returns an {@link Iterable} with all preference fields present in the tab.
	 * @return The {@link Iterable} with the fields.
	 */
	public Iterable<PreferenceField> getTabContent();

	/**
	 * Sets the {@link IPreferenceStore} for all fields in the tab.
	 * @param store The {@link IPreferenceStore} to use by the fields in the tab.
	 */
	public void setPreferenceStore(IPreferenceStore store);
}
