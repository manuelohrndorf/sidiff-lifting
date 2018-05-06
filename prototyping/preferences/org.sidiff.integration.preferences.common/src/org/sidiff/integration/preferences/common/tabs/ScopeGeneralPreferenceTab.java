package org.sidiff.integration.preferences.common.tabs;

import java.util.List;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.integration.preferences.common.settingsadapter.BaseSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.interfaces.IPreferenceTab;

/**
 * Class for general scope settings
 * @author Daniel Roedder, Robert Müller
 *
 */
public class ScopeGeneralPreferenceTab implements IPreferenceTab {

	private RadioBoxPreferenceField<?> scopeField;

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		scopeField = RadioBoxPreferenceField.create(BaseSettingsAdapter.KEY_SCOPE, "Scope", Scope.class);
		list.add(scopeField);
	}
}
