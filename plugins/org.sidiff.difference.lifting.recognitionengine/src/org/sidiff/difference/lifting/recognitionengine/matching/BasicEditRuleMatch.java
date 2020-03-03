package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.collections.Pair;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Link;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.difference.lifting.recognitionengine.IEditRuleMatch;
import org.sidiff.editrule.rulebase.EditRule;

/**
 * Match of edit rules into the (symmetric) difference. Thus, the following
 * intermediate mappings are encapsulated:
 * 
 * 1) Traces from edit rule nodes to recognition rule nodes
 * 
 * 2) Matching of recognition rule into difference.
 * 
 * Please note that in case of amalgamated rules, an edit rule node my be mapped
 * onto multiple diff objects. <br/>
 * The same is true for edit rule edges: In case of amalgamation, an EditRule
 * edge may be mapped onto multiple links (i.e. EReference instances) in the
 * models A or B.
 * 
 * @author kehrer
 */
public abstract class BasicEditRuleMatch implements IEditRuleMatch {

	/**
	 * Mapping: EditRuleNode -> EObject A (via trace A)
	 * 
	 * Konvention: EditRuleNode:
	 * <ul>
	 * <li>Preserved node -> LHS Node</li>
	 * <li>Deleted node -> LHS Node</li>
	 * <li>Created node -> RHS Node</li>
	 * </ul>
	 * 
	 */
	protected Map<Node, Set<EObject>> nodeOccurencesA = new HashMap<>();

	/**
	 * Mapping: EditRuleNode -> EObject B (via trace B)
	 * 
	 * Konvention: EditRuleNode:
	 * <ul>
	 * <li>Preserved node -> LHS Node</li>
	 * <li>Deleted node -> LHS Node</li>
	 * <li>Created node -> RHS Node</li>
	 * </ul>
	 * 
	 */
	protected Map<Node, Set<EObject>> nodeOccurencesB = new HashMap<>();

	/**
	 * Mapping: EditRuleEdge -> Reference A (via trace A)
	 * 
	 * Konvention: EditRuleEdge:
	 * <ul>
	 * <li>Preserved edge -> LHS edge</li>
	 * <li>Deleted edge -> LHS edge</li>
	 * <li>Created edge -> RHS edge</li>
	 * </ul>
	 * 
	 */
	protected Map<Edge, Set<Link>> edgeOccurencesA = new HashMap<>();

	/**
	 * Mapping: EditRuleEdge -> Reference B (via trace B)
	 * 
	 * Konvention: EditRuleEdge:
	 * <ul>
	 * <li>Preserved edge -> LHS edge</li>
	 * <li>Deleted edge -> LHS edge</li>
	 * <li>Created edge -> RHS edge</li>
	 * </ul>
	 * 
	 */
	protected Map<Edge, Set<Link>> edgeOccurencesB = new HashMap<>();

	/**
	 * The {@link EditRule} for which this EditRuleMatch is being created.
	 */
	private EditRule editRule;

	@Override
	public EditRule getEditRule() {
		return editRule;
	}

	@Override
	public Set<EObject> getOccurenceA(Node editRuleNode) {
		Node keyNode = getKeyNode(editRuleNode);
		Set<EObject> occurences = nodeOccurencesA.get(keyNode);
		return occurences == null ? Collections.emptySet() : Collections.unmodifiableSet(occurences);
	}

	@Override
	public Set<EObject> getOccurenceB(Node editRuleNode) {
		Node keyNode = getKeyNode(editRuleNode);
		Set<EObject> occurences = nodeOccurencesB.get(keyNode);
		return occurences == null ? Collections.emptySet() : Collections.unmodifiableSet(occurences);
	}

	@Override
	public Set<Link> getOccurenceA(Edge editRuleEdge) {
		Edge keyEdge = getKeyEdge(editRuleEdge);
		Set<Link> occurences = edgeOccurencesA.get(keyEdge);
		return occurences == null ? Collections.emptySet() : Collections.unmodifiableSet(occurences);
	}

	@Override
	public Set<Link> getOccurenceB(Edge editRuleEdge) {
		Edge keyEdge = getKeyEdge(editRuleEdge);
		Set<Link> occurences = edgeOccurencesB.get(keyEdge);
		return occurences == null ? Collections.emptySet() : Collections.unmodifiableSet(occurences);
	}

