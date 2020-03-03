package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionRuleMatch;

/**
 * Match of a recognition rule (either a simple Henshin Rule or an amalgamated
 * one) into the SymmetricDifference.<br>
 * Please note that in the latter case (amalgamated recognition rule), a node of
 * a recognition rule may be mapped onto multiple diff objects.
 * 
 * @author kehrer
 */
public class RecognitionRuleMatch implements IRecognitionRuleMatch {

	// mapping of LHS or RHS nodes to diff objects
	private Map<Node, Set<EObject>> nodeMapping;

	private RuleApplication recognitionRuleApplication;

	/**
	 * Creates a new RecognitionRuleMatch:<br>
	 * 
	 * 1) If the RecognitionRule (given by the rrApplicationWrapper) is a simple
	 * Henshin rule, things are quite easy and the nodeMapping corresponds to
	 * the Match of the Henshin RuleApplication.<br>
	 * 
	 * 2) If the RecognitionRule is an amalgamated rule, then we have to
	 * construct the nodeMapping by taking all the multi rule matches (even recursive)
	 * into account.
	 * 
	 * @param recognitionRuleApplication
	 */
	public RecognitionRuleMatch(RuleApplication recognitionRuleApplication) {
		this.recognitionRuleApplication = recognitionRuleApplication;
		nodeMapping = new HashMap<>();

		// Kernel Match
		putToNodeMapping(recognitionRuleApplication.getCompleteMatch());
		
		// Multi matches (recursive)
		processMultiMatches(recognitionRuleApplication.getCompleteMatch());
	}

	/**
	 * Simple getter.
	 * 
	 * @return the mapping of LHS or RHS nodes to diff objects.
	 */
	@Override
	public Map<Node, Set<EObject>> getNodeMapping() {
		return Collections.unmodifiableMap(nodeMapping);
	}

	/**
	 * Returns the value which is bound to a recognition rule parameter
	 * application. We use this method to retrieve actual values of
	 * ValueParameters.
	 * 
	 * TODO (TK 6.Okt.2012): Funktioniert bestens für simple Erkennungsregeln.<br>
	 * Man beachte aber, dass im Falle von amalgamierten Erkennungsregeln
	 * "rrApplicationWrapper.ruleApplication" die temporär erzeugte Henshin
	 * Regel referenziert. Sofern eine Multiregel Parameter hat ist zu klären,
	 * ob das auch funktioniert (Stichwort "generierte Parameternamen", s.
	 * Workaround in RecognitionEngine).
	 * 
	 * FIXME TK 9.Juli 2013: Punkt Möglicherweise erledigt mit Henshin-Migration auf 0.9.x
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public Object getParameterValue(String name) {
		return recognitionRuleApplication.getResultParameterValue(name);
	}
	
	@Override
	public String toString() {
		String res = "RR-Match:\n";
		for (Node rrNode : nodeMapping.keySet()) {
			res += rrNode + " => " + nodeMapping.get(rrNode) + "\n";
		}
		return res;
	}
	
	// *********************** private helper methods *****************
	
	private void putToNodeMapping(Match match){
		for (Node node : match.getRule().getLhs().getNodes()) {
			putToNodeMapping(node, match.getNodeTarget(node));
		}
	}

	private void putToNodeMapping(Node node, EObject object) {
		nodeMapping.computeIfAbsent(node, unused -> new HashSet<>()).add(object);
	}

	private void processMultiMatches(Match kernelMatch){
		// iterate over all multi rules
		for (Rule multiRule : kernelMatch.getRule().getAllMultiRules()) {
			// get alle matches for this multiRule
			List<Match> multiMatches = kernelMatch.getMultiMatches(multiRule);
			for (Match multiMatch : multiMatches) {				
				putToNodeMapping(multiMatch);
				
				// recursion
				processMultiMatches(multiMatch);						
			}
		}
	}
}
