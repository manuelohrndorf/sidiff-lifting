package org.sidiff.integration.preferences.lifting.tabs;

import java.util.List;

import org.sidiff.difference.lifting.api.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.ExpandableCompositeField;
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
	public void createPreferenceFields(List<PreferenceField> list) {
		recognitionEngineMode = RadioBoxPreferenceField.create(
				LiftingsSettingsAdapter.KEY_RECOGNITION_ENGINE_MODE,
				"Recognition Engine Mode",
				RecognitionEngineMode.class);
		list.add(recognitionEngineMode);

		// advanced settings
		ExpandableCompositeField compositeField = new ExpandableCompositeField("Advanced settings");
		list.add(compositeField);

		calculateEditRuleMatch = new CheckBoxPreferenceField(
				LiftingsSettingsAdapter.KEY_CALCULATE_EDIT_RULE_MATCH,
				"Calculate Edit Rule Match");
		compositeField.addField(calculateEditRuleMatch);

		serializeEditRuleMatch = new CheckBoxPreferenceField(
				LiftingsSettingsAdapter.KEY_SERIALIZE_EDIT_RULE_MATCH,
				"Serialize Edit Rule Match");
		compositeField.addField(serializeEditRuleMatch);

		useThreadPool = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_USE_THREAD_POOL, "Use Thread Pool");
		compositeField.addField(useThreadPool);

		numberOfThreads = new NumberPreferenceField(LiftingsSettingsAdapter.KEY_NUMBER_OF_THREADS, "Number of Threads");
		compositeField.addField(numberOfThreads);

		rulesPerThread = new NumberPreferenceField(LiftingsSettingsAdapter.KEY_RULES_PER_THREAD, "Rules per Thread");
		compositeField.addField(rulesPerThread);

		sortRecognitionRuleNodes = new CheckBoxPreferenceField(
				LiftingsSettingsAdapter.KEY_SORT_RECOGNITION_RULE_NODES,
				"Sort Recognition Rule Nodes");
		compositeField.addField(sortRecognitionRuleNodes);

		rulesetReduction = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_RULESET_REDUCTION, "Ruleset Reduction");
		compositeField.addField(rulesetReduction);

		buildGraphPerRule = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_BUILD_GRAPH_PER_RULE, "Build Graph per Rule");
		compositeField.addField(buildGraphPerRule);

		detectSplitJoins = new CheckBoxPreferenceField(LiftingsSettingsAdapter.KEY_DETECT_SPLIT_JOINS, "Detect Split Joins");
		compositeField.addField(detectSplitJoins);
	}
}
