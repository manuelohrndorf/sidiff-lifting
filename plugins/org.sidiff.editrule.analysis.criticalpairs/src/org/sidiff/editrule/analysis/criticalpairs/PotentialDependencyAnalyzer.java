package org.sidiff.editrule.analysis.criticalpairs;

import static org.sidiff.common.emf.access.EMFMetaAccess.assignable;
import static org.sidiff.common.emf.access.EMFMetaAccess.isAssignableTo;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getCreationAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getDeletionAttributes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isChangingAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isDeletionEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isDeletionNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isForbiddenAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isForbiddenEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isForbiddenNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isLHSAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isPreservedNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isRequireAttribute;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isRequireEdge;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isRequireNode;
import static org.sidiff.editrule.analysis.conditions.EditRuleConditions.isPostcondition;
import static org.sidiff.editrule.analysis.conditions.EditRuleConditions.isPrecondition;
import static org.sidiff.editrule.analysis.conditions.EditRuleConditionsConfiguration.isPreservedEdgePostCondition;
import static org.sidiff.editrule.analysis.conditions.EditRuleConditionsConfiguration.isPreservedEdgePreCondition;
import static org.sidiff.editrule.analysis.transienteffects.EditRuleTransientEffects.isPreservedNodeSearchedInModelA;
import static org.sidiff.editrule.analysis.transienteffects.EditRuleTransientEffects.isPreservedNodeSearchedInModelB;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.view.ActionGraph;
import org.sidiff.common.henshin.view.EdgePair;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.editrule.analysis.criticalpairs.util.PotentialRuleDependencies;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialDependencyKind;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;

/**
 * Calculates all potential dependencies between two rules. This algorithm isn't
 * optimal in the sense that it may reports more potential dependencies than necessary.
 *
 * @author Manuel Ohrndorf
 */
public abstract class PotentialDependencyAnalyzer extends AbstractAnalyzer {

	/**
	 * <code>true</code> to calculate transient potential dependencies;
	 * <code>false</code> otherwise.
	 */
	protected boolean transientPDs;

	/**
	 * <code>true</code> to calculate non transient potential dependencies;
	 * <code>false</code> otherwise.
	 */
	protected boolean nonTransientPDs;

	/*
	 * The >S<ource of a dependency is the >S<uccessor rule in a sequence of two rules! 
	 * Create -> Use <-> Target -> Source <-> Predecessor -> Successor 
	 * Use -> Delete <-> Target -> Source <-> Predecessor -> Successor
	 */

	/**
	 * Initializes a new potential dependency analyzer.
	 * 
	 * @param transientPDs
	 *            <code>true</code> to calculate transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 * @param nonTransientPDs
	 *            <code>true</code> to calculate non transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 */
	public PotentialDependencyAnalyzer(Set<EPackage> imports, boolean transientPDs, boolean nonTransientPDs) {
		super(imports);
		this.transientPDs = transientPDs;
		this.nonTransientPDs = nonTransientPDs;
	}

