package org.sidiff.integration.preferences.interfaces;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.integration.preferences.FeatureLevel;
import org.sidiff.integration.preferences.exceptions.InvalidSettingsException;

/**
 * 
 * Interface for all factory classes used by the SiDiff Preference plugins.
 * @author Daniel Roedder, Robert Müller
 */
public interface ISiDiffSettingsFactory {

	String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.settingsFactory";

	/**
	 * Used to determine the Factorie's feature level (Matching, Difference...)
	 * @return the feature level
	 */
	FeatureLevel getFeatureLevel();

	/**
	 * Used to set the fields in the implementing class to the appropriate values.
	 * @param store The preference store from which to load the values from.
	 * @param documentType The document type string of the ressource to work with
	 */
	void doSetFields(String documentType, IPreferenceStore store);

	/**
	 * Method used to get the generated settings object
	 * @return The generated settings object as {@link AbstractSettings}. Using class must cast to appropriate sub-class.
	 * @throws InvalidSettingsException if the settings are invalid.
	 */
	AbstractSettings getSettings() throws InvalidSettingsException;
}
