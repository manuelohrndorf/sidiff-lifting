package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.NodePair;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link AddObjectPattern#AddObjectPattern(NodePair, NodePair, Node)}
 * 
 * @author Manuel Ohrndorf
 */
public class AddObjectPattern {
	
	/**
	 * The Recognition-Rule Add-Object (low-level difference) node. 
	 */
	private NodePair addObject;
	
	/**
	 * The Recognition-Rule model B node (representing the traced node). 
	 */
	private NodePair nodeB;
	
	/**
	 * The << create >> Edit-Rule node.
	 */
	private Node trace;

	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for the pattern that recognizes an add object
	 * in the model difference.
	 * 
	 * @param addObject The Recognition-Rule Add-Object (low-level difference) node.
	 * 
	 * @param nodeB
	 *            The Recognition-Rule model B node (representing the traced node).
	 * @param trace
	 *            The << create >> Edit-Rule node.
	 */
	public AddObjectPattern(NodePair addObject, NodePair nodeB, Node trace) {
		super();
		this.addObject = addObject;
		this.nodeB = nodeB;
		this.trace = trace;
	}
	
	/**
	 * @return The {@link AddObjectPattern#addObject}
	 */
	public NodePair getAddObject() {
		return addObject;
	}
	
	/**
	 * @return The {@link AddObjectPattern#nodeB}
	 */
	public NodePair getNodeB() {
		return nodeB;
	}
	
	/**
	 * @return The {@link AddObjectPattern#trace}
	 */
	public Node getTrace() {
		return trace;
	}
}
