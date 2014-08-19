package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Attribute;
import org.sidiff.common.henshin.NodePair;

public class AttributeValueChangePattern {

	private NodePair attributeValueChange;
	private NodePair typeNode;
	
	private Attribute trace;

	public AttributeValueChangePattern(NodePair attributeValueChange, NodePair typeNode,
			Attribute trace) {
		super();
		this.attributeValueChange = attributeValueChange;
		this.typeNode = typeNode;
		this.trace = trace;
	}

	public NodePair getAttributeValueChange() {
		return attributeValueChange;
	}

	public void setAttributeValueChange(NodePair attributeValueChange) {
		this.attributeValueChange = attributeValueChange;
	}

	public NodePair getTypeNode() {
		return typeNode;
	}

	public void setTypeNode(NodePair typeNode) {
		this.typeNode = typeNode;
	}

	public Attribute getTrace() {
		return trace;
	}

	public void setTrace(Attribute trace) {
		this.trace = trace;
	}
}
