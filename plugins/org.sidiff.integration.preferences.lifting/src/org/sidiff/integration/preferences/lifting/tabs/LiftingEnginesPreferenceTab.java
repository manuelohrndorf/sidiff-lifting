package org.sidiff.integration.preferences.lifting.tabs;

import java.util.List;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.integration.preferences.fieldeditors.ICompositePreferenceField;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.lifting.settingsadapter.LiftingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.IPreferenceTab;

/**
 * 
 * Class for the lifting settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class LiftingEnginesPreferenceTab implements IPreferenceTab {

	private IPreferenceField recognitionEngineMode;
	private IPreferenceField calculateEditRuleMatch;
	private IPreferenceField serializeEditRuleMatch;
	private IPreferenceField useThreadPool;
	private IPreferenceField numberOfThreads;
	private IPreferenceField rulesPerThread;
	private IPreferenceField sortRecognitionRuleNodes;
	private IPreferenceField rulesetReduction;
	private IPreferenceField buildGraphPerRule;
	private IPreferenceField detectSplitJoins;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		recognitionEngineMode = PreferenceFieldFactory.createRadioBox(
				LiftingSettingsAdapter.KEY_RECOGNITION_ENGINE_MODE,
				"Recognition Engine Mode",
				RecognitionEngineMode.class);
		list.add(recognitionEngineMode);

		// advanced settings
		ICompositePreferenceField compositeField = PreferenceFieldFactory.createExpandableComposite("Advanced settings");
		list.add(compositeField);

		calculateEditRuleMatch = PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_CALCULATE_EDIT_RULE_MATCH, "Calculate Edit Rule Match");
		compositeField.addField(calculateEditRuleMatch);

		serializeEditRuleMatch = PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_SERIALIZE_EDIT_RULE_MATCH, "Serialize Edit Rule Match");
		compositeField.addField(serializeEditRuleMatch);

		useThreadPool = PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_USE_THREAD_POOL, "Use Thread Pool");
		useThreadPool.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				numberOfThreads.setEnabled((Boolean)event.getNewValue());
				rulesPerThread.setEnabled((Boolean)event.getNewValue());
			}
		});
		compositeField.addField(useThreadPool);

		numberOfThreads = PreferenceFieldFactory.createNumber(
				LiftingSettingsAdapter.KEY_NUMBER_OF_THREADS, "Number of Threads");
		compositeField.addField(numberOfThreads);

		rulesPerThread = PreferenceFieldFactory.createNumber(
				LiftingSettingsAdapter.KEY_RULES_PER_THREAD, "Rules per Thread");
		compositeField.addField(rulesPerThread);

		sortRecognitionRuleNodes = PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_SORT_RECOGNITION_RULE_NODES, "Sort Recognition Rule Nodes");
		compositeField.addField(sortRecognitionRuleNodes);

		rulesetReduction = PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_RULESET_REDUCTION, "Ruleset Reduction");
		compositeField.addField(rulesetReduction);

		buildGraphPerRule = PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_BUILD_GRAPH_PER_RULE, "Build Graph per Rule");
		compositeField.addField(buildGraphPerRule);

		detectSplitJoins = PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_DETECT_SPLIT_JOINS, "Detect Split Joins");
		compositeField.addField(detectSplitJoins);
	}
}
