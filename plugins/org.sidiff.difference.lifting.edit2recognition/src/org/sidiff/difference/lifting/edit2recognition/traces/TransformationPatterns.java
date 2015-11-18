package org.sidiff.difference.lifting.edit2recognition.traces;

import static org.sidiff.common.henshin.HenshinConditionAnalysis.isNestedConditionNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getLHS;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isLHSNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.view.NodePair;

/**
 * Storage and indexed access of the Edit- to Recognition-Rule transformation traces.
 * 
 * @author Manuel Ohrndorf
 */
public class TransformationPatterns {
	
	/**
	 * Index: LHS node of {@link CorrespondencePattern#trace} -> {@link CorrespondencePattern}
	 */
	private Map<Node, CorrespondencePattern> correspondencePatterns;
	
	/**
	 * Index: {@link AddObjectPattern#trace} -> {@link AddObjectPattern}
	 */
	private Map<Node, AddObjectPattern> addObjectPatterns;
	
	/**
	 * Index: {@link RemoveObjectPattern#trace} -> {@link RemoveObjectPattern}
	 */
	private Map<Node, RemoveObjectPattern> removeObjectPatterns;
	
	/**
	 * Index: {@link ACObjectPattern#acTrace} -> {@link ACObjectPattern}
	 */
	private Map<Node, ACObjectPattern> acObjectPatterns;
	
	/**
	 * Index: {@link AddReferencePattern#trace} -> {@link AddReferencePattern}
	 */
	private Map<Edge, AddReferencePattern> addReferencePatterns;
	
	/**
	 * Index: {@link RemoveReferencePattern#trace} -> {@link RemoveReferencePattern}
	 */
	private Map<Edge, RemoveReferencePattern> removeReferencePatterns;
	
	/**
	 * Index: {@link ACReferencePattern#acTrace} -> {@link ACReferencePattern}
	 */
	private Map<Edge, ACReferencePattern> acReferencePatterns;
	
	/**
	 * Index: {@link ACExtensionPattern#acBoundaryTrace} -> {@link ACExtensionPattern}
	 */
	private Map<Node, ACExtensionPattern> acExtensionPatterns_acBoundary;
	
	/**
	 * Index: LHS Node of {@link ACExtensionPattern#lhsBoundrayTrace} -> {@link ACExtensionPattern}
	 */
	private Map<Node, ACExtensionPattern> acExtensionPatterns_lhsBoundary;
	
	/**
	 * <p>
	 * Index: {@link ACBoundaryNodePattern#acBoundaryTrace} -> {@link ACBoundaryNodePattern}
	 * </p>
	 * <p>
	 * Including the AC-Extension-Pattern boundary traces: {@link ACBoundaryNodePattern} == (
	 * {@link ACExtensionPattern#acBoundaryTrace}, {@link ACExtensionPattern#acBoundaryNode})
	 * </p>
	 */
	private Map<Node, ACBoundaryNodePattern> acBoundaryNodePatterns;
	
	/**
	 * Index: {@link AttributeValueChangePattern#trace} (RHS) -> {@link AttributeValueChangePattern}
	 */
	private Map<Attribute, AttributeValueChangePattern> attributeValueChangePatterns;
	
	/**
	 * Initializes a new transformation pattern storage.
	 */
	public TransformationPatterns() {
		correspondencePatterns = new HashMap<Node, CorrespondencePattern>();
		addObjectPatterns = new HashMap<Node, AddObjectPattern>();
		removeObjectPatterns = new HashMap<Node, RemoveObjectPattern>();
		acObjectPatterns = new HashMap<Node, ACObjectPattern>();
		addReferencePatterns = new HashMap<Edge, AddReferencePattern>();
		removeReferencePatterns = new HashMap<Edge, RemoveReferencePattern>();
		acReferencePatterns = new HashMap<Edge, ACReferencePattern>();
		acExtensionPatterns_acBoundary = new HashMap<Node, ACExtensionPattern>();
		acExtensionPatterns_lhsBoundary = new HashMap<Node, ACExtensionPattern>();
		acBoundaryNodePatterns = new HashMap<Node, ACBoundaryNodePattern>();
		attributeValueChangePatterns = new HashMap<Attribute, AttributeValueChangePattern>();
	}
	
	/*
	 * Add patterns:
	 */
	
