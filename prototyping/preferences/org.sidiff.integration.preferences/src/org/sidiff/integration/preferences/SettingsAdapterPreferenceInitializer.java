package org.sidiff.integration.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;
import org.sidiff.integration.preferences.util.SettingsAdapterUtil;

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
