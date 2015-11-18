package org.sidiff.difference.lifting.settings;

import java.util.Set;

import javax.inject.Scope;

/**
 * Enumerations which are associated with a differencing setting.
 */
public enum SettingsItem {
	
	/**
	 * {@link Settings#setScope(Scope)}
	 */
	SCOPE, 
	
	/**
	 * {@link Settings#setMatcher(IMatcher)}
	 */
	MATCHER, 
	
	/**
	 * {@link Settings#setSymbolicLinkHandler(ISymbolicLinkHandler)}
	 */
	SYMBOLIC_LINK_HANDLER,
	
	/**
	 * {@link Settings#setTechBuilder(ITechnicalDifferenceBuilder)}
	 */
	TECH_BUILDER,
	
	/**
	 * {@link LiftingSettings#setRrSorter(IRecognitionRuleSorter)}
	 */
	RECOGNITION_RULE_SORTER,
	
	/**
	 * {@link Settings#setRuleBases(Set)}
	 */
	RULEBASES,
	
	/**
	 * {@link LiftingSettings#setRuleSetReduction(boolean)}
	 */
	RULE_SET_REDUCTION,
	
	/**
	 * {@link LiftingSettings#setUseThreadPool(boolean)}
	 */
	USE_THREAD_POOL,
	
	/**
	 * {@link LiftingSettings#setNumberOfThreads(int)}
	 */
	NUMBER_OF_THREADS,
	
	/**
	 * {@link LiftingSettings#setRulesPerThread(int)}
	 */
	RULES_PER_THREAD,
	
	/**
	 * {@link LiftingSettings#setBuildGraphPerRule(boolean)}
	 */
	BUILD_GRAPH_PER_RULE,
	
	/**
	 * {@link LiftingSettings#setCalculateEditRuleMatch(boolean)}
	 */
	CALCULATE_EDIT_RULE_MATCH,
	
	/**
	 * {@link LiftingSettings#setSerializeEditRuleMatch(boolean)}
	 */
	SERIALIZE_EDIT_RULE_MATCH;
}
