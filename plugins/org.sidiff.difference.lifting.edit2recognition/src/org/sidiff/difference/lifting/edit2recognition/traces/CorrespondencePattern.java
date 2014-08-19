package org.sidiff.difference.lifting.edit2recognition.traces;

import org.sidiff.common.henshin.NodePair;

public class CorrespondencePattern {

	private NodePair node_a;
	private NodePair node_b;
	private NodePair correspondence;
	
	private NodePair trace;
	
	public CorrespondencePattern(NodePair node_a, NodePair node_b, NodePair correspondence,
			NodePair trace) {
		super();
		this.node_a = node_a;
		this.node_b = node_b;
		this.correspondence = correspondence;
		this.trace = trace;
	}

	public NodePair getNode_a() {
		return node_a;
	}

	public void setNode_a(NodePair node_a) {
		this.node_a = node_a;
	}

	public NodePair getNode_b() {
		return node_b;
	}

	public void setNode_b(NodePair node_b) {
		this.node_b = node_b;
	}

	public NodePair getCorrespondence() {
		return correspondence;
	}

	public void setCorrespondence(NodePair correspondence) {
		this.correspondence = correspondence;
	}

	public NodePair getTrace() {
		return trace;
	}

	public void setTrace(NodePair trace) {
		this.trace = trace;
	}
}
