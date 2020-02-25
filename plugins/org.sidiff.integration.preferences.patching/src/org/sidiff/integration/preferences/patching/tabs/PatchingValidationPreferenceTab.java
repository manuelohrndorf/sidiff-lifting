package org.sidiff.integration.preferences.patching.tabs;

import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.AbstractPreferenceTab;
import org.sidiff.patching.validation.ValidationMode;

/**
 * Class for the validation patching settings.
 * @author Daniel Roedder
 * @author rmueller
 */
public class PatchingValidationPreferenceTab extends AbstractPreferenceTab {

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		IPreferenceField validationMode =
			PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_VALIDATION_MODE,
				"Validation Mode",
				ValidationMode.class);
		list.add(validationMode);
	}
}
