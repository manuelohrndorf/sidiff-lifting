package org.sidiff.integration.preferences.matching;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.candidates.CandidatesUtil;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.integration.preferences.AbstractEnginePreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.CheckListSelectField;
import org.sidiff.integration.preferences.fieldeditors.OrderListSelectField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
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
public class MatchingEnginesPreferenceTab extends AbstractEnginePreferenceTab {

	/**
	 * The {@link OrderListSelectField} for the order the {@link IMatcher}s are used
	 */
	private OrderListSelectField<?> matchingOrder;

	/**
	 * The {@link RadioBoxPreferenceField} for the candidate service
	 */
	private RadioBoxPreferenceField<?> candidatesServices;

	/**
	 * The {@link PreferenceField} for the correspondences service
	 */
	private PreferenceField correspondencesServices;

	/**
	 * The {@link PreferenceField} for the similarities services
	 */
	private PreferenceField similaritiesServices;

	/**
	 * The {@link PreferenceField} for the similarities calculation services
	 */
	private PreferenceField similaritiesCalculationServices;

	/**
	 * The {@link HashMap} for the matcher options fields
	 */
	private Map<String, PreferenceField> matcherOptions;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Matching";
	}

	@Override
	protected void createPreferenceFields() {
		List<IMatcher> matchers = MatcherUtil.getAllAvailableMatchers();
		matchingOrder = OrderListSelectField.create(
				"matchingOrder",
				"Matching Order",
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
		addField(matchingOrder);

		// TODO: matcher options are untested
		matcherOptions = new HashMap<String, PreferenceField>();
		for(IMatcher matcher : matchers) {
			if(matcher instanceof IConfigurable) {
				PreferenceField matcherOption = CheckListSelectField.create(
						matcher.getKey(), matcher.getName(),
						((IConfigurable)matcher).getConfigurationOptions().keySet(),
						new IdentityPreferenceValueConverter());
				addField(matcherOption);
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

		candidatesServices = RadioBoxPreferenceField.create("candidatesServices", "Candidates Services",
				CandidatesUtil.getAvailableCandidatesServices(), serviceValueConverter);
		addField(candidatesServices);

		correspondencesServices = RadioBoxPreferenceField.create("correspondencesServices", "Correspondences Services",
				CorrespondencesUtil.getAllAvailableCorrespondencesServices(), serviceValueConverter);
		addField(correspondencesServices);

		similaritiesServices = RadioBoxPreferenceField.create("similaritiesServices", "Similarities Services",
				SimilaritiesServiceUtil.getAvailableSimilaritiesService(), serviceValueConverter);
		addField(similaritiesServices);

		similaritiesCalculationServices = RadioBoxPreferenceField.create(
				"similaritiesCalculationServices", "Similarities Calculation Services",
				SimilaritiesCalculationUtil.getAvailableISimilaritiesCalculationServices(),
				serviceValueConverter);
		addField(similaritiesCalculationServices);
	}

	/**
	 * Superclass method, used to enable / disable matcher specific options if the matcher is enabled / disabled
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getSource() == matchingOrder) {
			List<String> newValue = Arrays.asList((String[]) event.getNewValue());
			for(Entry<String, PreferenceField> matcherOptions : this.matcherOptions.entrySet()) {
				if(newValue.contains(matcherOptions.getKey())) {
					matcherOptions.getValue().enable();
				} else {
					matcherOptions.getValue().disable();
				}
			}
		}
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 0;
	}
}
