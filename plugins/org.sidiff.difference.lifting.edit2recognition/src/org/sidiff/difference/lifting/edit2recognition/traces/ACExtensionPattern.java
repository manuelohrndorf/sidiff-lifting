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
	public NodePair lhsBoundaryNode;
	
	/**
	 * The nested Recognition-Rule AC boundary node.
	 */
	public Node acBoundaryNode;
	
	/**
	 * The Recognition-Rule AC extension node.
	 */
	public Node acExtensionNode;
	
	/**
	 * The Recognition-Rule AC correspondence node.
	 */
	public Node acCorrespondence;
	
	/**
	 * The LHS Edit-Rule LHS boundary node.
	 */
	public Node lhsBoundrayTrace;

	/**
	 * The nested Edit-Rule AC boundary node.
	 */
	public Node acBoundaryTrace;

	/**
	 * Creates a new (Edit- to Recognition-Rule) trace for an application condition (AC)
	 * correspondence extension. Boundary-Node <-> Correspondence <-> AC-Extension-Node. The
	 * AC boundary node is mapped to parent LHS-Boundary-Node.
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
}
