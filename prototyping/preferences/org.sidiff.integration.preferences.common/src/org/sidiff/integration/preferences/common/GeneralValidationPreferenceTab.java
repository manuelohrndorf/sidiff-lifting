package org.sidiff.integration.preferences.common;

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
	public TabPage getPage() {
		return TabPage.VALIDATION;
	}

	@Override
	public String getTitle() {
		return "General";
	}

	@Override
	public int getPosition() {
		return 0;
	}

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		validateModelsField = new CheckBoxPreferenceField(BaseSettingsAdapter.KEY_VALIDATE_MODELS, "Validate Models");
		list.add(validateModelsField);
	}
}
