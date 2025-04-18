package org.sidiff.difference.lifting.api.settings;

import org.sidiff.common.emf.settings.ISettingsItem;

/**
 * Enumerations which are associated with a {@link LiftingSettings lifting setting}.
 */
public enum LiftingSettingsItem implements ISettingsItem {

	/**
	 * {@link LiftingSettings#setRrSorter(IRecognitionRuleSorter)}
	 */
	RECOGNITION_RULE_SORTER,

	/**
	 * {@link LiftingSettings#setRuleBases(Set)}
	 */
	RULEBASES,
	
	/**
	 * {@link LiftingSettings#setOptimizeMatchingEngine(boolean)}
	 */
	OPTIMIZE_MATCHING_ENGINE,

	/**
	 * {@link LiftingSettings#setSortRecognitionRuleNodes(boolean)}
	 */
	SORT_RECOGNITIONRULE_NODES, 

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
	SERIALIZE_EDIT_RULE_MATCH,

	/**
	 * {@link LiftingSettings#setRecognitionEngineMode(RecognitionEngineMode)}
	 */
	RECOGNITION_ENGINE_MODE,

	/**
	 * {@link LiftingSettings#setRecognitionEngine(IRecognitionEngine)}
	 */
	RECOGNITION_ENGINE,

	/**
	 * {@link LiftingSettings#setDetectSplitJoins(boolean)}
	 */
	DETECT_SPLIT_JOINS,

	/**
	 * {@link LiftingSettings#setComparator(Comparator)}
	 */
	COMPARATOR
}
