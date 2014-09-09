package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;

public class ACObjectPattern {
	
	/**
	 * The Recognition-Rule AC node.
	 */
	public Node acNode;
	
	/**
	 * The Edit-Rule AC node.
	 */
	public Node acTrace;

	/**
	 * @param ac_node
	 * @param ac_trace
	 */
	public ACObjectPattern(Node ac_node, Node ac_trace) {
		super();
		this.acNode = ac_node;
		this.acTrace = ac_trace;
	}
}