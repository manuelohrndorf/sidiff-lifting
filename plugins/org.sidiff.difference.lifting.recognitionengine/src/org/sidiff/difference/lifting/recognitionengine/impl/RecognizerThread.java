package org.sidiff.difference.lifting.recognitionengine.impl;

import static org.sidiff.difference.lifting.recognitionengine.impl.RecognitionEngineStatistics.CREATE_GRAPH;
import static org.sidiff.difference.lifting.recognitionengine.impl.RecognitionEngineStatistics.MATCH_RR;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

/**
 * Starts the recognition procedure. The thread will execute all recognition
 * rules. A recognition rule groups the atomic changes to semantic change sets.
 * Note that there maybe overlapping semantic change sets.<br/>
 * 
 * Please note that the recognizerThread can also be used to apply the
 * recognition rules in the usual caller thread without starting new threads. In
 * this case, clients should not call the Method this{@link #run()} but this
 * {@link #recognize()} directly.
 */
public class RecognizerThread extends Thread {
	
	/**
	 * Checks if the Recognition-Engine statistic output is enabled.
	 */
	private boolean enableStatistic = false;
	
	/**
	 * Can be used to test the performance of the recognition engine.
	 */
	protected RecognitionEngineStatistics statistic;
	
	/**
	 * The recognition rules to execute.
	 */
	private Set<Rule> recognitionRules;
	
	/**
	 * The recognition engine
	 */
	private RecognitionEngine recognitionEngine;
	
	/**
	 * Set of all rule applications created by the recognizer threads.
	 */
	private Set<RuleApplication> recognizerRuleApplications = new HashSet<>();
	
	/**
	 * Initialize recognizer
	 * 
	 * @param recognitionRules
	 *            the recognition rules to execute.
	 * @param recognitionEngine
	 *            the RecognitionEngine
	 */
	public RecognizerThread(Set<Rule> recognitionRules, RecognitionEngine recognitionEngine) {
		super("RecognizerThread");
		this.recognitionRules = recognitionRules;		
		this.recognitionEngine = recognitionEngine;
		
		enableStatistic = recognitionEngine.getStatistic().isEnabled();
		statistic = recognitionEngine.getStatistic();
	}
	
	/**
	 * Will execute all recognition rules on all matches that can be found in
	 * parallel. A recognition rule groups the atomic changes to semantic change
	 * sets. Note that there maybe overlapping semantic change sets.
	 */
	public void recognize() {
		
		// Important: Use our sorting of recognition rule nodes, not Henshin's sorting
		Engine engine = recognitionEngine.createGraphMatchingEngine();
		engine.getOptions().put(Engine.OPTION_SORT_VARIABLES, false);
		
		try {
			
			// Collect unit applications
			for (Rule rr : recognitionRules) {
				// NOTE: Avoid synchronized statistic method calls: if (enableStatistic)
				if (enableStatistic) statistic.startSplitTimer(CREATE_GRAPH, "" + rr.hashCode(), rr.getName());
				
				// Get working graph
				EGraph graph = recognitionEngine.getGraphFactory().createLiftingGraph(
						rr, recognitionEngine.getRecognitionRuleBlueprint(rr));
				
				if (enableStatistic) statistic.stopSplitTimer(CREATE_GRAPH, "" + rr.hashCode());

				// Match Recognition-Rules:
				LogUtil.log(LogEvent.NOTICE, "Matching: " + rr.getModule().getName() + "...");
				LogUtil.log(LogEvent.DEBUG, "Matching: " + rr.getModule().eResource() + "...");
				
				if (enableStatistic) statistic.startSplitTimer(MATCH_RR, "" + rr.hashCode(), rr.getName());

				int numberOfMatches = 0;
				for (Match match : engine.findMatches(rr, graph, null)) {
					// Create Rule Application with prematch (which is actually a complete match)
					RuleApplication ruleApp = new RuleApplicationImpl(engine);
					ruleApp.setEGraph(graph);
					ruleApp.setRule(rr);
					ruleApp.setCompleteMatch(match);
					
					numberOfMatches++;
					recognizerRuleApplications.add(ruleApp);
				}
				
				if (enableStatistic) statistic.stopSplitTimer(MATCH_RR, "" + rr.hashCode());
				
				LogUtil.log(LogEvent.NOTICE, "Matches found: " + numberOfMatches);	
			}
		} finally {
			
			// Add matches to recognition engine:
			recognitionEngine.addRecognitionRuleApplication(recognizerRuleApplications);
			
			// Shutdown:
			engine.shutdown();
			
			// FIXME: WORKAROUND: Remove Henshin rule change listeners: 
			for (Rule rr : recognitionRules) {
				rr.eAdapters().clear();
			}
		}
	}
	
	@Override
	public void run() {
		// Just delegates the job to recognize()
		recognize();
	}
}