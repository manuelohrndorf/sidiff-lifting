package org.sidiff.common.henshin;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;

public class EdgePair {

	/**
	 * The LHS edge of the edge pair.
	 */
	private Edge lhsEdge;
	
	/**
	 * The RHS edge of the edge pair.
	 */
	private Edge rhsEdge;
	
	/**
	 * Constructs a new empty edge pair.
	 */
	public EdgePair() {
		super();
	}
	
	/**
	 * Constructs a new edge pair.
	 * 
	 * @param lhsEdge
	 *            the LHS edge.
	 * @param rhsEdge
	 *            the RHS edge.
	 */
	public EdgePair(Edge lhsEdge, Edge rhsEdge) {
		super();
		this.lhsEdge = lhsEdge;
		this.rhsEdge = rhsEdge;
	}

	/**
	 * @return the LHS edge.
	 */
	public Edge getLhsEdge() {
		return lhsEdge;
	}

	/**
	 * @param lhsEdge the new LHS edge.
	 */
	public void setLhsEdge(Edge lhsEdge) {
		this.lhsEdge = lhsEdge;
	}

	/**
	 * @return the RHS edge.
	 */
	public Edge getRhsEdge() {
		return rhsEdge;
	}

	/**
	 * @param rhsEdge the new RHS edge.
	 */
	public void setRhsEdge(Edge rhsEdge) {
		this.rhsEdge = rhsEdge;
	}
	
	/**
	 * @return the edge type.
	 */
	public EReference getType() {
		return this.lhsEdge.getType();
	}
}