	/**
	 * Adds all potential dependences from the predecessor to the successor rule.
	 * 
	 * @param predecessor
	 *            The predecessor rule, i.e. the target of dependencies.
	 * @param successor
	 *            The successor rule, i.e. the source of dependencies
	 */
	protected PotentialRuleDependencies findRuleDependencies(
			ActionGraph predecessor, EditRule predecessorEditRule, 
			ActionGraph successor, EditRule successorEditRule) {

		/*
		 * Divide the rule
		 */

		// Get nodes
		Collection<NodePair> predecessorPreserveNodes = predecessor.getPreserveNodes();
		Collection<Node> predecessorCreateNodes = predecessor.getCreateNodes();
		Collection<Node> predecessorDeleteNodes = predecessor.getDeleteNodes();

		Collection<NodePair> successorPreserveNodes = successor.getPreserveNodes();
		Collection<Node> successorCreateNodes = successor.getCreateNodes();
		Collection<Node> successorDeleteNodes = successor.getDeleteNodes();

		// Get <<forbid>> nodes
		Collection<Node> predecessorForbidNodes  = predecessor.getForbidNodes();
		Collection<Node> successorForbidNodes = successor.getForbidNodes();

		// Get <<require>> nodes
		Collection<Node> predecessorRequireNodes = predecessor.getRequireNodes();
		Collection<Node> successorRequireNodes = successor.getRequireNodes();

		// Get edges
		Collection<EdgePair> predecessorPreserveEdges = predecessor.getPreserveEdges();
		Collection<Edge> predecessorCreateEdges = predecessor.getCreateEdges();
		Collection<Edge> predecessorDeleteEdges = predecessor.getDeleteEdges();
		Collection<EdgePair> successorPreserveEdges = successor.getPreserveEdges();
		Collection<Edge> successorCreateEdges = successor.getCreateEdges();
		Collection<Edge> successorDeleteEdges = successor.getDeleteEdges();

		// Get <<forbid>> edges
		Collection<Edge> predecessorForbidEdges  = predecessor.getForbidEdges();
		Collection<Edge> successorForbidEdges = successor.getForbidEdges();

		// Get <<require>> edges
		Collection<Edge> predecessorRequireEdges = predecessor.getRequireEdges();
		Collection<Edge> successorRequireEdges = successor.getRequireEdges();

		// Get attributes
		Collection<Attribute> predecessorCreateAttributes = getCreationAttributes(predecessor.getRule());
		Collection<Attribute> predecessorSetAttributes = predecessor.getSetAttributes();
		Collection<Attribute> predecessorChangeAttributes = predecessor.getChangeAttributes().stream()
				.map(pair -> pair.getRhsAttribute()).collect(Collectors.toSet());
		Collection<Attribute> predecessorPreserveAttributes = predecessor.getPreserveAttributes();
		Collection<Attribute> predecessorRequireAttributes = predecessor.getRequireAttributes();
		Collection<Attribute> predecessorForbidAttributes = predecessor.getForbidAttributes();
		Collection<Attribute> predecessorSetChangeAttributes = new HashSet<Attribute>(predecessorSetAttributes);
		predecessorSetChangeAttributes.addAll(predecessorChangeAttributes);
		Collection<Attribute> predecessorUseAttributes = new HashSet<Attribute>(predecessorChangeAttributes);
		predecessorUseAttributes.addAll(predecessorPreserveAttributes);
		predecessorUseAttributes.addAll(predecessorRequireAttributes);
		
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
		 *  Find all potential dependencies - if the predecessor rule is applied first
		 *  and the successor rule is applied second.
		 */

		// Create new potential rule dependency
		PotentialRuleDependencies potRuleDep = new PotentialRuleDependencies();

		/*
		 *  Search node dependencies
		 */

		Consumer<Set<PotentialNodeDependency>> pndConsumer = pnds -> {
			for (PotentialNodeDependency pnd : pnds) {
				pnd.setSourceRule(successorEditRule);
				pnd.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPNDs(pnds);
		};
		

		if(!predecessorCreateNodes.isEmpty()) {
			// Create-Delete
			if (!successorDeleteNodes.isEmpty()) {
				pndConsumer.accept(findeCreateDeleteNodeDependencies(predecessorCreateNodes, successorDeleteNodes));
			}
			// Create-Use
			if (!successorPreserveNodes.isEmpty()) {
				pndConsumer.accept(findCreateUseNodeDependencies(predecessorCreateNodes, successorPreserveNodes));
			}
			// Create-Require
			if (!successorRequireNodes.isEmpty()) {
				pndConsumer.accept(findCreateRequireNodeDependencies(predecessorCreateNodes, successorRequireNodes));
			}
		}
		

		if(!predecessorDeleteNodes.isEmpty()) {
			// Delete-Create
			if(!successorCreateNodes.isEmpty()) {
				pndConsumer.accept(findDeleteCreateNodeDependencies(predecessorDeleteNodes, successorDeleteNodes));
			}
			// Delete-Forbid 
			if (!successorForbidNodes.isEmpty()) {
				pndConsumer.accept(findDeleteForbidNodeDependencies(predecessorDeleteNodes, successorForbidNodes));
			}
			
		}

		
		if(!successorDeleteNodes.isEmpty()) {
			// Use-Delete
			if (!predecessorPreserveNodes.isEmpty()) {
				pndConsumer.accept(findUseDeleteNodeDependencies(predecessorPreserveNodes, successorDeleteNodes));
			}
			// Require-Delete
			if (!predecessorRequireNodes.isEmpty()) {
				pndConsumer.accept(findRequireDeleteNodeDependencies(predecessorRequireNodes, successorDeleteNodes));
			}
		}

		// Forbid-Create
		if(!predecessorForbidNodes.isEmpty() && !successorCreateNodes.isEmpty()) {
			pndConsumer.accept(findForbidCreateNodeDependencies(predecessorForbidNodes, successorCreateNodes));
		}


		/*
		 * Search edge dependencies
		 */

		Consumer<Set<PotentialEdgeDependency>> pedConsumer = peds -> {
			for (PotentialEdgeDependency ped : peds) {
				ped.setSourceRule(successorEditRule);
				ped.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPEDs(peds);
		};

	

		if(!predecessorCreateEdges.isEmpty()) {
			// Create-Delete
			if (!successorDeleteEdges.isEmpty()) {
				pedConsumer.accept(
						findCreateDeleteEdgeDependencies(predecessorCreateEdges, successorDeleteEdges, potRuleDep));
			}
			// Create-Use
			if (!successorPreserveEdges.isEmpty()) {
				pedConsumer.accept(findCreateUseEdgeDependencies(predecessorCreateEdges, successorPreserveEdges, potRuleDep));
			}
			// Create-Use (PAC)
			if (!successorRequireEdges.isEmpty()) {
				pedConsumer.accept(findCreateRequireEdgeDependencies(predecessorCreateEdges, successorRequireEdges, potRuleDep));
			}
		}
		
		if(!predecessorDeleteEdges.isEmpty()) {
			// Delete-Create
			if(!successorCreateEdges.isEmpty()) {
				pedConsumer.accept(findDeleteCreateEdgeDependencies(predecessorDeleteEdges, successorCreateEdges, potRuleDep));
			}
			
			// Delete-Forbid
			if (!successorForbidEdges.isEmpty()) {
				pedConsumer.accept(findDeleteForbidEdgeDependencies(predecessorDeleteEdges, successorForbidEdges, potRuleDep));
			}
		}

		if (!successorDeleteEdges.isEmpty()) {
			// Use-Delete
			if (!predecessorPreserveEdges.isEmpty()) {
				pedConsumer.accept(
						findUseDeleteEdgeDependencies(predecessorPreserveEdges, successorDeleteEdges, potRuleDep));
			}
			// Use-Delete (PAC)
			if (!predecessorRequireEdges.isEmpty()) {
				pedConsumer.accept(
						findRequireDeleteEdgeDependencies(predecessorRequireEdges, successorDeleteEdges, potRuleDep));
			}
		}
		
		// Forbid-Create
		if (!predecessorForbidEdges.isEmpty() && !successorCreateEdges.isEmpty()) {
			pedConsumer.accept(findForbidCreateEdgeDependencies(predecessorForbidEdges, successorCreateEdges, potRuleDep));
		}

		/*
		 *  Search attribute dependencies
		 */

		Consumer<Set<PotentialAttributeDependency>> padConsumer = pads -> {
			for (PotentialAttributeDependency pad : pads) {
				pad.setSourceRule(successorEditRule);
				pad.setTargetRule(predecessorEditRule);
			}
			potRuleDep.addAllPADs(pads);
		};

		// Create-Use
		if(!predecessorCreateAttributes.isEmpty() && !successorUseAttributes.isEmpty()) {
			padConsumer.accept(findCreateUseAttributeDependency(predecessorCreateAttributes, successorUseAttributes));
		}
		
		if(!predecessorSetChangeAttributes.isEmpty()) {
			// Change-Use
			if (!successorUseAttributes.isEmpty()) {
				padConsumer.accept(findChangeUseAttributeDependency(predecessorSetChangeAttributes, successorUseAttributes));
			}
			
			// Change-Forbid
			if (!successorForbidAttributes.isEmpty()) {
				padConsumer.accept(findChangeForbidAttributeDependencies(predecessorChangeAttributes, successorForbidAttributes));
			}
		}

		if(!successorChangeAttributes.isEmpty()) {
			// Forbid-Change
			if (!predecessorForbidAttributes.isEmpty()) {
				padConsumer.accept(
						findForbidChangeAttributeDependencies(predecessorForbidAttributes, successorChangeAttributes));
			}
			
			// Use-Change
			if (!predecessorUseAttributes.isEmpty()) {
				padConsumer.accept(findUseChangeAttributeDependencies(predecessorUseAttributes, successorChangeAttributes));
			}
		}


		return potRuleDep;
	}

	/*
	 * Nodes
	 */

	/**
	 * Checks all nodes for Create-Delete dependencies.
	 * @param createPredecessors Node on RHS only (<< create >>).
	 * @param deleteSuccessors  Nodes on LHS only (<< delete >>).
	 * @return All potential Create-Delete dependencies.
	 */
	protected Set<PotentialNodeDependency> findeCreateDeleteNodeDependencies(
			Collection<Node> createPredecessors,
			Collection<Node> deleteSuccessors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<>();
		for (Node successorNode : deleteSuccessors) {
			for (Node predecessorNode : createPredecessors) {
				if (isCreateDeleteDependency(predecessorNode, successorNode)) {
					// Create-Use dependence found
					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.CREATE_DELETE);
					potDep.setTransient(true);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two nodes for a Create-Delete dependency.
	 * 
	 * @param createPredecessor
	 *            Node is on RHS only (<< create >>).
	 * @param deleteSuccessor
	 *            Node is on LHS only (<< delete >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateDeleteDependency(Node createPredecessor, Node deleteSuccessor) {
		assert (isCreationNode(createPredecessor)) : "Input Assertion failed: creation node expected!";
		assert (isDeletionNode(deleteSuccessor)): "Input Assertion failed: deletion node expected!";
		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Preserve-Node-Type
		 */

		boolean superType = createPredecessor.getType().getEAllSuperTypes().contains(deleteSuccessor.getType());
		boolean directType = createPredecessor.getType() == deleteSuccessor.getType();

		return directType || superType;
	}
	
	/**
	 * Checks all nodes for Create-Use dependencies.
	 * 
	 * @param createPredecessors
	 *            Nodes on RHS only (<< create >>).
	 * @param preservedSuccessors
	 *            Edges on LHS and RHS (<< preserve >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findCreateUseNodeDependencies(
			Collection<Node> createPredecessors, Collection<NodePair> preservedSuccessors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<>();
		for (NodePair successorNode : preservedSuccessors) {
			
			// Is transient potential dependences?
			boolean isTransient;
			
			if (isPreservedNodeSearchedInModelB(successorNode)) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}

			for (Node predecessorNode : createPredecessors) {
				if (isCreateUseDependency(predecessorNode, successorNode)) {
					// Create-Use dependence found
					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
					potDep.setSourceNode(successorNode.getLhsNode());
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two nodes for a Create-Use dependency.
	 * 
	 * @param createPredecessor
	 *            Node is on RHS only (<< create >>).
	 * @param preservedSuccessor
	 *            Node is on LHS and RHS (<< preserve >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateUseDependency(Node createPredecessor, NodePair preservedSuccessor) {

		assert (isCreationNode(createPredecessor)) : "Input Assertion failed: creation node expected!";
		
		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Preserve-Node-Type
		 */

		boolean superType = createPredecessor.getType().getEAllSuperTypes().contains(preservedSuccessor.getType());
		boolean directType = createPredecessor.getType() == preservedSuccessor.getType();

		return directType || superType;
	}
	
	/**
	 * Checks all nodes for Create-Use dependencies.
	 * 
	 * @param createPredecessors
	 *            Nodes on RHS only (<< create >>).
	 * @param requireSuccessors
	 *            Edges from PACs (<< require >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findCreateRequireNodeDependencies(
			Collection<Node> createPredecessors,
			Collection<Node> requireSuccessors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<>();
		for (Node successorNode : requireSuccessors) {

			// Is transient potential dependences?
			boolean isTransient;
			if (isPostcondition(successorNode.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}
			
			for (Node predecessorNode : createPredecessors) {
				if (isCreateRequireDependency(predecessorNode, successorNode)) {
					// Create-Use dependence found
					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two nodes for a Create-Use dependency.
	 * 
	 * @param createPredecessor
	 *            Node is on RHS only (<< create >>).
	 * @param requireSuccessor
	 *            Node is in PAC (<< require >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateRequireDependency(Node createPredecessor, Node requireSuccessor) {

		assert (isCreationNode(createPredecessor)) : "Input Assertion failed: creation node expected!";
		assert (isRequireNode(requireSuccessor)) : "Input Assertion failed: require node expected!";
		
		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Require-Node-Type
		 */

		boolean superType = createPredecessor.getType().getEAllSuperTypes().contains(requireSuccessor.getType());
		boolean directType = createPredecessor.getType() == requireSuccessor.getType();

		return directType || superType;
	}
	
	/**
	 * Checks all nodes for Delete-Create dependencies. Note, that is no real dependency but needed to detect potential (partial) transient effects.
	 * 
	 * @param deletePredecessors
	 *            Nodes on LHS only (<< delete >>).
	 * @param createSuccessors
	 *            Edges on RHS only (<< create >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findDeleteCreateNodeDependencies(
			Collection<Node> deletePredecessors, 
			Collection<Node> createSuccessors) {
		
		Set<PotentialNodeDependency> potDeps = new HashSet<>();
		for (Node successorNode : createSuccessors) {

			for (Node predecessorNode : deletePredecessors) {
				if (isDeleteCreateDependency(predecessorNode, successorNode)) {
					// Change-Forbid dependence found
					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.DELETE_CREATE);
					potDep.setTransient(true);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two nodes for a Delete-Create dependency. Note, that is no real dependency but needed to detect potential (partial) transient effects.
	 * 
	 * @param deletePredecessor
	 *            Node is on LHS only (<< delete >>).
	 * @param createSuccessor
	 *            Node is on RHS only (<< create >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isDeleteCreateDependency(Node deletePredecessor, Node createSuccessor) {

		assert (isDeletionNode(deletePredecessor)) : "Input Assertion failed: deletion node expected!";
		assert (isCreationNode(createSuccessor)) : "Input Assertion Failed: creation node expected!";
		
		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Require-Node-Type
		 */

		boolean superType = deletePredecessor.getType().getEAllSuperTypes().contains(createSuccessor.getType());
		boolean directType = deletePredecessor.getType() == createSuccessor.getType();

		return directType || superType;
	}
	
	
	/**
	 * Checks all nodes for Delete-Forbid dependencies.
	 * 
	 * @param deletePredecessors
	 *            Nodes on LHS only (<< delete >>).
	 * @param forbidSuccessors
	 *            Edges from NACs (<< forbid >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findDeleteForbidNodeDependencies(
			Collection<Node> deletePredecessors, 
			Collection<Node> forbidSuccessors) {
		
		Set<PotentialNodeDependency> potDeps = new HashSet<>();
		for (Node successorNode : forbidSuccessors) {

			// Is transient potential dependences?
			boolean isTransient;
			if (isPostcondition(successorNode.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}

			for (Node predecessorNode : deletePredecessors) {
				if (isDeleteForbidDependency(predecessorNode, successorNode)) {
					// Change-Forbid dependence found
					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.DELETE_FORBID);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}

	/**
	 * Checks two nodes for a Delete-Forbid dependency.
	 * 
	 * @param deletePredecessor
	 *            Node is on LHS only (<< delete >>).
	 * @param forbidSuccessor
	 *            Node is a NAC (<< forbid >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isDeleteForbidDependency(Node deletePredecessor, Node forbidSuccessor) {
		
		assert (isDeletionNode(deletePredecessor)) : "Input Assertion failed: deletion node expected!";
		assert (isForbiddenNode(forbidSuccessor)) : "Input Assertion failed: forbidden node expected!";
		
		return assignable(deletePredecessor.getType(), forbidSuccessor.getType());
	}
	
	/**
	 * Checks all nodes for Use-Delete dependencies.
	 * 
	 * @param preservePredecessors
	 *            Edges on LHS and RHS (<< preserve >>).
	 * @param deleteSuccessors
	 *            Nodes on LHS only (<< delete >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findUseDeleteNodeDependencies(
			Collection<NodePair> preservePredecessors, Collection<Node> deleteSuccessors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<>();

		for (NodePair predecessorNode : preservePredecessors) {
			
			// Is transient potential dependences?
			boolean isTransient;
			
			if (isPreservedNodeSearchedInModelA(predecessorNode)) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}
			
			for (Node successorNode : deleteSuccessors) {
				
				if (isUseDeleteDependency(predecessorNode, successorNode)) {
					// Delete-Use dependence found
					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();

					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode.getLhsNode());
					potDep.setKind(PotentialDependencyKind.USE_DELETE);
					potDep.setTransient(isTransient);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}

	/**
	 * Checks two nodes for a Use-Delete dependency.
	 * 
	 * @param predecessor
	 *            Node is on LHS and RHS (<< preserve >>).
	 * @param lhsSuccessor
	 *            Node is on LHS only (<< delete >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isUseDeleteDependency(NodePair predecessor, Node lhsSuccessor) {
		
		assert(isDeletionNode(lhsSuccessor)) : "Input Assertion failed: deletion node expected!";
		
		// a rule creating an edge connected to a node that is deleted cannot be in dependency
		if(predecessor.getRhsNode().getAllEdges().stream().anyMatch(e -> HenshinRuleAnalysisUtilEx.isCreationEdge(e))) {
			return false;
		}
		
		/*
		 * Preserve-Node-Type + Preserve-Node-Sub-Types + Preserve-Node-Super-Types == Delete-Node-Type
		 */

		boolean superType = predecessor.getType().getEAllSuperTypes().contains(lhsSuccessor.getType());
		boolean directType = predecessor.getType() == lhsSuccessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(predecessor.getType()).contains(lhsSuccessor.getType());
		
		return directType || superType || subType;
	}
	
	/**
	 * Checks all nodes for Use-Delete dependencies.
	 * 
	 * @param requirePredecessors
	 *            Edges from PACs (<< require >>).
	 * @param deleteSuccessors
	 *            Nodes on LHS only (<< delete >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findRequireDeleteNodeDependencies(
			Collection<Node> requirePredecessors, Collection<Node> deleteSuccessors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<>();

		for (Node predecessorNode : requirePredecessors) {
			
			// Is transient potential dependences?
			boolean isTransient;
			
			if (isPrecondition(predecessorNode.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}
			
			for (Node successorNode : deleteSuccessors) {
				if (isRequireDeleteDependency(predecessorNode, successorNode)) {
					// Delete-Use dependence found
					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();

					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.USE_DELETE);
					potDep.setTransient(isTransient);

					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}

	/**
	 * Checks two nodes for a Use-Delete dependency.
	 * 
	 * @param requirePredecessor
	 *            Node is in PAC (<< require >>).
	 * @param deleteSuccessor
	 *            Node is on LHS only (<< delete >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isRequireDeleteDependency(Node requirePredecessor, Node deleteSuccessor) {
		
		assert(isRequireNode(requirePredecessor)) : "Input Assertion failed: required node expected!";
		assert(isDeletionNode(deleteSuccessor)) : "Input Assertion failed: deletion node expected!";
		
		/*
		 * Preserve-Node-Type + Preserve-Node-Sub-Types + Preserve-Node-Super-Types == Delete-Node-Type
		 */

		boolean superType = requirePredecessor.getType().getEAllSuperTypes().contains(deleteSuccessor.getType());
		boolean directType = requirePredecessor.getType() == deleteSuccessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(requirePredecessor.getType()).contains(deleteSuccessor.getType());
		
		return directType || superType || subType;
	}
	
	/**
	 * Checks all nodes for Forbid-Create dependencies.
	 * 
	 * @param forbidPredecessors
	 *            Edges from NACs (<< forbid >>).
	 * @param createSuccessors
	 *            Nodes on RHS only (<< create >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findForbidCreateNodeDependencies(
			Collection<Node> forbidPredecessors, 
			Collection<Node> createSuccessors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<>();
		for (Node predecessorNode : forbidPredecessors) {

			// Is transient potential dependences?
			boolean isTransient;
			if (isPrecondition(predecessorNode.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}

			for (Node successorNode : createSuccessors) {
				if (isForbidCreateDependency(predecessorNode, successorNode)) {
					// Change-Forbid dependence found
					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.FORBID_CREATE);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}

	/**
	 * Checks two nodes for a Forbid-Create dependency.
	 * 
	 * @param forbidPredecessor
	 *            Node is a NAC (<< forbid >>).
	 * @param createSuccessor
	 *            Node is on RHS only (<< create >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isForbidCreateDependency(Node forbidPredecessor, Node createSuccessor) {
		
		assert (isForbiddenNode(forbidPredecessor)) : "Input Assertion failed: forbidden node expected!";
		assert (isCreationNode(createSuccessor)) : "Input Assertion failed: creation node expected!";

		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Forbid-Node-Type
		 */

		boolean superType = createSuccessor.getType().getEAllSuperTypes().contains(forbidPredecessor.getType());
		boolean directType = createSuccessor.getType() == forbidPredecessor.getType();

		if (directType || superType) {
			return true;
		}

		return false;
	}
	

	
	/*
	 * Edges
	 */

