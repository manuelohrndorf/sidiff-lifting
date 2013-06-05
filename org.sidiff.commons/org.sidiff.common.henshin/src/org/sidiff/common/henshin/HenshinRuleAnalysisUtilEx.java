package org.sidiff.common.henshin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * Utility methods for analyzing Henshin transformation rules. Extending the
 * original HenshinRuleAnalysisUtil class.
 */
public class HenshinRuleAnalysisUtilEx extends org.eclipse.emf.henshin.model.util.HenshinRuleAnalysisUtil {

	
	public static enum NodeKindSelection{
		CREATE,DELETE,PRESERVED,FORBID,ALL
		//,REQUIRED in later henshin versions
	}
	

	/**
	 * @param rule
	 * @param nodename
	 * @param isLhs
	 * @return
	 */
	public static Node getNodeByName(Rule rule, String nodename, boolean isLhs) {
		for (Node node : (isLhs) ? rule.getLhs().getNodes() : rule.getLhs().getNodes()) {
			if (nodename.equals(node.getName())) return node;
		}
		
		return null;
	}
	
	/**
	 * Creates a << preserve >> edge for the given rule.
	 * Expecting one source and one target NodePair
	 * @param rule
	 *            the Henshin rule.
	 * @param src
	 *            the source nodes of the new edge.
	 * @param tgt
	 *            the target nodes of the new edge.
	 * @param type
	 *            the type of the new edge.
	 */
	public static EdgePair createPreservedEdge(Rule rule, NodePair src, NodePair tgt, EReference type) {
		Edge lhsEdge = HenshinFactory.eINSTANCE.createEdge(src.getLhsNode(), tgt.getLhsNode(), type);
		lhsEdge.setGraph(rule.getLhs());

		Edge rhsEdge = HenshinFactory.eINSTANCE.createEdge(src.getRhsNode(), tgt.getRhsNode(), type);
		rhsEdge.setGraph(rule.getRhs());
		
		return new EdgePair(lhsEdge, rhsEdge);
	}
	
	/**
	 * Creates a << preserve >> edge for the given rule.
	 * Expecting one source and one target node
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @param src
	 *            the source node of the new edge.
	 * @param tgt
	 *            the target node of the new edge.
	 * @param type
	 *            the type of the new edge.
	 */
	public static void createPreservedEdge(Rule rule, Node src, Node tgt, EReference type) {
		Edge rhsEdge = HenshinFactory.eINSTANCE.createEdge(src, tgt, type);
		rhsEdge.setGraph(rule.getRhs());

	}

