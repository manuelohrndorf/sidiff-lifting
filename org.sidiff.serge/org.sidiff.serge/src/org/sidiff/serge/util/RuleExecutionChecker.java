package org.sidiff.serge.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.extensions.impl.EReferenceInfo;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;

/**
 * Primitive utility class that can check if a given rule is executable in
 * principal.
 * 
 * @author kehrer
 */
public class RuleExecutionChecker {

	private Rule rule;
	protected Map<Node, Map<EReference, Set<Edge>>> src2OutType2OutEdge;

	public RuleExecutionChecker(Rule rule) {
		super();
		this.rule = rule;
	}

	/**
	 * Primitive check if the rule to be checked is executable.
	 * 
	 * @return
	 */
	public boolean isExecutable() {
		buildSrc2OutIndex();
		for (Node src : src2OutType2OutEdge.keySet()) {
			Map<EReference, Set<Edge>> outType2Edge = src2OutType2OutEdge.get(src);
			for (EReference outType : outType2Edge.keySet()) {
				int sum_toBeMatched = 0;
				int sum_forbid = 0;

				for (Edge outEdge : outType2Edge.get(outType)) {
					if (HenshinRuleAnalysisUtilEx.isDeletionEdge(outEdge)
							|| HenshinRuleAnalysisUtilEx.isRequireEdge(outEdge)
							|| HenshinRuleAnalysisUtilEx.isPreservedEdge(outEdge)) {
						sum_toBeMatched++;
					}
					if (HenshinRuleAnalysisUtilEx.isForbiddenEdge(outEdge)) {
						sum_forbid++;
					}

					if (EReferenceInfo.isBounded(outType) && (sum_toBeMatched > outType.getUpperBound())) {
						return false;
					}
					if (sum_forbid > outType.getLowerBound()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private void buildSrc2OutIndex() {
		src2OutType2OutEdge = new HashMap<Node, Map<EReference, Set<Edge>>>();
		for (NodePair preservedNode : HenshinRuleAnalysisUtilEx.getPreservedNodes(rule)) {
			for (Edge edge : preservedNode.getLhsNode().getOutgoing()) {
				addEdge(preservedNode.getLhsNode(), edge);
			}
			for (Edge edge : preservedNode.getRhsNode().getOutgoing()) {
				addEdge(preservedNode.getLhsNode(), edge);
			}
		}
		for (Node deletionNode : HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes(rule)) {
			for (Edge edge : deletionNode.getOutgoing()) {
				addEdge(deletionNode, edge);
			}
		}
		for (Node creationNode : HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule)) {
			for (Edge edge : creationNode.getOutgoing()) {
				addEdge(creationNode, edge);
			}
		}
	}

	private void addEdge(Node src, Edge edge) {
		if (src2OutType2OutEdge.get(src) == null) {
			src2OutType2OutEdge.put(src, new HashMap<EReference, Set<Edge>>());
		}
		if (src2OutType2OutEdge.get(src).get(edge.getType()) == null) {
			src2OutType2OutEdge.get(src).put(edge.getType(), new HashSet<Edge>());
		}

		src2OutType2OutEdge.get(src).get(edge.getType()).add(edge);
	}
}
