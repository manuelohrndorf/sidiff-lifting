package org.sidiff.integration.preferences.remote;

import org.sidiff.integration.preferences.remote.pages.RemotePreferenceAdapter;
import org.sidiff.remote.common.settings.IRemotePreferencesSupplier;
import org.sidiff.remote.common.settings.RemotePreferences;

/**
 * 
 * @author Robert Müller
 *
 */
public class UiRemotePreferencesSupplier implements IRemotePreferencesSupplier {

	public static final String NAME = "UI Remote Preferences Supplier";
	public static final String KEY = "UiRemotePreferencesSupplier";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public RemotePreferences getRemotePreferences() {
		return new RemotePreferenceAdapter().getRemotePreferences();
	}
}
