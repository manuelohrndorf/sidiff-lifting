package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.EXECUTION;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.RULE_SET_REDUCTION;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.analyseFullEGraph;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.finishStatistic;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.startTimer;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.stopTimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.recognitionengine.matching.EngineBasedEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.matching.RecognitionRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.util.RecognitionRuleApplicationAnalysis;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.symmetric.EObjectSet;
import org.sidiff.difference.symmetric.EditRuleMatch;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.symmetric.util.ChangeIndex;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;
import org.silift.common.util.access.EMFModelAccessEx;

/**
 * Implementation of CO-Detection Service Interface.
 * 
 * The recognition engine is used to execute the recognition rules on the
 * difference. The recognition rules will group the atomic changes to semantic
 * change sets. Note that there maybe overlapping semantic change sets.
 */
public class RecognitionEngine {

	/**
	 * The Recognition-Engine setup.
	 */
	private LiftingSettings settings;

	/**
	 * Matching information:<br/>
	 * semantic change set -> recognition rule match
	 */
	private Map<SemanticChangeSet, RecognitionRuleMatch> scs2rrMatch;

	/**
	 * Matching information lifted up to the level of edit rules:<br/>
	 * semantic change set -> edit rule match
	 */
	private Map<SemanticChangeSet, EngineBasedEditRuleMatch> scs2erMatch;

	/**
	 * All recognition rules that will be executed by the recognition engine.
	 */
	private Set<Rule> recognitionRules;

	/**
	 * Rulebases containing all recognition rules that will be executed by the
	 * recognition engine.
	 */
	private Set<IRuleBase> usedRulebases;

	/**
	 * The difference to be semantically lifted
	 */
	private SymmetricDifference difference;

	/**
	 * RecognitionRules that are not needed.
	 */
	private Set<Rule> filtered = Collections.emptySet();

	/**
	 * Set of all rule applications created by the recognizer threads.
	 */
	private Set<RuleApplication> recognizerRuleApplications;

	/**
	 * Single instance of a graph factory
	 */
	private LiftingGraphFactory graphFactory;

	/**
	 * Initialize a new Recognition-Engine.
	 * 
	 * @param usedRulebases
	 *            The list of Rulebases to use with the Recognition-Engine.
	 * @param difference
	 *            The difference to lift.
	 * @param imports
	 *            A set of model elements that are imported by model A/B. The
	 *            list will be merged into the working graph.
	 * @param settings
	 *            The settings (scope, optimizations) of the Recognition-Engine.
	 */
	public RecognitionEngine(Set<IRuleBase> usedRulebases, SymmetricDifference difference, Set<EObject> imports,
			LiftingSettings settings) {

		this.usedRulebases = usedRulebases;
		this.difference = difference;
		this.settings = settings;

		// Create a graph factory:
		this.graphFactory = new LiftingGraphFactory(difference, imports, settings.getScope(),
				settings.isBuildGraphPerRule());

		// Statistics: Measure size of the full working graph:
		analyseFullEGraph(graphFactory);

		// Get all recognition rules to be used:
		recognitionRules = new HashSet<Rule>();
		for (IRuleBase rb : usedRulebases) {
			recognitionRules.addAll(rb.getActiveRecognitionTransformationUnits());
		}

		// Initialize Rule Applications:
		recognizerRuleApplications = new HashSet<RuleApplication>();

		// Some further initialization:
		scs2rrMatch = new HashMap<SemanticChangeSet, RecognitionRuleMatch>();
		scs2erMatch = new HashMap<SemanticChangeSet, EngineBasedEditRuleMatch>();

		// Optimize the Rulebase: Rule Set Reduction
		if (settings.isRuleSetReduction()) {
			startTimer(RULE_SET_REDUCTION);
			filterRecognitionRules();
			stopTimer(RULE_SET_REDUCTION);
		}

		// Optimize the Rulebase: Sorting Recognition-Rule nodes
		sortRecognitionRuleNodes();
	}

