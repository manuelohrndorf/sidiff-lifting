package org.sidiff.difference.lifting.edit2recognition.editrule;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getForbidAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getForbidEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRequireAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRequireEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithChangingAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithCreationAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithCreationEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithDeletionAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithDeletionEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithPreservedAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithPreservedEdges;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithoutAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isNodeWithoutEdges;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.view.AttributePair;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.difference.lifting.edit2recognition.editrule.EditRuleAnnotations.Condition;

/**
 * Analysis of edit-rule specific features. And configuration of edit- to recognition specific features
 * 
 * @author Manuel Ohrndorf
 */
public class EditRuleAnalysis {

	/**
	 * Configuration if a << preserve >> edge will be searched in model A.
	 * 
	 * @return <code>true</code> if it will be searched in model A;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isPreservedEdgeSearchedInModelA() {
		return false;
	}

	/**
	 * Configuration if a << preserve >> edge will be searched in model B.
	 * 
	 * @return <code>true</code> if it will be searched in model B;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isPreservedEdgeSearchedInModelB() {
		return true;
	}

	/**
	 * Configuration if a << preserve / delete >> attribute in a << preserve >> node
	 * will be searched in model A.
	 * 
	 * @return <code>true</code> if it will be searched in model A;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isPreservedAttributeSearchedInModelA() {
		return false;
	}

	/**
	 * Configuration if a << preserve / delete >> attribute in a << preserve >>
	 * node will be searched in model B.
	 * 
	 * @return <code>true</code> if it will be searched in model B;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isPreservedAttributeSearchedInModelB() {
		return true;
	}

	/**
	 * Tests if a << preserve >> node will be searched in model A.
	 * 
	 * @param editRuleNode
	 *            The << preserve >> edit rule node to test.
	 * @return <code>true</code> if it will be searched in model A;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isPreservedNodeSearchedInModelA(NodePair editRuleNode) {
		Node lhsNode = editRuleNode.getLhsNode();

		if (isNodeWithDeletionEdges(lhsNode) 
				|| isNodeWithChangingAttributes(lhsNode)
				|| (isPreservedAttributeSearchedInModelA() && isNodeWithPreservedAttributes(lhsNode))
				|| (isPreservedEdgeSearchedInModelA() && isNodeWithPreservedEdges(lhsNode))) {
			return true;
		}

		else if (isMultiContextNodeModelA(editRuleNode)) {
			return true;
		}

		return false;
	}

	/**
	 * Tests if a << preserve >> node will be searched in model B.
	 * 
	 * @param editRuleNode
	 *            The << preserve >> edit rule node to test.
	 * @return <code>true</code> if it will be searched in model B;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isPreservedNodeSearchedInModelB(NodePair editRuleNode) {
		Node lhsNode = editRuleNode.getLhsNode();
		Node rhsNode = editRuleNode.getRhsNode();

		if (isNodeWithCreationEdges(rhsNode)
				|| isNodeWithCreationAttributes(rhsNode)
				|| isNodeWithDeletionAttributes(lhsNode)
				|| isNodeWithChangingAttributes(lhsNode)
				|| (isPreservedAttributeSearchedInModelB() && isNodeWithPreservedAttributes(lhsNode))
				|| (isPreservedEdgeSearchedInModelB() && isNodeWithPreservedEdges(lhsNode))) {
			return true;
		}

		else if (isMultiContextNodeModelB(editRuleNode)) {
			return true;
		}

		else if (!isPreservedNodeSearchedInModelA(editRuleNode)) {
			// Empty node!?
			return true;
		}

		return false;
	}
	
	/**
	 * Tests if the given nested graph is an application precondition graph.
	 * 
	 * @param applicationCondition
	 *            A nested AC graph.
	 * @return <code>true</code> if the given AC is precondition;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isPrecondition(Graph applicationCondition) {
		return EditRuleAnnotations.getCondition(applicationCondition).equals(Condition.pre);
	}

	/**
	 * Tests if the given nested graph is an application postcondition graph.
	 * 
	 * @param applicationCondition
	 *            A nested AC graph.
	 * @return <code>true</code> if the given AC is postcondition;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isPostcondition(Graph applicationCondition) {
		return EditRuleAnnotations.getCondition(applicationCondition).equals(Condition.post);
	}

	/**
	 * Searches for incident << forbid >> (precondition) edges in a node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << forbid >> (precondition)
	 *         edge; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithPreconditionForbidEdges(Node node) {

		for (Edge forbidEdge : getForbidEdges(node)) {
			Condition conditionType = EditRuleAnnotations.getCondition(forbidEdge.getGraph());

			if (conditionType.equals(Condition.pre)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for incident << forbid >> (postcondition) edges in a node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << forbid >> (postcondition)
	 *         edge; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithPostconditionForbidEdges(Node node) {

		for (Edge forbidEdge : getForbidEdges(node)) {
			Condition conditionType = EditRuleAnnotations.getCondition(forbidEdge.getGraph());

			if (conditionType.equals(Condition.post)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for incident << require >> (precondition) edges in a node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << require >> (precondition)
	 *         edge; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithPreconditionRequireEdges(Node node) {

		for (Edge requireEdge : getRequireEdges(node)) {
			Condition conditionType = EditRuleAnnotations.getCondition(requireEdge.getGraph());

			if (conditionType.equals(Condition.pre)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for incident << require >> (postcondition) edge in a node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << require >> (postcondition)
	 *         edge; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithPostconditionRequireEdges(Node node) {

		for (Edge requireEdge : getRequireEdges(node)) {
			Condition conditionType = EditRuleAnnotations.getCondition(requireEdge.getGraph());

			if (conditionType.equals(Condition.post)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for << forbid >> (precondition) attributes in a node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << forbid >> (precondition)
	 *         attribute; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithPreconditionForbidAttributes(Node node) {

		for (Attribute forbidAttribute : getForbidAttributes(node)) {
			Condition conditionType = EditRuleAnnotations.getCondition(forbidAttribute.getGraph());

			if (conditionType.equals(Condition.pre)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for << forbid >> (postcondition) attributes in a node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << forbid >> (postcondition)
	 *         attribute; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithPostconditionForbidAttributes(Node node) {

		for (Attribute forbidAttribute : getForbidAttributes(node)) {
			Condition conditionType = EditRuleAnnotations.getCondition(forbidAttribute.getGraph());

			if (conditionType.equals(Condition.post)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for << require >> (precondition) attributes in a node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << require >> (precondition)
	 *         attribute; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithPreconditionRequireAttributes(Node node) {

		for (Attribute requireAttribute : getRequireAttributes(node)) {
			Condition conditionType = EditRuleAnnotations.getCondition(requireAttribute.getGraph());

			if (conditionType.equals(Condition.pre)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for << require >> (postcondition) attributes in a node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << require >> (postcondition)
	 *         attribute; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithPostconditionRequireAttributes(Node node) {

		for (Attribute requireAttribute : getRequireAttributes(node)) {
			Condition conditionType = EditRuleAnnotations.getCondition(requireAttribute.getGraph());

			if (conditionType.equals(Condition.post)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * A multi-context-node is a node of a kernel-rule which has exclusively
	 * incident edges of multi-rules. The model A/B side is only determined by
	 * this multi-rule edges, i.e. a multi-context-node has no edges/attributes
	 * in the kernel-rule.
	 * 
	 * @param editRuleNode
	 *            the edit-rule node to test.
	 * @return <code>true</code> if the given node is a multi-context node
	 *         <code>false</code> otherwise.
	 */
	public static boolean isMultiContextNode(NodePair editRuleNode) {

		if ((editRuleNode.getLhsNode() != null) && (editRuleNode.getRhsNode() != null)) {
			return isMultiContextNodeModelA(editRuleNode) || isMultiContextNodeModelB(editRuleNode);
		} else {
			return false;
		}
	}

