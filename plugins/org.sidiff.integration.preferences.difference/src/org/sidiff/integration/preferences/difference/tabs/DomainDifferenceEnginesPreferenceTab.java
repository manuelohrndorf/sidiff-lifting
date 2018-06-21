package org.sidiff.integration.preferences.difference.tabs;

import java.util.Collections;
import java.util.List;

import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.integration.preferences.difference.settingsadapter.DifferenceSettingsAdapter;
import org.sidiff.integration.preferences.difference.valueconverters.TechnicalDifferenceBuilderValueConverter;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.tabs.AbstractDomainPreferenceTab;

/**
 * 
 * Class for the creation of domain specific difference settings tabs.
 * @author Daniel Roedder, cpietsch, Robert Müller
 */
public class DomainDifferenceEnginesPreferenceTab extends AbstractDomainPreferenceTab {

	private IPreferenceField techDiffBuilderField;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		techDiffBuilderField = PreferenceFieldFactory.createOrderedList(
				DifferenceSettingsAdapter.KEY_TECHNICAL_DIFFERENCE_BUILDERS(getDocumentType()),
				"Technical Difference Builders",
				TechnicalDifferenceUtils.getAvailableTechnicalDifferenceBuilders(Collections.singleton(getDocumentType())),
				new TechnicalDifferenceBuilderValueConverter());
		list.add(techDiffBuilderField);
	}
}
