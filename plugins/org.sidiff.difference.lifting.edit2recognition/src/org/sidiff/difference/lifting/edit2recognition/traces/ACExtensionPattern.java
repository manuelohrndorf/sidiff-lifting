package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.NodePair;

public class ACExtensionPattern {

	/**
	 * Recognition-Rule context node (model A node).
	 */
	private NodePair context_node_a;
	
	/**
	 * Recognition-Rule (nested) NAC/PAC model A node.
	 */
	private Node ac_context_node_a;
	
	/**
	 * Recognition-Rule (nested) NAC/PAC model B node.
	 */
	private Node ac_node_b;
	
	/**
	 * Recognition-Rule (nested) NAC/PAC correspondence node.
	 */
	private Node ac_correspondence;
	
	/**
	 * Edit-Rule context node.
	 */
	private Node context_trace;

	/**
	 * Edit-Rule (nested) AC context node.
	 */
	private Node ac_context_trace;

	public ACExtensionPattern(NodePair context_node_a, 
			Node ac_context_node_a, Node ac_node_b, Node ac_correspondence,
			Node context_trace, Node ac_context_trace) {
		super();
		this.context_node_a = context_node_a;
		this.ac_context_node_a = ac_context_node_a;
		this.ac_node_b = ac_node_b;
		this.ac_correspondence = ac_correspondence;
		this.context_trace = context_trace;
		this.ac_context_trace = ac_context_trace;
	}

	public NodePair getContext_node_a() {
		return context_node_a;
	}

	public void setContext_node_a(NodePair context_node_a) {
		this.context_node_a = context_node_a;
	}

	public Node getAc_context_node_a() {
		return ac_context_node_a;
	}

	public void setAc_context_node_a(Node ac_context_node_a) {
		this.ac_context_node_a = ac_context_node_a;
	}

	public Node getAc_node_b() {
		return ac_node_b;
	}

	public void setAc_node_b(Node ac_node_b) {
		this.ac_node_b = ac_node_b;
	}

	public Node getAc_correspondence() {
		return ac_correspondence;
	}

	public void setAc_correspondence(Node ac_correspondence) {
		this.ac_correspondence = ac_correspondence;
	}

	public Node getContext_trace() {
		return context_trace;
	}

	public void setContext_trace(Node context_trace) {
		this.context_trace = context_trace;
	}

	public Node getAc_context_trace() {
		return ac_context_trace;
	}

	public void setAc_context_trace(Node ac_context_trace) {
		this.ac_context_trace = ac_context_trace;
	}
}
