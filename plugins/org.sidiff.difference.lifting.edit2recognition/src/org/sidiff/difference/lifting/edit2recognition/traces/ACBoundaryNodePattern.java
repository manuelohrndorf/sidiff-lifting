package org.sidiff.difference.lifting.edit2recognition.traces;

import static org.sidiff.common.henshin.HenshinConditionAnalysis.isNestedConditionNode;

import org.eclipse.emf.henshin.model.Node;

public class ACBoundaryNodePattern {

	/**
	 * The Recognition-Rule context Node.
	 */
	public Node acBoundaryNode;
	
	/**
	 * The Edit-Rule nested condition context node.
	 */
	public Node acBoundaryTrace;

	/**
	 * @param acContextNode
	 * @param acContextTrace
	 */
	public ACBoundaryNodePattern(Node acContextNode, Node acContextTrace) {
		super();
		
		assert isNestedConditionNode(acContextNode) : "Not a nested condition node!";
		assert isNestedConditionNode(acContextTrace) : "Not a nested condition node!";
		
		this.acBoundaryNode = acContextNode;
		this.acBoundaryTrace = acContextTrace;
	}
}
