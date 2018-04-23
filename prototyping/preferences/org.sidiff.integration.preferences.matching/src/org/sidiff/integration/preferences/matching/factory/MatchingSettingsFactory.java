package org.sidiff.integration.preferences.matching.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.candidates.CandidatesUtil;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.integration.preferences.exceptions.InvalidSettingsException;
import org.sidiff.integration.preferences.interfaces.ISiDiffSettingsFactory;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.similarities.ISimilarities;
import org.sidiff.similarities.SimilaritiesServiceUtil;
import org.sidiff.similaritiescalculation.ISimilaritiesCalculation;
import org.sidiff.similaritiescalculation.SimilaritiesCalculationUtil;

/**
 * 
 * Factory class for {@link org.sidiff.matching.api.settings.MatchingSettings}
 * @author Daniel Roedder, Robert Müller
 */
public class MatchingSettingsFactory implements ISiDiffSettingsFactory {

	/**
	 * The {@link Scope} to be used
	 */
	protected Scope scope;

	/**
	 * Boolean field for the validation setting
	 */
	protected boolean validate;

	/**
	 * The {@link List} of {@link IMatcher}s in the order to be used
	 */
	protected List<IMatcher> matcherList;

	/**
	 * The {@link ICandidates} to be used
	 */
	protected ICandidates candidatesService;

	/**
	 * The {@link ICorrespondences} to be used
	 */
	protected ICorrespondences correspondencesService;

	/**
	 * The {@link ISimilarities} to be used
	 */
	protected ISimilarities similaritiesService;

	/**
	 * The {@link ISimilaritiesCalculation} to be used
	 */
	protected ISimilaritiesCalculation similaritiesCalculationService;

	/**
	 * @see ISiDiffSettingsFactory
	 */
	public void doSetFields(String documentType, IPreferenceStore store) {
		scope = Scope.valueOf(store.getString("scope"));
		validate = store.getBoolean("validateModels");

		matcherList = new ArrayList<IMatcher>();
		for (String matcherKey : store.getString("matchingOrder").split(";")) {
			IMatcher matcher = MatcherUtil.getMatcher(matcherKey);

			// TODO: matcher options are untested
			if (matcher instanceof IConfigurable) {
				Map<String, Object> matcherOptions = ((IConfigurable)matcher).getConfigurationOptions();
				for (String optionKey : matcherOptions.keySet()) {
					((IConfigurable)matcher).setConfigurationOption(optionKey, store.getBoolean(matcherKey + ":" + optionKey));
				}
			}

			matcherList.add(matcher);
		}

		candidatesService = CandidatesUtil.getAvailableCandidatesService(store.getString("candidatesServices"));

		correspondencesService = CorrespondencesUtil.getAvailableCorrespondencesService(store.getString("correspondencesServices"));

		similaritiesService = SimilaritiesServiceUtil.getAvailableSimilaritiesService(store.getString("similaritiesServices"));

		similaritiesCalculationService = SimilaritiesCalculationUtil.getSimilaritiesCalculationService(
				store.getString("similaritiesCalculationServices"));
	}

	/**
	 * @see ISiDiffSettingsFactory
	 */
	public MatchingSettings getSettings() throws InvalidSettingsException {
		MatchingSettings settings = new MatchingSettings(
				scope, validate, createMatcher(), candidatesService,
				correspondencesService, similaritiesService, similaritiesCalculationService);
		if (!settings.validateSettings()) throw new InvalidSettingsException();
		return settings;
	}

	protected IMatcher createMatcher() {
		if(matcherList.size() == 1)
			return matcherList.get(0);
		return new IncrementalMatcher(matcherList);
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffSettingsFactory#getFeatureLevel()
	 */
	@Override
	public String getFeatureLevel() {
		return "Matching";
	}
}
