package org.sidiff.difference.lifting.api.settings;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionEngine;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.editrule.rulebase.project.runtime.library.IRuleBaseProject;
import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;
import org.sidiff.matcher.IMatcher;

/**
 * @see LiftingSettingsItem
 */
public class LiftingSettings extends DifferenceSettings {

	private static final String PLUGIN_ID = "org.sidiff.difference.lifting.api";

	/**
	 * Set of active Rulebases.
	 */
	private Set<ILiftingRuleBase> ruleBases = new LinkedHashSet<>(); // must not be null

	/**
	 * The Recognition Rule Sorter to use. ({@link IRecognitionRuleSorter})
	 */
	private IRecognitionRuleSorter rrSorter;

	/**
	 * The engine which executes the recognition rules.
	 */
	private IRecognitionEngine recognitionEngine;

	/**
	 * Triggering of the Recognition-Engine pipeline.
	 * 
	 * @see RecognitionEngineMode (Default: Post processed operation detection)
	 */
	private RecognitionEngineMode recognitionEngineMode = RecognitionEngineMode.LIFTING_AND_POST_PROCESSING; // must not be null

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


	/**
	 * Default constructor to create mostly empty settings.
	 * Call initDefaults last, after calling the necessary setters.
	 */
	public LiftingSettings() {
		super();
	}

	/**
	 * @deprecated Use the empty constructor and initDefaults instead.
	 */
	public LiftingSettings(Scope scope, boolean validate, IMatcher matcher, ICandidates candidatesService,
			ICorrespondences correspondenceService, ITechnicalDifferenceBuilder techBuilder) {
		super(scope, validate, matcher, candidatesService, correspondenceService, techBuilder);

		// Default: Use the default RecognitionRuleSorter
		this.rrSorter = getDefaultRecognitionRuleSorter(Collections.emptySet());
	}

	/**
	 * @deprecated Use the empty constructor and initDefaults instead.
	 */
	public LiftingSettings(Scope scope, boolean validate, IMatcher matcher, ICandidates candidatesService,
			ICorrespondences correspondenceService, ITechnicalDifferenceBuilder techBuilder,
			Set<ILiftingRuleBase> ruleBases, IRecognitionRuleSorter rrSorter) {

		super(scope, validate, matcher, candidatesService, correspondenceService, techBuilder);
		if(ruleBases != null) {
			this.ruleBases.addAll(ruleBases);
		}
		this.rrSorter = rrSorter;
	}

	/**
	 * @deprecated Use the empty constructor and initDefaults instead.
	 */
	public LiftingSettings(Scope scope, boolean validate, IMatcher matcher, ICandidates candidatesService,
			ICorrespondences correspondenceService, ITechnicalDifferenceBuilder techBuilder,
			Set<ILiftingRuleBase> ruleBases, IRecognitionRuleSorter rrsorter,
			boolean calculateEditRuleMatch, boolean serializeEditRuleMatch) {

		this(scope, validate, matcher, candidatesService, correspondenceService, techBuilder, ruleBases, rrsorter);
		this.calculateEditRuleMatch = calculateEditRuleMatch;
		this.serializeEditRuleMatch = serializeEditRuleMatch;
	}

	@Override
	public void initDefaults(Set<String> documentTypes) {
		super.initDefaults(documentTypes);
		if(ruleBases.isEmpty() || ruleBases.stream().anyMatch(rb -> !rb.getDocumentTypes().containsAll(documentTypes))) {
			setRuleBases(getDefaultRuleBases(documentTypes));
		}
		if(rrSorter == null || !rrSorter.canHandle(documentTypes)) {
			setRrSorter(getDefaultRecognitionRuleSorter(documentTypes));
		}
	}

	protected Set<ILiftingRuleBase> getDefaultRuleBases(Set<String> documentTypes) {
		if(documentTypes.isEmpty()) {
			return Collections.emptySet();
		}
		return IRuleBaseProject.MANAGER.getRuleBases(documentTypes, ILiftingRuleBase.TYPE);
	}

	protected IRecognitionRuleSorter getDefaultRecognitionRuleSorter(Set<String> documentTypes) {
		if(documentTypes.isEmpty()) {
			return IRecognitionRuleSorter.MANAGER.getDefaultExtension().orElseThrow(
					() -> new IllegalStateException("No generic recognition rule sorter is available"));
		}
		return IRecognitionRuleSorter.MANAGER.getDefaultExtension(documentTypes).orElseThrow(
				() -> new IllegalStateException("No recognition rule sorter is available for the document types " + documentTypes));
	}

