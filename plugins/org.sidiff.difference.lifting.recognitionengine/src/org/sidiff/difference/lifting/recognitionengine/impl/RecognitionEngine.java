package org.sidiff.difference.lifting.recognitionengine.impl;

import static org.sidiff.difference.lifting.recognitionengine.impl.RecognitionEngineStatistics.EXECUTION;
import static org.sidiff.difference.lifting.recognitionengine.impl.RecognitionEngineStatistics.RULE_SET_REDUCTION;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.recognitionengine.IEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionEngine;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.RecognitionEngineSetup;
import org.sidiff.difference.lifting.recognitionengine.graph.LiftingGraphDomainMap;
import org.sidiff.difference.lifting.recognitionengine.graph.LiftingGraphEngine;
import org.sidiff.difference.lifting.recognitionengine.graph.LiftingGraphFactory;
import org.sidiff.difference.lifting.recognitionengine.graph.LiftingGraphIndex;
import org.sidiff.difference.lifting.recognitionengine.matching.EngineBasedEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.matching.RecognitionRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.rules.RecognitionRuleApplicationAnalysis;
import org.sidiff.difference.lifting.recognitionengine.rules.RecognitionRuleBlueprint;
import org.sidiff.difference.lifting.recognitionengine.rules.RecognitionRuleFilter;
import org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.symmetric.EObjectSet;
import org.sidiff.difference.symmetric.EditRuleMatch;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;
import org.sidiff.editrule.rulebase.EditRule;

public class RecognitionEngine implements IRecognitionEngine {

	/**
	 * The Recognition-Engine setup.
	 */
	private RecognitionEngineSetup setup;
	
	/**
	 * Can be used to test the performance of the recognition engine.
	 */
	protected RecognitionEngineStatistics statistic;

	/**
	 * The corresponding difference index knowing the low-level changes per type.
	 */
	private LiftingGraphDomainMap liftingGraphDomainMap;
	
	/**
	 * The corresponding difference index knowing the low-level changes per object.
	 */
	private LiftingGraphIndex liftingGraphIndex;
	
	/**
	 * Factory that creates the Henshin graph for a corresponding recognition rule.
	 */
	private LiftingGraphFactory graphFactory;
	
	/**
	 * The recognition rules with their corresponding change type blueprints.
	 */
	private Map<Rule, RecognitionRuleBlueprint> recognitionRules;

	/**
	 * RecognitionRules that are not needed.
	 */
	private Set<Rule> filtered = Collections.emptySet();

	/**
	 * Set of all rule applications created by the recognizer threads.
	 */
	private Set<RuleApplication> recognizerRuleApplications = new HashSet<>();

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
	 * Initialize a new Recognition-Engine.
	 * 
	 * @param setup
	 *            The Recognition-Engine setup.
	 */
	public RecognitionEngine(RecognitionEngineSetup setup) {
		this.setup = Objects.requireNonNull(setup, "setup is null");
	}

	@Override
	public void execute() {
		
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "-------------- INITIALIZE RECOGNITION ENGINE ---------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		
		// Create a indices:
		this.liftingGraphDomainMap = new LiftingGraphDomainMap(setup.getDifference());
		this.liftingGraphIndex = new LiftingGraphIndex(setup.getDifference());
		
		// Create the graph factory:
		this.graphFactory = new LiftingGraphFactory(
				setup.isBuildGraphPerRule(), liftingGraphDomainMap, setup.getImports(), setup.getScope());
		
		// Get all recognition rules to be used:
		this.recognitionRules = new HashMap<>();
		
		for (ILiftingRuleBase rb : setup.getRulebases()) {
			for (Rule rr : rb.getActiveRecognitonUnits()) {
				recognitionRules.put(rr, new RecognitionRuleBlueprint(rr));
			}
		}

		// Initialize Rule Applications:
		recognizerRuleApplications = new HashSet<>();

		// Some further initialization:
		scs2rrMatch = new HashMap<SemanticChangeSet, RecognitionRuleMatch>();
		scs2erMatch = new HashMap<SemanticChangeSet, EngineBasedEditRuleMatch>();

		// Optimize the Rulebase: Rule Set Reduction
		if (setup.isRuleSetReduction()) {
			getStatistic().startTimer(RULE_SET_REDUCTION);
			filterRecognitionRules();
			getStatistic().stopTimer(RULE_SET_REDUCTION);
		}

		// Optimize the Rulebase: Sorting Recognition-Rule nodes
		sortRecognitionRuleNodes();
		
		// Start execution:
		getStatistic().startTimer(EXECUTION);
		Long startTime = System.currentTimeMillis();

		if (setup.isUseThreadPool()) {
			executeParallel();
		} else {
			executeSequential();
		}
		
		LogUtil.log(LogEvent.NOTICE, "Lifting Time: " + (System.currentTimeMillis() - startTime) / 1000.0 + "s");
		getStatistic().stopTimer(EXECUTION);

		// Finish Statistic:
		getStatistic().finishStatistic(setup.getDifference(), recognitionRules, filtered, graphFactory);
	}

