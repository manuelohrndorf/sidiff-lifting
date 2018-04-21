package org.sidiff.integration.preferences.domains.lifting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.CheckListSelectField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * Abstract class for the domain specific lifting settings.
 * @author Daniel Roedder, cpietsch, Robert Müller
 */
public class DomainLiftingPreferenceTab extends AbstractDomainPreferenceTab {

	/**
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;

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

	/**
	 * @see org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();

		// TODO: preference name is probably not correct
		ruleBasesField = CheckListSelectField.create(
				getDocumentType(),
				"Rule Bases",
				PipelineUtils.getAvailableRulebases(getDocumentType()),
				new IPreferenceValueConverter<ILiftingRuleBase>() {
					@Override
					public String getValue(ILiftingRuleBase value) {
						return value.getName();
					}
					@Override
					public String getLabel(ILiftingRuleBase value) {
						return value.getName();
					}
				});
		fieldList.add(ruleBasesField);

		recognitionRuleSorterField = RadioBoxPreferenceField.create(
				getDocumentType() + "recognitionRuleSorter", "Recognition Rule Sorter",
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
		fieldList.add(recognitionRuleSorterField);

		return fieldList;
	}

	@Override
	public int getStepInPipeline() {
		return 2;
	}
}
