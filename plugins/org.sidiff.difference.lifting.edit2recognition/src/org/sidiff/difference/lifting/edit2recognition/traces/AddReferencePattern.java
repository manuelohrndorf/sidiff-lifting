package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;
import org.sidiff.common.henshin.EdgePair;
import org.sidiff.common.henshin.NodePair;

public class AddReferencePattern {

	private NodePair addReference;
	private NodePair typeNode;
	private EdgePair node_b;
	
	private Edge trace;

	public AddReferencePattern(NodePair addReference, NodePair typeNode, EdgePair node_b, Edge trace) {
		super();
		this.addReference = addReference;
		this.typeNode = typeNode;
		this.node_b = node_b;
		this.trace = trace;
	}

	public NodePair getAddReference() {
		return addReference;
	}

	public void setAddReference(NodePair addReference) {
		this.addReference = addReference;
	}

	public NodePair getTypeNode() {
		return typeNode;
	}

	public void setTypeNode(NodePair typeNode) {
		this.typeNode = typeNode;
	}

	public Edge getTrace() {
		return trace;
	}

	public void setTrace(Edge trace) {
		this.trace = trace;
	}

	public EdgePair getNode_b() {
		return node_b;
	}

	public void setNode_b(EdgePair node_b) {
		this.node_b = node_b;
	}
}
