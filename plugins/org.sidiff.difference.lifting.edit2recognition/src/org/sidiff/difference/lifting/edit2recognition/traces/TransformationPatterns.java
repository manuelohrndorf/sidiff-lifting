package org.sidiff.difference.lifting.edit2recognition.traces;

import static org.sidiff.common.henshin.HenshinConditionAnalysis.isNestedConditionNode;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isLHSNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.NodePair;

public class TransformationPatterns {

	private Map<Node, CorrespondencePattern> correspondencePatterns; // LHS Node
	private Map<Node, AddObjectPattern> addObjectPatterns;
	private Map<Node, RemoveObjectPattern> removeObjectPatterns;
	private Map<Node, ACObjectPattern> acObjectPatterns;
	private Map<Edge, AddReferencePattern> addReferencePatterns;
	private Map<Edge, RemoveReferencePattern> removeReferencePatterns;
	private Map<Edge, ACReferencePattern> acReferencePatterns;
	private Map<Node, ACExtensionPattern> acExtensionPatterns_acContext;
	private Map<Node, ACExtensionPattern> acExtensionPatterns_lhsContext; // LHS Node
	private Map<Node, ACContextNodePattern> acContextNodePatterns; // Including the AC-Extension traces
	private Map<Attribute, AttributeValueChangePattern> attributeValueChangePatterns;
	
	public TransformationPatterns() {
		correspondencePatterns = new HashMap<Node, CorrespondencePattern>();
		addObjectPatterns = new HashMap<Node, AddObjectPattern>();
		removeObjectPatterns = new HashMap<Node, RemoveObjectPattern>();
		acObjectPatterns = new HashMap<Node, ACObjectPattern>();
		addReferencePatterns = new HashMap<Edge, AddReferencePattern>();
		removeReferencePatterns = new HashMap<Edge, RemoveReferencePattern>();
		acReferencePatterns = new HashMap<Edge, ACReferencePattern>();
		acExtensionPatterns_acContext = new HashMap<Node, ACExtensionPattern>();
		acExtensionPatterns_lhsContext = new HashMap<Node, ACExtensionPattern>();
		acContextNodePatterns = new HashMap<Node, ACContextNodePattern>();
		attributeValueChangePatterns = new HashMap<Attribute, AttributeValueChangePattern>();
	}
	
	/*
	 * Add Patterns
	 */

	public void addCorrespondencePattern(CorrespondencePattern pattern) {
		correspondencePatterns.put(pattern.getTrace().getLhsNode(), pattern);
	}
	
	public void addAddObjectPattern(AddObjectPattern pattern) {
		addObjectPatterns.put(pattern.getTrace(), pattern);
	}
	
	public void addRemoveObjectPattern(RemoveObjectPattern pattern) {
		removeObjectPatterns.put(pattern.getTrace(), pattern);
	}
	
	public void addACObjectPattern(ACObjectPattern pattern) {
		acObjectPatterns.put(pattern.getTrace(), pattern);
	}
	
	public void addAddReferencePattern(AddReferencePattern pattern) {
		addReferencePatterns.put(pattern.getTrace(), pattern);
	}
	
	public void addRemoveReferencePattern(RemoveReferencePattern pattern) {
		removeReferencePatterns.put(pattern.getTrace(), pattern);
	}
	
	public void addACRreferencePatter(ACReferencePattern pattern) {
		acReferencePatterns.put(pattern.getTrace(), pattern);
	}
	
	public void addACExtension(ACExtensionPattern pattern) {
		acExtensionPatterns_lhsContext.put(pattern.getContext_trace(), pattern);
		acExtensionPatterns_acContext.put(pattern.getAc_context_trace(), pattern);
	}
	
	public void addACContextNodePattern(ACContextNodePattern pattern) {
		acContextNodePatterns.put(pattern.getAcContextTrace(), pattern);
	}
	
	public void addAttributeValueChangePattern(AttributeValueChangePattern pattern) {
		attributeValueChangePatterns.put(pattern.getTrace(), pattern);
	}
	
	/*
	 * Get trace
	 */
	
	public NodePair getTraceA(Node editRuleNode) {
		CorrespondencePattern correspondencePattern = getCorrespondecePattern(editRuleNode);
		if (correspondencePattern != null) return correspondencePattern.getNode_a();
		
		RemoveObjectPattern removeObjectPattern = getRemoveObjectPattern(editRuleNode);
		if (removeObjectPattern != null) return removeObjectPattern.getNode_a();
		
		return null;
	}
	
