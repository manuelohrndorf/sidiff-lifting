package org.sidiff.integration.preferences.patching.tabs;

import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.IPreferenceTab;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;

/**
 * Class for the validation patching settings.
 * @author Daniel Roedder, Robert Müller
 *
 */
public class PatchingValidationPreferenceTab implements IPreferenceTab {

	private IPreferenceField validationMode;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		validationMode = PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_VALIDATION_MODE,
				"Validation Mode",
				ValidationMode.class);
		list.add(validationMode);
	}
}
