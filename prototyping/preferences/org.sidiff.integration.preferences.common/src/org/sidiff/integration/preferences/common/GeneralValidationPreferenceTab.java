package org.sidiff.integration.preferences.common;

import org.sidiff.integration.preferences.AbstractValidationPreferenceTab;
import org.sidiff.integration.preferences.common.settingsadapter.BaseSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;

/**
 * Class for general validation settings
 * @author Daniel Roedder, Robert Müller
 *
 */
public class GeneralValidationPreferenceTab extends AbstractValidationPreferenceTab {

	/**
	 * The {@link org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField} for the validate models setting
	 */
	private CheckBoxPreferenceField validateModelsField;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 0;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "General";
	}

	@Override
	protected void createPreferenceFields() {
		validateModelsField = new CheckBoxPreferenceField(BaseSettingsAdapter.KEY_VALIDATE_MODELS, "Validate Models");
		addField(validateModelsField);
	}
}
