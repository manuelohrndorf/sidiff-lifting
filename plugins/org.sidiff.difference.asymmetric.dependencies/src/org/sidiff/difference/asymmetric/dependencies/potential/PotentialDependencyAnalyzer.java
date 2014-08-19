package org.sidiff.difference.asymmetric.dependencies.potential;

import static org.silift.common.util.access.EMFMetaAccessEx.*;

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
import org.sidiff.common.henshin.EdgePair;
import org.sidiff.common.henshin.EditRuleAnalysis;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;
import org.sidiff.difference.asymmetric.dependencies.potential.util.EmbeddedRule;
import org.sidiff.difference.asymmetric.dependencies.potential.util.PotentialRuleDependencies;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.PotentialAttributeDependency;
import org.sidiff.difference.rulebase.PotentialDependencyKind;
import org.sidiff.difference.rulebase.PotentialEdgeDependency;
import org.sidiff.difference.rulebase.PotentialNodeDependency;
import org.sidiff.difference.rulebase.RulebaseFactory;

public abstract class PotentialDependencyAnalyzer {

	protected Map<EClass, Set<EClass>> subTypes;
	
	/*
	 * The >S<ource of a dependency is the >S<uccessor rule in a sequence of two rules! 
	 * Create -> Use <-> Target -> Source <-> Predecessor -> Successor 
	 * Use -> Delete <-> Target -> Source <-> Predecessor -> Successor
	 */

