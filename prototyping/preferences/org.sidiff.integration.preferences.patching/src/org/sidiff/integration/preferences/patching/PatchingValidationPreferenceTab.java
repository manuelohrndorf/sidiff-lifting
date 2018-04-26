package org.sidiff.integration.preferences.patching;

import org.sidiff.integration.preferences.AbstractValidationPreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;

/**
 * Class for the validation patching settings.
 * @author Daniel Roedder, Robert Müller
 *
 */
public class PatchingValidationPreferenceTab extends AbstractValidationPreferenceTab {

	/**
	 * The {@link PreferenceField} for the validation mode setting
	 */
	private PreferenceField validationMode;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 4;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Patching";
	}

	@Override
	protected void createPreferenceFields() {
		validationMode = RadioBoxPreferenceField.create(
				PatchingSettingsAdapter.KEY_VALIDATION_MODE,
				"Validation Mode",
				ValidationMode.class);
		addField(validationMode);
	}
}
