package org.sidiff.common.henshin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;

/**
 * Einige Verwaltungsinformationen zu PACs und NACs.
 * 
 * @author kehrer
 */
public class ApplicationCondition {

	private NestedCondition nestedCondition;
	private boolean inverted;
	
	private Set<Node> lhsContextNodes;
	private Set<Node> acContextNodes;
	private Set<Node> nonBoundaryNodes;
	
	private Map<Node, Node> lhsContextNode2acContextNode;
	private Map<Node, Node> acContextNode2LhsContextNode;
	
	public ApplicationCondition(NestedCondition nestedCondition, boolean inverted) {
		this.nestedCondition = nestedCondition;
		this.inverted = inverted;

		init();
	}

	public Set<Node> getLhsContextNodes() {
		return lhsContextNodes;
	}

	public Set<Node> getAcContextNodes() {
		return acContextNodes;
	}

	public Set<Node> getNonBoundaryNodes() {
		return nonBoundaryNodes;
	}
	
	public Node getAcContextNode(Node lhsContextNode){
		return lhsContextNode2acContextNode.get(lhsContextNode);
	}
	
	public Node getLhsContextNode(Node acContextNode){
		return acContextNode2LhsContextNode.get(acContextNode);
	}
	
	public NestedCondition getNestedCondition() {
		return nestedCondition;
	}
	
	public boolean isInverted(){
		return inverted;
	}
	
	private void init() {
		lhsContextNodes = new HashSet<Node>();
		acContextNodes = new HashSet<Node>();
		nonBoundaryNodes = new HashSet<Node>();
		acContextNode2LhsContextNode = new HashMap<Node, Node>();
		lhsContextNode2acContextNode = new HashMap<Node, Node>();
		
		inverted = nestedCondition.eContainer() instanceof Not;
		
		for (Node node : nestedCondition.getConclusion().getNodes()) {
			boolean boundary = false;
			for (Mapping mapping : nestedCondition.getMappings()) {
				if (mapping.getImage() == node) {
					// it's a boundary node
					lhsContextNodes.add(mapping.getOrigin());
					acContextNodes.add(mapping.getImage());
					acContextNode2LhsContextNode.put(mapping.getImage(), mapping.getOrigin());
					lhsContextNode2acContextNode.put(mapping.getOrigin(), mapping.getImage());
					boundary = true;
					break;
				} 
			}
			
			if (!boundary){
				nonBoundaryNodes.add(node);
			}
		}
	}

}
