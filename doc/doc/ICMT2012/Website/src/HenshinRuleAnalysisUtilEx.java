package org.sidiff.difference.lifting.henshin.util.rule;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * Utility methods for analyzing Henshin transformation rules. Extending the
 * original HenshinRuleAnalysisUtil class.
 */
public class HenshinRuleAnalysisUtilEx extends
		org.eclipse.emf.henshin.model.util.HenshinRuleAnalysisUtil {

	/**
	 * Creates a <<preserve>> edge for the given rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @param src
	 *            the source nodes of the new edge.
	 * @param tgt
	 *            the target nodes of the new edge.
	 * @param type
	 *            the type of the new edge.
	 */
	public static void createPreservedEdge(Rule rule, NodePair src,
			NodePair tgt, EReference type) {
		Edge lhsEdge = HenshinFactory.eINSTANCE.createEdge(src.getLhsNode(),
				tgt.getLhsNode(), type);
		lhsEdge.setGraph(rule.getLhs());

		Edge rhsEdge = HenshinFactory.eINSTANCE.createEdge(src.getRhsNode(),
				tgt.getRhsNode(), type);
		rhsEdge.setGraph(rule.getRhs());
	}

	/**
	 * Creates a <<preserve>> node with an attribute.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @param name
	 *            the name of new the node.
	 * @param nodeType
	 *            the type of new the node.
	 * @param attrType
	 *            the type of the new attribute.
	 * @param attrValue
	 *            the attribute value.
	 * @return
	 */
	public static NodePair createPreservedNodeWithAttribute(Rule rule,
			String name, EClass nodeType, EAttribute attrType, String attrValue) {
		NodePair res = createPreservedNode(rule, name, nodeType);

		Attribute attrL = HenshinFactory.eINSTANCE.createAttribute();
		attrL.setType(attrType);
		attrL.setValue("\"" + attrValue + "\"");
		attrL.setNode(res.getLhsNode());

		Attribute attrR = HenshinFactory.eINSTANCE.createAttribute();
		attrR.setType(attrType);
		attrR.setValue("\"" + attrValue + "\"");
		attrR.setNode(res.getRhsNode());

		return res;
	}

	/**
	 * Creates a <<preserve>> node.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @param name
	 *            the name of the new node
	 * @param type
	 *            the the type of new the node.
	 * @return
	 */
	public static NodePair createPreservedNode(Rule rule, String name,
			EClass type) {
		Node l = HenshinFactory.eINSTANCE.createNode();
		l.setName(name);
		l.setType(type);
		l.setGraph(rule.getLhs());

		Node r = HenshinFactory.eINSTANCE.createNode();
		r.setName(name);
		r.setType(type);
		r.setGraph(rule.getRhs());

		Mapping m = HenshinFactory.eINSTANCE.createMapping(l, r);
		rule.getMappings().add(m);

		return new NodePair(l, r);
	}

	/**
	 * Creates a <<preserve>> attribute for the given node.
	 * 
	 * @param node
	 *            the node of the new attribute.
	 * @param type
	 *            the type of the new attribute.
	 * @param value
	 *            the value of the new attribute.
	 */
	public static void createPreservedAttribute(NodePair node, EAttribute type,
			String value) {
		HenshinFactory.eINSTANCE
				.createAttribute(node.getLhsNode(), type, value);
		HenshinFactory.eINSTANCE
				.createAttribute(node.getRhsNode(), type, value);
	}

	/**
	 * Creates a rule parameter if it not already exists.
	 * 
	 * @param rule
	 *            the rule of the new parameter.
	 * @param name
	 *            the name of the new parameter.
	 * @return the new parameter or null if the parameter already exists.
	 */
	public static Parameter createParameter(Rule rule, String name) {

		if (rule.getParameterByName(name) == null) {
			Parameter parameter = HenshinFactory.eINSTANCE.createParameter();
			parameter.setName(name);
			rule.getParameters().add(parameter);
			return parameter;
		}
		return null;
	}

	/**
	 * Searches for a mapped unit parameter for given rule parameter.
	 * 
	 * @param mapping
	 *            the list of parameter mappings to be searched.
	 * @param ruleParameter
	 *            the rule parameter.
	 * @return the unit parameter or null if no mapping exists.
	 */
	public static ParameterMapping findUnitParamterMapping(
			List<ParameterMapping> mapping, Parameter ruleParameter) {

		for (ParameterMapping unitMapping : mapping) {
			if ((unitMapping.getSource() == ruleParameter)
					|| (unitMapping.getTarget() == ruleParameter)) {
				return unitMapping;
			}
		}
		return null;
	}

	/**
	 * Returns all <<delete>> edges of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<delete>> edges.
	 */
	public static List<Edge> getLHSMinusRHSEdges(Rule rule) {
		List<Edge> res = new LinkedList<Edge>();
		for (Edge lhsEdge : rule.getLhs().getEdges()) {
			if (!isEdgeMapped(rule.getMappings(), lhsEdge)) {
				res.add(lhsEdge);
			}
		}

		return res;
	}

	/**
	 * Returns all <<delete>> nodes of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<delete>> nodes.
	 */
	public static List<Node> getLHSMinusRHSNodes(Rule rule) {
		List<Node> res = new LinkedList<Node>();
		for (Node lhsNode : rule.getLhs().getNodes()) {
			if (!ModelHelper.isNodeMapped(rule.getMappings(), lhsNode)) {
				res.add(lhsNode);
			}
		}

		return res;
	}

	/**
	 * Returns all <<create>> edges of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<create>> edges.
	 */
	public static List<Edge> getRHSMinusLHSEdges(Rule rule) {
		List<Edge> res = new LinkedList<Edge>();
		for (Edge rhsEdge : rule.getRhs().getEdges()) {
			if (!isEdgeMapped(rule.getMappings(), rhsEdge)) {
				res.add(rhsEdge);
			}
		}

		return res;
	}

	/**
	 * Returns all <<create>> nodes of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<create>> nodes.
	 */
	public static List<Node> getRHSMinusLHSNodes(Rule rule) {
		List<Node> res = new LinkedList<Node>();
		for (Node rhsNode : rule.getRhs().getNodes()) {
			if (!ModelHelper.isNodeMapped(rule.getMappings(), rhsNode)) {
				res.add(rhsNode);
			}
		}

		return res;
	}

	/**
	 * Returns all <<preserve>> edges of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<preserve>> edges.
	 */
	public static List<Edge> getLHSIntersectRHSEdges(Rule r) {
		List<Edge> res = new LinkedList<Edge>();
		for (Edge lhsEdge : r.getLhs().getEdges()) {
			if (isEdgeMapped(r.getMappings(), lhsEdge)) {
				res.add(lhsEdge);
			}
		}

		return res;
	}

	/**
	 * Returns all <<preserve>> nodes of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<preserve>> nodes.
	 */
	public static List<Node> getLHSIntersectRHSNodes(Rule rule) {
		List<Node> res = new LinkedList<Node>();
		for (Node lhsNode : rule.getLhs().getNodes()) {
			if (ModelHelper.isNodeMapped(rule.getMappings(), lhsNode)) {
				res.add(lhsNode);
			}
		}

		return res;
	}

	/**
	 * Returns all <<preserve>> attributes of a rule and also <<delete>>
	 * attributes in a <<preserve>> node. A <<delete>> attribute in a
	 * <<preserve>> node acts like a <<preserve>> attribute.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<preserve>> attributes.
	 */
	public static List<Attribute> getPreservedAttributes(Rule rule) {
		List<Attribute> res = new LinkedList<Attribute>();
		for (Node lhsNode : getLHSIntersectRHSNodes(rule)) {
			for (Attribute lhsAttribute : lhsNode.getAttributes()) {
				Attribute rhsAttribute = getRemoteAttribute(lhsAttribute);

				if (rhsAttribute == null) {
					// A <<delete>> attribute in a <<preserve>> node acts like a
					// <<preserve>> attribute.
					res.add(lhsAttribute);
				} else {
					if (lhsAttribute.getValue().equals(rhsAttribute.getValue())) {
						res.add(lhsAttribute);
					}
				}
			}
		}

		return res;
	}

	/**
	 * Returns all attributes of the form value1->value2.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the changing attributes.
	 */
	public static List<AttributePair> getLHStoRHSChangedAttributes(Rule rule) {
		List<AttributePair> res = new LinkedList<AttributePair>();

		for (Node lhsNode : getLHSIntersectRHSNodes(rule)) {
			for (Attribute lhsAttribute : lhsNode.getAttributes()) {
				Attribute rhsAttribute = getRemoteAttribute(lhsAttribute);

				if (rhsAttribute != null) {
					if (!lhsAttribute.getValue().equals(rhsAttribute.getValue())) {
						res.add(new AttributePair(lhsAttribute, rhsAttribute));
					}
				}
			}
		}

		return res;
	}

	/**
	 * Returns all <<create>> attributes in a <<preserve>> node.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<create>> attributes.
	 */
	public static List<Attribute> getRHSChangedAttributes(Rule rule) {
		List<Attribute> res = new LinkedList<Attribute>();

		for (Node rhsNode : rule.getRhs().getNodes()) {
			if (ModelHelper.isNodeMapped(rule.getMappings(), rhsNode)) {
				for (Attribute rhsAttribute : rhsNode.getAttributes()) {
					Attribute lhsAttribute = getRemoteAttribute(rhsAttribute);

					if (lhsAttribute == null) {
						res.add(rhsAttribute);
					}
				}
			}
		}

		return res;
	}

	/**
	 * Returns all <<create>> attributes in a <<create>> node.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<create>> attributes.
	 */
	public static List<Attribute> getDeletionAttributes(Rule rule) {
		List<Attribute> res = new LinkedList<Attribute>();

		for (Node lhsNode : getLHSMinusRHSNodes(rule)) {
			for (Attribute lhsAttribute : lhsNode.getAttributes()) {
				res.add(lhsAttribute);
			}
		}

		return res;
	}

	/**
	 * Returns all <<delete>> attributes in a <<delete>> node.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the <<delete>> attributes.
	 */
	public static List<Attribute> getCreationAttributes(Rule rule) {
		List<Attribute> res = new LinkedList<Attribute>();

		for (Node rhsNode : getRHSMinusLHSNodes(rule)) {
			for (Attribute rhsAttribute : rhsNode.getAttributes()) {
				res.add(rhsAttribute);
			}
		}

		return res;
	}

	/**
	 * Returns the LHS/RHS attribute corresponding to the RHS/LHS attribute.
	 * 
	 * @param attribute
	 *            the known attribute.
	 * @return the corresponding remote attribute.
	 */
	public static Attribute getRemoteAttribute(Attribute attribute) {
		Node node = attribute.getNode();
		Node remoteNode = ModelHelper.getRemoteNode(node.getGraph()
				.getContainerRule().getMappings(), node);

		if ((node != null) && (remoteNode != null)) {
			for (Attribute remoteAttribute : remoteNode.getAttributes()) {
				if (remoteAttribute.getType() == attribute.getType()) {
					return remoteAttribute;
				}
			}
		}

		return null;
	}

	/**
	 * This function is copied from the Henshin ModelHelper but enhanced with a
	 * type check: An edge is only mapped if the type of the remoteEdge is also
	 * equal. Mapping of source and target node is not sufficient.
	 * 
	 * @param mappings
	 *            the node mappings.
	 * @param edge
	 *            the known edge.
	 * @return true if edge is mapped; false otherwise.
	 */
	public static boolean isEdgeMapped(List<Mapping> mappings, Edge edge) {
		Node sourceNode = edge.getSource();
		Node targetNode = edge.getTarget();

		Node remoteSourceNode = ModelHelper.getRemoteNode(mappings, sourceNode);
		Node remoteTargetNode = ModelHelper.getRemoteNode(mappings, targetNode);

		if (remoteSourceNode != null && remoteTargetNode != null) {
			for (Edge remoteEdge : remoteSourceNode.getOutgoing()) {
				if (remoteEdge.getTarget() == remoteTargetNode
						&& equalReferenceType(edge.getType(),
								remoteEdge.getType()))
					return true;
			}
		}

		return false;
	}

	/**
	 * Compares two EReferences. The compare differentiate between proxy and
	 * resolved EMF instances.
	 * 
	 * @param r1
	 *            the first EReference
	 * @param r2
	 *            the second EReference
	 * @return true if the types are equal; false otherwise.
	 */
	public static boolean equalReferenceType(EReference r1, EReference r2) {
		if (r1.eIsProxy() && r2.eIsProxy()) {
			return EcoreUtil.getURI(r1).equals(EcoreUtil.getURI(r2));
		} else if (!r1.eIsProxy() && !r2.eIsProxy()) {
			return r1.equals(r2);
		} else {
			return false;
		}
	}

	/**
	 * Returns the target nodes of the given outgoing edge.
	 * 
	 * @param source
	 *            the source node of the edge.
	 * @param edgeType
	 *            the EReference edge type.
	 * @return the target node.
	 */
	public static NodePair getNodeOfOutgoingEdge(NodePair source,
			EReference edgeType) {

		NodePair nodePair = new NodePair();

		for (Edge lhsEdge : source.getLhsNode().getOutgoing()) {
			if (equalReferenceType(lhsEdge.getType(), edgeType)) {
				nodePair.setLhsNode(lhsEdge.getTarget());
				nodePair.setRhsNode(ModelHelper.getRemoteNode(
						lhsEdge.getTarget().getGraph().getContainerRule().getMappings(),
						lhsEdge.getTarget()));
				break;
			}
		}

		return nodePair;
	}

	/**
	 * Returns the source nodes of the given incoming edge.
	 * 
	 * @param target
	 *            the target node of the edge.
	 * @param edgeType
	 *            the EReference edge type.
	 * @return all source node.
	 */
	public static List<NodePair> getNodeOfIncomingEdge(NodePair target,
			EReference edgeType) {

		List<NodePair> incoming = new LinkedList<NodePair>();

		for (Edge lhsEdge : target.getLhsNode().getIncoming()) {
			if (equalReferenceType(lhsEdge.getType(), edgeType)) {

				NodePair nodePair = new NodePair();
				nodePair.setLhsNode(lhsEdge.getSource());
				nodePair.setRhsNode(ModelHelper.getRemoteNode(
						lhsEdge.getSource().getGraph().getContainerRule().getMappings(),
						lhsEdge.getSource()));

				incoming.add(nodePair);
			}
		}

		return incoming;
	}
	
	public static Attribute getAttributeByType(Collection<Attribute> attributes, EAttribute type) {
		for(Attribute attribute : attributes) {
			if(attribute.getType().equals(type)) {
				return attribute;
			}
		}
		return null;
	}

	/**
	 * Returns the mapping of the image and origin node or null if no mapping
	 * exists.
	 * 
	 * @param mappings
	 *            the mappings to search.
	 * @param origin
	 *            the origin node.
	 * @param image
	 *            the image node.
	 * @return the node mapping or null if no mapping exists.
	 */
	public static Mapping findMapping(List<Mapping> mappings, Node origin, Node image) {
		for (Mapping mapping : mappings) {
			if (mapping.getImage() == image && mapping.getOrigin() == origin) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 * Returns the mapping of the origin node or null if no mapping exists.
	 * 
	 * @param mappings
	 *            the mappings to search.
	 * @param origin
	 *            the origin node.
	 * @return the node mapping or null if no mapping exists.
	 */
	public static Mapping findMappingByOrigin(List<Mapping> mappings, Node origin) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin() == origin) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 * Returns the mapping of the image node or null if no mapping exists.
	 * 
	 * @param mappings
	 *            the mappings to search.
	 * @param image
	 *            the image node.
	 * @return the node mapping or null if no mapping exists.
	 */
	public static Mapping findMappingByImage(List<Mapping> mappings, Node image) {
		for (Mapping mapping : mappings) {
			if (mapping.getImage() == image) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 * Returns the unit with the given name.
	 * 
	 * @param name
	 *            the unit name.
	 * @param units
	 *            the unit list to search.
	 * @return the unit with the given name.
	 */
	public static TransformationUnit getUnitByName(String name, List<TransformationUnit> units) {
		for (TransformationUnit unit : units) {
			if ((unit.getName() != null) && unit.getName().equalsIgnoreCase(name)) {
				return unit;
			}
		}
		return null;
	}

	/**
	 * Returns a list containing all unit of the transformation system
	 * (including all rules).
	 * 
	 * @param ts
	 *            the transformation system.
	 * @return a list containing all unit of the transformation system.
	 */
	public static List<TransformationUnit> getAllTransformationUnits(TransformationSystem ts) {
		List<TransformationUnit> units = new LinkedList<TransformationUnit>();
		units.addAll(ts.getRules());
		units.addAll(ts.getTransformationUnits());

		return units;
	}

	/**
	 * Returns whether the edge is <<preserve>> or not.
	 * 
	 * @param edge
	 *            the edge to test.
	 * @return true if edge is <<preserve>>; false otherwise.
	 */
	public static boolean isPreservedEdge(Edge edge) {

		// Edge must be connected and part of Rule
		if ((edge.getSource() != null) && (edge.getTarget() != null)
				&& (edge.getGraph() != null)
				&& (edge.getGraph().getContainerRule() != null)) {

			Rule rule = edge.getGraph().getContainerRule();

			// Test if edge is mapped
			if ((HenshinMappingUtil.getEdgeOrigin(edge, rule.getMappings()) != null)
					|| (HenshinMappingUtil.getEdgeImage(edge, rule.getRhs(),
							rule.getMappings()) != null)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns whether the edge is <<forbid>> or not.
	 * 
	 * @param edge
	 *            the edge to test.
	 * @return true if edge is <<forbid>>; false otherwise.
	 */
	public static boolean isForbiddenEdge(Edge edge) {

		// Load edge container
		Object container = edge.getGraph().eContainer();

		// Container must be a NestedCondition
		if (container instanceof NestedCondition) {
			return true;
		}

		return false;
	}

	/**
	 * Returns whether the node is <<create>> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is <<create>>; false otherwise.
	 */
	public static boolean isCreationNode(Node node) {

		// Load node container
		Object container = node.getGraph().eContainer();

		// Container must be a Rule
		if (!(container instanceof Rule)) {
			return false;
		}

		// Rule that contains the node
		Rule rule = (Rule) container;

		for (Mapping mapping : rule.getMappings()) {

			// Node is preserve node
			if (mapping.getImage() == node) {
				return false;
			}
		}

		// Node is create node
		if (rule.getRhs() == node.getGraph()) {
			return true;
		}

		// Node is delete node
		return false;
	}

	/**
	 * Returns whether the node is <<delete>> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is <<delete>>; false otherwise.
	 */
	public static boolean isDeletionNode(Node node) {

		// Load node container
		Object container = node.getGraph().eContainer();

		// Container must be a Rule
		if (!(container instanceof Rule)) {
			return false;
		}

		// Rule that contains the node
		Rule rule = (Rule) container;

		for (Mapping mapping : rule.getMappings()) {

			// Node is preserve node
			if (mapping.getOrigin() == node) {
				return false;
			}
		}

		// Node is delete node
		if (rule.getLhs() == node.getGraph()) {
			return true;
		}

		// Node is create node
		return false;
	}

	/**
	 * Returns whether the node is <<preserve>> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is <<preserve>>; false otherwise.
	 */
	public static boolean isPreservedNode(Node node) {

		// Load node container
		Object container = node.getGraph().eContainer();

		// Container must be a Rule
		if (!(container instanceof Rule)) {
			return false;
		}

		// Rule that contains the node
		Rule rule = (Rule) container;

		for (Mapping mapping : rule.getMappings()) {

			// Node is preserve node
			if ((mapping.getOrigin() == node) || (mapping.getImage() == node)) {
				return true;
			}
		}

		// Node is not a preserve node
		return false;
	}

	/**
	 * Returns whether the node is <<forbid>> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is <<forbid>>; false otherwise.
	 */
	public static boolean isForbiddenNode(Node node) {

		// Load node container
		Object container = node.getGraph().eContainer();

		// Container must be a NestedCondition
		if (!(container instanceof NestedCondition)) {
			return false;
		}

		// Nested condition that contains the node
		NestedCondition nestedCondition = (NestedCondition) container;

		for (Mapping mapping : nestedCondition.getMappings()) {

			// Forbidden node from LHS can not preserve to RHS
			if (mapping.getImage() == node) {
				return false;
			}
		}

		// Node is forbid note
		return true;
	}
	
	/**
	 * Apply a Henshin amalgamation unit and save the temporary generated rule
	 * for debugging.
	 * 
	 * @param ts
	 *            the Henshin transformation system.
	 * @param emfEngine
	 *            the Henshin EMF Engine.
	 * @param unitName
	 *            apply unit by name.
	 * @param path
	 *            save temporary amalgamation rule to this path.
	 * @return the success of the unit.
	 */
	public boolean applyAndSaveAmalgamationUnit(TransformationSystem ts, EmfEngine emfEngine,
			String unitName, String path) {

		TransformationUnit unit = ts.findUnitByName(unitName);
		UnitApplication unitApp = new UnitApplication(emfEngine, unit);
		boolean success = unitApp.execute();

		Stack<RuleApplication> appliedRules = unitApp.getAppliedRules();

		for (Iterator<RuleApplication> iterator = appliedRules.iterator(); iterator.hasNext();) {
			RuleApplication ruleApplication = (RuleApplication) iterator.next();

			// Save temporary Amalgamation Rule
			TransformationSystem newTS = HenshinFactory.eINSTANCE.createTransformationSystem();
			newTS.getRules().add(ruleApplication.getRule());

			URI uri = URI.createFileURI(new File(path).getAbsolutePath());
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = resourceSet.createResource(uri);
			resource.getContents().add((EObject) newTS);

			Map<String, Boolean> options = new HashMap<String, Boolean>();
			options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

			try {
				resource.save(options);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return success;
	}
}
