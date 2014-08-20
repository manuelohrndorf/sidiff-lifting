package org.sidiff.editrule.fixing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.henshin.INamingConventions;

public class EditRuleFixer {

	enum ParameterDirection {
		IN, OUT
	}

	/**
	 * 
	 * @param rule
	 * @return
	 */
	private static Map<Parameter, ParameterDirection> deriveInOutParameter(Rule rule) {
		HashMap<Parameter, ParameterDirection> parameterDirections = new HashMap<Parameter, ParameterDirection>();
		for (Parameter parameter : rule.getParameters()) {
			for (Node node : rule.getLhs().getNodes()) {
				if (parameter.getName().equals(node.getName())) {
					parameterDirections.put(parameter, ParameterDirection.IN);
				} else {
					for (Attribute attribute : node.getAttributes()) {
						if (attribute.getValue().equals(parameter.getName())) {
							parameterDirections.put(parameter, ParameterDirection.IN);
						}
					}
				}
			}
			for (Node node : rule.getRhs().getNodes()) {
				if (parameter.getName().equals(node.getName())) {
					if (node.getAction() != null && node.getAction().getType().equals(Action.Type.CREATE)) {
						parameterDirections.put(parameter, ParameterDirection.OUT);
					}
				} else {
					for (Attribute attribute : node.getAttributes()) {
						if (attribute.getValue().equals(parameter.getName())) {
							parameterDirections.put(parameter, ParameterDirection.IN);
						}
					}
				}
			}
		}
		return parameterDirections;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private static int deriveNodeDegree(Node node) {
		int degree = 0;
		for (Edge edge : node.getOutgoing()) {
			if (edge.getType().isContainment()) {
				degree = degree + 10;
			} else {
				degree++;
			}
		}
		return degree;
	}

	/**
	 * creates a 'mainUnit' along with all {@link Parameter}s and
	 * {@link ParameterMapping}s.
	 * 
	 * @param module
	 *            a {@link Module}
	 */
	public static void fix_mainUnit(Module module) {
		// at the moment we only consider modules with one rule
		if (HenshinModuleAnalysis.getAllKernelRules(module).size() != 1) {
			return;
		}

		Rule rule = HenshinModuleAnalysis.getAllKernelRules(module).get(0);

		// use the preserve node having the most edges for the 'selectedEObject'
		// parameter
		TreeMap<Integer, Parameter> nodeDegree = new TreeMap<Integer, Parameter>();
		for (Parameter parameter : rule.getParameters()) {
			for (Node node : rule.getParameterNodes()) {
				if (node.getAction() != null && node.getAction().getType().equals(Action.Type.PRESERVE)
						&& node.getName().equals(parameter.getName())) {
					nodeDegree.put(deriveNodeDegree(node), parameter);
					break;
				}
			}
		}

		// calculate the parameter directions
		Map<Parameter, ParameterDirection> parameterDirections = deriveInOutParameter(rule);

		// now create the 'mainUnit'
		PriorityUnit mainUnit = HenshinFactoryImpl.eINSTANCE.createPriorityUnit();
		mainUnit.setName("mainUnit");
		mainUnit.getSubUnits().add(rule);
		for (Parameter ruleParameter : rule.getParameters()) {
			Parameter unitParameter = HenshinFactoryImpl.eINSTANCE.createParameter();
			if (ruleParameter.equals(nodeDegree.lastEntry().getValue())) {
				unitParameter.setName("selectedEObject");
			} else {
				unitParameter.setName(ruleParameter.getName());
			}
			mainUnit.getParameters().add(unitParameter);
			ParameterMapping mapping = HenshinFactoryImpl.eINSTANCE.createParameterMapping();
			mainUnit.getParameterMappings().add(mapping);
			if (parameterDirections.get(ruleParameter).equals(ParameterDirection.IN)) {
				mapping.setSource(unitParameter);
				mapping.setTarget(ruleParameter);
			} else {
				mapping.setSource(ruleParameter);
				mapping.setTarget(unitParameter);
			}
		}
		module.getUnits().add(mainUnit);
	}

	// (TK, 18.08.2014): Sehr spekulativ
	// /**
	// * tries to create a mapping with a rule parameter that has an equal name
	// and is not involved in an existing mapping
	// *
	// * @param module
	// * a {@link Module}
	// * @param unitParameter
	// * a {@link Parameter} of the 'mainUnit'
	// */
	// public static void fix_mappedAllUnitParameters(Module module, Parameter
	// unitParameter){
	// Unit mainUnit = unitParameter.getUnit();
	// Rule rule = null;
	// // at the moment we only consider modules with one rule
	// for(Unit unit:module.getUnits()){
	// if(unit instanceof Rule){
	// rule = (Rule)unit;
	// break;
	// }
	// }
	// //check for a mapping candidate, i.e. a rule parameter with an equal name
	// Parameter ruleParameter = rule.getParameter(unitParameter.getName());
	// if(ruleParameter != null){
	// // if there is a candidate, we have to check if there is already a
	// mapping that contains it
	// boolean noMappingExists = true;
	// for(ParameterMapping mapping : mainUnit.getParameterMappings()){
	// if(mapping.getSource().equals(ruleParameter)||mapping.getTarget().equals(ruleParameter)){
	// noMappingExists = false;
	// break;
	// }
	// }
	// // if there is no mapping we can use the rule parameter for the mapping
	// with the unit parameter
	// if(noMappingExists){
	// Map<Parameter, ParameterDirection> parameterDirections =
	// deriveInOutParameter(rule);
	// ParameterMapping mapping =
	// HenshinFactoryImpl.eINSTANCE.createParameterMapping();
	// mainUnit.getParameterMappings().add(mapping);
	// if (parameterDirections.get(ruleParameter).equals(ParameterDirection.IN))
	// {
	// mapping.setSource(unitParameter);
	// mapping.setTarget(ruleParameter);
	// } else {
	// mapping.setSource(ruleParameter);
	// mapping.setTarget(unitParameter);
	// }
	// }
	// }
	//
	// }

	/**
	 * 
	 * @param originNode
	 */
	public static void fix_lhsBoundaries(Node originNode, Node imageNode) {
		// Incoming and outgoing edges of imageNode
		List<Edge> imageNodeEdges = new ArrayList<Edge>();
		for (Edge edge : imageNode.getOutgoing()) {
			imageNodeEdges.add(edge);
		}

		// Find nested condition, and invalid mapping
		NestedCondition nestedCondition = null;
		Mapping mapping = null;
		for (NestedCondition nc : originNode.getGraph().getNestedConditions()) {
			for (Mapping m : nc.getMappings()) {
				if ((m.getOrigin() == originNode) && m.getImage() == imageNode) {
					nestedCondition = nc;
					mapping = m;
					break;
				}
			}
			if (nestedCondition != null) {
				break;
			}
		}

		if ((nestedCondition != null) && (mapping != null)) {
			// Fix it...

			Graph graph = nestedCondition.getConclusion();

			// Delete incoming/outgoing image node edges from ac nodes
			for (Node n : graph.getNodes()) {
				n.getIncoming().removeAll(imageNodeEdges);
				n.getOutgoing().removeAll(imageNodeEdges);
			}

			// Delete incoming/outgoing image node edges from graph
			graph.getEdges().removeAll(imageNodeEdges);

			// Delete mapping
			nestedCondition.getMappings().remove(mapping);

			// Delete imageNode from graph
			graph.getNodes().remove(imageNode);
		}
	}

	/**
	 * A new mainUnit IN-parameter with the same name and the same type is
	 * created. If the given parameter is part of a multi-rule, we create the
	 * parameter also for all kernel rules, following the path to the root
	 * kernel rule.<br/>
	 * Please note that if a parameter with this name already exists for some
	 * unit following the path up to the mainUnit, no fix will be triggered.
	 * 
	 * @param parameter
	 */
	public static void fix_mappedAllRuleObjectInParameters(Module module, Parameter parameter) {
		// mainUnit
		Unit mainUnit = module.getUnit(INamingConventions.MAIN_UNIT);

		// Path to mainUnit
		Rule rule = (Rule) parameter.getUnit();
		List<Unit> pathToMainUnit = getPathToMainUnit(rule);

		// Parameter name
		String parameterName = parameter.getName();
		EClassifier parameterType = parameter.getType();

		// Fix allowed?
		// (Parameter with that name must not already exist in the mainUnit)
		if (mainUnit.getParameter(parameterName) != null) {
			return;
		}

		// Fix problem....

		Parameter ruleParameter = parameter;

		// Path to mainUnit
		for (int i = 1; i < pathToMainUnit.size() - 1; i++) {
			ruleParameter = HenshinFactoryImpl.eINSTANCE.createParameter();
			ruleParameter.setName(parameterName);
			ruleParameter.setType(parameterType);
			pathToMainUnit.get(i).getParameters().add(ruleParameter);
		}

		// Main unit: Create and map unit parameter
		Parameter unitParameter = HenshinFactoryImpl.eINSTANCE.createParameter();
		unitParameter.setName(parameter.getName());
		unitParameter.setType(parameter.getType());
		mainUnit.getParameters().add(unitParameter);
		ParameterMapping mapping = HenshinFactoryImpl.eINSTANCE.createParameterMapping();
		mapping.setSource(unitParameter);
		mapping.setTarget(ruleParameter);
		mainUnit.getParameterMappings().add(mapping);
	}

	/**
	 * A new rule parameter (if required) and a new mainUnit OUT-parameter with
	 * the same name and the same type is created. If the node is part of a
	 * multi-rule, we create the parameter also for all kernel rules, following
	 * the path to the root kernel rule.<br/>
	 * Please note that if a parameter with this name already exists for some
	 * unit following the path up to the mainUnit, no fix will be triggered.
	 * 
	 * 
	 * @param node
	 */
	public static void fix_mappedAllCreateNodes(Module module, Node node) {
		// mainUnit
		Unit mainUnit = module.getUnit(INamingConventions.MAIN_UNIT);

		// Path to mainUnit
		Rule rule = node.getGraph().getRule();
		List<Unit> pathToMainUnit = getPathToMainUnit(rule);

		// Parameter name
		String parameterName = null;
		if (node.getName() != null && !node.getName().equals("")) {
			parameterName = node.getName();
		} else {
			parameterName = "New_" + node.getType().getName();
		}

		// Fix allowed?
		// (Parameter with that name must not already exist in the mainUnit)
		if (mainUnit.getParameter(parameterName) != null) {
			return;
		}

		// Fix problem....

		// First rule
		Parameter ruleParameter = rule.getParameter(parameterName);
		if (ruleParameter == null) {
			ruleParameter = HenshinFactoryImpl.eINSTANCE.createParameter();
			ruleParameter.setName(parameterName);
			rule.getParameters().add(ruleParameter);
			node.setName(parameterName);
		}

		// Path to mainUnit
		for (int i = 1; i < pathToMainUnit.size() - 1; i++) {
			ruleParameter = pathToMainUnit.get(i).getParameter(parameterName);
			if (ruleParameter == null) {
				ruleParameter = HenshinFactoryImpl.eINSTANCE.createParameter();
				ruleParameter.setName(parameterName);
				pathToMainUnit.get(i).getParameters().add(ruleParameter);
			}
		}

		// Main unit: Create and map unit parameter
		Parameter unitParameter = HenshinFactoryImpl.eINSTANCE.createParameter();
		unitParameter.setName(ruleParameter.getName());
		unitParameter.setType(ruleParameter.getType());
		mainUnit.getParameters().add(unitParameter);
		ParameterMapping mapping = HenshinFactoryImpl.eINSTANCE.createParameterMapping();
		mapping.setSource(ruleParameter);
		mapping.setTarget(unitParameter);
		mainUnit.getParameterMappings().add(mapping);
	}

	private static List<Unit> getPathToMainUnit(Rule rule) {
		List<Unit> res = new ArrayList<Unit>();
		res.add(rule);
		Rule current = rule;
		while (current.getKernelRule() != null) {
			res.add(current.getKernelRule());
			current = current.getKernelRule();
		}

		// Finally add mainUnit
		res.add(current.getModule().getUnit(INamingConventions.MAIN_UNIT));

		return res;
	}

	/**
	 * 
	 * @param multiRule
	 * @param parameter
	 */
	public static void fix_multiRuleParameterEmbedding(Rule multiRule, Parameter parameter) {

		if (multiRule.getParameter(parameter.getName()) == null) {
			// parameter must not yet exist...

			if (multiRule.getLhs().getNode(parameter.getName()) != null
					|| multiRule.getRhs().getNode(parameter.getName()) != null) {
				// ... but a node with that name must exist

				// Do the fix
				Parameter multiParameter = HenshinFactoryImpl.eINSTANCE.createParameter();
				multiParameter.setName(parameter.getName());
				multiParameter.setType(parameter.getType());
				multiRule.getParameters().add(multiParameter);

				// maybe now we also have to fix nested multi rules
				for (Rule nestedMultiRule : multiRule.getMultiRules()) {
					fix_multiRuleParameterEmbedding(nestedMultiRule, multiParameter);
				}
			}

		}

	}

	/**
	 * 
	 * @param multiRule
	 * @param node
	 * @param attribute
	 */
	public static void fix_multiRuleAttributeEmbedding(Rule multiRule, Node node, Attribute attribute) {
		for (Mapping mapping : multiRule.getMultiMappings()) {
			if (mapping.getOrigin().equals(node)) {
				Node image = mapping.getImage();
				Attribute eObject = EcoreUtil.copy(attribute);
				image.getAttributes().add(eObject);
			}
		}
	}

	/**
	 * 
	 * @param kernelRule
	 * @param multiRule
	 * @param node
	 */
	public static void fix_multiRuleNodeEmbedding(Rule kernelRule, Rule multiRule, Node node) {
		Node multiNode = EcoreUtil.copy(node);
		boolean inLHS = false;
		boolean inRHS = false;
		if (kernelRule.getLhs().getNodes().contains(node)) {
			inLHS = true;
			multiRule.getLhs().getNodes().add(multiNode);
			Mapping multiMapping = HenshinFactoryImpl.eINSTANCE.createMapping(node, multiNode);
			multiRule.getMultiMappings().add(multiMapping);
		} else if (kernelRule.getRhs().getNodes().contains(node)) {
			inRHS = true;
			multiRule.getRhs().getNodes().add(multiNode);
			Mapping multiMapping = HenshinFactoryImpl.eINSTANCE.createMapping(node, multiNode);
			multiRule.getMultiMappings().add(multiMapping);
		}

		Node originNode = null;
		Node imageNode = null;
		if (inLHS) {
			originNode = node;
			for (Mapping kernelMapping : kernelRule.getMappings()) {
				if (kernelMapping.getOrigin().equals(node)) {
					imageNode = kernelMapping.getImage();
				}
			}
		} else if (inRHS) {
			imageNode = node;
			for (Mapping kernelMapping : kernelRule.getMappings()) {
				if (kernelMapping.getImage().equals(node)) {
					originNode = kernelMapping.getOrigin();
				}
			}
		}

		Node multiOriginNode = null;
		Node multiImageNode = null;
		for (Mapping multiMapping : multiRule.getMultiMappings()) {
			if (multiMapping.getOrigin().equals(originNode)) {
				multiOriginNode = multiMapping.getImage();
			} else if (multiMapping.getOrigin().equals(imageNode)) {
				multiImageNode = multiMapping.getImage();
			}
		}

		boolean multiLhsRhsMappingExists = false;
		for (Mapping multiLhsRhsMapping : multiRule.getMappings()) {
			if (multiLhsRhsMapping.getOrigin().equals(multiOriginNode)
					&& multiLhsRhsMapping.getImage().equals(multiImageNode)) {
				multiLhsRhsMappingExists = true;
				break;
			}
		}

		if (!multiLhsRhsMappingExists && multiOriginNode != null && multiImageNode != null) {
			Mapping multiLhsRhsMapping = HenshinFactoryImpl.eINSTANCE.createMapping();
			multiLhsRhsMapping.setOrigin(multiOriginNode);
			multiLhsRhsMapping.setImage(multiImageNode);
			multiRule.getMappings().add(multiLhsRhsMapping);
		}
	}

	public static void fix_consistentEOpposite(Edge edge) {
		EReference edgeType = edge.getType();
		EReference oppositeEdgeType = edgeType.getEOpposite();
		HenshinFactoryImpl.eINSTANCE.createEdge(edge.getTarget(), edge.getSource(), oppositeEdgeType);

	}

	// (TK, 18.08.2014): Sehr spekulativ
	// public static void fix_parameterTargets(Parameter parameter) {
	// Unit unit = parameter.getUnit();
	// Unit mainUnit = unit.getModule().getUnit("mainUnit");
	// ParameterMapping mappingToBeRemoved = null;
	// Parameter unitParameterToBeRemoved = null;
	// for(ParameterMapping mapping : mainUnit.getParameterMappings()){
	// if(mapping.getSource().equals(parameter)){
	// mappingToBeRemoved = mapping;
	// unitParameterToBeRemoved = mapping.getTarget();
	// break;
	// }else if(mapping.getTarget().equals(parameter)){
	// mappingToBeRemoved = mapping;
	// unitParameterToBeRemoved = mapping.getSource();
	// }
	// }
	// if(mappingToBeRemoved !=null){
	// mainUnit.getParameterMappings().remove(mappingToBeRemoved);
	// mainUnit.getParameters().remove(unitParameterToBeRemoved);
	// }
	// unit.getParameters().remove(parameter);
	//
	// }

}
