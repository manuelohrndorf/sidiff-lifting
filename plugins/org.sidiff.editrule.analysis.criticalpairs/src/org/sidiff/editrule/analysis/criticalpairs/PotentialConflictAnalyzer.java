package org.sidiff.editrule.analysis.criticalpairs;

import static org.sidiff.common.emf.access.EMFMetaAccess.isAssignableTo;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isDeletionEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isDeletionNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isForbiddenEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isForbiddenNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isLHSAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isPreservedEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isPreservedNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isRequireAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isRequireEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isRequireNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.view.ActionGraph;
import org.sidiff.common.henshin.view.EdgePair;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.editrule.analysis.criticalpairs.util.PotentialRuleConflicts;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.PotentialConflictKind;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialNodeConflict;

/**
 * Calculates all potential conflicts between two rules. This algorithm isn't
 * optimal in the sense that it may reports more potential conflicts than necessary.
 *
 * @author Manuel Ohrndorf
 * @author cpietsch
 */
public abstract class PotentialConflictAnalyzer extends AbstractAnalyzer {

	/*
	 * The >S<ource of a dependency is the >S<uccessor rule in a sequence of two rules! 
	 * Create -> Use <-> Target -> Source <-> Predecessor -> Successor 
	 * Use -> Delete <-> Target -> Source <-> Predecessor -> Successor
	 */

	/**
	 * Initializes a new potential conflict analyzer.
	 */
	public PotentialConflictAnalyzer(Set<EPackage> imports) {
		super(imports);
	}

