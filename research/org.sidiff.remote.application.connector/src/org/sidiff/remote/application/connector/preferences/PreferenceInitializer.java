package org.sidiff.remote.application.ui.connector.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ConnectorUIPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_URL, "localhost");	
		store.setDefault(PreferenceConstants.P_Port, "1904");
	}

}
