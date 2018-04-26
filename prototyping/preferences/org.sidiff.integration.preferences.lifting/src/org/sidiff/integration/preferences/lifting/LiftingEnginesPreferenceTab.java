package org.sidiff.integration.preferences.lifting;

import org.sidiff.difference.lifting.api.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.integration.preferences.AbstractEnginePreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.NumberPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.lifting.settingsadapter.LiftingsSettingsAdapter;

/**
 * 
 * Class for the lifting settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class LiftingEnginesPreferenceTab extends AbstractEnginePreferenceTab {

	// TODO: add setting for IRecognitionEngine?

	/**
	 * The {@link PreferenceField} for the recognition engine mode selection
	 */
	private PreferenceField recognitionEngineMode;

	/**
	 * The {@link PreferenceField} for the calculate edit rule match setting
	 */
	private PreferenceField calculateEditRuleMatch;

	/**
	 * The {@link PreferenceField} for the serialize edit rule match setting
	 */
	private PreferenceField serializeEditRuleMatch;

	/**
	 * {@link PreferenceField} for the use thread pool setting
	 */
	private PreferenceField useThreadPool;

	/**
	 * The {@link PreferenceField} for the number of threads to be used
	 */
	private PreferenceField numberOfThreads;

	/**
	 * The {@link PreferenceField} for the rules per thread setting
	 */
	private PreferenceField rulesPerThread;

	/**
	 * The {@link PreferenceField} for the sort recognition rule nodes setting
	 */
	private PreferenceField sortRecognitionRuleNodes;

	/**
	 * The {@link PreferenceField} for the ruleset reduction settings
	 */
	private PreferenceField rulesetReduction;

	/**
	 * The {@link PreferenceField} for the build graph per rule setting
	 */
	private PreferenceField buildGraphPerRule;

	/**
	 * the {@link PreferenceField} for the detect split joins setting
	 */
	private PreferenceField detectSplitJoins;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Lifting";
	}

	@Override
	protected void createPreferenceFields() {
		recognitionEngineMode = RadioBoxPreferenceField.create(
				LiftingsSettingsAdapter.KEY_RECOGNITION_ENGINE_MODE,
				"Recognition Engine Mode",
				RecognitionEngineMode.class);
		addField(recognitionEngineMode);

		calculateEditRuleMatch = new CheckBoxPreferenceField(
				LiftingsSettingsAdapter.KEY_CALCULATE_EDIT_RULE_MATCH,
				"Calculate Edit Rule Match");
		addField(calculateEditRuleMatch);

		serializeEditRuleMatch = new CheckBoxPreferenceField(
				LiftingsSettingsAdapter.KEY_SERIALIZE_EDIT_RULE_MATCH,
				"Serialize Edit Rule Match");
		addField(serializeEditRuleMatch);

		// advanced settings
		useThreadPool = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_USE_THREAD_POOL, "Use Thread Pool");
		addField(useThreadPool);

		numberOfThreads = new NumberPreferenceField(LiftingsSettingsAdapter.KEY_NUMBER_OF_THREADS, "Number of Threads");
		addField(numberOfThreads);

		rulesPerThread = new NumberPreferenceField(LiftingsSettingsAdapter.KEY_RULES_PER_THREAD, "Rules per Thread");
		addField(rulesPerThread);

		sortRecognitionRuleNodes = new CheckBoxPreferenceField(
				LiftingsSettingsAdapter.KEY_SORT_RECOGNITION_RULE_NODES,
				"Sort Recognition Rule Nodes");
		addField(sortRecognitionRuleNodes);

		rulesetReduction = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_RULESET_REDUCTION, "Ruleset Reduction");
		addField(rulesetReduction);

		buildGraphPerRule = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_BUILD_GRAPH_PER_RULE, "Build Graph per Rule");
		addField(buildGraphPerRule);

		detectSplitJoins = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_DETECT_SPLIT_JOINS, "Detect Split Joins");
		addField(detectSplitJoins);
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 2;
	}
}