	/**
	 * A multi-context-node is a node of a kernel-rule which has exclusively
	 * incident edges of multi-rules. The model A/B side is only determined by
	 * this multi-rule edges, i.e. a multi-context-node has no edges/attributes
	 * in the kernel-rule.
	 * 
	 * @param editRuleNode
	 *            the edit-rule node to test.
	 * @return <code>true</code> if the given node is a multi-context node that
	 *         will be searched in model A. <code>false</code> otherwise.
	 */
	public static boolean isMultiContextNodeModelA(NodePair editRuleNode) {
		Node lhsNode = editRuleNode.getLhsNode();
		Node rhsNode = editRuleNode.getRhsNode();

		// Is node without any edges/attributes?
		if (isNodeWithoutEdges(rhsNode) && isNodeWithoutEdges(lhsNode)
				&& isNodeWithoutAttributes(rhsNode) && isNodeWithoutAttributes(lhsNode)) {

			for (Rule multiRule : lhsNode.getGraph().getRule().getMultiRules()) {
				Node lhsNodeMulti = multiRule.getMultiMappings().getImage(lhsNode,
						multiRule.getLhs());
				Node rhsNodeMulti = multiRule.getMultiMappings().getImage(rhsNode,
						multiRule.getRhs());

				// Is multi-node?
				if ((lhsNodeMulti != null) && (rhsNodeMulti != null)) {
					return isPreservedNodeSearchedInModelA(new NodePair(lhsNodeMulti, rhsNodeMulti));
				}
			}
		}

		return false;
	}

