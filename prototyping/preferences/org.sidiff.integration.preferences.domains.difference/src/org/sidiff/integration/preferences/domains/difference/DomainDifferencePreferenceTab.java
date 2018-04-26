package org.sidiff.integration.preferences.domains.difference;

import java.util.Collections;

import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.domains.difference.settingsadapter.DomainDifferenceSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.OrderListSelectField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * Class for the creation of domain specific difference settings tabs.
 * @author Daniel Roedder, cpietsch, Robert Müller
 */
public class DomainDifferencePreferenceTab extends AbstractDomainPreferenceTab {

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

	@Override
	protected void createPreferenceFields() {
		techDiffBuilderField = OrderListSelectField.create(
				DomainDifferenceSettingsAdapter.KEY_TECHNICAL_DIFFERENCE_BUILDERS(getDocumentType()),
				"Technical Difference Builders",
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
		addField(techDiffBuilderField);
	}

	@Override
	public int getStepInPipeline() {
		return 1;
	}
}
