package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;
import org.sidiff.common.henshin.EdgePair;
import org.sidiff.common.henshin.NodePair;

public class RemoveReferencePattern {
	
	private NodePair removeReference;
	private NodePair typeNode;
	private EdgePair edge_A;
	
	private Edge trace;

	public RemoveReferencePattern(NodePair removeReference, NodePair typeNode, EdgePair edge_A, Edge trace) {
		super();
		this.removeReference = removeReference;
		this.typeNode = typeNode;
		this.edge_A = edge_A;
		this.trace = trace;
	}

	public NodePair getRemoveReference() {
		return removeReference;
	}

	public void setRemoveReference(NodePair removeReference) {
		this.removeReference = removeReference;
	}

	public NodePair getTypeNode() {
		return typeNode;
	}

	public void setTypeNode(NodePair typeNode) {
		this.typeNode = typeNode;
	}

	public EdgePair getEdge_A() {
		return edge_A;
	}

	public void setEdge_A(EdgePair edge_A) {
		this.edge_A = edge_A;
	}

	public Edge getTrace() {
		return trace;
	}

	public void setTrace(Edge trace) {
		this.trace = trace;
	}
}
