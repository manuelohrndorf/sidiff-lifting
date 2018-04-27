package org.sidiff.integration.preferences.common;

import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.integration.preferences.TabbedPreferenceFieldPage;
import org.sidiff.integration.preferences.common.settingsadapter.BaseSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;

/**
 * 
 * Preference page for resolution strategy
 * @author Daniel Roedder, Robert Müller
 */
public class ResolutionStrategyPreferencePage extends TabbedPreferenceFieldPage implements IWorkbenchPreferencePage {

	/**
	 * The {@link org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField} for the scope setting
	 */
	private RadioBoxPreferenceField<?> scopeField;

	/**
	 * Creates the preference fields for the page.
	 */
	@Override
	protected void createPreferenceFields() {
		scopeField = RadioBoxPreferenceField.create(BaseSettingsAdapter.KEY_SCOPE, "Scope", Scope.class);
		addField(scopeField, this.addTab("Scope settings"));
	}
}