	@Override
	public void validate(MultiStatus multiStatus) {
		super.validate(multiStatus);

		if(ruleBases.isEmpty()) {
			multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Rulebases are not set.", null));
		}

		if(rrSorter == null) {
			multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Recognition Rule Sorter is not set.", null));
		}
	}

	@Override
	public String toString() {
		return super.toString() + "\n"
			+ "LiftingSettings["
			+ "Rulebases: " + toString(getRuleBases()) + ", "
			+ "Recognition-Rule-Sorter: " + toString(getRrSorter()) + ", "
			+ "Recognition-Engine mode: " + getRecognitionEngineMode() + ", "
			+ "Use thread pool: " + isUseThreadPool() + ", "
			+ "Number of threads in the pool: " + getNumberOfThreads() + ", "
			+ "Recognition-Rules per thread: " + getRulesPerThread() + ", "
			+ "Sort of Recognition-Rule nodes: " + isSortRecognitionRuleNodes() + ", "
			+ "Rule set reduction: " + isRuleSetReduction() + ", "
			+ "Build minimal working graph per rule: " + isBuildGraphPerRule() + ", "
			+ "Calculate edit rule match: " + isCalculateEditRuleMatch() + ", "
			+ "Serialize edit rule match: " + isSerializeEditRuleMatch() + ", "
			+ "Split and Join Detection: " + isSerializeEditRuleMatch() + ", "
			+ "Comparator: " + toString(getComparator()) + "]";
	}

	@Override
	protected String toStringImpl(Object object) {
		if(object instanceof IBasicRuleBase) {
			return ((IBasicRuleBase)object).getName();
		}
		return super.toStringImpl(object);
	}

	// ---------- Getter and Setter Methods----------

	/**
	 * Set of all active Rulebases.
	 * @return The set of all active Rulebases.
	 * @see LiftingSettingsItem#RULEBASES
	 */
	public Set<ILiftingRuleBase> getRuleBases() {
		return Collections.unmodifiableSet(ruleBases);
	}

	/**
	 * Sets the active Rulebases.
	 * @param ruleBases All active Rulebases.
	 * @see LiftingSettingsItem#RULEBASES
	 */
	public void setRuleBases(Set<ILiftingRuleBase> ruleBases) {
		if (!Objects.equals(this.ruleBases, ruleBases)) {
			this.ruleBases.clear();
			this.ruleBases.addAll(ruleBases);
			notifyListeners(LiftingSettingsItem.RULEBASES);
		}
	}

	/**
	 * @return The Recognition Rule Sorter. ({@link IRecognitionRuleSorter})
	 * @see LiftingSettingsItem#RECOGNITION_RULE_SORTER
	 */
	public IRecognitionRuleSorter getRrSorter() {
		return rrSorter;
	}

	/**
	 * @param rrSorter The Recognition Rule Sorter. ({@link IRecognitionRuleSorter})
	 * @see LiftingSettingsItem#RECOGNITION_RULE_SORTER
	 */
	public void setRrSorter(IRecognitionRuleSorter rrSorter) {
		if (!Objects.equals(this.rrSorter, rrSorter)) {
			this.rrSorter = rrSorter;
			notifyListeners(LiftingSettingsItem.RECOGNITION_RULE_SORTER);
		}
	}

	/**
	 * @return The engine which executes the recognition rules.
	 * @see LiftingSettingsItem#RECOGNITION_ENGINE
	 */
	public IRecognitionEngine getRecognitionEngine() {
		return recognitionEngine;
	}

	/**
	 * @param recognitionEngine The engine which executes the recognition rules.
	 * @see LiftingSettingsItem#RECOGNITION_ENGINE
	 */
	public void setRecognitionEngine(IRecognitionEngine recognitionEngine) {
		if (!Objects.equals(this.recognitionEngine, recognitionEngine)) {
			this.recognitionEngine = recognitionEngine;
			notifyListeners(LiftingSettingsItem.RECOGNITION_ENGINE);
		}
	}

	/**
	 * Triggering of the Recognition-Engine pipeline. (Default: Post processed operation detection)
	 * @see LiftingSettingsItem#RECOGNITION_ENGINE_MODE
	 */
	public RecognitionEngineMode getRecognitionEngineMode() {
		return recognitionEngineMode;
	}

