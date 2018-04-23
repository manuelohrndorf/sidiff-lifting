package org.sidiff.integration.preferences;

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
		validateModelsField = new CheckBoxPreferenceField("validateModels", "Validate Models");
		addField(validateModelsField);
	}
}
