package org.sidiff.difference.lifting.recognitionengine;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.emf.access.Link;
import org.sidiff.difference.lifting.recognitionengine.matching.NacMatch;
import org.sidiff.editrule.rulebase.EditRule;

public interface IEditRuleMatch {

	public EditRule getEditRule();

	public Set<EObject> getOccurenceA(Node editRuleNode);

	public Set<EObject> getOccurenceB(Node editRuleNode);

	public Set<Link> getOccurenceA(Edge editRuleEdge);

	public Set<Link> getOccurenceB(Edge editRuleEdge);

	public Set<Node> getMatchedNodesA();

	public Set<Node> getMatchedNodesB();
	
	public Set<Edge> getMatchedEdgesA();
	
	public Set<Edge> getMatchedEdgesB();

	/**
	 * 
	 * @return The Nac-Occurrences in Model A if presented
	 */
	public Set<NacMatch> getNacOccurrences();

	/**
	 * Convenience function for client access:<br>
	 * Returns the occurrences of a forbid node in model A.<br>
	 * In case of multiple occurrences of the NAC to which the given forbidNode
	 * belongs to, the union of all forbidNode-occurrences (via different NACs)
	 * is returned.
	 * 
	 * @param forbidNode
	 * @return
	 */
	public Set<EObject> getForbidNodeOccurenceA(Node forbidNode);

	/**
	 * Convenience function for client access:<br>
	 * Returns the occurrences of a forbid edge in model A.<br>
	 * In case of multiple occurrences of the NAC to which the given forbidEdge
	 * belongs to, the union of all forbidEdge-occurrences (via different NACs)
	 * is returned.
	 * 
	 * @param forbidEdge
	 * @return
	 */
	public Set<Link> getForbidEdgeOccurenceA(Edge forbidEdge);

	public IRecognitionRuleMatch getRecognitionRuleMatch();
}
