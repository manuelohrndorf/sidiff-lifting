package org.sidiff.integration.preferences.matching;

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
import org.sidiff.integration.preferences.fieldeditors.CheckListSelectField;
import org.sidiff.integration.preferences.fieldeditors.OrderListSelectField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.interfaces.IPreferenceTab;
import org.sidiff.integration.preferences.matching.settingsadapter.MatchingSettingsAdapter;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.integration.preferences.valueconverters.IdentityPreferenceValueConverter;
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

	private OrderListSelectField<?> matchersField;
	private Map<String, PreferenceField> matcherOptions;
	private RadioBoxPreferenceField<?> candidatesServiceField;
	private PreferenceField correspondencesServiceField;
	private PreferenceField similaritiesServiceField;
	private PreferenceField similaritiesCalculationServiceField;

	@Override
	public TabPage getPage() {
		return TabPage.ENGINES;
	}

	@Override
	public String getTitle() {
		return "Matching";
	}

	@Override
	public int getPosition() {
		return 10;
	}

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		List<IMatcher> matchers = MatcherUtil.getAllAvailableMatchers();
		matchersField = OrderListSelectField.create(
				MatchingSettingsAdapter.KEY_MATCHERS,
				"Matchers",
				matchers,
				new IPreferenceValueConverter<IMatcher>() {
					@Override
					public String getValue(IMatcher value) {
						return value.getKey();
					}
					@Override
					public String getLabel(IMatcher value) {
						return value.getName();
					}
				});
		matchersField.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				List<String> newValue = Arrays.asList((String[])event.getNewValue());
				for(Entry<String, PreferenceField> matcherOptions : matcherOptions.entrySet()) {
					matcherOptions.getValue().setEnabled(newValue.contains(matcherOptions.getKey()));
				}
			}
		});
		list.add(matchersField);

		matcherOptions = new HashMap<String, PreferenceField>();
		for(IMatcher matcher : matchers) {
			if(matcher instanceof IConfigurable) {
				PreferenceField matcherOption = CheckListSelectField.create(
						MatchingSettingsAdapter.KEY_MATCHER_OPTIONS(matcher.getKey()),
						matcher.getName(),
						((IConfigurable)matcher).getConfigurationOptions().keySet(),
						new IdentityPreferenceValueConverter());
				list.add(matcherOption);
				matcherOptions.put(matcher.getKey(), matcherOption);
			}
		}

		IPreferenceValueConverter<IService> serviceValueConverter = new IPreferenceValueConverter<IService>() {
			@Override
			public String getValue(IService value) {
				return value.getServiceID();
			}
			@Override
			public String getLabel(IService value) {
				return value.getClass().getSimpleName();
			}
		};

		candidatesServiceField = RadioBoxPreferenceField.create(
				MatchingSettingsAdapter.KEY_CANDIDATES_SERVICE, "Candidates Service",
				CandidatesUtil.getAvailableCandidatesServices(), serviceValueConverter);
		list.add(candidatesServiceField);

		correspondencesServiceField = RadioBoxPreferenceField.create(
				MatchingSettingsAdapter.KEY_CORRESPONDENCES_SERVICE, "Correspondences Service",
				CorrespondencesUtil.getAllAvailableCorrespondencesServices(), serviceValueConverter);
		list.add(correspondencesServiceField);

		similaritiesServiceField = RadioBoxPreferenceField.create(
				MatchingSettingsAdapter.KEY_SIMILARITIES_SERVICE, "Similarities Service",
				SimilaritiesServiceUtil.getAvailableSimilaritiesService(), serviceValueConverter);
		list.add(similaritiesServiceField);

		similaritiesCalculationServiceField = RadioBoxPreferenceField.create(
				MatchingSettingsAdapter.KEY_SIMILARITIES_CALCULATION_SERVICE, "Similarities Calculation Service",
				SimilaritiesCalculationUtil.getAvailableISimilaritiesCalculationServices(),
				serviceValueConverter);
		list.add(similaritiesCalculationServiceField);
	}
}
