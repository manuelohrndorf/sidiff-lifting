package org.sidiff.difference.lifting.edit2recognition.traces;

import static org.sidiff.common.henshin.HenshinConditionAnalysis.isNestedConditionNode;

import org.eclipse.emf.henshin.model.Node;

public class ACContextNodePattern {

	/**
	 * The Recognition-Rule context Node.
	 */
	private Node acContextNode;
	
	/**
	 * The Edit-Rule nested condition context node.
	 */
	private Node acContextTrace;

	public ACContextNodePattern(Node acContextNode, Node acContextTrace) {
		super();
		
		assert isNestedConditionNode(acContextNode) : "Not a nested condition node!";
		assert isNestedConditionNode(acContextTrace) : "Not a nested condition node!";
		
		this.acContextNode = acContextNode;
		this.acContextTrace = acContextTrace;
	}

	public Node getAcContextNode() {
		return acContextNode;
	}

	public void setAcContextNode(Node acContextNode) {
		this.acContextNode = acContextNode;
	}

	public Node getAcContextTrace() {
		return acContextTrace;
	}

	public void setAcContextTrace(Node acContextTrace) {
		this.acContextTrace = acContextTrace;
	}
}
