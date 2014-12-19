
package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Edge;
import org.sidiff.common.henshin.EdgePair;
import org.sidiff.common.henshin.NodePair;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link RemoveReferencePattern#RemoveReferencePattern(NodePair, NodePair, EdgePair, Edge)}
 * 
 * @author Manuel Ohrndorf
 */
public class RemoveReferencePattern {
	
	/**
	 * The Recognition-Rule Remove-Reference (low-level difference) node.
	 */
	private NodePair removeReference;
	
	/**
	 * The Recognition-Rule node which indicates the type of the removed reference.
	 */
	private NodePair typeNode;
	
	/**
	 * The Recognition-Rule model A edge (representing the traced edge).
	 */
	private EdgePair edgeA;
	
	/**
	 * The << delete >> Edit-Rule edge.
	 */
	private Edge trace;
	
	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for the pattern that recognizes a removed
	 * reference in the model difference.
	 * 
	 * @param removeReference
	 *            The Recognition-Rule Remove-Reference (low-level difference) node.
	 * @param typeNode
	 *            The Recognition-Rule node which indicates the type of the removed reference.
	 * @param edgeA
	 *            The Recognition-Rule model A edge (representing the traced edge).
	 * @param trace
	 *            The << delete >> Edit-Rule edge.
	 */
	public RemoveReferencePattern(NodePair removeReference, NodePair typeNode, EdgePair edgeA, Edge trace) {
		super();
		this.removeReference = removeReference;
		this.typeNode = typeNode;
		this.edgeA = edgeA;
		this.trace = trace;
	}
	
	/**
	 * @return The {@link RemoveReferencePattern#removeReference}
	 */
	public NodePair getRemoveReference() {
		return removeReference;
	}
	
	/**
	 * @return The {@link RemoveReferencePattern#typeNode}
	 */
	public NodePair getTypeNode() {
		return typeNode;
	}
	
	/**
	 * @return The {@link RemoveReferencePattern#edgeA}
	 */
	public EdgePair getEdgeA() {
		return edgeA;
	}
	
	/**
	 * @return The {@link RemoveReferencePattern#trace}
	 */
	public Edge getTrace() {
		return trace;
	}
}