	public NodePair getTraceB(Node editRuleNode) {
		CorrespondencePattern correspondencePattern = getCorrespondecePattern(editRuleNode);
		if (correspondencePattern != null) return correspondencePattern.getNode_b();
		
		AddObjectPattern addObjectPattern = getAddObjectPattern(editRuleNode);
		if (addObjectPattern != null) return addObjectPattern.getNode_b();
		
		return null;
	}
	
	public Node getContextTrace(Node editRuleACNode) {
		assert isNestedConditionNode(editRuleACNode) : "Not a nested condition node!";
		
		ACContextNodePattern acContextNodePattern = acContextNodePatterns.get(editRuleACNode);
		if (acContextNodePattern != null) return acContextNodePattern.getAcContextNode();
		
		return null;
	}
	
	public CorrespondencePattern getCorrespondecePattern(Node editRuleNode) {
		if(isLHSNode(editRuleNode)) {
			return correspondencePatterns.get(editRuleNode);
		} else {
			return correspondencePatterns.get(getLHS(editRuleNode));
		}
	}

	public AddObjectPattern getAddObjectPattern(Node editRuleNode) {
		return addObjectPatterns.get(editRuleNode);
	}
	
	public RemoveObjectPattern getRemoveObjectPattern(Node editRuleNode) {
		return removeObjectPatterns.get(editRuleNode);
	}
	
	public ACObjectPattern getACObjectPattern(Node editRuleNode) {
		return acObjectPatterns.get(editRuleNode);
	}
	
	public AddReferencePattern getAddReferencePattern(Edge editRuleEdge) {
		return addReferencePatterns.get(editRuleEdge);
	}
	
	public RemoveReferencePattern getRemoveReferencePattern(Edge editRuleEdge) {
		return removeReferencePatterns.get(editRuleEdge);
	} 
	
	public ACReferencePattern getACReferencePattern(Edge editRuleEdge) {
		return acReferencePatterns.get(editRuleEdge);
	}
	
	public ACExtensionPattern getACExtensionPattern(Node editRuleNode) {
		
		if (isNestedConditionNode(editRuleNode)) {
			return acExtensionPatterns_acContext.get(editRuleNode);
		} else {
			if (isLHSNode(editRuleNode)) {
				return acExtensionPatterns_lhsContext.get(editRuleNode);
			} else {
				return acExtensionPatterns_lhsContext.get(getLHS(editRuleNode));
			}
		}
	}
	
	public ACContextNodePattern getACContextNodePattern(Node editRuleACNode) {
		return acContextNodePatterns.get(editRuleACNode);
	}

	public AttributeValueChangePattern getAttributeValueChangePattern(Attribute editRuleAttribute) {
		return attributeValueChangePatterns.get(editRuleAttribute);
	}
	
	/*
	 * Get list of patterns
	 */

	public Collection<CorrespondencePattern> getCorrespondencePatterns() {
		return correspondencePatterns.values();
	}

	public Collection<AddObjectPattern> getAddObjectPatterns() {
		return addObjectPatterns.values();
	}

	public Collection<RemoveObjectPattern> getRemoveObjectPatterns() {
		return removeObjectPatterns.values();
	}
	
	public Collection<ACObjectPattern> getACObjectPatterns() {
		return acObjectPatterns.values();
	}
	public Collection<AddReferencePattern> getAddReferencePatterns() {
		return addReferencePatterns.values();
	}

	public Collection<RemoveReferencePattern> getRemoveReferencePatterns() {
		return removeReferencePatterns.values();
	}
	
	public Collection<ACReferencePattern> getACReferencePatterns() {
		return acReferencePatterns.values();
	}
	
	public Collection<ACExtensionPattern> getACExtensionPatterns() {
		return acExtensionPatterns_acContext.values();
	}
	
	public Collection<ACContextNodePattern> getACContextNodePatterns() {
		return acContextNodePatterns.values();
	}

	public Collection<AttributeValueChangePattern> getAttributeValueChangePatterns() {
		return attributeValueChangePatterns.values();
	}
	
	/*
	 * Utils
	 */
	
	private Object getLHS(Node rhsNode) {
		return HenshinRuleAnalysisUtilEx.getRemoteNode(
				rhsNode.getGraph().getRule().getMappings(), rhsNode);
	}
}
