package org.sidiff.integration.preferences.lifting;

import java.util.List;

import org.sidiff.difference.lifting.api.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.NumberPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.interfaces.IPreferenceTab;
import org.sidiff.integration.preferences.lifting.settingsadapter.LiftingsSettingsAdapter;

/**
 * 
 * Class for the lifting settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class LiftingEnginesPreferenceTab implements IPreferenceTab {

	// TODO: add setting for IRecognitionEngine?
	private PreferenceField recognitionEngineMode;
	private PreferenceField calculateEditRuleMatch;
	private PreferenceField serializeEditRuleMatch;
	private PreferenceField useThreadPool;
	private PreferenceField numberOfThreads;
	private PreferenceField rulesPerThread;
	private PreferenceField sortRecognitionRuleNodes;
	private PreferenceField rulesetReduction;
	private PreferenceField buildGraphPerRule;
	private PreferenceField detectSplitJoins;

	@Override
	public TabPage getPage() {
		return TabPage.ENGINES;
	}

	@Override
	public String getTitle() {
		return "Lifting";
	}

	@Override
	public int getPosition() {
		return 30;
	}

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		recognitionEngineMode = RadioBoxPreferenceField.create(
				LiftingsSettingsAdapter.KEY_RECOGNITION_ENGINE_MODE,
				"Recognition Engine Mode",
				RecognitionEngineMode.class);
		list.add(recognitionEngineMode);

		calculateEditRuleMatch = new CheckBoxPreferenceField(
				LiftingsSettingsAdapter.KEY_CALCULATE_EDIT_RULE_MATCH,
				"Calculate Edit Rule Match");
		list.add(calculateEditRuleMatch);

		serializeEditRuleMatch = new CheckBoxPreferenceField(
				LiftingsSettingsAdapter.KEY_SERIALIZE_EDIT_RULE_MATCH,
				"Serialize Edit Rule Match");
		list.add(serializeEditRuleMatch);

		// advanced settings
		useThreadPool = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_USE_THREAD_POOL, "Use Thread Pool");
		list.add(useThreadPool);

		numberOfThreads = new NumberPreferenceField(LiftingsSettingsAdapter.KEY_NUMBER_OF_THREADS, "Number of Threads");
		list.add(numberOfThreads);

		rulesPerThread = new NumberPreferenceField(LiftingsSettingsAdapter.KEY_RULES_PER_THREAD, "Rules per Thread");
		list.add(rulesPerThread);

		sortRecognitionRuleNodes = new CheckBoxPreferenceField(
				LiftingsSettingsAdapter.KEY_SORT_RECOGNITION_RULE_NODES,
				"Sort Recognition Rule Nodes");
		list.add(sortRecognitionRuleNodes);

		rulesetReduction = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_RULESET_REDUCTION, "Ruleset Reduction");
		list.add(rulesetReduction);

		buildGraphPerRule = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_BUILD_GRAPH_PER_RULE, "Build Graph per Rule");
		list.add(buildGraphPerRule);

		detectSplitJoins = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_DETECT_SPLIT_JOINS, "Detect Split Joins");
		list.add(detectSplitJoins);
	}
}