	/**
	 * 
	 * @param createPredecessors
	 * @param deleteSuccessors
	 * @param potRuleDep
	 * @return
	 */
	protected Set<PotentialEdgeDependency> findCreateDeleteEdgeDependencies(
			Collection<Edge> createPredecessors,
			Collection<Edge> deleteSuccessors,
			PotentialRuleDependencies potRuleDep) {

		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (Edge rhsPredecessorEdge : createPredecessors) {
			for (Edge successorEdge : deleteSuccessors) {
				if (isCreateDeleteDependency(rhsPredecessorEdge, successorEdge, potRuleDep)) {
					// Create-Use dependence found
					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(rhsPredecessorEdge);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);
					potDep.setTransient(true);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	protected boolean isCreateDeleteDependency(
			Edge createPredecessor,
			Edge deleteSuccessor,
			PotentialRuleDependencies potRuleDep) {

		assert (isCreationEdge(createPredecessor)) : "Input Assertion failed: creation edge expected!";
		assert (isDeletionEdge(deleteSuccessor)) : "Input Assertion failed: deletion edge expected!";
		
		if (createPredecessor.getType() != deleteSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = createPredecessor.getSource();
		Node predecessorTgt = createPredecessor.getTarget();
		Node succesorSrc = deleteSuccessor.getSource();
		Node succesorTgt = deleteSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isCreationNode(predecessorSrc)) {
			srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
		} else {
			assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isCreationNode(predecessorTgt)) {
			tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
		} else {
			assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}
	
	/**
	 * Checks all edges for Create-Use dependencies.
	 * 
	 * @param createPredecessors
	 *            Edges on RHS only (<< create >>).
	 * @param preserveSuccessors
	 *            Edges on LHS and RHS (<< preserved >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findCreateUseEdgeDependencies(
			Collection<Edge> createPredecessors,
			Collection<EdgePair> preserveSuccessors,
			PotentialRuleDependencies potRuleDep) {

		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (Edge rhsPredecessorEdge : createPredecessors) {
			
			// Is transient potential dependences?
			boolean isTransient;
			if (isPreservedEdgePostCondition()) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}

			for (EdgePair successorEdge : preserveSuccessors) {
				if (isCreateUseDependency(rhsPredecessorEdge, successorEdge, potRuleDep)) {
					// Create-Use dependence found
					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
					potDep.setSourceEdge(successorEdge.getLhsEdge());
					potDep.setTargetEdge(rhsPredecessorEdge);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}

	/**
	 * Checks two edges for a Create-Use dependency.
	 * 
	 * @param createPredecessor
	 *            Edge is on RHS only (<< create >>).
	 * @param preserveSuccessor
	 *            Edge is on LHS and RHS (<< preserve >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateUseDependency(Edge createPredecessor, EdgePair preserveSuccessor, 
			PotentialRuleDependencies potRuleDep) {

		assert (isCreationEdge(createPredecessor)) : "Input Assertion failed: creation edge expected!";

		if (createPredecessor.getType() != preserveSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = createPredecessor.getSource();
		Node predecessorTgt = createPredecessor.getTarget();
		Node succesorSrc = preserveSuccessor.getLhsEdge().getSource();
		Node succesorTgt = preserveSuccessor.getLhsEdge().getTarget();

		// Src
		boolean srcOK = false;
		if (isCreationNode(predecessorSrc)) {
			srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
		} else {
			assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isCreationNode(predecessorTgt)) {
			tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
		} else {
			assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}
	
	/**
	 * Checks all edges for Create-Use dependencies.
	 * 
	 * @param createPredecessors
	 *            Edges on RHS only (<< create >>).
	 * @param requireSuccessors
	 *            Edges from PACs (<< require >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findCreateRequireEdgeDependencies(
			Collection<Edge> createPredecessors,
			Collection<Edge> requireSuccessors,
			PotentialRuleDependencies potRuleDep) {

		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (Edge successorEdge : requireSuccessors) {

			// Is transient potential dependences?
			boolean isTransient;
			if (isPostcondition(successorEdge.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}

			for (Edge rhsPredecessorEdge : createPredecessors) {
				if (isCreateRequireDependency(rhsPredecessorEdge, successorEdge, potRuleDep)) {
					// Create-Use dependence found
					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(rhsPredecessorEdge);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two edges for a Create-Use dependency.
	 * 
	 * @param createPredecessor
	 *            Edge is on RHS only (<< create >>).
	 * @param requireSuccessor
	 *            Edge is in PAC (<< require >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateRequireDependency(Edge createPredecessor, Edge requireSuccessor, 
			PotentialRuleDependencies potRuleDep) {
		
		assert (isCreationEdge(createPredecessor)) : "Input Assertion Failed!";
		assert (isRequireEdge(requireSuccessor)) : "Input Assertion Failed!";

		if (createPredecessor.getType() != requireSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = createPredecessor.getSource();
		Node predecessorTgt = createPredecessor.getTarget();
		Node succesorSrc = requireSuccessor.getSource();
		Node succesorTgt = requireSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isCreationNode(predecessorSrc)) {
			srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
		} else {
			assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isCreationNode(predecessorTgt)) {
			tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
		} else {
			assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}
	
	/**
	 * Checks all edges for Delete-Create dependencies. Note, that is no real dependency but needed to detect potential (partial) transient effects.
	 * 
	 * @param deletePredecessors
	 *            Edges on LHS only (<< delete >>).
	 * @param createSuccessors
	 *            Edges on RHS only (<< create >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findDeleteCreateEdgeDependencies(
			Collection<Edge> deletePredecessors,
			Collection<Edge> createSuccessors,
			PotentialRuleDependencies potRuleDep) {
		
		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (Edge successorEdge : createSuccessors) {

			for (Edge predecessorEdge : deletePredecessors) {
				if (isDeleteCreateDependency(predecessorEdge, successorEdge, potRuleDep)) {
					// Delete-Create dependence found
					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(predecessorEdge);
					potDep.setKind(PotentialDependencyKind.DELETE_CREATE);
					potDep.setTransient(true);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two edges for a Delete-Create dependency. Note, that is no real dependency but needed to detect potential (partial) transient effects.
	 * 
	 * @param deletePredecessor
	 *            Edge is on LHS only (<< delete >>).
	 * @param createSuccessor
	 *            Edge is on RHS only (<< create >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isDeleteCreateDependency(
			Edge deletePredecessor,
			Edge createSuccessor,
			PotentialRuleDependencies potRuleDep) {
		
		assert (isDeletionEdge(deletePredecessor)) : "Input Assertion failed: deletion edge expected!";
		assert (isCreationEdge(createSuccessor)) : "Input Assertion failed: creation edge expected!";

		// Edge types are equal?
		if (deletePredecessor.getType() != createSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = deletePredecessor.getSource();
		Node predecessorTgt = deletePredecessor.getTarget();
		Node succesorSrc = createSuccessor.getSource();
		Node succesorTgt = createSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isDeletionNode(predecessorSrc)) {
			srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
		} else {
			assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isDeletionNode(predecessorTgt)) {
			tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
		} else {
			assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}
	
	/**
	 * Checks all edges for Delete-Forbid dependencies.
	 * 
	 * @param deletePredecessors
	 *            Edges on LHS only (<< delete >>).
	 * @param forbidSuccessors
	 *            Edges from NACs (<< forbid >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findDeleteForbidEdgeDependencies(
			Collection<Edge> deletePredecessors,
			Collection<Edge> forbidSuccessors,
			PotentialRuleDependencies potRuleDep) {
		
		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (Edge successorEdge : forbidSuccessors) {

			// Is transient potential dependences?
			boolean isTransient;
			if (isPrecondition(successorEdge.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}

			for (Edge predecessorEdge : deletePredecessors) {
				if (isDeleteForbidDependency(predecessorEdge, successorEdge, potRuleDep)) {
					// Change-Forbid dependence found
					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(predecessorEdge);
					potDep.setKind(PotentialDependencyKind.DELETE_FORBID);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two edges for a Delete-Forbid dependency.
	 * 
	 * @param deletePredecessor
	 *            Edge is on LHS only (<< delete >>).
	 * @param forbidSuccessor
	 *            Edge is a NAC (<< forbid >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isDeleteForbidDependency(
			Edge deletePredecessor,
			Edge forbidSuccessor,
			PotentialRuleDependencies potRuleDep) {
		
		assert (isDeletionEdge(deletePredecessor)) : "Input Assertion failed: deletion edge expected!";
		assert (isForbiddenEdge(forbidSuccessor)) : "Input Assertion Failed: forbidden edge expected!";

		// Edge types are equal?
		if (deletePredecessor.getType() != forbidSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = deletePredecessor.getSource();
		Node predecessorTgt = deletePredecessor.getTarget();
		Node succesorSrc = forbidSuccessor.getSource();
		Node succesorTgt = forbidSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isDeletionNode(predecessorSrc)) {
			srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
		} else {
			assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isDeletionNode(predecessorTgt)) {
			tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
		} else {
			assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}
	
	/**
	 * Checks all edges for Use-Delete dependencies.
	 * 
	 * @param preservePredecessors
	 *            Edges on LHS and RHS (<< preserved >>).
	 * @param deleteSuccessors
	 *            Edges on LHS only (<< delete >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findUseDeleteEdgeDependencies(
			Collection<EdgePair> preservePredecessors,
			Collection<Edge> deleteSuccessors, 
			PotentialRuleDependencies potRuleDep) {

		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (EdgePair predecessorEdge : preservePredecessors) {

			// Is transient potential dependences?
			boolean isTransient;
			if (isPreservedEdgePreCondition()) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}

			for (Edge successorEdge : deleteSuccessors) {
				if (isUseDeleteDependency(predecessorEdge, successorEdge, potRuleDep)) {
					// Delete-Use dependence found
					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(predecessorEdge.getLhsEdge());
					potDep.setKind(PotentialDependencyKind.USE_DELETE);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two edges for a Use-Delete dependency.
	 * 
	 * @param preservePredecessor
	 *            Edge is on LHS and RHS (<< preserve >>).
	 * @param deleteSuccessor
	 *            Edge is on LHS only (<< delete >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isUseDeleteDependency(
			EdgePair preservePredecessor,
			Edge deleteSuccessor, 
			PotentialRuleDependencies potRuleDep) {

		assert(isDeletionEdge(deleteSuccessor)) : "Input Assertion failed: deletion edge expected!";

		if (preservePredecessor.getType() != deleteSuccessor.getType()) {
			return false;
		}
		Node predecessorSrc = preservePredecessor.getLhsEdge().getSource();
		Node predecessorTgt = preservePredecessor.getLhsEdge().getTarget();
		Node succesorSrc = deleteSuccessor.getSource();
		Node succesorTgt = deleteSuccessor.getTarget();
		
		// Src
		boolean srcOK = false;
		if (isDeletionNode(succesorSrc)) {
			srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
		} else {
			assert isPreservedNode(succesorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isDeletionNode(succesorTgt)) {
			tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
		} else {
			assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}
	
	/**
	 * Checks all edges for Use-Delete dependencies.
	 * 
	 * @param requirePredecessors
	 *            Edges from PACs (<< require >>).
	 * @param deleteSuccessors
	 *            Edges on LHS only (<< delete >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findRequireDeleteEdgeDependencies(
			Collection<Edge> requirePredecessors,
			Collection<Edge> deleteSuccessors, 
			PotentialRuleDependencies potRuleDep) {
		
		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (Edge predecessorEdge : requirePredecessors) {
			
			// Is transient potential dependences?
			boolean isTransient;
			if (isPrecondition(predecessorEdge.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}
			
			for (Edge successorEdge : deleteSuccessors) {
				if (isRequireDeleteDependency(predecessorEdge, successorEdge, potRuleDep)) {
					// Delete-Use dependence found
					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(predecessorEdge);
					potDep.setKind(PotentialDependencyKind.USE_DELETE);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two edges for a Use-Delete dependency.
	 * 
	 * @param requirePredecessor
	 *            Edge is in PAC (<< require >>).
	 * @param deleteSuccessor
	 *            Edge is on LHS only (<< delete >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isRequireDeleteDependency(Edge requirePredecessor, Edge deleteSuccessor, 
			PotentialRuleDependencies potRuleDep) {
		
		assert(isRequireEdge(requirePredecessor)) : "Input Assertion failed: required edge expected!";
		assert(isDeletionEdge(deleteSuccessor)) : "Input Assertion failed: deletion edge expected!";

		if (requirePredecessor.getType() != deleteSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = requirePredecessor.getSource();
		Node predecessorTgt = requirePredecessor.getTarget();
		Node succesorSrc = deleteSuccessor.getSource();
		Node succesorTgt = deleteSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isDeletionNode(succesorSrc)) {
			srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
		} else {
			assert isPreservedNode(succesorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = true;
		}

		// Tgt
		boolean tgtOK = false;
		if (isDeletionNode(succesorTgt)) {
			tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
		} else {
			assert isPreservedNode(succesorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = true;
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}
	
	/**
	 * Checks all edges for Forbid-Create dependencies.
	 * 
	 * @param forbidPredecessors
	 *            Edges from NACs (<< forbid >>).
	 * @param createSuccessors
	 *            Edges on RHS only (<< create >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findForbidCreateEdgeDependencies(
			Collection<Edge> forbidPredecessors,
			Collection<Edge> createSuccessors,
			PotentialRuleDependencies potRuleDep) {
		
		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (Edge successorEdge : createSuccessors) {
			
			// Is transient potential dependences?
			boolean isTransient;
			if (isPrecondition(successorEdge.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}

			for (Edge predecessorEdge : forbidPredecessors) {
				if (isForbidCreateDependency(predecessorEdge, successorEdge, potRuleDep)) {
					// Change-Forbid dependence found
					PotentialEdgeDependency potDep = rbFactory.createPotentialEdgeDependency();
					potDep.setSourceEdge(successorEdge);
					potDep.setTargetEdge(predecessorEdge);
					potDep.setKind(PotentialDependencyKind.FORBID_CREATE);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two edges for a Forbid-Create dependency.
	 * 
	 * @param forbidPredecessors
	 *            Edge is a NAC (<< forbid >>).
	 * @param createSuccessor
	 *            Edge is on RHS only (<< create >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isForbidCreateDependency(
			Edge forbidPredecessors,
			Edge createSuccessor,
			PotentialRuleDependencies potRuleDep) {
		
		assert (isForbiddenEdge(forbidPredecessors)) : "Input Assertion failed: forbidden edge expected!";
		assert (isCreationEdge(createSuccessor)) : "Input Assertion failed: creation edge expected!";

		// Edge types are equal?
		if (forbidPredecessors.getType() != createSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = forbidPredecessors.getSource();
		Node predecessorTgt = forbidPredecessors.getTarget();
		Node succesorSrc = createSuccessor.getSource();
		Node succesorTgt = createSuccessor.getTarget();

		// Src
		boolean srcOK = false;
		if (isCreationNode(predecessorSrc)) {
			srcOK = hasPotentialNodeDependency(potRuleDep, predecessorSrc, succesorSrc);
		} else {
//			assert isPreservedNode(predecessorSrc) : "<< preserve >> predecessor source node expected!";
			srcOK = isPreservedNode(predecessorSrc);
		}

		// Tgt
		boolean tgtOK = false;
		if (isCreationNode(succesorTgt)) {
			tgtOK = hasPotentialNodeDependency(potRuleDep, predecessorTgt, succesorTgt);
		} else {
//			assert isPreservedNode(predecessorTgt) : "<< preserve >> predecessor target node expected!";
			tgtOK = isPreservedNode(predecessorTgt);
		}

		// Tgt & Src
		return srcOK && tgtOK;
	}

	/*
	 * Attributes
	 */

	
	/**
	 * Checks all attributes for a Create-Use dependency.
	 * @param createPredecessors Attribute is on RHS only in a creation node(<< create >>).
	 * @param useSuccessors Attribute is on LHS (<< delete / preserve >>) or in PAC (<<require>>).
	 * @return All attribute create-use dependencies
	 */
	protected Set<PotentialAttributeDependency> findCreateUseAttributeDependency(Collection<Attribute> createPredecessors, Collection<Attribute> useSuccessors){
		// Calculate non-transients?
		if (!nonTransientPDs) {
			return Collections.emptySet();
		}

		Set<PotentialAttributeDependency> potDeps = new HashSet<>();
		for (Attribute createPredecessor : createPredecessors) {
			for (Attribute useSuccessor : useSuccessors) {
				if (isCreateUseDependency(createPredecessor, useSuccessor)) {
					// Create-Use dependence found
					PotentialAttributeDependency potDep = rbFactory.createPotentialAttributeDependency();
					potDep.setSourceAttribute(useSuccessor);
					potDep.setTargetAttribute(createPredecessor);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);
					potDep.setTransient(false);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two attributes for a Create-Use dependency.
	 * 
	 * @param createPredecessor
	 *            Attribute is on RHS only in a creation node(<< create >>).
	 * @param useSuccessor
	 *            Attribute is on LHS (<< delete / preserve >>) or in PAC (<<require>>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateUseDependency(Attribute createPredecessor, Attribute useSuccessor) {
		
		assert (isCreationAttribute(createPredecessor) && isCreationNode(createPredecessor.getNode())) : "Input Assertion failed: set attribute in creation node expected!";
		assert (isLHSAttribute(useSuccessor)
				|| isRequireAttribute(useSuccessor)) : "Input Assertion failed: lhs or required attribute expected!";

		// Attributes have the same type
		if (createPredecessor.getType() == useSuccessor.getType()) {

			// is predecessor nodeType assignable to successor nodeType?
			if (isAssignableTo(createPredecessor.getNode().getType(), useSuccessor.getNode().getType())) {
				// Attribute case differentiation precondition is fulfilled?
				if (attributeCaseDifferentiation(createPredecessor, useSuccessor)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks all attributes for Change-Use dependencies.
	 * 
	 * @param setPredecessors
	 *            Attributes on RHS only (<< create >>).
	 * @param useSuccessors
	 *            Attributes on LHS (<< delete / preserve >>) or from PACs (<<require>>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialAttributeDependency> findChangeUseAttributeDependency(
			Collection<Attribute> setPredecessors, Collection<Attribute> useSuccessors) {

		// Calculate non-transients? 
		if (!nonTransientPDs) {
			return Collections.emptySet();
		}

		Set<PotentialAttributeDependency> potDeps = new HashSet<>();
		for (Attribute rhsPredecessorAttribute : setPredecessors) {
			for (Attribute lhsSuccessorAttribute : useSuccessors) {
				if (isChangeUseDependency(rhsPredecessorAttribute, lhsSuccessorAttribute)) {
					// Change-Use dependence found
					PotentialAttributeDependency potDep = rbFactory.createPotentialAttributeDependency();
					potDep.setSourceAttribute(lhsSuccessorAttribute);
					potDep.setTargetAttribute(rhsPredecessorAttribute);
					potDep.setKind(PotentialDependencyKind.CHANGE_USE);
					potDep.setTransient(false);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two attributes for a Change-Use dependency.
	 * 
	 * @param setPredecessor
	 *            Attribute is on RHS only (<< create >>).
	 * @param useSuccessor
	 *            Attribute is on LHS (<< delete / preserve >>) or in PAC (<<require>>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isChangeUseDependency(Attribute setPredecessor, Attribute useSuccessor) {
		
		assert ((isCreationAttribute(setPredecessor) || isChangingAttribute(setPredecessor))
				&& isPreservedNode(setPredecessor
						.getNode())) : "Input Assertion failed: set or changing attribute in preserved node expected!";
		assert (isLHSAttribute(useSuccessor)
				|| isRequireAttribute(useSuccessor)) : "Input Assertion failed: lhs or required attribute expected!";

		// Attributes have the same type
		if (setPredecessor.getType() == useSuccessor.getType()) {

			// is predecessor nodeType assignable to successor nodeType?
			if (isAssignableTo(setPredecessor.getNode().getType(), useSuccessor.getNode().getType())) {
				// Attribute case differentiation precondition is fulfilled?
				if (attributeCaseDifferentiation(setPredecessor, useSuccessor)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks all attributes for Change-Forbid dependencies.
	 * 
	 * @param rhsPredecessor
	 *            Attributes on RHS only (<< create >>).
	 * @param forbidSuccessor
	 *            Attributes from NACs (<< forbid >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialAttributeDependency> findChangeForbidAttributeDependencies(
			Collection<Attribute> setPredecessors,
			Collection<Attribute> forbidSuccessors) {

		Set<PotentialAttributeDependency> potDeps = new HashSet<>();
		for (Attribute successorAttribute : forbidSuccessors) {
			
			// Is transient potential dependences?
			boolean isTransient;
			if (isPostcondition(successorAttribute.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}

			for (Attribute predecessorAttribute : setPredecessors) {
				if (isChangeForbidDependency(predecessorAttribute, successorAttribute)) {
					// Change-Forbid dependence found
					PotentialAttributeDependency potDep = rbFactory.createPotentialAttributeDependency();
					potDep.setSourceAttribute(successorAttribute);
					potDep.setTargetAttribute(predecessorAttribute);
					potDep.setKind(PotentialDependencyKind.CHANGE_FORBID);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two attributes for a Change-Forbid dependency.
	 * 
	 * @param setPredecessor
	 *            Attribute is on RHS only (<< create >>).
	 * @param forbidSuccessor
	 *            Attribute is a NAC (<< forbid >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isChangeForbidDependency(Attribute setPredecessor, Attribute forbidSuccessor) {

		assert ((isCreationAttribute(setPredecessor) || isChangingAttribute(setPredecessor))
				&& isPreservedNode(setPredecessor
						.getNode())) : "Input Assertion failed: creation or changing attribute in preserved node expected!";
		assert (isForbiddenAttribute(forbidSuccessor)) : "Input Assertion failed: forbidden attribute expected!";

		// Attributes have the same type
		if (setPredecessor.getType() == forbidSuccessor.getType()) {

			if (assignable(setPredecessor.getNode().getType(), forbidSuccessor.getNode().getType())) {
				// Attribute case differentiation precondition is fulfilled?
				if (attributeCaseDifferentiation(setPredecessor, forbidSuccessor)) {
					return true;
				}
			}

		}

		return false;
	}
	
	/**
	 * Checks all attributes for Use-Change dependencies.
	 * 
	 * @param usePredecessors
	 *            Attributes on LHS (<< delete / preserve >>) or from PACs (<<require>>).
	 * @param setSuccessors
	 *            Attributes on RHS only (<< create >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialAttributeDependency> findUseChangeAttributeDependencies(
			Collection<Attribute> usePredecessors,
			Collection<Attribute> setSuccessors) {

		// Calculate transients? 
		if (!transientPDs) {
			return Collections.emptySet();
		}

		Set<PotentialAttributeDependency> potDeps = new HashSet<>();
		for (Attribute rhsPredecessorAttribute : usePredecessors) {
			for (Attribute lhsSuccessorAttribute : setSuccessors) {
				if (isUseChangeDependency(rhsPredecessorAttribute, lhsSuccessorAttribute)) {
					// Change-Use dependence found
					PotentialAttributeDependency potDep = rbFactory.createPotentialAttributeDependency();
					potDep.setSourceAttribute(lhsSuccessorAttribute);
					potDep.setTargetAttribute(rhsPredecessorAttribute);
					potDep.setKind(PotentialDependencyKind.USE_CHANGE);
					potDep.setTransient(true);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two attributes for a Use-Change dependency.
	 * 
	 * @param usePredecessor
	 *            Attribute is on LHS (<< delete / preserve >>) or in PAC (<<require>>).
	 * @param setSuccessor
	 *            Attribute is on RHS only (<< create >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isUseChangeDependency(Attribute usePredecessor, Attribute setSuccessor) {

		assert (isLHSAttribute(usePredecessor)
				|| isRequireAttribute(usePredecessor)) : "Input Assertion failed: lhs or required attribute expected!";
		assert ((isCreationAttribute(setSuccessor) || isChangingAttribute(setSuccessor)) && isPreservedNode(setSuccessor
				.getNode())) : "Input Assertion failed: set or changing attribute in preserved node expected!";

		// Attributes have the same type
		if (usePredecessor.getType().equals(setSuccessor.getType())) {

			// Attribute case differentiation precondition is fulfilled?
			if (attributeCaseDifferentiation(usePredecessor, setSuccessor)) {
				return true;
			}

		}

		return false;
	}
	
	/**
	 * Checks all attributes for Forbid-Change dependencies.
	 * 
	 * @param forbidPredecessors
	 *            Attributes from NACs (<< forbid >>).
	 * @param setSuccessors
	 *            Attributes on RHS only (<< create >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialAttributeDependency> findForbidChangeAttributeDependencies(
			Collection<Attribute> forbidPredecessors,
			Collection<Attribute> setSuccessors) {

		Set<PotentialAttributeDependency> potDeps = new HashSet<>();
		for (Attribute successorAttribute : setSuccessors) {
			
			// Is transient potential dependences?
			boolean isTransient;
			if (isPrecondition(successorAttribute.getGraph())) {
				isTransient = false;
				
				// Calculate non-transients? 
				if (!nonTransientPDs) {
					continue;
				}
			} else {
				isTransient = true;
				
				// Calculate transients? 
				if (!transientPDs) {
					continue;
				}
			}
			
			for (Attribute predecessorAttribute : forbidPredecessors) {
				if (isForbidChangeDependency(predecessorAttribute, successorAttribute)) {
					// Change-Forbid dependence found
					PotentialAttributeDependency potDep = rbFactory.createPotentialAttributeDependency();
					potDep.setSourceAttribute(successorAttribute);
					potDep.setTargetAttribute(predecessorAttribute);
					potDep.setKind(PotentialDependencyKind.FORBID_CHANGE);
					potDep.setTransient(isTransient);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two attributes for a Forbid-Change dependency.
	 * 
	 * @param forbidPredecessor
	 *            Attribute is a NAC (<< forbid >>).
	 * @param setSuccessor
	 *            Attribute is on RHS only (<< create >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isForbidChangeDependency(Attribute forbidPredecessor, Attribute setSuccessor) {

		assert (isForbiddenAttribute(forbidPredecessor)) : "Input Assertion failed: forbidden attribute expected!";
		assert ((isCreationAttribute(setSuccessor)) || isChangingAttribute(setSuccessor))
				&& isPreservedNode(setSuccessor
						.getNode()) : "Input Assertion failed: set or changing attribute in preserve node expected!";

		// Attributes have the same type
		if (forbidPredecessor.getType() == setSuccessor.getType()) {
			// Attribute case differentiation precondition is fulfilled?
			if (attributeCaseDifferentiation(forbidPredecessor, setSuccessor)) {
				return true;
			}

		}
		return false;
	}
	


	/*
	 * Utils
	 */

	/**
	 * Tests if the given list contains a potential dependency between the two nodes.
	 * 
	 * @param potRuleDep
	 *            The list of potential dependencies.
	 * @param nodeA
	 *            The first node to test.
	 * @param nodeB
	 *            The second node to test.
	 * @return <code>true</code> if the list contains a potential dependency;
	 *         <code>false</code> otherwise.
	 */
	protected boolean hasPotentialNodeDependency(PotentialRuleDependencies potRuleDep, Node nodeA, Node nodeB) {
		for(PotentialNodeDependency potDep : potRuleDep.getPotentialNodeDependencies()) {
			if ((potDep.getSourceNode() == nodeA || potDep.getTargetNode() == nodeA)
					&& (potDep.getSourceNode() == nodeB || potDep.getTargetNode() == nodeB)) {
				return true;
			}
		}
		return false;
	}
}
