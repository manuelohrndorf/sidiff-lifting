package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.emf.access.Field;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.difference.symmetric.EditRuleMatch;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.editrule.rulebase.EditRule;

public class UriBasedEditRuleMatch extends BasicEditRuleMatch {


	/**
	 * Mapping: EditRuleAttribute -> EObject A
	 */
	private Map<Attribute, Set<Field>> attributeOccurrencesA = new HashMap<Attribute, Set<Field>>();
	
	/**
	 * Mapping: editRuleAttribute -> EObject B
	 */
	private Map<Attribute, Set<Field>> attributeOccurrencesB = new HashMap<Attribute, Set<Field>>();
	
	public UriBasedEditRuleMatch(SemanticChangeSet scs) {
		EditRule editRule = scs.resolveEditRule();
		assert (editRule != null) : "EditRule for SemanticChangeSet " + scs + " cannot be resolved!";
		assert (editRule.eResource() != null) : "editRule " + editRule + " is not contained in a resource!";

		Resource henshinResource = editRule.getExecuteMainUnit().eResource();		
		
		setEditRule(editRule);
		EditRuleMatch erMatch = scs.getEditRuleMatch();

		assert (erMatch != null) : "No EditRuleMatch has been seriaized for  SemanticChangeSet " + scs + "!";

		// Resolve node occurrences in A
		for (String fragment : erMatch.getNodeOccurrencesA().keySet()) {
			Node node = (Node) henshinResource.getEObject(fragment);

			assert (node != null) : "Node with the URI fragment " + fragment + " cannot be found in resource "
					+ henshinResource + "!";

			nodeOccurencesA.put(node, erMatch.getNodeOccurrencesA().get(fragment).toJavaSet());
		}

		// Resolve node occurrences in B
		for (String fragment : erMatch.getNodeOccurrencesB().keySet()) {
			Node node = (Node) henshinResource.getEObject(fragment);

			assert (node != null) : "Node with the URI fragment " + fragment + " cannot be found in resource "
					+ henshinResource + "!";

			nodeOccurencesB.put(node, erMatch.getNodeOccurrencesB().get(fragment).toJavaSet());
		}

		// Derive the edge occurrences
		super.deriveEdgeOccurrences();
		
		deriveAttributeOccurrences();
	}
	
	/**
	 * 
	 */
	private void deriveAttributeOccurrences(){
		
		for(Attribute a : getAllLHSAttributes()){
			Set<Field> attributeOccurences = new HashSet<Field>();
			for(EObject eObject : nodeOccurencesA.get(a.getNode())){
				createAttribute(attributeOccurences, eObject, a.getType());
			}
			if(!attributeOccurences.isEmpty()){
				attributeOccurrencesA.put(a, attributeOccurences);
			}
		}
		
		for(Attribute a: getAllRHSAttributes()){
			Set<Field> attributeOccurences = new HashSet<Field>();
			for(EObject eObject : nodeOccurencesB.get(HenshinRuleAnalysisUtilEx.findCorrespondingNodeInLHS(a.getNode()))){
				createAttribute(attributeOccurences, eObject, a.getType());
			}
			if(!attributeOccurences.isEmpty()){
				attributeOccurrencesB.put(a, attributeOccurences);
			}
		}
	}
	
	/**
	 * 
	 * @param attributeOccurrences
	 * @param eObject
	 * @param eAttribute
	 */
	protected void createAttribute(Set<Field> attributeOccurrences, EObject eObject, EAttribute eAttribute){
		
		attributeOccurrences.add(new Field(eObject, eAttribute , eObject.eGet(eAttribute).toString()));		
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Attribute> getMatchedAttributesA(){
		return attributeOccurrencesA.keySet();
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Attribute> getMatchedAttributesB(){
		return attributeOccurrencesB.keySet();
	}
	
	/**
	 * 
	 * @param editRuleAttribute
	 * @return
	 */
	public Set<Field> getOccurrenceA(Attribute editRuleAttribute){
		Attribute keyAttribute = getKeyAttribute(editRuleAttribute);
		if(attributeOccurrencesA.get(keyAttribute) == null){
			return new HashSet<Field>();
		}else{
			return attributeOccurrencesA.get(keyAttribute);
		}
	}
	
	/**
	 * 
	 * @param editRuleAttribute
	 * @return
	 */
	public Set<Field> getOccurrenceB(Attribute editRuleAttribute){
		Attribute keyAttribute = getKeyAttribute(editRuleAttribute);
		if(attributeOccurrencesB.get(keyAttribute) == null){
			return new HashSet<Field>();
		}else{
			return attributeOccurrencesB.get(keyAttribute);
		}
	}
	
	/**
	 * 
	 * @param editRuleAttribute
	 * @return
	 */
	public Attribute getKeyAttribute(Attribute editRuleAttribute){
		Attribute keyAttribute = editRuleAttribute;
		
		// Schritt(1):
		Node editRuleNode = getKeyNode(keyAttribute.getNode());
		
		// Schritt(2):
		if(HenshinRuleAnalysisUtilEx.isRHSChangingAttribute(keyAttribute)||HenshinRuleAnalysisUtilEx.isChangingAttribute(keyAttribute)){
			if(HenshinRuleAnalysisUtilEx.isLHSAttribute(keyAttribute)){
				return HenshinRuleAnalysisUtilEx.getRemoteAttribute(keyAttribute);
			}else{
				return keyAttribute;
			}
		}else{
			return editRuleNode.getAttribute(keyAttribute.getType());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private Set<Attribute> getAllLHSAttributes(){
		Set<Attribute> attributes = new HashSet<Attribute>();
		for(Node n : nodeOccurencesA.keySet()){
			attributes.addAll(n.getAttributes());
		}
		return attributes;
	}
	
	/**
	 * 
	 * @return
	 */
	private Set<Attribute> getAllRHSAttributes(){
		Set<Attribute> attributes = new HashSet<Attribute>();
		for(Node n : nodeOccurencesB.keySet()){
			if(HenshinRuleAnalysisUtilEx.isLHSNode(n)){
				Node rhs_node = HenshinRuleAnalysisUtilEx.findCorrespondingNodeInRHS(n);
				attributes.addAll(rhs_node.getAttributes());
			}
		}
		return attributes;
	}
}
