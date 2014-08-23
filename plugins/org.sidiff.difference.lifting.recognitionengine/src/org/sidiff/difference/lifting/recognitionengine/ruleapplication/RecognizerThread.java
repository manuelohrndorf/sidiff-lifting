package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.CREATE_GRAPH;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.MATCH_RR;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.STATISTICS;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.analyseEGraph;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.startSplitTimer;
import static org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngineStatistics.stopSplitTimer;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
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
 * recognition rules in the usual caller thread withoud starting new threads. In
 * this case, clients should not call the Method this{@link #run()} but this
 * {@link #recognize()} directly.
 */
public class RecognizerThread extends Thread {
	
	/**
	 * The recognition rules to execute.
	 */
	private Set<Rule> recognitionRules;
	
	/**
	 * The recognition engine
	 */
	private RecognitionEngine recognitionEngine;
	
	/**
	 * Initialize recognizer
	 * 
	 * @param recognitionRules
	 *            the recognition rules to execute.
	 * @param recognitionEngine
	 *            the RecognitionEngine
	 */
	public RecognizerThread(Set<Rule> recognitionRules, RecognitionEngine recognitionEngine) {
		this.recognitionRules = recognitionRules;		
		this.recognitionEngine = recognitionEngine;
	}
	
	/**
	 * Will execute all recognition rules on all matches that can be found in
	 * parallel. A recognition rule groups the atomic changes to semantic change
	 * sets. Note that there maybe overlapping semantic change sets.
	 */
	public void recognize() {
		
		// Important: Use our sorting of recognition rule nodes, not Henshin's sorting
		Engine engine = new EngineImpl();
		engine.getOptions().put(Engine.OPTION_SORT_VARIABLES, false);
		
		try {
			
			// Collect unit applications
			for (Rule rr : recognitionRules) {
				// NOTE: Avoid synchronized statistic method calls: if (STATISTICS)
				if (STATISTICS) startSplitTimer(CREATE_GRAPH, "" + rr.hashCode(), rr.getName());
				
				// Get working graph
				EGraph graph = recognitionEngine.getGraphFactory().getEGraph(rr);
				
				if (STATISTICS) stopSplitTimer(CREATE_GRAPH, "" + rr.hashCode());
				if (STATISTICS) analyseEGraph(graph, rr);

				// Match Recognition-Rules:
				LogUtil.log(LogEvent.NOTICE, "Matching: " + rr.getModule().getName() + "...");
				Iterator<Match> matchFinder = engine.findMatches(rr, graph, null).iterator();
				
				if (STATISTICS) startSplitTimer(MATCH_RR, "" + rr.hashCode(), rr.getName());
				
				int numberOfMatches = 0;
				while (matchFinder.hasNext()) {
					Match match = matchFinder.next();
					
					// Create Rule Application with prematch (which is actually a complete match)
					RuleApplication ruleApp = new RuleApplicationImpl(engine);
					ruleApp.setEGraph(graph);
					ruleApp.setRule(rr);
					ruleApp.setCompleteMatch(match);
					
					numberOfMatches++;
					recognitionEngine.addRecognitionRuleApplication(ruleApp);
				}
				
				if (STATISTICS) stopSplitTimer(MATCH_RR, "" + rr.hashCode());	
				LogUtil.log(LogEvent.NOTICE, "Matches found: " + numberOfMatches);	
			}
		} finally {
			// Shutdown:
			engine.shutdown();
			
			// FIXME: Workaround to remove Henshin rule change listeners: 
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