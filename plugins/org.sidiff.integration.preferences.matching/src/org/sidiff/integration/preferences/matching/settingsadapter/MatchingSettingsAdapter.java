package org.sidiff.integration.preferences.matching.settingsadapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.candidates.CandidatesUtil;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.settings.ISettings;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.integration.preferences.matching.Activator;
import org.sidiff.integration.preferences.settingsadapter.AbstractSettingsAdapter;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.similarities.ISimilarities;
import org.sidiff.similarities.SimilaritiesServiceUtil;
import org.sidiff.similaritiescalculation.ISimilaritiesCalculation;
import org.sidiff.similaritiescalculation.SimilaritiesCalculationUtil;

/**
 * 
 * @author Robert Müller
 *
 */
public class MatchingSettingsAdapter extends AbstractSettingsAdapter {

	public static final String KEY_MATCHERS = "matchers";
	public static String KEY_MATCHERS(String documentType) {
		return KEY_MATCHERS + "[" + documentType + "]";
	}
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
	public boolean canAdapt(ISettings settings) {
		return settings instanceof MatchingSettings;
	}

	@Override
	public void adapt(ISettings settings) {
		MatchingSettings matchingSettings = (MatchingSettings)settings;

		if(isConsidered(MatchingSettingsItem.MATCHER)) {
			IMatcher matcher = createMatcher();
			if(matcher != null) {
				matchingSettings.setMatcher(matcher);
			}
		}
		if(candidatesService != null && isConsidered(MatchingSettingsItem.CANDITATE_SERVICE)) {
			matchingSettings.setCandidatesService(candidatesService);
		}
		if(correspondencesService != null && isConsidered(MatchingSettingsItem.CORRESPONDENCE_SERVICE)) {
			matchingSettings.setCorrespondencesService(correspondencesService);
		}
		if(similaritiesService != null && isConsidered(MatchingSettingsItem.SIMILARTIY_SERVICE)) {
			matchingSettings.setSimilaritiesService(similaritiesService);
		}
		if(similaritiesCalculationService != null && isConsidered(MatchingSettingsItem.SIMILARITY_CALCULATION_SERVICE)) {
			matchingSettings.setSimilaritiesCalculationService(similaritiesCalculationService);
		}
	}

	@Override
	public void load(IPreferenceStore store) {
		loadMatchers(store);
		loadCandidatesService(store);
		loadCorrespondencesService(store);
		loadSimilaritiesService(store);
		loadSimilaritiesCalculationService(store);
	}

	protected void loadMatchers(IPreferenceStore store) {
		// get keys of all domain specific matchers
		List<String> matcherKeys = new ArrayList<String>();
		for(String docType : getDocumentTypes()) {
			for(String matcherKey : store.getString(KEY_MATCHERS(docType)).split(";")) {
				if(!matcherKey.isEmpty()) {
					matcherKeys.add(matcherKey);
				}
			}
		}
		// get keys of all generic matchers
		for(String matcherKey : store.getString(KEY_MATCHERS).split(";")) {
			if(!matcherKey.isEmpty()) {
				matcherKeys.add(matcherKey);
			}
		}
		// get the matchers
		matchers = new ArrayList<IMatcher>();
		for(String matcherKey : matcherKeys) {
			IMatcher matcher = MatcherUtil.getMatcher(matcherKey);
			if(matcher != null) {
				if(matcher instanceof IConfigurable) {
					Map<String, Object> matcherOptions = ((IConfigurable)matcher).getConfigurationOptions();
					for(String optionKey : matcherOptions.keySet()) {
						((IConfigurable)matcher).setConfigurationOption(optionKey,
								store.getBoolean(KEY_MATCHER_OPTIONS(matcherKey) + ":" + optionKey));
					}
				}
				matchers.add(matcher);
			} else {
				addWarning("Matcher with key '" + matcherKey + "' was not found.");
			}
		}
		if(matchers.isEmpty()) {
			addError("No Matchers were specified.");
		}
	}

	protected void loadCandidatesService(IPreferenceStore store) {
		String candidatesServiceId = store.getString(KEY_CANDIDATES_SERVICE);
		candidatesService = CandidatesUtil.getAvailableCandidatesService(candidatesServiceId);
		if(candidatesService == null) {
			addError("Candidates Service with id '" + candidatesServiceId + "' was not found.");
		}
	}

	protected void loadCorrespondencesService(IPreferenceStore store) {
		String correspondencesServiceId = store.getString(KEY_CORRESPONDENCES_SERVICE);
		correspondencesService = CorrespondencesUtil.getAvailableCorrespondencesService(correspondencesServiceId);
		if(correspondencesService == null) {
			addError("Correspondences Service with id '" + correspondencesServiceId + "' was not found.");
		}
	}

	protected void loadSimilaritiesService(IPreferenceStore store) {
		String similaritiesServiceId = store.getString(KEY_SIMILARITIES_SERVICE);
		similaritiesService = SimilaritiesServiceUtil.getAvailableSimilaritiesService(similaritiesServiceId);
		if(similaritiesService == null) {
			addError("Similarities Service with id '" + similaritiesServiceId + "' was not found.");
		}
	}

	protected void loadSimilaritiesCalculationService(IPreferenceStore store) {
		String similaritiesCalculationServiceId = store.getString(KEY_SIMILARITIES_CALCULATION_SERVICE);
		similaritiesCalculationService = SimilaritiesCalculationUtil.getSimilaritiesCalculationService(similaritiesCalculationServiceId);
		if(similaritiesCalculationService == null) {
			addError("Similarities Calculation Service with id '" + similaritiesCalculationServiceId + "' was not found.");
		}
	}

	private IMatcher createMatcher() {
		switch(matchers.size()) {
			case 0: return null;
			case 1: return matchers.get(0);
			default: return new IncrementalMatcher(matchers);
		}
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_MATCHERS, "org.sidiff.matcher.signature.name.NamedElementMatcher");
		store.setDefault(KEY_CANDIDATES_SERVICE, "InterModelTypeCandidates");
		store.setDefault(KEY_CORRESPONDENCES_SERVICE, "MatchingModelCorrespondences");
		store.setDefault(KEY_SIMILARITIES_SERVICE, "DefaultSimilarities");
		store.setDefault(KEY_SIMILARITIES_CALCULATION_SERVICE, "DefaultSimilaritiesCalculationService");
	}

	@Override
	protected BasicDiagnostic getDiagnosticGroup() {
		return new BasicDiagnostic(Activator.PLUGIN_ID, 0, "Matching settings", null);
	}
}
