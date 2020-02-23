package org.sidiff.integration.preferences.matching.tabs;

import java.util.Collection;
import java.util.List;

import org.sidiff.candidates.ICandidates;
import org.sidiff.integration.preferences.fieldeditors.ICompositePreferenceField;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.matching.settingsadapter.MatchingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.AbstractPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.ExtensionValueConverter;
import org.sidiff.matcher.IMatcher;
import org.sidiff.similarities.ISimilarities;
import org.sidiff.similaritiescalculation.ISimilaritiesCalculation;

/**
 * 
 * Class for the matching settings tab.
 * @author Daniel Roedder
 * @author rmueller
 */
public class MatchingEnginesPreferenceTab extends AbstractPreferenceTab {

	private IPreferenceField matchersField;
	private IPreferenceField candidatesServiceField;
	private IPreferenceField similaritiesServiceField;
	private IPreferenceField similaritiesCalculationServiceField;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		Collection<IMatcher> matchers = IMatcher.MANAGER.getGenericExtensions();
		matchersField = PreferenceFieldFactory.createOrderedList(
				MatchingSettingsAdapter.KEY_MATCHERS, "Matchers",
				matchers, ExtensionValueConverter.getInstance());
		list.add(matchersField);
		ICompositePreferenceField<IPreferenceField> matcherOptions =
				PreferenceFieldFactory.createConfigurableExtensionsFields(
						matchers, matchersField, MatchingSettingsAdapter::KEY_MATCHER_OPTIONS);
		if(!matcherOptions.isEmpty()) {
			list.add(matcherOptions);			
		}

		candidatesServiceField = PreferenceFieldFactory.createRadioBox(
				MatchingSettingsAdapter.KEY_CANDIDATES_SERVICE, "Candidates Service",
				ICandidates.MANAGER.getSortedExtensions(), ExtensionValueConverter.getInstance());
		list.add(candidatesServiceField);

		similaritiesServiceField = PreferenceFieldFactory.createRadioBox(
				MatchingSettingsAdapter.KEY_SIMILARITIES_SERVICE, "Similarities Service",
				ISimilarities.MANAGER.getSortedExtensions(), ExtensionValueConverter.getInstance(), true);
		list.add(similaritiesServiceField);

		similaritiesCalculationServiceField = PreferenceFieldFactory.createRadioBox(
				MatchingSettingsAdapter.KEY_SIMILARITIES_CALCULATION_SERVICE, "Similarities Calculation Service",
				ISimilaritiesCalculation.MANAGER.getSortedExtensions(), ExtensionValueConverter.getInstance(), true);
		list.add(similaritiesCalculationServiceField);
	}
}
