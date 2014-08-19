package org.sidiff.common.henshin;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.findMappingByImage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

public class HenshinMultiRuleAnalysis {

	/**
	 * Returns all root rules of Multi-Rules contained by the module.
	 * 
	 * @param module
	 *            The module to search.
	 * @return All root rules of Multi-Rules contained by the module.
	 */
	public static Set<Rule> getRootRules(Module module) {
		Set<Rule> rootRules = new HashSet<Rule>();
		
		for (Unit unit : module.getUnits()) {
			if (unit instanceof Rule && !((Rule)unit).getMultiRules().isEmpty()){
				rootRules.add((Rule) unit);
			}
		}
		
		for (Module subModule : module.getSubModules()) {
			rootRules.addAll(getRootRules(subModule));
		}
		
		return rootRules;
	}
	
	/**
	 * Get the target node and all mapped multi target nodes of the given edge. The edge is given by
	 * its type and its source node.
	 * 
	 * @param source
	 *            The source node.
	 * @param type
	 *            The edge type.
	 * @return A list containing the target node and all mapped multi target nodes.
	 */
	public static List<Node> getTargets(Node source, EReference type) {
		
		// Collect all multi source nodes:
		Set<Node> multiSourceNodes = getAllMappedNodes(source);
		multiSourceNodes.add(source);
		
		// Collect all mapped node (LHS/RHS):
		List<Node> mappedMultiSourceNodes = new ArrayList<Node>();
		for (Node multiSourceNode : multiSourceNodes) {
			List<Mapping> mappings = multiSourceNode.getGraph().getRule().getMappings();

			for (Mapping mapping : mappings) {
				if (mapping.getImage() == multiSourceNode) {
					mappedMultiSourceNodes.add(mapping.getOrigin());
				} else if (mapping.getOrigin() == multiSourceNode) {
					mappedMultiSourceNodes.add(mapping.getImage());
				}
			}
		}
		multiSourceNodes.addAll(mappedMultiSourceNodes);
		
		// Collect all multi target nodes:
		List<Node> multiTargetNodes = new ArrayList<Node>();
		for (Node multiSourceNode : multiSourceNodes) {
			for (Edge edgeType : multiSourceNode.getOutgoing(type)) {
				multiTargetNodes.add(edgeType.getTarget());
			}	
		}
		
		return multiTargetNodes;
	}
	
	/**
	 * Get the source node and all mapped multi source nodes of the given edge. The edge is given by
	 * its type and its target node.
	 * 
	 * @param target
	 *            The target node.
	 * @param type
	 *            The edge type.
	 * @return A list containing the source node and all mapped multi source nodes.
	 */
	public static List<Node> getSources(Node target, EReference type) {
		
		// Collect all multi nodes:
		Set<Node> multiTargetNodes = getAllMappedNodes(target);
		multiTargetNodes.add(target);
		
		// Collect all mapped node (LHS/RHS):
		List<Node> mappedMultiTargetNodes = new ArrayList<Node>();
		for (Node multiTargetNode : multiTargetNodes) {
			List<Mapping> mappings = multiTargetNode.getGraph().getRule().getMappings();

			for (Mapping mapping : mappings) {
				if (mapping.getImage() == multiTargetNode) {
					mappedMultiTargetNodes.add(mapping.getOrigin());
				} else if (mapping.getOrigin() == multiTargetNode) {
					mappedMultiTargetNodes.add(mapping.getImage());
				}
			}
		}
		multiTargetNodes.addAll(mappedMultiTargetNodes);
		
		// Collect all multi edges:
		List<Node> multiSourceNodes = new ArrayList<Node>();
		for (Node multiTargetNode : multiTargetNodes) {
			for (Edge edgeType : multiTargetNode.getIncoming(type)) {
				multiSourceNodes.add(edgeType.getSource());
			}	
		}
		
		return multiSourceNodes;
	}
	
	/**
	 * Returns all nodes that are mapped with the given node (in all
	 * Multi-Rules).
	 * 
	 * @param node
	 *            The start node.
	 * @return All nodes that are mapped with the given node (in all
	 *         Multi-Rules).
	 */
	public static Set<Node> getAllMappedNodes(Node node) {
		Set<Node> multiNodes = new HashSet<Node>();
		getImages(findOrigin(node), multiNodes);
		return multiNodes;
	}

	/**
	 * Follows the mappings (of the given node) from the Multi-Rule to the Kernel-Rule and returns
	 * the node origin.
	 * 
	 * @param nodeImage
	 *            The start node.
	 * @return The origin of the given node.
	 */
	public static Node findOrigin(Node nodeImage) {
		Rule imageRule = nodeImage.getGraph().getRule();
		
		for (Mapping mapping : imageRule.getMultiMappings()) {
			if (mapping.getImage() == nodeImage) {
				// Search in origin direction:
				return findOrigin(mapping.getOrigin());
			}
		}
		
		return nodeImage;
	}
	
