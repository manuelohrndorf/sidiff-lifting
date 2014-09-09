package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.NodePair;

public class ACExtensionPattern {

	/**
	 * Recognition-Rule context/glue node.
	 */
	public NodePair contextNode;
	
	/**
	 * Recognition-Rule (nested) NAC/PAC  context/glue node.
	 */
	public Node acContextNode;
	
	/**
	 * Recognition-Rule (nested) NAC/PAC extension node.
	 */
	public Node acExtensionNode;
	
	/**
	 * Recognition-Rule (nested) NAC/PAC correspondence node.
	 */
	public Node acCorrespondence;
	
	/**
	 * Edit-Rule context node.
	 */
	public Node contextTrace;

	/**
	 * Edit-Rule (nested) AC context node.
	 */
	public Node acContextTrace;

	/**
	 * @param context_node
	 * @param ac_context_node
	 * @param ac_extension_node
	 * @param ac_correspondence
	 * @param context_trace
	 * @param ac_context_trace
	 */
	public ACExtensionPattern(NodePair context_node, 
			Node ac_context_node, Node ac_extension_node, Node ac_correspondence, 
			Node context_trace, Node ac_context_trace) {
		super();
		this.contextNode = context_node;
		this.acContextNode = ac_context_node;
		this.acExtensionNode = ac_extension_node;
		this.acCorrespondence = ac_correspondence;
		this.contextTrace = context_trace;
		this.acContextTrace = ac_context_trace;
	}
}
