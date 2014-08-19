package org.sidiff.difference.lifting.henshin.edit2recognition;

import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.createPreservedAttribute;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.createPreservedEdge;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.createPreservedNode;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.createPreservedNodeWithAttribute;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.getLHSIntersectRHSEdges;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.getLHSIntersectRHSNodes;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.getLHSMinusRHSEdges;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.getRHSChangedAttributes;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.getRHSMinusLHSEdges;
import static org.sidiff.difference.lifting.henshin.util.rule.HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.sidiff.difference.lifting.henshin.util.rule.NodePair;

import differencemodel.DifferencemodelPackage;

/**
 * Transforms an edit rule into a recognition rule. A recognition rule matches
 * the atomic changes in a difference which were produced by the edit rule. The
 * recognition rule groups the atomic changes in a semantic change set.
 */
public class EditRule2RecognitionRule {

	/**
	 * The edit rule from which the recognition rule will be created.
	 */
	private Rule editRule;

	/**
	 * The recognition rule which will be created by <code>transform()</code>.
	 */
	private Rule recognitionRule;

	/**
	 * The unit which contains the input edit rule.
	 */
	private TransformationUnit containingUnit;

	/**
	 * Collects traces from edit rule nodes to the corresponding recognition
	 * rule model A nodes. We need this to resolve the source and target nodes
	 * of edges from edit rule to recognition rule.
	 * 
	 * Map <editNode, recognitionNode>
	 */
	private Map<Node, NodePair> aTraces;

	/**
	 * Collects traces from edit rule nodes to the corresponding recognition
	 * rule model B nodes. We need this to resolve the source and target nodes
	 * of edges from edit rule to recognition rule.
	 * 
	 * Map <editNode, recognitionNode>
	 */
	private Map<Node, NodePair> bTraces;

	/**
	 * The recognition rule Difference node.
	 */
	private NodePair difference;

	/**
	 * The recognition rule semantic change set node (RHS).
	 */
	private Node semanticChangeSet;

	/**
	 * Collects created changes for semantic change set.
	 */
	private List<NodePair> changes;

	/**
	 * Prevent Ecore-Type-Nodes from duplicating (EReference)
	 */
	private Map<EReference, NodePair> eReferenceTypeNodes;

	/**
	 * Prevent Ecore-Type-Nodes from duplicating (EAttribute)
	 */
	private Map<EAttribute, NodePair> eAttributeTypeNodes;

	/**
	 * Transforms an edit rule into a recognition rule. A recognition rule
	 * matches the atomic changes in a difference which were produced by the
	 * edit rule. The recognition rule groups the atomic changes in a semantic
	 * change set.
	 * 
	 * @param editRule
	 *            the edit rule which will be transformed.
	 * @param containingUnit
	 *            the unit which contains the given edit rule.
	 * @return the recognition rule
	 */
	public Rule transform(Rule editRule, TransformationUnit containingUnit) {
		this.editRule = editRule;
		this.containingUnit = containingUnit;

		// Add implicit edges to edit rule
		createImplicitEdges(editRule.getLhs().getEdges());
		createImplicitEdges(editRule.getRhs().getEdges());

		// Initialize new recognition rule
		recognitionRule = HenshinFactory.eINSTANCE.createRule();
		recognitionRule.setName(EditTS2RecognitionTS.RECOGNITION_RULE_PREFIX + editRule.getName());

		// Initialize transformation information
		aTraces = new HashMap<Node, NodePair>();
		bTraces = new HashMap<Node, NodePair>();		
		changes = new LinkedList<NodePair>();

		// Initialize Ecore-Type-Nodes lists
		eReferenceTypeNodes = new HashMap<EReference, NodePair>();
		eAttributeTypeNodes = new HashMap<EAttribute, NodePair>();		

		// Create Patterns
		createCorrespondencePatterns();		
		createAttributeValueChangePatterns();
		createPreservedReferencePattterns();
		createRemoveObjectPatterns();		
		createRemoveReferencePatterns();
		createAddObjectPattterns();		
		createAddReferencePattern();

		// Create Semantic change set and link changes to the change set
		createChangeSet(recognitionRule, editRule.getName(), changes);

		return recognitionRule;
	}

