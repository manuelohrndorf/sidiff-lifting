package org.sidiff.integration.preferences.domains.lifting;

import java.util.Collections;

import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.domains.lifting.settingsadapter.DomainLiftingSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.CheckListSelectField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * Class for the domain specific lifting settings.
 * @author Daniel Roedder, cpietsch, Robert Müller
 */
public class DomainLiftingPreferenceTab extends AbstractDomainPreferenceTab {

	/**
	 * The {@link CheckListSelectField} for the available {@link IRuleBase}s
	 */
	private CheckListSelectField ruleBasesField;

	/**
	 * The {@link RadioBoxPreferenceField} for the {@link IRecognitionRuleSorter}s
	 */
	private RadioBoxPreferenceField<?> recognitionRuleSorterField;

	/**
	 * @see org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Lifting";
	}

	@Override
	protected void createPreferenceFields() {
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
		addField(ruleBasesField);

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
		addField(recognitionRuleSorterField);
	}

	@Override
	public int getStepInPipeline() {
		return 2;
	}
}
