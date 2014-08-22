package org.sidiff.editrule.generator.core;

import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.editrule.generator.configuration.Configuration;
import org.sidiff.editrule.generator.configuration.GlobalConstants;
import org.sidiff.editrule.generator.configuration.Configuration.OperationType;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.filter.ElementFilter;

public class ModuleInternalsApplicator {

	/**
	 * Creates a rule inside a module containing Nodes and Edges for the
	 * EClassifiers and EReferences given as arguments. How the Nodes and Edges
	 * are created depends on the OperationType. The result is a rule that
	 * does not consider consistency criteria yet (like mandatory children or
	 * neighbours).
	 * 
	 * @param module
	 *            the module that should contain the basic rule
	 * @param eRefA
	 *            reference from eClassifier to targetA, if any
	 * @param eRefB
	 *            reference from eClassifier to targetB, if any
	 * @param eClassifier
	 *            eClassifier which will be the 'selectedEObject', and might
	 *            have references to given targets.
	 * @param targetA
	 *            a targeted classifier, if any
	 * @param targetB
	 *            a targeted classifier, if any
	 * @param opType
	 *            the operationType
	 * @throws OperationTypeNotImplementedException
	 *             in case switch-case concerning opType is missing
	 */
	public static Rule createBasicRule(Module module, EReference eRefA, EClassifier eClassifier, EClassifier targetA,
			EReference eRefB, EClassifier targetB, OperationType opType) throws OperationTypeNotImplementedException {

		Rule rule = null;
		NodePair selectedNodePair = null;
		NodePair newNodePair = null;
		NodePair oldNodePair = null;
		Node rhsNode = null;

		switch (opType) {

		case SET_REFERENCE:

			// SET
			// ***************************************************************************************************/
			rule = HenshinFactory.eINSTANCE.createRule();
			rule.setActivated(true);
			rule.setName("set" + eClassifier.getName() + "_" + eRefA.getName() + GlobalConstants.TO + targetA.getName());
			rule.setDescription("Set" + eClassifier.getName() + "Ref" + eRefA.getName() + "To" + targetA.getName());
			module.getUnits().add(rule);

			// create preserved node for eClass
			selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL,
					(EClass) eClassifier);
			rhsNode = selectedNodePair.getRhsNode();

			newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWTGT, (EClass) targetA);

			// create <<create>> edge for new target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(rhsNode, newNodePair.getRhsNode(), eRefA);

			break;

		case CHANGE_REFERENCE:

			// CHANGE
			// ***************************************************************************************************/
			rule = HenshinFactory.eINSTANCE.createRule();
			rule.setActivated(true);
			rule.setName("change" + eClassifier.getName() + "_" + eRefA.getName() + GlobalConstants.TO
					+ targetA.getName());
			rule.setDescription("Change the EReference " + eRefA.getName());
			module.getUnits().add(rule);