	/**
	 * Create the implicit opposite edges in the edit rule which were not
	 * defined before.
	 * 
	 * @param edges
	 *            the list of edges which will be searched for undefined
	 *            opposite edges.
	 */
	private void createImplicitEdges(List<Edge> edges) {
		List<Edge> oppositeEdges = new LinkedList<Edge>();

		for (Edge edge : edges) {
			// Check if edge (EReference type) has opposite
			if (edge.getType().getEOpposite() != null) {
				// Look for existing opposite edge
				boolean oppositeExists = false;

				for (Edge opposite : edge.getTarget().getOutgoing()) {
					if (opposite.getType() == edge.getType().getEOpposite()) {
						oppositeExists = true;
					}
				}

				// If opposite do not exist
				if (!oppositeExists) {
					// Create new edge (unlinked to graph)
					Edge oppositeEdge = HenshinFactory.eINSTANCE.createEdge();
					oppositeEdge.setSource(edge.getTarget());
					oppositeEdge.setTarget(edge.getSource());
					oppositeEdge.setType(edge.getType().getEOpposite());
					oppositeEdges.add(oppositeEdge);
				}
			}
		}

		// Link new edges to graph
		edges.addAll(oppositeEdges);
	}

	/**
	 * Creates the correspondence patterns for the recognition rule. For each
	 * preserved node in the edit rule there will be Correspondence node linked
	 * to the model A and model B node. Model A and B nodes represents the
	 * preserved node in the edit rule.
	 */
	private void createCorrespondencePatterns() {

		// Intersection of edit rule LHS and RHS <<preserve>> node
		for (Node p : getLHSIntersectRHSNodes(editRule)) {

			// Create model A node
			NodePair p_a = createPreservedNode(recognitionRule, "A." + p.getName(), p.getType());
			// Save edit to recognition trace
			// (LHS/RHS-Edit-Node -> NodePair-ModelA)
			aTraces.put(p, p_a);
			aTraces.put(ModelHelper.getRemoteNode(editRule.getMappings(), p), p_a);

			// Create model B node
			NodePair p_b = createPreservedNode(recognitionRule, "B." + p.getName(), p.getType());
			// Save edit to recognition trace
			// (LHS/RHS-Edit-Node -> NodePair-ModelB)
			bTraces.put(p, p_b);
			bTraces.put(ModelHelper.getRemoteNode(editRule.getMappings(), p), p_b);

			// Create correspondence node
			NodePair c = createPreservedNode(recognitionRule, "",
					DifferencemodelPackage.eINSTANCE.getCorrespondence());
			// Link correspondence node to model A and model B node
			createPreservedEdge(recognitionRule, c, p_a,
					DifferencemodelPackage.eINSTANCE.getCorrespondence_ObjA());
			createPreservedEdge(recognitionRule, c, p_b,
					DifferencemodelPackage.eINSTANCE.getCorrespondence_ObjB());
		}
	}

	/**
	 * Creates attribute value change patterns for recognition rule. There are
	 * two possibilities in the edit rule to change an attribute. First, change
	 * an attribute from a specified value to another 'value1->value2'. Second,
	 * simply set an attribute to a specified value '<<create>> value'.
	 */
	private void createAttributeValueChangePatterns() {
		// Create attribute value change pattern
		// Content check: <<create>> value
		for (Attribute attribute : getRHSChangedAttributes(editRule)) {

			// Find model A and B node for edit rule attribute
			Node lhsNode = ModelHelper.getRemoteNode(editRule.getMappings(), attribute.getNode());
			NodePair aNode = aTraces.get(lhsNode);
			NodePair bNode = bTraces.get(lhsNode);

			// Create content check for model A node (attributes for recognition
			// rule)
			String value = attribute.getValue();
			EAttribute type = attribute.getType();
			
			// Node attribute
			createPreservedAttribute(aNode, type, value);
			
			// Create attribute value change pattern (nodes for recognition
			// rule)			
			NodePair avcN = createPreservedNode(recognitionRule, "",
					DifferencemodelPackage.eINSTANCE.getAttributeValueChange());
			createPreservedEdge(recognitionRule, avcN, aNode,
					DifferencemodelPackage.eINSTANCE.getAttributeValueChange_ObjA());
			createPreservedEdge(recognitionRule, avcN, bNode,
					DifferencemodelPackage.eINSTANCE.getAttributeValueChange_ObjB());

			// Look for existing EAttribute node
			NodePair eAttribute = eAttributeTypeNodes.get(type);

			if (eAttribute == null) {
				// Create EAttribute node if it not exists
				eAttribute = createPreservedNodeWithAttribute(recognitionRule,
						"", EcorePackage.eINSTANCE.getEAttribute(),
						EcorePackage.eINSTANCE.getENamedElement_Name(), type.getName());

				eAttributeTypeNodes.put(type, eAttribute);
			}

			// Link EAttribute node to pattern
			createPreservedEdge(recognitionRule, avcN, eAttribute,
					DifferencemodelPackage.eINSTANCE.getAttributeValueChange_Type());

			// Save change for semantic change set
			changes.add(avcN);
		}
	}

