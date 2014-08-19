package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Link;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.difference.rulebase.EditRule;

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
public abstract class BasicEditRuleMatch {

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
	protected Map<Node, Set<EObject>> nodeOccurencesA = new HashMap<Node, Set<EObject>>();

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
	protected Map<Node, Set<EObject>> nodeOccurencesB = new HashMap<Node, Set<EObject>>();

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
	protected Map<Edge, Set<Link>> edgeOccurencesA = new HashMap<Edge, Set<Link>>();

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
	protected Map<Edge, Set<Link>> edgeOccurencesB = new HashMap<Edge, Set<Link>>();

	/**
	 * The {@link EditRule} for which this EditRuleMatch is being created.
	 */
	private EditRule editRule;

	public EditRule getEditRule() {
		return editRule;
	}

	public Set<EObject> getOccurenceA(Node editRuleNode) {
		Node keyNode = getKeyNode(editRuleNode);
		if (nodeOccurencesA.get(keyNode) == null) {
			return new HashSet<EObject>();
		} else {
			return nodeOccurencesA.get(keyNode);
		}
	}

	public Set<EObject> getOccurenceB(Node editRuleNode) {
		Node keyNode = getKeyNode(editRuleNode);
		if (nodeOccurencesB.get(keyNode) == null) {
			return new HashSet<EObject>();
		} else {
			return nodeOccurencesB.get(keyNode);
		}
	}

	public Set<Link> getOccurenceA(Edge editRuleEdge) {
		Edge keyEdge = getKeyEdge(editRuleEdge);
		if (edgeOccurencesA.get(keyEdge) == null) {
			return new HashSet<Link>();
		} else {
			return edgeOccurencesA.get(keyEdge);
		}
	}

	public Set<Link> getOccurenceB(Edge editRuleEdge) {
		Edge keyEdge = getKeyEdge(editRuleEdge);
		if (edgeOccurencesB.get(keyEdge) == null) {
			return new HashSet<Link>();
		} else {
			return edgeOccurencesB.get(keyEdge);
		}
	}

	public Set<Node> getMatchedNodesA() {
		return nodeOccurencesA.keySet();
	}

	public Set<Node> getMatchedNodesB() {
		return nodeOccurencesB.keySet();
	}

	protected String deriveEdgeOccurrences() {
		StringBuffer info = new StringBuffer();

		// via trace A
		for (Edge edge : getAllLHSEdges()) {
			Set<Link> edgeOccurrences = new HashSet<Link>();

			// 1. Kreuzprodukt aus getOccurenceA(src) und getOccurenceA(tgt)
			// bilden
			Node src = getKeyNode(edge.getSource());
			Node tgt = getKeyNode(edge.getTarget());
			Set<EObject[]> crossProduct = MatchingHelper.getCartesianProduct(getOccurenceA(src), getOccurenceA(tgt));

			// 2. Checken, welche References (= edge occurrence) tatsächlich
			// existieren
			for (EObject[] tuple : crossProduct) {
				if (EMFModelAccess.getNodeNeighbors(tuple[0], edge.getType()).contains(tuple[1])) {
					// edge occurrence found
					edgeOccurrences.add(new Link(tuple[0], tuple[1], edge.getType()));
				}
			}

			if (!edgeOccurrences.isEmpty()) {
				info.append(" edge trace A: " + edge + " (" + edge.getType().getName() + ") ==> ");
				info.append(edgeOccurrences + "\n");

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
			Set<EObject[]> crossProduct = MatchingHelper.getCartesianProduct(getOccurenceB(src), getOccurenceB(tgt));

			// 2. Checken, welche References (= edge occurrence) tatsächlich
			// existieren
			for (EObject[] tuple : crossProduct) {
				if (EMFModelAccess.getNodeNeighbors(tuple[0], edge.getType()).contains(tuple[1])) {
					// edge occurrence found
					edgeOccurrences.add(new Link(tuple[0], tuple[1], edge.getType()));
				}
			}

			Edge keyEdge = getKeyEdge(edge);
			if (!edgeOccurrences.isEmpty()) {
				info.append(" edge trace B: " + keyEdge + " (" + keyEdge.getType().getName() + ") ==> ");
				info.append(edgeOccurrences + "\n");

				edgeOccurencesB.put(keyEdge, edgeOccurrences);
			}
		}

		return info.toString();
	}

	protected void setEditRule(EditRule r) {
		if (editRule == null) {
			editRule = r;
		}
	}

	/**
	 * Derive all EditRule-Edges from the nodeOccurrencesA. All edges are
	 * LHS-edges of the corresponding EditRule.
	 * 
	 * @return The set of LHS EditRule-edges
	 */
	protected Set<Edge> getAllLHSEdges() {
		Set<Edge> res = new HashSet<Edge>();
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
		Set<Edge> res = new HashSet<Edge>();
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
		if (HenshinRuleAnalysisUtilEx.isPreservedNode(keyNode) && HenshinRuleAnalysisUtilEx.isRHSNode(keyNode)) {
			return HenshinRuleAnalysisUtilEx.findCorrespondingNodeInLHS(keyNode);
		} else {
			return keyNode;
		}
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
		} else {
			return editRuleEdge;
		}
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append("EditRuleMatch for " + editRule.getExecuteModule().getName() + "\n");
		for (Node erNode : nodeOccurencesA.keySet()) {
			res.append(" node trace A: " + erNode + " ==> " + nodeOccurencesA.get(erNode) + "\n");
		}
		for (Node erNode : nodeOccurencesB.keySet()) {
			res.append(" node trace B: " + erNode + " ==> " + nodeOccurencesB.get(erNode) + "\n");
		}
		for (Edge erEdge : edgeOccurencesA.keySet()) {
			res.append(" edge trace A: " + erEdge + " (" + erEdge.getType().getName() + ") ==> ");
			res.append(edgeOccurencesA.get(erEdge) + "\n");
		}
		for (Edge erEdge : edgeOccurencesB.keySet()) {
			res.append(" edge trace B: " + erEdge + " (" + erEdge.getType().getName() + ") ==> ");
			res.append(edgeOccurencesB.get(erEdge) + "\n");
		}

		return res.toString();
	}

}