	@Override
	public Set<Node> getMatchedNodesA() {
		return Collections.unmodifiableSet(nodeOccurencesA.keySet());
	}

	@Override
	public Set<Node> getMatchedNodesB() {
		return Collections.unmodifiableSet(nodeOccurencesB.keySet());
	}
	
	@Override
	public Set<Edge> getMatchedEdgesA(){
		return Collections.unmodifiableSet(edgeOccurencesA.keySet());
	}

	@Override
	public Set<Edge> getMatchedEdgesB(){
		return Collections.unmodifiableSet(edgeOccurencesB.keySet());
	}
	
	protected String deriveEdgeOccurrences() {
		StringBuilder info = new StringBuilder();

		// via trace A
		for (Edge edge : getAllLHSEdges()) {
			Set<Link> edgeOccurrences = new HashSet<>();

			// 1. Kreuzprodukt aus getOccurenceA(src) und getOccurenceA(tgt)
			// bilden
			Node src = getKeyNode(edge.getSource());
			Node tgt = getKeyNode(edge.getTarget());
			Set<Pair<EObject,EObject>> crossProduct =
				MatchingHelper.getCartesianProduct(getOccurenceA(src), getOccurenceA(tgt));

			// 2. Checken, welche References (= edge occurrence) tatsächlich
			// existieren
			for (Pair<EObject,EObject> tuple : crossProduct) {
				createLink(edgeOccurrences, tuple.getFirst(), tuple.getSecond(), edge.getType());
			}

			if (!edgeOccurrences.isEmpty()) {
				info.append("\n edge trace A: " + edge + " (" + edge.getType().getName() + ") ==> ");
				info.append(edgeOccurrences);

				edgeOccurencesA.put(edge, edgeOccurrences);
			}
		}

		// via trace B
		for (Edge edge : getAllRHSEdges()) {
			Set<Link> edgeOccurrences = new HashSet<Link>();

			// 1. Kreuzprodukt aus getOccurenceA(src) und getOccurenceA(tgt)
			// bilden
			Node src = getKeyNode(edge.getSource());
			Node tgt = getKeyNode(edge.getTarget());
			Set<Pair<EObject,EObject>> crossProduct =
				MatchingHelper.getCartesianProduct(getOccurenceB(src), getOccurenceB(tgt));

			// 2. Checken, welche References (= edge occurrence) tatsächlich
			// existieren
			for (Pair<EObject,EObject> tuple : crossProduct) {
				createLink(edgeOccurrences, tuple.getFirst(), tuple.getSecond(), edge.getType());
			}

			Edge keyEdge = getKeyEdge(edge);
			if (!edgeOccurrences.isEmpty()) {
				info.append("\n edge trace B: " + keyEdge + " (" + keyEdge.getType().getName() + ") ==> ");
				info.append(edgeOccurrences);

				edgeOccurencesB.put(keyEdge, edgeOccurrences);
			}
		}

		return info.toString();
	}

	protected void createLink(Set<Link> edgeOccurrences, EObject source, EObject target, EReference reference) {
		if (EMFModelAccess.getNodeNeighbors(source, reference).contains(target)) {
			// edge occurrence found
			edgeOccurrences.add(new Link(source, target, reference));
		}
	}

	protected void setEditRule(EditRule editRule) {
		Assert.isTrue(this.editRule == null || this.editRule == editRule, "EditRule should always be the same");
		this.editRule = editRule;
	}

	/**
	 * Derive all EditRule-Edges from the nodeOccurrencesA. All edges are
	 * LHS-edges of the corresponding EditRule.
	 * 
	 * @return The set of LHS EditRule-edges
	 */
	protected Set<Edge> getAllLHSEdges() {
		Set<Edge> res = new HashSet<>();
		for (Node n : nodeOccurencesA.keySet()) {
			res.addAll(n.getOutgoing());
		}
		return res;
	}