	/**
	 * Adds all potential conflicts from the predecessor to the successor rule.
	 * 
	 * @param predecessor
	 *            The predecessor rule, i.e. the target of dependencies.
	 * @param successor
	 *            The successor rule, i.e. the source of dependencies
	 */
	protected PotentialRuleConflicts findRuleConflicts(
			ActionGraph predecessor, EditRule predecessorEditRule, 
			ActionGraph successor, EditRule successorEditRule) {
		
		/*
		 * Divide the rule
		 */
		
		// Get nodes
		List<NodePair> predecessorPreserveNodes = predecessor.getPreserveNodes();
		List<Node> predecessorCreateNodes = predecessor.getCreateNodes();
		List<Node> predecessorDeleteNodes = predecessor.getDeleteNodes();
		
		List<NodePair> successorPreserveNodes = successor.getPreserveNodes();
		List<Node> successorCreateNodes = successor.getCreateNodes();
		List<Node> successorDeleteNodes = successor.getDeleteNodes();

		// Get <<forbid>> nodes
		List<Node> predecessorForbidNodes  = predecessor.getForbidNodes();
		
		List<Node> successorForbidNodes = successor.getForbidNodes();
		
		// Get <<require>> nodes
		List<Node> predecessorRequireNodes = predecessor.getRequireNodes();
		
		List<Node> successorRequireNodes = successor.getRequireNodes();
		
		// Get edges
		List<EdgePair> predecessorPreserveEdges = predecessor.getPreserveEdges();
		List<Edge> predecessorCreateEdges = predecessor.getCreateEdges();
		List<Edge> predecessorDeleteEdges = predecessor.getDeleteEdges();
		
		List<EdgePair> successorPreserveEdges = successor.getPreserveEdges();
		List<Edge> successorCreateEdges = successor.getCreateEdges();
		List<Edge> successorDeleteEdges = successor.getDeleteEdges();
		
		// Get <<forbid>> edges
		List<Edge> predecessorForbidEdges  = predecessor.getForbidEdges();
		
		List<Edge> successorForbidEdges = successor.getForbidEdges();
		
		// Get <<require>> edges
		List<Edge> predecessorRequireEdges = predecessor.getRequireEdges();
		
		List<Edge> successorRequireEdges = successor.getRequireEdges();
		
		// Get attributes
		List<Attribute> predecessorChangingAttributes = new ArrayList<>(predecessor.getSetAttributes());
		predecessorChangingAttributes.addAll(predecessor.getChangeAttributes().stream().map(pair -> pair.getRhsAttribute()).collect(Collectors.toList()));
		List<Attribute> predecessorUsingAttributes = predecessor.getPreserveAttributes();
		
		List<Attribute> successorChangingAttributes = new ArrayList<>(successor.getSetAttributes());
		successorChangingAttributes.addAll(successor.getChangeAttributes().stream().map(pair -> pair.getRhsAttribute()).collect(Collectors.toList()));
		List<Attribute> successorUsingAttributes = successor.getPreserveAttributes(); 
		
		// Get <<forbid>> attributes
		List<Attribute> predecessorForbidAttributes = predecessor.getForbidAttributes();
		
		List<Attribute> successorForbidAttributes = successor.getForbidAttributes();
		
		// Get <<require>> attributes
		List<Attribute> predecessorRequireAttributes = predecessor.getRequireAttributes();
		
		List<Attribute> successorRequireAttributes = successor.getRequireAttributes();
		
		/*
		 *  Find all potential conflicts between the predecessor and the successor rule.
		 */
		
		// Create new potential rule conflicts
		PotentialRuleConflicts potRuleCon = new PotentialRuleConflicts();

		/*
		 *  Search node conflicts
		 */
		
		// Delete-Use (delete-delete)
		if ((!predecessorDeleteNodes.isEmpty()) && (!successorDeleteNodes.isEmpty())) {
			Set<PotentialNodeConflict> deleteDeleteNodePotCons = findDeleteDelete_Node(
					predecessorDeleteNodes, successorDeleteNodes);
			
			for (PotentialNodeConflict pnc : deleteDeleteNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(deleteDeleteNodePotCons);
		}
		
		// Delete-Use (delete-preserve)
		if ((!predecessorDeleteNodes.isEmpty()) && (!successorPreserveNodes.isEmpty())) {
			Set<PotentialNodeConflict> deletePreserveNodePotCons = findDeletePreserve_Node(
					predecessorDeleteNodes, successorPreserveNodes);
			
			for (PotentialNodeConflict pnc : deletePreserveNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(deletePreserveNodePotCons);
		}

		// Delete-Use (delete-require)
		if ((!predecessorDeleteNodes.isEmpty()) && (!successorRequireNodes.isEmpty())) {
			Set<PotentialNodeConflict> deleteRequireNodePotCons = findDeleteRequire_Node(
					predecessorDeleteNodes, successorRequireNodes);
			
			for (PotentialNodeConflict pnc : deleteRequireNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(deleteRequireNodePotCons);
		}
		
		// Create-Forbid (create-create)
		if((!predecessorCreateNodes.isEmpty()) && (!successorCreateNodes.isEmpty())) {
			Set<PotentialNodeConflict> createCreateNodePotCons = findCreateCreate_Node(predecessorCreateNodes, successorCreateNodes);
			for(PotentialNodeConflict pnc : createCreateNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(createCreateNodePotCons);
		}
		
		// Create-Forbid (create-forbid)
		if ((!predecessorCreateNodes.isEmpty()) && (!successorForbidNodes.isEmpty())) {
			Set<PotentialNodeConflict> createForbidNodePotCons = findCreateForbid_Node(
					predecessorCreateNodes, successorForbidNodes);
			
			for (PotentialNodeConflict pnc : createForbidNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(createForbidNodePotCons);
		}
		
		/*
		 * Search edge conflicts		
		 */
		
		// Delete-Use (delete-delete)
		if ((!predecessorDeleteEdges.isEmpty()) && (!successorDeleteEdges.isEmpty())) {
			Set<PotentialEdgeConflict> deleteDeleteEdgePotCons = findDeleteDelete_Edge(
					predecessorDeleteEdges, successorDeleteEdges);
			
			for (PotentialEdgeConflict pec : deleteDeleteEdgePotCons) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(deleteDeleteEdgePotCons);
		}
		
		// Delete-Use (delete-preserve)
		if ((!predecessorDeleteEdges.isEmpty()) && (!successorPreserveEdges.isEmpty())) {
			Set<PotentialEdgeConflict> deletePreserveEdgePotCons = findDeletePreserve_Edge(predecessorDeleteEdges,
					successorPreserveEdges);

			for (PotentialEdgeConflict pec : deletePreserveEdgePotCons) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(deletePreserveEdgePotCons);
		}

		// Delete-Use (delete-require)
		if ((!predecessorDeleteEdges.isEmpty()) && (!successorRequireEdges.isEmpty())) {
			Set<PotentialEdgeConflict> deleteRequireEdgePotCons = findDeleteRequire_Edge(
					predecessorDeleteEdges, successorRequireEdges);
			
			for (PotentialEdgeConflict pec : deleteRequireEdgePotCons) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(deleteRequireEdgePotCons);
		}
		
		// Create-Forbid (create-create)
		if ((!predecessorCreateEdges.isEmpty()) && (!successorCreateEdges.isEmpty())) {
			Set<PotentialEdgeConflict> createCreateEdgePotConcs = findCreateForbid_Edge(predecessorCreateEdges,
					successorForbidEdges);

			for (PotentialEdgeConflict pec : createCreateEdgePotConcs) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(createCreateEdgePotConcs);
		}
		
		// Create-Forbid (create-forbid)
		if((!predecessorCreateEdges.isEmpty()) && (!successorForbidEdges.isEmpty())) {
			Set<PotentialEdgeConflict> createForbidEdgePotConcs = findCreateForbid_Edge(predecessorCreateEdges, successorForbidEdges);
			
			for (PotentialEdgeConflict pec : createForbidEdgePotConcs) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(createForbidEdgePotConcs);
		}

		
		// Change-Use (change-preserve)
		if ((!predecessorChangingAttributes.isEmpty()) && (!successorUsingAttributes.isEmpty())) {
			Set<PotentialAttributeConflict> changePreserveAttributePotCons = findChangeUses_Attribute(
					predecessorChangingAttributes, successorUsingAttributes);
			
			for (PotentialAttributeConflict pac : changePreserveAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(changePreserveAttributePotCons);
		}
		
		// Change-Use (change-require)
		if ((!predecessorChangingAttributes.isEmpty()) && (!successorRequireAttributes.isEmpty())) {
			Set<PotentialAttributeConflict> changeRequireAttributePotConsPAC = findChangeUses_Attribute(
					predecessorChangingAttributes, successorRequireAttributes);
			
			for (PotentialAttributeConflict pac : changeRequireAttributePotConsPAC) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(changeRequireAttributePotConsPAC);
		}
		
		// Change-Change
		if ((!predecessorChangingAttributes.isEmpty()) && (!successorChangingAttributes.isEmpty())) {
			Set<PotentialAttributeConflict> changeChangeAttributePotCons = findChangeChange_Attribute(
					predecessorChangingAttributes, successorChangingAttributes);
			
			for (PotentialAttributeConflict pac : changeChangeAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(changeChangeAttributePotCons);
		}	

		return potRuleCon;
	}

	/*
	 * Nodes
	 */

	/**
	 * Checks all nodes for Delete-Use (delete-delete) conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Nodes on LHS only (<<delete>>).
	 * @param lhsSuccessors
	 *            Nodes on LHS only)  (<<delete>>).
	 * @return All potential node conflicts.
	 */
	protected Set<PotentialNodeConflict> findDeleteDelete_Node(
			Collection<Node> lhsPredecessors, Collection<Node> lhsSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : lhsPredecessors) {
			for (Node successorNode : lhsSuccessors) {
				if (isDeleteDeleteConflict(predecessorNode, successorNode)) {
					// delete-delete conflict found
					PotentialNodeConflict potCon = rbFactory.createPotentialNodeConflict();
					potCon.setSourceNode(predecessorNode);
					potCon.setTargetNode(successorNode);
					potCon.setPotentialConflictKind(PotentialConflictKind.DELETE_USE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}
	
	/**
	 * Checks two nodes for a Delete-Use (delete-delete) conflict.
	 * 
	 * @param lhsPredecessor
	 *            Node is on LHS (<<delete>>).
	 * @param lhsSuccessor
	 *            Node is LHS (<<delete>>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeleteDeleteConflict(Node lhsPredecessor, Node lhsSuccessor) {
		
		assert(isDeletionNode(lhsPredecessor)) : "Input Assertion Failed: Must be a deletion node!";
		assert(isDeletionNode(lhsSuccessor)): "Input Assertion Failed: Must be a deletion node!";
		
		/*
		 * Delete-Node-Type + Delete-Node-Sub-Types + Delete-Node-Super-Types == Delete-Node-Type
		 */

		boolean superType = lhsPredecessor.getType().getEAllSuperTypes().contains(lhsSuccessor.getType());
		boolean directType = lhsPredecessor.getType() == lhsSuccessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(lhsPredecessor.getType()).contains(lhsSuccessor.getType());
		return directType || superType || subType;
	}
	
	/**
	 * Checks all nodes for Delete-Use (delete-preserve) conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Nodes on LHS only (<<delete>>).
	 * @param successors
	 *            Nodes on LHS and RHS  ( <<preserve>>).
	 * @return All potential node conflicts.
	 */
	protected Set<PotentialNodeConflict> findDeletePreserve_Node(
			Collection<Node> lhsPredecessors, Collection<NodePair> successors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : lhsPredecessors) {
			for (NodePair successorNode : successors) {
	
				if (isDeletePreserveConflict(predecessorNode, successorNode)) {
					// delete-preserve conflict found
					PotentialNodeConflict potCon = rbFactory.createPotentialNodeConflict();
					
					potCon.setSourceNode(predecessorNode);
					potCon.setTargetNode(successorNode.getLhsNode());
					potCon.setPotentialConflictKind(PotentialConflictKind.DELETE_USE);
					
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two nodes for a Delete-Use (delete-preserve) conflict.
	 * 
	 * @param lhsPredecessor
	 *            Node is on LHS (<<delete>>).
	 * @param successor
	 *            Node is LHS and RHS (<<preserve>>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeletePreserveConflict(Node lhsPredecessor, NodePair successor) {
		
		assert(isDeletionNode(lhsPredecessor)) : "Input Assertion Failed: Must be a delition node!";
		assert(isPreservedNode((successor.getLhsNode()))): "Input Assertion Failed: Must be a preserved node!";
		
		/*
		 * Delete-Node-Type + Delete-Node-Sub-Types + Delete-Node-Super-Types == Preserve-Node-Type
		 */

		boolean superType = lhsPredecessor.getType().getEAllSuperTypes().contains(successor.getType());
		boolean directType = lhsPredecessor.getType() == successor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(lhsPredecessor.getType()).contains(successor.getType());
		return directType || superType || subType;
	}
	

	
	/**
	 * Checks all nodes for Delete-Use (delete-require) conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Nodes on LHS only (<<delete>>).
	 * @param requiredSuccessors
	 *            Nodes from PAC  (<< require >>).
	 * @return All potential conflicts.
	 */
	protected Set<PotentialNodeConflict> findDeleteRequire_Node(
			Collection<Node> lhsPredecessors, Collection<Node> requiredSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : lhsPredecessors) {
			for (Node successorNode : requiredSuccessors) {
				
				if (isDeleteRequireConflict(predecessorNode, successorNode)) {
					// delete-require conflict found
					PotentialNodeConflict potDep = rbFactory.createPotentialNodeConflict();
					
					potDep.setSourceNode(predecessorNode);
					potDep.setTargetNode(successorNode);
					potDep.setPotentialConflictKind(PotentialConflictKind.DELETE_USE);
					
					potCons.add(potDep);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two nodes for a Delete-Use (delete-require) conflict.
	 * 
	 * @param lhsPredecessor
	 *            Nodes on LHS only (<<delete>>).
	 * @param requiredSuccessor
	 *            Nodes from PAC  (<< require >>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeleteRequireConflict(Node lhsPredecessor, Node requiredSuccessor) {
		
		assert(isDeletionNode(lhsPredecessor)) : "Input Assertion Failed: Must be a deletion node!";
		assert(isRequireNode(requiredSuccessor)) : "Input Assertion Failed: Must be a required node!";
		
		/*
		 * Delete-Node-Type + Delete-Node-Sub-Types + Delete-Node-Super-Types == Require-Node-Type
		 */

		boolean superType = lhsPredecessor.getType().getEAllSuperTypes().contains(requiredSuccessor.getType());
		boolean directType = lhsPredecessor.getType() == requiredSuccessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(lhsPredecessor.getType()).contains(requiredSuccessor.getType());
		return directType || superType || subType;
	}
	
	/**
	 * Checks all nodes for Create-Forbid (create-create) conflicts.
	 * Note: This isn't a real potential conflict as an output parameter is always assigned by the rule, however it is needed to detect partial duplicates
	 * @param rhsPredecessors
	 *            Nodes on RHS only (<<create>>).
	 * @param rhsSuccessors
	 *            Nodes on RHS only  (<<create>>).
	 * @return All potential node conflicts.
	 */
	protected Set<PotentialNodeConflict> findCreateCreate_Node(
			Collection<Node> rhsPredecessors, Collection<Node> rhsSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : rhsPredecessors) {
			for (Node successorNode : rhsSuccessors) {
				if (isCreateCreateConflict(predecessorNode, successorNode)) {
					// create-create conflict found
					PotentialNodeConflict potCon = rbFactory.createPotentialNodeConflict();
					potCon.setSourceNode(predecessorNode);
					potCon.setTargetNode(successorNode);
					potCon.setPotentialConflictKind(PotentialConflictKind.CREATE_FORBID);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}
	
	/**
	 * Checks two nodes for a Create-Forbid (create-create) conflict.
	 * 
	 * @param lhsPredecessor
	 *            Node is on RHS (<<create>>).
	 * @param lhsSuccessor
	 *            Node is RHS (<<create>>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isCreateCreateConflict(Node rhsPredecessor, Node rhsSuccessor) {
		
		assert(isCreationNode(rhsPredecessor)) : "Input Assertion Failed: Must be a creation node!";
		assert(isCreationNode(rhsSuccessor)): "Input Assertion Failed: Must be a creation node!";
		
		/*
		 * Create-Node-Type == Create-Node-Type, we do not consider super and sub-types
		 */

//		boolean superType = rhsPredecessor.getType().getEAllSuperTypes().contains(rhsSuccessor.getType());
		boolean directType = rhsPredecessor.getType() == rhsSuccessor.getType();
//		boolean subType = getSubTypeIndex().getSubTypes(rhsPredecessor.getType()).contains(rhsSuccessor.getType());
//		return directType || superType || subType;
		return directType;
	}
	
	/**
	 * Checks all nodes for a Create-Forbid (create-forbid) conflict.
	 * 
	 * @param rhsPredecessors
	 * 			Nodes on RHS only (<<create>>)
	 * @param forbiddenSuccessors
	 * 			Nodes from NAC (<<forbid>>)
	 * @return All potential Create-Forbid conflicts.
	 */
	protected Set<PotentialNodeConflict> findCreateForbid_Node(
			Collection<Node> rhsPredecessors,
			Collection<Node> forbiddenSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : rhsPredecessors) {
			for (Node successorNode : forbiddenSuccessors) {
				
				if (isCreateForbidConflict(predecessorNode, successorNode)) {
					// create-forbid conflict found
					PotentialNodeConflict potDep = rbFactory.createPotentialNodeConflict();
					
					potDep.setSourceNode(predecessorNode);
					potDep.setTargetNode(successorNode);
					potDep.setPotentialConflictKind(PotentialConflictKind.CREATE_FORBID);
					
					potCons.add(potDep);
				}
			}
		}
		return potCons;
	}
	
	
	/**
	 * Checks tow nodes for a Create-Forbid (create-forbid) conflict.
	 * 
	 * @param rhsPredecessors
	 * 			Nodes on RHS only (<<create>>)
	 * @param forbiddenSuccessors
	 * 			Nodes from NAC (<<forbid>>)
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isCreateForbidConflict(Node rhsPredecessor, Node forbiddenSuccessor) {
		
		assert(isCreationNode(rhsPredecessor)) : "Input Assertion Failed: Must be a creation node!";
		assert(isForbiddenNode(forbiddenSuccessor)) : "Input Assertion Failed: Must be a forbidden node!";
		
		/*
		 * Forbid-Node-Type + Forbid-Node-Sub-Types + Forbid-Node-Super-Types == Create-Node-Type
		 */

		boolean superType = forbiddenSuccessor.getType().getEAllSuperTypes().contains(rhsPredecessor.getType());
		boolean directType = forbiddenSuccessor.getType() == rhsPredecessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(forbiddenSuccessor.getType()).contains(rhsPredecessor.getType());
		return directType || superType || subType;
	}
	

	/*
	 * Edges
	 */

	/**
	 * Checks all edges for Delete-Use (delete-delete) conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Edges on LHS only (<<delete>>).
	 * @param lhsSuccessors
	 *            Edges on LHS only (<< delete >>).
	 * @return All potential conflicts.
	 */
	protected Set<PotentialEdgeConflict> findDeleteDelete_Edge(
			List<Edge> lhsPredecessors, List<Edge> lhsSuccessors) {
		
		Set<PotentialEdgeConflict> potCons = new HashSet<>();
		for (Edge predecessorEdge : lhsPredecessors) {
			for (Edge successorEdge : lhsSuccessors) {
				if (isDeleteDeleteConflict(predecessorEdge, successorEdge)) {
					// Delete-Use dependence found
					PotentialEdgeConflict potCon = rbFactory.createPotentialEdgeConflict();
					potCon.setSourceEdge(successorEdge);
					potCon.setTargetEdge(predecessorEdge);
					potCon.setPotentialConflictKind(PotentialConflictKind.DELETE_USE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}
	
	/**
	 * Checks two edges for a Delete-Use (delete-delete)  conflict.
	 * 
	 * @param lhsPredecessor
	 *            Edge is on LHS only (<< delete >>)
	 * @param lhsSuccessor
	 *            Edge is on LHS only (<< delete >>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeleteDeleteConflict(Edge lhsPredecessor, Edge lhsSuccessor) {
		
		assert(isDeletionEdge(lhsPredecessor)) : "Input Assertion Failed: Must be a deletion edge!";
		assert(isDeletionEdge(lhsSuccessor)) : "Input Assertion Failed: Must be a deletion edge!";
		
		return lhsPredecessor.getType() == lhsSuccessor.getType();
	}
	
	/**
	 * Checks all edges for Delete-Use (delete-preserve) conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Edges on LHS only (<<delete>>).
	 * @param successors
	 *            Edges on LHS and RHS only (<<preserve>>).
	 * @return All potential conflicts.
	 */
	protected Set<PotentialEdgeConflict> findDeletePreserve_Edge(
			List<Edge> lhsPredecessors, List<EdgePair> successors) {
		
		Set<PotentialEdgeConflict> potCons = new HashSet<>();
		for (Edge predecessorEdge : lhsPredecessors) {
			
			for (EdgePair successorEdge : successors) {
				if (isDeleteUseConflict(predecessorEdge, successorEdge)) {
					// Delete-Use dependence found
					PotentialEdgeConflict potCon = rbFactory.createPotentialEdgeConflict();
					potCon.setSourceEdge(predecessorEdge);
					potCon.setTargetEdge(successorEdge.getLhsEdge());
					potCon.setPotentialConflictKind(PotentialConflictKind.DELETE_USE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}
	
	/**
	 * Checks two edges for a Delete-Use (delete-preserve) conflict.
	 * 
	 * @param lhsPredecessor
	 * 			   Edge is on LHS only (<<delete>>).
	 * @param successor
	 *             Edge is on LHS and RHS (<<preserve>>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeleteUseConflict(Edge lhsPredecessor, EdgePair successor) {
		
		assert(isDeletionEdge(lhsPredecessor)) : "Input Assertion Failed: Must be a deletion edge!";
		assert(isPreservedEdge(successor.getLhsEdge())) : "Input Assertion Failed: Must be a preserved edge!";
		
		return lhsPredecessor.getType() == successor.getType();
	}
	
	/**
	 * Checks all edges for Delete-Use (delete-require) conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Edges on LHS only (<<delete>>).
	 * @param requiredSuccessors
	 *           
	 *            Edges from PACs (<<require>>).
	 * @return All potential conflicts.
	 */
	protected Set<PotentialEdgeConflict> findDeleteRequire_Edge(
			List<Edge> lhsPredecessors, List<Edge> requiredSuccessors) {

		Set<PotentialEdgeConflict> potCons = new HashSet<>();
		for (Edge predecessorEdge : lhsPredecessors) {
			for (Edge successorEdge : requiredSuccessors) {
				if (isDeleteRequireConflict(predecessorEdge, successorEdge)) {
					// Delete-Use conflict found
					PotentialEdgeConflict potCon = rbFactory.createPotentialEdgeConflict();
					potCon.setSourceEdge(predecessorEdge);
					potCon.setTargetEdge(successorEdge);
					potCon.setPotentialConflictKind(PotentialConflictKind.DELETE_USE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}
	
	/**
	 * Checks two edges for a Delete-Use (delete-require) conflict.
	 * 
	 * @param lhsPredecessor
	 * 			   Edge is on LHS only (<<delete>>).
	 * @param successor
	 *             Edge is from PAC (<<require>>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeleteRequireConflict(Edge lhsPredecessor, Edge successor) {
		
		assert(isDeletionEdge(lhsPredecessor)) : "Input Assertion Failed: Must be a deletion edge!";
		assert(isRequireEdge(successor)) : "Input Assertion Failed: Must be a required edge!";
		
		return lhsPredecessor.getType() == successor.getType();
	}
	
	/**
	 * Checks all edges for Delete-Use (delete-require) conflicts.
	 * 
	 * @param rhsPredecessors
	 * 				Edge is on RHS only (<<create>>)
	 * @param forbidSuccessors
	 * 				Edge is from NAC (<<forbid>>)
	 * 
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected Set<PotentialEdgeConflict> findCreateForbid_Edge(
			Collection<Edge> rhsPredecessors,
			Collection<Edge> forbidSuccessors) {

		Set<PotentialEdgeConflict> potCons = new HashSet<>();
		for(Edge successorEdge : forbidSuccessors) {
			for(Edge predecessorEdge : rhsPredecessors) {
				if(isCreateForbidConflict(predecessorEdge, successorEdge)) {
					// Create-Forbid conflict found
					PotentialEdgeConflict potCon = rbFactory.createPotentialEdgeConflict();
					potCon.setSourceEdge(predecessorEdge);
					potCon.setTargetEdge(successorEdge);
					potCon.setPotentialConflictKind(PotentialConflictKind.CREATE_FORBID);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}
	
	/**
	 * Checks two edges for a Create-Forbid (create-forbid) conflict.
	 * @param rhsPredecessors
	 * 				Edge is on RHS only (<<create>>)
	 * @param forbidSuccessors
	 * 				Edge is from NAC (<<forbid>>)
	 * @param potRuleCon
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isCreateForbidConflict(Edge rhsPredecessors, Edge forbidSuccessor) {
		assert(isCreationEdge(rhsPredecessors)) : "Input Assertion Failed!";
		assert(isForbiddenEdge(forbidSuccessor)) : "Input Assertion Failed!";

		return rhsPredecessors.getType() == forbidSuccessor.getType();
	}

	/*
	 * Attributes
	 */

	/**
	 * Checks all attributes for Change-Use conflicts.
	 * 
	 * @param rhsPredecessors
	 *            Attributes on RHS only (<<create>>).
	 * @param lhsSuccessors
	 *            Attributes on LHS (<< delete / preserve >>) or from PACs (<<require>>).
	 * @return All potential conflicts.
	 */
	protected Set<PotentialAttributeConflict> findChangeUses_Attribute(
			Collection<Attribute> rhsPredecessors, Collection<Attribute> lhsSuccessors) {

		Set<PotentialAttributeConflict> potConss = new HashSet<>();
		for (Attribute rhsPredecessorAttribute : rhsPredecessors) {
			for (Attribute lhsSuccessorAttribute : lhsSuccessors) {
				if (isChangeUseConflict(rhsPredecessorAttribute, lhsSuccessorAttribute)) {
					// Change-Use dependence found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(rhsPredecessorAttribute);
					potcon.setTargetAttribute(lhsSuccessorAttribute);
					potcon.setPotentialConflictKind(PotentialConflictKind.CHANGE_USE);
					potConss.add(potcon);
				}
			}
		}
		return potConss;
	}
	
	/**
	 * Checks all attributes for Change-Change conflicts.
	 * 
	 * @param rhsPredecessors
	 *            Attributes on RHS only (<<create>>).
	 * @param rhsSuccessors
	 *            Attributes on RHS only (<<create>>).
	 * @return All potential conflicts.
	 */
	protected Set<PotentialAttributeConflict> findChangeChange_Attribute(
			Collection<Attribute> rhsPredecessors,
			Collection<Attribute> rhsSuccessors) {

		Set<PotentialAttributeConflict> potConss = new HashSet<>();
		for(Attribute rhsPredecessorAttribute : rhsPredecessors) {
			for(Attribute rhsSuccessorAttribute : rhsSuccessors) {
				if(isChangeChangeConflict(rhsPredecessorAttribute, rhsSuccessorAttribute)) {
					// Change-Change conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(rhsPredecessorAttribute);
					potcon.setTargetAttribute(rhsSuccessorAttribute);
					potcon.setPotentialConflictKind(PotentialConflictKind.CHANGE_CHANGE);
					potConss.add(potcon);
				}
			}
		}
		return potConss;
	}
	
	/**
	 * Checks two attributes for a Change-Use conflict.
	 * 
	 * @param rhsPredecessor
	 *            Attribute is on RHS only (<< create >>).
	 * @param lhsSuccessor
	 *            Attribute is on LHS (<< delete / preserve >>) or in PAC (<<require>>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isChangeUseConflict(Attribute rhsPredecessor, Attribute lhsSuccessor) {
		
		assert (isCreationAttribute(rhsPredecessor)) : "Input Assertion Failed!";
		assert (isLHSAttribute(lhsSuccessor) || isRequireAttribute(lhsSuccessor)) : "Input Assertion Failed!";

		// Attributes have the same type
		if (rhsPredecessor.getType().equals(lhsSuccessor.getType())){
			// Predecessor nodes is <<preserve>> ?
			if (isPreservedNode(rhsPredecessor.getNode())) {
				// is predecessor nodeType assignable to successor nodeType?
				if (isAssignableTo(rhsPredecessor.getNode().getType(), lhsSuccessor.getNode().getType())){
					// Attribute case differentiation precondition is not fulfilled?
					return !attributeCaseDifferentiation(rhsPredecessor, lhsSuccessor);
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Checks two attributes for a Change-Change conflict.
	 * 
	 * @param rhsPredecessor
	 *            Attribute is on RHS only (<< create >>).
	 * @param rhsSuccessor
	 *            Attribute is on RHS (<< create >>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isChangeChangeConflict(Attribute rhsPredecessor, Attribute rhsSuccessor) {
		
		assert (isCreationAttribute(rhsPredecessor)) : "Input Assertion Failed!";
		assert (isCreationAttribute(rhsSuccessor)) : "Input Assertion Failed!";

		// Attributes have the same type
		if (rhsPredecessor.getType().equals(rhsSuccessor.getType())){
			// Predecessor nodes is <<preserve>> ?
			if (isPreservedNode(rhsPredecessor.getNode()) && isPreservedNode(rhsSuccessor.getNode())) {
				// is predecessor nodeType assignable to successor nodeType?
				if (isAssignableTo(rhsPredecessor.getNode().getType(), rhsSuccessor.getNode().getType())){
					// Attribute case differentiation precondition is not fulfilled?
					return !attributeCaseDifferentiation(rhsPredecessor, rhsSuccessor);
				}
			}
		}
		
		return false;
	}

	/*
	 * Utils
	 */

	/**
	 * Tests if the given list contains a potential conflict between the two nodes.
	 * 
	 * @param potRuleCon
	 *            The list of potential conflicts.
	 * @param nodeA
	 *            The first node to test.
	 * @param nodeB
	 *            The second node to test.
	 * @return <code>true</code> if the list contains a potential conflict;
	 *         <code>false</code> otherwise.
	 */
	protected boolean hasPotentialNodeConflict(PotentialRuleConflicts potRuleDep, Node nodeA, Node nodeB) {
		for(PotentialNodeConflict potCon : potRuleDep.getPotentialNodeConflicts()) {
			if ((potCon.getSourceNode().equals(nodeA) || potCon.getTargetNode().equals(nodeA))
					&& (potCon.getSourceNode().equals(nodeB) || potCon.getTargetNode().equals(nodeB))) {
				return true;
			}
		}
		return false;
	}
}
