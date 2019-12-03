package org.sidiff.integration.preferences.connector;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class ConnectorPreferencesInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ConnectorPreferencesPlugin.getDefault().getPreferenceStore();
		store.setDefault(ConnectorPreferencesConstants.P_URL, "localhost");	
		store.setDefault(ConnectorPreferencesConstants.P_Port, "1904");
		store.setDefault(ConnectorPreferencesConstants.P_USER, "");
		store.setDefault(ConnectorPreferencesConstants.P_PASSWORD, "");
	}

}