			// create preserved node for eClass
			selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL,
					(EClass) eClassifier);

			oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.OLDTGT, (EClass) targetA);
			newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWTGT, (EClass) targetA);

			// create <<delete>> edge to old target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createDeleteEdge(selectedNodePair.getLhsNode(), oldNodePair.getLhsNode(), eRefA,
					rule);
			// create <<create>> edge for new target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(selectedNodePair.getRhsNode(), newNodePair.getRhsNode(), eRefA);

			break;

		case MOVE:

			// MOVE
			// ***************************************************************************************************/
			rule = HenshinFactory.eINSTANCE.createRule();
			rule.setActivated(true);
			rule.setName("move" + eClassifier.getName() + GlobalConstants.FROM + targetA.getName() + "_"
					+ eRefA.getName() + GlobalConstants.TO + targetB.getName() + "_" + targetB.getName() + "");
			rule.setDescription("Moves " + eClassifier.getName() + " from " + targetA.getName() + "(Reference:"
					+ eRefA.getName() + ") to" + targetB.getName() + "(Reference:" + targetB.getName() + ")");
			module.getUnits().add(rule);

			// create preserved node for eClass
			selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL,
					(EClass) eClassifier);

			oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.OLDSRC, (EClass) targetA);
			newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWSRC, (EClass) targetB);

			// create <<delete>> edge to old target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createDeleteEdge(oldNodePair.getLhsNode(), selectedNodePair.getLhsNode(), eRefA,
					rule);
			// create <<create>> edge for new target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(newNodePair.getRhsNode(), selectedNodePair.getRhsNode(), eRefB);

			break;
			
		case MOVE_UP:

			// MOVE UP
			// ***************************************************************************************************/
			rule = HenshinFactory.eINSTANCE.createRule();
			rule.setActivated(true);
			rule.setName("moveUp" + eClassifier.getName() + GlobalConstants.FROM + targetA.getName() + "_"
					+ eRefA.getName() + GlobalConstants.TO + targetB.getName() + "_" + targetB.getName() + "");
			rule.setDescription("Moves up " + eClassifier.getName() + " from " + targetA.getName() + "(Reference:"
					+ eRefA.getName() + ") to" + targetB.getName() + "(Reference:" + targetB.getName() + ")");
			module.getUnits().add(rule);

			// create preserved node for eClass
			selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL,
					(EClass) eClassifier);

			oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.OLDSRC, (EClass) targetA);
			newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWSRC, (EClass) targetB);

			// create <<delete>> edge to old target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createDeleteEdge(oldNodePair.getLhsNode(), selectedNodePair.getLhsNode(), eRefA,
					rule);
			// create <<create>> edge for new target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(newNodePair.getRhsNode(), selectedNodePair.getRhsNode(), eRefB);

			break;
			
		case MOVE_DOWN:

			// MOVE DOWN
			// ***************************************************************************************************/
			rule = HenshinFactory.eINSTANCE.createRule();
			rule.setActivated(true);
			rule.setName("moveDown" + eClassifier.getName() + GlobalConstants.FROM + targetA.getName() + "_"
					+ eRefA.getName() + GlobalConstants.TO + targetB.getName() + "_" + targetB.getName() + "");
			rule.setDescription("Moves down " + eClassifier.getName() + " from " + targetA.getName() + "(Reference:"
					+ eRefA.getName() + ") to" + targetB.getName() + "(Reference:" + targetB.getName() + ")");
			module.getUnits().add(rule);

			// create preserved node for eClass
			selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL,
					(EClass) eClassifier);

			oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.OLDSRC, (EClass) targetA);
			newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWSRC, (EClass) targetB);

			// create <<delete>> edge to old target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createDeleteEdge(oldNodePair.getLhsNode(), selectedNodePair.getLhsNode(), eRefA,
					rule);
			// create <<create>> edge for new target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(newNodePair.getRhsNode(), selectedNodePair.getRhsNode(), eRefB);

			break;

		case MOVE_REFERENCE_COMBINATION:

			// MOVE_REFERENCE_COMBINATION
			// *********************************************************************************/
			rule = HenshinFactory.eINSTANCE.createRule();
			rule.setActivated(true);
			rule.setName("move" + eClassifier.getName() + GlobalConstants.FROM + targetA.getName() + "_"
					+ eRefA.getName() + GlobalConstants.TO + targetB.getName() + "_" + targetB.getName() + "");
			rule.setDescription("Moves " + eClassifier.getName() + " from " + targetA.getName() + "(Reference:"
					+ eRefA.getName() + ") to" + targetB.getName() + "(Reference:" + targetB.getName() + ")");
			module.getUnits().add(rule);

			// create preserved node for eClass
			selectedNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL,
					(EClass) eClassifier);

			oldNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.OLDSRC, (EClass) targetA);
			newNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.NEWSRC, (EClass) targetB);

			// create <<delete>> edge to old target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createDeleteEdge(oldNodePair.getLhsNode(), selectedNodePair.getLhsNode(), eRefA,
					rule);
			// create <<create>> edge for new target for EReference and it's
			// EOpposite, if any
			HenshinRuleAnalysisUtilEx.createCreateEdge(newNodePair.getRhsNode(), selectedNodePair.getRhsNode(), eRefB);

			break;

		case CREATE:

			// CamelCasing of target-context name
			String contextName = "";
			if (targetA != null) {
				contextName = toCamelCase(targetA.getName());
			} else {
				contextName = "Model";
			}

			// Add new rule to Module
			rule = HenshinRuleAnalysisUtilEx.createRule("create" + eClassifier.getName() + GlobalConstants.IN
					+ contextName, "creates one " + eClassifier.getName() + " in the context: " + contextName, true,
					module);

			// create <<preserve>> nodes for context, if any
			String selectedName = getFreeNodeName(GlobalConstants.SEL, rule);
			Graph rhs = null;
			if (targetA != null) {
				NodePair nodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, selectedName, (EClass) targetA);
				rhs = nodePair.getRhsNode().getGraph();
			} else {
				rhs = rule.getRhs();
			}

			// Add new eClass to RHS
			String newName = getFreeNodeName(GlobalConstants.NEW, rule);
			Node newNode = HenshinRuleAnalysisUtilEx.createCreateNode(rhs, newName, (EClass) eClassifier);

			// Add necessary attributes to the new eClass node
			createAttributes((EClass) eClassifier, newNode, rule);

			// Add edge between target-context and new eClass, if any
			if (targetA != null && eRefA != null) {
				Node contextNode = null;
				for (Node n : rhs.getNodes()) {
					String nName = n.getName();
					if (nName != null && nName.equals(selectedName)) {
						contextNode = n;
					}
				}
				HenshinRuleAnalysisUtilEx.createCreateEdge(contextNode, newNode, eRefA);
			}

			break;

		default:
			throw new OperationTypeNotImplementedException(opType.toString());
		}
		return rule;
	}
	
	/**
	 * This recursive method creates mandatory children for a given EClassifier.
	 * It will create mandatory children and mandatory neighbours of the child
	 * if necessary.
	 * 
	 * @param rule
	 *            the container rule
	 * @param eClassifierInfo
	 *            The EClassifierInfo of the connectable Node
	 * @param eClassifierNode
	 *            The Node to which mandatory neighbours need to be connected
	 * @param opType
	 *            needs to now the OperationType
	 * @param reduceToSuperType
	 *            needs to know if reducedToSuperType is wished for the given
	 *            OperationType
	 * @throws OperationTypeNotImplementedException
	 */
	public static void createMandatoryChildren(Rule rule, EClassifierInfo eClassifierInfo, Node eClassifierNode,
			OperationType opType, boolean reduceToSuperType) throws OperationTypeNotImplementedException {

		for (Entry<EReference, List<EClassifier>> childEntry : eClassifierInfo.getMandatoryChildren().entrySet()) {
			EReference eRef = childEntry.getKey();

			EClassifier child = eRef.getEType();

			if (!ElementFilter.getInstance().isAllowedAsDangling(child, opType, reduceToSuperType))
				continue;

			for (int i = 0; i < eRef.getLowerBound(); i++) {

				Node newChildNode = null;
				String name = getFreeNodeName(GlobalConstants.CHILD, rule);
				// create node for mandatory child
				newChildNode = HenshinRuleAnalysisUtilEx.createCreateNode(rule.getRhs(), name, (EClass) child);
				// create edge for mandatory child
				HenshinRuleAnalysisUtilEx.createCreateEdge(eClassifierNode, newChildNode, eRef);
				// Add necessary attributes to the new eClass node
				createAttributes((EClass) child, newChildNode, rule);
				// recursively check for child's mandatories and create them
				if (EClassifierInfoManagement.getInstance().getEClassifierInfo(child).hasMandatories()) {
					createMandatoryChildren(rule,
							EClassifierInfoManagement.getInstance().getEClassifierInfo(child), newChildNode,
							opType, reduceToSuperType);
					createMandatoryNeighbours(rule,
							EClassifierInfoManagement.getInstance().getEClassifierInfo(child), newChildNode,
							opType, reduceToSuperType);
				}

			}
		}		
	}
	
	/**
	 * This recursive method creates mandatory neighbours for a given EClass. It
	 * will create mandatory children and mandatory neighbours of the neighbour
	 * if necessary.
	 * 
	 * @param rule
	 *            the container rule
	 * @param eClassifierInfo
	 *            The EClassifierInfo of the connectable Node
	 * @param eClassifierNode
	 *            The Node to which mandatory neighbours need to be connected
	 * @param opType
	 *            needs to now the OperationType
	 * @param reduceToSuperType
	 *            needs to know if reducedToSuperType is wished for the given
	 *            OperationType
	 * @throws OperationTypeNotImplementedException
	 */
	public static void createMandatoryNeighbours(Rule rule, EClassifierInfo eClassifierInfo, Node eClassifierNode,
			OperationType opType, boolean reduceToSuperType) throws OperationTypeNotImplementedException {
	
		assert (HenshinRuleAnalysisUtilEx.isCreationNode(eClassifierNode));
	
		for (Entry<EReference, List<EClassifier>> neighbourEntry : eClassifierInfo.getMandatoryNeighbours().entrySet()) {
			EReference eRef = neighbourEntry.getKey();
	
			// cancel if this reference is already available in the rule
			// at its maximum (upperbound) regarding this eClassifierNode 
			EReference eOpposite = eRef.getEOpposite();
			EList<Edge> incEOpposites = eClassifierNode.getIncoming(eOpposite);
			if(!incEOpposites.isEmpty()) {
				int currentIncomings = incEOpposites.size();
				if(eRef.getUpperBound() == currentIncomings) {
					continue;	
				}
			}
			
			assert (eRef.getLowerBound() > 0);
	
			EClass neighbour = eRef.getEReferenceType();
			if (!ElementFilter.getInstance().isAllowedAsDangling(neighbour, opType, reduceToSuperType)) {
				// cut off
				continue;
			}
	
			for (int i = 0; i < eRef.getLowerBound(); i++) {
	
				// create <<preserved>> node for mandatory neighbour
				String existingName = getFreeNodeName(GlobalConstants.EX, rule);
				NodePair preservedNeighbour = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, existingName, neighbour);
				Node rhsNeighbour = preservedNeighbour.getRhsNode();
	
				// create <<create>> edge for mandatory neighbour
				HenshinRuleAnalysisUtilEx.createCreateEdge(eClassifierNode, rhsNeighbour, eRef);
			}
		}
	}
	
	/**
	 * Converts the first letter of a string to upper case
	 * 
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String s) {
		if (Character.isLetter(s.charAt(0))) {
			char c = s.charAt(0);
			return s.replaceFirst(String.valueOf(c), String.valueOf(c).toUpperCase());
		} else {
			return s;
		}
	}
	
	/**
	 * Method that finds the next unused name for an attribute variable.
	 * Example: If a rule already contains some attribute variable for an
	 * EAttribute 'name' e.g. "Name1", it will deliver "Name2" in case there is
	 * no "Name2" and so on.
	 * 
	 * @param originalName
	 * @param rule
	 * @return free attribute variable name
	 */
	private static String getFreeAttributeName(String originalName, Rule rule) {

		originalName = toCamelCase(originalName);

		List<Attribute> allCreateAttributes = HenshinRuleAnalysisUtilEx.getCreationAttributes(rule);
		int count = 0;
		for (Attribute attrib : allCreateAttributes) {

			if (attrib.getValue().startsWith(originalName)) {
				count++;
			}
		}

		if (count != 0) {
			return originalName + String.valueOf(count);
		} else {
			return originalName;
		}
	}
	
	/**
	 * Creates attributes inside a node in respect to the node type.
	 * @param forEClass
	 * @param inEClassNode
	 * @param rule
	 */
	private static void createAttributes(EClass forEClass, Node inEClassNode, Rule rule) {

		// Add necessary attributes to the new eClass node
		for (EAttribute ea : forEClass.getEAllAttributes()) {
			// we don't want: derived, transient or unchangeable EAttributes
			if (!ea.isDerived() && !ea.isTransient() && ea.isChangeable()) {
				String eaName = getFreeAttributeName(ea.getName(), rule);

				boolean createNotRequiredAndNotIDAttributes = Configuration.getInstance().CREATE_NOT_REQUIRED_AND_NOT_ID_ATTRIBUTES;
				if (createNotRequiredAndNotIDAttributes || !createNotRequiredAndNotIDAttributes
						&& (ea.isRequired() || ea.isID())) {
					HenshinRuleAnalysisUtilEx.createCreateAttribute(inEClassNode, ea,
							toCamelCase(getFreeAttributeName(eaName, rule)));
				}
			}
		}
	}
	
	
	/**
	 * Method that finds the next unused name for a Node. Example: If a rule
	 * already contains a Node for an EClassifier 'Operation' e.g. "Operation1",
	 * it will deliver "Operation2" in case there is no "Operation2" and so on.
	 * 
	 * @param originalName
	 * @param rule
	 * @return free node name
	 */
	private static String getFreeNodeName(String originalName, Rule rule) {

		originalName = toCamelCase(originalName);

		List<Node> allNodes = HenshinRuleAnalysisUtilEx.getLHSIntersectRHSNodes(rule);
		allNodes.addAll(HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes(rule));
		allNodes.addAll(HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule));

		int count = 0;
		for (Node node : allNodes) {
			String nNode = node.getName();
			if (nNode != null && node.getName().startsWith(originalName)) {
				count++;
			}
		}

		if (count != 0) {
			return originalName + String.valueOf(count);
		} else {
			return originalName;
		}
	}
	

}
