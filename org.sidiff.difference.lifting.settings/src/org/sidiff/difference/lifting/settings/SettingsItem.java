package org.sidiff.difference.lifting.settings;

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
	 * {@link Settings#setTechBuilder(ITechnicalDifferenceBuilder)}
	 */
	TECH_BUILDER,
	
	/**
	 * {@link Settings#setRuleBases(Set)}
	 */
	RULEBASES,
	
	/**
	 * {@link LiftingSettings#setRuleSetReduction(boolean)}
	 */
	RULE_SET_REDUCTION,
	
	/**
	 * {@link LiftingSettings#setSortRecognitionRuleNodes(boolean)}
	 */
	SORT_RECOGNITION_RULE_NODES,
	
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
	BUILD_GRAPH_PER_RULE
}
