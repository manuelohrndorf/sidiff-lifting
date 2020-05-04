package org.sidiff.editrule.analysis.criticalpairs;

import static org.sidiff.common.emf.access.EMFMetaAccess.isAssignableTo;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getCreationAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getDeletionAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRemoteAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isChangingAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isDeletionAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isDeletionEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isDeletionNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isForbiddenAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isForbiddenEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isForbiddenNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isPreservedAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isPreservedEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isPreservedNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isRequireAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isRequireEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isRequireNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.view.ActionGraph;
import org.sidiff.editrule.analysis.criticalpairs.util.PotentialRuleConflicts;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.PotentialConflictKind;
import org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialNodeConflict;

/**
 * Calculates all potential conflicts between two rules. This algorithm isn't
 * optimal in the sense that it may reports more potential conflicts than
 * necessary.
 *
 * @author cpietsch
 */
public abstract class PotentialConflictAnalyzer extends AbstractAnalyzer {

	/*
	 * The >S<ource of a conflict is the >P<redecessor rule in a sequence of two
	 * rules!
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
	 * @param predecessor The predecessor rule, i.e. the target of dependencies.
	 * @param successor   The successor rule, i.e. the source of dependencies
	 */
	protected PotentialRuleConflicts findRuleConflicts(ActionGraph predecessor, EditRule predecessorEditRule,
			ActionGraph successor, EditRule successorEditRule) {

		/*
		 * Divide the rule
		 */

		// Get nodes
		Collection<Node> predecessorCreateNodes = predecessor.getCreateNodes();
		Collection<Node> predecessorDeleteNodes = predecessor.getDeleteNodes();

		Collection<Node> successorCreateNodes = successor.getCreateNodes();
		Collection<Node> successorDeleteNodes = successor.getDeleteNodes();
		Collection<Node> successorPreserveNodes = successor.getPreserveNodes().stream().map(pair -> pair.getLhsNode()).collect(Collectors.toSet());
		Collection<Node> successorRequireNodes = successor.getRequireNodes();
		Collection<Node> successorForbidNodes = successor.getForbidNodes();
		Collection<Node> successorUseNodes = new HashSet<Node>(successorPreserveNodes);
		successorUseNodes.addAll(successorRequireNodes);

		// Get edges
		Collection<Edge> predecessorCreateEdges = predecessor.getCreateEdges();
		Collection<Edge> predecessorDeleteEdges = predecessor.getDeleteEdges();

		Collection<Edge> successorCreateEdges = successor.getCreateEdges();
		Collection<Edge> successorDeleteEdges = successor.getDeleteEdges();
		Collection<Edge> successorPreserveEdges = successor.getPreserveEdges().stream().map(pair -> pair.getLhsEdge()).collect(Collectors.toSet());
		Collection<Edge> successorRequireEdges = successor.getRequireEdges();
		Collection<Edge> successorForbidEdges = successor.getForbidEdges();
		Collection<Edge> successorUseEdges = new HashSet<Edge>(successorPreserveEdges);
		successorUseEdges.addAll(successorRequireEdges);

		// Get attributes
		Collection<Attribute> predecessorCreateAttributes = getCreationAttributes(predecessor.getRule());
		Collection<Attribute> predecessorSetAttributes = predecessor.getSetAttributes();
		Collection<Attribute> predecessorChangeAttributes = predecessor.getChangeAttributes().stream().map(pair -> pair.getRhsAttribute()).collect(Collectors.toSet());
		Collection<Attribute> predecessorSetChangeAttributes = new HashSet<Attribute>(predecessorSetAttributes);
		predecessorSetChangeAttributes.addAll(predecessorChangeAttributes);

		Collection<Attribute> successorDeleteAttributes = getDeletionAttributes(successor.getRule());
		Collection<Attribute> successorSetAttributes = successor.getSetAttributes();
		Collection<Attribute> successorChangeAttributes = successor.getChangeAttributes().stream().map(pair -> pair.getRhsAttribute()).collect(Collectors.toSet());
		Collection<Attribute> successorPreserveAttributes = successor.getPreserveAttributes();
		Collection<Attribute> successorRequireAttributes = successor.getRequireAttributes();
		Collection<Attribute> successorForbidAttributes = successor.getForbidAttributes();
		Collection<Attribute> successorSetChangeAttributes = new HashSet<Attribute>(successorSetAttributes);
		successorSetChangeAttributes.addAll(successorChangeAttributes);
		Collection<Attribute> successorUseAttributes = new HashSet<Attribute>(successorDeleteAttributes);
		successorUseAttributes.addAll(successorChangeAttributes);
		successorUseAttributes.addAll(successorPreserveAttributes);
		successorUseAttributes.addAll(successorRequireAttributes);

		/*
		 * Find all potential conflicts between the predecessor and the successor rule.
		 */

		// Create new potential rule conflicts
		PotentialRuleConflicts potRuleCon = new PotentialRuleConflicts();

		/*
		 * Search node conflicts
		 */

		// Delete-Delete ((partial) duplicates) and Delete-Use
		if (!predecessorDeleteNodes.isEmpty()) {
			if (!successorDeleteNodes.isEmpty()) {
				Set<PotentialNodeConflict> deleteDeleteNodePotCons = findDeleteDeleteNodeConflicts(
						predecessorDeleteNodes, successorDeleteNodes);

				for (PotentialNodeConflict pnc : deleteDeleteNodePotCons) {
					pnc.setSourceRule(predecessorEditRule);
					pnc.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPNCs(deleteDeleteNodePotCons);
			}
			if (!successorUseNodes.isEmpty()) {
				Set<PotentialNodeConflict> deleteUseNodePotCons = findDeleteUseNodeConflicts(predecessorDeleteNodes,
						successorUseNodes);

				for (PotentialNodeConflict pnc : deleteUseNodePotCons) {
					pnc.setSourceRule(predecessorEditRule);
					pnc.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPNCs(deleteUseNodePotCons);
			}
		}

		// Create-Create ((partial) duplicates) and Create-Forbid
		if (!predecessorCreateNodes.isEmpty()) {
			if (!successorCreateNodes.isEmpty()) {
				Set<PotentialNodeConflict> createCreateNodePotCons = findCreateCreateNodeConflicts(
						predecessorCreateNodes, successorCreateNodes);
				for (PotentialNodeConflict pnc : createCreateNodePotCons) {
					pnc.setSourceRule(predecessorEditRule);
					pnc.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPNCs(createCreateNodePotCons);
			}
			if (!successorForbidNodes.isEmpty()) {
				Set<PotentialNodeConflict> createForbidNodePotCons = findCreateForbidNodeConflicts(
						predecessorCreateNodes, successorForbidNodes);

				for (PotentialNodeConflict pnc : createForbidNodePotCons) {
					pnc.setSourceRule(predecessorEditRule);
					pnc.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPNCs(createForbidNodePotCons);
			}
		}

		/*
		 * Search edge conflicts
		 */

		// Delete-Delete ((partial) duplicates) and Delete-Use
		if (!predecessorDeleteEdges.isEmpty()) {
			if (!successorDeleteEdges.isEmpty()) {
				Set<PotentialEdgeConflict> deleteDeleteEdgePotCons = findDeleteDeleteEdgeConflicts(
						predecessorDeleteEdges, successorDeleteEdges, potRuleCon);
				for (PotentialEdgeConflict pec : deleteDeleteEdgePotCons) {
					pec.setSourceRule(predecessorEditRule);
					pec.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPECs(deleteDeleteEdgePotCons);
			}
			if (!successorUseEdges.isEmpty()) {
				Set<PotentialEdgeConflict> deleteUseEdgePotCons = findDeleteUseEdgeConflicts(predecessorDeleteEdges,
						successorUseEdges, potRuleCon);

				for (PotentialEdgeConflict pec : deleteUseEdgePotCons) {
					pec.setSourceRule(predecessorEditRule);
					pec.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPECs(deleteUseEdgePotCons);
			}
		}

		// Create-Create ((partial) duplicates) and Create-Forbid and Create-Forbid
		// (dangling edges)
		if (!predecessorCreateEdges.isEmpty()) {
			if (!successorCreateEdges.isEmpty()) {
				Set<PotentialEdgeConflict> createCreateEdgePotConcs = findCreateCreateEdgeConflicts(
						predecessorCreateEdges, successorCreateEdges, potRuleCon);

				for (PotentialEdgeConflict pec : createCreateEdgePotConcs) {
					pec.setSourceRule(predecessorEditRule);
					pec.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPECs(createCreateEdgePotConcs);
			}
			if (!successorForbidEdges.isEmpty()) {
				Set<PotentialEdgeConflict> createForbidEdgePotConcs = findCreateForbidEdgeConflicts(
						predecessorCreateEdges, successorForbidEdges, potRuleCon);

				for (PotentialEdgeConflict pec : createForbidEdgePotConcs) {
					pec.setSourceRule(predecessorEditRule);
					pec.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPECs(createForbidEdgePotConcs);
			}
			if (!successorDeleteNodes.isEmpty()) {
				Set<PotentialDanglingEdgeConflict> createForbidDanglingEdgePotCons = findDanglingEdgeConflict(
						predecessorCreateEdges, successorDeleteNodes);
				for (PotentialDanglingEdgeConflict pdec : createForbidDanglingEdgePotCons) {
					pdec.setSourceRule(predecessorEditRule);
					pdec.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPDECs(createForbidDanglingEdgePotCons);
			}
		}

		/*
		 * Search attribute conflicts
		 */

		// Create-Forbid
		if (!predecessorCreateAttributes.isEmpty() && !successorForbidAttributes.isEmpty()) {
			Set<PotentialAttributeConflict> createForbidAttributePotCons = findCreateForbidAttributeConflicts(
					predecessorCreateAttributes, successorForbidAttributes, potRuleCon);

			for (PotentialAttributeConflict pac : createForbidAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(createForbidAttributePotCons);
		}

		// Change-Use and Change-Forbid and Change-Change
		if (!predecessorSetChangeAttributes.isEmpty()) {
			if (!successorUseAttributes.isEmpty()) {
				Set<PotentialAttributeConflict> setUseAttributePotCons = findChangeUseAttributeConflicts(
						predecessorSetChangeAttributes, successorUseAttributes, potRuleCon);

				for (PotentialAttributeConflict pac : setUseAttributePotCons) {
					pac.setSourceRule(predecessorEditRule);
					pac.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPACs(setUseAttributePotCons);
			}

			if (!successorForbidAttributes.isEmpty()) {
				Set<PotentialAttributeConflict> setForbidAttributePotCons = findChangeForbidAttributeConflicts(
						predecessorSetChangeAttributes, successorForbidAttributes, potRuleCon);

				for (PotentialAttributeConflict pac : setForbidAttributePotCons) {
					pac.setSourceRule(predecessorEditRule);
					pac.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPACs(setForbidAttributePotCons);
			}

			if (!successorSetChangeAttributes.isEmpty()) {
				Set<PotentialAttributeConflict> changeChangeAttributePotCons = findChangeChangeAttributeConflicts(
						predecessorSetChangeAttributes, successorSetChangeAttributes, potRuleCon);

				for (PotentialAttributeConflict pac : changeChangeAttributePotCons) {
					pac.setSourceRule(predecessorEditRule);
					pac.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPACs(changeChangeAttributePotCons);
			}

		}

		return potRuleCon;
	}

	/*
	 * Nodes
	 */

	/**
	 * Checks all nodes for Delete-Delete conflicts (special kind of delete-use for
	 * identifying (partial) duplicates).
	 * 
	 * @param deletePredecessors Nodes on LHS only (<<delete>>).
	 * @param deleteSuccessors   Nodes on LHS only (<<delete>>).
	 * 
	 * @return All potential Delete-Delete node conflicts.
	 */
	protected Set<PotentialNodeConflict> findDeleteDeleteNodeConflicts(Collection<Node> deletePredecessors,
			Collection<Node> deleteSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : deletePredecessors) {
			for (Node successorNode : deleteSuccessors) {
				if (isDeleteDeleteConflict(predecessorNode, successorNode)) {
					// delete-delete conflict found
					PotentialNodeConflict potCon = rbFactory.createPotentialNodeConflict();
					potCon.setSourceNode(predecessorNode);
					potCon.setTargetNode(successorNode);
					potCon.setKind(PotentialConflictKind.DELETE_DELETE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two nodes for a Delete-Delete conflict (special kind of delete-use for
	 * identifying (partial) duplicates).
	 * 
	 * @param deletePredecessor Node is on LHS only (<<delete>>).
	 * @param deleteSuccessor   Node is on LHS only (<<delete>>).
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isDeleteDeleteConflict(Node deletePredecessor, Node deleteSuccessor) {

		assert (isDeletionNode(deletePredecessor)) : "Input Assertion failed: deletion node expected!";
		assert (isDeletionNode(deleteSuccessor)) : "Input Assertion failed: deletion node expected!";

		/*
		 * Delete-Node-Type + Delete-Node-Sub-Types + Delete-Node-Super-Types ==
		 * Delete-Node-Type
		 */

		boolean superType = deletePredecessor.getType().getEAllSuperTypes().contains(deleteSuccessor.getType());
		boolean directType = deletePredecessor.getType() == deleteSuccessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(deletePredecessor.getType())
				.contains(deleteSuccessor.getType());
		return directType || superType || subType;
	}

	/**
	 * Checks all nodes for Delete-Use conflicts.
	 * 
	 * @param deletePredecessors Nodes on LHS only (<<delete>>).
	 * @param useSuccessors      Nodes on LHS and RHS or PAC (<<preserve>> or
	 *                           <<require>>).
	 * 
	 * @return All potential Delete-Use node conflicts.
	 */
	protected Set<PotentialNodeConflict> findDeleteUseNodeConflicts(Collection<Node> deletePredecessors,
			Collection<Node> useSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : deletePredecessors) {
			for (Node successorNode : useSuccessors) {
				if (isDeleteUseConflict(predecessorNode, successorNode)) {
					// delete-use conflict found
					PotentialNodeConflict potCon = rbFactory.createPotentialNodeConflict();
					potCon.setSourceNode(predecessorNode);
					potCon.setTargetNode(successorNode);
					potCon.setKind(PotentialConflictKind.DELETE_USE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two nodes for a Delete-Use conflict.
	 * 
	 * @param deletePredecessor Node is on LHS only (<<delete>>).
	 * @param useSuccessor      Node is on LHS and RHS or PAC (<<preserve>> or
	 *                          <<require>>).
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isDeleteUseConflict(Node deletePredecessor, Node useSuccessor) {

		assert (isDeletionNode(deletePredecessor)) : "Input Assertion failed: deletion node expected!";
		assert (isPreservedNode(useSuccessor)
				|| isRequireNode(useSuccessor)) : "Input Assertion failed: preserved or required node expected!";

		/*
		 * Delete-Node-Type + Delete-Node-Sub-Types + Delete-Node-Super-Types ==
		 * Delete-Node-Type
		 */

		boolean superType = deletePredecessor.getType().getEAllSuperTypes().contains(useSuccessor.getType());
		boolean directType = deletePredecessor.getType() == useSuccessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(deletePredecessor.getType()).contains(useSuccessor.getType());
		return directType || superType || subType;
	}

	/**
	 * Checks all nodes for Create-Create conflicts (special kind of create-forbid
	 * for identifying (partial) duplicates).
	 * 
	 * @param createPredecessors Nodes on RHS only (<<create>>).
	 * @param createSuccessors   Nodes on RHS only (<<create>>).
	 * 
	 * @return All potential Create-Create node conflicts.
	 */
	protected Set<PotentialNodeConflict> findCreateCreateNodeConflicts(Collection<Node> createPredecessors,
			Collection<Node> createSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : createPredecessors) {
			for (Node successorNode : createSuccessors) {
				if (isCreateCreateConflict(predecessorNode, successorNode)) {
					// create-create conflict found
					PotentialNodeConflict potCon = rbFactory.createPotentialNodeConflict();
					potCon.setSourceNode(predecessorNode);
					potCon.setTargetNode(successorNode);
					potCon.setKind(PotentialConflictKind.CREATE_CREATE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two nodes for a Create-Forbid (create-create) conflict (special kind
	 * of create-forbid for identifying (partial) duplicates).
	 * 
	 * @param lhsPredecessor Node is on RHS (<<create>>).
	 * @param lhsSuccessor   Node is on RHS (<<create>>).
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isCreateCreateConflict(Node createPredecessor, Node createSuccessor) {

		assert (isCreationNode(createPredecessor)) : "Input Assertion failed: creation node expected!";
		assert (isCreationNode(createSuccessor)) : "Input Assertion failed: creation node expected!";

		/*
		 * Create-Node-Type == Create-Node-Type, we do not consider super and sub-types
		 */

		boolean superType = createPredecessor.getType().getEAllSuperTypes().contains(createSuccessor.getType());
		boolean directType = createPredecessor.getType() == createSuccessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(createPredecessor.getType())
				.contains(createSuccessor.getType());
		return directType || superType || subType;
	}

	/**
	 * Checks all nodes for a Create-Forbid conflict.
	 * 
	 * @param createPredecessors Nodes on RHS only (<<create>>)
	 * @param forbidSuccessors   Nodes from NAC (<<forbid>>)
	 * 
	 * @return All potential Create-Forbid node conflicts.
	 */
	protected Set<PotentialNodeConflict> findCreateForbidNodeConflicts(Collection<Node> createPredecessors,
			Collection<Node> forbidSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : createPredecessors) {
			for (Node successorNode : forbidSuccessors) {

				if (isCreateForbidConflict(predecessorNode, successorNode)) {
					// create-forbid conflict found
					PotentialNodeConflict potDep = rbFactory.createPotentialNodeConflict();

					potDep.setSourceNode(predecessorNode);
					potDep.setTargetNode(successorNode);
					potDep.setKind(PotentialConflictKind.CREATE_FORBID);

					potCons.add(potDep);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks tow nodes for a Create-Forbid conflict.
	 * 
	 * @param createPredecessor Nodes on RHS only (<<create>>)
	 * @param forbidSuccessor   Nodes from NAC (<<forbid>>)
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isCreateForbidConflict(Node createPredecessor, Node forbidSuccessor) {

		assert (isCreationNode(createPredecessor)) : "Input Assertion failed: creation node expected!";
		assert (isForbiddenNode(forbidSuccessor)) : "Input Assertion failed: forbidden node expected!";

		/*
		 * Forbid-Node-Type + Forbid-Node-Sub-Types + Forbid-Node-Super-Types ==
		 * Create-Node-Type
		 */

		boolean superType = forbidSuccessor.getType().getEAllSuperTypes().contains(createPredecessor.getType());
		boolean directType = forbidSuccessor.getType() == createPredecessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(forbidSuccessor.getType())
				.contains(createPredecessor.getType());
		return directType || superType || subType;
	}

	/*
	 * Edges
	 */

	/**
	 * Checks all edges for Delete-Delete conflicts (special kind of delete-use for
	 * identifying (partial) duplicates).
	 * 
	 * @param deletePredecessors Edges on LHS only (<<delete>>).
	 * @param deleteSuccessors   Edges on LHS only (<<delete>>).
	 * @param potRuleCon         potential conflicts
	 * 
	 * @return All potential Delete-use edge conflicts.
	 */
	protected Set<PotentialEdgeConflict> findDeleteDeleteEdgeConflicts(Collection<Edge> deletePredecessors,
			Collection<Edge> deleteSuccessors, PotentialRuleConflicts potRuleCon) {

		Set<PotentialEdgeConflict> potCons = new HashSet<>();
		for (Edge predecessorEdge : deletePredecessors) {
			for (Edge successorEdge : deleteSuccessors) {
				if (isDeleteDeleteConflict(predecessorEdge, successorEdge, potRuleCon)) {
					// Delete-Delete dependence found
					PotentialEdgeConflict potCon = rbFactory.createPotentialEdgeConflict();
					potCon.setSourceEdge(successorEdge);
					potCon.setTargetEdge(predecessorEdge);
					potCon.setKind(PotentialConflictKind.DELETE_DELETE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two edges for a Delete-Delete conflict (special kind of delete-use for
	 * identifying (partial) duplicates).
	 * 
	 * @param deletePredecessor Edge is on LHS only (<<delete>>)
	 * @param deleteSuccessor   Edge is on LHS only (<<delete>>).
	 * @param potRuleCon        potential conflicts
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isDeleteDeleteConflict(Edge deletePredecessor, Edge deleteSuccessor,
			PotentialRuleConflicts potRuleCon) {

		assert (isDeletionEdge(deletePredecessor)) : "Input Assertion failed: deletion edge expected!";
		assert (isDeletionEdge(deleteSuccessor)) : "Input Assertion failed: deletion edge expected!";

		if (deletePredecessor.getType() != deleteSuccessor.getType()) {
			return false;
		}

		Node deletePredecessorSrc = deletePredecessor.getSource();
		Node deletePredecessorTgt = deletePredecessor.getTarget();
		Node useSuccesorSrc = deleteSuccessor.getSource();
		Node useSuccesorTgt = deleteSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isDeletionNode(deletePredecessorSrc)) {
			srcOK = hasPotentialNodeConflict(potRuleCon, deletePredecessorSrc, useSuccesorSrc);
		} else {
			assert isPreservedNode(deletePredecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isDeletionNode(deletePredecessorTgt)) {
			tgtOK = hasPotentialNodeConflict(potRuleCon, deletePredecessorTgt, useSuccesorTgt);
		} else {
			assert isPreservedNode(deletePredecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}

	/**
	 * Checks all edges for Delete-Use conflicts.
	 * 
	 * @param deletePredecessors Edges on LHS only (<<delete>>).
	 * @param useSuccessors      Edges on LHS and RHS or PAC (<<preserver>> or
	 *                           <<require>>).
	 * @param potRuleCon         potential conflicts
	 * 
	 * @return All potential Delete-use edge conflicts.
	 */
	protected Set<PotentialEdgeConflict> findDeleteUseEdgeConflicts(Collection<Edge> deletePredecessors,
			Collection<Edge> useSuccessors, PotentialRuleConflicts potRuleCon) {

		Set<PotentialEdgeConflict> potCons = new HashSet<>();
		for (Edge predecessorEdge : deletePredecessors) {
			for (Edge successorEdge : useSuccessors) {
				if (isDeleteUseConflict(predecessorEdge, successorEdge, potRuleCon)) {
					// Delete-Use dependence found
					PotentialEdgeConflict potCon = rbFactory.createPotentialEdgeConflict();
					potCon.setSourceEdge(successorEdge);
					potCon.setTargetEdge(predecessorEdge);
					potCon.setKind(PotentialConflictKind.DELETE_USE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two edges for a Delete-Use conflict.
	 * 
	 * @param deletePredecessor Edge is on LHS only (<<delete>>)
	 * @param useSuccessor      Edge is on LHS and RHS or PAC (<<preserve>> or
	 *                          <<require>>).
	 * @param potRuleCon        potential conflicts
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isDeleteUseConflict(Edge deletePredecessor, Edge useSuccessor,
			PotentialRuleConflicts potRuleCon) {

		assert (isDeletionEdge(deletePredecessor)) : "Input Assertion failed: deletion edge expected!";
		assert (isPreservedEdge(useSuccessor)
				|| isRequireEdge(useSuccessor)) : "Input Assertion failed: preserved or required edge expected!";

		if (deletePredecessor.getType() != useSuccessor.getType()) {
			return false;
		}

		Node deletePredecessorSrc = deletePredecessor.getSource();
		Node deletePredecessorTgt = deletePredecessor.getTarget();
		Node useSuccesorSrc = useSuccessor.getSource();
		Node useSuccesorTgt = useSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isDeletionNode(deletePredecessorSrc)) {
			srcOK = hasPotentialNodeConflict(potRuleCon, deletePredecessorSrc, useSuccesorSrc);
		} else {
			assert isPreservedNode(deletePredecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isDeletionNode(deletePredecessorTgt)) {
			tgtOK = hasPotentialNodeConflict(potRuleCon, deletePredecessorTgt, useSuccesorTgt);
		} else {
			assert isPreservedNode(deletePredecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}

	/**
	 * Checks all edges for Create-Create conflicts (special kind of create-forbid
	 * for identifying (partial) duplicates).
	 * 
	 * @param createPredecessors Edge is on RHS only (<<create>>)
	 * @param createSuccessor    Edge is on RHS only (<<create>>)
	 * @param potRuleCon         potential conflicts
	 * 
	 * @return All potential Create-Forbid (create-create) edge conflicts.
	 */
	protected Set<PotentialEdgeConflict> findCreateCreateEdgeConflicts(Collection<Edge> createPredecessors,
			Collection<Edge> createSuccessor, PotentialRuleConflicts potRuleCon) {

		Set<PotentialEdgeConflict> potCons = new HashSet<>();
		for (Edge successorEdge : createSuccessor) {
			for (Edge predecessorEdge : createPredecessors) {
				if (isCreateCreateConflict(predecessorEdge, successorEdge, potRuleCon)) {
					// Create-Forbid conflict found
					PotentialEdgeConflict potCon = rbFactory.createPotentialEdgeConflict();
					potCon.setSourceEdge(predecessorEdge);
					potCon.setTargetEdge(successorEdge);
					potCon.setKind(PotentialConflictKind.CREATE_CREATE);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two edges for a Create-Forbid (create-create) conflict (special kind
	 * of create-forbid for identifying (partial) duplicates).
	 * 
	 * @param createPredecessor Edge is on RHS only (<<create>>)
	 * @param createSuccessor   Edge is on RHS only (<<create>>)
	 * @param potRuleCon        potential conflicts
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isCreateCreateConflict(Edge createPredecessor, Edge createSuccessor,
			PotentialRuleConflicts potRuleCon) {
		assert (isCreationEdge(createPredecessor)) : "Input Assertion failed: creation edge expected!";
		assert (isCreationEdge(createSuccessor)) : "Input Assertion failed: creation edge expected!";

		if (createPredecessor.getType() != createSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = createPredecessor.getSource();
		Node predecessorTgt = createPredecessor.getTarget();
		Node succesorSrc = createSuccessor.getSource();
		Node succesorTgt = createSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isCreationNode(predecessorSrc)) {
			srcOK = hasPotentialNodeConflict(potRuleCon, predecessorSrc, succesorSrc);
		} else {
			assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isCreationNode(predecessorTgt)) {
			tgtOK = hasPotentialNodeConflict(potRuleCon, predecessorTgt, succesorTgt);
		} else {
			assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}

	/**
	 * Checks all edges for Create-Forbid conflicts.
	 * 
	 * @param createPredecessors Edge is on RHS only (<<create>>)
	 * @param forbidSuccessors   Edge is from NAC (<<forbid>>)
	 * @param potRuleCon         potential conflicts
	 * 
	 * @return All potential Create-Forbid (create-forbid) edge conflicts.
	 */
	protected Set<PotentialEdgeConflict> findCreateForbidEdgeConflicts(Collection<Edge> createPredecessors,
			Collection<Edge> forbidSuccessors, PotentialRuleConflicts potRuleCon) {

		Set<PotentialEdgeConflict> potCons = new HashSet<>();
		for (Edge successorEdge : forbidSuccessors) {
			for (Edge predecessorEdge : createPredecessors) {
				if (isCreateForbidConflict(predecessorEdge, successorEdge, potRuleCon)) {
					// Create-Forbid conflict found
					PotentialEdgeConflict potCon = rbFactory.createPotentialEdgeConflict();
					potCon.setSourceEdge(predecessorEdge);
					potCon.setTargetEdge(successorEdge);
					potCon.setKind(PotentialConflictKind.CREATE_FORBID);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two edges for a Create-Forbid conflict.
	 * 
	 * @param createPredecessor Edge is on RHS only (<<create>>)
	 * @param forbidSuccessors  Edge is from NAC (<<forbid>>)
	 * @param potRuleCon        potential conflicts
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isCreateForbidConflict(Edge createPredecessor, Edge forbidSuccessor,
			PotentialRuleConflicts potRuleCon) {
		assert (isCreationEdge(createPredecessor)) : "Input Assertion failed: creation edge expected!";
		assert (isForbiddenEdge(forbidSuccessor)) : "Input Assertion failed: forbidden edge expected!";

		if (createPredecessor.getType() != forbidSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = createPredecessor.getSource();
		Node predecessorTgt = createPredecessor.getTarget();
		Node succesorSrc = forbidSuccessor.getSource();
		Node succesorTgt = forbidSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isCreationNode(predecessorSrc)) {
			srcOK = hasPotentialNodeConflict(potRuleCon, predecessorSrc, succesorSrc);
		} else {
			assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isCreationNode(predecessorTgt)) {
			tgtOK = hasPotentialNodeConflict(potRuleCon, predecessorTgt, succesorTgt);
		} else {
			assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}

	/**
	 * Checks all potential dangling edges conflicts (special kind of Create-Forbid
	 * conflict).
	 * 
	 * @param createPredecessors Edge is on RHS only (<<create>>)
	 * @param deleteSuccessors   Node is on LHS only (<<delete>>)
	 * @return All potential dangling edge conflicts.
	 */
	protected Set<PotentialDanglingEdgeConflict> findDanglingEdgeConflict(Collection<Edge> createPredecessors,
			Collection<Node> deleteSuccessors) {
		Set<PotentialDanglingEdgeConflict> potCons = new HashSet<>();
		for (Node deleteSuccessor : deleteSuccessors) {
			for (Edge predecessorEdge : createPredecessors) {
				if (isDanglingEdgeConflict(predecessorEdge, deleteSuccessor)) {
					// Create-Forbid conflict found
					PotentialDanglingEdgeConflict potCon = rbFactory.createPotentialDanglingEdgeConflict();
					potCon.setCreationEdge(predecessorEdge);
					potCon.setDeletionNode(deleteSuccessor);
					potCon.setKind(PotentialConflictKind.CREATE_FORBID);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks if a creation edge leads to dangling edges of a deletion node (special
	 * kind of Create-Forbid conflict).
	 * 
	 * @param createPredecessor Edge is on RHS only (<<create>>)
	 * @param deleteSuccessor   Node is on LHS only (<<delete>>)
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isDanglingEdgeConflict(Edge createPredecessor, Node deleteSuccessor) {
		assert isCreationEdge(createPredecessor) : "Input Assertion failed: creation edge expected!";
		assert isDeletionNode(deleteSuccessor) : "Input Assertion failed: deletion node expected!";

		for (EReference eReference : deleteSuccessor.getType().getEAllReferences()) {
			if (deleteSuccessor.getOutgoing(eReference).isEmpty()) {
				return true;
			}
		}

		Collection<Setting> settings = EcoreUtil.UsageCrossReferencer.find(deleteSuccessor.getType(),
				deleteSuccessor.getType().eResource());
		for (Setting setting : settings) {
			if (setting.getEObject() == createPredecessor.getType()) {
				if (deleteSuccessor.getIncoming(createPredecessor.getType()).isEmpty()) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Attributes
	 */

	// delete-delete attribute conflicts are already covered by
	// delete-delete node conflicts but must be considered when analyzing
	// (partial) duplicates

	// create-create attribute conflicts are already covered by
	// create-create node conflicts but must be considered when analyzing (partial)
	// duplicates

	/**
	 * Checks all attributes for Create-Forbid conflicts (the attributes are set
	 * when creating the node, such that a negative precondition isn't fulfilled
	 * anymore)
	 * 
	 * @param predecessorCreateAttributes Attribute of a node that is on RHS only
	 *                                    (<<create>>)
	 * @param successorForbidAttributes   Attribute from NAC (<<forbid>>)
	 * @param potRuleCon                  potential conflicts
	 * @return All Create-Forbid attribute conflicts.
	 */
	protected Set<PotentialAttributeConflict> findCreateForbidAttributeConflicts(
			Collection<Attribute> predecessorCreateAttributes, Collection<Attribute> successorForbidAttributes,
			PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potCons = new HashSet<>();

		for (Attribute createPredecessor : predecessorCreateAttributes) {
			for (Attribute forbidSuccessor : successorForbidAttributes) {
				if (isCreateForbidConflict(createPredecessor, forbidSuccessor, potRuleCon)) {
					// Create-Forbid conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(createPredecessor);
					potcon.setTargetAttribute(forbidSuccessor);
					potcon.setKind(PotentialConflictKind.CREATE_FORBID);
					potCons.add(potcon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks all attributes for Create-Forbid conflicts (the attributes are set
	 * when creating the node, such that a negative precondition isn't fulfilled
	 * anymore)
	 * 
	 * @param createPredecessor Attribute of a node that is on RHS only (<<create>>)
	 * @param forbidSuccessor   Attribute from NAC (<<forbid>>)
	 * @param potRuleCon        potential conflicts
	 * @return All Create-Forbid attribute conflicts.
	 */
	protected boolean isCreateForbidConflict(Attribute createPredecessor, Attribute forbidSuccessor,
			PotentialRuleConflicts potRuleCon) {
		assert (isCreationAttribute(createPredecessor)
				&& isCreationNode(createPredecessor.getNode())) : "Input Assertion Failed!";
		assert (isForbiddenAttribute(forbidSuccessor)) : "Input Assertion Failed!";

		if (!(createPredecessor.getType().equals(forbidSuccessor.getType())
				&& isAssignableTo(createPredecessor.getNode().getType(), forbidSuccessor.getNode().getType()))) {
			return false;
		}

		// Attribute case differentiation precondition is not fulfilled?
		if (isLiteral(createPredecessor) && isLiteral(forbidSuccessor)) {
			// Literals are equal
			return createPredecessor.getValue().equals(forbidSuccessor.getValue());
		}
		// Predecessor or successor is variable!
		return true;
	}

	/**
	 * Checks all attributes for Change-Use conflicts
	 * 
	 * @param setPredecessors Attribute is RHS only or changing value (<<set>>)
	 * @param useSuccessors   is LHS or required (<<delete>>, <<preserve>>,
	 *                        <<required>>)
	 * @param potRuleCon
	 * @return
	 */
	protected Set<PotentialAttributeConflict> findChangeUseAttributeConflicts(Collection<Attribute> setPredecessors,
			Collection<Attribute> useSuccessors, PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potConss = new HashSet<>();

		for (Attribute rhsPredecessorAttribute : setPredecessors) {
			for (Attribute lhsSuccessorAttribute : useSuccessors) {
				if (isChangeUseConflict(rhsPredecessorAttribute, lhsSuccessorAttribute, potRuleCon)) {
					// Create-Use conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(rhsPredecessorAttribute);
					potcon.setTargetAttribute(lhsSuccessorAttribute);
					potcon.setKind(PotentialConflictKind.CHANGE_USE);
					potConss.add(potcon);
				}
			}
		}
		return potConss;
	}

	/**
	 * 
	 * @param setPredecessor
	 * @param useSuccessor
	 * @param potRulecon
	 * @return
	 */
	private boolean isChangeUseConflict(Attribute setPredecessor, Attribute useSuccessor,
			PotentialRuleConflicts potRulecon) {

		assert (isCreationAttribute(setPredecessor) || isChangingAttribute(setPredecessor)) : "Input Assertion Failed!";
		assert (isDeletionAttribute(useSuccessor) || isChangingAttribute(useSuccessor)
				|| isPreservedAttribute(useSuccessor) || isRequireAttribute(useSuccessor)) : "Input Assertion Failed!";

		if (!(setPredecessor.getType().equals(useSuccessor.getType())
				&& isAssignableTo(setPredecessor.getNode().getType(), useSuccessor.getNode().getType()))) {
			return false;
		}

		// Attribute case differentiation precondition is not fulfilled?
		if (isCreationAttribute(setPredecessor) && isLiteral(setPredecessor) && isLiteral(useSuccessor)) {
			// Literals are equal
			return !setPredecessor.getValue().equals(useSuccessor.getValue());

		} else if (isChangingAttribute(setPredecessor) && isLiteral(getRemoteAttribute(setPredecessor))
				&& isLiteral(useSuccessor)) {
			// Predecessor or successor is variable!
			return getRemoteAttribute(setPredecessor).getValue().equals(useSuccessor.getValue());
		}

		return true;
	}

	/**
	 * 
	 * @param setPredecessors
	 * @param forbidSuccessors
	 * @param potRuleCon
	 * @return
	 */
	protected Set<PotentialAttributeConflict> findChangeForbidAttributeConflicts(Collection<Attribute> setPredecessors,
			Collection<Attribute> forbidSuccessors, PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potCons = new HashSet<>();

		for (Attribute setPredecessor : setPredecessors) {
			for (Attribute forbidSuccessor : forbidSuccessors) {
				if (isChangeForbidConflict(setPredecessor, forbidSuccessor, potRuleCon)) {
					// Set-Forbid conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(setPredecessor);
					potcon.setTargetAttribute(forbidSuccessor);
					potcon.setKind(PotentialConflictKind.CHANGE_FORBID);
					potCons.add(potcon);
				}
			}
		}
		return potCons;
	}

	/**
	 * 
	 * @param setPredecessor
	 * @param forbidSuccessor
	 * @param potRuleCon
	 * @return
	 */
	protected boolean isChangeForbidConflict(Attribute setPredecessor, Attribute forbidSuccessor,
			PotentialRuleConflicts potRuleCon) {
		assert (isCreationAttribute(setPredecessor) || isChangingAttribute(setPredecessor)) : "Input Assertion Failed!";
		assert (isForbiddenAttribute(forbidSuccessor)) : "Input Assertion Failed!";

		// Attributes have the same type
		if (setPredecessor.getType().equals(forbidSuccessor.getType())) {

			// is predecessor nodeType assignable to successor nodeType?
			if (isAssignableTo(setPredecessor.getNode().getType(), forbidSuccessor.getNode().getType())) {
				// Attribute case differentiation precondition is not fulfilled?
				if (isLiteral(setPredecessor) && isLiteral(forbidSuccessor)) {
					// Literals are equal
					return setPredecessor.getValue().equals(forbidSuccessor.getValue());
				}
				return true;
			}

		}

		return false;
	}

	protected Set<PotentialAttributeConflict> findChangeChangeAttributeConflicts(
			Collection<Attribute> predecessorSetAttributes, Collection<Attribute> successorSetAttributes,
			PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potCons = new HashSet<>();

		for (Attribute setPredecessor : successorSetAttributes) {
			for (Attribute setSuccessor : successorSetAttributes) {
				if (isChangeChangeConflict(setPredecessor, setSuccessor, potRuleCon)) {
					// Set-Forbid conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(setPredecessor);
					potcon.setTargetAttribute(setSuccessor);
					potcon.setKind(PotentialConflictKind.CHANGE_CHANGE);
					potCons.add(potcon);
				}
			}
		}
		return potCons;
	}

	protected boolean isChangeChangeConflict(Attribute setPredecessor, Attribute setSuccessor,
			PotentialRuleConflicts potRuleCon) {
		assert (isCreationAttribute(setPredecessor)) : "Input Assertion Failed!";
		assert (isCreationAttribute(setSuccessor)) : "Input Assertion Failed!";

		if (!(setPredecessor.getType().equals(setSuccessor.getType())
				&& isAssignableTo(setPredecessor.getNode().getType(), setSuccessor.getNode().getType()))) {
			return false;
		}

//		// Attribute case differentiation precondition is not fulfilled?
//		if (isLiteral(setPredecessor) && isLiteral(setSuccessor)) {
//			// Literals are equal
//			return !setPredecessor.getValue().equals(setSuccessor.getValue());
//		}
		// Predecessor or successor is variable!
		return true;

	}

	/*
	 * Utils
	 */

	/**
	 * Tests if the given list contains a potential conflict between the two nodes.
	 * 
	 * @param potRuleCon The list of potential conflicts.
	 * @param nodeA      The first node to test.
	 * @param nodeB      The second node to test.
	 * @return <code>true</code> if the list contains a potential conflict;
	 *         <code>false</code> otherwise.
	 */
	protected boolean hasPotentialNodeConflict(PotentialRuleConflicts potRuleDep, Node nodeA, Node nodeB) {
		for (PotentialNodeConflict potCon : potRuleDep.getPotentialNodeConflicts()) {
			if ((potCon.getSourceNode().equals(nodeA) || potCon.getTargetNode().equals(nodeA))
					&& (potCon.getSourceNode().equals(nodeB) || potCon.getTargetNode().equals(nodeB))) {
				return true;
			}
		}
		return false;
	}
}