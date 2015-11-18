package org.sidiff.difference.lifting.settings;

import java.util.Set;

import javax.inject.Scope;

import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.util.TechnicalDifferenceBuilderUtil;
import org.sidiff.matcher.IMatcher;

public class LiftingSettings extends Settings {

	/**
	 * Enables/disables the internal mergeImports function
	 * (more specifically the EObject location lookup)
	 * This lookup is quite costly when comparing big models
	 * and can be disabled here for performance gain
	 * however at the expense of some matching correctness.
	 */
	private boolean mergeImports = true;
	
	/**
	 * Validation of the input models. (Default: False)
	 */
	private boolean validate = false;

	/**
	 * The Technical Difference Builder to use. (
	 * {@link ITechnicalDifferenceBuilder})
	 */
	private ITechnicalDifferenceBuilder techBuilder;

	/**
	 * The Recognition Rule Sorter to use. (
	 * {@link IRecognitionRuleSorter})
	 */
	private IRecognitionRuleSorter rrSorter;
	
	/**
	 * Triggering of the Recognition-Engine pipeline.
	 * 
	 * @see LiftingSettings.RecognitionEngineMode (Default: Post processed
	 *      operation detection)
	 */
	private RecognitionEngineMode recognitionEngineMode = RecognitionEngineMode.LIFTING_AND_POST_PROCESSING;

	/**
	 * Whether to calculate the EditRuleMatch or not.
	 */
	private boolean calculateEditRuleMatch = false;
	
	/**
	 * Whether to serialize the EditRuleMatch or not.
	 */
	private boolean serializeEditRuleMatch = false;
	
	
	/**
	 * Whether to detect Split/Joins or not
	 */
	private boolean detectSplitJoins = false;
	

	/**
	 * <p>
	 * Triggering of the Recognition-Engine pipeline.
	 * </p>
	 * <ul>
	 * <li><code>NO_LIFTING</code>: Disable the operation detection (Lifting).</li>
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

	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 */
	private boolean useThreadPool = true;

	/**
	 * Number of recognizer threads in the tread pool. (Default: 16)
	 */
	private int numberOfThreads = 32;

	/**
	 * Recognition-Rules per recognizer thread. (Default: 10)
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
	 * Setup the lifting settings.
	 */
	public LiftingSettings() {
		super();
		
		// Default: Use the default RecognitionRuleSorter
		this.rrSorter = RecognitionRuleSorterUtil.getDefaultRecognitionRuleSorter(EMFModelAccess.GENERIC_DOCUMENT_TYPE);
	}

	/**
	 * Setup the lifting settings.
	 * 
	 * @param documentType
	 *            The document type of the models.
	 */
	public LiftingSettings(String documentType) {
		super(documentType);

		// Default: Use the default TechnicalDifference builder
		this.techBuilder = TechnicalDifferenceBuilderUtil.getDefaultTechnicalDifferenceBuilder(documentType);
		// Default: Use the default RecognitionRuleSorter
		this.rrSorter = RecognitionRuleSorterUtil.getDefaultRecognitionRuleSorter(documentType);
	}

	/**
	 * Setup the lifting settings.
	 * 
	 * @param scope
	 *            {@link Settings#setScope(Scope)}
	 * @param matcher
	 *            {@link Settings#setMatcher(IMatcher)}
	 * @param techBuilder
	 *            {@link Settings#setTechBuilder(ITechnicalDifferenceBuilder)}
	 * @param ruleBases
	 *            {@link Settings#setRuleBases(Set)}
	 * @param validate
	 *            {@link LiftingSettings#setValidate(boolean)}
	 * @param recognitionEngine
	 *            {@link LiftingSettings#setRecognitionEngineMode(RecognitionEngineMode)}
	 */
	public LiftingSettings(Scope scope, IMatcher matcher, ITechnicalDifferenceBuilder techBuilder,
			IRecognitionRuleSorter rrSorter, Set<IRuleBase> ruleBases, boolean validate, RecognitionEngineMode recognitionEngine) {

		super(scope, matcher, ruleBases);

		this.techBuilder = techBuilder;
		this.rrSorter = rrSorter;
		this.validate = validate;
		this.recognitionEngineMode = recognitionEngine;
	}

	/**
	 * Returns whether mergeImports (esp. EObject Localization Lookup)
	 * is enabled. (Default: true)
	 * 
	 * @return <code>true</code> if enabled;
	 *         <code>false</code> otherwise.
	 */
	public boolean isEnabled_MergeImports() {
		return mergeImports;
	}
	
	
	/**
	 * Get the validation of the input models. (Default: False)
	 * 
	 * @return <code>true</code> if the input models will be validated;
	 *         <code>false</code> otherwise.
	 */
	public boolean isValidate() {
		return validate;
	}

	
	/**
	 * Checks if Split/Join Detection is enabled
	 * 
	 * @return <code>true</code> if enabled;
	 *         <code>false</code> otherwise.
	 */
	public boolean isDetectSplitJoins() {
		return detectSplitJoins;
	}
	
