package org.sidiff.integration.preferences.common.settingsadapter;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.common.settings.BaseSettings;
import org.sidiff.integration.preferences.interfaces.AbstractSettingsAdapter;

/**
 * 
 * @author Robert Müller
 *
 */
public class BaseSettingsAdapter extends AbstractSettingsAdapter {

	public static final String KEY_SCOPE = "scope";
	public static final String KEY_VALIDATE_MODELS = "validateModels";

	private Scope scope;
	private boolean validate;

	@Override
	public boolean canAdapt(AbstractSettings settings) {
		return settings instanceof BaseSettings;
	}

	@Override
	public void adapt(AbstractSettings settings) {
		BaseSettings baseSettings = (BaseSettings)settings;
		baseSettings.setScope(scope);
		baseSettings.setValidate(validate);
	}

	@Override
	public void load(IPreferenceStore store) {
		scope = Scope.valueOf(store.getString(KEY_SCOPE));
		validate = store.getBoolean(KEY_VALIDATE_MODELS);
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_SCOPE, Scope.RESOURCE.name());
		store.setDefault(KEY_VALIDATE_MODELS, true);
	}
}