	/**
	 * Creates the preserved reference pattern. That means the function will
	 * create a preserved edge between model A and model B nodes for each
	 * <<preserve>> node in the edit rule.
	 */
	private void createPreservedReferencePattterns() {

		// Intersection of edit rule LHS and RHS (preserved)
		for (Edge e : getLHSIntersectRHSEdges(editRule)) {

			// Corresponding model A node
			createPreservedEdge(recognitionRule, aTraces.get(e.getSource()),
					aTraces.get(e.getTarget()), e.getType());

			// Corresponding model B node
			createPreservedEdge(recognitionRule, bTraces.get(e.getSource()),
					bTraces.get(e.getTarget()), e.getType());
		}
	}

	/**
	 * Creates the remove object patterns. That means the function will create a
	 * RemoveObject node and a model A node for each <<delete>> node in the edit
	 * rule.
	 */
	private void createRemoveObjectPatterns() {

		// Edit rule LHS \ RHS <<delete>> node
		for (Node n : getLHSMinusRHSNodes(editRule)) {

			// RemoveObject node
			NodePair rmvN = createPreservedNode(recognitionRule, "",
					DifferencemodelPackage.eINSTANCE.getRemoveObject());

			// Corresponding model A node
			NodePair n_a = createPreservedNode(recognitionRule, "A." + n.getName(), n.getType());
			// Save edit to recognition trace
			// (LHS-Edit-Node -> NodePair-ModelA)
			aTraces.put(n, n_a);

			// Link RemoveObject node and model A node
			createPreservedEdge(recognitionRule, rmvN, aTraces.get(n),
					DifferencemodelPackage.eINSTANCE.getRemoveObject_Obj());

			// Save change for semantic change set
			changes.add(rmvN);
		}
	}

	/**
	 * Creates the remove reference patterns. That means the function will
	 * create a RemoveRefernece node linked to the corresponding model A source
	 * and target nodes. It also create and link a new EReferenc type node if
	 * necessary. Otherwise it will only link the type node if it already
	 * exists.
	 */
	private void createRemoveReferencePatterns() {

		// Edit rule LHS \ RHS <<delete>> node
		for (Edge e : getLHSMinusRHSEdges(editRule)) {
			NodePair rmvR = createPreservedNode(recognitionRule, "",
					DifferencemodelPackage.eINSTANCE.getRemoveReference());

			// Add EReference type node if necessary
			NodePair refT = eReferenceTypeNodes.get(e.getType());

			if (refT == null) {
				refT = createPreservedNodeWithAttribute(recognitionRule, "",
						EcorePackage.eINSTANCE.getEReference(),
						EcorePackage.eINSTANCE.getENamedElement_Name(), e.getType().getName());
				eReferenceTypeNodes.put(e.getType(), refT);
			}

			// Create edges
			createPreservedEdge(recognitionRule, rmvR, aTraces.get(e.getSource()),
					DifferencemodelPackage.eINSTANCE.getRemoveReference_Src());
			createPreservedEdge(recognitionRule, rmvR, aTraces.get(e.getTarget()),
					DifferencemodelPackage.eINSTANCE.getRemoveReference_Tgt());
			createPreservedEdge(recognitionRule, rmvR, refT,
					DifferencemodelPackage.eINSTANCE.getRemoveReference_Type());

			// Save change for semantic change set
			changes.add(rmvR);
		}
	}

	/**
	 * Creates the add object patterns. That means the function will create a
	 * AddObject node and a model B node for each <<create>> node in the edit
	 * rule.
	 */
	private void createAddObjectPattterns() {

		// Edit rule RHS \ LHS <<create>> node
		for (Node n : getRHSMinusLHSNodes(editRule)) {

			// AddObject node
			NodePair addN = createPreservedNode(recognitionRule, "",
					DifferencemodelPackage.eINSTANCE.getAddObject());

			// Corresponding model A node
			NodePair n_b = createPreservedNode(recognitionRule, "B." + n.getName(), n.getType());
			// Save edit to recognition trace
			// (RHS-Edit-Node -> NodePair-ModelB)
			bTraces.put(n, n_b);

			// Link AddObject node and model B node
			createPreservedEdge(recognitionRule, addN, n_b,
					DifferencemodelPackage.eINSTANCE.getAddObject_Obj());

			// Save change for semantic change set
			changes.add(addN);
		}
	}

