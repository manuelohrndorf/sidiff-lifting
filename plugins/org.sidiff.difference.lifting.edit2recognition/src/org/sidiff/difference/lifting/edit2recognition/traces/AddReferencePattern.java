
package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;
import org.sidiff.common.henshin.EdgePair;
import org.sidiff.common.henshin.NodePair;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link AddReferencePattern#AddReferencePattern(NodePair, NodePair, EdgePair, Edge)}
 * 
 * @author Manuel Ohrndorf
 */
public class AddReferencePattern {
	
	/**
	 * The Recognition-Rule Add-Reference (low-level difference) node.
	 */
	public NodePair addReference;
	
	/**
	 * The Recognition-Rule node which indicates the type of the added reference.
	 */
	public NodePair typeNode;
	
	/**
	 * The Recognition-Rule model B edge (representing the traced edge).
	 */
	public EdgePair edgeB;
	
	/**
	 * The << create >> Edit-Rule edge.
	 */
	public Edge trace;
	
	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for the pattern that recognizes an add
	 * reference in the model difference.
	 * 
	 * @param addReference
	 *            The Recognition-Rule Add-Reference (low-level difference) node.
	 * @param typeNode
	 *            The Recognition-Rule node which indicates the type of the added reference.
	 * @param edgeB
	 *            The Recognition-Rule model B edge (representing the traced edge).
	 * @param trace
	 *            The << create >> Edit-Rule edge.
	 */
	public AddReferencePattern(NodePair addReference, NodePair typeNode, EdgePair edgeB, Edge trace) {
		super();
		this.addReference = addReference;
		this.typeNode = typeNode;
		this.edgeB = edgeB;
		this.trace = trace;
	}
}
