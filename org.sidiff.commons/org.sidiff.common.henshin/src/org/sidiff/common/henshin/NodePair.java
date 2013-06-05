package org.sidiff.common.henshin;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Node;

/**
 * Contains a LHS and its corresponding RHS Henshin node.
 */
public class NodePair {
	
	/**
	 * The LHS node of the node pair.
	 */
	private Node lhsNode;
	
	/**
	 * The RHS node of the node pair.
	 */
	private Node rhsNode;

	/**
	 * Constructs a new empty node pair.
	 */
	public NodePair() {
	}
	
	/**
	 * Constructs a new node pair.
	 * 
	 * @param lhsNode
	 *            the LHS node.
	 * @param rhsNode
	 *            the RHS node.
	 */
	public NodePair(Node lhsNode, Node rhsNode) {
		super();
		this.lhsNode = lhsNode;
		this.rhsNode = rhsNode;
	}
	
	/**
	 * @return the LHS node.
	 */
	public Node getLhsNode() {
		return lhsNode;
	}

	/**
	 * @param lhsNode the new LHS node.
	 */
	public void setLhsNode(Node lhsNode) {
		this.lhsNode = lhsNode;
	}
	
	/**
	 * @return the RHS node.
	 */
	public Node getRhsNode() {
		return rhsNode;
	}
	
	/**
	 * @param rhsNode the new RHS node.
	 */
	public void setRhsNode(Node rhsNode) {
		this.rhsNode = rhsNode;
	}

	/**
	 * @return the node type.
	 */
	public EClass getType() {
		return lhsNode.getType();
	}
}
