package org.sidiff.integration.preferences.lifting.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.lifting.settingsadapter.LiftingSettingsAdapter;
import org.sidiff.integration.preferences.lifting.valueconverters.LiftingRuleBaseValueConverter;
import org.sidiff.integration.preferences.tabs.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.ExtensionValueConverter;

/**
 * 
 * Class for the domain specific lifting settings.
 * @author Daniel Roedder, cpietsch, Robert Müller
 */
public class DomainLiftingEnginesPreferenceTab extends AbstractDomainPreferenceTab {

	private IPreferenceField ruleBasesField;
	private IPreferenceField recognitionRuleSorterField;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		List<ILiftingRuleBase> ruleBases = new ArrayList<>(PipelineUtils.getAvailableRulebases(Collections.singleton(getDocumentType())));
		Collections.sort(ruleBases, new Comparator<ILiftingRuleBase>() {
			@Override
			public int compare(ILiftingRuleBase o1, ILiftingRuleBase o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		ruleBasesField = PreferenceFieldFactory.createCheckBoxList(
				LiftingSettingsAdapter.KEY_RULE_BASES(getDocumentType()),
				"Rulebases",
				ruleBases,
				new LiftingRuleBaseValueConverter());
		list.add(ruleBasesField);

		List<IRecognitionRuleSorter> recognitionRuleSorters = new ArrayList<>(
				IRecognitionRuleSorter.MANAGER.getExtensions(Collections.singleton(getDocumentType()), true));
		Collections.sort(recognitionRuleSorters, IRecognitionRuleSorter.MANAGER.getComparator());
			
		recognitionRuleSorterField = PreferenceFieldFactory.createRadioBox(
				LiftingSettingsAdapter.KEY_RECOGNITION_RULE_SORTER(getDocumentType()),
				"Recognition Rule Sorter",
				recognitionRuleSorters,
				ExtensionValueConverter.getInstance());
		list.add(recognitionRuleSorterField);
	}
}
