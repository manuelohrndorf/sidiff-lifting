package org.sidiff.editrule.analysis.criticalpairs;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.*;
import static org.sidiff.common.emf.access.EMFMetaAccess.isAssignableTo;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.view.ActionGraph;
import org.sidiff.common.henshin.view.EdgePair;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.editrule.analysis.criticalpairs.util.PotentialRuleConflicts;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.PotentialConflictKind;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialNodeConflict;
import org.sidiff.editrule.rulebase.RulebaseFactory;

/**
 * Calculates all potential conflicts between two rules. This algorithm isn't
 * optimal in the sense that it may reports more potential conflicts than necessary.
 *
 * @author Manuel Ohrndorf, cpietsch
 */
public abstract class PotentialConflictAnalyzer {

	/**
	 * The EMF Rulebase factory instance.
	 */
	protected static RulebaseFactory rbFactory = RulebaseFactory.eINSTANCE;
	
	/**
	 * Caches the Henshin rules as {@link ActionGraph}.
	 */
	protected Map<Rule, ActionGraph> actionGraphCache = new HashMap<Rule, ActionGraph>();
	
	/**
	 * Type index form each EClass to its sub-types.
	 */
	protected Map<EClass, Set<EClass>> subTypes;
	
	/*
	 * The >S<ource of a dependency is the >S<uccessor rule in a sequence of two rules! 
	 * Create -> Use <-> Target -> Source <-> Predecessor -> Successor 
	 * Use -> Delete <-> Target -> Source <-> Predecessor -> Successor
	 */
	
	/**
	 * Initializes a new potential conflict analyzer.
	 */
	public PotentialConflictAnalyzer() {
	}
	