	/**
	 * Creates a << preserve >> node with an String ("attrValue") attribute.
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
	public static NodePair createPreservedNodeWithAttribute(Rule rule, String name, EClass nodeType, EAttribute attrType, String attrValue) {
		NodePair res = createPreservedNode(rule, name, nodeType);
		createPreservedAttribute(res, attrType, "\"" + attrValue + "\"");

		return res;
	}

	/**
	 * Creates a << preserve >> node.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @param name
	 *            the name of the new node
	 * @param type
	 *            the the type of new the node.
	 * @return
	 */
	public static NodePair createPreservedNode(Rule rule, String name, EClass type) {
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
	 * Creates a << preserve >> attribute for the given node, i.e. it will
	 * create a LHS attribute in an << preserve >> node.
	 * 
	 * @param node
	 *            the node of the new attribute.
	 * @param type
	 *            the type of the new attribute.
	 * @param value
	 *            the value of the new attribute.
	 */
	public static void createPreservedAttribute(NodePair node, EAttribute type, String value) {
		HenshinFactory.eINSTANCE.createAttribute(node.getLhsNode(), type, value);
		// We do not need RHS. 
		//HenshinFactory.eINSTANCE.createAttribute(node.getRhsNode(), type, value);
	}

	/**
	 * Creates a << create >> node.
	 * 
	 * @param graph
	 *            the graph under which the node should be created.
	 * @param name
	 *            the name of the new node.
	 * @param type
	 *            the type of the new node.
	 * @return the new node.
	 */
	public static Node createCreateNode(Graph graph, String name, EClass type) {

		Node newNode = HenshinFactory.eINSTANCE.createNode(graph, type);
		newNode.setName(name);

		return newNode;
	}

	/**
	 * Creates a << create >> edge between nodes and automatically for its
	 * opposite EReference.
	 * 
	 * @param from
	 *            the source node.
	 * @param to
	 *            the target node.
	 * @param eRef
	 *            the EReference.
	 */
	public static void createCreateEdge(Node from, Node to, EReference eRef) {

		HenshinFactory.eINSTANCE.createEdge(from, to, eRef);
		
		if(eRef.getEOpposite()!=null) {
			HenshinFactory.eINSTANCE.createEdge(to, from, eRef.getEOpposite()); 
		}
	}

	/**
	 * Creates a << create >> attribute under a node and sets a name for the
	 * attribute value placeholder.
	 * 
	 * @param node
	 *            the node which should be given the attribute.
	 * @param type
	 *            the type of the attribute.
	 * @param value
	 *            the name for the attribute value placeholder.
	 */
	public static void createCreateAttribute(Node node, EAttribute type, String value) {

		HenshinFactory.eINSTANCE.createAttribute(node, type, value);

	}

	
	
	/**
	 * Creates a << delete >> edge between two nodes within a rule.
	 * 
	 * @param from
	 * 			 	the context node.
	 * @param to
	 * 				the target node.
	 * @param eRef
	 * 				the used EReference.
	 * @param rule
	 * 				the rule under which the nodes lie.
	 */
	public static void createDeleteEdge(Node from, Node to, EReference eRef, Rule rule) {
		
	
		Edge edge = HenshinFactory.eINSTANCE.createEdge(from, to, eRef);		
		rule.getLhs().getEdges().add(edge);
		
		if(eRef.getEOpposite()!=null) {
			Edge eOppositeEdge = HenshinFactory.eINSTANCE.createEdge(to, from, eRef.getEOpposite());
			rule.getLhs().getEdges().add(eOppositeEdge);
		}

	}
	
	
	
	
	/**
	 * Creates a << forbid >> node under a rule
	 * @param rule
	 * 				the rule which shall contain the forbid node.
	 * @param type
	 * 				the type of the forbid node.
	 * @return the created Not-Object
	 */
	public static Node createForbidNode(Rule rule, EClass type) {
		
		//TODO this method requires a differentiation of Formulas like Not, And, Or, Xor..
		
		Formula formula = rule.getLhs().getFormula();
		NestedCondition nestedCondition = null;
		Not not = null;
		
		// check if there is already a Not and a NestedCondition
		if(formula!=null) {
			not = (Not) formula; // formula is a not actually
			Formula childFormula = not.getChild();
			if(childFormula!=null) {
				nestedCondition = (NestedCondition) childFormula;
			}
		}
		// else create new Not, Graph and NestedCondition
		else{
			not = HenshinFactory.eINSTANCE.createNot();
			Graph newGraph = HenshinFactory.eINSTANCE.createGraph();
			newGraph.setName("graph");
			nestedCondition = HenshinFactory.eINSTANCE.createNestedCondition();
			nestedCondition.setConclusion(newGraph);
		}
		
		// Now create new <<forbid>> Node
		Node newNode = HenshinFactory.eINSTANCE.createNode();
		newNode.setType(type);
		newNode.setGraph(nestedCondition.getConclusion());
		newNode.setName("");
		
		nestedCondition.getConclusion().getNodes().add(newNode);
		not.setChild(nestedCondition);
		rule.getLhs().setFormula(not);

		return newNode;
	}

	/**
	 * Creates a << forbid >> edge between two nodes within a rule.
	 * 
	 * @param from
	 * 			 	the context node.
	 * @param to
	 * 				the target node.
	 * @param eRef
	 * 				the used EReference.
	 * @param rule
	 * 				the rule under which the nodes lie.
	 */
	public static Edge createForbidEdge(Node from, Node to, EReference eRef, Rule rule) {

		Edge edge = HenshinFactory.eINSTANCE.createEdge(from, to, eRef);
		
		if(rule.getLhs().getFormula() instanceof Not) {
			Not not = (Not) rule.getLhs().getFormula();
			NestedCondition nestedCond = (NestedCondition) not.getChild();
			nestedCond.getConclusion().getEdges().add(edge);
		}
		
		return edge;
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
	 * Creates a rule under a given TransformationSystem.
	 * 
	 * @param name
	 * 				the name of the rule.
	 * @param description
	 * 				the description of the rule.
	 * @param activated
	 * 				== true if rule should be activated.
	 * @param tsSystem
	 * 				the TransformationSystem which shall contain the new rule.
	 * @return the rule.
	 */
	public static Rule createRule(String name, String description, Boolean activated, Module module) {

		Rule rule = HenshinFactory.eINSTANCE.createRule();
		rule.setName(name);
		rule.setDescription(description);
		rule.setActivated(activated);
		module.getUnits().add(rule);		

		return rule;
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
	public static ParameterMapping findUnitParamterMapping(List<ParameterMapping> mapping, Parameter ruleParameter) {

		for (ParameterMapping unitMapping : mapping) {
			if ((unitMapping.getSource() == ruleParameter) || (unitMapping.getTarget() == ruleParameter)) {
				return unitMapping;
			}
		}
		return null;
	}

	
	/**
	 * Finds the corresponding preserved node in LHS to a preserved Node in RHS
	 *
	 * @param rhsNode
	 * 				the node in RHS which shall be looked up in LHS.
	 * @return the corresponding LHS node.
	 */
	public static Node findCorrespondingNodeInLHS(Node rhsNode) {
		
		Rule rule = (Rule) rhsNode.getGraph().eContainer();
		
		Node lhsNode = null;
		for(Mapping mapping: rule.getMappings()) {
			if(mapping.getImage().equals(rhsNode)) {
				lhsNode = mapping.getOrigin();
			}
		}

		return lhsNode;
	}
	
	/**
	 * Finds the corresponding preserved node in RHS to a preserved Node in LHS
	 *
	 * @param lhsNode
	 * 				the node in LHS which shall be looked up in RHS.
	 * @return the corresponding RHS node.
	 */
	public static Node findCorrespondingNodeInRHS(Node lhsNode) {
		
		Rule rule = (Rule) lhsNode.getGraph().eContainer();
		
		Node rhsNode = null;
		for(Mapping mapping: rule.getMappings()) {
			if(mapping.getOrigin().equals(lhsNode)) {
				rhsNode = mapping.getImage();
			}
		}

		return rhsNode;
	}
	
	/**
	 * Finds the corresponding preserved edge in LHS to a preserved edge in RHS
	 *
	 * @param rhsNode
	 * 				the edge in RHS which shall be looked up in LHS.
	 * @return the corresponding LHS edge.
	 */
	public static Edge findCorrespondingEdgeInLHS(Edge rhsEdge) {				
		Node lhsSrc = findCorrespondingNodeInLHS(rhsEdge.getSource());
		Node lhsTgt = findCorrespondingNodeInLHS(rhsEdge.getTarget());
		
		for (Edge lhsEdge : lhsSrc.getOutgoing()) {
			if (lhsEdge.getTarget() == lhsTgt && lhsEdge.getType() == rhsEdge.getType()){
				return lhsEdge;
			}
		}
		
		assert (false) : "No lhs node found";
		return null;
	}
	
	/**
	 * Creates a new TransformationSystem that is an inverse of an input TransformationSystem
	 * @param name
	 * 				the name for the new TransformationSystem.
	 * @param description
	 * 				the description for the new TransformationSystem..
	 * @param inputTS
	 * 				the Henshin TransformationSystem from which an inverse should be created.
	 * @return the new TransformationSystem.
	 */	
	public static Module createInverse(String name, String description, Module inputModule) {
		
		// create inverse
		Module module = EcoreUtil.copy(inputModule);
		module.setName(name);
		module.setDescription(description);
		for(Unit unit: module.getUnits()) {
			if(unit instanceof Rule){
				
				Rule r = (Rule)unit;
				Graph lhs = r.getRhs();
				lhs.setName("LHS");
				Graph rhs = r.getLhs();
				rhs.setName("RHS");
				r.setLhs(lhs);
				r.setRhs(rhs);
			
				for(Mapping m: r.getMappings()) {
					Node origin = m.getImage();
					Graph orginGraph = m.getImage().getGraph();
					origin.setGraph(orginGraph);
				
					Node image = m.getOrigin();
					Graph imageGraph = m.getOrigin().getGraph();
					image.setGraph(imageGraph);
				
					m.setImage(image);
					m.setOrigin(origin);
				
				}
			
				// remove attributes under <<delete>> nodes and their ParameterMappings
				// and not used Parameters will be deleted automatically then.
				for(Node n:r.getLhs().getNodes()) {
					List<ParameterMapping> removableMappings = new ArrayList<ParameterMapping>();
				
					if(isDeletionNode(n)) {
						for(Attribute a:n.getAttributes()) {
						
							for(ParameterMapping pm:r.getParameterMappings()) {
								if(	pm.getTarget().getName().equals(a.getValue()) ||
										pm.getSource().getName().equals(a.getValue())) {
								
									removableMappings.add(pm);
								}
							}
						}
						n.getAttributes().clear();
					}
					r.getParameterMappings().removeAll(removableMappings);
				}

			}

		}
		return module;
		
	}
	
	
	/**
	 * Returns all << delete >> edges of a rule.
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
	 * Returns all << delete >> nodes of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << delete >> nodes.
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
	 * Returns all << create >> edges of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << create >> edges.
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
	 * Returns all << create >> nodes of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << create >> nodes.
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
	 * Returns all << preserve >> edges of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << preserve >> edges.
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
	 * Returns all LHS nodes of a rule which intersects with a RHS node. These nodes are << preserve >>.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << preserve >> nodes.
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
	 * Returns all << preserve >> nodes of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << preserve >> nodes.
	 */
	public static List<NodePair> getPreservedNodes(Rule rule) {
		List<NodePair> res = new LinkedList<NodePair>();

		for (Node lhsNode : rule.getLhs().getNodes()) {
			Node rhsNode = ModelHelper.getRemoteNode(rule.getMappings(), lhsNode);

			if (rhsNode != null) {
				res.add(new NodePair(lhsNode, rhsNode));
			}
		}

		return res;
	}

	
	/**
	 * Returns all forbid nodes under a rule.
	 * @param rule
	 * 			under which to search.
	 * @return the list of forbid nodes.
	 */
	public static List<Node> getForbidNodes(Rule rule) {
		ArrayList<Node> forbidNodes = new ArrayList<Node>();
		
		// TODO: What about a formula?
		if(rule.getLhs().getFormula() instanceof Not) {
			Not not = (Not) rule.getLhs().getFormula();
			NestedCondition nestedCond = (NestedCondition) not.getChild();
			
			for(Node node : nestedCond.getConclusion().getNodes()) {
				// Mapped nodes are not part of the NAC.
				// Mapped nodes are needed e.g. <<forbid>> attribute in <<preserve>> node. 
				if(findMappingByImage(nestedCond.getMappings(), node) == null) {
					forbidNodes.add(node);	
				}	
			}
		}
		
		return forbidNodes;
	}
	
	/**
	 * Returns all << preserve >> edges of a rule.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << preserve >> edges.
	 */
	public static List<EdgePair> getPreservedEdges(Rule r) {
		List<EdgePair> res = new LinkedList<EdgePair>();
		
		for (Edge lhsEdge : r.getLhs().getEdges()) {
			Edge lhsRemoteEdge = getRemoteEdge(r.getMappings(), lhsEdge);
			if (lhsRemoteEdge != null) {
				EdgePair nextEdgePair = new EdgePair(lhsEdge, lhsRemoteEdge);
				res.add(nextEdgePair);
			}
		}

		return res;
	}
	
	public static List<Edge> getForbidEdges(Rule rule) {
		List<Edge> res = new LinkedList<Edge>();
		
		// TODO: What about a formula?
		if(rule.getLhs().getFormula() instanceof Not) {
			Not not = (Not) rule.getLhs().getFormula();
			NestedCondition nestedCond = (NestedCondition) not.getChild();
			res.addAll(nestedCond.getConclusion().getEdges());
		}
		
		return res;
	}
	
	/**
	 * Returns all << preserve >> attributes of a rule and also << delete >>
	 * attributes in a << preserve >> node. A << delete >> attribute in a <<
	 * preserve >> node acts like a << preserve >> attribute.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << preserve >> attributes (LHS).
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
	 * Returns all << create >> attributes in a << preserve >> node.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << create >> attributes.
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
	 * Returns all << delete >> attributes in a << delete >> node.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << delete >> attributes.
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
	 * Returns all << create >> attributes in a << create >> node.
	 * 
	 * @param rule
	 *            the Henshin rule.
	 * @return the << create >> attributes.
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
	 * Returns all << forbid >> attributes of a rule.
	 * 
	 * @param rule
	 * 	the Henshin rule.
	 * @return the << forbid >> attributes.
	 */
	public static List<Attribute> getForbidAttributes(Rule rule) {
		List<Attribute> res = new LinkedList<Attribute>();

		// TODO: What about a formula?
		if(rule.getLhs().getFormula() instanceof Not) {
			Not not = (Not) rule.getLhs().getFormula();
			NestedCondition nestedCond = (NestedCondition) not.getChild();
				
			for(Node node : nestedCond.getConclusion().getNodes()) {
				res.addAll(node.getAttributes());
			}
		}
		
		return res;
	}

	/**
	 * Returns the LHS/RHS attribute corresponding to the RHS/LHS attribute.
	 * 
	 * @param attribute
	 *            the known attribute.
	 * @return the corresponding remote attribute or null if it not exists.
	 */
	public static Attribute getRemoteAttribute(Attribute attribute) {
		Node node = attribute.getNode();
		Node remoteNode = ModelHelper.getRemoteNode(node.getGraph().getContainerRule().getMappings(), node);

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
	 * This method searches in LHS and RHS for Nodes with given EClass and given name.
	 * 
	 * @param rule
	 * 				the rule in which to search for nodes.
	 * @param eClass
	 * 				the type which a node must have.
	 * @param name
	 * 				the name which a node must have.
	 * @return the NodePair.
	 */
	public static NodePair getNodePair(Rule rule, EClass eClass, String name) {
		
		NodePair nodePair = null;
		Node nodeLHS = null;
		Node nodeRHS = null;
		
		for(Node n: rule.getLhs().getNodes()) {
			if(n.getType().equals(eClass) && n.getName().equals(name)) {
				nodeLHS = n;
			}
		}

		for(Node n: rule.getRhs().getNodes()) {
			if(n.getType().equals(eClass) && n.getName().equals(name)) {
				nodeRHS = n;
			}
		}
		
		nodePair = new NodePair(nodeLHS, nodeRHS);
		
		return nodePair;
	}
	
	
	/**
	 * Gets all nodes from a node list where their type equals the type argument and the eRef argument
	 * is the reference type of one connected edge.
	 * @param type
	 * 				the type a node has to match.
	 * @param eRef
	 * 				the EReference one of the node's edge types has to match.
	 * @param isIncomingERef
	 * 				true if EReference is an incoming edge for the node.
	 * @param allNodes
	 * 				list of nodes to apply match.
	 * @return list of matched nodes.
	 */
	public static List<Node> getNodesBy(EClass type, EReference eRef, Boolean isIncomingERef, List<Node> allNodes) {

		ArrayList<Node> nodeList = new ArrayList<Node>();

		for(Node n: allNodes) {

			if(n.getType().equals(type) && isIncomingERef && !n.getIncoming().isEmpty()) {			
				for(Edge e: n.getIncoming()) {
					if(e.getType().equals(eRef)) {
						nodeList.add(n);
					}
				}				
			}
			if(n.getType().equals(type) && !isIncomingERef && !n.getOutgoing().isEmpty()) {
				for(Edge e: n.getOutgoing()) {
					if(e.getType().equals(eRef)) {

						nodeList.add(n);
					}
				}			
			}

		}

		return nodeList;
	}
	
	/**
	 * Returns a list of (abstract) child nodes which are part of a containment edge reference.
	 * @param allNodes
	 * 				the input nodes to check.
	 * @param nodeKind
	 * 				the node kind which shall be looked for.
	 * @param onlyAbstracts
	 * 				if true, only containment relations with abstract children are considered.
	 * @return the nodes that match.
	 */
	public static List<Node> getChildNodesWithinAContainmentRelation(TransformationSystem ts, NodeKindSelection nodeKind, Boolean onlyAbstracts) {
	
		ArrayList<Node> nodeList = new ArrayList<Node>();
		ArrayList<Node> resultList = new ArrayList<Node>();
		
		// choose the correct nodeList
		for(Rule r: ts.getRules()) {
			switch(nodeKind) {
				case CREATE: 
					nodeList.addAll(getRHSMinusLHSNodes(r));
					break;
				case DELETE:
					nodeList.addAll(getLHSMinusRHSNodes(r));
					break;
				case PRESERVED:
					nodeList.addAll(getLHSIntersectRHSNodes(r));
					break;
				case FORBID: 
					nodeList.addAll(getForbidNodes(r));
					break;
				case ALL:
					nodeList.addAll(r.getLhs().getNodes());
					nodeList.addAll(r.getRhs().getNodes());
					break;		
			}
		}

		// search for nodes which are connected to other nodes via a composite edge
		for(Node n: nodeList) {
	
			for(Edge e: n.getIncoming()) {
				// only add nodes that are abstract children
				if(onlyAbstracts && e.getType().isContainment() && n.getType().isAbstract() && !resultList.contains(n)) {		
					resultList.add(n);
				}
				// only add nodes that are children
				else if(!onlyAbstracts && e.getType().isContainment() && !resultList.contains(n)) {
					resultList.add(n);
				}
			}				

		}
		
		return resultList;
	
	}
	
	/**
	 * Returns a list of rules that equal a given name.
	 * @param name
	 * 				the name a rule must have to match.
	 * @param ts
	 * 				the TransformationSystem under which to search.
	 * @return a list of matched rules.
	 */
	public static List<Rule> getRulesByName(String name, TransformationSystem ts) {
		ArrayList<Rule> ruleList = new ArrayList<Rule>();
		
		for(Rule r: ts.getRules()) {
			if(r.getName().equals(name)) {
				ruleList.add(r);
			}
		}
		return ruleList;
	}
	
	/**
	 * This function is copied from the Henshin ModelHelper but enhanced with a
	 * type check: An edge is only mapped if the type of the remoteEdge is also
	 * equal. Mapping of source and target node is not sufficient.
	 * 
	 * @param mappings
	 *            the node mappings of the corresponding rule.
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
				if (remoteEdge.getTarget() == remoteTargetNode && equalReferenceType(edge.getType(), remoteEdge.getType()))
					return true;
			}
		}

		return false;
	}
	
	/**
	 * Returns the LHS/RHS edge corresponding to the RHS/LHS edge.
	 * 
	 * @param mappings
	 *            the node mappings of the corresponding rule.
	 * @param edge
	 *            the known edge.
	 * @return the corresponding remote edge.
	 */
	public static Edge getRemoteEdge(List<Mapping> mappings, Edge edge) {
		Node sourceNode = edge.getSource();
		Node targetNode = edge.getTarget();

		Node remoteSourceNode = ModelHelper.getRemoteNode(mappings, sourceNode);
		Node remoteTargetNode = ModelHelper.getRemoteNode(mappings, targetNode);

		if (remoteSourceNode != null && remoteTargetNode != null) {
			for (Edge remoteEdge : remoteSourceNode.getOutgoing()) {
				if (remoteEdge.getTarget() == remoteTargetNode && equalReferenceType(edge.getType(), remoteEdge.getType()))
					return remoteEdge;
			}
		}

		return null;
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
	public static NodePair getNodeOfOutgoingEdge(NodePair source, EReference edgeType) {

		NodePair nodePair = new NodePair();

		for (Edge lhsEdge : source.getLhsNode().getOutgoing()) {
			if (equalReferenceType(lhsEdge.getType(), edgeType)) {
				nodePair.setLhsNode(lhsEdge.getTarget());
				nodePair.setRhsNode(ModelHelper.getRemoteNode(lhsEdge.getTarget().getGraph().getContainerRule().getMappings(), lhsEdge.getTarget()));
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
	public static List<NodePair> getNodeOfIncomingEdge(NodePair target, EReference edgeType) {

		List<NodePair> incoming = new LinkedList<NodePair>();

		for (Edge lhsEdge : target.getLhsNode().getIncoming()) {
			if (equalReferenceType(lhsEdge.getType(), edgeType)) {

				NodePair nodePair = new NodePair();
				nodePair.setLhsNode(lhsEdge.getSource());
				nodePair.setRhsNode(ModelHelper.getRemoteNode(lhsEdge.getSource().getGraph().getContainerRule().getMappings(), lhsEdge.getSource()));

				incoming.add(nodePair);
			}
		}

		return incoming;
	}

	public static Attribute getAttributeByType(Collection<Attribute> attributes, EAttribute type) {
		for (Attribute attribute : attributes) {
			if (attribute.getType().equals(type)) {
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
	 * Returns the amalgamation mapping of the origin node or null if no mapping exists.
	 * 
	 * @param mappings
	 *            the mappings to search.
	 * @param origin
	 *            the origin node.
	 * @param multiRule
	 *            the multi rule of the target image node.
	 * @return the node mapping or null if no mapping exists.
	 */
	public static Mapping findAUMappingByOrigin(List<Mapping> mappings, Node origin, Rule multiRule) {
		for (Mapping mapping : mappings) {
			if ((mapping.getOrigin() == origin)
					&& (mapping.getImage().getGraph().getContainerRule() == multiRule)) {
				return mapping;
			}
		}
		return null;
	}
	
	/**
	 * Returns the amalgamation mapping of the image node or null if no mapping exists.
	 * 
	 * @param mappings
	 *            the mappings to search.
	 * @param image
	 *            the image node.
	 * @param multiRule
	 *            the multi rule of the target image node.
	 * @return the node mapping or null if no mapping exists.
	 */
	public static Mapping findAUMappingByImage(List<Mapping> mappings, Node image, Rule multiRule) {
		for (Mapping mapping : mappings) {
			if ((mapping.getImage() == image)
					&& (mapping.getImage().getGraph().getContainerRule() == multiRule)) {
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
	 * Returns whether the node is << preserve >> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is << preserve >>; false otherwise.
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
	 * Returns whether the edge is << preserve >> or not.
	 * 
	 * @param edge
	 *            the edge to test.
	 * @return true if edge is << preserve >>; false otherwise.
	 */
	public static boolean isPreservedEdge(Edge edge) {

		// Edge must be connected and part of Rule
		if ((edge.getSource() != null) && (edge.getTarget() != null) && (edge.getGraph() != null) && (edge.getGraph().getContainerRule() != null)) {

			Rule rule = edge.getGraph().getContainerRule();

			// Test if edge is mapped
			if ((HenshinMappingUtil.getEdgeOrigin(edge, rule.getMappings()) != null)
					|| (HenshinMappingUtil.getEdgeImage(edge, rule.getRhs(), rule.getMappings()) != null)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Returns whether the node is << delete >> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is << delete >>; false otherwise.
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
	 * Returns whether the node is << create >> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is << create >>; false otherwise.
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
	 * Is the given attribute a << create >> attribute in a << preserve >> node.
	 * 
	 * @param attribute
	 *            the attribute to test.
	 * @return <code>true</code> if the attribute is a << create >> attribute in a << preserve >> node;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isRHSChangedAttribute(Attribute attribute) {

		// Parent node is a << preserve >> node
		if (isPreservedNode(attribute.getNode())) {

			// Attribute has no LHS
			if (getRemoteAttribute(attribute) == null) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Is the given attribute a << create >> attribute, i.e. the attribute is only in the RHS.
	 * 
	 * @param attribute
	 *            the attribute to test.
	 * @return <code>true</code> if the attribute is a << create >> attribute; <code>false</code> otherwise.
	 */
	public static boolean isCreationAttribute(Attribute attribute) {
		
		// Attribute has no LHS
		if (getRemoteAttribute(attribute) == null) {
			return true;
		}
		
		return false;
	}

	/**
	 * Returns whether the node is << forbid >> or not.
	 * 
	 * @param node
	 *            the node to test.
	 * @return true if node is << forbid >>; false otherwise.
	 */
	public static boolean isForbiddenNode(Node node) {

		// TODO: What about a formula?
		
		// Load node container
		Object container = node.getGraph().eContainer();

		// Container must be a NestedCondition
		if (!(container instanceof NestedCondition)) {
			return false;
		}

		// Nested condition that contains the node
		NestedCondition nestedCondition = (NestedCondition) container;

		if (!(nestedCondition.eContainer() instanceof Not)) {
			return false;
		}
		
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
	 * Returns whether the edge is << forbid >> or not.
	 * 
	 * @param edge
	 *            the edge to test.
	 * @return true if edge is << forbid >>; false otherwise.
	 */
	public static boolean isForbiddenEdge(Edge edge) {

		// Load edge container
		EObject nestedCondition = edge.getGraph().eContainer();

		// Container must be a NestedCondition
		if (nestedCondition instanceof NestedCondition) {
			// Container of NestedCondition must be Not
			if (nestedCondition.eContainer() instanceof Not) {
				// Edge must not be mapped to LHS
				if (!isEdgeMapped(((NestedCondition)nestedCondition).getMappings(), edge)){
					return true;
				}					
			}
		}

		return false;
	}
	
	public static boolean isForbiddenAttribute(Attribute attribute) {
		
		// Load attribute container
		EObject nestedCondition = attribute.getNode().getGraph().eContainer();

		// Container must be a NestedCondition
		if (nestedCondition instanceof NestedCondition) {
			// Container of NestedCondition must be Not
			if (nestedCondition.eContainer() instanceof Not) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Returns whether the Node is a LHS of a Rule.
	 * 
	 * @param node
	 *            the node to test.
	 * @return whether the Node is a LHS of a Rule.
	 */
	public static boolean isLHSNode(Node node) {
		if (isLHS(node.getGraph())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether the Edge is a LHS of a Rule.
	 * 
	 * @param edge
	 *            the edge to test.
	 * @return whether the Edge is a LHS of a Rule.
	 */
	public static boolean isLHSEdge(Edge edge) {
		if (isLHS(edge.getGraph())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether the Attribute is a LHS of a Rule.
	 * 
	 * @param attribute
	 *            the attribute to test.
	 * @return whether the Attribute is a LHS of a Rule.
	 */
	public static boolean isLHSAttribute(Attribute attribute) {
		return isLHSNode(attribute.getNode());
	}
	
	/**
	 * Returns whether the Node is a RHS of a Rule.
	 * 
	 * @param node
	 *            the node to test.
	 * @return whether the Node is a RHS of a Rule.
	 */
	public static boolean isRHSNode(Node node) {
		if (isRHS(node.getGraph())) {
			return true;
		}
		return false;
	}

	/**
	 * Returns whether the Edge is a RHS of a Rule.
	 * 
	 * @param edge
	 *            the edge to test.
	 * @return whether the Edge is a RHS of a Rule.
	 */
	public static boolean isRHSEdge(Edge edge) {
		if (isRHS(edge.getGraph())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Searches for the first incoming or outgoing << preserve >> edge of the given node. Returns
	 * <code> true </code> if there is a << preserve >> edge; <code> false </code> otherwise.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << preserve >> edge; <code> false </code>
	 *         otherwise.
	 */
	public static boolean isNodeWithPreservedEdges(Node node) {

		for (Edge edge : node.getIncoming()) {
			if (isPreservedEdge(edge)) {
				return true;
			}
		}
		
		for (Edge edge : node.getOutgoing()) {
			if (isPreservedEdge(edge)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for the first incoming or outgoing << delete >> edge of the given node. Returns
	 * <code> true </code> if there is a << delete >> edge; <code> false </code> otherwise.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << delete >> edge; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithDeletionEdges(Node node) {

		for (Edge edge : node.getIncoming()) {
			if (isDeletionEdge(edge)) {
				return true;
			}
		}
		
		for (Edge edge : node.getOutgoing()) {
			if (isDeletionEdge(edge)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for the first incoming or outgoing << create >> edge of the given node. Returns
	 * <code> true </code> if there is a << create >> edge; <code> false </code> otherwise.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << create >> edge; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithCreationEdges(Node node) {

		for (Edge edge : node.getIncoming()) {
			if (isCreationEdge(edge)) {
				return true;
			}
		}
		
		for (Edge edge : node.getOutgoing()) {
			if (isCreationEdge(edge)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for << create >> attribute in an node.
	 * 
	 * @param node the node to test.
	 * @return <code> true </code> if there is a << create >> attribute;
	 *         <code> false </code> otherwise.
	 */
	public static boolean isNodeWithCreationAttributes(Node node) {

		Node rhsNode = null;

		if (isRHS(node.getGraph())) {
			rhsNode = node;
		} else {
			rhsNode = ModelHelper.getRemoteNode(
					node.getGraph().getContainerRule().getMappings(), node);
		}

		for (Attribute rhsAttribute : rhsNode.getAttributes()) {

			Attribute lhsAttribute = getRemoteAttribute(rhsAttribute);

			if ((lhsAttribute == null) || !lhsAttribute.getValue().equals(rhsAttribute.getValue())) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Searches for LHS attribute in an << preserve >> node.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if there is a << preserved >> attribute;
	 *         <code> false </code> otherwise, i.e. it will
	 *         search for a LHS attribute in an << preserve >> node.
	 */
	public static boolean isNodeWithPreservedAttributes(Node node) {
		
		Node lhsNode;
		
		if(isLHSNode(node)) {
			lhsNode = node;
		} else {
			lhsNode = ModelHelper.getRemoteNode(node.getGraph().getContainerRule().getMappings(), node);
		}
		
		if((lhsNode != null) && (lhsNode.getAttributes().size() > 0)) {
			return true;
		} else {
			return false;	
		}
	}

	/**
	 * Returns <code> true </code> if the node has no edges; <code> false </code> otherwise.
	 * 
	 * @param node
	 *            the node to test.
	 * @return <code> true </code> if the node has no edges; <code> false </code> otherwise.
	 */
	public static boolean isNodeWithoutEdges(Node node) {

		if (node.getAllEdges().size() == 0) {
			return true;
		}

		return false;
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
	
	/**
	 * Returns the {@link TransformationSystem} the given unit is (recursively) 
	 * contained in. If unit is contained in no transformation system at all, this method
	 * does not throw an exception but simply returns <code>null</code>.
	 * 
	 * @param unit
	 * @return
	 */
	public TransformationSystem getContainingTransformationSystem(TransformationUnit unit){
		EObject parent = unit.eContainer();
		while ((parent != null) && !(parent instanceof TransformationSystem)){
			parent = parent.eContainer();
		}
		
		if (parent == null){
			return null;
		}
		
		// we can be sure that we have a trafo system here
		return (TransformationSystem) parent;
	}
}
