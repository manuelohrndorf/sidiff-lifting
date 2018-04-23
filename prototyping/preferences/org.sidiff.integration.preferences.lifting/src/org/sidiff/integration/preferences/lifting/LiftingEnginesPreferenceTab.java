package org.sidiff.integration.preferences.lifting;

import org.sidiff.difference.lifting.api.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.integration.preferences.AbstractEnginePreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.NumberPreferenceField;
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

		// advanced settings
		useThreadPool = new CheckBoxPreferenceField("useThreadPool", "Use Thread Pool");
		addField(useThreadPool);

		numberOfThreads = new NumberPreferenceField("numberOfThreads", "Number of Threads");
		addField(numberOfThreads);

		rulesPerThread = new NumberPreferenceField("rulesPerThread", "Rules per Thread");
		addField(rulesPerThread);

		sortRecognitionRuleNodes = new CheckBoxPreferenceField("sortRecognitionRuleNodes", "Sort Recognition Rule Nodes");
		addField(sortRecognitionRuleNodes);

		rulesetReduction = new CheckBoxPreferenceField("rulesetReduction", "Ruleset Reduction");
		addField(rulesetReduction);

		buildGraphPerRule = new CheckBoxPreferenceField("buildGraphPerRule", "Build Graph per Rule");
		addField(buildGraphPerRule);

		detectSplitJoins = new CheckBoxPreferenceField("detectSplitJoins", "Detect Split Joins");
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
