package org.eclipse.emf.henshin.editing.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.editing.utils.rulegen.ExampleBasedRuleGenerator;
import static org.eclipse.emf.henshin.editing.utils.util.HenshinModelHelper.*;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Action.Type;

/**
 * Utility methods for editing Henshin Modules.
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class HenshinEditingUtils {

	/**
	 * Cleans up unused meta-model imports.
	 * 
	 * @param module
	 *            The Henshin Module.
	 */
	public static void cleanUpImports(Module module) {
		module.getImports().clear();
		module.getImports().addAll(calculateImports(module));
	}

	/**
	 * Cleans up unused Henshin rule parameters.
	 * 
	 * @param module
	 *            The Henshin Module.
	 */
	public static void cleanUpParameters(Module module) {

		Set<String> names = new HashSet<>();

		// Collect parameter names:
		module.eAllContents().forEachRemaining(element -> {
			if (element instanceof Node) {
				names.add(((Node) element).getName());
			}

			else if (element instanceof Attribute) {
				names.add(((Attribute) element).getValue());
			}
		});

		// Remove unknown parameters:
		List<EObject> unknownParamerters = new ArrayList<>();

		module.eAllContents().forEachRemaining(element -> {
			if (element instanceof Parameter) {
				if (!names.contains(((Parameter) element).getName())) {
					unknownParamerters.add(element);
				}
			}
		});

		for (EObject parameter : unknownParamerters) {
			EcoreUtil.remove(parameter);
		}

		// Remove deprecated mappings:
		List<EObject> deprecatedMappings = new ArrayList<>();

		module.eAllContents().forEachRemaining(element -> {
			if (element instanceof ParameterMapping) {
				ParameterMapping mapping = (ParameterMapping) element;

				if ((mapping.getSource() == null) || (mapping.getTarget() == null)) {
					EcoreUtil.remove(mapping);
				}

				else if ((mapping.getSource().eContainer() == null) || (mapping.getTarget().eContainer() == null)) {
					deprecatedMappings.add(mapping);
				}
			}
		});

		for (EObject mappings : deprecatedMappings) {
			EcoreUtil.remove(mappings);
		}
	}

	/**
	 * Constructs a Henshin rule from a pair of models, i.e. an example which
	 * declaratively demonstrates the transformation in terms of an original
	 * model A and a changed model B.<br/>
	 * 
	 * Basically, the corresponding elements in A and B will be treated as
	 * elements to be preserved by the rule, elements contained in A only will
	 * be treated as elements to be deleted, and elements contained in B only
	 * will be treated as elements to be created.<br/>
	 * 
	 * Corresponding elements in A and B are currently determined by calculating
	 * a matching using EMFCompare. This might be a variation point in the
	 * future, the basic infrastructure is designed for exchangeability of the
	 * matcher used to determine the corresponding elements in A and B.
	 * 
	 * 
	 * @param modelA
	 *            The historic model version.
	 * @param modelB
	 *            The actual model version.
	 * @return The constructed Henshin Module.
	 */
	public static Module createRuleByExample(Resource modelA, Resource modelB) {
		String name = modelA.getURI().segments()[modelA.getURI().segmentCount() - 1] + "-"
				+ modelB.getURI().segments()[modelB.getURI().segmentCount() - 1];

		// Create Module serving as rule container:
		Module module = HenshinFactory.eINSTANCE.createModule();
		module.setName(name);

		ExampleBasedRuleGenerator generator = new ExampleBasedRuleGenerator();
		Rule rule = generator.generateRule(name, modelA, modelB);

		module.getUnits().add(rule);

		return module;
	}

	/**
	 * Makes each << preserve >> and << delete >> node as abstract as possible.
	 * 
	 * @param module
	 *            The Henshin Module.
	 */
	public static void generalizeRule(Module module) {

		module.eAllContents().forEachRemaining(element -> {
			if (element instanceof Node) {
				Node node = (Node) element;

				if (node.getActionNode().getAction().getType().equals(Type.DELETE)) {
					node.setType(selectMostSpecificType(getRequiredTypes(node)));
				}

				else if ((node != node.getActionNode())
						&& (node.getActionNode().getAction().getType().equals(Type.PRESERVE))) {

					Node lhsNode = node.getActionNode();
					Set<EClass> requiredTypes = getRequiredTypes(node);
					requiredTypes.addAll(getRequiredTypes(lhsNode));

					EClass mostAbstractType = selectMostSpecificType(requiredTypes);

					node.setType(mostAbstractType);
					lhsNode.setType(mostAbstractType);
				}
			}
		});
	}

	/**
	 * Removes all non-context (preserve) nodes from an edit rule.
	 * 
	 * @param module
	 *            The Henshin Module.
	 */
	public static void reduceToMinimalRule(Module module) {

		// We identify all non-context nodes and <<preserve>> edges
		List<Node> nonContextNodes = new ArrayList<>();
		List<Edge> preserveEdges = new ArrayList<>();

		for (Iterator<EObject> iterator = module.eAllContents(); iterator.hasNext();) {
			EObject element = iterator.next();

			// Nodes
			if (element instanceof Node) {
				Node node = (Node) element;

				if (node.getActionNode().getAction().getType().equals(Type.PRESERVE)) {
					if (node.getActionNode() != node) {
						Node lhsNode = node.getActionNode();
						Node rhsNode = node;

						if (!isNodeWithActionEdges(rhsNode, Type.CREATE) && !isNodeWithActionEdges(lhsNode, Type.DELETE)
								&& !isNodeWithChangingAttributes(lhsNode, rhsNode)) {

							nonContextNodes.add(lhsNode);
							nonContextNodes.add(rhsNode);
						}
					}
				}
			}

			// Edges
			if (element instanceof Edge) {
				Edge edge = (Edge) element;

				if (edge.getActionEdge().getAction().getType().equals(Type.PRESERVE)) {
					if (edge.getActionEdge() != edge) {
						Edge lhsEdge = edge.getActionEdge();
						Edge rhsEdge = edge;

						preserveEdges.add(lhsEdge);
						preserveEdges.add(rhsEdge);
					}
				}
			}

		}

		// Then, we delete all the identified elements
		for (Edge edge : preserveEdges) {
			deleteEdge(edge);
		}

		for (Node node : nonContextNodes) {
			deleteNode(node);
		}
	}
}
