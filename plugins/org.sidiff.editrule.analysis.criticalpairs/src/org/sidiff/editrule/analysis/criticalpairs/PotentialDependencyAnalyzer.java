package org.sidiff.editrule.analysis.criticalpairs;

import static org.sidiff.common.emf.access.EMFMetaAccess.assignable;
import static org.sidiff.common.emf.access.EMFMetaAccess.isAssignableTo;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
		predecessorChangingAttributes.addAll(predecessor.getChangeAttributes().stream()
				.map(pair -> pair.getRhsAttribute()).collect(Collectors.toList()));
		List<Attribute> predecessorUsingAttributes = predecessor.getPreserveAttributes();
		List<Attribute> successorChangingAttributes = new ArrayList<>(successor.getSetAttributes());
		successorChangingAttributes.addAll(successor.getChangeAttributes().stream()
				.map(pair -> pair.getRhsAttribute()).collect(Collectors.toList()));
		List<Attribute> successorUsingAttributes = successor.getPreserveAttributes(); 

		// Get <<forbid>> attributes
		List<Attribute> predecessorForbidAttributes = predecessor.getForbidAttributes();
		List<Attribute> successorForbidAttributes = successor.getForbidAttributes();

		// Get <<require>> attributes
		List<Attribute> predecessorRequireAttributes = predecessor.getRequireAttributes();
		List<Attribute> successorRequireAttributes = successor.getRequireAttributes();

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

		// Use-Delete
		if (!predecessorPreserveNodes.isEmpty() && !successorDeleteNodes.isEmpty()) {
			pndConsumer.accept(findUseDeletes_Node(predecessorPreserveNodes, successorDeleteNodes));
		}

		// Use-Delete (PAC)
		if (!predecessorRequireNodes.isEmpty() && !successorDeleteNodes.isEmpty()) {
			pndConsumer.accept(findUseDeletes_Node_PAC(predecessorRequireNodes, successorDeleteNodes));
		}

		if(!predecessorCreateNodes.isEmpty()) {
			// Create-Use
			if (!successorPreserveNodes.isEmpty()) {
				pndConsumer.accept(findCreateUses_Node(predecessorCreateNodes, successorPreserveNodes));
			}

			// Create-Delete
			if (!successorDeleteNodes.isEmpty()) {
				pndConsumer.accept(findCreateDelete_Node(predecessorCreateNodes, successorDeleteNodes));
			}

			// Create-Use (PAC)
			if (!successorRequireNodes.isEmpty()) {
				pndConsumer.accept(findCreateUses_Node_PAC(predecessorCreateNodes, successorRequireNodes));
			}
		}

		// Forbid-Create
		if (!predecessorForbidNodes.isEmpty() && !successorCreateNodes.isEmpty()) {
			pndConsumer.accept(findForbidCreates_Node(predecessorForbidNodes, successorCreateNodes));
		}

		// Delete-Forbid
		if (!predecessorDeleteNodes.isEmpty() && !successorForbidNodes.isEmpty()) {
			pndConsumer.accept(findDeleteForbids_Node(predecessorDeleteNodes, successorForbidNodes));
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

		// Use-Delete
		if (!predecessorPreserveEdges.isEmpty() && !successorDeleteEdges.isEmpty()) {
			pedConsumer.accept(findUseDeletes_Edge(predecessorPreserveEdges, successorDeleteEdges, potRuleDep));
		}

		// Use-Delete (PAC)
		if (!predecessorRequireEdges.isEmpty() && !successorDeleteEdges.isEmpty()) {
			pedConsumer.accept(findUseDeletes_Edge_PAC(predecessorRequireEdges, successorDeleteEdges, potRuleDep));
		}

		if(!predecessorCreateEdges.isEmpty()) {
			// Create-Use
			if (!successorPreserveEdges.isEmpty()) {
				pedConsumer.accept(findCreateUses_Edge(predecessorCreateEdges, successorPreserveEdges, potRuleDep));
			}
			
			// Create-Delete
			if (!successorDeleteEdges.isEmpty()) {
				pedConsumer.accept(findCreateDelete_Edge(predecessorCreateEdges, successorDeleteEdges, potRuleDep));
			}
			
			// Create-Use (PAC)
			if (!successorRequireEdges.isEmpty()) {
				pedConsumer.accept(findCreateUses_Edge_PAC(predecessorCreateEdges, successorRequireEdges, potRuleDep));
			}
		}

		// Forbid-Create
		if (!predecessorForbidEdges.isEmpty() && !successorCreateEdges.isEmpty()) {
			pedConsumer.accept(findForbidCreates_Edge(predecessorForbidEdges, successorCreateEdges, potRuleDep));
		}

		// Delete-Forbid
		if (!predecessorDeleteEdges.isEmpty() && !successorForbidEdges.isEmpty()) {
			pedConsumer.accept(findDeleteForbids_Edge(predecessorDeleteEdges, successorForbidEdges, potRuleDep));
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

		if(!predecessorChangingAttributes.isEmpty()) {
			// Change-Use
			if (!successorUsingAttributes.isEmpty()) {
				padConsumer.accept(findChangeUses_Attribute(predecessorChangingAttributes, successorUsingAttributes));
			}
			
			// Change-Use (PAC)
			if (!successorRequireAttributes.isEmpty()) {
				padConsumer.accept(findChangeUses_Attribute(predecessorChangingAttributes, successorRequireAttributes));
			}
			
			// Change-Forbid
			if (!successorForbidAttributes.isEmpty()) {
				padConsumer.accept(findChangeForbids_Attribute(predecessorChangingAttributes, successorForbidAttributes));
			}

			// Forbid-Change
			if (!predecessorForbidAttributes.isEmpty()) {
				padConsumer.accept(findForbidChanges_Attribute(predecessorForbidAttributes, predecessorChangingAttributes));
			}
		}

		// Use-Change
		if (!predecessorUsingAttributes.isEmpty() && !successorChangingAttributes.isEmpty()) {
			padConsumer.accept(findUseChanges_Attribute(predecessorUsingAttributes, successorChangingAttributes));
		}

		// Use-Change (PAC)
		if (!predecessorRequireAttributes.isEmpty() && !successorChangingAttributes.isEmpty()) {
			padConsumer.accept(findUseChanges_Attribute(predecessorRequireAttributes, successorChangingAttributes));
		}

		return potRuleDep;
	}

	/*
	 * Nodes
	 */

	/**
	 * Checks all nodes for Use-Delete dependencies.
	 * 
	 * @param predecessors
	 *            Edges on LHS and RHS (<< preserve >>).
	 * @param lhsSuccessors
	 *            Nodes on LHS only (<< delete >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findUseDeletes_Node(
			Collection<NodePair> predecessors, Collection<Node> lhsSuccessors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<>();

		for (NodePair predecessorNode : predecessors) {
			
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
			
			for (Node successorNode : lhsSuccessors) {
				
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
		
		assert(isDeletionNode(lhsSuccessor)) : "Input Assertion Failed!";
		
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
	 * @param lhsSuccessors
	 *            Nodes on LHS only (<< delete >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findUseDeletes_Node_PAC(
			Collection<Node> requirePredecessors, Collection<Node> lhsSuccessors) {

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
			
			for (Node successorNode : lhsSuccessors) {
				if (isUseDeleteDependency_PAC(predecessorNode, successorNode)) {
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
	 * @param lhsSuccessor
	 *            Node is on LHS only (<< delete >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isUseDeleteDependency_PAC(Node requirePredecessor, Node lhsSuccessor) {
		
		assert(isRequireNode(requirePredecessor)) : "Input Assertion Failed!";
		assert(isDeletionNode(lhsSuccessor)) : "Input Assertion Failed!";
		
		/*
		 * Preserve-Node-Type + Preserve-Node-Sub-Types + Preserve-Node-Super-Types == Delete-Node-Type
		 */

		boolean superType = requirePredecessor.getType().getEAllSuperTypes().contains(lhsSuccessor.getType());
		boolean directType = requirePredecessor.getType() == lhsSuccessor.getType();
		boolean subType = getSubTypeIndex().getSubTypes(requirePredecessor.getType()).contains(lhsSuccessor.getType());
		
		return directType || superType || subType;
	}

	/**
	 * Checks all nodes for Create-Use dependencies.
	 * 
	 * @param rhsPredecessors
	 *            Nodes on RHS only (<< create >>).
	 * @param successors
	 *            Edges on LHS and RHS (<< preserve >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findCreateUses_Node(
			Collection<Node> rhsPredecessors, Collection<NodePair> successors) {

		Set<PotentialNodeDependency> potDeps = new HashSet<>();
		for (NodePair successorNode : successors) {
			
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

			for (Node predecessorNode : rhsPredecessors) {
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

	private Set<PotentialNodeDependency> findCreateDelete_Node(
			Collection<Node> predecessorCreateNodes,
			Collection<Node> successorDeleteNodes) {

		Set<PotentialNodeDependency> potDeps = new HashSet<>();
		for (Node successorNode : successorDeleteNodes) {
			for (Node predecessorNode : predecessorCreateNodes) {
				if (isCreateDeleteDependency(predecessorNode, successorNode)) {
					// Create-Use dependence found
					PotentialNodeDependency potDep = rbFactory.createPotentialNodeDependency();
					potDep.setSourceNode(successorNode);
					potDep.setTargetNode(predecessorNode);
					potDep.setKind(PotentialDependencyKind.CREATE_USE);
					potDep.setTransient(true);
					potDeps.add(potDep);
				}
			}
		}
		return potDeps;
	}
	
	/**
	 * Checks two nodes for a Create-Use dependency.
	 * 
	 * @param rhsPredecessor
	 *            Node is on RHS only (<< create >>).
	 * @param successor
	 *            Node is on LHS and RHS (<< preserve >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateUseDependency(Node rhsPredecessor, NodePair successor) {

		assert (isCreationNode(rhsPredecessor)) : "Input Assertion Failed!";
		
		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Preserve-Node-Type
		 */

		boolean superType = rhsPredecessor.getType().getEAllSuperTypes().contains(successor.getType());
		boolean directType = rhsPredecessor.getType() == successor.getType();

		return directType || superType;
	}

	private boolean isCreateDeleteDependency(Node predecessorNode, Node successorNode) {
		assert (isCreationNode(predecessorNode)) : "Input Assertion Failed!";
		
		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Preserve-Node-Type
		 */

		boolean superType = predecessorNode.getType().getEAllSuperTypes().contains(successorNode.getType());
		boolean directType = predecessorNode.getType() == successorNode.getType();

		return directType || superType;
	}
	
	/**
	 * Checks all nodes for Create-Use dependencies.
	 * 
	 * @param rhsPredecessors
	 *            Nodes on RHS only (<< create >>).
	 * @param requireSuccessors
	 *            Edges from PACs (<< require >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findCreateUses_Node_PAC(
			Collection<Node> rhsPredecessors,
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
			
			for (Node predecessorNode : rhsPredecessors) {
				if (isCreateUseDependency_PAC(predecessorNode, successorNode)) {
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
	 * @param rhsPredecessor
	 *            Node is on RHS only (<< create >>).
	 * @param requireSuccessor
	 *            Node is in PAC (<< require >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateUseDependency_PAC(Node rhsPredecessor, Node requireSuccessor) {

		assert (isCreationNode(rhsPredecessor)) : "Input Assertion Failed!";
		assert (isRequireNode(requireSuccessor)) : "Input Assertion Failed!";
		
		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Require-Node-Type
		 */

		boolean superType = rhsPredecessor.getType().getEAllSuperTypes().contains(requireSuccessor.getType());
		boolean directType = rhsPredecessor.getType() == requireSuccessor.getType();

		return directType || superType;
	}
	
	/**
	 * Checks all nodes for Forbid-Create dependencies.
	 * 
	 * @param forbidPredecessors
	 *            Edges from NACs (<< forbid >>).
	 * @param rhsSuccessors
	 *            Nodes on RHS only (<< create >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findForbidCreates_Node(
			Collection<Node> forbidPredecessors, 
			Collection<Node> rhsSuccessors) {

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

			for (Node successorNode : rhsSuccessors) {
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
	 * @param rhsSuccessor
	 *            Node is on RHS only (<< create >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isForbidCreateDependency(Node forbidPredecessor, Node rhsSuccessor) {
		
		assert (isForbiddenNode(forbidPredecessor)) : "Input Assertion Failed!";
		assert (isCreationNode(rhsSuccessor)) : "Input Assertion Failed!";

		/*
		 * Create-Node-Type + Create-Node-Sub-Types + Create-Node-Super-Types == Forbid-Node-Type
		 */

		boolean superType = rhsSuccessor.getType().getEAllSuperTypes().contains(forbidPredecessor.getType());
		boolean directType = rhsSuccessor.getType() == forbidPredecessor.getType();

		if (directType || superType) {
			return true;
		}

		return false;
	}
	
	/**
	 * Checks all nodes for Delete-Forbid dependencies.
	 * 
	 * @param lhsPredecessors
	 *            Nodes on LHS only (<< delete >>).
	 * @param forbidSuccessors
	 *            Edges from NACs (<< forbid >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialNodeDependency> findDeleteForbids_Node(
			Collection<Node> lhsPredecessors, 
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

			for (Node predecessorNode : lhsPredecessors) {
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
	 * @param lhsPredecessor
	 *            Node is on LHS only (<< delete >>).
	 * @param forbidSuccessor
	 *            Node is a NAC (<< forbid >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isDeleteForbidDependency(Node lhsPredecessor, Node forbidSuccessor) {
		
		assert (isDeletionNode(lhsPredecessor)) : "Input Assertion Failed!";
		assert (isForbiddenNode(forbidSuccessor)) : "Input Assertion Failed!";
		
		return assignable(lhsPredecessor.getType(), forbidSuccessor.getType());
	}
	
	/*
	 * Edges
	 */

	/**
	 * Checks all edges for Use-Delete dependencies.
	 * 
	 * @param predecessors
	 *            Edges on LHS and RHS (<< preserved >>).
	 * @param lhsSuccessors
	 *            Edges on LHS only (<< delete >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findUseDeletes_Edge(
			List<EdgePair> predecessors,
			List<Edge> lhsSuccessors, 
			PotentialRuleDependencies potRuleDep) {

		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (EdgePair predecessorEdge : predecessors) {

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

			for (Edge successorEdge : lhsSuccessors) {
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
	 * @param predecessor
	 *            Edge is on LHS and RHS (<< preserve >>).
	 * @param lhsSuccessor
	 *            Edge is on LHS only (<< delete >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isUseDeleteDependency(
			EdgePair predecessor,
			Edge lhsSuccessor, 
			PotentialRuleDependencies potRuleDep) {

		assert(isDeletionEdge(lhsSuccessor)) : "Input Assertion Failed!";

		if (predecessor.getType() != lhsSuccessor.getType()) {
			return false;
		}
		Node predecessorSrc = predecessor.getLhsEdge().getSource();
		Node predecessorTgt = predecessor.getLhsEdge().getTarget();
		Node succesorSrc = lhsSuccessor.getSource();
		Node succesorTgt = lhsSuccessor.getTarget();
		
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
	 * @param lhsSuccessors
	 *            Edges on LHS only (<< delete >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findUseDeletes_Edge_PAC(
			List<Edge> requirePredecessors,
			List<Edge> lhsSuccessors, 
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
			
			for (Edge successorEdge : lhsSuccessors) {
				if (isUseDeleteDependency_PAC(predecessorEdge, successorEdge, potRuleDep)) {
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
	 * @param lhsSuccessor
	 *            Edge is on LHS only (<< delete >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isUseDeleteDependency_PAC(Edge requirePredecessor, Edge lhsSuccessor, 
			PotentialRuleDependencies potRuleDep) {
		
		assert(isRequireEdge(requirePredecessor)) : "Input Assertion Failed!";
		assert(isDeletionEdge(lhsSuccessor)) : "Input Assertion Failed!";

		if (requirePredecessor.getType() != lhsSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = requirePredecessor.getSource();
		Node predecessorTgt = requirePredecessor.getTarget();
		Node succesorSrc = lhsSuccessor.getSource();
		Node succesorTgt = lhsSuccessor.getTarget();

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
	 * Checks all edges for Create-Use dependencies.
	 * 
	 * @param rhsPredecessors
	 *            Edges on RHS only (<< create >>).
	 * @param successors
	 *            Edges on LHS and RHS (<< preserved >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findCreateUses_Edge(
			List<Edge> rhsPredecessors,
			List<EdgePair> successors,
			PotentialRuleDependencies potRuleDep) {

		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (Edge rhsPredecessorEdge : rhsPredecessors) {
			
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

			for (EdgePair successorEdge : successors) {
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

	private Set<PotentialEdgeDependency> findCreateDelete_Edge(
			Collection<Edge> predecessorCreateEdges,
			Collection<Edge> successorDeleteEdges,
			PotentialRuleDependencies potRuleDep) {

		Set<PotentialEdgeDependency> potDeps = new HashSet<>();
		for (Edge rhsPredecessorEdge : predecessorCreateEdges) {
			for (Edge successorEdge : successorDeleteEdges) {
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

	/**
	 * Checks two edges for a Create-Use dependency.
	 * 
	 * @param rhsPredecessor
	 *            Edge is on RHS only (<< create >>).
	 * @param successor
	 *            Edge is on LHS and RHS (<< preserve >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateUseDependency(Edge rhsPredecessor, EdgePair successor, 
			PotentialRuleDependencies potRuleDep) {

		assert (isCreationEdge(rhsPredecessor)) : "Input Assertion Failed!";

		if (rhsPredecessor.getType() != successor.getType()) {
			return false;
		}

		Node predecessorSrc = rhsPredecessor.getSource();
		Node predecessorTgt = rhsPredecessor.getTarget();
		Node succesorSrc = successor.getLhsEdge().getSource();
		Node succesorTgt = successor.getLhsEdge().getTarget();

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

	protected boolean isCreateDeleteDependency(
			Edge rhsPredecessorEdge,
			Edge successorEdge,
			PotentialRuleDependencies potRuleDep) {

		assert (isCreationEdge(rhsPredecessorEdge)) : "Input Assertion Failed!";

		if (rhsPredecessorEdge.getType() != successorEdge.getType()) {
			return false;
		}

		Node predecessorSrc = rhsPredecessorEdge.getSource();
		Node predecessorTgt = rhsPredecessorEdge.getTarget();
		Node succesorSrc = successorEdge.getSource();
		Node succesorTgt = successorEdge.getTarget();

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
	 * @param rhsPredecessors
	 *            Edges on RHS only (<< create >>).
	 * @param requireSuccessors
	 *            Edges from PACs (<< require >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findCreateUses_Edge_PAC(
			List<Edge> rhsPredecessors,
			List<Edge> requireSuccessors,
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

			for (Edge rhsPredecessorEdge : rhsPredecessors) {
				if (isCreateUseDependency_PAC(rhsPredecessorEdge, successorEdge, potRuleDep)) {
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
	 * @param rhsPredecessor
	 *            Edge is on RHS only (<< create >>).
	 * @param requireSuccessor
	 *            Edge is in PAC (<< require >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isCreateUseDependency_PAC(Edge rhsPredecessor, Edge requireSuccessor, 
			PotentialRuleDependencies potRuleDep) {
		
		assert (isCreationEdge(rhsPredecessor)) : "Input Assertion Failed!";
		assert (isRequireEdge(requireSuccessor)) : "Input Assertion Failed!";

		if (rhsPredecessor.getType() != requireSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = rhsPredecessor.getSource();
		Node predecessorTgt = rhsPredecessor.getTarget();
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
	protected Set<PotentialEdgeDependency> findForbidCreates_Edge(
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
	 * @param rhsSuccessor
	 *            Edge is on RHS only (<< create >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isForbidCreateDependency(
			Edge forbidPredecessors,
			Edge rhsSuccessor,
			PotentialRuleDependencies potRuleDep) {
		
		assert (isForbiddenEdge(forbidPredecessors)) : "Input Assertion Failed!";
		assert (isCreationEdge(rhsSuccessor)) : "Input Assertion Failed!";

		// Edge types are equal?
		if (forbidPredecessors.getType() != rhsSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = forbidPredecessors.getSource();
		Node predecessorTgt = forbidPredecessors.getTarget();
		Node succesorSrc = rhsSuccessor.getSource();
		Node succesorTgt = rhsSuccessor.getTarget();

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

	/**
	 * Checks all edges for Delete-Forbid dependencies.
	 * 
	 * @param lhsPredecessors
	 *            Edges on LHS only (<< delete >>).
	 * @param forbidSuccessors
	 *            Edges from NACs (<< forbid >>).
	 * @param potRuleDep
	 *            List of potential node dependencies. Contains at least all
	 *            potential node dependencies of the predecessor and successor
	 *            source and target nodes.
	 * @return All potential dependencies.
	 */
	protected Set<PotentialEdgeDependency> findDeleteForbids_Edge(
			Collection<Edge> lhsPredecessors,
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

			for (Edge predecessorEdge : lhsPredecessors) {
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
	 * @param lhsPredecessor
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
			Edge lhsPredecessor,
			Edge forbidSuccessor,
			PotentialRuleDependencies potRuleDep) {
		
		assert (isDeletionEdge(lhsPredecessor)) : "Input Assertion Failed!";
		assert (isForbiddenEdge(forbidSuccessor)) : "Input Assertion Failed!";

		// Edge types are equal?
		if (lhsPredecessor.getType() != forbidSuccessor.getType()) {
			return false;
		}

		Node predecessorSrc = lhsPredecessor.getSource();
		Node predecessorTgt = lhsPredecessor.getTarget();
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

	/*
	 * Attributes
	 */

	/**
	 * Checks all attributes for Change-Use dependencies.
	 * 
	 * @param rhsPredecessors
	 *            Attributes on RHS only (<< create >>).
	 * @param lhsSuccessors
	 *            Attributes on LHS (<< delete / preserve >>) or from PACs (<<require>>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialAttributeDependency> findChangeUses_Attribute(
			Collection<Attribute> rhsPredecessors, Collection<Attribute> lhsSuccessors) {

		// Calculate non-transients? 
		if (!nonTransientPDs) {
			return Collections.emptySet();
		}

		Set<PotentialAttributeDependency> potDeps = new HashSet<>();
		for (Attribute rhsPredecessorAttribute : rhsPredecessors) {
			for (Attribute lhsSuccessorAttribute : lhsSuccessors) {
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
	 * @param rhsPredecessor
	 *            Attribute is on RHS only (<< create >>).
	 * @param lhsSuccessor
	 *            Attribute is on LHS (<< delete / preserve >>) or in PAC (<<require>>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isChangeUseDependency(Attribute rhsPredecessor, Attribute lhsSuccessor) {
		
		assert (isCreationAttribute(rhsPredecessor)) : "Input Assertion Failed!";
		assert (isLHSAttribute(lhsSuccessor) || isRequireAttribute(lhsSuccessor)) : "Input Assertion Failed!";

		// Attributes have the same type
		if (rhsPredecessor.getType() == lhsSuccessor.getType()) {
			// Predecessor nodes is <<preserve>> ?
			if (isPreservedNode(rhsPredecessor.getNode())) {
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
	
	/**
	 * Checks all attributes for Use-Change dependencies.
	 * 
	 * @param lhsPredecessors
	 *            Attributes on LHS (<< delete / preserve >>) or from PACs (<<require>>).
	 * @param rhsSuccessors
	 *            Attributes on RHS only (<< create >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialAttributeDependency> findUseChanges_Attribute(
			Collection<Attribute> lhsPredecessors,
			Collection<Attribute> rhsSuccessors) {

		// Calculate transients? 
		if (!transientPDs) {
			return Collections.emptySet();
		}

		Set<PotentialAttributeDependency> potDeps = new HashSet<>();
		for (Attribute rhsPredecessorAttribute : lhsPredecessors) {
			for (Attribute lhsSuccessorAttribute : rhsSuccessors) {
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
	 * @param lhsPredecessor
	 *            Attribute is on LHS (<< delete / preserve >>) or in PAC (<<require>>).
	 * @param rhsSuccessor
	 *            Attribute is on RHS only (<< create >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isUseChangeDependency(Attribute lhsPredecessor, Attribute rhsSuccessor) {
		
		assert (isLHSAttribute(lhsPredecessor) || isRequireAttribute(lhsPredecessor)) : "Input Assertion Failed!";
		assert (isCreationAttribute(rhsSuccessor)) : "Input Assertion Failed!";

		// Attributes have the same type
		if (lhsPredecessor.getType().equals(rhsSuccessor.getType())){
			// Predecessor/Successor nodes is <<preserve>> ?
			if (isPreservedNode(lhsPredecessor.getNode()) && isPreservedNode(rhsSuccessor.getNode())) {
				// Attribute case differentiation precondition is fulfilled?
				if (attributeCaseDifferentiation(lhsPredecessor, rhsSuccessor)) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * Checks all attributes for Forbid-Change dependencies.
	 * 
	 * @param forbidPredecessors
	 *            Attributes from NACs (<< forbid >>).
	 * @param rhsSuccessors
	 *            Attributes on RHS only (<< create >>).
	 * @return All potential dependencies.
	 */
	protected Set<PotentialAttributeDependency> findForbidChanges_Attribute(
			List<Attribute> forbidPredecessors,
			List<Attribute> rhsSuccessors) {

		Set<PotentialAttributeDependency> potDeps = new HashSet<>();
		for (Attribute successorAttribute : rhsSuccessors) {
			
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
				if (isChangeForbidDependency(predecessorAttribute, successorAttribute)) {
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
	 * @param rhsSuccessor
	 *            Attribute is on RHS only (<< create >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isForbidChangeDependency(Attribute forbidPredecessor, Attribute rhsSuccessor) {
		
		assert (isForbiddenAttribute(forbidPredecessor)) : "Input Assertion Failed!";
		assert (isCreationAttribute(rhsSuccessor)) : "Input Assertion Failed!";
				
		// Attributes have the same type
		if (forbidPredecessor.getType() == rhsSuccessor.getType()){
			// Predecessor/Successor nodes is <<preserve>> ?
			if (isPreservedNode(rhsSuccessor.getNode())) {
				// Attribute case differentiation precondition is fulfilled?
				if (attributeCaseDifferentiation(forbidPredecessor, rhsSuccessor)) {
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
	protected Set<PotentialAttributeDependency> findChangeForbids_Attribute(
			List<Attribute> rhsPredecessors,
			List<Attribute> forbidSuccessors) {

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

			for (Attribute predecessorAttribute : rhsPredecessors) {
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
	 * @param rhsPredecessor
	 *            Attribute is on RHS only (<< create >>).
	 * @param forbidSuccessor
	 *            Attribute is a NAC (<< forbid >>).
	 * @return <code>true</code> if there  is a dependency; <code>false</code> otherwise.
	 */
	protected boolean isChangeForbidDependency(Attribute rhsPredecessor, Attribute forbidSuccessor) {

		assert (isCreationAttribute(rhsPredecessor)) : "Input Assertion Failed!";
		assert (isForbiddenAttribute(forbidSuccessor)) : "Input Assertion Failed!";

		// Attributes have the same type
		if (rhsPredecessor.getType() == forbidSuccessor.getType()){
			// Predecessor nodes is <<preserve>> ?
			if (isPreservedNode(rhsPredecessor.getNode())) {
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
