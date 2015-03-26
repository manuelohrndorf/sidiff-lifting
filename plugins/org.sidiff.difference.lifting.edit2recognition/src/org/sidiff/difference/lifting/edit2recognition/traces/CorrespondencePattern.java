
package org.sidiff.difference.lifting.edit2recognition.traces;

import org.sidiff.common.henshin.view.NodePair;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link CorrespondencePattern#CorrespondencePattern(NodePair, NodePair, NodePair, NodePair)}
 * 
 * @author Manuel Ohrndorf
 */
public class CorrespondencePattern {
	
	/**
	 * The Recognition-Rule model A node (representing the traced node).
	 */
	private NodePair nodeA;
	
	/**
	 * The Recognition-Rule model B node (representing the traced node).
	 */
	private NodePair nodeB;
	
	/**
	 * The Recognition-Rule correspondence node between model A and model B.
	 */
	private NodePair correspondence;
	
	/**
	 * The << preserve >> Edit-Rule node.
	 */
	private NodePair trace;
	
	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for the pattern that recognizes corresponding
	 * object in the model difference.
	 * 
	 * @param nodeA
	 *            The Recognition-Rule model A node (representing the traced node).
	 * @param nodeB
	 *            The Recognition-Rule model B node (representing the traced node).
	 * @param correspondence
	 *            The Recognition-Rule correspondence node between model A and model B.
	 * @param trace
	 *            The << preserve >> Edit-Rule node.
	 */
	public CorrespondencePattern(NodePair nodeA, NodePair nodeB, NodePair correspondence, NodePair trace) {
		super();
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.correspondence = correspondence;
		this.trace = trace;
	}
	
	/**
	 * @return The {@link CorrespondencePattern#nodeA}
	 */
	public NodePair getNodeA() {
		return nodeA;
	}
	
	/**
	 * @return The {@link CorrespondencePattern#nodeB}
	 */
	public NodePair getNodeB() {
		return nodeB;
	}
	
	/**
	 * @return The {@link CorrespondencePattern#correspondence}
	 */
	public NodePair getCorrespondence() {
		return correspondence;
	}
	
	/**
	 * @return The {@link CorrespondencePattern#trace}
	 */
	public NodePair getTrace() {
		return trace;
	}
}
