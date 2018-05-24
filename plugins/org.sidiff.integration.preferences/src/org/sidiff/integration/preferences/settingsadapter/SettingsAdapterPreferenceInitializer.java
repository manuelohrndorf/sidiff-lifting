package org.sidiff.integration.preferences.settingsadapter;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;

/**
 * Initializes default preference values using available settings adapters.
 * @author Robert Müller
 *
 */
public class SettingsAdapterPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		SettingsAdapterUtil.initializeDefaults(PreferenceStoreUtil.getPreferenceStore());
	}
}
