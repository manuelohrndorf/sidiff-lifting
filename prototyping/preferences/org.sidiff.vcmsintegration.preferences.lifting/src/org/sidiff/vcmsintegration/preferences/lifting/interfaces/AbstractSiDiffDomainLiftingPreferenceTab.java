package org.sidiff.vcmsintegration.preferences.lifting.interfaces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab;
import org.sidiff.vcmsintegration.preferences.fieldeditors.CheckListSelectField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.ValueAndLabelProvider;

/**
 * 
 * Abstract class for the domain specific lifting settings.
 * @author Daniel Roedder, cpietsch
 */
public abstract class AbstractSiDiffDomainLiftingPreferenceTab implements ISiDiffDomainPreferenceTab {

	/**
	 * List to hold all {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField}
	 */
	private ArrayList<PreferenceField> fieldList;

	/**
	 * The {@link CheckListSelectField} for the available {@link IRuleBase}s
	 */
	private CheckListSelectField ruleBasesField;
	
	/**
	 * The {@link RadioBoxPreferenceField} for the {@link IRecognitionRuleSorter}s
	 */
	private RadioBoxPreferenceField recognitionRuleSorterField;

	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Lifting";
	}

	/**
	 * Superclass method, not needed here
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {

	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();

		Set<ILiftingRuleBase> bases = PipelineUtils.getAvailableRulebases(getDocumentType());
		ruleBasesField = CheckListSelectField.createFromIterable(getDocumentType(), "Rule Bases", bases,
				new ValueAndLabelProvider<ILiftingRuleBase>() {
					@Override
					public String[] get(ILiftingRuleBase input) {
						return new String[] { input.getName(), input.getName() };
					}
				});

		fieldList.add(ruleBasesField);

		Set<String> documentTypes = new HashSet<String>();
		documentTypes.add(getDocumentType());
		Set<IRecognitionRuleSorter> ruleSorters = PipelineUtils.getAvailableRecognitionRuleSorters(documentTypes);
		recognitionRuleSorterField = RadioBoxPreferenceField.createFromIterable(
				getDocumentType().concat("recognitionRuleSorter"), "Recognition Rule Sorter", ruleSorters,
				new ValueAndLabelProvider<IRecognitionRuleSorter>() {
					@Override
					public String[] get(IRecognitionRuleSorter input) {
						return new String[] { input.getKey(), input.getName() };
					}
				});

		fieldList.add(recognitionRuleSorterField);

		return fieldList;
	}

	/**
	 * Not implemented interface method.
	 * Subclasses must implement.
	 */
	@Override
	public abstract String getDocumentType();

}
