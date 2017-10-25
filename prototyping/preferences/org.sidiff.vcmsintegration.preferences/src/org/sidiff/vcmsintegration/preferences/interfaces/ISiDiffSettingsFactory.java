package org.sidiff.vcmsintegration.preferences.interfaces;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.vcmsintegration.preferences.exceptions.InvalidSettingsException;

/**
 * 
 * Interface for all factory classes used by the SiDiff Preference plugins.
 * @author Daniel Roedder
 */
public interface ISiDiffSettingsFactory {
	
	/**
	 * Used to determine the Factorie's feature level (Matching, Difference...)
	 * @return String value with the feature level.
	 */
	public String getFeatureLevel();

	
	/**
	 * Used to set the fields in the implementing class to the appropriate values.
	 * @param store The preference store from which to load the values from.
	 * @param documentType The document type string of the ressource to work with
	 */
	public void doSetFields(String documentType, IPreferenceStore store);
	
	
	/**
	 * Method used to get the generated settings object
	 * @return The generated settings object as {@link AbstractSettings}. Using class must cast to appropriate sub-class.
	 * @throws InvalidSettingsException if the settings are invalid.
	 */
	public AbstractSettings getSettings() throws InvalidSettingsException;
}
