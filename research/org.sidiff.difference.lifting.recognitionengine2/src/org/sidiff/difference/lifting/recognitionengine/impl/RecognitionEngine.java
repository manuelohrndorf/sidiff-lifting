package org.sidiff.difference.lifting.recognitionengine.impl;

import static org.sidiff.difference.lifting.recognitionengine.impl.RecognitionEngineStatistics.EXECUTION;
import static org.sidiff.difference.lifting.recognitionengine.impl.RecognitionEngineStatistics.RULE_SET_REDUCTION;

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
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.emf.access.EMFModelAccess;
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
	private Set<RuleApplication> recognizerRuleApplications = new HashSet<RuleApplication>();

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
		this.setup = setup;
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
		this.graphFactory = new LiftingGraphFactory(liftingGraphDomainMap, setup.getImports(), setup.getScope());
		
		// Get all recognition rules to be used:
		this.recognitionRules = new HashMap<Rule, RecognitionRuleBlueprint>();
		
		for (ILiftingRuleBase rb : setup.getRulebases()) {
			for (Rule rr : rb.getActiveRecognitonUnits()) {
				RecognitionRuleBlueprint blueprint = new RecognitionRuleBlueprint(rr);
				recognitionRules.put(rr, blueprint);
			}
		}

		// Initialize Rule Applications:
		recognizerRuleApplications = new HashSet<RuleApplication>();

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

		if (setup.isUseThreadPool()) {
			executeParallel();
		} else {
			executeSequential();
		}

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
		Set<Rule> units = new HashSet<Rule>();

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
		LogUtil.log(LogEvent.NOTICE, "------------------- Difference Analysis --------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		DifferenceAnalysis analysis = new DifferenceAnalysis(setup.getDifference());

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

		setup.getRuleSorter().setDifferenceAnalysis(analysis);

		for (Rule recognitionRule : recognitionRules.keySet()) {
			if (!filtered.contains(recognitionRule)) {
				// Sort kernel rule
				ECollections.sort(recognitionRule.getLhs().getNodes(), setup.getRuleSorter());

				// Sort all multi-rules (if there are any)
				for (Rule multiRule : recognitionRule.getAllMultiRules()) {
					ECollections.sort(multiRule.getLhs().getNodes(), setup.getRuleSorter());
				}
			}
		}
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
			LogUtil.log(LogEvent.DEBUG, "Execute recognition rule: " + recognitionRuleApp.getRule().eResource());
			boolean success = recognitionRuleApp.execute(null);
			assert (success) : "Could not apply rule " + recognitionRuleApp + ". Should never happen";

			if (success) {
				addMatches(recognitionRuleApp);
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
	private void addMatches(RuleApplication recognitionruleApp) {
		if (setup.isCalculateEditRuleMatch()) {

			SemanticChangeSet scs = getSemanticChangeSet(recognitionruleApp);

			// Internal data structures
			RecognitionRuleMatch rrMatch = new RecognitionRuleMatch(recognitionruleApp);
			EngineBasedEditRuleMatch erMatch = new EngineBasedEditRuleMatch(rrMatch, this);

			scs2rrMatch.put(scs, rrMatch);
			scs2erMatch.put(scs, erMatch);

			// Add to difference (if EditRuleMatch serialization is required)
			if (setup.isSerializeEditRuleMatch()) {
				EditRuleMatch editRuleMatch = SymmetricFactory.eINSTANCE.createEditRuleMatch();
				scs.setEditRuleMatch(editRuleMatch);
				
				for (Node editRuleNode : erMatch.getMatchedNodesA()) {
					EObjectSet occurrences = SymmetricFactory.eINSTANCE.createEObjectSet();
					occurrences.addElements(erMatch.getOccurenceA(editRuleNode));
					editRuleMatch.getNodeOccurrencesA().put(EMFModelAccess.getURIFragment(editRuleNode), occurrences);
				}
				
				for (Node editRuleNode : erMatch.getMatchedNodesB()) {
					EObjectSet occurrences = SymmetricFactory.eINSTANCE.createEObjectSet();
					occurrences.addElements(erMatch.getOccurenceB(editRuleNode));
					editRuleMatch.getNodeOccurrencesB().put(EMFModelAccess.getURIFragment(editRuleNode), occurrences);
				}
			}
		}
	}

	/**
	 * The recognizer threads call this method to add the recognition rule
	 * applications for all recognition rule matches that can be found. Thus,
	 * method is synchronized!
	 * 
	 * @param rrApp
	 *            All recognition rule applications of a recognizer thread.
	 */
	protected synchronized void addRecognitionRuleApplication(Collection<RuleApplication> rrApplications) {
		recognizerRuleApplications.addAll(rrApplications);
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

	@Override
	public Map<EditRule, Set<SemanticChangeSet>> getChangeSets() {
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
	
	/**
	 * @return A new graph matching engine.
	 */
	public Engine createGraphMatchingEngine() {
		return new LiftingGraphEngine(liftingGraphDomainMap, liftingGraphIndex);
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
	
	public RecognitionRuleBlueprint getRecognitionRuleBlueprint(Rule rr) {
		return recognitionRules.get(rr);
	}
	
	/**
	 * @return The difference this RecognitionEngine is working on.
	 */
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
