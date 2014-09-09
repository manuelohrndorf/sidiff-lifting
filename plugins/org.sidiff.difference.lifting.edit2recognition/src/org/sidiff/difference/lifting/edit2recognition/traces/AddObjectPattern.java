package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.NodePair;

public class AddObjectPattern {
	
	public NodePair nodeB;
	public NodePair addObject;
	
	public Node trace;
	
	/**
	 * @param node_b
	 * @param addObject
	 * @param trace
	 */
	public AddObjectPattern(NodePair node_b, NodePair addObject, Node trace) {
		super();
		this.nodeB = node_b;
		this.addObject = addObject;
		this.trace = trace;
	}
}
