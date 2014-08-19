package org.sidiff.common.henshin;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Attribute;

/**
 * Contains a LHS and its corresponding RHS Henshin attribute.
 */
public class AttributePair {

	/**
	 * The LHS attribute of the attribute pair.
	 */
	private Attribute lhsAttribute;
	
	/**
	 * The RHS attribute of the attribute pair.
	 */
	private Attribute rhsAttribute;

	/**
	 * Constructs a new empty attribute pair.
	 */
	public AttributePair() {
		super();
	}
	
	/**
	 * Constructs a new attribute pair.
	 * 
	 * @param lhsAttribute
	 *            the LHS attribute.
	 * @param rhsAttribute
	 *            the RHS attribute.
	 */
	public AttributePair(Attribute lhsAttribute, Attribute rhsAttribute) {
		super();
		this.lhsAttribute = lhsAttribute;
		this.rhsAttribute = rhsAttribute;
	}

	/**
	 * @return the LHS attribute.
	 */
	public Attribute getLhsAttribute() {
		return lhsAttribute;
	}

	/**
	 * @param lhsAttribute
	 *            the new LHS attribute.
	 */
	public void setLhsAttribute(Attribute lhsAttribute) {
		this.lhsAttribute = lhsAttribute;
	}

	/**
	 * @return the LHS attribute.
	 */
	public Attribute getRhsAttribute() {
		return rhsAttribute;
	}

	/**
	 * @param rhsAttribute
	 *            the new RHS attribute.
	 */
	public void setRhsAttribute(Attribute rhsAttribute) {
		this.rhsAttribute = rhsAttribute;
	}
	
	/**
	 * @return the attribute type.
	 */
	public EAttribute getType() {
		return lhsAttribute.getType();
	}
}
