package org.sidiff.editrule.analysis.criticalpairs;

import static org.sidiff.common.emf.access.EMFMetaAccess.assignable;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.collections.CollectionUtil;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
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

	private Map<Resource,Map<EClass,Set<EReference>>> referenceTypeIndex = new HashMap<>();

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
		Collection<Node> successorPreserveNodes = successor.getPreserveNodes().stream().map(pair -> pair.getLhsNode())
				.collect(Collectors.toSet());
		Collection<Node> successorRequireNodes = successor.getRequireNodes();
		Collection<Node> successorForbidNodes = successor.getForbidNodes();
		Collection<Node> successorUseNodes = new HashSet<Node>(successorPreserveNodes);
		successorUseNodes.addAll(successorRequireNodes);

		// Get edges
		Collection<Edge> predecessorCreateEdges = predecessor.getCreateEdges();
		Collection<Edge> predecessorDeleteEdges = predecessor.getDeleteEdges();

		Collection<Edge> successorCreateEdges = successor.getCreateEdges();
		Collection<Edge> successorDeleteEdges = successor.getDeleteEdges();
		Collection<Edge> successorPreserveEdges = successor.getPreserveEdges().stream().map(pair -> pair.getLhsEdge())
				.collect(Collectors.toSet());
		Collection<Edge> successorRequireEdges = successor.getRequireEdges();
		Collection<Edge> successorForbidEdges = successor.getForbidEdges();
		Collection<Edge> successorUseEdges = new HashSet<Edge>(successorPreserveEdges);
		successorUseEdges.addAll(successorRequireEdges);

		// Get attributes
		Collection<Attribute> predecessorCreateAttributes = getCreationAttributes(predecessor.getRule());
		Collection<Attribute> predecessorSetAttributes = predecessor.getSetAttributes();
		Collection<Attribute> predecessorChangeAttributes = predecessor.getChangeAttributes().stream()
				.map(pair -> pair.getRhsAttribute()).collect(Collectors.toSet());
		Collection<Attribute> predecessorSetChangeAttributes = new HashSet<Attribute>(predecessorSetAttributes);
		predecessorSetChangeAttributes.addAll(predecessorChangeAttributes);

		Collection<Attribute> successorDeleteAttributes = getDeletionAttributes(successor.getRule());
		Collection<Attribute> successorSetAttributes = successor.getSetAttributes();
		Collection<Attribute> successorChangeAttributes = successor.getChangeAttributes().stream()
				.map(pair -> pair.getRhsAttribute()).collect(Collectors.toSet());
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

		if (!predecessorDeleteNodes.isEmpty()) {
			// Delete-Delete ((partial) duplicates)
			if (!successorDeleteNodes.isEmpty()) {
				Set<PotentialNodeConflict> deleteDeleteNodePotCons = findDeleteDeleteNodeConflicts(
						predecessorDeleteNodes, successorDeleteNodes);

				for (PotentialNodeConflict pnc : deleteDeleteNodePotCons) {
					pnc.setSourceRule(predecessorEditRule);
					pnc.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPNCs(deleteDeleteNodePotCons);
			}
			// Delete-Use
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

		if (!predecessorCreateNodes.isEmpty()) {
			// Create-Create ((partial) duplicates)
			if (!successorCreateNodes.isEmpty()) {
				Set<PotentialNodeConflict> createCreateNodePotCons = findCreateCreateNodeConflicts(
						predecessorCreateNodes, successorCreateNodes);
				for (PotentialNodeConflict pnc : createCreateNodePotCons) {
					pnc.setSourceRule(predecessorEditRule);
					pnc.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPNCs(createCreateNodePotCons);
			}
			// Create-Forbid
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

		if (!predecessorDeleteEdges.isEmpty()) {
			// Delete-Delete ((partial) duplicates)
			if (!successorDeleteEdges.isEmpty()) {
				Set<PotentialEdgeConflict> deleteDeleteEdgePotCons = findDeleteDeleteEdgeConflicts(
						predecessorDeleteEdges, successorDeleteEdges, potRuleCon);
				for (PotentialEdgeConflict pec : deleteDeleteEdgePotCons) {
					pec.setSourceRule(predecessorEditRule);
					pec.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPECs(deleteDeleteEdgePotCons);
			}
			// Delete-Use
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

		if (!predecessorCreateEdges.isEmpty()) {
			// Create-Create ((partial) duplicates)
			if (!successorCreateEdges.isEmpty()) {
				Set<PotentialEdgeConflict> createCreateEdgePotConcs = findCreateCreateEdgeConflicts(
						predecessorCreateEdges, successorCreateEdges, potRuleCon);

				for (PotentialEdgeConflict pec : createCreateEdgePotConcs) {
					pec.setSourceRule(predecessorEditRule);
					pec.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPECs(createCreateEdgePotConcs);
			}
			// Create-Forbid
			if (!successorForbidEdges.isEmpty()) {
				Set<PotentialEdgeConflict> createForbidEdgePotConcs = findCreateForbidEdgeConflicts(
						predecessorCreateEdges, successorForbidEdges, potRuleCon);

				for (PotentialEdgeConflict pec : createForbidEdgePotConcs) {
					pec.setSourceRule(predecessorEditRule);
					pec.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPECs(createForbidEdgePotConcs);
			}
			// Create-Forbid (dangling edges)
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

		if (!predecessorSetChangeAttributes.isEmpty()) {
			// Change-Use
			if (!successorUseAttributes.isEmpty()) {
				Set<PotentialAttributeConflict> setUseAttributePotCons = findChangeUseAttributeConflicts(
						predecessorSetChangeAttributes, successorUseAttributes, potRuleCon);

				for (PotentialAttributeConflict pac : setUseAttributePotCons) {
					pac.setSourceRule(predecessorEditRule);
					pac.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPACs(setUseAttributePotCons);
			}
			// Change-Forbid
			if (!successorForbidAttributes.isEmpty()) {
				Set<PotentialAttributeConflict> setForbidAttributePotCons = findChangeForbidAttributeConflicts(
						predecessorSetChangeAttributes, successorForbidAttributes, potRuleCon);

				for (PotentialAttributeConflict pac : setForbidAttributePotCons) {
					pac.setSourceRule(predecessorEditRule);
					pac.setTargetRule(successorEditRule);
				}
				potRuleCon.addAllPACs(setForbidAttributePotCons);
			}
		}

		// Change-Change
		if (!predecessorSetAttributes.isEmpty() && !successorSetAttributes.isEmpty()) {
			Set<PotentialAttributeConflict> changeChangeAttributePotCons = findChangeChangeAttributeConflicts(
					predecessorSetAttributes, successorSetAttributes, potRuleCon);

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
	 * Checks all nodes for Delete-Delete conflicts (special kind of delete-use for
	 * identifying duplicates).
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
					potCon.setKind(PotentialConflictKind.DELETE_USE);
					potCon.setDuplicate(true);
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
//		return directType || superType || subType;
		
		if(directType || superType|| subType) {
			// check dangling condition
			//
			Set<Edge> successorEdges = new HashSet<Edge>(deleteSuccessor.getOutgoing());
			successorEdges.addAll(deleteSuccessor.getIncoming());
			Set<Edge> predecessorEdges = new HashSet<Edge>(deletePredecessor.getOutgoing());
			predecessorEdges.addAll(deletePredecessor.getIncoming());
			
			for(Iterator<Edge> iterator= successorEdges.iterator(); iterator.hasNext();) {
				Edge successorEdge = iterator.next();
				for (Edge predecessorEdge : predecessorEdges) {
					if (successorEdge.getType().equals(predecessorEdge.getType())) {
						iterator.remove();
					}
				}
			}
			
			return successorEdges.isEmpty();
		}
		
		return false;
	}

	/**
	 * Checks all nodes for Delete-Use conflicts except Delete-Delete conflicts.
	 * 
	 * @param deletePredecessors Nodes on LHS only (<<delete>>).
	 * @param useSuccessors      Nodes on LHS and RHS or PAC (<<preserve>> or
	 *                           <<require>>).
	 * 
	 * @return All potential Delete-Use node conflicts except Delete-Delete
	 *         conflicts.
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
					potCon.setCondition(HenshinRuleAnalysisUtilEx.isRequireNode(successorNode));
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two nodes for a Delete-Use conflict except Delete-Delete conflict.
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
		if(directType || superType|| subType) {
			// check dangling condition
			//
			Set<Edge> successorEdges = new HashSet<Edge>(useSuccessor.getOutgoing());
			successorEdges.addAll(useSuccessor.getIncoming());
			Set<Edge> predecessorEdges = new HashSet<Edge>(deletePredecessor.getOutgoing());
			predecessorEdges.addAll(deletePredecessor.getIncoming());
			
			for(Iterator<Edge> iterator= successorEdges.iterator(); iterator.hasNext();) {
				Edge successorEdge = iterator.next();
				for(Edge predecessorEdge : predecessorEdges) {
					if(successorEdge.getType().equals(predecessorEdge.getType())) {
						iterator.remove();
					}
				}
			}
			
			return successorEdges.isEmpty();
		}
		
		return false;
	}

	/**
	 * Checks all nodes for Create-Create conflicts. Note: This isn't a real
	 * potential conflict but is used for identifying duplicates. It is treated as
	 * special kind of create-forbid conflict
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
					potCon.setKind(PotentialConflictKind.CREATE_FORBID);
					potCon.setDuplicate(true);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two nodes for a Create-Create conflict. Note: This isn't a real
	 * potential conflict but is used for identifying duplicates.
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
					PotentialNodeConflict potCon = rbFactory.createPotentialNodeConflict();

					potCon.setSourceNode(predecessorNode);
					potCon.setTargetNode(successorNode);
					potCon.setKind(PotentialConflictKind.CREATE_FORBID);
					potCon.setCondition(true);
					potCons.add(potCon);
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
	 * identifying duplicates).
	 * 
	 * @param deletePredecessors Edges on LHS only (<<delete>>).
	 * @param deleteSuccessors   Edges on LHS only (<<delete>>).
	 * @param potRuleCon         potential conflicts
	 * 
	 * @return All potential Delete-Delete edge conflicts.
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
					potCon.setKind(PotentialConflictKind.DELETE_USE);
					potCon.setDuplicate(true);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two edges for a Delete-Delete conflict (special kind of delete-use for
	 * identifying duplicates).
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

		boolean assignableSrc = assignable(deletePredecessorSrc.getType(), useSuccesorSrc.getType());
		boolean assignableTgt = assignable(deletePredecessorTgt.getType(), useSuccesorTgt.getType());
		
		if(!(assignableSrc && assignableTgt)) {
			return false;
		}
		
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
	 * Checks all edges for Delete-Use conflicts except Delete-Delete conflicts.
	 * 
	 * @param deletePredecessors Edges on LHS only (<<delete>>).
	 * @param useSuccessors      Edges on LHS and RHS or PAC (<<preserver>> or
	 *                           <<require>>).
	 * @param potRuleCon         potential conflicts
	 * 
	 * @return All potential Delete-use edge conflicts except Delete-Delete
	 *         conflicts.
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
					potCon.setCondition(HenshinRuleAnalysisUtilEx.isRequireEdge(successorEdge));
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two edges for a Delete-Use conflict except Delete-Delete conflict.
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

		boolean assignableSrc = assignable(deletePredecessorSrc.getType(), useSuccesorSrc.getType());
		boolean assignableTgt = assignable(deletePredecessorTgt.getType(), useSuccesorTgt.getType());
		
		if(!(assignableSrc && assignableTgt)) {
			return false;
		}
		
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
	 * Checks all edges for Create-Create conflicts. Note: This isn't a real
	 * potential conflict but is used for identifying duplicates. It is treated as
	 * special kind of create-forbid conflict
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
					potCon.setKind(PotentialConflictKind.CREATE_FORBID);
					potCon.setDuplicate(true);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two edges for a Create-Create conflict. Note: This isn't a real
	 * potential conflict but is used for identifying (partial) duplicates.
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

		boolean assignableSrc = assignable(predecessorSrc.getType(), succesorSrc.getType());
		boolean assignableTgt = assignable(predecessorTgt.getType(), succesorTgt.getType());
		
		if(!(assignableSrc && assignableTgt)) {
			return false;
		}
		
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
					potCon.setCondition(true);
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

		boolean assignableSrc = assignable(predecessorSrc.getType(), succesorSrc.getType());
		boolean assignableTgt = assignable(predecessorTgt.getType(), succesorTgt.getType());
		
		if(!(assignableSrc && assignableTgt)) {
			return false;
		}
		
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
	 * Checks all potential dangling edge conflicts (special kind of Create-Forbid
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
	 * Checks if a creation edge leads to a dangling edge of a deletion node
	 * (special kind of Create-Forbid conflict).
	 * 
	 * @param createPredecessor Edge is on RHS only (<<create>>)
	 * @param deleteSuccessor   Node is on LHS only (<<delete>>)
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isDanglingEdgeConflict(Edge createPredecessor, Node deleteSuccessor) {
		assert isCreationEdge(createPredecessor) : "Input Assertion failed: creation edge expected!";
		assert isDeletionNode(deleteSuccessor) : "Input Assertion failed: deletion node expected!";

		EReference predecessorType = createPredecessor.getType();
		EClass successorType = deleteSuccessor.getType();

		Node srcNode = createPredecessor.getSource();
		Node tgtNode = createPredecessor.getTarget();
		
		if (assignable(srcNode.getType(), deleteSuccessor.getType()) 
				&& successorType.getEAllReferences().contains(predecessorType)
				&& deleteSuccessor.getOutgoing(predecessorType).isEmpty()) {
			return true;
		}
//		if (!deleteSuccessor.getIncoming(predecessorType).isEmpty()) {
//			return false;
//		}

		if(assignable(tgtNode.getType(), deleteSuccessor.getType())
				&& deleteSuccessor.getIncoming(predecessorType).isEmpty()){
			return referenceTypeIndex
					.computeIfAbsent(successorType.eResource(),
							resource -> CollectionUtil.asStream(resource.getAllContents())
									.filter(EReference.class::isInstance).map(EReference.class::cast)
									.collect(Collectors.groupingBy(EReference::getEReferenceType, Collectors.toSet())))
					.getOrDefault(successorType, Collections.emptySet()).contains(predecessorType);
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
					PotentialAttributeConflict potCon = rbFactory.createPotentialAttributeConflict();
					potCon.setSourceAttribute(createPredecessor);
					potCon.setTargetAttribute(forbidSuccessor);
					potCon.setKind(PotentialConflictKind.CREATE_FORBID);
					potCon.setCondition(true);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two attributes for Create-Forbid conflict (the attribute is set when
	 * creating the node, such that a negative precondition isn't fulfilled anymore)
	 * 
	 * @param createPredecessor Attribute of a node that is on RHS only (<<create>>)
	 * @param forbidSuccessor   Attribute from NAC (<<forbid>>)
	 * @param potRuleCon        potential conflicts
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
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
	 * Checks all attributes for Change-Use conflicts.
	 * 
	 * @param setChangePredecessors Attribute is RHS only or changing value
	 *                              (<<set>>)
	 * @param useSuccessors         is LHS or required (<<delete>>, <<preserve>>,
	 *                              <<required>>)
	 * @param potRuleCon            potential conflicts
	 * @return All Change-Use attribute conflicts.
	 */
	protected Set<PotentialAttributeConflict> findChangeUseAttributeConflicts(
			Collection<Attribute> setChangePredecessors, Collection<Attribute> useSuccessors,
			PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potConss = new HashSet<>();

		for (Attribute rhsPredecessorAttribute : setChangePredecessors) {
			for (Attribute lhsSuccessorAttribute : useSuccessors) {
				if (isChangeUseConflict(rhsPredecessorAttribute, lhsSuccessorAttribute, potRuleCon)) {
					// Create-Use conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(rhsPredecessorAttribute);
					potcon.setTargetAttribute(lhsSuccessorAttribute);
					potcon.setKind(PotentialConflictKind.CHANGE_USE);
					potcon.setCondition(HenshinRuleAnalysisUtilEx.isRequireAttribute(lhsSuccessorAttribute));
					potConss.add(potcon);
				}
			}
		}
		return potConss;
	}

	/**
	 * Checks two attributes for Change-Use conflict.
	 * 
	 * @param setChangePredecessor Attribute is RHS only or changing value (<<set>>)
	 * @param useSuccessor         is LHS or required (<<delete>>, <<preserve>>,
	 *                             <<required>>)
	 * @param potRulecon           potential conflicts
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	private boolean isChangeUseConflict(Attribute setChangePredecessor, Attribute useSuccessor,
			PotentialRuleConflicts potRulecon) {

		assert ((isCreationAttribute(setChangePredecessor) || isChangingAttribute(setChangePredecessor))
				&& isPreservedNode(setChangePredecessor
						.getNode())) : "Input Assertion failed: set or changing attribute in preserved node expected!";
		assert (isDeletionAttribute(useSuccessor) || isChangingAttribute(useSuccessor)
				|| isPreservedAttribute(useSuccessor) || isRequireAttribute(
						useSuccessor)) : "Input Assertion failed: deletion, changing, preserved or required attribute expected!";

		if (!(setChangePredecessor.getType().equals(useSuccessor.getType())
				&& isAssignableTo(setChangePredecessor.getNode().getType(), useSuccessor.getNode().getType()))) {
			return false;
		}

		// Attribute case differentiation precondition is not fulfilled?
		if (isCreationAttribute(setChangePredecessor) && isLiteral(setChangePredecessor) && isLiteral(useSuccessor)) {
			// Literals are equal
			return !setChangePredecessor.getValue().equals(useSuccessor.getValue());

		} else if (isChangingAttribute(setChangePredecessor) && isLiteral(getRemoteAttribute(setChangePredecessor))
				&& isLiteral(useSuccessor)) {

			return getRemoteAttribute(setChangePredecessor).getValue().equals(useSuccessor.getValue());
		}
		// Predecessor or successor is variable!
		return true;
	}

	/**
	 * Checks all attributes for Change-Forbid conflicts.
	 * 
	 * @param setChangePredecessors Attribute is RHS only or changing value
	 *                              (<<set>>)
	 * @param forbidSuccessors      Attribute from NAC (<<forbid>>)
	 * @param potRuleCon            potential conflicts
	 * @return All Change-Forbid attribute conflicts.
	 */
	protected Set<PotentialAttributeConflict> findChangeForbidAttributeConflicts(
			Collection<Attribute> setChangePredecessors, Collection<Attribute> forbidSuccessors,
			PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potCons = new HashSet<>();

		for (Attribute setPredecessor : setChangePredecessors) {
			for (Attribute forbidSuccessor : forbidSuccessors) {
				if (isChangeForbidConflict(setPredecessor, forbidSuccessor, potRuleCon)) {
					// Set-Forbid conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(setPredecessor);
					potcon.setTargetAttribute(forbidSuccessor);
					potcon.setKind(PotentialConflictKind.CHANGE_FORBID);
					potcon.setCondition(true);
					potCons.add(potcon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two attributes for Change-Forbid conflict.
	 * 
	 * @param setChangePredecessor Attribute is RHS only or changing value (<<set>>)
	 * @param forbidSuccessor      Attribute from NAC (<<forbid>>)
	 * @param potRuleCon           potential conflicts
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isChangeForbidConflict(Attribute setChangePredecessor, Attribute forbidSuccessor,
			PotentialRuleConflicts potRuleCon) {
		assert ((isCreationAttribute(setChangePredecessor) || isChangingAttribute(setChangePredecessor))
				&& isPreservedNode(setChangePredecessor
						.getNode())) : "Input Assertion failed: set or changing attribute in preserved node expected!";
		assert (isForbiddenAttribute(forbidSuccessor)) : "Input Assertion failed: forbidden attribute expected!";

		// Attributes have the same type
		if (setChangePredecessor.getType().equals(forbidSuccessor.getType())) {

			// is predecessor nodeType assignable to successor nodeType?
			if (isAssignableTo(setChangePredecessor.getNode().getType(), forbidSuccessor.getNode().getType())) {
				// Attribute case differentiation precondition is not fulfilled?
				if (isLiteral(setChangePredecessor) && isLiteral(forbidSuccessor)) {
					// Literals are equal
					return setChangePredecessor.getValue().equals(forbidSuccessor.getValue());
				}
				return true;
			}

		}

		return false;
	}

	/**
	 * Checks all attributes for Change-Change conflicts. Note: That isn't a real
	 * potential conflict but is used to detect so-called soft-conflicts, i.e. blind
	 * overwrites.
	 * 
	 * @param setPredecessors Attribute is RHS only (<<set>>)
	 * @param setSuccessors   Attribute is RHS only (<<set>>)
	 * @param potRuleCon      potential conflicts
	 * @return All Change-Change attribute conflicts.
	 */
	protected Set<PotentialAttributeConflict> findChangeChangeAttributeConflicts(Collection<Attribute> setPredecessors,
			Collection<Attribute> setSuccessors, PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potCons = new HashSet<>();

		for (Attribute setPredecessor : setPredecessors) {
			for (Attribute setSuccessor : setSuccessors) {
				if (isChangeChangeConflict(setPredecessor, setSuccessor, potRuleCon)) {
					// Set-Set conflict found
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

	/**
	 * Checks two attributes for Change-Change conflicts. Note: That isn't a real
	 * potential conflict but is used to detect so-called soft-conflicts, i.e. blind
	 * overwrites.
	 * 
	 * @param setPredecessor Attribute is RHS only or changing value (<<set>>)
	 * @param setSuccessor   Attribute is RHS only or changing value (<<set>>)
	 * @param potRuleCon     potential conflicts
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isChangeChangeConflict(Attribute setPredecessor, Attribute setSuccessor,
			PotentialRuleConflicts potRuleCon) {
		assert (isCreationAttribute(setPredecessor) && isPreservedNode(
				setPredecessor.getNode())) : "Input Assertion failed: set attribute in preserved node expected!";
		assert (isCreationAttribute(setSuccessor) && isPreservedNode(
				setSuccessor.getNode())) : "Input Assertion failed: set attribute in preserved node expected!";

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
	 * @param potRuleCons The list of potential conflicts.
	 * @param nodeA       The first node to test.
	 * @param nodeB       The second node to test.
	 * @return <code>true</code> if the list contains a potential conflict;
	 *         <code>false</code> otherwise.
	 */
	protected boolean hasPotentialNodeConflict(PotentialRuleConflicts potRuleCons, Node nodeA, Node nodeB) {
		for (PotentialNodeConflict potCon : potRuleCons.getPotentialNodeConflicts()) {
			if ((potCon.getSourceNode().equals(nodeA) || potCon.getTargetNode().equals(nodeA))
					&& (potCon.getSourceNode().equals(nodeB) || potCon.getTargetNode().equals(nodeB))) {
				return true;
			}
		}
		return false;
	}
}