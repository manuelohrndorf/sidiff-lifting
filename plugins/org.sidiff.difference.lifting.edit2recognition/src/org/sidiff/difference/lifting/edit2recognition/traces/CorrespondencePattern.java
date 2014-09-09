package org.sidiff.difference.lifting.edit2recognition.traces;

import org.sidiff.common.henshin.NodePair;

public class CorrespondencePattern {

	public NodePair nodeA;
	public NodePair nodeB;
	public NodePair correspondence;
	
	public NodePair trace;
	
	/**
	 * @param node_a
	 * @param node_b
	 * @param correspondence
	 * @param trace
	 */
	public CorrespondencePattern(NodePair node_a, NodePair node_b, NodePair correspondence,
			NodePair trace) {
		super();
		this.nodeA = node_a;
		this.nodeB = node_b;
		this.correspondence = correspondence;
		this.trace = trace;
	}
}
