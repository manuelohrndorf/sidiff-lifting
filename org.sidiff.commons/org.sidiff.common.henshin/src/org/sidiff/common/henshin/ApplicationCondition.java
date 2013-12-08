package org.sidiff.common.henshin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Einige Verwaltungsinformationen zu PACs und NACs.
 * 
 * @author kehrer
 */
public class ApplicationCondition {

	private NestedCondition nestedCondition;
	
	private Set<Node> lhsContextNodes;
	private Set<Node> nacContextNodes;
	private Set<Node> nonBoundaryNodes;
	
	private boolean inverted;
	
	private Map<Node, Node> lhsContextNode2nacContextNode;
	private Map<Node, Node> nacContextNode2LhsContextNode;
	
	public ApplicationCondition(NestedCondition nestedCondition) {
		this.nestedCondition = nestedCondition;

		init();
	}

	public Set<Node> getLhsContextNodes() {
		return lhsContextNodes;
	}

	public Set<Node> getNacContextNodes() {
		return nacContextNodes;
	}

	public Set<Node> getNonBoundaryNodes() {
		return nonBoundaryNodes;
	}
	
	public Node getNacContextNode(Node lhsContextNode){
		return lhsContextNode2nacContextNode.get(lhsContextNode);
	}
	
	public Node getLhsContextNode(Node nacContextNode){
		return nacContextNode2LhsContextNode.get(nacContextNode);
	}
	
	public NestedCondition getNestedCondition() {
		return nestedCondition;
	}
	
	public boolean isInverted(){
		return inverted;
	}
	
	private void init() {
		lhsContextNodes = new HashSet<Node>();
		nacContextNodes = new HashSet<Node>();
		nonBoundaryNodes = new HashSet<Node>();
		nacContextNode2LhsContextNode = new HashMap<Node, Node>();
		lhsContextNode2nacContextNode = new HashMap<Node, Node>();
		
		inverted = nestedCondition.eContainer() instanceof Not;
		
		for (Node node : nestedCondition.getConclusion().getNodes()) {
			boolean boundary = false;
			for (Mapping mapping : nestedCondition.getMappings()) {
				if (mapping.getImage() == node) {
					// it's a boundary node
					lhsContextNodes.add(mapping.getOrigin());
					nacContextNodes.add(mapping.getImage());
					nacContextNode2LhsContextNode.put(mapping.getImage(), mapping.getOrigin());
					lhsContextNode2nacContextNode.put(mapping.getOrigin(), mapping.getImage());
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
