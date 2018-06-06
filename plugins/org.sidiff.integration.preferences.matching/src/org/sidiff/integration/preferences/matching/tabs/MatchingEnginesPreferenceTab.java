package org.sidiff.integration.preferences.matching.tabs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.candidates.CandidatesUtil;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.matching.settingsadapter.MatchingSettingsAdapter;
import org.sidiff.integration.preferences.matching.valueconverters.MatcherValueConverter;
import org.sidiff.integration.preferences.matching.valueconverters.ServiceValueConverter;
import org.sidiff.integration.preferences.matching.valueconverters.SimilaritiesValueConverter;
import org.sidiff.integration.preferences.tabs.IPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.GenericPreferenceValueConverter;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.service.IService;
import org.sidiff.similarities.SimilaritiesServiceUtil;
import org.sidiff.similaritiescalculation.SimilaritiesCalculationUtil;

/**
 * 
 * Class for the matching settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class MatchingEnginesPreferenceTab implements IPreferenceTab {

	private IPreferenceField matchersField;
	private Map<String, IPreferenceField> matcherOptions;
	private IPreferenceField candidatesServiceField;
	private IPreferenceField correspondencesServiceField;
	private IPreferenceField similaritiesServiceField;
	private IPreferenceField similaritiesCalculationServiceField;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		List<IMatcher> matchers = MatcherUtil.getGenericMatchers();
		matchersField = PreferenceFieldFactory.createOrderedList(
				MatchingSettingsAdapter.KEY_MATCHERS, "Matchers",
				matchers, new MatcherValueConverter());
		matchersField.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				List<String> newValue = Arrays.asList((String[])event.getNewValue());
				for(Entry<String, IPreferenceField> matcherOptions : matcherOptions.entrySet()) {
					matcherOptions.getValue().setVisible(newValue.contains(matcherOptions.getKey()));
				}
			}
		});
		list.add(matchersField);

		matcherOptions = new HashMap<String, IPreferenceField>();
		for(IMatcher matcher : matchers) {
			if(matcher instanceof IConfigurable) {
				IPreferenceField matcherOption = PreferenceFieldFactory.createCheckBoxList(
						MatchingSettingsAdapter.KEY_MATCHER_OPTIONS(matcher.getKey()),
						matcher.getName(),
						((IConfigurable)matcher).getConfigurationOptions().keySet(),
						new GenericPreferenceValueConverter());
				list.add(matcherOption);
				matcherOptions.put(matcher.getKey(), matcherOption);
			}
		}

		final IPreferenceValueConverter<IService> serviceValueConverter = new ServiceValueConverter();

		candidatesServiceField = PreferenceFieldFactory.createRadioBox(
				MatchingSettingsAdapter.KEY_CANDIDATES_SERVICE, "Candidates Service",
				CandidatesUtil.getAvailableCandidatesServices(), serviceValueConverter);
		list.add(candidatesServiceField);

		correspondencesServiceField = PreferenceFieldFactory.createRadioBox(
				MatchingSettingsAdapter.KEY_CORRESPONDENCES_SERVICE, "Correspondences Service",
				CorrespondencesUtil.getAllAvailableCorrespondencesServices(), serviceValueConverter);
		list.add(correspondencesServiceField);

		similaritiesServiceField = PreferenceFieldFactory.createRadioBox(
				MatchingSettingsAdapter.KEY_SIMILARITIES_SERVICE,
				"Similarities Service",
				SimilaritiesServiceUtil.getAvailableSimilaritiesService(),
				new SimilaritiesValueConverter());
		list.add(similaritiesServiceField);

		similaritiesCalculationServiceField = PreferenceFieldFactory.createRadioBox(
				MatchingSettingsAdapter.KEY_SIMILARITIES_CALCULATION_SERVICE, "Similarities Calculation Service",
				SimilaritiesCalculationUtil.getAvailableISimilaritiesCalculationServices(),
				serviceValueConverter);
		list.add(similaritiesCalculationServiceField);
	}
}