	/**
	 * Add a new {@link CorrespondencePattern}.
	 * 
	 * @param pattern
	 *            The new {@link CorrespondencePattern}.
	 */
	public void addCorrespondencePattern(CorrespondencePattern pattern) {
		correspondencePatterns.put(pattern.getTrace().getLhsNode(), pattern);
	}
	
	/**
	 * Add a new {@link AddObjectPattern}.
	 * 
	 * @param pattern
	 *            The new {@link AddObjectPattern}.
	 */
	public void addAddObjectPattern(AddObjectPattern pattern) {
		addObjectPatterns.put(pattern.getTrace(), pattern);
	}
	
	/**
	 * Add a new {@link RemoveObjectPattern}.
	 * 
	 * @param pattern
	 *            The new {@link RemoveObjectPattern}.
	 */
	public void addRemoveObjectPattern(RemoveObjectPattern pattern) {
		removeObjectPatterns.put(pattern.getTrace(), pattern);
	}
	
	/**
	 * Add a new {@link ACObjectPattern}.
	 * 
	 * @param pattern
	 *            The new {@link ACObjectPattern}.
	 */
	public void addACObjectPattern(ACObjectPattern pattern) {
		acObjectPatterns.put(pattern.getACTrace(), pattern);
	}
	
	/**
	 * Add a new {@link AddReferencePattern}.
	 * 
	 * @param pattern
	 *            The new {@link AddReferencePattern}.
	 */
	public void addAddReferencePattern(AddReferencePattern pattern) {
		addReferencePatterns.put(pattern.getTrace(), pattern);
	}
	
	/**
	 * Add a new {@link RemoveReferencePattern}.
	 * 
	 * @param pattern
	 *            The new {@link RemoveReferencePattern}.
	 */
	public void addRemoveReferencePattern(RemoveReferencePattern pattern) {
		removeReferencePatterns.put(pattern.getTrace(), pattern);
	}
	
	/**
	 * Add a new {@link ACReferencePattern}.
	 * 
	 * @param pattern
	 *            The new {@link ACReferencePattern}.
	 */
	public void addACRreferencePatter(ACReferencePattern pattern) {
		acReferencePatterns.put(pattern.getACTrace(), pattern);
	}
	
	/**
	 * Add a new {@link ACExtensionPattern}.
	 * 
	 * @param pattern
	 *            The new {@link ACExtensionPattern}.
	 */
	public void addACExtension(ACExtensionPattern pattern) {
		acExtensionPatterns_lhsBoundary.put(pattern.getLHSBoundrayTrace(), pattern);
		acExtensionPatterns_acBoundary.put(pattern.getACBoundaryTrace(), pattern);
	}
	
	/**
	 * Add a new {@link ACBoundaryNodePattern}.
	 * 
	 * @param pattern
	 *            The new {@link ACBoundaryNodePattern}.
	 */
	public void addACBoundaryNodePattern(ACBoundaryNodePattern pattern) {
		acBoundaryNodePatterns.put(pattern.getACBoundaryTrace(), pattern);
	}
	
	/**
	 * Add a new {@link AttributeValueChangePattern}.
	 * 
	 * @param pattern
	 *            The new {@link AttributeValueChangePattern}.
	 */
	public void addAttributeValueChangePattern(AttributeValueChangePattern pattern) {
		attributeValueChangePatterns.put(pattern.getTrace(), pattern);
	}
	
	/*
	 * Get traces:
	 */
	
	/**
	 * Searches the Recognition-Rule model A node, representing the given Edit-Rule node. The
	 * Edit-Rule node is either a << preserve >> node (LHS or RHS) or a << delete >> node (LHS).
	 * 
	 * @param editRuleNode
	 *            The traced Edit-Rule (LHS or RHS) node.
	 * @return The Recognition-Rule model A node, representing the given Edit-Rule node.
	 */
	public NodePair getTraceA(Node editRuleNode) {
		CorrespondencePattern correspondencePattern = getCorrespondecePattern(editRuleNode);
		
		if (correspondencePattern != null) {
			return correspondencePattern.getNodeA();
		}
		
		RemoveObjectPattern removeObjectPattern = getRemoveObjectPattern(editRuleNode);
		
		if (removeObjectPattern != null) {
			return removeObjectPattern.getNodeA();
		}
		
		return null;
	}
	