	/**
	 * Transforms the Henshin rule to a {@link ActionGraph}
	 * 
	 * @param rule
	 *            The Henshin rule.
	 * @return The {@link ActionGraph}.
	 */
	protected ActionGraph getActionGraph(Rule rule) {
		ActionGraph actionGraph = actionGraphCache.get(rule);
		
		if (actionGraph == null) {
			actionGraph = new ActionGraph(rule, true);
			actionGraphCache.put(rule, actionGraph);
		}
		
		return actionGraph;
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
		List<Attribute> predecessorChangingAttributes = predecessor.getSetAttributes();
		List<Attribute> predecessorUsingAttributes = predecessor.getPreserveAttributes();
		
		List<Attribute> successorChangingAttributes = successor.getSetAttributes();
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
			Set<PotentialNodeConflict> deleteUseNodePotCons = findDeleteDelete_Node(
					predecessorDeleteNodes, successorDeleteNodes);
			
			for (PotentialNodeConflict pnc : deleteUseNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(deleteUseNodePotCons);
		}
		
		// Delete-Use (delete-preserve)
		if ((!predecessorDeleteNodes.isEmpty()) && (!successorPreserveNodes.isEmpty())) {
			Set<PotentialNodeConflict> deleteUseNodePotCons = findDeleteUse_Node(
					predecessorDeleteNodes, successorPreserveNodes);
			
			for (PotentialNodeConflict pnc : deleteUseNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(deleteUseNodePotCons);
		}

		// Delete-Use (PAC)
		if ((!predecessorDeleteNodes.isEmpty()) && (!successorRequireNodes.isEmpty())) {
			Set<PotentialNodeConflict> useDeleteNodePotConsPAC = findDeleteUse_Node_PAC(
					predecessorDeleteNodes, successorRequireNodes);
			
			for (PotentialNodeConflict pnc : useDeleteNodePotConsPAC) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(useDeleteNodePotConsPAC);
		}
		
		// Create-Forbid (NAC)
		if ((!predecessorCreateNodes.isEmpty()) && (!successorForbidNodes.isEmpty())) {
			Set<PotentialNodeConflict> createForbidNodePotConsPAC = findCreateForbid_Node(
					predecessorCreateNodes, successorForbidNodes);
			
			for (PotentialNodeConflict pnc : createForbidNodePotConsPAC) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(createForbidNodePotConsPAC);
		}
		
		if((!predecessorCreateEdges.isEmpty()) && (!successorForbidEdges.isEmpty())) {
			Set<PotentialEdgeConflict> createForbidEdgePotConcs = findCreateForbid_Edge(predecessorCreateEdges, successorForbidEdges, potRuleCon);
			
		}
//		
//		// Create-Use
//		if ((!predecessorCreateNodes.isEmpty()) && (!successorPreserveNodes.isEmpty())) {
//			Set<PotentialNodeDependency> createUseNodePotDeps = findCreateUses_Node(
//					predecessorCreateNodes, successorPreserveNodes);
//			
//			for (PotentialNodeDependency pnd : createUseNodePotDeps) {
//				pnd.setSourceRule(successorEditRule);
//				pnd.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPNDs(createUseNodePotDeps);
//		}
//		
//		// Create-Use (PAC)
//		if ((!predecessorCreateNodes.isEmpty()) && (!successorRequireNodes.isEmpty())) {
//			Set<PotentialNodeDependency> createUseNodePotDepsPAC = findCreateUses_Node_PAC(
//					predecessorCreateNodes, successorRequireNodes);
//			
//			for (PotentialNodeDependency pnd : createUseNodePotDepsPAC) {
//				pnd.setSourceRule(successorEditRule);
//				pnd.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPNDs(createUseNodePotDepsPAC);
//		}
//		
//		// Forbid-Create
//		if ((!predecessorForbidNodes.isEmpty()) && (!successorCreateNodes.isEmpty())) {
//			Set<PotentialNodeDependency> forbidCreateNodePotDeps = findForbidCreates_Node(
//					predecessorForbidNodes, successorCreateNodes);
//			
//			for (PotentialNodeDependency pnd : forbidCreateNodePotDeps) {
//				pnd.setSourceRule(successorEditRule);
//				pnd.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPNDs(forbidCreateNodePotDeps);
//		}
//		
//		// Delete-Forbid
//		if ((!predecessorDeleteNodes.isEmpty()) && (!successorForbidNodes.isEmpty())) {
//			Set<PotentialNodeDependency> deleteForbidNodePotDeps = findDeleteForbids_Node(
//					predecessorDeleteNodes, successorForbidNodes);
//			
//			for (PotentialNodeDependency pnd : deleteForbidNodePotDeps) {
//				pnd.setSourceRule(successorEditRule);
//				pnd.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPNDs(deleteForbidNodePotDeps);
//		}
		
		/*
		 * Search edge conflicts		
		 */
		
		// Delete-Use (delete-delete)
		if ((!predecessorDeleteEdges.isEmpty()) && (!successorDeleteEdges.isEmpty())) {
			Set<PotentialEdgeConflict> useDeleteEdgePotCons = findDeleteDelete_Edge(
					predecessorDeleteEdges, successorDeleteEdges);
			
			for (PotentialEdgeConflict pec : useDeleteEdgePotCons) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(useDeleteEdgePotCons);
		}
		
		// Delete-Use (delete-preserve)
		if ((!predecessorDeleteEdges.isEmpty()) && (!successorPreserveEdges.isEmpty())) {
			Set<PotentialEdgeConflict> useDeleteEdgePotCons = findDeleteUse_Edge(predecessorDeleteEdges,
					successorPreserveEdges);

			for (PotentialEdgeConflict pec : useDeleteEdgePotCons) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(useDeleteEdgePotCons);
		}

		// Delete-Use (PAC)
		if ((!predecessorDeleteEdges.isEmpty()) && (!successorRequireEdges.isEmpty())) {
			Set<PotentialEdgeConflict> useDeleteEdgePotConsPAC = findDeleteUse_Edge_PAC(
					predecessorDeleteEdges, successorRequireEdges);
			
			for (PotentialEdgeConflict pec : useDeleteEdgePotConsPAC) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(useDeleteEdgePotConsPAC);
		}
//		
//		// Create-Use
//		if ((!predecessorCreateEdges.isEmpty()) && (!successorPreserveEdges.isEmpty())) {
//			Set<PotentialEdgeDependency> createUseEdgePotDeps = findCreateUses_Edge(
//					predecessorCreateEdges, successorPreserveEdges, potRuleCon);
//			
//			for (PotentialEdgeDependency ped : createUseEdgePotDeps) {
//				ped.setSourceRule(successorEditRule);
//				ped.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPEDs(createUseEdgePotDeps);
//		}
//		
//		// Create-Use (PAC)
//		if ((!predecessorCreateEdges.isEmpty()) && (!successorRequireEdges.isEmpty())) {
//			Set<PotentialEdgeDependency> createUseEdgePotDepsPAC = findCreateUses_Edge_PAC(
//					predecessorCreateEdges, successorRequireEdges, potRuleCon);
//			
//			for (PotentialEdgeDependency ped : createUseEdgePotDepsPAC) {
//				ped.setSourceRule(successorEditRule);
//				ped.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPEDs(createUseEdgePotDepsPAC);
//		}
//
//		// Forbid-Create
//		if ((!predecessorForbidEdges.isEmpty()) && (!successorCreateEdges.isEmpty())) {
//			Set<PotentialEdgeDependency> forbidCreateEdgesPotDeps = findForbidCreates_Edge(
//					predecessorForbidEdges, successorCreateEdges, potRuleCon);
//			
//			for (PotentialEdgeDependency ped : forbidCreateEdgesPotDeps) {
//				ped.setSourceRule(successorEditRule);
//				ped.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPEDs(forbidCreateEdgesPotDeps);
//		}
//		
//		// Delete-Forbid
//		if ((!predecessorDeleteEdges.isEmpty()) && (!successorForbidEdges.isEmpty())) {
//			Set<PotentialEdgeDependency> deleteForbidEdgesPotDeps = findDeleteForbids_Edge(
//					predecessorDeleteEdges, successorForbidEdges, potRuleCon);
//			
//			for (PotentialEdgeDependency ped : deleteForbidEdgesPotDeps) {
//				ped.setSourceRule(successorEditRule);
//				ped.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPEDs(deleteForbidEdgesPotDeps);
//		}
		
		/*
		 *  Search attribute conflicts
		 */
		
		// Change-Use
		if ((!predecessorChangingAttributes.isEmpty()) && (!successorUsingAttributes.isEmpty())) {
			Set<PotentialAttributeConflict> changeUseAttributePotCons = findChangeUses_Attribute(
					predecessorChangingAttributes, successorUsingAttributes);
			
			for (PotentialAttributeConflict pac : changeUseAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(changeUseAttributePotCons);
		}
		
		// Change-Use (PAC)
		if ((!predecessorChangingAttributes.isEmpty()) && (!successorRequireAttributes.isEmpty())) {
			Set<PotentialAttributeConflict> changeUseAttributePotConsPAC = findChangeUses_Attribute(
					predecessorChangingAttributes, successorRequireAttributes);
			
			for (PotentialAttributeConflict pac : changeUseAttributePotConsPAC) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(changeUseAttributePotConsPAC);
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
		
//		// Use-Change
//		if ((!predecessorUsingAttributes.isEmpty()) && (!successorChangingAttributes.isEmpty())) {
//			Set<PotentialAttributeDependency> useChangeAttributePotDeps = findUseChanges_Attribute(
//					predecessorUsingAttributes, successorChangingAttributes);
//			
//			for (PotentialAttributeDependency pad : useChangeAttributePotDeps) {
//				pad.setSourceRule(successorEditRule);
//				pad.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPADs(useChangeAttributePotDeps);
//		}
//		
//		// Use-Change (PAC)
//		if ((!predecessorRequireAttributes.isEmpty()) && (!successorChangingAttributes.isEmpty())) {
//			Set<PotentialAttributeDependency> useChangeAttributePotDepsPAC = findUseChanges_Attribute(
//					predecessorRequireAttributes, successorChangingAttributes);
//			
//			for (PotentialAttributeDependency pad : useChangeAttributePotDepsPAC) {
//				pad.setSourceRule(successorEditRule);
//				pad.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPADs(useChangeAttributePotDepsPAC);
//		}
//		
//		// Forbid-Change
//		if ((!predecessorForbidAttributes.isEmpty()) && (!predecessorChangingAttributes.isEmpty())) {
//			Set<PotentialAttributeDependency> forbidChangeAttributePotDeps = findForbidChanges_Attribute(
//					predecessorForbidAttributes, predecessorChangingAttributes);
//			
//			for (PotentialAttributeDependency pad : forbidChangeAttributePotDeps) {
//				pad.setSourceRule(successorEditRule);
//				pad.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPADs(forbidChangeAttributePotDeps);
//		}
//		
//		// Change-Forbid
//		if ((!predecessorChangingAttributes.isEmpty()) && (!successorForbidAttributes.isEmpty())) {
//			Set<PotentialAttributeDependency> changeForbidAttributePotDeps = findChangeForbids_Attribute(
//					predecessorChangingAttributes, successorForbidAttributes);
//			
//			for (PotentialAttributeDependency pad : changeForbidAttributePotDeps) {
//				pad.setSourceRule(successorEditRule);
//				pad.setTargetRule(predecessorEditRule);
//			}
//			potRuleCon.addAllPADs(changeForbidAttributePotDeps);
//		}

		return potRuleCon;
	}

	/*
	 * Nodes
	 */

	/**
	 * Checks all nodes for Delete-Use conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Nodes on LHS only (<<delete>>).
	 * @param lhsSuccessors
	 *            Nodes on union(LHS, LHS and RHS)  (<< delete >> and <<preserve>>).
	 * @return All potential node conflicts.
	 */
	protected Set<PotentialNodeConflict> findDeleteDelete_Node(
			Collection<Node> lhsPredecessors, Collection<Node> lhsSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<PotentialNodeConflict>();

		for (Node predecessorNode : lhsPredecessors) {
			
			for (Node successorNode : lhsSuccessors) {
				
				if (isDeleteDeleteConflict(predecessorNode, successorNode)) {
					// Delete-Use conflict found
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
	 * Checks all nodes for Delete-Use conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Nodes on LHS only (<<delete>>).
	 * @param successors
	 *            Nodes on LHS and RHS  ( <<preserve>>).
	 * @return All potential node conflicts.
	 */
	protected Set<PotentialNodeConflict> findDeleteUse_Node(
			Collection<Node> lhsPredecessors, Collection<NodePair> successors) {

		Set<PotentialNodeConflict> potCons = new HashSet<PotentialNodeConflict>();

		for (Node predecessorNode : lhsPredecessors) {
			
			for (NodePair successorNode : successors) {
				
				if (isDeleteUseConflict(predecessorNode, successorNode)) {
					// Delete-Use conflict found
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
	 * Checks two nodes for a Delete-Use conflict.
	 * 
	 * @param lhsPredecessor
	 *            Node is on LHS (<<delete>>).
	 * @param lhsSuccessor
	 *            Node is LHS (<<delete>>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeleteDeleteConflict(Node lhsPredecessor, Node lhsSuccessor) {
		
		assert(isDeletionNode(lhsPredecessor)) : "Input Assertion Failed: Must be a delition node!";
		assert(isDeletionNode(lhsSuccessor)): "Input Assertion Failed: Must be a delition node!";
		
		/*
		 * Delete-Node-Type + Delete-Node-Sub-Types + Delete-Node-Super-Types == Delete-Node-Type
		 */

		boolean superType = lhsPredecessor.getType().getEAllSuperTypes().contains(lhsSuccessor.getType());
		boolean directType = lhsPredecessor.getType() == lhsSuccessor.getType();
		boolean subType = getSubTypes(lhsPredecessor.getType()).contains(lhsSuccessor.getType());
		
		if (directType || superType || subType) {
			return true;
		}

		return false;
	}
	
	/**
	 * Checks two nodes for a Delete-Use conflict.
	 * 
	 * @param lhsPredecessor
	 *            Node is on LHS (<<delete>>).
	 * @param successor
	 *            Node is LHS and RHS (<<preserve>>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeleteUseConflict(Node lhsPredecessor, NodePair successor) {
		
		assert(isDeletionNode(lhsPredecessor)) : "Input Assertion Failed: Must be a delition node!";
		assert(isPreservedNode((successor.getLhsNode()))): "Input Assertion Failed: Must be a preserved node!";
		
		/*
		 * Delete-Node-Type + Delete-Node-Sub-Types + Delete-Node-Super-Types == Preserve-Node-Type
		 */

		boolean superType = lhsPredecessor.getType().getEAllSuperTypes().contains(successor.getType());
		boolean directType = lhsPredecessor.getType() == successor.getType();
		boolean subType = getSubTypes(lhsPredecessor.getType()).contains(successor.getType());
		
		if (directType || superType || subType) {
			return true;
		}

		return false;
	}
	
	/**
	 * Checks all nodes for Delete-Use conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Nodes on LHS only (<<delete>>).
	 * @param requiredSuccessors
	 *            Nodes from PAC  (<< require >>).
	 * @return All potential conflicts.
	 */
	protected Set<PotentialNodeConflict> findDeleteUse_Node_PAC(
			Collection<Node> lhsPredecessors, Collection<Node> requiredSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<PotentialNodeConflict>();

		for (Node predecessorNode : lhsPredecessors) {
			
			for (Node successorNode : requiredSuccessors) {
				if (isDeleteUseConflict_PAC(predecessorNode, successorNode)) {
					// Delete-Use conflict found
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
	 * Checks two nodes for a Delete-Use conflict.
	 * 
	 * @param lhsPredecessor
	 *            Nodes on LHS only (<<delete>>).
	 * @param requiredSuccessor
	 *            Nodes from PAC  (<< require >>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeleteUseConflict_PAC(Node lhsPredecessor, Node requiredSuccessor) {
		
		assert(isRequireNode(lhsPredecessor)) : "Input Assertion Failed: Must be a delition node!";
		assert(isDeletionNode(requiredSuccessor)) : "Input Assertion Failed: Must be a required node!";
		
		/*
		 * Delete-Node-Type + Delete-Node-Sub-Types + Delete-Node-Super-Types == Require-Node-Type
		 */

		boolean superType = lhsPredecessor.getType().getEAllSuperTypes().contains(requiredSuccessor.getType());
		boolean directType = lhsPredecessor.getType() == requiredSuccessor.getType();
		boolean subType = getSubTypes(lhsPredecessor.getType()).contains(requiredSuccessor.getType());
		
		if (directType || superType || subType) {
			return true;
		}

		return false;
	}
	
	/**
	 * Checks all nodes for a Create-Forbid conflict.
	 * 
	 * @param rhsPredecessors
	 * 			Nodes on RHS only (<< create >>)
	 * @param forbiddenSuccessors
	 * 			Nodes from NAC (<< forbid >>)
	 * @return All potential Create-Forbid conflicts.
	 */
	protected Set<PotentialNodeConflict> findCreateForbid_Node(
			Collection<Node> rhsPredecessors, Collection<Node> forbiddenSuccessors) {
		Set<PotentialNodeConflict> potCons = new HashSet<PotentialNodeConflict>();
		
		for (Node predecessorNode : rhsPredecessors) {
			
			for (Node successorNode : forbiddenSuccessors) {
				if (isCreateForbidConflict_NAC(predecessorNode, successorNode)) {
					// Create-Forbid conflict found
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
	 * Checks tow nodes for a Create-Forbid conflict.
	 * 
	 * @param rhsPredecessors
	 * 			Nodes on RHS only (<< create >>)
	 * @param forbiddenSuccessors
	 * 			Nodes from NAC (<< forbid >>)
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isCreateForbidConflict_NAC(Node rhsPredecessor, Node forbiddenSuccessor) {
		
		assert(isCreationNode(rhsPredecessor)) : "Input Assertion Failed: Must be a creation node!";
		assert(isForbiddenNode(forbiddenSuccessor)) : "Input Assertion Failed: Must be a forbidden node!";
		
		/*
		 * Forbid-Node-Type + Forbid-Node-Sub-Types + Forbid-Node-Super-Types == Create-Node-Type
		 */

		boolean superType = forbiddenSuccessor.getType().getEAllSuperTypes().contains(rhsPredecessor.getType());
		boolean directType = forbiddenSuccessor.getType() == rhsPredecessor.getType();
		boolean subType = getSubTypes(forbiddenSuccessor.getType()).contains(rhsPredecessor.getType());
		
		if (directType || superType || subType) {
			return true;
		}

		return false;
	}
	
	protected Set<PotentialEdgeConflict> findCreateForbid_Edge(Collection<Edge> rhsPredecessors, Collection<Edge> forbidSuccessors, PotentialRuleConflicts potRuleCon){
		Set<PotentialEdgeConflict> potCons = new HashSet<PotentialEdgeConflict>();
		
		for(Edge successorEdge : forbidSuccessors) {
			for(Edge predecessorEdge : rhsPredecessors) {
				if(isCreateForbidConflict(predecessorEdge, successorEdge, potRuleCon)) {
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
	
	protected boolean isCreateForbidConflict(Edge rhsPredecessors, Edge forbidSuccessor, PotentialRuleConflicts potRuleCon) {
		assert(isCreationEdge(rhsPredecessors)) : "Input Assertion Failed!";
		assert(isForbiddenEdge(forbidSuccessor)) : "Input Assertion Failed!";
		
		if(rhsPredecessors.getType().equals(forbidSuccessor.getType())) {
			Node predecessorSrc = rhsPredecessors.getSource();
			Node predecessorTgt = rhsPredecessors.getTarget();
			Node successorSrc = forbidSuccessor.getSource();
			Node successorTgt = forbidSuccessor.getTarget();
			
			boolean srcOK = false;
			if(isCreationNode(predecessorSrc)) {
				srcOK = hasPotentialNodeConflict(potRuleCon, predecessorSrc, successorSrc);
			}else {
				srcOK = isPreservedNode(predecessorSrc);
			}
			
			boolean tgtOK = false;
			if(isCreationNode(predecessorTgt)) {
				tgtOK = hasPotentialNodeConflict(potRuleCon, predecessorTgt, successorTgt);
			}else {
				tgtOK = isPreservedNode(predecessorTgt);
			}
			
			return srcOK && tgtOK;
			
		}
		return false;
	}
	
//
//	/**
//	 * Checks all nodes for Create-Use dependencies.
//	 * 
//	 * @param rhsPredecessors
//	 *            Nodes on RHS only (<< create >>).
//	 * @param successors
//	 *            Edges on LHS and RHS (<< preserve >>).
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialNodeDependency> findCreateUses_Node(
//			Collection<Node> rhsPredecessors, Collection<NodePair> successors) {
//
//		Set<PotentialNodeDependency> potDeps = new HashSet<PotentialNodeDependency>();
//
//		for (NodePair successorNode : successors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPreservedNodeSearchedInModelB(successorNode)) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (Node predecessorNode : rhsPredecessors) {
//				if (isCreateUseDependency(predecessorNode, successorNode)) {
//					// Create-Use dependence found
//					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
//
//					potDep.setSourceNode(successorNode.getLhsNode());
//					potDep.setTargetNode(predecessorNode);
//					potDep.setKind(PotentialDependencyKind.CREATE_USE);
//					potDep.setTransient(isTransient);
//					
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//	
//	/**
//	 * Checks two nodes for a Create-Use dependency.
//	 * 
//	 * @param rhsPredecessor
//	 *            Node is on RHS only (<< create >>).
//	 * @param successor
//	 *            Node is on LHS and RHS (<< preserve >>).
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isCreateUseDependency(Node rhsPredecessor, NodePair successor) {
//
//		assert (isCreationNode(rhsPredecessor)) : "Input Assertion Failed!";
//		
//		/*
//		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Preserve-Node-Type
//		 */
//
//		boolean superType = rhsPredecessor.getType().getEAllSuperTypes().contains(successor.getType());
//		boolean directType = rhsPredecessor.getType() == successor.getType();
//
//		if (directType || superType) {
//			return true;
//		}
//
//		return false;
//	}
//	
//	/**
//	 * Checks all nodes for Create-Use dependencies.
//	 * 
//	 * @param rhsPredecessors
//	 *            Nodes on RHS only (<< create >>).
//	 * @param requireSuccessors
//	 *            Edges from PACs (<< require >>).
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialNodeDependency> findCreateUses_Node_PAC(
//			Collection<Node> rhsPredecessors, Collection<Node> requireSuccessors) {
//
//		Set<PotentialNodeDependency> potDeps = new HashSet<PotentialNodeDependency>();
//
//		for (Node successorNode : requireSuccessors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPostcondition(successorNode.getGraph())) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (Node predecessorNode : rhsPredecessors) {
//				if (isCreateUseDependency_PAC(predecessorNode, successorNode)) {
//					// Create-Use dependence found
//					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
//
//					potDep.setSourceNode(successorNode);
//					potDep.setTargetNode(predecessorNode);
//					potDep.setKind(PotentialDependencyKind.CREATE_USE);
//					potDep.setTransient(isTransient);
//
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//	
//	/**
//	 * Checks two nodes for a Create-Use dependency.
//	 * 
//	 * @param rhsPredecessor
//	 *            Node is on RHS only (<< create >>).
//	 * @param requireSuccessor
//	 *            Node is in PAC (<< require >>).
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isCreateUseDependency_PAC(Node rhsPredecessor, Node requireSuccessor) {
//
//		assert (isCreationNode(rhsPredecessor)) : "Input Assertion Failed!";
//		assert (isRequireNode(requireSuccessor)) : "Input Assertion Failed!";
//		
//		/*
//		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Require-Node-Type
//		 */
//
//		boolean superType = rhsPredecessor.getType().getEAllSuperTypes().contains(requireSuccessor.getType());
//		boolean directType = rhsPredecessor.getType() == requireSuccessor.getType();
//
//		if (directType || superType) {
//			return true;
//		}
//
//		return false;
//	}
//	
//	/**
//	 * Checks all nodes for Forbid-Create dependencies.
//	 * 
//	 * @param forbidPredecessors
//	 *            Edges from NACs (<< forbid >>).
//	 * @param rhsSuccessors
//	 *            Nodes on RHS only (<< create >>).
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialNodeDependency> findForbidCreates_Node(
//			Collection<Node> forbidPredecessors, 
//			Collection<Node> rhsSuccessors) {
//		
//		Set<PotentialNodeDependency> potDeps = new HashSet<PotentialNodeDependency>();
//
//		for (Node predecessorNode : forbidPredecessors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPrecondition(predecessorNode.getGraph())) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (Node successorNode : rhsSuccessors) {
//				if (isForbidCreateDependency(predecessorNode, successorNode)) {
//					// Change-Forbid dependence found
//					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
//
//					potDep.setSourceNode(successorNode);
//					potDep.setTargetNode(predecessorNode);
//					potDep.setKind(PotentialDependencyKind.FORBID_CREATE);
//					potDep.setTransient(isTransient);
//					
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//
//	/**
//	 * Checks two nodes for a Forbid-Create dependency.
//	 * 
//	 * @param forbidPredecessor
//	 *            Node is a NAC (<< forbid >>).
//	 * @param rhsSuccessor
//	 *            Node is on RHS only (<< create >>).
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isForbidCreateDependency(Node forbidPredecessor, Node rhsSuccessor) {
//		
//		assert (isForbiddenNode(forbidPredecessor)) : "Input Assertion Failed!";
//		assert (isCreationNode(rhsSuccessor)) : "Input Assertion Failed!";
//
//		/*
//		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Forbid-Node-Type
//		 */
//
//		boolean superType = rhsSuccessor.getType().getEAllSuperTypes().contains(forbidPredecessor.getType());
//		boolean directType = rhsSuccessor.getType() == forbidPredecessor.getType();
//
//		if (directType || superType) {
//			return true;
//		}
//
//		return false;
//	}
//	
//	/**
//	 * Checks all nodes for Delete-Forbid dependencies.
//	 * 
//	 * @param lhsPredecessors
//	 *            Nodes on LHS only (<< delete >>).
//	 * @param forbidSuccessors
//	 *            Edges from NACs (<< forbid >>).
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialNodeDependency> findDeleteForbids_Node(
//			Collection<Node> lhsPredecessors, 
//			Collection<Node> forbidSuccessors) {
//		
//		Set<PotentialNodeDependency> potDeps = new HashSet<PotentialNodeDependency>();
//
//		for (Node successorNode : forbidSuccessors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPostcondition(successorNode.getGraph())) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (Node predecessorNode : lhsPredecessors) {
//				if (isDeleteForbidDependency(predecessorNode, successorNode)) {
//					// Change-Forbid dependence found
//					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
//
//					potDep.setSourceNode(successorNode);
//					potDep.setTargetNode(predecessorNode);
//					potDep.setKind(PotentialDependencyKind.DELETE_FORBID);
//					potDep.setTransient(isTransient);
//					
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//
//	/**
//	 * Checks two nodes for a Delete-Forbid dependency.
//	 * 
//	 * @param lhsPredecessor
//	 *            Node is on LHS only (<< delete >>).
//	 * @param forbidSuccessor
//	 *            Node is a NAC (<< forbid >>).
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isDeleteForbidDependency(Node lhsPredecessor, Node forbidSuccessor) {
//		
//		assert (isDeletionNode(lhsPredecessor)) : "Input Assertion Failed!";
//		assert (isForbiddenNode(forbidSuccessor)) : "Input Assertion Failed!";
//		
//		if (assignable(lhsPredecessor.getType(), forbidSuccessor.getType())) {
//			return true;
//		}
//		
//		return false;
//	}
//	
	/*
	 * Edges
	 */

	/**
	 * Checks all edges for Delete-Use conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Edges on LHS only (<<delete>>).
	 * @param lhsSuccessors
	 *            Edges on LHS only (<< delete >>).
	 * @return All potential conflicts.
	 */
	protected Set<PotentialEdgeConflict> findDeleteDelete_Edge(
			List<Edge> lhsPredecessors, List<Edge> lhsSuccessors) {
		
		Set<PotentialEdgeConflict> potCons = new HashSet<PotentialEdgeConflict>();

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
	 * Checks two edges for a Delete-Use conflict.
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
		
		if (lhsPredecessor.getType() == lhsSuccessor.getType()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks all edges for Delete-Use conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Edges on LHS only (<<delete>>).
	 * @param successors
	 *            Edges on LHS and RHS only (<<preserve>>).
	 * @return All potential conflicts.
	 */
	protected Set<PotentialEdgeConflict> findDeleteUse_Edge(
			List<Edge> lhsPredecessors, List<EdgePair> successors) {
		
		Set<PotentialEdgeConflict> potCons = new HashSet<PotentialEdgeConflict>();

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
	 * Checks two edges for a Delete-Use conflict.
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
		
		if (lhsPredecessor.getType() == successor.getType()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks all edges for Delete-Use conflicts.
	 * 
	 * @param lhsPredecessors
	 *            Edges on LHS only (<<delete>>).
	 * @param requiredSuccessors
	 *           
	 *            Edges from PACs (<<require>>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeConflict> findDeleteUse_Edge_PAC(
			List<Edge> lhsPredecessors, List<Edge> requiredSuccessors) {
		
		Set<PotentialEdgeConflict> potCons = new HashSet<PotentialEdgeConflict>();

		for (Edge predecessorEdge : lhsPredecessors) {
			
			for (Edge successorEdge : requiredSuccessors) {
				if (isDeleteUseConflict_PAC(predecessorEdge, successorEdge)) {
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
	 * Checks two edges for a Delete-Use conflict.
	 * 
	 * @param lhsPredecessor
	 * 			   Edge is on LHS only (<<delete>>).
	 * @param successor
	 *             Edge is from PAC (<<require>>).
	 * @return <code>true</code> if there  is a conflict; <code>false</code> otherwise.
	 */
	protected boolean isDeleteUseConflict_PAC(Edge lhsPredecessor, Edge successor) {
		
		assert(isDeletionEdge(lhsPredecessor)) : "Input Assertion Failed: Must be a deletion edge!";
		assert(isRequireEdge(successor)) : "Input Assertion Failed: Must be a required edge!";
		
		if (lhsPredecessor.getType() == successor.getType()) {
			return true;
		}
		return false;
	}
//	
//	/**
//	 * Checks two edges for a Use-Delete dependency.
//	 * 
//	 * @param requirePredecessor
//	 *            Edge is in PAC (<< require >>).
//	 * @param lhsSuccessor
//	 *            Edge is on LHS only (<< delete >>).
//	 * @param potRuleDep
//	 *            List of potential node dependencies. Contains at least all
//	 *            potential node dependencies of the predecessor and successor
//	 *            source and target nodes.
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isUseDeleteDependency_PAC(Edge requirePredecessor, Edge lhsSuccessor, 
//			PotentialRuleDependencies potRuleDep) {
//		
//		assert(isRequireEdge(requirePredecessor)) : "Input Assertion Failed!";
//		assert(isDeletionEdge(lhsSuccessor)) : "Input Assertion Failed!";
//		
//		if (requirePredecessor.getType() == lhsSuccessor.getType()) {
//			
//				Node predecessorSrc = requirePredecessor.getSource();
//				Node predecessorTgt = requirePredecessor.getTarget();
//				Node succesorSrc = lhsSuccessor.getSource();
//				Node succesorTgt = lhsSuccessor.getTarget();
//				
//				// Src
//				boolean srcOK = false;
//
//				if (isDeletionNode(succesorSrc)) {
//					srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
//				} else {
//					assert isPreservedNode(succesorSrc) : "<< preserve >> predecessor source node expected!";
//					srcOK = true;
//				}
//
//				// Tgt
//				boolean tgtOK = false;
//
//				if (isDeletionNode(succesorTgt)) {
//					tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
//				} else {
//					assert isPreservedNode(succesorTgt) : "<< preserve >> predecessor target node expected!";
//					tgtOK = true;
//				}
//
//				// Tgt & Src
//				if (srcOK && tgtOK) {
//					return true;
//				} else {
//					return false;
//				}
//		}
//		return false;
//	}
//	
//	/**
//	 * Checks all edges for Create-Use dependencies.
//	 * 
//	 * @param rhsPredecessors
//	 *            Edges on RHS only (<< create >>).
//	 * @param successors
//	 *            Edges on LHS and RHS (<< preserved >>).
//	 * @param potRuleDep
//	 *            List of potential node dependencies. Contains at least all
//	 *            potential node dependencies of the predecessor and successor
//	 *            source and target nodes.
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialEdgeDependency> findCreateUses_Edge(
//			List<Edge> rhsPredecessors, List<EdgePair> successors,
//			PotentialRuleDependencies potRuleDep) {
//
//		Set<PotentialEdgeDependency> potDeps = new HashSet<PotentialEdgeDependency>();
//
//		for (Edge rhsPredecessorEdge : rhsPredecessors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPreservedEdgePostCondition()) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (EdgePair successorEdge : successors) {
//				if (isCreateUseDependency(rhsPredecessorEdge, successorEdge, potRuleDep)) {
//					// Create-Use dependence found
//					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
//
//					potDep.setSourceEdge(successorEdge.getLhsEdge());
//					potDep.setTargetEdge(rhsPredecessorEdge);
//					potDep.setKind(PotentialDependencyKind.CREATE_USE);
//					potDep.setTransient(isTransient);
//					
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//	
//	/**
//	 * Checks two edges for a Create-Use dependency.
//	 * 
//	 * @param rhsPredecessor
//	 *            Edge is on RHS only (<< create >>).
//	 * @param successor
//	 *            Edge is on LHS and RHS (<< preserve >>).
//	 * @param potRuleDep
//	 *            List of potential node dependencies. Contains at least all
//	 *            potential node dependencies of the predecessor and successor
//	 *            source and target nodes.
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isCreateUseDependency(Edge rhsPredecessor, EdgePair successor, 
//			PotentialRuleDependencies potRuleDep) {
//		
//		assert (isCreationEdge(rhsPredecessor)) : "Input Assertion Failed!";
//		
//		if (rhsPredecessor.getType() == successor.getType()) {
//
//			Node predecessorSrc = rhsPredecessor.getSource();
//			Node predecessorTgt = rhsPredecessor.getTarget();
//			Node succesorSrc = successor.getLhsEdge().getSource();
//			Node succesorTgt = successor.getLhsEdge().getTarget();
//
//			// Src
//			boolean srcOK = false;
//
//			if (isCreationNode(predecessorSrc)) {
//				srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
//			} else {
//				assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
//				srcOK = true;
//			}
//
//			// Tgt
//			boolean tgtOK = false;
//
//			if (isCreationNode(predecessorTgt)) {
//				tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
//			} else {
//				assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
//				tgtOK = true;
//			}
//
//			// Tgt & Src
//			if (srcOK && tgtOK) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//		return false;
//	}
//	
//	/**
//	 * Checks all edges for Create-Use dependencies.
//	 * 
//	 * @param rhsPredecessors
//	 *            Edges on RHS only (<< create >>).
//	 * @param requireSuccessors
//	 *            Edges from PACs (<< require >>).
//	 * @param potRuleDep
//	 *            List of potential node dependencies. Contains at least all
//	 *            potential node dependencies of the predecessor and successor
//	 *            source and target nodes.
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialEdgeDependency> findCreateUses_Edge_PAC(
//			List<Edge> rhsPredecessors, List<Edge> requireSuccessors,
//			PotentialRuleDependencies potRuleDep) {
//
//		Set<PotentialEdgeDependency> potDeps = new HashSet<PotentialEdgeDependency>();
//
//		for (Edge successorEdge : requireSuccessors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPostcondition(successorEdge.getGraph())) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (Edge rhsPredecessorEdge : rhsPredecessors) {
//				if (isCreateUseDependency_PAC(rhsPredecessorEdge, successorEdge, potRuleDep)) {
//					// Create-Use dependence found
//					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
//
//					potDep.setSourceEdge(successorEdge);
//					potDep.setTargetEdge(rhsPredecessorEdge);
//					potDep.setKind(PotentialDependencyKind.CREATE_USE);
//					potDep.setTransient(isTransient);
//					
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//	
//	/**
//	 * Checks two edges for a Create-Use dependency.
//	 * 
//	 * @param rhsPredecessor
//	 *            Edge is on RHS only (<< create >>).
//	 * @param requireSuccessor
//	 *            Edge is in PAC (<< require >>).
//	 * @param potRuleDep
//	 *            List of potential node dependencies. Contains at least all
//	 *            potential node dependencies of the predecessor and successor
//	 *            source and target nodes.
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isCreateUseDependency_PAC(Edge rhsPredecessor, Edge requireSuccessor, 
//			PotentialRuleDependencies potRuleDep) {
//		
//		assert (isCreationEdge(rhsPredecessor)) : "Input Assertion Failed!";
//		assert (isRequireEdge(requireSuccessor)) : "Input Assertion Failed!";
//		
//		if (rhsPredecessor.getType() == requireSuccessor.getType()) {
//			
//			Node predecessorSrc = rhsPredecessor.getSource();
//			Node predecessorTgt = rhsPredecessor.getTarget();
//			Node succesorSrc = requireSuccessor.getSource();
//			Node succesorTgt = requireSuccessor.getTarget();
//
//			// Src
//			boolean srcOK = false;
//
//			if (isCreationNode(predecessorSrc)) {
//				srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
//			} else {
//				assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
//				srcOK = true;
//			}
//
//			// Tgt
//			boolean tgtOK = false;
//
//			if (isCreationNode(predecessorTgt)) {
//				tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
//			} else {
//				assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
//				tgtOK = true;
//			}
//
//			// Tgt & Src
//			if (srcOK && tgtOK) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//		return false;
//	}
//	
//	/**
//	 * Checks all edges for Forbid-Create dependencies.
//	 * 
//	 * @param forbidPredecessors
//	 *            Edges from NACs (<< forbid >>).
//	 * @param createSuccessors
//	 *            Edges on RHS only (<< create >>).
//	 * @param potRuleDep
//	 *            List of potential node dependencies. Contains at least all
//	 *            potential node dependencies of the predecessor and successor
//	 *            source and target nodes.
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialEdgeDependency> findForbidCreates_Edge(
//			Collection<Edge> forbidPredecessors, Collection<Edge> createSuccessors,
//			PotentialRuleDependencies potRuleDep) {
//		
//		Set<PotentialEdgeDependency> potDeps = new HashSet<PotentialEdgeDependency>();
//
//		for (Edge successorEdge : createSuccessors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPrecondition(successorEdge.getGraph())) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (Edge predecessorEdge : forbidPredecessors) {
//				if (isDeleteForbidDependency(predecessorEdge, successorEdge, potRuleDep)) {
//					// Change-Forbid dependence found
//					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
//
//					potDep.setSourceEdge(successorEdge);
//					potDep.setTargetEdge(predecessorEdge);
//					potDep.setKind(PotentialDependencyKind.FORBID_CREATE);
//					potDep.setTransient(isTransient);
//					
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//	
//	/**
//	 * Checks two edges for a Forbid-Create dependency.
//	 * 
//	 * @param forbidPredecessors
//	 *            Edge is a NAC (<< forbid >>).
//	 * @param rhsSuccessor
//	 *            Edge is on RHS only (<< create >>).
//	 * @param potRuleDep
//	 *            List of potential node dependencies. Contains at least all
//	 *            potential node dependencies of the predecessor and successor
//	 *            source and target nodes.
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isForbidCreateDependency(Edge forbidPredecessors, Edge rhsSuccessor,
//			PotentialRuleDependencies potRuleDep) {
//		
//		assert (isForbiddenEdge(forbidPredecessors)) : "Input Assertion Failed!";
//		assert (isCreationEdge(rhsSuccessor)) : "Input Assertion Failed!";
//		
//		// Edge types are equal?
//		if (forbidPredecessors.getType().equals(rhsSuccessor.getType())) {
//			
//			Node predecessorSrc = forbidPredecessors.getSource();
//			Node predecessorTgt = forbidPredecessors.getTarget();
//			Node succesorSrc = rhsSuccessor.getSource();
//			Node succesorTgt = rhsSuccessor.getTarget();
//
//			// Src
//			boolean srcOK = false;
//
//			if (isCreationNode(predecessorSrc)) {
//				srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
//			} else {
//				assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
//				srcOK = true;
//			}
//
//			// Tgt
//			boolean tgtOK = false;
//
//			if (isCreationNode(succesorTgt)) {
//				tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
//			} else {
//				assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
//				tgtOK = true;
//			}
//
//			// Tgt & Src
//			if (srcOK && tgtOK) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//		
//		return false;
//	}
//	
//	/**
//	 * Checks all edges for Delete-Forbid dependencies.
//	 * 
//	 * @param lhsPredecessors
//	 *            Edges on LHS only (<< delete >>).
//	 * @param forbidSuccessors
//	 *            Edges from NACs (<< forbid >>).
//	 * @param potRuleDep
//	 *            List of potential node dependencies. Contains at least all
//	 *            potential node dependencies of the predecessor and successor
//	 *            source and target nodes.
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialEdgeDependency> findDeleteForbids_Edge(
//			Collection<Edge> lhsPredecessors, Collection<Edge> forbidSuccessors,
//			PotentialRuleDependencies potRuleDep) {
//		
//		Set<PotentialEdgeDependency> potDeps = new HashSet<PotentialEdgeDependency>();
//
//		for (Edge successorEdge : forbidSuccessors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPrecondition(successorEdge.getGraph())) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (Edge predecessorEdge : lhsPredecessors) {
//				if (isDeleteForbidDependency(predecessorEdge, successorEdge, potRuleDep)) {
//					// Change-Forbid dependence found
//					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
//
//					potDep.setSourceEdge(successorEdge);
//					potDep.setTargetEdge(predecessorEdge);
//					potDep.setKind(PotentialDependencyKind.DELETE_FORBID);
//					potDep.setTransient(isTransient);
//
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//	
//	/**
//	 * Checks two edges for a Delete-Forbid dependency.
//	 * 
//	 * @param lhsPredecessor
//	 *            Edge is on LHS only (<< delete >>).
//	 * @param forbidSuccessor
//	 *            Edge is a NAC (<< forbid >>).
//	 * @param potRuleDep
//	 *            List of potential node dependencies. Contains at least all
//	 *            potential node dependencies of the predecessor and successor
//	 *            source and target nodes.
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isDeleteForbidDependency(Edge lhsPredecessor, Edge forbidSuccessor,
//			PotentialRuleDependencies potRuleDep) {
//		
//		assert (isDeletionEdge(lhsPredecessor)) : "Input Assertion Failed!";
//		assert (isForbiddenEdge(forbidSuccessor)) : "Input Assertion Failed!";
//		
//		// Edge types are equal?
//		if (lhsPredecessor.getType().equals(forbidSuccessor.getType())) {
//			
//			Node predecessorSrc = lhsPredecessor.getSource();
//			Node predecessorTgt = lhsPredecessor.getTarget();
//			Node succesorSrc = forbidSuccessor.getSource();
//			Node succesorTgt = forbidSuccessor.getTarget();
//
//			// Src
//			boolean srcOK = false;
//
//			if (isDeletionNode(predecessorSrc)) {
//				srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
//			} else {
//				assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
//				srcOK = true;
//			}
//
//			// Tgt
//			boolean tgtOK = false;
//
//			if (isDeletionNode(predecessorTgt)) {
//				tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
//			} else {
//				assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
//				tgtOK = true;
//			}
//
//			// Tgt & Src
//			if (srcOK && tgtOK) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//		
//		return false;
//	}
//	
//	/*
//	 * Attributes
//	 */
//
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
		
		Set<PotentialAttributeConflict> potConss = new HashSet<PotentialAttributeConflict>();

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
	protected Set<PotentialAttributeConflict> findChangeChange_Attribute (Collection<Attribute> rhsPredecessors, Collection<Attribute> rhsSuccessors){
		Set<PotentialAttributeConflict> potConss = new HashSet<PotentialAttributeConflict>();
		
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
//	
//	/**
//	 * Checks all attributes for Use-Change dependencies.
//	 * 
//	 * @param lhsPredecessors
//	 *            Attributes on LHS (<< delete / preserve >>) or from PACs (<<require>>).
//	 * @param rhsSuccessors
//	 *            Attributes on RHS only (<< create >>).
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialAttributeDependency> findUseChanges_Attribute(
//			Collection<Attribute> lhsPredecessors, Collection<Attribute> rhsSuccessors) {
//		
//		Set<PotentialAttributeDependency> potDeps = new HashSet<PotentialAttributeDependency>();
//
//		// Calculate transients? 
//		if (!transientPDs) {
//			return potDeps;
//		}
//		
//		for (Attribute rhsPredecessorAttribute : lhsPredecessors) {
//			for (Attribute lhsSuccessorAttribute : rhsSuccessors) {
//				if (isUseChangeDependency(rhsPredecessorAttribute, lhsSuccessorAttribute)) {
//					// Change-Use dependence found
//					PotentialAttributeDependency potDep = rbFactory.createPotentialAttributeDependency();
//
//					potDep.setSourceAttribute(lhsSuccessorAttribute);
//					potDep.setTargetAttribute(rhsPredecessorAttribute);
//					potDep.setKind(PotentialDependencyKind.USE_CHANGE);
//					potDep.setTransient(true);
//
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//	
//	/**
//	 * Checks two attributes for a Use-Change dependency.
//	 * 
//	 * @param lhsPredecessor
//	 *            Attribute is on LHS (<< delete / preserve >>) or in PAC (<<require>>).
//	 * @param rhsSuccessor
//	 *            Attribute is on RHS only (<< create >>).
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isUseChangeDependency(Attribute lhsPredecessor, Attribute rhsSuccessor) {
//		
//		assert (isLHSAttribute(lhsPredecessor) || isRequireAttribute(lhsPredecessor)) : "Input Assertion Failed!";
//		assert (isCreationAttribute(rhsSuccessor)) : "Input Assertion Failed!";
//
//		// Attributes have the same type
//		if (lhsPredecessor.getType().equals(rhsSuccessor.getType())){
//			// Predecessor/Successor nodes is <<preserve>> ?
//			if (isPreservedNode(lhsPredecessor.getNode()) && isPreservedNode(rhsSuccessor.getNode())) {
//				// Attribute case differentiation precondition is fulfilled?
//				if (attributeCaseDifferentiation(lhsPredecessor, rhsSuccessor)) {
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
//	
//	/**
//	 * Checks all attributes for Forbid-Change dependencies.
//	 * 
//	 * @param forbidPredecessors
//	 *            Attributes from NACs (<< forbid >>).
//	 * @param rhsSuccessors
//	 *            Attributes on RHS only (<< create >>).
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialAttributeDependency> findForbidChanges_Attribute(
//			List<Attribute> forbidPredecessors,
//			List<Attribute> rhsSuccessors) {
//
//		Set<PotentialAttributeDependency> potDeps = new HashSet<PotentialAttributeDependency>();
//
//		for (Attribute successorAttribute : rhsSuccessors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPrecondition(successorAttribute.getGraph())) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (Attribute predecessorAttribute : forbidPredecessors) {
//				if (isChangeForbidDependency(predecessorAttribute, successorAttribute)) {
//					// Change-Forbid dependence found
//					PotentialAttributeDependency potDep = rbFactory.createPotentialAttributeDependency();
//
//					potDep.setSourceAttribute(successorAttribute);
//					potDep.setTargetAttribute(predecessorAttribute);
//					potDep.setKind(PotentialDependencyKind.FORBID_CHANGE);
//					potDep.setTransient(isTransient);
//					
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//	
//	/**
//	 * Checks two attributes for a Forbid-Change dependency.
//	 * 
//	 * @param forbidPredecessor
//	 *            Attribute is a NAC (<< forbid >>).
//	 * @param rhsSuccessor
//	 *            Attribute is on RHS only (<< create >>).
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isForbidChangeDependency(Attribute forbidPredecessor, Attribute rhsSuccessor) {
//		
//		assert (isForbiddenAttribute(forbidPredecessor)) : "Input Assertion Failed!";
//		assert (isCreationAttribute(rhsSuccessor)) : "Input Assertion Failed!";
//				
//		// Attributes have the same type
//		if (forbidPredecessor.getType().equals(rhsSuccessor.getType())){
//			// Predecessor/Successor nodes is <<preserve>> ?
//			if (isPreservedNode(rhsSuccessor.getNode())) {
//				// Attribute case differentiation precondition is fulfilled?
//				if (attributeCaseDifferentiation(forbidPredecessor, rhsSuccessor)) {
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
//	
//	/**
//	 * Checks all attributes for Change-Forbid dependencies.
//	 * 
//	 * @param rhsPredecessor
//	 *            Attributes on RHS only (<< create >>).
//	 * @param forbidSuccessor
//	 *            Attributes from NACs (<< forbid >>).
//	 * @return All potential dependencies.
//	 */
//	protected Set<PotentialAttributeDependency> findChangeForbids_Attribute(
//			List<Attribute> rhsPredecessors,
//			List<Attribute> forbidSuccessors) {
//
//		Set<PotentialAttributeDependency> potDeps = new HashSet<PotentialAttributeDependency>();
//
//		for (Attribute successorAttribute : forbidSuccessors) {
//			
//			// Is transient potential dependences?
//			boolean isTransient;
//			
//			if (isPostcondition(successorAttribute.getGraph())) {
//				isTransient = false;
//				
//				// Calculate non-transients? 
//				if (!nonTransientPDs) {
//					continue;
//				}
//			} else {
//				isTransient = true;
//				
//				// Calculate transients? 
//				if (!transientPDs) {
//					continue;
//				}
//			}
//			
//			for (Attribute predecessorAttribute : rhsPredecessors) {
//				if (isChangeForbidDependency(predecessorAttribute, successorAttribute)) {
//					// Change-Forbid dependence found
//					PotentialAttributeDependency potDep = rbFactory.createPotentialAttributeDependency();
//
//					potDep.setSourceAttribute(successorAttribute);
//					potDep.setTargetAttribute(predecessorAttribute);
//					potDep.setKind(PotentialDependencyKind.CHANGE_FORBID);
//					potDep.setTransient(isTransient);
//					
//					potDeps.add(potDep);
//				}
//			}
//		}
//		return potDeps;
//	}
//	
//	/**
//	 * Checks two attributes for a Change-Forbid dependency.
//	 * 
//	 * @param rhsPredecessor
//	 *            Attribute is on RHS only (<< create >>).
//	 * @param forbidSuccessor
//	 *            Attribute is a NAC (<< forbid >>).
//	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
//	 */
//	protected boolean isChangeForbidDependency(Attribute rhsPredecessor, Attribute forbidSuccessor) {
//		
//		assert (isCreationAttribute(rhsPredecessor)) : "Input Assertion Failed!";
//		assert (isForbiddenAttribute(forbidSuccessor)) : "Input Assertion Failed!";
//				
//		// Attributes have the same type
//		if (rhsPredecessor.getType().equals(forbidSuccessor.getType())){
//			// Predecessor nodes is <<preserve>> ?
//			if (isPreservedNode(rhsPredecessor.getNode())) {
//				if (assignable(rhsPredecessor.getNode().getType(), forbidSuccessor.getNode().getType())) {
//					// Attribute case differentiation precondition is fulfilled?
//					if (attributeCaseDifferentiation(rhsPredecessor, forbidSuccessor)) {
//						return true;
//					}
//				}
//			}
//		}
//		
//		return false;
//	}
//	
	/**
	 * Tests if at least one of the attributes is a variable or if both
	 * attributes contain a different literal.
	 * 
	 * @param predecessor
	 *            The predecessor attribute, i.e. the target of conflicts.
	 * @param successor
	 *            The successor attribute, i.e. the source of conflicts
	 * @return <code>true</code> if at least one of the attributes is a variable
	 *         or if both attributes contain different literal;
	 *         <code>false</code> otherwise.
	 */
	protected boolean attributeCaseDifferentiation(Attribute predecessor, Attribute successor) {
		
		// Test precondition: Values => 'equal literals' or 'at least one is variable' == true
		boolean isSufficiently = false;
		
		// Is literal?
		if (isLiteral(predecessor) && isLiteral(successor)) {
			// Literals are equal
			if (predecessor.getValue().equals(successor.getValue())) {
				isSufficiently = true;
			}
		}
		
//		// Predecessor or successor is variable!
//		else {
//			isSufficiently = true;
//		}
		
		return isSufficiently;
	}
	
	/*
	 * Utils
	 */

	/**
	 * Checks if the attribute is a literal attribute.
	 * 
	 * @param attribute
	 *            The attribute to test.
	 * @return <code>true</code> if the attribute is a literal;
	 *         <code>false</code> if it is a variable or expression.
	 */
	protected boolean isLiteral(Attribute attribute) {
		String value = attribute.getValue();

		/*
		 *  String
		 */
		if (value.startsWith("\"") && value.endsWith("\"")) {
			return true;
		}
		
		/*
		 * Boolean
		 */
		
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return true;
		}
		
		/*
		 * Value
		 */
		
		boolean isValue = false;
		
		// Integer
		try {
			Integer.valueOf(value);
			isValue = true;
		} catch (NumberFormatException e) {
			return false;
		}
		
		// Float
		try {
			Float.valueOf(value);
			isValue = true;
		} catch (NumberFormatException e) {
			return false;
		}
		
		// Double
		try {
			Double.valueOf(value);
			isValue = true;
		} catch (NumberFormatException e) {
			return false;
		}

		return isValue;
	}
	
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
			if (potCon.getSourceNode().equals(nodeA) || potCon.getTargetNode().equals(nodeA)) {
				if (potCon.getSourceNode().equals(nodeB) || potCon.getTargetNode().equals(nodeB)) {
					return true;	
				}
			}
		}
		return false;
	}
	
	/**
	 * Removes all node pairs from the list which matches (LHS or RHS) a node in the other list.
	 * 
	 * @param collection
	 *            Node pair collection.
	 * @param toBeRemoved
	 *            Nodes that will be removed from the other list.
	 */
	protected void removeAllNodes(List<NodePair> collection, Set<Node> toBeRemoved) {
		Iterator<NodePair> it = collection.iterator();

		while (it.hasNext()) {
			NodePair pNode = it.next();
			if ((toBeRemoved.contains(pNode.getLhsNode())) || (toBeRemoved.contains(pNode.getRhsNode()))) {
				it.remove();
			}
		}
	}
	
	/**
	 * Removes all edge pairs from the list which matches (LHS or RHS) a edge in the other list.
	 * 
	 * @param collection
	 *            Edge pair collection.
	 * @param toBeRemoved
	 *            Edges that will be removed from the other list.
	 */
	protected void removeAllEdges(List<EdgePair> collection, Set<Edge> toBeRemoved) {
		Iterator<EdgePair> it = collection.iterator();

		while (it.hasNext()) {
			EdgePair pNode = it.next();
			if ((toBeRemoved.contains(pNode.getLhsEdge())) || (toBeRemoved.contains(pNode.getRhsEdge()))) {
				it.remove();
			}
		}
	}
	
	/**
	 * Returns all sub-types of the given EClass.
	 * 
	 * @param referenceType
	 *            The super-type.
	 * @return All sub-types of the given EClass.
	 */
	private Set<EClass> getSubTypes(EClass referenceType){
		
		if (subTypes == null) {
			subTypes = getSubtypeIndex(getImports());
		}
		
		Set<EClass> res = subTypes.get(referenceType);
		if (res == null){
			return new HashSet<EClass>();
		} else {
			return res;
		}		
	}
	
	/**
	 * Creates a map form each class in the package to its corresponding sub-types (in the package).
	 * 
	 * @param ePackage
	 *            The package containing the sub- and super-classes.
	 * @return A map EClass -> Set of EClass sup-types.
	 */
	protected Map<EClass, Set<EClass>> getSubtypeIndex(Set<EPackage> ePackages) {

		// Class (A) -> [Sub classes (X, Y, Z)]
		Map<EClass, Set<EClass>> subTypes = new HashMap<EClass, Set<EClass>>();

		// Iterate over all docType packages
		for (EPackage ePackage : ePackages) {

			// Iterate over all classes in the package
			for (Iterator<EObject> i = ePackage.eAllContents(); i.hasNext();) {
				EObject obj = i.next();
	
				if (obj instanceof EClass) {
					// Next class (A)
					EClass eSubClass = (EClass) obj;
	
					if (subTypes.get(eSubClass) == null) {
						subTypes.put(eSubClass, new HashSet<EClass>());
					}
	
					// Lookup the super types (X,Y,Z) of class (A) and add
					// class (A) as sub type to the classes (X, Y, Z)
					for (EClass eSuperClass : eSubClass.getEAllSuperTypes()) {
						Set<EClass> allSubTypes = subTypes.get(eSuperClass);
	
						if (allSubTypes == null) {
							allSubTypes = new HashSet<EClass>();
							subTypes.put(eSuperClass, allSubTypes);
						}
	
						allSubTypes.add(eSubClass);
					}
				}
			}
		}

		return subTypes;
	}
	
	/**
	 * @return All imports of the Henshin rules (document types) which shall be analyzed.
	 */
	protected abstract Set<EPackage> getImports();
}
