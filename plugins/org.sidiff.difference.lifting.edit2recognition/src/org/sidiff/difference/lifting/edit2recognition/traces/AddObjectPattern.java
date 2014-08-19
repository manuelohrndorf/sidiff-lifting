package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.NodePair;

public class AddObjectPattern {
	
	private NodePair node_b;
	private NodePair addObject;
	
	private Node trace;
	
	public AddObjectPattern(NodePair node_b, NodePair addObject, Node trace) {
		super();
		this.node_b = node_b;
		this.addObject = addObject;
		this.trace = trace;
	}

	public NodePair getNode_b() {
		return node_b;
	}

	public void setNode_b(NodePair node_b) {
		this.node_b = node_b;
	}

	public NodePair getAddObject() {
		return addObject;
	}

	public void setAddObject(NodePair addObject) {
		this.addObject = addObject;
	}

	public Node getTrace() {
		return trace;
	}

	public void setTrace(Node trace) {
		this.trace = trace;
	}
	
	
}
