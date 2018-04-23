package org.sidiff.integration.preferences.lifting;

import org.sidiff.difference.lifting.api.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.integration.preferences.AbstractEnginePreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.EnumPreferenceValueConverter;

/**
 * 
 * Class for the lifting settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class LiftingEnginesPreferenceTab extends AbstractEnginePreferenceTab {

	/**
	 * The {@link PreferenceField} for the recognition engine mode selection
	 */
	private PreferenceField recognitionEngineModes;

	/**
	 * The {@link PreferenceField} for the calculate edit rule match setting
	 */
	private PreferenceField calculateEditRuleMatch;

	/**
	 * The {@link PreferenceField} for the serialize edit rule match setting
	 */
	private PreferenceField serializeEditRuleMatch;

	/**
	 * The {@link PreferenceField} for the merge imports setting
	 */
	private PreferenceField mergeImports;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Lifting";
	}

	@Override
	protected void createPreferenceFields() {
		recognitionEngineModes = RadioBoxPreferenceField.create(
				"recognitionEngineModes", "Recognition Engine Modes Services",
				RecognitionEngineMode.values(), new EnumPreferenceValueConverter());
		addField(recognitionEngineModes);

		calculateEditRuleMatch = new CheckBoxPreferenceField("calculateEditRuleMatch", "Calculate Edit Rule Match");
		addField(calculateEditRuleMatch);

		serializeEditRuleMatch = new CheckBoxPreferenceField("serializeEditRuleMatch", "Serialize Edit Rule Match");
		addField(serializeEditRuleMatch);

		mergeImports = new CheckBoxPreferenceField("mergeImports", "Merge Imports");
		addField(mergeImports);
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 2;
	}
}
