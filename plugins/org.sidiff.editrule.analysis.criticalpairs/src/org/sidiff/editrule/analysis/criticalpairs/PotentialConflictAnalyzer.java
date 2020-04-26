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
import java.util.List;
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
//import org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict;
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
		Set<Node> predecessorCreateNodes = new HashSet<Node>(predecessor.getCreateNodes());
		Set<Node> predecessorDeleteNodes = new HashSet<Node>(predecessor.getDeleteNodes());

		Set<Node> successorCreateNodes = new HashSet<Node>(successor.getCreateNodes());
		Set<Node> successorDeleteNodes = new HashSet<Node>(successor.getDeleteNodes());
		Set<Node> successorPreseveNodes = new HashSet<Node>(
				successor.getPreserveNodes().stream().map(pair -> pair.getLhsNode()).collect(Collectors.toSet()));
		Set<Node> successorRequireNodes = new HashSet<Node>(successor.getRequireNodes());
		Set<Node> successorForbidNodes = new HashSet<Node>(successor.getForbidNodes());

		Set<Node> successorUseNodes = new HashSet<Node>(successorDeleteNodes);
		successorUseNodes.addAll(successorPreseveNodes);
		successorUseNodes.addAll(successorRequireNodes);

		// Get edges
		Set<Edge> predecessorCreateEdges = new HashSet<Edge>(predecessor.getCreateEdges());
		Set<Edge> predecessorDeleteEdges = new HashSet<Edge>(predecessor.getDeleteEdges());

		Set<Edge> successorCreateEdges = new HashSet<Edge>(successor.getCreateEdges());
		Set<Edge> successorDeleteEdges = new HashSet<Edge>(successor.getDeleteEdges());
		Set<Edge> successorPreserveEdges = new HashSet<Edge>(
				successor.getPreserveEdges().stream().map(pair -> pair.getLhsEdge()).collect(Collectors.toSet()));
		Set<Edge> successorRequireEdges = new HashSet<Edge>(successor.getRequireEdges());
		Set<Edge> successorForbidEdges = new HashSet<Edge>(successor.getForbidEdges());

		Set<Edge> successorUseEdges = new HashSet<Edge>(successorDeleteEdges);
		successorUseEdges.addAll(successorPreserveEdges);
		successorUseEdges.addAll(successorRequireEdges);

		// Get attributes
		Set<Attribute> predecessorCreateAttributes = new HashSet<Attribute>(
				getCreationAttributes(predecessor.getRule()));
		Set<Attribute> predecessorDeleteAttributes = new HashSet<Attribute>(
				getDeletionAttributes(predecessor.getRule()));
		Set<Attribute> predecessorSetAttributes = new HashSet<Attribute>(predecessor.getSetAttributes());
		Set<Attribute> predecessorChangeAttributes = new HashSet<Attribute>(predecessor.getChangeAttributes().stream()
				.map(pair -> pair.getRhsAttribute()).collect(Collectors.toSet()));

		Set<Attribute> successorCreateAttributes = new HashSet<Attribute>(getCreationAttributes(successor.getRule()));
		Set<Attribute> successorDeleteAttributes = new HashSet<Attribute>(getDeletionAttributes(successor.getRule()));
		Set<Attribute> successorSetAttributes = new HashSet<Attribute>(successor.getSetAttributes());
		Set<Attribute> successorChangeAttributes = new HashSet<Attribute>(successor.getChangeAttributes().stream()
				.map(pair -> pair.getRhsAttribute()).collect(Collectors.toSet()));
		Set<Attribute> successorPreserveAttributes = new HashSet<Attribute>(successor.getChangeAttributes().stream()
				.map(pair -> pair.getRhsAttribute()).collect(Collectors.toSet()));
		Set<Attribute> successorRequireAttributes = new HashSet<Attribute>(successor.getRequireAttributes());
		List<Attribute> successorForbidAttributes = successor.getForbidAttributes();

		Set<Attribute> successorUseAttributes = new HashSet<Attribute>(successorDeleteAttributes);
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

		// Delete-Use
		if ((!predecessorDeleteNodes.isEmpty()) && (!successorUseNodes.isEmpty())) {
			Set<PotentialNodeConflict> deleteUseNodePotCons = findDeleteUseConflicts(predecessorDeleteNodes,
					successorUseNodes);

			for (PotentialNodeConflict pnc : deleteUseNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(deleteUseNodePotCons);
		}

		// Create-Forbid (create-create) ((partial) duplicates)
		if ((!predecessorCreateNodes.isEmpty()) && (!successorCreateNodes.isEmpty())) {
			Set<PotentialNodeConflict> createCreateNodePotCons = findCreateCreateConflicts(predecessorCreateNodes,
					successorCreateNodes);
			for (PotentialNodeConflict pnc : createCreateNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(createCreateNodePotCons);
		}

		// Create-Forbid (create-forbid)
		if ((!predecessorCreateNodes.isEmpty()) && (!successorForbidNodes.isEmpty())) {
			Set<PotentialNodeConflict> createForbidNodePotCons = findCreateForbidConflicts(predecessorCreateNodes,
					successorForbidNodes);

			for (PotentialNodeConflict pnc : createForbidNodePotCons) {
				pnc.setSourceRule(predecessorEditRule);
				pnc.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPNCs(createForbidNodePotCons);
		}

		/*
		 * Search edge conflicts
		 */

		// Delete-Use
		if ((!predecessorDeleteEdges.isEmpty()) && (!successorUseEdges.isEmpty())) {
			Set<PotentialEdgeConflict> deleteUseEdgePotCons = findDeleteUseConflicts(predecessorDeleteEdges,
					successorUseEdges, potRuleCon);

			for (PotentialEdgeConflict pec : deleteUseEdgePotCons) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(deleteUseEdgePotCons);
		}

		// Create-Forbid (create-create) ((partial) duplicates)
		if ((!predecessorCreateEdges.isEmpty()) && (!successorCreateEdges.isEmpty())) {
			Set<PotentialEdgeConflict> createCreateEdgePotConcs = findCreateCreateConflicts(predecessorCreateEdges,
					successorCreateEdges, potRuleCon);

			for (PotentialEdgeConflict pec : createCreateEdgePotConcs) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(createCreateEdgePotConcs);
		}

		// Create-Forbid (create-forbid)
		if ((!predecessorCreateEdges.isEmpty()) && (!successorForbidEdges.isEmpty())) {
			Set<PotentialEdgeConflict> createForbidEdgePotConcs = findCreateForbidConflicts(predecessorCreateEdges,
					successorForbidEdges, potRuleCon);

			for (PotentialEdgeConflict pec : createForbidEdgePotConcs) {
				pec.setSourceRule(predecessorEditRule);
				pec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPECs(createForbidEdgePotConcs);
		}

		// Create-Forbid (dangling edges)
		if ((!predecessorCreateEdges.isEmpty()) && (!successorDeleteNodes.isEmpty())) {
			Set<PotentialDanglingEdgeConflict> createForbidDanglingEdgePotCons = findDanglingConflict(
					predecessorCreateEdges, successorDeleteNodes);
			for (PotentialDanglingEdgeConflict pdec : createForbidDanglingEdgePotCons) {
				pdec.setSourceRule(predecessorEditRule);
				pdec.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPDECs(createForbidDanglingEdgePotCons);
		}

		/*
		 * Search attribute conflicts
		 */

		// Change-Use (delete-use)
		if ((!predecessorDeleteAttributes.isEmpty()) && (!successorUseAttributes.isEmpty())) {
			Set<PotentialAttributeConflict> deleteUseAttributePotCons = findDeleteUseConflict(
					predecessorDeleteAttributes, successorUseAttributes, potRuleCon);

			for (PotentialAttributeConflict pac : deleteUseAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(deleteUseAttributePotCons);
		}

		// Change-Use (set-use)
		Set<Attribute> predecessorSetChangingAttributes = new HashSet<Attribute>(predecessorSetAttributes);
		predecessorSetChangingAttributes.addAll(predecessorChangeAttributes);
		if ((!predecessorSetChangingAttributes.isEmpty()) && (!successorUseAttributes.isEmpty())) {
			Set<PotentialAttributeConflict> setUseAttributePotCons = findSetUseConflict(
					predecessorSetChangingAttributes, successorUseAttributes, potRuleCon);

			for (PotentialAttributeConflict pac : setUseAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(setUseAttributePotCons);
		}

		// Change-Forbid (create-create)
		if (!predecessorCreateAttributes.isEmpty() && !successorCreateAttributes.isEmpty()) {
			Set<PotentialAttributeConflict> createCreateAttributePotCons = findCreateCreateConflict(
					predecessorCreateAttributes, successorCreateAttributes, potRuleCon);

			for (PotentialAttributeConflict pac : createCreateAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(createCreateAttributePotCons);
		}

		// Change-Forbid (create-forbid)
		if (!predecessorCreateAttributes.isEmpty() && !successorForbidAttributes.isEmpty()) {
			Set<PotentialAttributeConflict> createForbidAttributePotCons = findCreateForbidConflict(
					predecessorCreateAttributes, successorForbidAttributes, potRuleCon);

			for (PotentialAttributeConflict pac : createForbidAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(createForbidAttributePotCons);
		}

		// Change-Forbid (set-forbid)
		if ((!predecessorSetChangingAttributes.isEmpty()) && (!successorForbidAttributes.isEmpty())) {
			Set<PotentialAttributeConflict> setForbidAttributePotCons = findSetForbidConflict(
					predecessorSetChangingAttributes, successorForbidAttributes, potRuleCon);

			for (PotentialAttributeConflict pac : setForbidAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(setForbidAttributePotCons);
		}

		// Change-Change (create-create)
		if (!predecessorCreateAttributes.isEmpty() && !successorCreateAttributes.isEmpty()) {
			Set<PotentialAttributeConflict> createCreateAttributePotCons = findCreateCreateConflict(
					predecessorCreateAttributes, successorCreateAttributes, potRuleCon);

			for (PotentialAttributeConflict pac : createCreateAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(createCreateAttributePotCons);
		}
				
		// Change-Change (set-set)
		if (!predecessorSetAttributes.isEmpty() && !successorSetAttributes.isEmpty()) {
			Set<PotentialAttributeConflict> setSetForbidAttributePotCons = findSetSetConflict(predecessorSetAttributes,
					successorSetAttributes, potRuleCon);

			for (PotentialAttributeConflict pac : setSetForbidAttributePotCons) {
				pac.setSourceRule(predecessorEditRule);
				pac.setTargetRule(successorEditRule);
			}
			potRuleCon.addAllPACs(setSetForbidAttributePotCons);
		}

		return potRuleCon;
	}

	/*
	 * Nodes
	 */

	/**
	 * Checks all nodes for Delete-Use conflicts.
	 * 
	 * @param deletePredecessors Nodes on LHS only (<<delete>>).
	 * @param useSuccessors      Nodes on LHS or LHS and RHS or PAC (<<delete>> or
	 *                           <<preserve>> or <<require>>).
	 * 
	 * @return All potential Delete-Use node conflicts.
	 */
	protected Set<PotentialNodeConflict> findDeleteUseConflicts(Collection<Node> deletePredecessors,
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
	 * @param deletePredecessor Node is on LHS (<<delete>>).
	 * @param useSuccessor      Node is LHS or LHS and RHS or PAC (<<delete>> or
	 *                          <<preserve>> or <<require>>).
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isDeleteUseConflict(Node deletePredecessor, Node useSuccessor) {

		assert (isDeletionNode(deletePredecessor)) : "Input Assertion Failed: Must be a deletion node!";
		assert (isDeletionNode(useSuccessor) || isPreservedNode(useSuccessor) || isRequireNode(
				useSuccessor)) : "Input Assertion Failed: Must be a deletion, preserved or required node!";

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
	 * Checks all nodes for Create-Forbid (create-create) conflicts. Note: This
	 * isn't a real potential conflict as an output parameter is always assigned by
	 * the rule, however it is needed to detect partial matches later
	 * 
	 * @param createPredecessors Nodes on RHS only (<<create>>).
	 * @param createSuccessors   Nodes on RHS only (<<create>>).
	 * 
	 * @return All potential Create-Forbid (create-create) node conflicts.
	 */
	protected Set<PotentialNodeConflict> findCreateCreateConflicts(Collection<Node> createPredecessors,
			Collection<Node> createSuccessors) {

		Set<PotentialNodeConflict> potCons = new HashSet<>();
		for (Node predecessorNode : createPredecessors) {
			for (Node successorNode : createSuccessors) {
				if (isCreateCreateConflict(predecessorNode, successorNode)) {
					// create-create conflict found
					PotentialNodeConflict potCon = rbFactory.createPotentialNodeConflict();
					potCon.setSourceNode(predecessorNode);
					potCon.setTargetNode(successorNode);
//					potCon.setPotentialConflictKind(PotentialConflictKind.CREATE_FORBID_DUPLICATE);
					potCon.setKind(PotentialConflictKind.CREATE_FORBID);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two nodes for a Create-Forbid (create-create) conflict.
	 * 
	 * @param lhsPredecessor Node is on RHS (<<create>>).
	 * @param lhsSuccessor   Node is on RHS (<<create>>).
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isCreateCreateConflict(Node createPredecessor, Node createSuccessor) {

		assert (isCreationNode(createPredecessor)) : "Input Assertion Failed: Must be a creation node!";
		assert (isCreationNode(createSuccessor)) : "Input Assertion Failed: Must be a creation node!";

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
	 * Checks all nodes for a Create-Forbid (create-forbid) conflict.
	 * 
	 * @param createPredecessors Nodes on RHS only (<<create>>)
	 * @param forbidSuccessors   Nodes from NAC (<<forbid>>)
	 * 
	 * @return All potential Create-Forbid node conflicts.
	 */
	protected Set<PotentialNodeConflict> findCreateForbidConflicts(Collection<Node> createPredecessors,
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
	 * Checks tow nodes for a Create-Forbid (create-forbid) conflict.
	 * 
	 * @param createPredecessor Nodes on RHS only (<<create>>)
	 * @param forbidSuccessor   Nodes from NAC (<<forbid>>)
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isCreateForbidConflict(Node createPredecessor, Node forbidSuccessor) {

		assert (isCreationNode(createPredecessor)) : "Input Assertion Failed: Must be a creation node!";
		assert (isForbiddenNode(forbidSuccessor)) : "Input Assertion Failed: Must be a forbidden node!";

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
	 * Checks all edges for Delete-Use conflicts.
	 * 
	 * @param deletePredecessors Edges on LHS only (<<delete>>).
	 * @param useSuccessors      Edges on LHS or LHS and RHS or PAC (<<delete>> or
	 *                           <<preserver>> or <<require>>).
	 * @param potRuleCon         potential conflicts
	 * 
	 * @return All potential Delete-use edge conflicts.
	 */
	protected Set<PotentialEdgeConflict> findDeleteUseConflicts(Collection<Edge> deletePredecessors,
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
	 * @param useSuccessor      Edge is on LHS or LHS and RHS or PAC (<<delete>> or
	 *                          <<preserve>> or <<require>>).
	 * @param potRuleCon        potential conflicts
	 * 
	 * @return <code>true</code> if there is a conflict; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isDeleteUseConflict(Edge deletePredecessor, Edge useSuccessor,
			PotentialRuleConflicts potRuleCon) {

		assert (isDeletionEdge(deletePredecessor)) : "Input Assertion Failed: Must be a deletion edge!";
		assert (isDeletionEdge(useSuccessor) || isPreservedEdge(useSuccessor) || isRequireEdge(
				useSuccessor)) : "Input Assertion Failed: Must be a deletion, preserved or required edge!";

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
	 * Checks all edges for Create-Forbid (create-create) conflicts.
	 * 
	 * @param createPredecessors Edge is on RHS only (<<create>>)
	 * @param createSuccessor    Edge is on RHS only (<<create>>)
	 * @param potRuleCon         potential conflicts
	 * 
	 * @return All potential Create-Forbid (create-create) edge conflicts.
	 */
	protected Set<PotentialEdgeConflict> findCreateCreateConflicts(Collection<Edge> createPredecessors,
			Collection<Edge> createSuccessor, PotentialRuleConflicts potRuleCon) {

		Set<PotentialEdgeConflict> potCons = new HashSet<>();
		for (Edge successorEdge : createSuccessor) {
			for (Edge predecessorEdge : createPredecessors) {
				if (isCreateCreateConflict(predecessorEdge, successorEdge, potRuleCon)) {
					// Create-Forbid conflict found
					PotentialEdgeConflict potCon = rbFactory.createPotentialEdgeConflict();
					potCon.setSourceEdge(predecessorEdge);
					potCon.setTargetEdge(successorEdge);
//					potCon.setPotentialConflictKind(PotentialConflictKind.CREATE_FORBID_DUPLICATE);
					potCon.setKind(PotentialConflictKind.CREATE_FORBID);
					potCons.add(potCon);
				}
			}
		}
		return potCons;
	}

	/**
	 * Checks two edges for a Create-Forbid (create-create) conflict.
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
		assert (isCreationEdge(createPredecessor)) : "Input Assertion Failed!";
		assert (isCreationEdge(createSuccessor)) : "Input Assertion Failed!";

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
	 * Checks all edges for Create-Forbid (create-forbid) conflicts.
	 * 
	 * @param createPredecessors Edge is on RHS only (<<create>>)
	 * @param forbidSuccessors   Edge is from NAC (<<forbid>>)
	 * @param potRuleCon         potential conflicts
	 * 
	 * @return All potential Create-Forbid (create-forbid) edge conflicts.
	 */
	protected Set<PotentialEdgeConflict> findCreateForbidConflicts(Collection<Edge> createPredecessors,
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
	 * Checks two edges for a Create-Forbid (create-forbid) conflict.
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
		assert (isCreationEdge(createPredecessor)) : "Input Assertion Failed!";
		assert (isForbiddenEdge(forbidSuccessor)) : "Input Assertion Failed!";

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
	 * Checks all edges for Create-Forbid (due to dangling edges) conflicts.
	 * 
	 * @param createPredecessors Edge is on RHS only (<<create>>)
	 * @param deleteSuccessors   Node from LHS only (<<delete>>)
	 * @return All potential Create-Forbid conflicts due to dangling edges .
	 */
	protected Set<PotentialDanglingEdgeConflict> findDanglingConflict(Collection<Edge> createPredecessors,
			Collection<Node> deleteSuccessors) {
		Set<PotentialDanglingEdgeConflict> potCons = new HashSet<>();
		for (Node deleteSuccessor : deleteSuccessors) {
			for (Edge predecessorEdge : createPredecessors) {
				if (isDanglingConflict(predecessorEdge, deleteSuccessor)) {
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
	 * Checks if a creation edge leads to dangling edges of a deletion node.
	 * 
	 * @param createPredecessor
	 * @param deleteSuccessor
	 * @return
	 */
	protected boolean isDanglingConflict(Edge createPredecessor, Node deleteSuccessor) {
		assert isCreationEdge(createPredecessor) : "<< create >> predecessor edge expected!";
		assert isDeletionNode(deleteSuccessor) : "<< delete >> successor node expected!";

		for (EReference eReference : deleteSuccessor.getType().getEAllReferences()) {
			if (deleteSuccessor.getOutgoing(eReference).isEmpty()) {
				return true;
			}
		}

		Collection<Setting> settings = EcoreUtil.UsageCrossReferencer.find(deleteSuccessor.getType(),
				deleteSuccessor.getType().eResource());
		for (Setting setting : settings) {
			if (setting.getEStructuralFeature() == createPredecessor.getType()) {
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
	/**
	 * 
	 * @param deletePredecessors
	 * @param useSuccessors
	 * @param potRuleCon
	 * @return
	 */
	protected Set<PotentialAttributeConflict> findDeleteUseConflict(Collection<Attribute> deletePredecessors,
			Collection<Attribute> useSuccessors, PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potConss = new HashSet<>();

		for (Attribute lhsPredecessorAttribute : deletePredecessors) {
			for (Attribute useSuccessorAttribute : useSuccessors) {
				if (isDeleteUseConflict(lhsPredecessorAttribute, useSuccessorAttribute, potRuleCon)) {
					// Delete-Use conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(lhsPredecessorAttribute);
					potcon.setTargetAttribute(useSuccessorAttribute);
					potcon.setKind(PotentialConflictKind.CHANGE_USE);
					potConss.add(potcon);
				}
			}
		}
		return potConss;
	}

	/**
	 * 
	 * @param deletePredecessor
	 * @param useSuccessor
	 * @param potRuleCon
	 * @return
	 */
	private boolean isDeleteUseConflict(Attribute deletePredecessor, Attribute useSuccessor,
			PotentialRuleConflicts potRuleCon) {

		assert (isDeletionAttribute(deletePredecessor)
				&& isDeletionNode(deletePredecessor.getNode())) : "deletion attribute in deletion node expected";
		assert (isDeletionAttribute(useSuccessor) || isChangingAttribute(useSuccessor)
				|| isPreservedAttribute(useSuccessor) || isRequireAttribute(useSuccessor)) : "used attribute expected";

		if (!(deletePredecessor.getType().equals(useSuccessor.getType())
				&& isAssignableTo(deletePredecessor.getNode().getType(), useSuccessor.getNode().getType()))) {
			return false;
		}

		if (hasPotentialNodeConflict(potRuleCon, deletePredecessor.getNode(), useSuccessor.getNode())) {
			// is predecessor nodeType assignable to successor nodeType?

			// Attribute case differentiation precondition is not fulfilled?
			if (isLiteral(deletePredecessor) && isLiteral(useSuccessor)) {
				// Literals are equal
				return deletePredecessor.getValue().equals(useSuccessor.getValue());
			}
			// Predecessor or successor is variable!
			return true;

		}

		return false;
	}

	/**
	 * 
	 * @param setPredecessors
	 * @param useSuccessors
	 * @param potRuleCon
	 * @return
	 */
	protected Set<PotentialAttributeConflict> findSetUseConflict(Collection<Attribute> setPredecessors,
			Collection<Attribute> useSuccessors, PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potConss = new HashSet<>();

		for (Attribute rhsPredecessorAttribute : setPredecessors) {
			for (Attribute lhsSuccessorAttribute : useSuccessors) {
				if (isSetUseConflict(rhsPredecessorAttribute, lhsSuccessorAttribute, potRuleCon)) {
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
	private boolean isSetUseConflict(Attribute setPredecessor, Attribute useSuccessor,
			PotentialRuleConflicts potRulecon) {

		assert (isCreationAttribute(setPredecessor) || isChangingAttribute(setPredecessor)) : "Input Assertion Failed!";
		assert (isDeletionAttribute(useSuccessor) || isChangingAttribute(useSuccessor)
				|| isPreservedAttribute(useSuccessor) || isRequireAttribute(useSuccessor)) : "used attribute expected";

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
	 * special form of set-forbid conflict, as the attributes are set when creating
	 * the node, which can be considered as a rule for creating the node and rules
	 * for each set attribute
	 * 
	 * @param predecessorCreateAttributes
	 * @param successorForbidAttributes
	 * @param potRuleCon
	 * @return
	 */
	protected Set<PotentialAttributeConflict> findCreateForbidConflict(Set<Attribute> predecessorCreateAttributes,
			List<Attribute> successorForbidAttributes, PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potCons = new HashSet<>();

		for (Attribute createPredecessor : predecessorCreateAttributes) {
			for (Attribute forbidSuccessor : successorForbidAttributes) {
				if (isCreateForbidConflict(createPredecessor, forbidSuccessor, potRuleCon)) {
					// Create-Forbid conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(createPredecessor);
					potcon.setTargetAttribute(forbidSuccessor);
					potcon.setKind(PotentialConflictKind.CHANGE_FORBID);
					potCons.add(potcon);
				}
			}
		}
		return potCons;
	}

	/**
	 * @param createPredecessor
	 * @param forbidSuccessor
	 * @param potRuleCon
	 * @return
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
	 * 
	 * @param setPredecessors
	 * @param forbidSuccessors
	 * @param potRuleCon
	 * @return
	 */
	protected Set<PotentialAttributeConflict> findSetForbidConflict(Collection<Attribute> setPredecessors,
			Collection<Attribute> forbidSuccessors, PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potCons = new HashSet<>();

		for (Attribute setPredecessor : setPredecessors) {
			for (Attribute forbidSuccessor : forbidSuccessors) {
				if (isSetForbidConflict(setPredecessor, forbidSuccessor, potRuleCon)) {
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
	protected boolean isSetForbidConflict(Attribute setPredecessor, Attribute forbidSuccessor,
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
					return !setPredecessor.getValue().equals(forbidSuccessor.getValue());
				}
				return true;
			}

		}

		return false;
	}

	private Set<PotentialAttributeConflict> findCreateCreateConflict(Set<Attribute> predecessorCreateAttributes,
			Set<Attribute> successorCreateAttributes, PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potCons = new HashSet<>();

		for (Attribute createPredecessor : predecessorCreateAttributes) {
			for (Attribute createSuccessor : successorCreateAttributes) {
				if (isCreateCreateConflict(createPredecessor, createSuccessor, potRuleCon)) {
					// Create-Forbid conflict found
					PotentialAttributeConflict potcon = rbFactory.createPotentialAttributeConflict();
					potcon.setSourceAttribute(createPredecessor);
					potcon.setTargetAttribute(createSuccessor);
					potcon.setKind(PotentialConflictKind.CHANGE_CHANGE);
					potCons.add(potcon);
				}
			}
		}
		return potCons;
	}

	protected boolean isCreateCreateConflict(Attribute createPredecessor, Attribute createSuccessor,
			PotentialRuleConflicts potRuleCon) {
		assert (isCreationAttribute(createPredecessor) && isCreationNode(createPredecessor.getNode())) : "Input Assertion Failed!";
		assert (isCreationAttribute(createSuccessor) && isCreationNode(createSuccessor.getNode())) : "Input Assertion Failed!";

		if(!(createPredecessor.getType().equals(createSuccessor.getType()) && isAssignableTo(createPredecessor.getNode().getType(), createSuccessor.getNode().getType()))) {
			return false;
		}
		
		if(hasPotentialNodeConflict(potRuleCon, createPredecessor.getNode(), createSuccessor.getNode())) {
		// Attribute case differentiation precondition is not fulfilled?
		if (isLiteral(createPredecessor) && isLiteral(createSuccessor)) {
			// Literals are equal
			return !createPredecessor.getValue().equals(createSuccessor.getValue());
		}
		// Predecessor or successor is variable!
		return true;
		}
		return false;
	}
	
	protected Set<PotentialAttributeConflict> findSetSetConflict(Collection<Attribute> predecessorSetAttributes,
			Collection<Attribute> successorSetAttributes, PotentialRuleConflicts potRuleCon) {
		Set<PotentialAttributeConflict> potCons = new HashSet<>();

		for (Attribute setPredecessor : successorSetAttributes) {
			for (Attribute setSuccessor : successorSetAttributes) {
				if (isSetSetConflict(setPredecessor, setSuccessor, potRuleCon)) {
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

	protected boolean isSetSetConflict(Attribute setPredecessor, Attribute setSuccessor,
			PotentialRuleConflicts potRuleCon) {
		assert (isCreationAttribute(setPredecessor)
				&& isPreservedNode(setPredecessor.getNode())) : "Input Assertion Failed!";
		assert (isCreationAttribute(setSuccessor)
				&& isPreservedNode(setSuccessor.getNode())) : "Input Assertion Failed!";

		if (!(setPredecessor.getType().equals(setSuccessor.getType())
				&& isAssignableTo(setPredecessor.getNode().getType(), setSuccessor.getNode().getType()))) {
			return false;
		}

		// Attribute case differentiation precondition is not fulfilled?
		if (isLiteral(setPredecessor) && isLiteral(setSuccessor)) {
			// Literals are equal
			return !setPredecessor.getValue().equals(setSuccessor.getValue());
		}
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