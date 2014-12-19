
package org.sidiff.difference.lifting.edit2recognition.traces;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.NodePair;

/**
 * Stores a transformation trace between the Edit-Rule and the corresponding Recognition-Rule.
 * 
 * @see {@link ACExtensionPattern#ACExtensionPattern(NodePair, Node, Node, Node, Node, Node)}
 * 
 * @author Manuel Ohrndorf
 */
public class ACExtensionPattern {
	
	/**
	 * The Recognition-Rule LHS boundary node.
	 */
	private NodePair lhsBoundaryNode;
	
	/**
	 * The nested Recognition-Rule AC boundary node.
	 */
	private Node acBoundaryNode;
	
	/**
	 * The Recognition-Rule AC extension node.
	 */
	private Node acExtensionNode;
	
	/**
	 * The Recognition-Rule AC correspondence node.
	 */
	private Node acCorrespondence;
	
	/**
	 * The LHS Edit-Rule LHS boundary node.
	 */
	private Node lhsBoundrayTrace;
	
	/**
	 * The nested Edit-Rule AC boundary node.
	 */
	private Node acBoundaryTrace;
	
	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for an application condition (AC)
	 * correspondence extension. Boundary-Node <-> Correspondence <-> AC-Extension-Node. The AC
	 * boundary node is mapped to parent LHS-Boundary-Node.
	 * 
	 * @param lhsBoundaryNode
	 *            The Recognition-Rule LHS boundary node.
	 * @param acBoundaryNode
	 *            The nested Recognition-Rule AC boundary node.
	 * @param acExtensionNode
	 *            The Recognition-Rule AC extension node.
	 * @param acCorrespondence
	 *            The Recognition-Rule AC correspondence node.
	 * @param lhsBoundrayTrace
	 *            The LHS Edit-Rule LHS boundary node.
	 * @param acBoundaryTrace
	 *            The nested Edit-Rule AC boundary node.
	 */
	public ACExtensionPattern(NodePair lhsBoundaryNode, Node acBoundaryNode, Node acExtensionNode,
			Node acCorrespondence, Node lhsBoundrayTrace, Node acBoundaryTrace) {
		super();
		this.lhsBoundaryNode = lhsBoundaryNode;
		this.acBoundaryNode = acBoundaryNode;
		this.acExtensionNode = acExtensionNode;
		this.acCorrespondence = acCorrespondence;
		this.lhsBoundrayTrace = lhsBoundrayTrace;
		this.acBoundaryTrace = acBoundaryTrace;
	}
	
	/**
	 * @return The {@link ACExtensionPattern#lhsBoundaryNode}
	 */
	public NodePair getLHSBoundaryNode() {
		return lhsBoundaryNode;
	}
	
	/**
	 * @return The {@link ACExtensionPattern#acBoundaryNode}
	 */
	public Node getACBoundaryNode() {
		return acBoundaryNode;
	}
	
	/**
	 * @return The {@link ACExtensionPattern#acExtensionNode}
	 */
	public Node getACExtensionNode() {
		return acExtensionNode;
	}
	
	/**
	 * @return The {@link ACExtensionPattern#acCorrespondence}
	 */
	public Node getACCorrespondence() {
		return acCorrespondence;
	}
	
	/**
	 * @return The {@link ACExtensionPattern#lhsBoundrayTrace}
	 */
	public Node getLHSBoundrayTrace() {
		return lhsBoundrayTrace;
	}
	
	/**
	 * @return The {@link ACExtensionPattern#acBoundaryTrace}
	 */
	public Node getACBoundaryTrace() {
		return acBoundaryTrace;
	}
}