	/**
	 * Derive all EditRule-Edges from the nodeOccurrencesB. All edges are
	 * RHS-edges of the corresponding EditRule.
	 * 
	 * @return The set of RHS EditRule-edges
	 */
	protected Set<Edge> getAllRHSEdges() {
		Set<Edge> res = new HashSet<>();
		for (Node n : nodeOccurencesB.keySet()) {
			// Be sure it's a RHS node (because of our key convention)
			if (HenshinRuleAnalysisUtilEx.isLHSNode(n)) {
				n = HenshinRuleAnalysisUtilEx.findCorrespondingNodeInRHS(n);
			}
			res.addAll(n.getOutgoing());
		}

		return res;
	}

	/**
	 * Notwendig aufgrund unserer Konvention zur Abspeicherung von Knoten
	 * Mappings (s. Doku zu den Instanzvariablen der Klasse).
	 * 
	 * Die Auflösung der Key-Nodes erfolgt in zwei Schritten:
	 * 
	 * (1) Für Knoten von Multirules muss geprüft werden, ob diese auch in der
	 * Kernel-Rule enthalten sind. Unsere Konvention ist, dass wir für gemappte
	 * Knoten in Multirules stets den Kernel-Node als key verwenden, nicht den
	 * Multi-Node. Für aus der Kernel-regel gemappte Multi-Nodes wird also der
	 * korrespondierende Mapping-Partner (origin) der Kernel-Regel im weiteren
	 * Verlauf als Key Node betrachtet. Ansonsten wird immer der als Parameter
	 * übergebene node weiter verwendet. Wenn der Knoten gar kein Multi-Knoten
	 * ist, so wird er natürlich ebenso einfach weiter verwendet.
	 * 
	 * (2) Für alle preserved und deleted nodes verwenden wir immer den LHS node
	 * als key: Für RHS nodes von preserved nodes wird daher der
	 * korrespondierende LHS node zurück geliefert. Ansonsten wird immer der als
	 * Parameter übergebene node wieder zurück geliefert.
	 * 
	 * Bemerkung: Die Auflösung des key nodes ist nur für occurrenceB-queries
	 * notwendig.
	 * 
	 * @param editRuleNode
	 * @return the key node which can be used for occurence-query
	 */
	protected Node getKeyNode(Node editRuleNode) {
		Node keyNode = editRuleNode;

		// Schritt (1):
		Rule henshinRule = editRuleNode.getGraph().getRule();
		if (henshinRule.isMultiRule()) {
			for (Mapping mapping : henshinRule.getMultiMappings()) {
				if (mapping.getImage() == editRuleNode) {
					keyNode = mapping.getOrigin();
				}
			}
		}

		// Schritt (2):
		if (HenshinRuleAnalysisUtilEx.isPreservedNode(keyNode)
				&& HenshinRuleAnalysisUtilEx.isRHSNode(keyNode)) {
			return HenshinRuleAnalysisUtilEx.findCorrespondingNodeInLHS(keyNode);
		}
		return keyNode;
	}

	/**
	 * Analog zu {@link this#getKeyNode(Node)} für edges.
	 * 
	 * @param editRuleEdge
	 * @return the key edge which can be used for occurence-query
	 */
	protected Edge getKeyEdge(Edge editRuleEdge) {
		if (HenshinRuleAnalysisUtilEx.isPreservedEdge(editRuleEdge)
				&& HenshinRuleAnalysisUtilEx.isRHSEdge(editRuleEdge)) {
			return HenshinRuleAnalysisUtilEx.findCorrespondingEdgeInLHS(editRuleEdge);
		}
		return editRuleEdge;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("EditRuleMatch for " + editRule.getExecuteModule().getName());
		for (Node erNode : nodeOccurencesA.keySet()) {
			res.append("\n node trace A: " + erNode + " ==> " + nodeOccurencesA.get(erNode));
		}
		for (Node erNode : nodeOccurencesB.keySet()) {
			res.append("\n node trace B: " + erNode + " ==> " + nodeOccurencesB.get(erNode));
		}
		for (Edge erEdge : edgeOccurencesA.keySet()) {
			res.append("\n edge trace A: " + erEdge + " (" + erEdge.getType().getName() + ") ==> ");
			res.append(edgeOccurencesA.get(erEdge));
		}
		for (Edge erEdge : edgeOccurencesB.keySet()) {
			res.append("\n edge trace B: " + erEdge + " (" + erEdge.getType().getName() + ") ==> ");
			res.append(edgeOccurencesB.get(erEdge));
		}
		return res.toString();
	}

}
