package org.sidiff.integration.preferences.lifting.tabs;

import java.util.List;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.integration.preferences.fieldeditors.ICompositePreferenceField;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.lifting.settingsadapter.LiftingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.AbstractPreferenceTab;

/**
 * 
 * Class for the lifting settings tab.
 * @author Daniel Roedder
 * @author rmueller
 */
public class LiftingEnginesPreferenceTab extends AbstractPreferenceTab {

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		IPreferenceField recognitionEngineMode =
			PreferenceFieldFactory.createRadioBox(
				LiftingSettingsAdapter.KEY_RECOGNITION_ENGINE_MODE,
				"Recognition Engine Mode",
				RecognitionEngineMode.class);
		list.add(recognitionEngineMode);

		// advanced settings
		ICompositePreferenceField<IPreferenceField> compositeField =
				PreferenceFieldFactory.createExpandableComposite("Advanced settings");
		list.add(compositeField);

		IPreferenceField calculateEditRuleMatch =
			PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_CALCULATE_EDIT_RULE_MATCH,
				"Calculate Edit Rule Match");
		compositeField.addField(calculateEditRuleMatch);

		IPreferenceField serializeEditRuleMatch =
			PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_SERIALIZE_EDIT_RULE_MATCH,
				"Serialize Edit Rule Match");
		compositeField.addField(serializeEditRuleMatch);

		IPreferenceField useThreadPool =
			PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_USE_THREAD_POOL,
				"Use Thread Pool");
		compositeField.addField(useThreadPool);

		IPreferenceField numberOfThreads =
			PreferenceFieldFactory.createNumber(
				LiftingSettingsAdapter.KEY_NUMBER_OF_THREADS,
				"Number of Threads");
		compositeField.addField(numberOfThreads);

		IPreferenceField rulesPerThread =
			PreferenceFieldFactory.createNumber(
				LiftingSettingsAdapter.KEY_RULES_PER_THREAD,
				"Rules per Thread");
		compositeField.addField(rulesPerThread);

		IPreferenceField sortRecognitionRuleNodes =
			PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_SORT_RECOGNITION_RULE_NODES,
				"Sort Recognition Rule Nodes");
		compositeField.addField(sortRecognitionRuleNodes);

		IPreferenceField rulesetReduction =
			PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_RULESET_REDUCTION,
				"Ruleset Reduction");
		compositeField.addField(rulesetReduction);

		IPreferenceField buildGraphPerRule = 
			PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_BUILD_GRAPH_PER_RULE,
				"Build Graph per Rule");
		compositeField.addField(buildGraphPerRule);

		IPreferenceField detectSplitJoins =
			PreferenceFieldFactory.createCheckBox(
				LiftingSettingsAdapter.KEY_DETECT_SPLIT_JOINS,
				"Detect Split Joins");
		compositeField.addField(detectSplitJoins);

		// hook up enabled states
		useThreadPool.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				numberOfThreads.setEnabled((Boolean)event.getNewValue());
				rulesPerThread.setEnabled((Boolean)event.getNewValue());
			}
		});
	}
}