	/**
	 * Triggering of the Recognition-Engine pipeline.
	 * @param recognitionEngineMode The mode
	 * @see LiftingSettingsItem#RECOGNITION_ENGINE_MODE
	 */
	public void setRecognitionEngineMode(RecognitionEngineMode recognitionEngineMode) {
		if (this.recognitionEngineMode != recognitionEngineMode) {
			this.recognitionEngineMode = Objects.requireNonNull(recognitionEngineMode, "recognitionEngineMode must not be null");
			notifyListeners(LiftingSettingsItem.RECOGNITION_ENGINE_MODE);
		}
	}

	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 * @return <code>true</code> if the optimization is active; <code>false</code> otherwise.
	 * @see LiftingSettingsItem#USE_THREAD_POOL
	 */
	public boolean isUseThreadPool() {
		return useThreadPool;
	}

	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 * @param useThreadPool <code>true</code> to activate the optimization; <code>false</code> otherwise.
	 * @see LiftingSettingsItem#USE_THREAD_POOL
	 */
	public void setUseThreadPool(boolean useThreadPool) {
		if (this.useThreadPool != useThreadPool) {
			this.useThreadPool = useThreadPool;
			notifyListeners(LiftingSettingsItem.USE_THREAD_POOL);
		}
	}

	/**
	 * Thread pool configuration.
	 * @return The number of recognizer threads in the tread pool. (Default: 10)
	 * @see LiftingSettingsItem#NUMBER_OF_THREADS
	 */
	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	/**
	 * Thread pool configuration.
	 * @param numberOfThreads The number of recognizer threads in the tread pool. (Default: 10)
	 * @see LiftingSettingsItem#NUMBER_OF_THREADS
	 */
	public void setNumberOfThreads(int numberOfThreads) {
		if (this.numberOfThreads != numberOfThreads) {
			this.numberOfThreads = numberOfThreads;
			notifyListeners(LiftingSettingsItem.NUMBER_OF_THREADS);
		}
	}

	/**
	 * Thread pool configuration.
	 * @return The number of Recognition-Rules per recognizer thread. (Default: 10)
	 * @see LiftingSettingsItem#RULES_PER_THREAD
	 */
	public int getRulesPerThread() {
		return rulesPerThread;
	}

	/**
	 * Thread pool configuration.
	 * @param rulesPerThread The number of Recognition-Rules per recognizer thread. (Default: 10)
	 * @see LiftingSettingsItem#RULES_PER_THREAD
	 */
	public void setRulesPerThread(int rulesPerThread) {
		if (this.rulesPerThread != rulesPerThread) {
			this.rulesPerThread = rulesPerThread;
			notifyListeners(LiftingSettingsItem.RULES_PER_THREAD);
		}
	}

	/**
	 * Optimizes the (matching) order of the nodes in the Recognition-Rules.
	 * (Default (strongly recommended): <code>true</code>)
	 * @return <code>true</code> if the optimization is active; <code>false</code> otherwise.
	 * @see LiftingSettingsItem#SORT_RECOGNITIONRULE_NODES
	 */
	public boolean isSortRecognitionRuleNodes() {
		return sortRecognitionRuleNodes;
	}

	/**
	 * Optimizes the (matching) order of the nodes in the Recognition-Rules.
	 * (Default (strongly recommended): <code>true</code>)
	 * @param sortRecognitionRuleNodes <code>true</code> to activate the optimization; <code>false</code> otherwise.
	 * @see LiftingSettingsItem#SORT_RECOGNITIONRULE_NODES
	 */
	public void setSortRecognitionRuleNodes(boolean sortRecognitionRuleNodes) {
		if (this.sortRecognitionRuleNodes != sortRecognitionRuleNodes) {
			this.sortRecognitionRuleNodes = sortRecognitionRuleNodes;
			notifyListeners(LiftingSettingsItem.SORT_RECOGNITIONRULE_NODES);
		}
	}

	/**
	 * Optimization: Filtering of unmatchable Recognition-Rules. (Default:
	 * <code>true</code>)
	 * @return <code>true</code> if the optimization is active; <code>false</code> otherwise.
	 * @see LiftingSettingsItem#RULE_SET_REDUCTION
	 */
	public boolean isRuleSetReduction() {
		return ruleSetReduction;
	}

