package org.sidiff.integration.preferences.common.tabs;

import java.util.List;

import org.sidiff.integration.preferences.common.settingsadapter.BaseSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.tabs.IPreferenceTab;

/**
 * Class for general validation settings
 * @author Daniel Roedder, Robert Müller
 *
 */
public class GeneralValidationPreferenceTab implements IPreferenceTab {

	private IPreferenceField validateModelsField;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		validateModelsField = PreferenceFieldFactory.createCheckBox(
				BaseSettingsAdapter.KEY_VALIDATE_MODELS, "Validate Models");
		list.add(validateModelsField);
	}
}
