package org.sidiff.serge.generators.conditions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;

public abstract class AbstractBoundCheckGenerator extends AbstractConditionGenerator {

	protected Map<NodePair, Set<EReference>> src2Out;
	
	private int ubCounter;
	private int lbCounter;
	
	public AbstractBoundCheckGenerator(Rule editRule) {
		super(editRule);
		buildSrc2OutIndex();
		ubCounter = 0;
		lbCounter = 0;
	}

	private void buildSrc2OutIndex() {
		src2Out = new HashMap<NodePair, Set<EReference>>();
		for (NodePair preservedNode : HenshinRuleAnalysisUtilEx.getPreservedNodes(editRule)) {
			for (Edge edge : preservedNode.getLhsNode().getOutgoing()) {
				addEdgeType(preservedNode, edge.getType());
			}
			for (Edge edge : preservedNode.getRhsNode().getOutgoing()) {
				addEdgeType(preservedNode, edge.getType());
			}
		}
	}

	private void addEdgeType(NodePair key, EReference edgeType) {
		if (src2Out.get(key) == null) {
			src2Out.put(key, new HashSet<EReference>());
		}

		src2Out.get(key).add(edgeType);
	}

	/**
	 * "Balance" outgoing references of type edgeType which are created/deleted
	 * for node preservedNode. Positive integer means that more references are
	 * created than deleted, 0 means that an equal number of references are
	 * created and deleted, and a negative integer means that more references
	 * are deleted.
	 * 
	 * @param preservedNode
	 * @param edgeType
	 * @return
	 */
	protected int getBalance(NodePair preservedNode, EReference edgeType) {
		int created = 0;
		int deleted = 0;

		for (Edge edge : preservedNode.getRhsNode().getOutgoing()) {
			if (edge.getType().equals(edgeType) && HenshinRuleAnalysisUtilEx.isCreationEdge(edge)) {
				created++;
			}
		}
		for (Edge edge : preservedNode.getLhsNode().getOutgoing()) {
			if (edge.getType().equals(edgeType) && HenshinRuleAnalysisUtilEx.isDeletionEdge(edge)) {
				deleted++;
			}
		}

		return created - deleted;
	}

	protected int getPreservedCount(NodePair preservedNode, EReference edgeType) {
		int res = 0;

		for (Edge edge : preservedNode.getLhsNode().getOutgoing()) {
			if (edge.getType().equals(edgeType) && HenshinRuleAnalysisUtilEx.isPreservedEdge(edge)) {
				res++;
			}
		}

		return res;
	}

	protected Formula createBoundCheck(int offset, Node lhsBoundaryNode, EReference edgeType, boolean invert) {
		NestedCondition cond = HenshinFactory.eINSTANCE.createNestedCondition();
		Graph graph = HenshinFactory.eINSTANCE.createGraph();
		if (invert){
			// ub check
			graph.setName("ub" + ubCounter);
			ubCounter++;
		} else {
			// lb check
			graph.setName("lb" + lbCounter);
			lbCounter++;
		}
		cond.setConclusion(graph);

		// Create AC graph
		Node acSrc = HenshinFactory.eINSTANCE.createNode(graph, lhsBoundaryNode.getType(), "");
		for (int i = 0; i < offset; i++) {
			Node acTgt = HenshinFactory.eINSTANCE.createNode(graph, edgeType.getEReferenceType(), "");
			HenshinFactory.eINSTANCE.createEdge(acSrc, acTgt, edgeType);
		}

		// Embed src node into editRule LHS
		Mapping mapping = HenshinFactory.eINSTANCE.createMapping(lhsBoundaryNode, acSrc);
		cond.getMappings().add(mapping);

		// Return result
		if (invert) {
			Not res = HenshinFactory.eINSTANCE.createNot();
			res.setChild(cond);
			return res;
		} else {
			return cond;
		}
	}
}