	/**
	 * Searches the Recognition-Rule model B node, representing the given Edit-Rule node. The
	 * Edit-Rule node is either a << preserve >> node (LHS or RHS) or a << delete >> node (LHS).
	 * 
	 * @param editRuleNode
	 *            The traced Edit-Rule (LHS or RHS) node.
	 * @return The Recognition-Rule model B node, representing the given Edit-Rule node.
	 */
	public NodePair getTraceB(Node editRuleNode) {
		CorrespondencePattern correspondencePattern = getCorrespondecePattern(editRuleNode);
		
		if (correspondencePattern != null) {
			return correspondencePattern.getNodeB();
		}
		
		AddObjectPattern addObjectPattern = getAddObjectPattern(editRuleNode);
		
		if (addObjectPattern != null) {
			return addObjectPattern.getNodeB();
		}
		
		return null;
	}
	
	/**
	 * Searches the Recognition-Rule application condition (AC) node, representing the given
	 * Edit-Rule (AC) node.
	 * 
	 * @param editRuleACEdge
	 *            The traced Edit-Rule (AC) node.
	 * @return The Recognition-Rule application condition (AC) node, representing the given
	 *         Edit-Rule (AC) node.
	 */
	public Node getBoundaryTrace(Node editRuleACNode) {
		assert isNestedConditionNode(editRuleACNode) : "Not a nested condition node!";
		
		ACBoundaryNodePattern acBoundaryNodePattern = acBoundaryNodePatterns.get(editRuleACNode);
		
		if (acBoundaryNodePattern != null) {
			return acBoundaryNodePattern.getACBoundaryNode();
		}
		
		return null;
	}
	
	/*
	 * Get patterns:
	 */
	
	/**
	 * Searches the Recognition-Rule {@link CorrespondencePattern} caused by the given Edit-Rule
	 * (LHS or RHS) node.
	 * 
	 * @param editRuleNode
	 *            The traced Edit-Rule (LHS or RHS) node.
	 * @return The generated {@link CorrespondencePattern}.
	 */
	public CorrespondencePattern getCorrespondecePattern(Node editRuleNode) {
		if (isLHSNode(editRuleNode)) {
			return correspondencePatterns.get(editRuleNode);
		} else {
			return correspondencePatterns.get(getLHS(editRuleNode));
		}
	}
	
	/**
	 * Searches the Recognition-Rule {@link AddObjectPattern} caused by the given Edit-Rule (RHS)
	 * node.
	 * 
	 * @param editRuleNode
	 *            The traced Edit-Rule (RHS) node.
	 * @return The generated {@link AddObjectPattern}.
	 */
	public AddObjectPattern getAddObjectPattern(Node editRuleNode) {
		return addObjectPatterns.get(editRuleNode);
	}
	
	/**
	 * Searches the Recognition-Rule {@link RemoveObjectPattern} caused by the given Edit-Rule (LHS)
	 * node.
	 * 
	 * @param editRuleNode
	 *            The traced Edit-Rule (LHS) node.
	 * @return The generated {@link RemoveObjectPattern}.
	 */
	public RemoveObjectPattern getRemoveObjectPattern(Node editRuleNode) {
		return removeObjectPatterns.get(editRuleNode);
	}
	
	/**
	 * Searches the Recognition-Rule {@link ACExtensionPattern} caused by the given Edit-Rule (AC)
	 * node.
	 * 
	 * @param editRuleACNode
	 *            The traced Edit-Rule (AC) node.
	 * @return The generated {@link ACExtensionPattern}.
	 */
	public ACExtensionPattern getACExtensionPattern(Node editRuleACNode) {
		
		if (isNestedConditionNode(editRuleACNode)) {
			return acExtensionPatterns_acBoundary.get(editRuleACNode);
		} else {
			if (isLHSNode(editRuleACNode)) {
				return acExtensionPatterns_lhsBoundary.get(editRuleACNode);
			} else {
				return acExtensionPatterns_lhsBoundary.get(getLHS(editRuleACNode));
			}
		}
	}
	
	/**
	 * Searches the Recognition-Rule {@link ACBoundaryNodePattern} caused by the given Edit-Rule
	 * (AC) node.
	 * 
	 * @param editRuleACEdge
	 *            The traced Edit-Rule (AC) node.
	 * @return The generated {@link ACBoundaryNodePattern}.
	 */
	public ACBoundaryNodePattern getACBoundaryNodePattern(Node editRuleACNode) {
		return acBoundaryNodePatterns.get(editRuleACNode);
	}
	
