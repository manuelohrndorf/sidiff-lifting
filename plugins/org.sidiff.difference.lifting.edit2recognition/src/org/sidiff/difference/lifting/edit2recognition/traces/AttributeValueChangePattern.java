package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Attribute;
import org.sidiff.common.henshin.NodePair;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link AttributeValueChangePattern#AttributeValueChangePattern(NodePair, NodePair, Attribute)}
 * 
 * @author Manuel Ohrndorf
 */
public class AttributeValueChangePattern {

	/**
	 * The Recognition-Rule Attribute-Value-Change (low-level difference) node. 
	 */
	public NodePair attributeValueChange;
	
	/**
	 * The Recognition-Rule node which indicates the type of the modified attribute.
	 */
	public NodePair typeNode;
	
	/**
	 * The RHS Edit-Rule attribute.
	 */
	public Attribute trace;

	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for the pattern that recognizes an attribute
	 * value change in the model difference.
	 * 
	 * @param attributeValueChange
	 *            The Recognition-Rule Attribute-Value-Change (low-level difference) node.
	 * @param typeNode
	 *            The Recognition-Rule node which indicates the type of the modified attribute.
	 * @param trace
	 *            The RHS Edit-Rule attribute.
	 */
	public AttributeValueChangePattern(NodePair attributeValueChange, NodePair typeNode, Attribute trace) {
		super();
		this.attributeValueChange = attributeValueChange;
		this.typeNode = typeNode;
		this.trace = trace;
	}
}
