package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.NodePair;

public class RemoveObjectPattern {

	private NodePair node_a;
	private NodePair removeObject;
	
	private Node trace;
	
	public RemoveObjectPattern(NodePair node_a, NodePair removeObject, Node trace) {
		super();
		this.node_a = node_a;
		this.removeObject = removeObject;
		this.trace = trace;
	}

	public NodePair getNode_a() {
		return node_a;
	}

	public void setNode_a(NodePair node_a) {
		this.node_a = node_a;
	}

	public NodePair getRemoveObject() {
		return removeObject;
	}

	public void setRemoveObject(NodePair removeObject) {
		this.removeObject = removeObject;
	}

	public Node getTrace() {
		return trace;
	}

	public void setTrace(Node trace) {
		this.trace = trace;
	}
}
