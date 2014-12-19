package org.sidiff.difference.lifting.edit2recognition.traces;

import static org.sidiff.common.henshin.HenshinConditionAnalysis.isNestedConditionNode;

import org.eclipse.emf.henshin.model.Node;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link ACBoundaryNodePattern#ACBoundaryNodePattern(Node, Node)}
 * 
 * @author Manuel Ohrndorf
 */
public class ACBoundaryNodePattern {

	/**
	 * The nested Recognition-Rule AC boundary node.
	 */
	private Node acBoundaryNode;
	
	/**
	 * The nested Edit-Rule AC boundary node.
	 */
	private Node acBoundaryTrace;

	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for an application condition (AC) boundary
	 * node. A boundary node is a node of a nested (application condition) graph that is mapped to a
	 * parent (LHS) graph node, i.e a boundary node is the glue point between the AC and the LHS.
	 * 
	 * @param acBoundaryNode
	 *            The nested Recognition-Rule boundary node.
	 * @param acBoundaryTrace
	 *            The nested Edit-Rule AC boundary node.
	 */
	public ACBoundaryNodePattern(Node acBoundaryNode, Node acBoundaryTrace) {
		super();
		
		assert isNestedConditionNode(acBoundaryNode) : "Not a nested condition node!";
		assert isNestedConditionNode(acBoundaryTrace) : "Not a nested condition node!";
		
		this.acBoundaryNode = acBoundaryNode;
		this.acBoundaryTrace = acBoundaryTrace;
	}

	/**
	 * @return The {@link ACBoundaryNodePattern#acBoundaryNode}
	 */
	public Node getACBoundaryNode() {
		return acBoundaryNode;
	}

	/**
	 * @return The {@link ACBoundaryNodePattern#acBoundaryTrace}
	 */
	public Node getACBoundaryTrace() {
		return acBoundaryTrace;
	}
}
