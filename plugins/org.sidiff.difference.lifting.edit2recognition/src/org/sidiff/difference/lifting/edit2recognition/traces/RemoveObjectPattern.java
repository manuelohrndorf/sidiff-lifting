
package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.view.NodePair;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link RemoveObjectPattern#RemoveObjectPattern(NodePair, NodePair, Node)}
 * 
 * @author Manuel Ohrndorf
 */
public class RemoveObjectPattern {
	
	/**
	 * The Recognition-Rule Remove-Object (low-level difference) node.
	 */
	private NodePair removeObject;
	
	/**
	 * The Recognition-Rule model A node (representing the traced node).
	 */
	private NodePair nodeA;
	
	/**
	 * The << delete >> Edit-Rule node.
	 */
	private Node trace;
	
	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for the pattern that recognizes a remove
	 * object in the model difference.
	 * 
	 * @param removeObject
	 *            The Recognition-Rule Remove-Object (low-level difference) node.
	 * @param nodeA
	 *            The Recognition-Rule model A node (representing the traced node).
	 * @param trace
	 *            The << delete >> Edit-Rule node.
	 */
	public RemoveObjectPattern(NodePair removeObject, NodePair nodeA, Node trace) {
		super();
		this.removeObject = removeObject;
		this.nodeA = nodeA;
		this.trace = trace;
	}
	
	/**
	 * @return The {@link RemoveObjectPattern#removeObject}
	 */
	public NodePair getRemoveObject() {
		return removeObject;
	}
	
	/**
	 * @return The {@link RemoveObjectPattern#nodeA}
	 */
	public NodePair getNodeA() {
		return nodeA;
	}
	
	/**
	 * @return The {@link RemoveObjectPattern#trace}
	 */
	public Node getTrace() {
		return trace;
	}
}