
package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link ACObjectPattern#ACObjectPattern(Node, Node)}
 * 
 * @author Manuel Ohrndorf
 */
public class ACObjectPattern {
	
	/**
	 * The Recognition-Rule AC node.
	 */
	private Node acNode;
	
	/**
	 * The Edit-Rule AC node.
	 */
	private Node acTrace;
	
	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for an application condition (AC) node.
	 * 
	 * @param acNode
	 *            The Recognition-Rule AC node.
	 * @param acTrace
	 *            The Edit-Rule AC node.
	 */
	public ACObjectPattern(Node acNode, Node acTrace) {
		super();
		this.acNode = acNode;
		this.acTrace = acTrace;
	}
	
	/**
	 * @return The {@link ACObjectPattern#acNode}
	 */
	public Node getACNode() {
		return acNode;
	}
	
	/**
	 * @return The {@link ACObjectPattern#acTrace}
	 */
	public Node getACTrace() {
		return acTrace;
	}
}