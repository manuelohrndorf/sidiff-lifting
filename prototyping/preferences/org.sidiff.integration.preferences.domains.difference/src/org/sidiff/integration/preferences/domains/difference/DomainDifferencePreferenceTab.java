package org.sidiff.integration.preferences.domains.difference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.OrderListSelectField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * Abstract class for the creation of domain specific difference settings tabs.
 * @author Daniel Roedder, cpietsch, Robert Müller
 */
public class DomainDifferencePreferenceTab extends AbstractDomainPreferenceTab {

	/**
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;

	/**
	 * {@link org.sidiff.integration.preferences.fieldeditors.OrderListSelectField} for the {@link ITechnicalDifferenceBuilder}s
	 */
	private OrderListSelectField<?> techDiffBuilderField;

	/**
	 * @see org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Difference";
	}

	/**
	 * @see org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();

		techDiffBuilderField = OrderListSelectField.create(
				getDocumentType() + "technicalDifferenceBuilderOrder",
				"Technical Difference Builder Order",
				TechnicalDifferenceUtils.getAvailableTechnicalDifferenceBuilders(Collections.singleton(getDocumentType())),
				new IPreferenceValueConverter<ITechnicalDifferenceBuilder>() {
					@Override
					public String getValue(ITechnicalDifferenceBuilder value) {
						return value.getKey();
					}
					@Override
					public String getLabel(ITechnicalDifferenceBuilder value) {
						return value.getName();
					}
				});

		fieldList.add(techDiffBuilderField);
		return fieldList;
	}

	@Override
	public int getStepInPipeline() {
		return 1;
	}
}
