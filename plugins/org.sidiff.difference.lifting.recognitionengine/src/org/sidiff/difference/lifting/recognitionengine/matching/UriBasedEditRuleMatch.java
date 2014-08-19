package org.sidiff.difference.lifting.recognitionengine.matching;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.symmetric.EditRuleMatch;
import org.sidiff.difference.symmetric.SemanticChangeSet;

public class UriBasedEditRuleMatch extends BasicEditRuleMatch {

	public UriBasedEditRuleMatch(SemanticChangeSet scs) {
		EditRule editRule = scs.resolveEditRule();
		assert (editRule != null) : "EditRule for SemanticChangeSet " + scs + " cannot be resolved!";
		assert (editRule.eResource() != null) : "editRule " + editRule + " is not contained in a resource!";

		Resource henshinResource = editRule.getExecuteMainUnit().eResource();		
		
		setEditRule(editRule);
		EditRuleMatch erMatch = scs.getEditRuleMatch();

		assert (erMatch != null) : "No EditRuleMatch has been seriaized for  SemanticChangeSet " + scs + "!";

		// Resolve node occurrences in A
		for (String fragment : erMatch.getNodeOccurrencesA().keySet()) {
			Node node = (Node) henshinResource.getEObject(fragment);

			assert (node != null) : "Node with the URI fragment " + fragment + " cannot be found in resource "
					+ henshinResource + "!";

			nodeOccurencesA.put(node, erMatch.getNodeOccurrencesA().get(fragment).toJavaSet());
		}

		// Resolve node occurrences in B
		for (String fragment : erMatch.getNodeOccurrencesB().keySet()) {
			Node node = (Node) henshinResource.getEObject(fragment);

			assert (node != null) : "Node with the URI fragment " + fragment + " cannot be found in resource "
					+ henshinResource + "!";

			nodeOccurencesB.put(node, erMatch.getNodeOccurrencesB().get(fragment).toJavaSet());
		}

		// Derive the edge occurrences
		super.deriveEdgeOccurrences();
	}
}