	/**
	 * Sequential execution of all Recognition-Rules.
	 */
	private void executeSequential() {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "--------- SEQUENTIALLY MATCHING RECOGNITION RULES ----------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		// Sequential recognizer:
		Set<Rule> units = new HashSet<Rule>();
		units.addAll(recognitionRules.keySet());
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
		LogUtil.log(LogEvent.NOTICE, "----------- PARALLEL MATCHING RECOGNITION RULES ------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		// Create thread pool
		ExecutorService executor = Executors.newFixedThreadPool(setup.getNumberOfThreads());

		// List of units for each thread
		Set<Rule> units = new HashSet<>();

		// Start recognizer threads
		for (Rule transformationUnit : recognitionRules.keySet()) {

			// Filter rules
			if (!filtered.contains(transformationUnit)) {
				if (units.size() == setup.getRulesPerThread()) {
					// Start new recognizer thread
					RecognizerThread recognizerThread = new RecognizerThread(units, this);

					// Do all the hard work in one {@link RecognizerThread}
					executor.execute(recognizerThread);

					// New rule collection for next thread
					units = new HashSet<>();
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
			throw new RuntimeException(e);
		}

		// Apply recognition rules
		applyRecognitionRules();
	}
	
	/**
	 * Filters unmatchable recognition rules by counting the atomic changes in
	 * the difference and in the recognition rule.
	 */
	private void filterRecognitionRules() {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "----------------- FILTER RECOGNITION RULES -----------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		LogUtil.log(LogEvent.NOTICE, "Filter recognition rules.");
		RecognitionRuleFilter filter = new RecognitionRuleFilter();
		filtered = filter.filter(liftingGraphDomainMap, recognitionRules);

		LogUtil.log(LogEvent.NOTICE, "Filtered " + filtered.size() + " of " + recognitionRules.size() + " recognitionRules");

		for (Unit unit : filtered) {
			LogUtil.log(LogEvent.DEBUG, unit.getModule().getName());
		}
	}

	/**
	 * Optimizes the (matching) order of the nodes in the Recognition-Rules.
	 */
	private void sortRecognitionRuleNodes() {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------ SORT RECOGNITION RULES ------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		DifferenceAnalysis analysis = RecognitionRuleSorterUtil.sort(
				setup.getRuleSorter(), recognitionRules.keySet(), filtered, setup.getDifference());

		
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------- Difference Analysis --------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		
		LogUtil.log(LogEvent.NOTICE, "Difference:");
		LogUtil.log(LogEvent.NOTICE, " Total AddObjects: " + analysis.getAddObjectCount());
		LogUtil.log(LogEvent.NOTICE, " Total RemoveObjects: " + analysis.getRemoveObjectCount());
		LogUtil.log(LogEvent.NOTICE, " Total AddReferences: " + analysis.getAddReferenceCount());
		LogUtil.log(LogEvent.NOTICE, " Total RemoveReferences: " + analysis.getRemoveReferenceCount());
		LogUtil.log(LogEvent.NOTICE, " Total AttributeValueChanges: " + analysis.getAttributeValueChangeCount());
		LogUtil.log(LogEvent.NOTICE, " Total Correspondences: " + analysis.getCorrespondenceCount());
	}

	private void applyRecognitionRules() {
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "---------------- APPLYING RECOGNITION RULES ----------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		// Pre-filtering of collected rule applications: Keep only one
		// representative of a set of equal (same changes covered and same
		// recognition rule) recognition rule applications.
		Collection<List<RuleApplication>> rrApplicationsByRule = RecognitionRuleApplicationAnalysis
				.partitionByEqualRule(recognizerRuleApplications);
		Set<RuleApplication> toBeRemoved = new HashSet<>();
		
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
			// Internal data structures
			RecognitionRuleMatch rrMatch = new RecognitionRuleMatch(recognitionRuleApp);
			EngineBasedEditRuleMatch erMatch = new EngineBasedEditRuleMatch(rrMatch, this);
			
			// Check injectivity
			if(recognitionRuleApp.getRule().isInjectiveMatching()) {
				if (erMatch.isNonInjective()) {
					LogUtil.log(LogEvent.DEBUG, "Skip recognition rule (non-injective matching): "
							+ recognitionRuleApp.getRule().eResource());
					continue;
				}				
			}
			
			LogUtil.log(LogEvent.DEBUG, "Execute recognition rule: " + recognitionRuleApp.getRule().eResource());
			boolean success = recognitionRuleApp.execute(null);
			assert (success) : "Could not apply rule " + recognitionRuleApp + ". Should never happen";

			if (success) {
				addMatches(recognitionRuleApp, rrMatch, erMatch);
			}
		}
	}

	@Override
	public Set<RuleApplication> getRecognitionRuleApplications() {
		return recognizerRuleApplications;
	}

	@Override
	public IRecognitionRuleMatch getRecognitionRuleMatch(SemanticChangeSet scs) {
		return scs2rrMatch.get(scs);
	}

	@Override
	public IEditRuleMatch getEditRuleMatch(SemanticChangeSet scs) {
		return scs2erMatch.get(scs);
	}

	@Override
	public void removeMatches(SemanticChangeSet scs) {
		if (setup.isCalculateEditRuleMatch()) {
			scs2rrMatch.remove(scs);
			scs2erMatch.remove(scs);
	
			// Remove from difference (if EditRuleMatch serialization is requested)
			scs.setEditRuleMatch(null);
		}
	}

	/**
	 * Adds editRuleMatch and recognitionRuleMatch for recognitionruleApp
	 * 
	 * @param recognitionruleApp
	 */
	private void addMatches(RuleApplication recognitionruleApp, RecognitionRuleMatch rrMatch, EngineBasedEditRuleMatch erMatch) {
		// Store internally in RecognitionEngine (transient)
		SemanticChangeSet scs = getSemanticChangeSet(recognitionruleApp);

		scs2rrMatch.put(scs, rrMatch);
		scs2erMatch.put(scs, erMatch);

		// Also add to difference (if EditRuleMatch serialization is required)
		if (setup.isSerializeEditRuleMatch()) {
			EditRuleMatch editRuleMatch = SymmetricFactory.eINSTANCE.createEditRuleMatch();
			scs.setEditRuleMatch(editRuleMatch);
			
			for (Node editRuleNode : erMatch.getMatchedNodesA()) {
				EObjectSet occurrences = SymmetricFactory.eINSTANCE.createEObjectSet();
				occurrences.addElements(erMatch.getOccurenceA(editRuleNode));
				editRuleMatch.getNodeOccurrencesA().put(EcoreUtil.getURI(editRuleNode).fragment(), occurrences);
			}
			
			for (Node editRuleNode : erMatch.getMatchedNodesB()) {
				EObjectSet occurrences = SymmetricFactory.eINSTANCE.createEObjectSet();
				occurrences.addElements(erMatch.getOccurenceB(editRuleNode));
				editRuleMatch.getNodeOccurrencesB().put(EcoreUtil.getURI(editRuleNode).fragment(), occurrences);
			}
		}
	}
	
	/**
	 * The recognizer threads call this method to add the recognition rule
	 * applications for all recognition rule matches that can be found. Thus,
	 * method is thread-safe!
	 * 
	 * @param rrApp
	 *            All recognition rule applications of a recognizer thread.
	 */
	protected void addRecognitionRuleApplication(Collection<RuleApplication> rrApplications) {
		synchronized(recognizerRuleApplications) {
			recognizerRuleApplications.addAll(rrApplications);
		}
	}
	
	/**
	 * Returns the SemanticChangeSet which has been created by ruleApp (via
	 * co-match of RecognitionRule Matching)
	 * 
	 * @param recognitionRuleApp
	 * @return SemanticChangeSet
	 */
	private static SemanticChangeSet getSemanticChangeSet(RuleApplication recognitionRuleApp) {
		List<SemanticChangeSet> createdChangeSets =
			recognitionRuleApp.getResultMatch().getNodeTargets().stream()
				.filter(SemanticChangeSet.class::isInstance)
				.map(SemanticChangeSet.class::cast)
				.collect(Collectors.toList());

		assert (createdChangeSets.size() == 1) : "No or multiple semantic change sets were created by ruleApp "
				+ recognitionRuleApp.toString();

		return createdChangeSets.get(0);
	}

	@Override
	public Map<EditRule, Set<SemanticChangeSet>> getChangeSets() {
		return scs2erMatch.entrySet().stream()
				.collect(Collectors.groupingBy(e -> e.getValue().getEditRule(),
						Collectors.mapping(e -> e.getKey(), Collectors.toSet())));
	}
	
	/**
	 * @return A new graph matching engine.
	 */
	public Engine createGraphMatchingEngine() {
		if (setup.isOptimizeMatchingEngine()) {
			return new LiftingGraphEngine(liftingGraphDomainMap, liftingGraphIndex);
		}
		return new EngineImpl();
	}
	
	/**
	 * Clients that want to apply rules on a difference should use this methods
	 * to obtain the single instance of a graph factory.
	 * 
	 * @return Single instance of a graph factory
	 */
	public LiftingGraphFactory getGraphFactory() {
		return graphFactory;
	}
	
	@Override
	public EGraph getGraphModelA() {
		if (getGraphFactory() != null) {
			return getGraphFactory().getModelAGraph();
		}
		return null;
	}

	@Override
	public EGraph getGraphModelB() {
		if (getGraphFactory() != null) {
			return getGraphFactory().getModelBGraph();
		}
		return null;
	}
	
	public RecognitionRuleBlueprint getRecognitionRuleBlueprint(Rule rr) {
		return recognitionRules.get(rr);
	}
	
	/**
	 * @return The difference this RecognitionEngine is working on.
	 */
	@Override
	public SymmetricDifference getDifference() {
		return setup.getDifference();
	}
	
	@Override
	public RecognitionEngineSetup getSetup() {
		return setup;
	}
	
	@Override
	public RecognitionEngineStatistics getStatistic() {
		if (statistic == null) {
			statistic = new RecognitionEngineStatistics();
		}
		return statistic;
	}
}
