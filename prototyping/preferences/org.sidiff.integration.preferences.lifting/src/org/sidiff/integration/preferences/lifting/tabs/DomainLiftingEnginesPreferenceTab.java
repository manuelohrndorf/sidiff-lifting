package org.sidiff.integration.preferences.lifting.tabs;

import java.util.Collections;
import java.util.List;

import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.lifting.settingsadapter.LiftingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

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
		ruleBasesField = PreferenceFieldFactory.createCheckBoxList(
				LiftingSettingsAdapter.KEY_RULE_BASES(getDocumentType()),
				"Rulebases",
				PipelineUtils.getAvailableRulebases(getDocumentType()),
				new IPreferenceValueConverter<ILiftingRuleBase>() {
					@Override
					public String getValue(ILiftingRuleBase value) {
						// TODO: this is not a good value to permanently save
						return value.getName();
					}
					@Override
					public String getLabel(ILiftingRuleBase value) {
						return value.getName();
					}
				});
		list.add(ruleBasesField);

		recognitionRuleSorterField = PreferenceFieldFactory.createRadioBox(
				LiftingSettingsAdapter.KEY_RECOGNITION_RULE_SORTER(getDocumentType()),
				"Recognition Rule Sorter",
				PipelineUtils.getAvailableRecognitionRuleSorters(Collections.singleton(getDocumentType())),
				new IPreferenceValueConverter<IRecognitionRuleSorter>() {
					@Override
					public String getValue(IRecognitionRuleSorter value) {
						return value.getKey();
					}
					@Override
					public String getLabel(IRecognitionRuleSorter value) {
						return value.getName();
					}
				});
		list.add(recognitionRuleSorterField);
	}
}