	/**
	 * Set the validation of the input models. (Default: False)
	 * 
	 * @param validate
	 *            <code>true</code> if the input models should be validated;
	 *            <code>false</code> otherwise.
	 */
	public void setValidate(boolean validate) {
		if (this.validate != validate) {
			this.validate = validate;
			this.notifyListeners(LiftingSettingsItem.VALTIDATE);
		}
	}

	/**
	 * Enables or disables Split/Join Detection.
	 * 
	 * @param detectSplitJoins
	 * 			<code>true</code> if enabled
	 *          <code>false</code> otherwise.
	 */
	public void setDetectSplitJoins(boolean detectSplitJoins) {
		this.detectSplitJoins = detectSplitJoins;
	}
	
	/**
	 * @return The Technical Difference Builder. (
	 *         {@link ITechnicalDifferenceBuilder})
	 */
	public ITechnicalDifferenceBuilder getTechBuilder() {
		return techBuilder;
	}

	/**
	 * @param techBuilder
	 *            The Technical Difference Builder. (
	 *            {@link ITechnicalDifferenceBuilder})
	 */
	public void setTechBuilder(ITechnicalDifferenceBuilder techBuilder) {
		if (this.techBuilder == null || !this.techBuilder.equals(techBuilder)) {
			this.techBuilder = techBuilder;
			this.notifyListeners(SettingsItem.TECH_BUILDER);
		}
	}

	/**
	 * @return The Recognition Rule Sorter. (
	 * 			{@link IRecognitionRuleSorter})
	 */
	public IRecognitionRuleSorter getRrSorter() {
		return rrSorter;
	}

	/**
	 * 
	 * @param rrSorter
	 * 			The Recognition Rule Sorter. (
	 * 			{@link IRecognitionRuleSorter})
	 */
	public void setRrSorter(IRecognitionRuleSorter rrSorter) {
		if(this.rrSorter == null || !this.rrSorter.getName().equals(rrSorter.getName())){
			this.rrSorter = rrSorter;
			this.notifyListeners(SettingsItem.RECOGNITION_RULE_SORTER);
		}
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
			this.notifyListeners(SettingsItem.USE_THREAD_POOL);
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
			this.notifyListeners(SettingsItem.NUMBER_OF_THREADS);
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
			this.notifyListeners(SettingsItem.RULES_PER_THREAD);
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
			this.notifyListeners(SettingsItem.RULE_SET_REDUCTION);
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
			this.notifyListeners(SettingsItem.BUILD_GRAPH_PER_RULE);
		}
	}

	public boolean isSerializeEditRuleMatch() {
		return serializeEditRuleMatch;
	}

	public void setSerializeEditRuleMatch(boolean serializeEditRuleMatch) {
		if (serializeEditRuleMatch != this.serializeEditRuleMatch) {
			this.serializeEditRuleMatch = serializeEditRuleMatch;
			this.notifyListeners(SettingsItem.SERIALIZE_EDIT_RULE_MATCH);
		}		
	}
	
	public boolean isCalculateEditRuleMatch() {
		return calculateEditRuleMatch;
	}

	public void setCalculateEditRuleMatch(boolean calculateEditRuleMatch) {
		if (calculateEditRuleMatch != this.calculateEditRuleMatch) {
			this.calculateEditRuleMatch = calculateEditRuleMatch;
			this.notifyListeners(SettingsItem.CALCULATE_EDIT_RULE_MATCH);
		}		
	}
	/**
	 * Disables merge imports (esp. EObject location lookup)
	 * See {@link LiftingSettings#mergeImports}
	 */
	public void disableMergeImports() {
		mergeImports = false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sidiff.difference.lifting.settings.Settings#toString()
	 */
	public String toString() {
		StringBuffer result = new StringBuffer(super.toString());

		result.append(techBuilder != null ? "Technical-Difference-Builder: " + techBuilder.getName() + "\n" : "");
		result.append("Recognition-Rule-Sorter: " + rrSorter.getName() + "\n");
		result.append("Validate: " + validate + "\n");
		result.append("Recognition-Engine mode: " + recognitionEngineMode + "\n");

		result.append("Use thread pool: " + useThreadPool + "\n");
		result.append("Number of threads in the pool: " + numberOfThreads + "\n");
		result.append("Recognition-Rules per thread: " + rulesPerThread + "\n");
		result.append("Sort of Recognition-Rule nodes: " + sortRecognitionRuleNodes + "\n");
		result.append("Rule set reduction: " + ruleSetReduction + "\n");
		result.append("Build minimal working graph per rule: " + buildGraphPerRule + "\n");

		result.append("Calculate edit rule match: " + calculateEditRuleMatch + "\n");
		result.append("Serialize edit rule match: " + serializeEditRuleMatch + "\n");
		
		return result.toString();
	}
}
