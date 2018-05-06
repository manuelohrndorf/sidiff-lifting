package org.sidiff.integration.preferences.common.tabs;

import java.util.List;

import org.sidiff.integration.preferences.common.settingsadapter.BaseSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.IPreferenceTab;

/**
 * Class for general validation settings
 * @author Daniel Roedder, Robert Müller
 *
 */
public class GeneralValidationPreferenceTab implements IPreferenceTab {

	private CheckBoxPreferenceField validateModelsField;

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		validateModelsField = new CheckBoxPreferenceField(BaseSettingsAdapter.KEY_VALIDATE_MODELS, "Validate Models");
		list.add(validateModelsField);
	}
}
