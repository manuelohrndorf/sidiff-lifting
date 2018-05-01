package org.sidiff.integration.preferences.domains.lifting;

import java.util.Collections;
import java.util.List;

import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.domains.lifting.settingsadapter.DomainLiftingSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.CheckListSelectField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * Class for the domain specific lifting settings.
 * @author Daniel Roedder, cpietsch, Robert Müller
 */
public class DomainLiftingPreferenceTab extends AbstractDomainPreferenceTab {

	private CheckListSelectField ruleBasesField;
	private RadioBoxPreferenceField<?> recognitionRuleSorterField;

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
		ruleBasesField = CheckListSelectField.create(
				DomainLiftingSettingsAdapter.KEY_RULE_BASES(getDocumentType()),
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

		recognitionRuleSorterField = RadioBoxPreferenceField.create(
				DomainLiftingSettingsAdapter.KEY_RECOGNITION_RULE_SORTER(getDocumentType()),
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
