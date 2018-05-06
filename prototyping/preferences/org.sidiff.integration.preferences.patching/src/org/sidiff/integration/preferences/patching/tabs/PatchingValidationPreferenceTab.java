package org.sidiff.integration.preferences.patching.tabs;

import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.interfaces.IPreferenceTab;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;

/**
 * Class for the validation patching settings.
 * @author Daniel Roedder, Robert Müller
 *
 */
public class PatchingValidationPreferenceTab implements IPreferenceTab {

	private PreferenceField validationMode;

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		validationMode = RadioBoxPreferenceField.create(
				PatchingSettingsAdapter.KEY_VALIDATION_MODE,
				"Validation Mode",
				ValidationMode.class);
		list.add(validationMode);
	}
}
