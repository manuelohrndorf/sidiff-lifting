package org.sidiff.integration.preferences.lifting.tabs;

import java.util.Collections;
import java.util.List;

import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.lifting.settingsadapter.LiftingSettingsAdapter;
import org.sidiff.integration.preferences.lifting.valueconverters.LiftingRuleBaseValueConverter;
import org.sidiff.integration.preferences.lifting.valueconverters.RecognitionRuleSorterValueConverter;
import org.sidiff.integration.preferences.tabs.AbstractDomainPreferenceTab;

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
				new LiftingRuleBaseValueConverter());
		list.add(ruleBasesField);

		recognitionRuleSorterField = PreferenceFieldFactory.createRadioBox(
				LiftingSettingsAdapter.KEY_RECOGNITION_RULE_SORTER(getDocumentType()),
				"Recognition Rule Sorter",
				PipelineUtils.getAvailableRecognitionRuleSorters(Collections.singleton(getDocumentType())),
				new RecognitionRuleSorterValueConverter());
		list.add(recognitionRuleSorterField);
	}
}