	/**
	 * Searches the Recognition-Rule {@link ACObjectPattern} caused by the given Edit-Rule (AC)
	 * node.
	 * 
	 * @param editRuleACNode
	 *            The traced Edit-Rule (AC) node.
	 * @return The generated {@link ACObjectPattern}.
	 */
	public ACObjectPattern getACObjectPattern(Node editRuleACNode) {
		return acObjectPatterns.get(editRuleACNode);
	}
	
	/**
	 * Searches the Recognition-Rule {@link AddReferencePattern} caused by the given Edit-Rule (RHS)
	 * edge.
	 * 
	 * @param editRuleNode
	 *            The traced Edit-Rule (RHS) edge.
	 * @return The generated {@link AddReferencePattern}.
	 */
	public AddReferencePattern getAddReferencePattern(Edge editRuleEdge) {
		return addReferencePatterns.get(editRuleEdge);
	}
	
	/**
	 * Searches the Recognition-Rule {@link RemoveReferencePattern} caused by the given Edit-Rule
	 * (LHS) edge.
	 * 
	 * @param editRuleNode
	 *            The traced Edit-Rule (LHS) edge.
	 * @return The generated {@link RemoveReferencePattern}.
	 */
	public RemoveReferencePattern getRemoveReferencePattern(Edge editRuleEdge) {
		return removeReferencePatterns.get(editRuleEdge);
	}
	
	/**
	 * Searches the Recognition-Rule {@link ACReferencePattern} caused by the given Edit-Rule (AC)
	 * edge.
	 * 
	 * @param editRuleACEdge
	 *            The traced Edit-Rule (AC) edge.
	 * @return The generated {@link ACReferencePattern}.
	 */
	public ACReferencePattern getACReferencePattern(Edge editRuleACEdge) {
		return acReferencePatterns.get(editRuleACEdge);
	}
	
	/**
	 * Searches the Recognition-Rule {@link AttributeValueChangePattern} caused by the given
	 * Edit-Rule (RHS) attribute.
	 * 
	 * @param editRuleNode
	 *            The traced Edit-Rule (RHS) attribute.
	 * @return The generated {@link AttributeValueChangePattern}.
	 */
	public AttributeValueChangePattern getAttributeValueChangePattern(Attribute editRuleAttribute) {
		return attributeValueChangePatterns.get(editRuleAttribute);
	}
	
	/*
	 * Get lists of patterns:
	 */
	
	/**
	 * @return All stored {@link CorrespondencePattern}.
	 */
	public Collection<CorrespondencePattern> getCorrespondencePatterns() {
		return correspondencePatterns.values();
	}
	
	/**
	 * @return All stored {@link AddObjectPattern}.
	 */
	public Collection<AddObjectPattern> getAddObjectPatterns() {
		return addObjectPatterns.values();
	}
	
	/**
	 * @return All stored {@link RemoveObjectPattern}.
	 */
	public Collection<RemoveObjectPattern> getRemoveObjectPatterns() {
		return removeObjectPatterns.values();
	}
	
	/**
	 * @return All stored {@link ACObjectPattern}.
	 */
	public Collection<ACObjectPattern> getACObjectPatterns() {
		return acObjectPatterns.values();
	}
	
	/**
	 * @return All stored {@link AddReferencePattern}.
	 */
	public Collection<AddReferencePattern> getAddReferencePatterns() {
		return addReferencePatterns.values();
	}
	
	/**
	 * @return All stored {@link RemoveReferencePattern}.
	 */
	public Collection<RemoveReferencePattern> getRemoveReferencePatterns() {
		return removeReferencePatterns.values();
	}
	
	/**
	 * @return All stored {@link ACReferencePattern}.
	 */
	public Collection<ACReferencePattern> getACReferencePatterns() {
		return acReferencePatterns.values();
	}
	
	/**
	 * @return All stored {@link ACExtensionPattern}.
	 */
	public Collection<ACExtensionPattern> getACExtensionPatterns() {
		return acExtensionPatterns_acBoundary.values();
	}
	
	/**
	 * @return All stored {@link ACBoundaryNodePattern}.
	 */
	public Collection<ACBoundaryNodePattern> getACBoundaryNodePatterns() {
		return acBoundaryNodePatterns.values();
	}
	
	/**
	 * @return All stored {@link AttributeValueChangePattern}.
	 */
	public Collection<AttributeValueChangePattern> getAttributeValueChangePatterns() {
		return attributeValueChangePatterns.values();
	}
}