	/**
	 * Follows the mappings (of the given node) from the Kernel-Rule to all deeper Multi-Rules and
	 * collects all nodes.
	 * 
	 * @param nodeOrigin
	 *            The start node.
	 * @param multiNodes
	 *            An initially empty list. The list will be filled recursively with the mapped nodes
	 *            (in image node direction).
	 */
	public static void getImages(Node nodeOrigin, Set<Node> multiNodes) {
		
		// Collect all origin multi nodes:
		Rule originRule = nodeOrigin.getGraph().getRule();
		
		for (Rule subRule : originRule.getMultiRules()) {
			for (Mapping mapping : subRule.getMultiMappings()) {
				if (mapping.getOrigin() == nodeOrigin) {
					multiNodes.add(mapping.getImage());
					getImages(mapping.getImage(), multiNodes);
					break;
				}
			}
		}
	}
	
	
	/**
	 * Collects all parameters which are not embedded from the Kernel-Rule.
	 * 
	 * @param multiRule
	 *            The Multi-Rule to process.
	 * @return All (real) Multi-Rule parameters.
	 */
	public static List<Parameter> getMultiRuleParameters(Rule multiRule) {
		
		List<Parameter> multiParameters = new ArrayList<Parameter>();
		
		for (Parameter parameter : multiRule.getParameters()) {
			if (multiRule.getKernelRule().getParameter(parameter.getName()) == null) {
				multiParameters.add(parameter);
			}
		}
		
		return multiParameters;
	}
	
	/**
	 * Collects all mappings of << preserve >> nodes which are not mapped to the Kernel-Rule.
	 * 
	 * @param multiRule
	 *            The Multi-Rule to process.
	 * @return All (real) Multi-Rule mappings of << preserve >> nodes.
	 */
	public static List<Mapping> getMultiRulePreservedNodes(Rule multiRule) {
		
		List<Mapping> multiRulePresevedNodesMapping = new ArrayList<Mapping>();
		
		for (Mapping mapping : multiRule.getMappings()) {
			if ((findMappingByImage(multiRule.getMultiMappings(), mapping.getImage()) == null)
					&& (findMappingByImage(multiRule.getMultiMappings(), mapping.getOrigin()) == null)) {
				multiRulePresevedNodesMapping.add(mapping);
			}
		}
		
		return multiRulePresevedNodesMapping;
	}

	/**
	 * Collects all nodes which are not mapped to the Kernel-Rule.
	 * 
	 * @param multiRule
	 *            The Multi-Rule to process.
	 * @return All (real) Multi-Rule nodes.
	 */
	public static List<Node> getMultiRuleNodes(Rule multiRule) {
		
		List<Node> multiNodes = new ArrayList<Node>();
		
		for (Node lhsNode : multiRule.getLhs().getNodes()) {
			if (findMappingByImage(multiRule.getMultiMappings(), lhsNode) == null) {
				// Node is not mapped => Node is a Multi-Rule node:
				multiNodes.add(lhsNode);
			}
		}
		
		for (Node rhsNode : multiRule.getRhs().getNodes()) {
			if (findMappingByImage(multiRule.getMultiMappings(), rhsNode) == null) {
				// Node is not mapped => Node is a Multi-Rule node:
				multiNodes.add(rhsNode);
			}
		}
		
		return multiNodes;
	}
	
	/**
	 * Collects all edges which are not mapped to the Kernel-Rule.
	 * 
	 * @param multiRule
	 *            The Multi-Rule to process.
	 * @return All (real) Multi-Rule edges.
	 */
	public static List<Edge> getMultiRuleEdges(Rule multiRule) {
		List<Edge> multiEdges = new ArrayList<Edge>();

		for (Edge lhsEdge : multiRule.getLhs().getEdges()) {
			if ((findMappingByImage(multiRule.getMultiMappings(), lhsEdge.getSource()) == null)
					|| (findMappingByImage(multiRule.getMultiMappings(), lhsEdge.getTarget())== null)) {
				// Source or edge target is not mapped => Edge is a Multi-Rule node:
				multiEdges.add(lhsEdge);
			}
		}
		
		for (Edge rhsEdge : multiRule.getRhs().getEdges()) {
			if ((findMappingByImage(multiRule.getMultiMappings(), rhsEdge.getSource()) == null)
					|| (findMappingByImage(multiRule.getMultiMappings(), rhsEdge.getTarget())== null)) {
				// Source or edge target is not mapped => Edge is a Multi-Rule node:
				multiEdges.add(rhsEdge);
			}
		}
		
		return multiEdges;
	}
}
