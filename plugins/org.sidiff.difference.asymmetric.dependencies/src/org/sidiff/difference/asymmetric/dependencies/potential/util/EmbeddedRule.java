package org.sidiff.difference.asymmetric.dependencies.potential.util;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isAmalgamationUnit;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.rulebase.EditRule;

public class EmbeddedRule {
	EditRule editRule;
	Rule kernelRule;

	Set<Node> embeddedNodes = new HashSet<Node>();
	Set<Edge> embeddedEdges = new HashSet<Edge>();
	Set<Attribute> embeddedAttributes = new HashSet<Attribute>();

	public EmbeddedRule(EditRule editRule) {
		this.editRule = editRule;

		if (isAmalgamtionUnit(editRule)) {
			kernelRule = (Rule) editRule.getExecuteMainUnit().getSubUnits(false).get(0);
			// (1) find embedded nodes first!
			findEmbeddedNodes();
			// (2) embedded nodes needed!
			findEmbeddedEdges();
			findEmbeddedAttributes();
		}
	}
	
	public static boolean isAmalgamtionUnit(EditRule editRule) {
		if (isAmalgamationUnit(editRule.getExecuteMainUnit())) {
			return true;
		} else {
			return false;
		}
	}

	private void findEmbeddedNodes() {
		for(Rule multiRule : kernelRule.getMultiRules()) {
			for (Mapping mapping : multiRule.getMultiMappings()) {
				embeddedNodes.add(mapping.getImage());
			}
		}
	}
	
	private void findEmbeddedEdges() {
		// An edge is embedded if source and target nodes are embedded.
		for(Rule multiRule : kernelRule.getMultiRules()) {
			for(Edge lhsEdge : multiRule.getLhs().getEdges()) {
				if(embeddedNodes.contains(lhsEdge.getSource()) && embeddedNodes.contains(lhsEdge.getTarget())) {
					embeddedEdges.add(lhsEdge);
				}
			}
			
			for(Edge rhsEdge : multiRule.getRhs().getEdges()) {
				if(embeddedNodes.contains(rhsEdge.getSource()) && embeddedNodes.contains(rhsEdge.getTarget())) {
					embeddedEdges.add(rhsEdge);
				}
			}
		}
	}
	
	private void findEmbeddedAttributes() {
		// An attribute is embedded if its corresponding node is embedded
		for(Node embeddedNode : embeddedNodes) {
			embeddedAttributes.addAll(embeddedNode.getAttributes());
		}
	}

	public boolean isEmpty() {
		return embeddedNodes.isEmpty() && embeddedEdges.isEmpty()
				&& embeddedAttributes.isEmpty();
	}
	
	public Rule getKernelRule() {
		return kernelRule;
	}

	public Set<Node> getEmbeddedNodes() {
		return embeddedNodes;
	}

	public Set<Edge> getEmbeddedEdges() {
		return embeddedEdges;
	}

	public Set<Attribute> getEmbeddedAttributes() {
		return embeddedAttributes;
	}
}
