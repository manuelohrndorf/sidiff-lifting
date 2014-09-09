package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.NodePair;

public class RemoveObjectPattern {

	public NodePair nodeA;
	public NodePair removeObject;
	
	public Node trace;
	
	/**
	 * @param node_a
	 * @param removeObject
	 * @param trace
	 */
	public RemoveObjectPattern(NodePair node_a, NodePair removeObject, Node trace) {
		super();
		this.nodeA = node_a;
		this.removeObject = removeObject;
		this.trace = trace;
	}
}