	/**
	 * Filters unmatchable recognition rules by counting the atomic changes in
	 * the difference and in the recognition rule.
	 */
	private void filterRecognitionRules() {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "----------------- FILTER RECOGNITION RULES -----------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		LogUtil.log(LogEvent.NOTICE, "Building change index.");
		ChangeIndex idx = new ChangeIndex(difference);
		LogUtil.log(LogEvent.NOTICE, "Finished build change index.");

		LogUtil.log(LogEvent.NOTICE, "Filter recognition rules.");
		RecognitionRuleFilter filter = new RecognitionRuleFilter();
		filtered = filter.filter(idx, recognitionRules);

		LogUtil.log(LogEvent.NOTICE, "Filtered " + filtered.size() + " of " + recognitionRules.size()
				+ " recognitionRules");

		for (Unit unit : filtered) {
			LogUtil.log(LogEvent.DEBUG, unit.getModule().getName());
		}
	}

	/**
	 * Optimizes the (matching) order of the nodes in the Recognition-Rules.
	 */
	private void sortRecognitionRuleNodes() {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------- Difference Analysis --------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		DifferenceAnalysis analysis = new DifferenceAnalysis(difference);

		// Print report
		LogUtil.log(LogEvent.NOTICE, "Difference:");
		LogUtil.log(LogEvent.NOTICE, " Total AddObjects: " + analysis.getAddObjectCount());
		LogUtil.log(LogEvent.NOTICE, " Total RemoveObjects: " + analysis.getRemoveObjectCount());
		LogUtil.log(LogEvent.NOTICE, " Total AddReferences: " + analysis.getAddReferenceCount());
		LogUtil.log(LogEvent.NOTICE, " Total RemoveReferences: " + analysis.getRemoveReferenceCount());
		LogUtil.log(LogEvent.NOTICE, " Total AttributeValueChanges: " + analysis.getAttributeValueChangeCount());
		LogUtil.log(LogEvent.NOTICE, " Total Correspondences: " + analysis.getCorrespondenceCount());

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------ SORT RECOGNITION RULES ------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		RecognitionRuleSorter RuleSorter = new RecognitionRuleSorter(analysis);

		for (Rule recognitionRule : this.recognitionRules) {
			if (!filtered.contains(recognitionRule)) {
				// Sort kernel rule
				ECollections.sort(recognitionRule.getLhs().getNodes(), RuleSorter);
				ECollections.sort(recognitionRule.getRhs().getNodes(), RuleSorter);

				// Sort all multi-rules (if there are any)
				for (Rule multiRule : recognitionRule.getAllMultiRules()) {
					ECollections.sort(multiRule.getLhs().getNodes(), RuleSorter);
					ECollections.sort(multiRule.getRhs().getNodes(), RuleSorter);
				}
			}
		}
	}

	/**
	 * Execution of all Recognition-Rules.
	 */
	public void execute() {
		startTimer(EXECUTION);

		// Start execution:
		if (settings.isUseThreadPool()) {
			executeParallel();
		} else {
			executeSequential();
		}

		stopTimer(EXECUTION);

		// Finish Statistic:
		finishStatistic(difference, recognitionRules, filtered);
	}

