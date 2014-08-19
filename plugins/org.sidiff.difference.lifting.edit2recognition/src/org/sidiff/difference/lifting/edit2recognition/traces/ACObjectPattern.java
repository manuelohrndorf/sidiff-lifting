package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;

public class ACObjectPattern {
	
	private Node node_b;
	
	private Node trace;

	public ACObjectPattern(Node node_b, Node trace) {
		super();
		this.node_b = node_b;
		this.trace = trace;
	}

	public Node getNode_b() {
		return node_b;
	}

	public void setNode_b(Node node_b) {
		this.node_b = node_b;
	}

	public Node getTrace() {
		return trace;
	}

	public void setTrace(Node trace) {
		this.trace = trace;
	}
}

