package org.sidiff.difference.lifting.api.settings;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionEngine;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterLibrary;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.editrule.rulebase.project.runtime.library.RuleBaseProjectLibrary;
import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;
import org.sidiff.matcher.IMatcher;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

public class LiftingSettings extends DifferenceSettings {

	/**
	 * List of active Rulebases.
	 */
	private Set<ILiftingRuleBase> ruleBases;

	/**
	 * The Recognition Rule Sorter to use. ( {@link IRecognitionRuleSorter})
	 */
	private IRecognitionRuleSorter rrSorter;

	/**
	 * The engine which executes the recognition rules.
	 */
	private IRecognitionEngine recognitionEngine;

	/**
	 * Triggering of the Recognition-Engine pipeline.
	 * 
	 * @see LiftingSettings.RecognitionEngineMode (Default: Post processed
	 *      operation detection)
	 */
	private RecognitionEngineMode recognitionEngineMode = RecognitionEngineMode.LIFTING_AND_POST_PROCESSING;

	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 */
	private boolean useThreadPool = true;

	/**
	 * Number of recognizer threads in the tread pool.
	 */
	private int numberOfThreads = 10;

	/**
	 * Recognition-Rules per recognizer thread.
	 */
	private int rulesPerThread = 10;

	/**
	 * Optimizes the (matching) order of the nodes in the Recognition-Rules.
	 * (Default (strongly recommended): <code>true</code>)
	 */
	private boolean sortRecognitionRuleNodes = true;

	/**
	 * Optimization: Filtering of unmatchable Recognition-Rules. (Default:
	 * <code>true</code>)
	 */
	private boolean ruleSetReduction = true;

	/**
	 * <p>
	 * Optimization: (Default: <code>true</code>)
	 * </p>
	 * <ul>
	 * <li><code>true</code>: Builds a minimal working graph for each
	 * Recognition-Rule.
	 * <li><code>false</code>: Build a single working graph for all
	 * Recognition-Rule.
	 * </ul>
	 */
	private boolean buildGraphPerRule = true;

	/**
	 * Whether to calculate the EditRuleMatch or not.
	 */
	private boolean calculateEditRuleMatch = true;

	/**
	 * Whether to serialize the EditRuleMatch or not.
	 */
	private boolean serializeEditRuleMatch = true;

	/**
	 * Whether to detect Split/Joins or not
	 */
	private boolean detectSplitJoins = false;
	
	private Comparator<SemanticChangeSet> comparator;

