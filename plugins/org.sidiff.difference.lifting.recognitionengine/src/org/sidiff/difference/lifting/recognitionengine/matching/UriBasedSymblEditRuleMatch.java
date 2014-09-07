package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.emf.access.Field;
import org.sidiff.common.emf.access.Link;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.silift.difference.symboliclink.SymbolicLinkAttribute;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkReference;

/**
 * 
 * @author cpietsch
 *
 */
public class UriBasedSymblEditRuleMatch extends UriBasedEditRuleMatch {

	/**
	 * Mapping: EditRuleAttribute -> EObject A
	 */
	private Map<Attribute, Set<Field>> attributeOccurrencesA = new HashMap<Attribute, Set<Field>>();
	
	/**
	 * Mapping: editRuleAttribute -> EObject B
	 */
	private Map<Attribute, Set<Field>> attributeOccurrencesB = new HashMap<Attribute, Set<Field>>();
	
	
	
	public UriBasedSymblEditRuleMatch(SemanticChangeSet scs) {
		super(scs);
		deriveAttributeOccurrences();
	}
	
	@Override
	protected  void createLink(Set<Link> edgeOccurrences, EObject[] tuple, EReference reference ){
		// FIXME (cpietsch: 02.09.2014) getNodeNeighbors doesn't work with symbolic links 
		// (see also org.sidiff.common.emf.access.Link)
		if (tuple[0] instanceof SymbolicLinkObject) {
			SymbolicLinkObject symbloSrc = (SymbolicLinkObject) tuple[0];
			for (SymbolicLinkReference symblRef : symbloSrc
					.getOutgoings(reference)) {
				if (symblRef.getTarget().equals(tuple[1])) {
					edgeOccurrences.add(new Link(tuple[0], tuple[1], reference));
				}
			}
		}
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
	private void createAttribute(Set<Field> attributeOccurrences, EObject eObject, EAttribute eAttribute){
		
		if(eObject instanceof SymbolicLinkObject){
			for(SymbolicLinkAttribute symblA : ((SymbolicLinkObject)eObject).getLinkAttributes()){
				if(symblA.getKind().equals(eAttribute)){
					attributeOccurrences.add(new Field(symblA.eContainer(), symblA.getKind() , symblA.getValue()));
				}
			}
		}
		
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
		if(HenshinRuleAnalysisUtilEx.isRHSChangedAttribute(keyAttribute)||HenshinRuleAnalysisUtilEx.isChangedAttribute(keyAttribute)){
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
			Node rhs_node = HenshinRuleAnalysisUtilEx.findCorrespondingNodeInRHS(n);
			attributes.addAll(rhs_node.getAttributes());
		}
		return attributes;
	}

}