	/**
	 * A multi-context-node is a node of a kernel-rule which has exclusively
	 * incident edges of multi-rules. The model A/B side is only determined by
	 * this multi-rule edges, i.e. a multi-context-node has no edges/attributes
	 * in the kernel-rule.
	 * 
	 * @param editRuleNode
	 *            the edit-rule node to test.
	 * @return <code>true</code> if the given node is a multi-context node that
	 *         will be searched in model B. <code>false</code> otherwise.
	 */
	public static boolean isMultiContextNodeModelB(NodePair editRuleNode) {
		Node lhsNode = editRuleNode.getLhsNode();
		Node rhsNode = editRuleNode.getRhsNode();

		// Is node without any edges/attributes?
		if (isNodeWithoutEdges(rhsNode) && isNodeWithoutEdges(lhsNode)
				&& isNodeWithoutAttributes(rhsNode) && isNodeWithoutAttributes(lhsNode)) {

			for (Rule multiRule : lhsNode.getGraph().getRule().getMultiRules()) {
				Node lhsNodeMulti = multiRule.getMultiMappings().getImage(lhsNode,
						multiRule.getLhs());
				Node rhsNodeMulti = multiRule.getMultiMappings().getImage(rhsNode,
						multiRule.getRhs());

				// Is multi-node?
				if ((lhsNodeMulti != null) && (rhsNodeMulti != null)) {
					return isPreservedNodeSearchedInModelB(new NodePair(lhsNodeMulti, rhsNodeMulti));
				}
			}
		}

		return false;
	}

	/**
	 * Test attribute value change (attributeX -> attributeY) for containing
	 * parameters/variables, which makes the attribute value change optional.
	 * 
	 * @param attribute
	 *            The attribute (attributeX -> attributeY).
	 * 
	 * @return <code>true</code> if the AVC is only optional; <code>false</code>
	 *         otherwise.
	 */
	public static boolean isOptionalAttributeValueChange(AttributePair attribute) {
		return containsParameters(attribute.getLhsAttribute())
				|| containsParameters(attribute.getRhsAttribute());
	}

	/**
	 * Test attribute contains parameters/variables.
	 * 
	 * @param attribute
	 *            The attribute to test.
	 * 
	 * @return <code>true</code> if the attribute contains parameters;
	 *         <code>false</code> otherwise.
	 */
	private static boolean containsParameters(Attribute attribute) {

		Rule containingRule = attribute.getNode().getGraph().getRule();

		// LHS or RHS attribute contains a variable/parameter:
		for (Parameter parameter : containingRule.getParameters()) {
			// FIXME: Need real parsing:
			if (attribute.getValue().contains(parameter.getName())) {
				return true;
			}
		}

		return false;
	}
}