	/**
	 * Sequential execution of all Recognition-Rules.
	 */
	private void executeSequential() {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "---------------- SEQUENTIALLY MATCHING RECOGNITION RULES ----------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		
		// Sequential recognizer:
		Set<Rule> units = new HashSet<Rule>();
		units.addAll(recognitionRules);
		units.removeAll(filtered);

		// Start Recognizer-Thread in "sequential mode":
		RecognizerThread recognizer = new RecognizerThread(units, this);
		recognizer.recognize();
		
		// Apply recognition rules
		applyRecognitionRules();
	}

	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 */
	private void executeParallel() {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "---------------- PARALLEL MATCHING RECOGNITION RULES ----------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		// Create thread pool
		ExecutorService executor = Executors.newFixedThreadPool(settings.getNumberOfThreads());

		// List of units for each thread
		Set<Rule> units = new HashSet<Rule>();

		// Start recognizer threads
		for (Rule transformationUnit : recognitionRules) {

			// Filter rules
			if (!filtered.contains(transformationUnit)) {
				if (units.size() == settings.getRulesPerThread()) {
					// Start new recognizer thread
					RecognizerThread recognizerThread = new RecognizerThread(units, this);

					// Do all the hard work in one {@link RecognizerThread}
					executor.execute(recognizerThread);

					// New rule collection for next thread
					units = new HashSet<Rule>();
				}

				// Add rule to thread
				units.add(transformationUnit);
			}
		}

		// Units left?
		if (units.size() > 0) {
			// Create one last thread
			RecognizerThread recognizerThread = new RecognizerThread(units, this);

			// Do all the hard work in one {@link RecognizerThread}
			executor.execute(recognizerThread);
		}

		// This will make the executor accept no new threads
		// and finish all existing threads in the queue.
		executor.shutdown();

		// Wait until all threads are finished:
		try {
			executor.awaitTermination(24, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Apply recognition rules
		applyRecognitionRules();
	}

	private void applyRecognitionRules(){
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "---------------- APPLYING RECOGNITION RULES ----------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		// Pre-filtering of collected rule applications: Keep only one
		// representative of a set of equal (same changes covered and same
		// recognition rule) recognition rule applications.
		Collection<List<RuleApplication>> rrApplicationsByRule = RecognitionRuleApplicationAnalysis
				.partitionByEqualRule(recognizerRuleApplications);
		Set<RuleApplication> toBeRemoved = new HashSet<RuleApplication>();
		for (List<RuleApplication> rrApplicationsRule : rrApplicationsByRule) {
			Collection<List<RuleApplication>> partitionsByEqual = RecognitionRuleApplicationAnalysis
					.partitionByEqualChanges(rrApplicationsRule);
			for (List<RuleApplication> equalApplications : partitionsByEqual) {
				for (int i = 1; i < equalApplications.size(); i++) {
					toBeRemoved.add(equalApplications.get(i));
				}
			}
		}
		recognizerRuleApplications.removeAll(toBeRemoved);

		// Execution of collected rule applications:
		for (RuleApplication recognitionRuleApp : recognizerRuleApplications) {
			boolean success = recognitionRuleApp.execute(null);
			assert (success) : "Could not apply rule " + recognitionRuleApp + ". Should never happen";

			if (success) {
				addMatches(recognitionRuleApp);
			}
		}
	}
	
	/**
	 * Clients that want to apply recognition rules should use this methods to
	 * obtain the single instance of a graph factory.
	 * 
	 * @return Single instance of a graph factory
	 */
	public LiftingGraphFactory getGraphFactory() {
		return this.graphFactory;
	}

	/**
	 * Get "trace" back to recognition rule for given SemanticChangeSet scs
	 * 
	 * @param scs
	 * @return
	 */
	public RecognitionRuleMatch getRecognitionRuleMatch(SemanticChangeSet scs) {
		return scs2rrMatch.get(scs);
	}

	/**
	 * Get "trace" back to edit rule for given SemanticChangeSet scs
	 * 
	 * @param scs
	 * @return
	 */
	public EngineBasedEditRuleMatch getEditRuleMatch(SemanticChangeSet scs) {
		return scs2erMatch.get(scs);
	}

	/**
	 * Removes recognitionRuleMatch and editRuleMatch for scs.
	 * 
	 * @param scs
	 */
	public void removeMatches(SemanticChangeSet scs) {
		scs2rrMatch.remove(scs);
		scs2erMatch.remove(scs);

		// Remove from difference (if EditRuleMatch serialization is requested)
		scs.setEditRuleMatch(null);
	}

	/**
	 * Adds editRuleMatch and recognitionRuleMatch for recognitionruleApp
	 * 
	 * @param recognitionruleApp
	 */
	private void addMatches(RuleApplication recognitionruleApp) {
		SemanticChangeSet scs = getSemanticChangeSet(recognitionruleApp);

		// Internal data structures
		RecognitionRuleMatch rrMatch = new RecognitionRuleMatch(recognitionruleApp);
		EngineBasedEditRuleMatch erMatch = new EngineBasedEditRuleMatch(rrMatch, this);

		scs2rrMatch.put(scs, rrMatch);
		scs2erMatch.put(scs, erMatch);

		// Add to difference (if EditRuleMatch serialization is required)
		if (settings.isSerializeEditRuleMatch()) {
			EditRuleMatch editRuleMatch = SymmetricFactory.eINSTANCE.createEditRuleMatch();
			scs.setEditRuleMatch(editRuleMatch);
			for (Node editRuleNode : erMatch.getMatchedNodesA()) {
				EObjectSet occurrences = SymmetricFactory.eINSTANCE.createEObjectSet();
				occurrences.addElements(erMatch.getOccurenceA(editRuleNode));
				editRuleMatch.getNodeOccurrencesA().put(EMFModelAccessEx.getURIFragment(editRuleNode), occurrences);
			}
			for (Node editRuleNode : erMatch.getMatchedNodesB()) {
				EObjectSet occurrences = SymmetricFactory.eINSTANCE.createEObjectSet();
				occurrences.addElements(erMatch.getOccurenceB(editRuleNode));
				editRuleMatch.getNodeOccurrencesB().put(EMFModelAccessEx.getURIFragment(editRuleNode), occurrences);
			}
		}
	}

	/**
	 * Returns the SemanticChangeSet which has been created by ruleApp (via
	 * co-match of RecognitionRule Matching)
	 * 
	 * @param recognitionRuleApp
	 * @return SemanticChangeSet
	 */
	private SemanticChangeSet getSemanticChangeSet(RuleApplication recognitionRuleApp) {
		Collection<EObject> values = recognitionRuleApp.getResultMatch().getNodeTargets();
		List<SemanticChangeSet> createdChangeSets = new ArrayList<SemanticChangeSet>();
		for (EObject eObject : values) {
			if (eObject instanceof SemanticChangeSet) {
				createdChangeSets.add((SemanticChangeSet) eObject);
			}
		}

		assert (createdChangeSets.size() == 1) : "No or multiple semantic change sets were created by ruleApp "
				+ recognitionRuleApp.toString();

		return createdChangeSets.get(0);
	}

	/**
	 * recognizerThreads call this method to add a recognition rule application
	 * for every recognition rule match that can be found. Thus, method is
	 * synchronized!
	 * 
	 * @param rrApp
	 */
	public synchronized void addRecognitionRuleApplication(RuleApplication rrApp) {
		recognizerRuleApplications.add(rrApp);
	}

	/**
	 * @return The difference this RecognitionEngine is working on
	 */
	public SymmetricDifference getDifference() {
		return this.difference;
	}

	/**
	 * @return The rulebases used by this RecognitionEngine
	 */
	public Set<IRuleBase> getUsedRulebases() {
		return this.usedRulebases;
	}

	/**
	 * Mapping: EditRule r -> Set of SemanticChangeSets representing an
	 * invocation of r
	 */
	public Map<EditRule, Set<SemanticChangeSet>> getEditRule2SCS() {
		Map<EditRule, Set<SemanticChangeSet>> editRule2SCS = new HashMap<EditRule, Set<SemanticChangeSet>>();
		for (SemanticChangeSet scs : scs2erMatch.keySet()) {
			EngineBasedEditRuleMatch editRuleMatch = scs2erMatch.get(scs);
			EditRule er = editRuleMatch.getEditRule();
			if (!editRule2SCS.containsKey(er)) {
				editRule2SCS.put(er, new HashSet<SemanticChangeSet>());
			}
			editRule2SCS.get(er).add(scs);
		}

		return editRule2SCS;
	}

	public Set<String> getDocumentTypes() {
		return EMFModelAccessEx.getDocumentTypes(difference.getModelA());
	}
}
