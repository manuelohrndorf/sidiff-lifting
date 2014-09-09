package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;
import org.sidiff.common.henshin.EdgePair;
import org.sidiff.common.henshin.NodePair;

public class RemoveReferencePattern {
	
	public NodePair removeReference;
	public NodePair typeNode;
	public EdgePair edgeA;
	
	public Edge trace;

	/**
	 * @param removeReference
	 * @param typeNode
	 * @param edge_A
	 * @param trace
	 */
	public RemoveReferencePattern(NodePair removeReference, NodePair typeNode, EdgePair edge_A, Edge trace) {
		super();
		this.removeReference = removeReference;
		this.typeNode = typeNode;
		this.edgeA = edge_A;
		this.trace = trace;
	}
}
