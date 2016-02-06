package org.sidiff.difference.settings.settings;

import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.settings.settings.SiDiffSettings;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.similarities.ISimilarities;
import org.sidiff.similaritiescalculation.ISimilaritiesCalculation;

public class MatchingSettings extends SiDiffSettings {

	/**
	 * The Matcher for calculating correspondences.
	 */
	private IMatcher matcher;
	private ICandidates candidateService;
	private ICorrespondences correspondencesService;
	private ISimilarities similaritiesService;
	private ISimilaritiesCalculation similaritiesCalculationService;
	
	public MatchingSettings(String documentType){
		//TODO
		this(Scope.RESOURCE_SET, MatcherUtil.getMatcher("EcoreIDMatching"));
	}

	public MatchingSettings() {
		this(Scope.RESOURCE_SET, MatcherUtil.getMatcher("EcoreIDMatching"));
	}

	public MatchingSettings(IMatcher matcher) {
		this(Scope.RESOURCE_SET, matcher);
	}

	public MatchingSettings(Scope scope) {
		this(scope, MatcherUtil.getMatcher("EcoreIDMatching"));
	}

	public MatchingSettings(Scope scope, IMatcher matcher) {
		super(scope);
		this.matcher = matcher;
		this.correspondencesService = CorrespondencesUtil.getAvailableCorrespondencesService("MatchingModelCorrespondences");
	}


	/**
	 * @return The Matcher for model comparison.
	 */
	public IMatcher getMatcher() {
		return matcher;
	}
	
	

	public ICandidates getCandidatesService() {
		return candidateService;
	}

	public ICorrespondences getCorrespondencesService() {
		return correspondencesService;
	}

	public ISimilarities getSimilaritiesService() {
		return similaritiesService;
	}

	public ISimilaritiesCalculation getSimilaritiesCalculationService() {
		return similaritiesCalculationService;
	}

	/**
	 * @param matcher
	 *            The Matcher for model comparison.
	 */
	public void setMatcher(IMatcher matcher) {
		if (this.matcher == null || !this.matcher.getName().equals(matcher.getName())) {
			this.matcher = matcher;
			if(correspondencesService != null){
				matcher.setCorrespondencesService(correspondencesService);
			}
			this.notifyListeners(MatchingSettingsItem.MATCHER);
		}
	}

	public void setCandidateService(ICandidates candidateService) {
		this.candidateService = candidateService;
		this.notifyListeners(MatchingSettingsItem.CANDITATE_SERVICE);
	}

	public void setCorrespondencesService(ICorrespondences correspondencesService) {
		this.correspondencesService = correspondencesService;
		if(matcher != null){
			matcher.setCorrespondencesService(correspondencesService);
		}
		this.notifyListeners(MatchingSettingsItem.CORRESPONDENCE_SERVICE);
	}

	public void setSimilaritiesService(ISimilarities similarityService) {
		this.similaritiesService = similarityService;
		this.notifyListeners(MatchingSettingsItem.SIMILARTIY_SERVICE);
	}

	public void setSimilaritiesCalculationService(ISimilaritiesCalculation similaritiesCalculationService) {
		this.similaritiesCalculationService = similaritiesCalculationService;
		this.notifyListeners(MatchingSettingsItem.SIMILARITY_CALCULATION_SERVICE);
	}
	
	
	
}