	/**
	 * Optimization: Filtering of unmatchable Recognition-Rules. (Default: <code>true</code>)
	 * @param ruleSetReduction <code>true</code> to activate the optimization; <code>false</code> otherwise.
	 * @see LiftingSettingsItem#RULE_SET_REDUCTION
	 */
	public void setRuleSetReduction(boolean ruleSetReduction) {
		if (this.ruleSetReduction != ruleSetReduction) {
			this.ruleSetReduction = ruleSetReduction;
			notifyListeners(LiftingSettingsItem.RULE_SET_REDUCTION);
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
	 * @return <code>true</code> if the optimization is active; <code>false</code> otherwise.
	 * @see LiftingSettingsItem#BUILD_GRAPH_PER_RULE
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
	 * @param buildGraphPerRule <code>true</code> to activate the optimization; <code>false</code> otherwise.
	 * @see LiftingSettingsItem#BUILD_GRAPH_PER_RULE
	 */
	public void setBuildGraphPerRule(boolean buildGraphPerRule) {
		if (this.buildGraphPerRule != buildGraphPerRule) {
			this.buildGraphPerRule = buildGraphPerRule;
			notifyListeners(LiftingSettingsItem.BUILD_GRAPH_PER_RULE);
		}
	}

	/**
	 * @return Whether to calculate the EditRuleMatch or not.
	 * @see LiftingSettingsItem#CALCULATE_EDIT_RULE_MATCH
	 */
	public boolean isCalculateEditRuleMatch() {
		return calculateEditRuleMatch;
	}

	/**
	 * @param calculateEditRuleMatch Whether to calculate the EditRuleMatch or not.
	 * @see LiftingSettingsItem#CALCULATE_EDIT_RULE_MATCH
	 */
	public void setCalculateEditRuleMatch(boolean calculateEditRuleMatch) {
		if (this.calculateEditRuleMatch != calculateEditRuleMatch) {
			this.calculateEditRuleMatch = calculateEditRuleMatch;
			notifyListeners(LiftingSettingsItem.CALCULATE_EDIT_RULE_MATCH);
		}
	}

	/**
	 * @return Whether to serialize the EditRuleMatch or not.
	 * @see LiftingSettingsItem#SERIALIZE_EDIT_RULE_MATCH
	 */
	public boolean isSerializeEditRuleMatch() {
		return serializeEditRuleMatch;
	}

	/**
	 * @param serializeEditRuleMatch Whether to serialize the EditRuleMatch or not.
	 * @see LiftingSettingsItem#SERIALIZE_EDIT_RULE_MATCH
	 */
	public void setSerializeEditRuleMatch(boolean serializeEditRuleMatch) {
		if (this.serializeEditRuleMatch != serializeEditRuleMatch) {
			this.serializeEditRuleMatch = serializeEditRuleMatch;
			notifyListeners(LiftingSettingsItem.SERIALIZE_EDIT_RULE_MATCH);
		}
	}

	/**
	 * Checks if Split/Join Detection is enabled
	 * @return <code>true</code> if enabled; <code>false</code> otherwise.
	 * @see LiftingSettingsItem#DETECT_SPLIT_JOINS
	 */
	public boolean isDetectSplitJoins() {
		return detectSplitJoins;
	}

	/**
	 * Enables or disables Split/Join Detection.
	 * @param detectSplitJoins <code>true</code> if enabled <code>false</code> otherwise.
	 * @see LiftingSettingsItem#DETECT_SPLIT_JOINS
	 */
	public void setDetectSplitJoins(boolean detectSplitJoins) {
		if (this.detectSplitJoins != detectSplitJoins) {
			this.detectSplitJoins = detectSplitJoins;
			notifyListeners(LiftingSettingsItem.DETECT_SPLIT_JOINS);
		}
	}

	/**
	 * @return Comparator for {@link SemanticChangeSet}s
	 * @see LiftingSettingsItem#COMPARATOR
	 */
	public Comparator<SemanticChangeSet> getComparator() {
		return comparator;
	}

	/**
	 * @param comparator Comparator for {@link SemanticChangeSet}s
	 * @see LiftingSettingsItem#COMPARATOR
	 */
	public void setComparator(Comparator<SemanticChangeSet> comparator) {
		if (!Objects.equals(this.comparator, comparator)) {
			this.comparator = comparator;
			notifyListeners(LiftingSettingsItem.COMPARATOR);
		}
	}


	public static LiftingSettings defaultSettings() {
		return defaultSettings(Collections.emptySet());
	}

	public static LiftingSettings defaultSettings(Set<String> documentTypes) {
		LiftingSettings settings = new LiftingSettings();
		settings.initDefaults(documentTypes);
		return settings;
	}
}