	/**
	 * Creates the add reference patterns. That means the function will create a
	 * AddRefernece node linked to the corresponding model B source and target
	 * nodes. It also create and link a new EReferenc type node if necessary.
	 * Otherwise it will only link the type node if it already exists.
	 */
	private void createAddReferencePattern() {

		// Edit rule RHS \ LHS (added)
		for (Edge e : getRHSMinusLHSEdges(editRule)) {
			NodePair addR = createPreservedNode(recognitionRule, "",
					DifferencemodelPackage.eINSTANCE.getAddReference());

			// Add reference type node
			NodePair refT = eReferenceTypeNodes.get(e.getType());

			if (refT == null) {
				refT = createPreservedNodeWithAttribute(recognitionRule, "",
						EcorePackage.eINSTANCE.getEReference(),
						EcorePackage.eINSTANCE.getENamedElement_Name(), e.getType().getName());
				eReferenceTypeNodes.put(e.getType(), refT);
			}

			// Create edges
			createPreservedEdge(recognitionRule, addR, bTraces.get(e.getSource()),
					DifferencemodelPackage.eINSTANCE.getAddReference_Src());
			createPreservedEdge(recognitionRule, addR, bTraces.get(e.getTarget()),
					DifferencemodelPackage.eINSTANCE.getAddReference_Tgt());
			createPreservedEdge(recognitionRule, addR, refT,
					DifferencemodelPackage.eINSTANCE.getAddReference_Type());

			// Save change for semantic change set
			changes.add(addR);
		}
	}

	/**
	 * Creates a semantic change node and links it to the given atomic change
	 * nodes in the given recognition rule.
	 * 
	 * @param rule
	 *            the recognition rule.
	 * @param name
	 *            the semantic change set name.
	 * @param changes
	 *            the atomic change nodes.
	 */
	private void createChangeSet(Rule rule, String name, List<NodePair> changes) {
		// Create new semantic change set node
		Node cs = HenshinFactory.eINSTANCE.createNode(rule.getRhs(),
				DifferencemodelPackage.eINSTANCE.getSemanticChangeSet());
		
		// Name attribute
		Attribute attrName = HenshinFactory.eINSTANCE.createAttribute();
		attrName.setType(DifferencemodelPackage.eINSTANCE.getSemanticChangeSet_Name());
		attrName.setValue("\"" + name + "\"");
		attrName.setNode(cs);
		
		// Add low-level changes to semantic change set
		for (NodePair nodePair : changes) {
			HenshinFactory.eINSTANCE.createEdge(cs, nodePair.getRhsNode(),
					DifferencemodelPackage.eINSTANCE.getSemanticChangeSet_Changes());
		}

		// Add semantic change set to difference
		NodePair diff = createPreservedNode(rule, "",
				DifferencemodelPackage.eINSTANCE.getDifference());
		HenshinFactory.eINSTANCE.createEdge(diff.getRhsNode(), cs,
				DifferencemodelPackage.eINSTANCE.getDifference_ChangeSets());

		// Save nodes
		semanticChangeSet = cs;
		difference = diff;
	}

	/**
	 * @return the edit rule from which the recognition rule will be created.
	 */
	public Rule getEditRule() {
		return editRule;
	}

	/**
	 * @return the recognition rule which will be created by
	 *         <code>transform()</code>.
	 */
	public Rule getRecognitionRule() {
		return recognitionRule;
	}

	/**
	 * @return the unit which contains the given edit rule.
	 */
	public TransformationUnit getContainingUnit() {
		return containingUnit;
	}

	/**
	 * Collected traces from edit rule nodes to the corresponding recognition
	 * rule model A nodes. Map <editNode, recognitionNode>
	 * 
	 * @return the model A traces.
	 */
	public Map<Node, NodePair> getaTraces() {
		return aTraces;
	}

	/**
	 * Collected traces from edit rule nodes to the corresponding recognition
	 * rule model B nodes. Map <editNode, recognitionNode>
	 * 
	 * @return the model B traces.
	 */
	public Map<Node, NodePair> getbTraces() {
		return bTraces;
	}

	/**
	 * @return the recognition rule Difference node.
	 */
	public NodePair getDifference() {
		return difference;
	}

	/**
	 * @return the recognition rule semantic change set node.
	 */
	public Node getSemanticChangeSet() {
		return semanticChangeSet;
	}

	/**
	 * @return the atomic changes of the recognition rule semantic change set.
	 */
	public List<NodePair> getChanges() {
		return changes;
	}

	/**
	 * @return all recognition rule EReference type nodes.
	 */
	public Map<EReference, NodePair> geteReferenceTypeNodes() {
		return eReferenceTypeNodes;
	}

	/**
	 * @return all recognition rule EAttribute type nodes.
	 */
	public Map<EAttribute, NodePair> geteAttributeTypeNodes() {
		return eAttributeTypeNodes;
	}
}
