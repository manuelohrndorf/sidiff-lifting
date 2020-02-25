package org.sidiff.integration.preferences.common.tabs;

import java.util.List;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.integration.preferences.common.settingsadapter.BaseSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.tabs.AbstractPreferenceTab;

/**
 * Class for general scope settings
 * @author Daniel Roedder
 * @author rmueller
 */
public class ScopeGeneralPreferenceTab extends AbstractPreferenceTab {

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		IPreferenceField scopeField =
			PreferenceFieldFactory.createRadioBox(
				BaseSettingsAdapter.KEY_SCOPE,
				"Scope",
				Scope.class);
		list.add(scopeField);
	}
}