	public Comparator<SemanticChangeSet> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<SemanticChangeSet> comparator) {
		this.comparator = comparator;
	}

	/**
	 * Setup the lifting settings.
	 */
	public LiftingSettings() {
		super();

		// Default: Use the default RecognitionRuleSorter
		Set<String> genericDocumentTypes = new HashSet<String>();
		genericDocumentTypes.add(EMFModelAccess.GENERIC_DOCUMENT_TYPE);

		this.rrSorter = RecognitionRuleSorterLibrary.getDefaultRecognitionRuleSorter(genericDocumentTypes);
	}

	/**
	 * default {@link LiftingSettings}
	 * 
	 * @param Set
	 *            of documentTypes the document types of the models which are
	 *            compared
	 */
	public LiftingSettings(Set<String> documentTypes) {
		super(documentTypes);
		this.ruleBases = RuleBaseProjectLibrary.getRuleBases(documentTypes, ILiftingRuleBase.TYPE);
		
		// Search proper recognition rule sorter:
		this.rrSorter = RecognitionRuleSorterLibrary.getDefaultRecognitionRuleSorter(documentTypes);
		
		if (rrSorter == null) {
			Set<String> genericDocumentTypes = new HashSet<String>();
			documentTypes.add(EMFModelAccess.GENERIC_DOCUMENT_TYPE);
			RecognitionRuleSorterLibrary.getDefaultRecognitionRuleSorter(genericDocumentTypes);
		}
	}

	/**
	 * 
	 * @param scope
	 * @param matcher
	 * @param candidatesService
	 * @param correspondenceService
	 * @param techBuilder
	 * @param symbolicLinkHandler
	 * @param validate
	 * @param ruleBases
	 * @param rrsorter
	 */
	public LiftingSettings(Scope scope, boolean validate, IMatcher matcher, ICandidates candidatesService,
			ICorrespondences correspondenceService, ITechnicalDifferenceBuilder techBuilder,
			ISymbolicLinkHandler symbolicLinkHandler, Set<ILiftingRuleBase> ruleBases,
			IRecognitionRuleSorter rrsorter) {

		super(scope, validate, matcher, candidatesService, correspondenceService, techBuilder, symbolicLinkHandler);
		this.ruleBases = ruleBases;
		this.rrSorter = rrsorter;
	}

	/**
	 * 
	 * @param scope
	 * @param matcher
	 * @param candidatesService
	 * @param correspondenceService
	 * @param techBuilder
	 * @param symbolicLinkHandler
	 * @param validate
	 * @param ruleBases
	 * @param rrsorter
	 * @param calculateEditRuleMatch
	 * @param serializeEditRuleMatch
	 */
	public LiftingSettings(Scope scope, boolean validate, IMatcher matcher, ICandidates candidatesService,
			ICorrespondences correspondenceService, ITechnicalDifferenceBuilder techBuilder,
			ISymbolicLinkHandler symbolicLinkHandler, Set<ILiftingRuleBase> ruleBases, IRecognitionRuleSorter rrsorter,
			boolean calculateEditRuleMatch, boolean serializeEditRuleMatch) {

		this(scope, validate, matcher, candidatesService, correspondenceService, techBuilder, symbolicLinkHandler,
				ruleBases, rrsorter);
		this.calculateEditRuleMatch = calculateEditRuleMatch;
		this.serializeEditRuleMatch = serializeEditRuleMatch;
	}

	@Override
	public boolean validateSettings() {
		// TODO CPietsch (2016-02-08)
		return super.validateSettings();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sidiff.difference.lifting.settings.Settings#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(super.toString());

		if (ruleBases != null) {
			result.append("Rulebases: ");

			for (IBasicRuleBase rb : ruleBases) {
				result.append(rb.getName() + ", ");
			}
			if (result.toString().endsWith(",")) {
				result.deleteCharAt(result.toString().lastIndexOf(','));
			}
			result.append("\n");
		}

		result.append("Recognition-Rule-Sorter: " + rrSorter.getName() + "\n");

		result.append("Recognition-Engine mode: " + recognitionEngineMode + "\n");

		result.append("Use thread pool: " + useThreadPool + "\n");
		result.append("Number of threads in the pool: " + numberOfThreads + "\n");
		result.append("Recognition-Rules per thread: " + rulesPerThread + "\n");
		result.append("Sort of Recognition-Rule nodes: " + sortRecognitionRuleNodes + "\n");
		result.append("Rule set reduction: " + ruleSetReduction + "\n");
		result.append("Build minimal working graph per rule: " + buildGraphPerRule + "\n");

		result.append("Calculate edit rule match: " + calculateEditRuleMatch + "\n");
		result.append("Serialize edit rule match: " + serializeEditRuleMatch + "\n");

		result.append("Split and Join Detection: " + detectSplitJoins + "\n");

		return result.toString();
	}

	// ---------- Getter and Setter Methods----------

	/**
	 * List of active Rulebases.
	 * 
	 * @return The list of all active Rulebases.
	 */
	public Set<ILiftingRuleBase> getRuleBases() {
		return ruleBases;
	}

	/**
	 * Sets the list of active Rulebases.
	 * 
	 * @param ruleBases
	 *            The list of all active Rulebases.
	 */
	public void setRuleBases(Set<ILiftingRuleBase> ruleBases) {
		if (this.ruleBases == null || !this.ruleBases.equals(ruleBases)) {
			this.ruleBases = ruleBases;
			this.notifyListeners(LiftingSettingsItem.RULEBASES);
		}
	}

	/**
	 * @return The Recognition Rule Sorter. ( {@link IRecognitionRuleSorter})
	 */
	public IRecognitionRuleSorter getRrSorter() {
		return rrSorter;
	}

	/**
	 * 
	 * @param rrSorter
	 *            The Recognition Rule Sorter. ( {@link IRecognitionRuleSorter})
	 */
	public void setRrSorter(IRecognitionRuleSorter rrSorter) {
		if (this.rrSorter == null || !this.rrSorter.getName().equals(rrSorter.getName())) {
			this.rrSorter = rrSorter;
			this.notifyListeners(LiftingSettingsItem.RECOGNITION_RULE_SORTER);
		}
	}

	/**
	 * @return The engine which executes the recognition rules.
	 */
	public IRecognitionEngine getRecognitionEngine() {
		return recognitionEngine;
	}

	/**
	 * @param recognitionEngine
	 *            The engine which executes the recognition rules.
	 */
	public void setRecognitionEngine(IRecognitionEngine recognitionEngine) {
		this.recognitionEngine = recognitionEngine;
	}

	/**
	 * Triggering of the Recognition-Engine pipeline.
	 * 
	 * @see LiftingSettings.RecognitionEngineMode (Default: Post processed
	 *      operation detection)
	 */
	public RecognitionEngineMode getRecognitionEngineMode() {
		return recognitionEngineMode;
	}

	/**
	 * Triggering of the Recognition-Engine pipeline.
	 * 
	 * @param recognitionEngineMode
	 *            The mode {@link LiftingSettings.RecognitionEngineMode}
	 */
	public void setRecognitionEngineMode(RecognitionEngineMode recognitionEngineMode) {

		if ((this.recognitionEngineMode == null) || !(this.recognitionEngineMode.equals(recognitionEngineMode))) {

			this.recognitionEngineMode = recognitionEngineMode;
			this.notifyListeners(LiftingSettingsItem.RECOGNITION_ENGINE_MODE);
		}
	}

	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 * 
	 * @return <code>true</code> if the optimization is active;
	 *         <code>false</code> otherwise.
	 */
	public boolean isUseThreadPool() {
		return useThreadPool;
	}

	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 * 
	 * @param useThreadPool
	 *            <code>true</code> to activate the optimization;
	 *            <code>false</code> otherwise.
	 */
	public void setUseThreadPool(boolean useThreadPool) {
		if (useThreadPool != this.useThreadPool) {
			this.useThreadPool = useThreadPool;
			this.notifyListeners(LiftingSettingsItem.USE_THREAD_POOL);
		}
	}

	/**
	 * Thread pool configuration.
	 * 
	 * @return The number of recognizer threads in the tread pool. (Default: 16)
	 */
	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	/**
	 * Thread pool configuration.
	 * 
	 * @param numberOfThreads
	 *            The number of recognizer threads in the tread pool. (Default:
	 *            16)
	 */
	public void setNumberOfThreads(int numberOfThreads) {
		if (numberOfThreads != this.numberOfThreads) {
			this.numberOfThreads = numberOfThreads;
			this.notifyListeners(LiftingSettingsItem.NUMBER_OF_THREADS);
		}
	}

	/**
	 * Thread pool configuration.
	 * 
	 * @return The number of Recognition-Rules per recognizer thread. (Default:
	 *         10)
	 */
	public int getRulesPerThread() {
		return rulesPerThread;
	}

	/**
	 * Thread pool configuration.
	 * 
	 * @param rulesPerThread
	 *            The number of Recognition-Rules per recognizer thread.
	 *            (Default: 10)
	 */
	public void setRulesPerThread(int rulesPerThread) {
		if (rulesPerThread != this.rulesPerThread) {
			this.rulesPerThread = rulesPerThread;
			this.notifyListeners(LiftingSettingsItem.RULES_PER_THREAD);
		}
	}

	public boolean isSortRecognitionRuleNodes() {
		return sortRecognitionRuleNodes;
	}

	public void setSortRecognitionRuleNodes(boolean sortRecognitionRuleNodes) {
		if (sortRecognitionRuleNodes != this.sortRecognitionRuleNodes) {
			this.sortRecognitionRuleNodes = sortRecognitionRuleNodes;
			this.notifyListeners(LiftingSettingsItem.SORT_RECOGNITIONRULE_NODES);
		}
	}

	/**
	 * Optimization: Filtering of unmatchable Recognition-Rules. (Default:
	 * <code>true</code>)
	 * 
	 * @return <code>true</code> if the optimization is active;
	 *         <code>false</code> otherwise.
	 */
	public boolean isRuleSetReduction() {
		return ruleSetReduction;
	}

	/**
	 * Optimization: Filtering of unmatchable Recognition-Rules. (Default:
	 * <code>true</code>)
	 * 
	 * @param ruleSetReduction
	 *            <code>true</code> to activate the optimization;
	 *            <code>false</code> otherwise.
	 */
	public void setRuleSetReduction(boolean ruleSetReduction) {
		if (ruleSetReduction != this.ruleSetReduction) {
			this.ruleSetReduction = ruleSetReduction;
			this.notifyListeners(LiftingSettingsItem.RULE_SET_REDUCTION);
		}
	}

	/**
	 * <p>
	 * Optimization: (Default: <code>true</code>)
	 * </p>
	 * <ul>
	 * <li><code>true</code>: Builds a minimal working graph for each
	 * Recognition-Rule.
	 * <li><code>false</code>: Build a single working graph for all
	 * Recognition-Rule.
	 * </ul>
	 * 
	 * @return <code>true</code> if the optimization is active;
	 *         <code>false</code> otherwise.
	 */
	public boolean isBuildGraphPerRule() {
		return buildGraphPerRule;
	}

	/**
	 * <p>
	 * Optimization: (Default: <code>true</code>)
	 * </p>
	 * <ul>
	 * <li><code>true</code>: Builds a minimal working graph for each
	 * Recognition-Rule.
	 * <li><code>false</code>: Build a single working graph for all
	 * Recognition-Rule.
	 * </ul>
	 * 
	 * @param buildGraphPerRule
	 *            <code>true</code> to activate the optimization;
	 *            <code>false</code> otherwise.
	 */
	public void setBuildGraphPerRule(boolean buildGraphPerRule) {
		if (buildGraphPerRule != this.buildGraphPerRule) {
			this.buildGraphPerRule = buildGraphPerRule;
			this.notifyListeners(LiftingSettingsItem.BUILD_GRAPH_PER_RULE);
		}
	}

	public boolean isCalculateEditRuleMatch() {
		return calculateEditRuleMatch;
	}

	public void setCalculateEditRuleMatch(boolean calculateEditRuleMatch) {
		if (calculateEditRuleMatch != this.calculateEditRuleMatch) {
			this.calculateEditRuleMatch = calculateEditRuleMatch;
			this.notifyListeners(LiftingSettingsItem.CALCULATE_EDIT_RULE_MATCH);
		}
	}

	public boolean isSerializeEditRuleMatch() {
		return serializeEditRuleMatch;
	}

	public void setSerializeEditRuleMatch(boolean serializeEditRuleMatch) {
		if (serializeEditRuleMatch != this.serializeEditRuleMatch) {
			this.serializeEditRuleMatch = serializeEditRuleMatch;
			this.notifyListeners(LiftingSettingsItem.SERIALIZE_EDIT_RULE_MATCH);
		}
	}

	/**
	 * Checks if Split/Join Detection is enabled
	 * 
	 * @return <code>true</code> if enabled; <code>false</code> otherwise.
	 */
	public boolean isDetectSplitJoins() {
		return detectSplitJoins;
	}

	/**
	 * Enables or disables Split/Join Detection.
	 * 
	 * @param detectSplitJoins
	 *            <code>true</code> if enabled <code>false</code> otherwise.
	 */
	public void setDetectSplitJoins(boolean detectSplitJoins) {
		this.detectSplitJoins = detectSplitJoins;
	}

	/**
	 * <p>
	 * Triggering of the Recognition-Engine pipeline.
	 * </p>
	 * <ul>
	 * <li><code>NO_LIFTING</code>: Disable the operation detection (Lifting).
	 * </li>
	 * <li><code>LIFTING</code>: Enable the operation detection (Lifting).</li>
	 * <li><code>LIFTING_AND_POST_PROCESSING:</code> Post processed (remove
	 * overlapping Semantic-Change-Sets) operation detection (Lifting).</li>
	 * </ul>
	 */
	public enum RecognitionEngineMode {

		/**
		 * Disable the operation detection (Lifting).
		 */
		NO_LIFTING,

		/**
		 * Enable the operation detection (Lifting).
		 */
		LIFTING,

		/**
		 * Post processed (remove overlapping Semantic-Change-Sets) operation
		 * detection (Lifting).
		 */
		LIFTING_AND_POST_PROCESSING
	}
}
