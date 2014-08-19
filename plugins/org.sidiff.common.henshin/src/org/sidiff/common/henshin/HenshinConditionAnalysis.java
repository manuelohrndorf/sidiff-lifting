package org.sidiff.common.henshin;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.findMappingByImage;

import java.util.List;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;

public class HenshinConditionAnalysis {

	/**
	 * Check whether the given node is a node of a nested condition.
	 * 
	 * @param node
	 *            The node to test.
	 * @return <code>true</code> if the node is part of a nested condition;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isNestedConditionNode(Node node) {
		return node.getGraph().isNestedCondition();
	}

	/**
	 * Check whether the given edge is a edge of a nested condition.
	 * 
	 * @param edge
	 *            The edge to test.
	 * @return <code>true</code> if the edge is part of a nested condition;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isNestedConditionEdge(Edge edge) {
		return edge.getGraph().isNestedCondition();
	}

	/**
	 * Checks whether the given node is an application condition (AC) glue node.
	 * An AC glue point is an AC context node with incident AC edges.
	 * 
	 * @param node
	 *            The node to test.
	 * @return <code>true</code> if the node is a glue node; <code>false</code>
	 *         otherwise.
	 */
	public static boolean isACGlueNode(Node node) {

		if (!isNestedConditionNode(node)) {
			return false;
		}

		if (isACContextNode(node)) {
			for (Edge edge : node.getAllEdges()) {
				if (isACEdge(edge)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks whether this node is a context node of an application condition
	 * (AC).
	 * 
	 * @param node
	 *            The node to test.
	 * @return <code>true</code> if the node is a context node;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isACContextNode(Node node) {

		if (!isNestedConditionNode(node)) {
			return false;
		}

		List<Mapping> mappings = ((NestedCondition) node.getGraph()
				.eContainer()).getMappings();

		if (HenshinRuleAnalysisUtilEx.isNodeMapped(mappings, node)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether this edge is a context edge of an application condition
	 * (AC).
	 * 
	 * @param edge
	 *            The edge to test.
	 * @return <code>true</code> if the edge is a context node;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isACEdge(Edge edge) {

		if (!isNestedConditionEdge(edge)) {
			return false;
		}

		// Mappings for context AC nodes:
		List<Mapping> mappings = ((NestedCondition) edge.getGraph()
				.eContainer()).getMappings();
		Mapping source_mapping = findMappingByImage(mappings, edge.getSource());
		Mapping target_mapping = findMappingByImage(mappings, edge.getTarget());

		// Is edge mapped?
		if ((source_mapping != null) && (target_mapping != null)) {
			return false;
		} else {
			return true;
		}
	}
}