	/**
	 * Adds all potential dependences from the predecessor to the successor rule to the rulebase.
	 * 
	 * @param predecessor
	 * @param successor
	 */
	protected PotentialRuleDependencies findRuleDependencies(
			Rule predecessor, EditRule predecessorEditRule, EmbeddedRule embeddedPredecessor, 
			Rule successor, EditRule successorEditRule, EmbeddedRule embeddedSuccessor) {
		
		/*
		 * Divide the rule
		 */
		
		// Get nodes
		List<NodePair> predecessorPreserveNodes = HenshinRuleAnalysisUtilEx.getPreservedNodes(predecessor);
		List<Node> predecessorCreateNodes = HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(predecessor);
		List<Node> predecessorDeleteNodes = HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes(predecessor);
		
		List<NodePair> successorPreserveNodes = HenshinRuleAnalysisUtilEx.getPreservedNodes(successor);
		List<Node> successorDeleteNodes = HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes(successor);

		// Get <<forbid>> nodes
		List<Node> successorForbidNodes = HenshinRuleAnalysisUtilEx.getForbidNodes(successor);
		
		// Get <<require>> nodes
		List<Node> successorRequireNodes = HenshinRuleAnalysisUtilEx.getRequireNodes(successor);
		
		// Get edges
		List<EdgePair> predecessorPreserveEdges = HenshinRuleAnalysisUtilEx.getPreservedEdges(predecessor);
		List<Edge> predecessorCreateEdges = HenshinRuleAnalysisUtilEx.getRHSMinusLHSEdges(predecessor);
		List<Edge> predecessorDeleteEdges = HenshinRuleAnalysisUtilEx.getLHSMinusRHSEdges(predecessor);
		
		List<EdgePair> successorPreserveEdges = HenshinRuleAnalysisUtilEx.getPreservedEdges(successor);
		List<Edge> successorDeleteEdges = HenshinRuleAnalysisUtilEx.getLHSMinusRHSEdges(successor);
		
		// Get <<forbid>> edges
		List<Edge> successorForbidEdges = HenshinRuleAnalysisUtilEx.getForbidEdges(successor);
		
		// Get <<forbid>> edges
				List<Edge> successorRequireEdges = HenshinRuleAnalysisUtilEx.getRequireEdges(successor);
		
		// Get attributes
		List<Attribute> predecessorChangingAttributes = HenshinRuleAnalysisUtilEx.getRHSChangedAttributes(predecessor);
		
		List<Attribute> successorUsingAttributes = HenshinRuleAnalysisUtilEx.getPreservedAttributes(successor);
		successorUsingAttributes.addAll(HenshinRuleAnalysisUtilEx.getDeletionAttributes(successor));
		
		// Get <<forbid>> attributes
		List<Attribute> successorForbidAttributes = HenshinRuleAnalysisUtilEx.getForbidAttributes(successor);
		
		// Get <<require>> attributes
		List<Attribute> successorRequireAttributes = HenshinRuleAnalysisUtilEx.getRequireAttributes(successor);
		
		/*
		 * Filter embedded nodes from amalgamation units
		 */
		
		// TODO: Forbids müssen auch beachtet werden

		if (embeddedPredecessor != null) {
			// Filter embedded nodes
			removeAllNodes(predecessorPreserveNodes, embeddedPredecessor.getEmbeddedNodes());
			predecessorCreateNodes.removeAll(embeddedPredecessor.getEmbeddedNodes());
			
			// Filter embedded edges
			removeAllEdges(predecessorPreserveEdges, embeddedPredecessor.getEmbeddedEdges());
			predecessorCreateEdges.removeAll(embeddedPredecessor.getEmbeddedEdges());
			
			// Filter embedded attributes
			predecessorChangingAttributes.removeAll(embeddedPredecessor.getEmbeddedAttributes());
		}
		if (embeddedSuccessor != null) {
			// Filter embedded nodes
			removeAllNodes(successorPreserveNodes, embeddedSuccessor.getEmbeddedNodes());
			successorDeleteNodes.removeAll(embeddedSuccessor.getEmbeddedNodes());
			
			// Filter embedded edges
			removeAllEdges(successorPreserveEdges, embeddedSuccessor.getEmbeddedEdges());
			successorDeleteEdges.removeAll(embeddedSuccessor.getEmbeddedEdges());
			
			// Filter embedded attributes
			successorUsingAttributes.removeAll(embeddedSuccessor.getEmbeddedAttributes());
		}
		
		/*
		 *  Find all potential dependencies - if the predecessor rule is applied first
		 *  and the successor rule is applied second.
		 */
		
		// Create new potential rule dependency
		PotentialRuleDependencies potRuleDep = new PotentialRuleDependencies();

		// Search node dependencies
		if ((!predecessorPreserveNodes.isEmpty()) && (!successorDeleteNodes.isEmpty())) {
			// Use-Delete
			Set<PotentialNodeDependency> useDeleteNodePotDeps = findUseDeletes_Node(
					predecessorPreserveNodes, successorDeleteNodes);
			for (PotentialNodeDependency pnd : useDeleteNodePotDeps) {
				pnd.setSourceRule(successorEditRule);
				pnd.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPNDs(useDeleteNodePotDeps);
		}
//TODO:(TK 8.12.2013): Vielleicht brauchen wir irgendwann auch potentielle UseDeletes für PACs
//		         (sobald wir PACs auch auf Modell A prüfen)
//		if ((!predecessorRequireNodes.isEmpty()) && (!successorDeleteNodes.isEmpty())) {
//			// Use-Delete (PAC)
//			Set<PotentialNodeDependency> useDeleteNodePotDepsPAC = findUseDeletes_Node_PAC(
//					predecessorRequireNodes, successorDeleteNodes);
//			for (PotentialNodeDependency pnd : useDeleteNodePotDepsPAC) {
//				pnd.setSourceRule(successorEditRule);
//				pnd.setTargetRule(predecessorEditRule);
//			}
//			potRuleDep.addAllPNDs(useDeleteNodePotDepsPAC);
//		}
		if ((!predecessorCreateNodes.isEmpty()) && (!successorPreserveNodes.isEmpty())) {
			// Create-Use
			Set<PotentialNodeDependency> createUseNodePotDeps = findCreateUses_Node(
					predecessorCreateNodes, successorPreserveNodes);
			for (PotentialNodeDependency pnd : createUseNodePotDeps) {
				pnd.setSourceRule(successorEditRule);
				pnd.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPNDs(createUseNodePotDeps);
		}
		if ((!predecessorCreateNodes.isEmpty()) && (!successorRequireNodes.isEmpty())) {
			// Create-Use (PAC)
			Set<PotentialNodeDependency> createUseNodePotDepsPAC = findCreateUses_Node_PAC(
					predecessorCreateNodes, successorRequireNodes);
			for (PotentialNodeDependency pnd : createUseNodePotDepsPAC) {
				pnd.setSourceRule(successorEditRule);
				pnd.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPNDs(createUseNodePotDepsPAC);
		}
		if ((!predecessorDeleteNodes.isEmpty()) && (!successorForbidNodes.isEmpty())) {
			// Delete-Forbid
			Set<PotentialNodeDependency> deleteForbidNodePotDeps = findDeleteForbids_Node(
					predecessorDeleteNodes, successorForbidNodes);
			for (PotentialNodeDependency pnd : deleteForbidNodePotDeps) {
				pnd.setSourceRule(successorEditRule);
				pnd.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPNDs(deleteForbidNodePotDeps);
		}
		
		// Search edge dependencies		
		if ((!predecessorPreserveEdges.isEmpty()) && (!successorDeleteEdges.isEmpty())) {
			// Use-Delete
			Set<PotentialEdgeDependency> useDeleteEdgePotDeps = findUseDeletes_Edge(
					predecessorPreserveEdges, successorDeleteEdges, potRuleDep);
			for (PotentialEdgeDependency ped : useDeleteEdgePotDeps) {
				ped.setSourceRule(successorEditRule);
				ped.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPEDs(useDeleteEdgePotDeps);
		}
//TODO:(TK 8.12.2013): Vielleicht brauchen wir irgendwann auch potentielle UseDeletes für PACs
//		          (sobald wir PACs auch auf Modell A prüfen)
//		if ((!predecessorRequireEdges.isEmpty()) && (!successorDeleteEdges.isEmpty())) {
//			// Use-Delete (PAC)
//			Set<PotentialEdgeDependency> useDeleteEdgePotDepsPAC = findUseDeletes_Edge_PAC(
//					predecessorRequireEdges, successorDeleteEdges, potRuleDep);
//			for (PotentialEdgeDependency ped : useDeleteEdgePotDepsPAC) {
//				ped.setSourceRule(successorEditRule);
//				ped.setTargetRule(predecessorEditRule);
//			}
//			potRuleDep.addAllPEDs(useDeleteEdgePotDepsPAC);
//		}
		if ((!predecessorCreateEdges.isEmpty()) && (!successorPreserveEdges.isEmpty())) {
			// Create-Use
			Set<PotentialEdgeDependency> createUseEdgePotDeps = findCreateUses_Edge(
					predecessorCreateEdges, successorPreserveEdges, potRuleDep);
			for (PotentialEdgeDependency ped : createUseEdgePotDeps) {
				ped.setSourceRule(successorEditRule);
				ped.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPEDs(createUseEdgePotDeps);
		}
		if ((!predecessorCreateEdges.isEmpty()) && (!successorRequireEdges.isEmpty())) {
			// Create-Use (PAC)
			Set<PotentialEdgeDependency> createUseEdgePotDepsPAC = findCreateUses_Edge_PAC(
					predecessorCreateEdges, successorRequireEdges, potRuleDep);
			for (PotentialEdgeDependency ped : createUseEdgePotDepsPAC) {
				ped.setSourceRule(successorEditRule);
				ped.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPEDs(createUseEdgePotDepsPAC);
		}
		if ((!predecessorDeleteEdges.isEmpty()) && (!successorForbidEdges.isEmpty())) {
			// Delete-Forbid
			Set<PotentialEdgeDependency> deleteForbidEdgesPotDeps = findDeleteForbids_Edge(
					predecessorDeleteEdges, successorForbidEdges);
			for (PotentialEdgeDependency ped : deleteForbidEdgesPotDeps) {
				ped.setSourceRule(successorEditRule);
				ped.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPEDs(deleteForbidEdgesPotDeps);
		}
		
		// Search attribute dependencies
		if ((!predecessorChangingAttributes.isEmpty()) && (!successorUsingAttributes.isEmpty())) {
			// Change-Use
			Set<PotentialAttributeDependency> changeUseAttributePotDeps = findChangeUses_Attribute(
					predecessorChangingAttributes, successorUsingAttributes);
			for (PotentialAttributeDependency pad : changeUseAttributePotDeps) {
				pad.setSourceRule(successorEditRule);
				pad.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPADs(changeUseAttributePotDeps);
		}
		if ((!predecessorChangingAttributes.isEmpty()) && (!successorRequireAttributes.isEmpty())) {
			// Change-Use (PAC)
			Set<PotentialAttributeDependency> changeUseAttributePotDepsPAC = findChangeUses_Attribute(
					predecessorChangingAttributes, successorRequireAttributes);
			for (PotentialAttributeDependency pad : changeUseAttributePotDepsPAC) {
				pad.setSourceRule(successorEditRule);
				pad.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPADs(changeUseAttributePotDepsPAC);
		}
		if ((!predecessorChangingAttributes.isEmpty()) && (!successorForbidAttributes.isEmpty())) {
			// Change-Forbid
			Set<PotentialAttributeDependency> changeForbidAttributePotDeps = findChangeForbids_Attribute(
					predecessorChangingAttributes, successorForbidAttributes);
			for (PotentialAttributeDependency pad : changeForbidAttributePotDeps) {
				pad.setSourceRule(successorEditRule);
				pad.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPADs(changeForbidAttributePotDeps);
		}

		return potRuleDep;
	}

	/*
	 * Nodes
	 */

	protected Set<PotentialNodeDependency> findUseDeletes_Node(Collection<NodePair> predecessors, Collection<Node> lhsSuccessors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<PotentialNodeDependency>();

		for (NodePair predecessorNode : predecessors) {
			for (Node successorNode : lhsSuccessors) {
				if (isUseDeleteDependency(predecessorNode, successorNode)) {
					// Delete-Use dependence found
					PotentialNodeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialNodeDependency();

					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode.getLhsNode());
					potDep.setKind(PotentialDependencyKind.USE_DELETE);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}

	protected boolean isUseDeleteDependency(NodePair predecessor, Node lhsSuccessor) {
		assert(HenshinRuleAnalysisUtilEx.isDeletionNode(lhsSuccessor));
		
		/*
		 * Preserve-Node-Type + Preserve-Node-Sub-Types + Preserve-Node-Super-Types == Delete-Node-Type
		 */

		boolean superType = predecessor.getType().getEAllSuperTypes().contains(lhsSuccessor.getType());
		boolean directType = predecessor.getType() == lhsSuccessor.getType();
		boolean subType = getSubTypes(predecessor.getType()).contains(lhsSuccessor.getType());
		
		if (directType || superType || subType) {

			// Filter transient potential dependences:
			if (EditRuleAnalysis.isSearchedInModelA(predecessor)) {
				return true;
			}
		}

		return false;
	}

	protected Set<PotentialNodeDependency> findCreateUses_Node(Collection<Node> rhsPredecessors, Collection<NodePair> successors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<PotentialNodeDependency>();

		for (Node predecessorNode : rhsPredecessors) {
			for (NodePair successorNode : successors) {
				if (isCreateUseDependency(predecessorNode, successorNode)) {
					// Create-Use dependence found
					PotentialNodeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialNodeDependency();

					potDep.setSourceNode(successorNode.getLhsNode());
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	protected Set<PotentialNodeDependency> findCreateUses_Node_PAC(Collection<Node> rhsPredecessors, Collection<Node> successors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<PotentialNodeDependency>();

		for (Node predecessorNode : rhsPredecessors) {
			for (Node successorNode : successors) {
				if (isCreateUseDependency_PAC(predecessorNode, successorNode)) {
					// Create-Use dependence found
					PotentialNodeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialNodeDependency();

					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}

	/**
	 * @param rhsPredecessor
	 *            Node is on RHS only (<< create >>).
	 * @param successor
	 *            Node is on LHS and RHS (<< preserve >>).
	 * @return
	 */
	protected boolean isCreateUseDependency(Node rhsPredecessor, NodePair successor) {

		// Input assertion
		assert (HenshinRuleAnalysisUtilEx.isCreationNode(rhsPredecessor)) 
				: "Unfulfilled Precondition";
		
		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Preserve-Node-Type
		 */

		boolean superType = rhsPredecessor.getType().getEAllSuperTypes().contains(successor.getType());
		boolean directType = rhsPredecessor.getType() == successor.getType();

		if (directType || superType) {

			// Filter transient potential dependences:
			if (EditRuleAnalysis.isSearchedInModelB(successor)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * @param rhsPredecessor
	 *            Node is on RHS only (<< create >>).
	 * @param successor
	 *            Node is in PAC (<< require >>).
	 * @return
	 */
	protected boolean isCreateUseDependency_PAC(Node rhsPredecessor, Node successor) {

		// Input assertion
		assert (HenshinRuleAnalysisUtilEx.isCreationNode(rhsPredecessor)) 
				: "Unfulfilled Precondition";
		
		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Require-Node-Type
		 */

		boolean superType = rhsPredecessor.getType().getEAllSuperTypes().contains(successor.getType());
		boolean directType = rhsPredecessor.getType() == successor.getType();

		if (directType || superType) {

// TODO TK (8.12.2013): yet, PACs are only searched in model B, so this condition is not necessary			
//			// Filter transient potential dependences:
//			if (EditRule2RecognitionRule.isSearchedInModelB(successor)) {
//				return true;
//			}
			return true;
		}

		return false;
	}
	
	protected Set<PotentialNodeDependency> findDeleteForbids_Node(
			Collection<Node> lhsPredecessors, 
			Collection<Node> forbidSuccessors) {
		
		Set<PotentialNodeDependency> potDeps = new HashSet<PotentialNodeDependency>();

		for (Node predecessorNode : lhsPredecessors) {
			for (Node successorNode : forbidSuccessors) {
				if (isDeleteForbidDependency(predecessorNode, successorNode)) {
					// Change-Forbid dependence found
					PotentialNodeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialNodeDependency();

					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.DELETE_FORBID);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}

	/**
	 * @param lhsPredecessor
	 *            Node is on LHS only (<< delete >>).
	 * @param forbidSuccessor
	 *            Node is a negative nested condition (<< forbid >>).
	 * @return
	 */
	protected boolean isDeleteForbidDependency(Node lhsPredecessor, Node forbidSuccessor) {
		
		assert (HenshinRuleAnalysisUtilEx.isDeletionNode(lhsPredecessor) &&
				HenshinRuleAnalysisUtilEx.isForbiddenNode(forbidSuccessor)) 
				: "Unfulfilled Precondition";
		
		if (assignable(lhsPredecessor.getType(), forbidSuccessor.getType())) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Edges
	 */

	protected Set<PotentialEdgeDependency> findUseDeletes_Edge(
			List<EdgePair> predecessors, List<Edge> lhsSuccessors, 
			PotentialRuleDependencies potRuleDep) {
		
		Set<PotentialEdgeDependency> potDeps = new HashSet<PotentialEdgeDependency>();

		for (EdgePair predecessorEdge : predecessors) {
			for (Edge successorEdge : lhsSuccessors) {
				if (isUseDeleteDependency(predecessorEdge, successorEdge, potRuleDep)) {
					// Delete-Use dependence found
					PotentialEdgeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialEdgeDependency();

					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(predecessorEdge.getLhsEdge());
					potDep.setKind(PotentialDependencyKind.USE_DELETE);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	protected boolean isUseDeleteDependency(EdgePair predecessor, Edge lhsSuccessor, 
			PotentialRuleDependencies potRuleDep) {
		
		assert(HenshinRuleAnalysisUtilEx.isDeletionEdge(lhsSuccessor));
		
		if (predecessor.getType() == lhsSuccessor.getType()) {
			// Filter transient potential dependences:
			if (EditRuleAnalysis.isSearchedInModelA(predecessor)) {
				Node predecessorSrc = predecessor.getLhsEdge().getSource();
				Node predecessorTgt = predecessor.getLhsEdge().getTarget();
				Node succesorSrc = lhsSuccessor.getSource();
				Node succesorTgt = lhsSuccessor.getTarget();
				
				// Tgt & Src
				if (assignable(predecessorSrc.getType(), succesorSrc.getType())
						&& assignable(predecessorTgt.getType(), succesorTgt.getType())) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	protected Set<PotentialEdgeDependency> findCreateUses_Edge(
			List<Edge> rhsPredecessors, List<EdgePair> successors,
			PotentialRuleDependencies potRuleDep) {

		Set<PotentialEdgeDependency> potDeps = new HashSet<PotentialEdgeDependency>();

		for (Edge rhsPredecessorEdge : rhsPredecessors) {
			for (EdgePair successorEdge : successors) {
				if (isCreateUseDependency(rhsPredecessorEdge, successorEdge, potRuleDep)) {
					// Create-Use dependence found
					PotentialEdgeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialEdgeDependency();

					potDep.setSourceEdge(successorEdge.getLhsEdge());
					potDep.setTargetEdge(rhsPredecessorEdge);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	protected Set<PotentialEdgeDependency> findCreateUses_Edge_PAC(
			List<Edge> rhsPredecessors, List<Edge> successors,
			PotentialRuleDependencies potRuleDep) {

		Set<PotentialEdgeDependency> potDeps = new HashSet<PotentialEdgeDependency>();

		for (Edge rhsPredecessorEdge : rhsPredecessors) {
			for (Edge successorEdge : successors) {
				if (isCreateUseDependency_PAC(rhsPredecessorEdge, successorEdge, potRuleDep)) {
					// Create-Use dependence found
					PotentialEdgeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialEdgeDependency();

					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(rhsPredecessorEdge);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * @param rhsPredecessor
	 *            Edge is on RHS only (<< create >>).
	 * @param successor
	 *            Edge is on LHS and RHS (<< preserve >>).
	 * @param potRuleDep
	 * @return
	 */
	protected boolean isCreateUseDependency(Edge rhsPredecessor, EdgePair successor, 
			PotentialRuleDependencies potRuleDep) {
		
		// Input assertion
		assert (HenshinRuleAnalysisUtilEx.isCreationEdge(rhsPredecessor)) 
				: "Unfulfilled Precondition";
		
		if (rhsPredecessor.getType() == successor.getType()) {
			// Filter transient potential dependences:
			if (EditRuleAnalysis.isSearchedInModelB(successor)) {
				Node predecessorSrc = rhsPredecessor.getSource();
				Node predecessorTgt = rhsPredecessor.getTarget();
				Node succesorSrc = successor.getLhsEdge().getSource();
				Node succesorTgt = successor.getLhsEdge().getTarget();
				
				// Src
				boolean srcOK = false;
				
				if (HenshinRuleAnalysisUtilEx.isPreservedNode(predecessorSrc) 
						&& assignable(predecessorSrc.getType(), succesorSrc.getType())) {
					srcOK = true;
				} else if (HenshinRuleAnalysisUtilEx.isCreationNode(predecessorSrc)
						&& hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc)) {
					srcOK = true;
				}
				
				// Tgt
				boolean tgtOK = false;
				
				if (HenshinRuleAnalysisUtilEx.isPreservedNode(predecessorTgt) 
						&& assignable(predecessorTgt.getType(), succesorTgt.getType())) {
					tgtOK = true;
				} else if (HenshinRuleAnalysisUtilEx.isCreationNode(predecessorTgt)
						&& hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt)) {
					tgtOK = true;
				}
				
				// Tgt & Src
				if (srcOK && tgtOK) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param rhsPredecessor
	 *            Edge is on RHS only (<< create >>).
	 * @param successor
	 *            Edge is in PAC (<< require >>).
	 * @param potRuleDep
	 * @return
	 */
	protected boolean isCreateUseDependency_PAC(Edge rhsPredecessor, Edge successor, 
			PotentialRuleDependencies potRuleDep) {
		
		// Input assertion
		assert (HenshinRuleAnalysisUtilEx.isCreationEdge(rhsPredecessor)) 
				: "Unfulfilled Precondition";
		
		if (rhsPredecessor.getType() == successor.getType()) {

// TODO: TK (8.12.2013): yet, PACs are only searched in model B, so this "transient check" is not necessary			
			// Filter transient potential dependences:
//			if (EditRule2RecognitionRule.isSearchedInModelB(successor)) {
			
			Node predecessorSrc = rhsPredecessor.getSource();
			Node predecessorTgt = rhsPredecessor.getTarget();
			Node succesorSrc = successor.getSource();
			Node succesorTgt = successor.getTarget();
			
			// Src
			boolean srcOK = false;
			
			if (HenshinRuleAnalysisUtilEx.isPreservedNode(predecessorSrc) 
					&& assignable(predecessorSrc.getType(), succesorSrc.getType())) {
				srcOK = true;
			} else if (HenshinRuleAnalysisUtilEx.isCreationNode(predecessorSrc)
					&& hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc)) {
				srcOK = true;
			}
			
			// Tgt
			boolean tgtOK = false;
			
			if (HenshinRuleAnalysisUtilEx.isPreservedNode(predecessorTgt) 
					&& assignable(predecessorTgt.getType(), succesorTgt.getType())) {
				tgtOK = true;
			} else if (HenshinRuleAnalysisUtilEx.isCreationNode(predecessorTgt)
					&& hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt)) {
				tgtOK = true;
			}
			
			// Tgt & Src
			if (srcOK && tgtOK) {
				return true;
			} else {
				return false;
			}
			
//			}
		}
		return false;
	}
	
	protected Set<PotentialEdgeDependency> findDeleteForbids_Edge(
			Collection<Edge> lhsPredecessors, 
			Collection<Edge> forbidSuccessors) {
		
		Set<PotentialEdgeDependency> potDeps = new HashSet<PotentialEdgeDependency>();

		for (Edge predecessorEdge : lhsPredecessors) {
			for (Edge successorEdge : forbidSuccessors) {
				if (isDeleteForbidDependency(predecessorEdge, successorEdge)) {
					// Change-Forbid dependence found
					PotentialEdgeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialEdgeDependency();

					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(predecessorEdge);
					potDep.setKind(PotentialDependencyKind.DELETE_FORBID);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * @param lhsPredecessor
	 *            Edge is on LHS only (<< delete >>).
	 * @param forbidSuccessor
	 *            Edge is a negative nested condition (<< forbid >>).
	 * @return
	 */
	protected boolean isDeleteForbidDependency(Edge lhsPredecessor, Edge forbidSuccessor) {
		
		assert (HenshinRuleAnalysisUtilEx.isDeletionEdge(lhsPredecessor) &&
				HenshinRuleAnalysisUtilEx.isForbiddenEdge(forbidSuccessor))
				: "Unfulfilled Precondition";
		
		// Edge types are equal?
		if (lhsPredecessor.getType().equals(forbidSuccessor.getType())) {
			// Source and target are assignable?
			if (assignable(lhsPredecessor.getSource().getType(), forbidSuccessor.getSource().getType()) &&
				assignable(lhsPredecessor.getTarget().getType(), forbidSuccessor.getTarget().getType())) {
				
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Attributes
	 */

	protected Set<PotentialAttributeDependency> findChangeUses_Attribute(
			Collection<Attribute> rhsPredecessors, Collection<Attribute> lhsSuccessors) {
		
		Set<PotentialAttributeDependency> potDeps = new HashSet<PotentialAttributeDependency>();

		for (Attribute rhsPredecessorAttribute : rhsPredecessors) {
			for (Attribute lhsSuccessorAttribute : lhsSuccessors) {
				if (isChangeUseDependency(rhsPredecessorAttribute, lhsSuccessorAttribute)) {
					// Change-Use dependence found
					PotentialAttributeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialAttributeDependency();

					potDep.setSourceAttribute(lhsSuccessorAttribute);
					potDep.setTargetAttribute(rhsPredecessorAttribute);
					potDep.setKind(PotentialDependencyKind.CHANGE_USE);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * @param rhsPredecessor
	 *            Attribute is on RHS only (<< create >>).
	 * @param lhsSuccessor
	 *            Attribute is on LHS (<< delete / preserve >>) or in PAC (<<require>>).
	 * @return
	 */
	protected boolean isChangeUseDependency(Attribute rhsPredecessor, Attribute lhsSuccessor) {
		
		// Input assertion
		assert (HenshinRuleAnalysisUtilEx.isCreationAttribute(rhsPredecessor) &&
				HenshinRuleAnalysisUtilEx.isLHSAttribute(lhsSuccessor)) : "Unfulfilled Precondition";
		
		// Attributes have the same type
		if (rhsPredecessor.getType().equals(lhsSuccessor.getType())){
			// Predecessor nodes is <<preserve>> ?
			if (HenshinRuleAnalysisUtilEx.isPreservedNode(rhsPredecessor.getNode())) {
				// is predecessor nodeType assignable to successor nodeType?
				if (isAssignableTo(rhsPredecessor.getNode().getType(), lhsSuccessor.getNode().getType())){
					// Attribute case differentiation precondition is fulfilled?
					if (attributeCaseDifferentiation(rhsPredecessor, lhsSuccessor)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	protected Set<PotentialAttributeDependency> findChangeForbids_Attribute(
			List<Attribute> rhsPredecessors,
			List<Attribute> forbidSuccessors) {

		Set<PotentialAttributeDependency> potDeps = new HashSet<PotentialAttributeDependency>();

		for (Attribute predecessorAttribute : rhsPredecessors) {
			for (Attribute successorAttribute : forbidSuccessors) {
				if (isChangeForbidDependency(predecessorAttribute, successorAttribute)) {
					// Change-Forbid dependence found
					PotentialAttributeDependency potDep = RulebaseFactory.eINSTANCE.createPotentialAttributeDependency();

					potDep.setSourceAttribute(successorAttribute);
					potDep.setTargetAttribute(predecessorAttribute);
					potDep.setKind(PotentialDependencyKind.CHANGE_FORBID);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * @param rhsPredecessor
	 *            Attribute is on RHS only (<< create >>).
	 * @param forbidSuccessor
	 *            Attribute is a negative nested condition (<< forbid >>).
	 * @return
	 */
	protected boolean isChangeForbidDependency(Attribute rhsPredecessor, Attribute forbidSuccessor) {
		
		// Input assertion
		assert (HenshinRuleAnalysisUtilEx.isCreationAttribute(rhsPredecessor) &&
				HenshinRuleAnalysisUtilEx.isForbiddenAttribute(forbidSuccessor)) 
				: "Unfulfilled Precondition";
		
		// Attributes have the same type
		if (rhsPredecessor.getType().equals(forbidSuccessor.getType())){
			// Predecessor nodes is <<preserve>> ?
			if (HenshinRuleAnalysisUtilEx.isPreservedNode(rhsPredecessor.getNode())) {
				if (assignable(rhsPredecessor.getNode().getType(), forbidSuccessor.getNode().getType())) {
					// Attribute case differentiation precondition is fulfilled?
					if (attributeCaseDifferentiation(rhsPredecessor, forbidSuccessor)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
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
		
		// Predecessor or successor is variable!
		else {
			isSufficiently = true;
		}
		
		return isSufficiently;
	}
	
	/*
	 * Utils
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
	
	protected boolean hasPotentialNodeDependency(PotentialRuleDependencies potRuleDep, Node nodeA, Node nodeB) {
		for(PotentialNodeDependency potDep : potRuleDep.getPotentialNodeDependencies()) {
			if (potDep.getSourceNode().equals(nodeA) || potDep.getTargetNode().equals(nodeA)) {
				if (potDep.getSourceNode().equals(nodeB) || potDep.getTargetNode().equals(nodeB)) {
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
	
	private Set<EClass> getSubTypes(EClass referenceType){
		if (subTypes == null || !subTypes.containsKey(referenceType)) {
			// init or dynamically adjust subType index (adjustment is actually creating a new index with an actual list of documentTypes)
			Set<EPackage> docTypePackages = new HashSet<EPackage>();
			for (String docType : getDocumentTypes()) {
				EPackage docTypePackage = EPackage.Registry.INSTANCE.getEPackage(docType);
				assert (docTypePackage != null) : "Package for docType " + docType + " not found in the global EMF package registry";
				docTypePackages.add(docTypePackage);
			}
			
			subTypes = initSubtypeIndex(docTypePackages);
		}
		
		return subTypes.get(referenceType);
	}
	
	/**
	 * Creates a map form each class in the package to its corresponding sub types (in the package).
	 * 
	 * @param ePackage
	 *            The package containing the sub and super classes.
	 * @return A map EClass -> Set of EClass sup types.
	 */
	protected Map<EClass, Set<EClass>> initSubtypeIndex(Set<EPackage> ePackages) {

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
	 * Template method which has to be overridden by subclasses.
	 * 
	 * @return
	 */
	protected abstract Collection<String> getDocumentTypes();
}
