package org.sidiff.difference.lifting.edit2recognition.traces;

import static org.sidiff.common.henshin.HenshinConditionAnalysis.isNestedConditionNode;

import org.eclipse.emf.henshin.model.Node;

public class ACContextNodePattern {

	/**
	 * The Recognition-Rule context Node.
	 */
	public Node acContextNode;
	
	/**
	 * The Edit-Rule nested condition context node.
	 */
	public Node acContextTrace;

	/**
	 * @param acContextNode
	 * @param acContextTrace
	 */
	public ACContextNodePattern(Node acContextNode, Node acContextTrace) {
		super();
		
		assert isNestedConditionNode(acContextNode) : "Not a nested condition node!";
		assert isNestedConditionNode(acContextTrace) : "Not a nested condition node!";
		
		this.acContextNode = acContextNode;
		this.acContextTrace = acContextTrace;
	}
}
