package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;
import org.sidiff.common.henshin.EdgePair;
import org.sidiff.common.henshin.NodePair;

public class AddReferencePattern {

	public NodePair addReference;
	public NodePair typeNode;
	public EdgePair nodeB;
	
	public Edge trace;

	/**
	 * @param addReference
	 * @param typeNode
	 * @param node_b
	 * @param trace
	 */
	public AddReferencePattern(NodePair addReference, NodePair typeNode, EdgePair node_b, Edge trace) {
		super();
		this.addReference = addReference;
		this.typeNode = typeNode;
		this.nodeB = node_b;
		this.trace = trace;
	}
}
