package org.sidiff.common.henshin;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithCreationAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithCreationEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithDeletionEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithPreservedAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithPreservedEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithoutEdges;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class EditRuleAnalysis {
	
	/**
	 * A edge is searched in model A if its source and target nodes are searched in model A.
	 * 
	 * @param editRuleEdge
	 *            The << preserve >> edit rule edge to test.
	 * @return <code>true</code> if it will be searched in model A; <code>false</code> otherwise.
	 */
	public static boolean isSearchedInModelA(EdgePair editRuleEdge) {
		NodePair sourceNode = new NodePair(editRuleEdge.getLhsEdge().getSource(), editRuleEdge.getRhsEdge().getSource());
		NodePair targetNode = new NodePair(editRuleEdge.getLhsEdge().getTarget(), editRuleEdge.getRhsEdge().getTarget());

		if (isSearchedInModelA(sourceNode)
				&& isSearchedInModelA(targetNode)) {
			return true;
		}

		return false;
	}

	/**
	 * A edge is searched in model B if its source and target nodes are searched in model B.
	 * 
	 * @param editRuleEdge
	 *            The << preserve >> edit rule edge to test.
	 * @return <code>true</code> if it will be searched in model B; <code>false</code> otherwise.
	 */
	public static boolean isSearchedInModelB(EdgePair editRuleEdge) {
		NodePair sourceNode = new NodePair(editRuleEdge.getLhsEdge().getSource(), editRuleEdge.getRhsEdge().getSource());
		NodePair targetNode = new NodePair(editRuleEdge.getLhsEdge().getTarget(), editRuleEdge.getRhsEdge().getTarget());

		if (isSearchedInModelB(sourceNode)
				&& isSearchedInModelB(targetNode)) {
			return true;
		}

		return false;
	}

	/**
	 * A <<preserve>> node with <<delete>> edges has always a match in model A.<br>
	 * 
	 * <li>A node with attribute set operations has always a match in model A and model B.</li>
	 * 
	 * @param editRuleNode
	 *            The << preserve >> edit rule node to test.
	 * @return <code>true</code> if it will be searched in model A; <code>false</code> otherwise.
	 */
	public static boolean isSearchedInModelA(NodePair editRuleNode) {
		Node lhsNode = editRuleNode.getLhsNode();
		Node rhsNode = editRuleNode.getRhsNode();

		//TODO MO: Has to be checked if this is correct!!!
		if (isNodeWithDeletionEdges(lhsNode) || isNodeWithCreationAttributes(rhsNode)) {
			return true;
		} 
		
		// Check if a mapping origin has deletion edges in a multi rule
		for (Rule multiRule : lhsNode.getGraph().getRule().getMultiRules()) {
			Node lhsNodeMulti = multiRule.getMultiMappings().getImage(lhsNode, multiRule.getLhs());
			if (lhsNodeMulti != null && isNodeWithDeletionEdges(lhsNodeMulti)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * A node without edges has always a match in model B. We presuppose that positive application
	 * conditions will not be changed after applying an edit operation.<br>
	 * 
	 * <li>A << preserve >> node with << create >> edges has always a match in model B.</li>
	 * 
	 * <li>A << preserve>> node with << preserve >> edges has always a match in model B. We
	 * presuppose that positive application conditions will not be changed after applying an edit
	 * operation.</li>
	 * 
	 * <li>A node with attribute set operations has always a match in model A and model B.</li>
	 * 
	 * <li>A node with << preserve >> attributes has always a match in model B. We presuppose that
	 * positive application conditions will not be changed after applying an edit operation.</li>
	 * 
	 * @param editRuleNode
	 *            The << preserve >> edit rule node to test.
	 * @return <code>true</code> if it will be searched in model B; <code>false</code> otherwise.
	 */
	public static boolean isSearchedInModelB(NodePair editRuleNode) {
		Node lhsNode = editRuleNode.getLhsNode();
		Node rhsNode = editRuleNode.getRhsNode();

		if ((isNodeWithoutEdges(rhsNode) && isNodeWithoutEdges(lhsNode))
				|| isNodeWithCreationEdges(rhsNode)
				|| isNodeWithPreservedEdges(lhsNode)
				|| isNodeWithCreationAttributes(rhsNode)
				|| isNodeWithPreservedAttributes(lhsNode)) {
			return true;
		} 
		
		// Check if any of the conditions apply for a mapping origin in a multi rule
		for (Rule multiRule : lhsNode.getGraph().getRule().getMultiRules()) {
			Node lhsNodeMulti = multiRule.getMultiMappings().getImage(lhsNode, multiRule.getLhs());
			Node rhsNodeMulti = multiRule.getMultiMappings().getImage(rhsNode, multiRule.getRhs());
			
			if ((lhsNodeMulti != null && rhsNodeMulti != null && isNodeWithoutEdges(rhsNodeMulti) && isNodeWithoutEdges(lhsNodeMulti))
					|| (rhsNodeMulti != null && isNodeWithCreationEdges(rhsNodeMulti))
					|| (lhsNodeMulti != null && isNodeWithPreservedEdges(lhsNodeMulti))
					|| (rhsNodeMulti != null && isNodeWithCreationAttributes(rhsNodeMulti))
					|| (lhsNodeMulti != null && isNodeWithPreservedAttributes(lhsNodeMulti))) {
				return true;
			}	
		}
		
		return false;
	}
}
