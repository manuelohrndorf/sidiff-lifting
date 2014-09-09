package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Attribute;
import org.sidiff.common.henshin.NodePair;

public class AttributeValueChangePattern {

	public NodePair attributeValueChange;
	public NodePair typeNode;
	
	public Attribute trace;

	/**
	 * @param attributeValueChange
	 * @param typeNode
	 * @param trace
	 */
	public AttributeValueChangePattern(NodePair attributeValueChange, NodePair typeNode,
			Attribute trace) {
		super();
		this.attributeValueChange = attributeValueChange;
		this.typeNode = typeNode;
		this.trace = trace;
	}
}
