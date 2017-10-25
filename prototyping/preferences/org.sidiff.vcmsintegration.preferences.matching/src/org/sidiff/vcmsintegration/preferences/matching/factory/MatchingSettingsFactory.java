package org.sidiff.vcmsintegration.preferences.matching.factory;

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
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.similarities.ISimilarities;
import org.sidiff.similarities.SimilaritiesServiceUtil;
import org.sidiff.similaritiescalculation.ISimilaritiesCalculation;
import org.sidiff.similaritiescalculation.SimilaritiesCalculationUtil;
import org.sidiff.vcmsintegration.preferences.exceptions.InvalidSettingsException;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffSettingsFactory;

/**
 * 
 * Factory class for {@link org.sidiff.matching.api.settings.MatchingSettings}
 * @author Daniel Roedder
 */
public class MatchingSettingsFactory implements ISiDiffSettingsFactory{

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
		
		String matchers = store.getString("matchingOrder");
		String[] matchers2 = matchers.split(";");
		
		matcherList = new ArrayList<IMatcher>();
		
		for (String matcherKey : matchers2) {
			IMatcher matcher = MatcherUtil.getMatcher(matcherKey);
			
			if (matcher instanceof IConfigurable) {
				Map<String, Object> matcherOptions = ((IConfigurable) matcher).getConfigurationOptions();
				
				for (String optionKey : matcherOptions.keySet()) {
					((IConfigurable) matcher).setConfigurationOption(optionKey, store.getBoolean(matcherKey + ":" + optionKey));
				}
			}
			
			matcherList.add(matcher);
		}
		
		candidatesService = CandidatesUtil.getAvailableCandidatesService(store.getString("candidatesServices"));
		
		correspondencesService = CorrespondencesUtil.getAvailableCorrespondencesService(store.getString("correspondencesServices"));
		
		similaritiesService = SimilaritiesServiceUtil.getAvailableSimilaritiesService("similaritiesServices");
		
		similaritiesCalculationService = SimilaritiesCalculationUtil.getSimilaritiesCalculationServiceInstance();
		
		
	}
	
	/**
	 * @see ISiDiffSettingsFactory
	 */
	public MatchingSettings getSettings() throws InvalidSettingsException {
		
		MatchingSettings settings;
		
		if (matcherList.size() == 1) {
			settings = new MatchingSettings(scope, validate, matcherList.get(0), candidatesService, correspondencesService, similaritiesService, similaritiesCalculationService);
			
			if (!settings.validateSettings()) throw new InvalidSettingsException();
			return settings;
		}
		
		else {
			IncrementalMatcher incMatcher = new IncrementalMatcher(matcherList);
			settings = new MatchingSettings(scope, validate, incMatcher, candidatesService, correspondencesService, similaritiesService, similaritiesCalculationService);
			
			if (!settings.validateSettings()) throw new InvalidSettingsException();
			return settings;
		}
		
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffSettingsFactory#getFeatureLevel()
	 */
	@Override
	public String getFeatureLevel() {
		return "Matching";
	}



}
