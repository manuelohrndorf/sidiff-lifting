package org.sidiff.integration.preferences.matching.settingsadapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.candidates.CandidatesUtil;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.integration.preferences.interfaces.AbstractSettingsAdapter;
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
 * @author Robert M�ller
 *
 */
public class MatchingSettingsAdapter extends AbstractSettingsAdapter {

	public static final String KEY_MATCHERS = "matchers";
	public static String KEY_MATCHER_OPTIONS(String matcher) {
		return "matcherOptions[" + matcher + "]";
	}
	public static final String KEY_CANDIDATES_SERVICE = "candidatesService";
	public static final String KEY_CORRESPONDENCES_SERVICE = "correspondencesService";
	public static final String KEY_SIMILARITIES_SERVICE = "similaritiesService";
	public static final String KEY_SIMILARITIES_CALCULATION_SERVICE = "similaritiesCalculationService";

	private List<IMatcher> matchers;
	private ICandidates candidatesService;
	private ICorrespondences correspondencesService;
	private ISimilarities similaritiesService;
	private ISimilaritiesCalculation similaritiesCalculationService;

	@Override
	public boolean canAdapt(AbstractSettings settings) {
		return settings instanceof MatchingSettings;
	}

	@Override
	public void adapt(AbstractSettings settings) {
		MatchingSettings matchingSettings = (MatchingSettings)settings;
		matchingSettings.setMatcher(createMatcher());
		matchingSettings.setCandidatesService(candidatesService);
		matchingSettings.setCorrespondencesService(correspondencesService);
		matchingSettings.setSimilaritiesService(similaritiesService);
		matchingSettings.setSimilaritiesCalculationService(similaritiesCalculationService);
	}

	@Override
	public void load(IPreferenceStore store) {
		matchers = new ArrayList<IMatcher>();
		for (String matcherKey : store.getString(KEY_MATCHERS).split(";")) {
			IMatcher matcher = MatcherUtil.getMatcher(matcherKey);

			if (matcher instanceof IConfigurable) {
				Map<String, Object> matcherOptions = ((IConfigurable)matcher).getConfigurationOptions();
				for (String optionKey : matcherOptions.keySet()) {
					((IConfigurable)matcher).setConfigurationOption(optionKey,
							store.getBoolean(KEY_MATCHER_OPTIONS(matcherKey) + ":" + optionKey));
				}
			}

			matchers.add(matcher);
		}

		candidatesService = CandidatesUtil.getAvailableCandidatesService(store.getString(KEY_CANDIDATES_SERVICE));

		correspondencesService = CorrespondencesUtil.getAvailableCorrespondencesService(store.getString(KEY_CORRESPONDENCES_SERVICE));

		similaritiesService = SimilaritiesServiceUtil.getAvailableSimilaritiesService(store.getString(KEY_SIMILARITIES_SERVICE));

		similaritiesCalculationService = SimilaritiesCalculationUtil.getSimilaritiesCalculationService(
				store.getString(KEY_SIMILARITIES_CALCULATION_SERVICE));
	}

	private IMatcher createMatcher() {
		if(matchers.size() == 1)
			return matchers.get(0);
		return new IncrementalMatcher(matchers);
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_MATCHERS, "org.sidiff.matcher.signature.name.NamedElementMatcher");
		store.setDefault(KEY_CANDIDATES_SERVICE, "InterModelTypeCandidates");
		store.setDefault(KEY_CORRESPONDENCES_SERVICE, "MatchingModelCorrespondences");
		store.setDefault(KEY_SIMILARITIES_SERVICE, "DefaultSimilaritiesService");
		store.setDefault(KEY_SIMILARITIES_CALCULATION_SERVICE, "DefaultSimilaritiesCalculationService");
	}
}
