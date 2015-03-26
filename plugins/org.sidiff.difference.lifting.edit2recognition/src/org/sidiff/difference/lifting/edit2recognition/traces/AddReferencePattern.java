
package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;
import org.sidiff.common.henshin.view.EdgePair;
import org.sidiff.common.henshin.view.NodePair;

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
	private NodePair addReference;
	
	/**
	 * The Recognition-Rule node which indicates the type of the added reference.
	 */
	private NodePair typeNode;
	
	/**
	 * The Recognition-Rule model B edge (representing the traced edge).
	 */
	private EdgePair edgeB;
	
	/**
	 * The << create >> Edit-Rule edge.
	 */
	private Edge trace;
	
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
	
	/**
	 * @return The {@link AddReferencePattern#addReference}
	 */
	public NodePair getAddReference() {
		return addReference;
	}
	
	/**
	 * @return The {@link AddReferencePattern#typeNode}
	 */
	public NodePair getTypeNode() {
		return typeNode;
	}
	
	/**
	 * @return The {@link AddReferencePattern#edgeB}
	 */
	public EdgePair getEdgeB() {
		return edgeB;
	}
	
	/**
	 * @return The {@link AddReferencePattern#trace}
	 */
	public Edge getTrace() {
		return trace;
	}
	
}
