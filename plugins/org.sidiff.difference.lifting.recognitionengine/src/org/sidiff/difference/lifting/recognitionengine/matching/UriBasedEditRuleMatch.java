package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Field;
import org.sidiff.common.emf.access.Link;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionRuleMatch;
import org.sidiff.difference.symmetric.EditRuleMatch;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.project.runtime.library.IRuleBaseProject;

public class UriBasedEditRuleMatch extends BasicEditRuleMatch {

	/**
	 * Mapping: EditRuleAttribute -> EObject A
	 */
	private Map<Attribute, Set<Field>> attributeOccurrencesA = new HashMap<>();
	
	/**
	 * Mapping: editRuleAttribute -> EObject B
	 */
	private Map<Attribute, Set<Field>> attributeOccurrencesB = new HashMap<>();

	public UriBasedEditRuleMatch(SemanticChangeSet scs) {
		this(scs, EMFModelAccess.getDocumentTypes(Arrays.asList(
				((SymmetricDifference)scs.eContainer()).getModelA(),
				((SymmetricDifference)scs.eContainer()).getModelB())));
	}
	
	public UriBasedEditRuleMatch(SemanticChangeSet scs, Set<String> docTypes) {

		EditRule editRule =  IRuleBaseProject.MANAGER.resolveEditRule(docTypes, scs.getEditRName());
		
		assert (editRule != null) : "EditRule for SemanticChangeSet " + scs + " cannot be resolved!";
		assert (editRule.eResource() != null) : "editRule " + editRule + " is not contained in a resource!";

		Resource henshinResource = editRule.getExecuteMainUnit().eResource();		
		
		setEditRule(editRule);
		EditRuleMatch erMatch = scs.getEditRuleMatch();

		assert (erMatch != null) : "No EditRuleMatch has been seriaized for SemanticChangeSet " + scs + "!";

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
	private void deriveAttributeOccurrences() {
		for(Attribute a : getAllLHSAttributes()){
			Set<Field> attributeOccurences = new HashSet<>();
			for(EObject eObject : nodeOccurencesA.get(a.getNode())){
				createAttribute(attributeOccurences, eObject, a.getType());
			}
			if(!attributeOccurences.isEmpty()){
				attributeOccurrencesA.put(a, attributeOccurences);
			}
		}
		
		for(Attribute a: getAllRHSAttributes()){
			Set<Field> attributeOccurences = new HashSet<>();
			for(EObject eObject : nodeOccurencesB.get(HenshinRuleAnalysisUtilEx.findCorrespondingNodeInLHS(a.getNode()))) {
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
	protected void createAttribute(Set<Field> attributeOccurrences, EObject eObject, EAttribute eAttribute) {
		String stringValue =
			EMFUtil.getAttributeValues(eObject, eAttribute).stream()
				.map(value -> EcoreUtil.convertToString(eAttribute.getEAttributeType(), value))
				.collect(Collectors.joining(","));
		attributeOccurrences.add(new Field(eObject, eAttribute, stringValue));		
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Attribute> getMatchedAttributesA() {
		return Collections.unmodifiableSet(attributeOccurrencesA.keySet());
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Attribute> getMatchedAttributesB() {
		return Collections.unmodifiableSet(attributeOccurrencesB.keySet());
	}
	
	/**
	 * 
	 * @param editRuleAttribute
	 * @return
	 */
	public Set<Field> getOccurrenceA(Attribute editRuleAttribute) {
		Attribute keyAttribute = getKeyAttribute(editRuleAttribute);
		Set<Field> occurences = attributeOccurrencesA.get(keyAttribute);
		return occurences == null ? Collections.emptySet() : Collections.unmodifiableSet(occurences);
	}
	
	/**
	 * 
	 * @param editRuleAttribute
	 * @return
	 */
	public Set<Field> getOccurrenceB(Attribute editRuleAttribute) {
		Attribute keyAttribute = getKeyAttribute(editRuleAttribute);
		Set<Field> occurences = attributeOccurrencesB.get(keyAttribute);
		return occurences == null ? Collections.emptySet() : Collections.unmodifiableSet(occurences);
	}
	
	/**
	 * 
	 * @param editRuleAttribute
	 * @return
	 */
	public Attribute getKeyAttribute(Attribute editRuleAttribute) {
		Attribute keyAttribute = editRuleAttribute;
		
		// Schritt(1):
		Node editRuleNode = getKeyNode(keyAttribute.getNode());
		
		// Schritt(2):
		if(HenshinRuleAnalysisUtilEx.isRHSChangingAttribute(keyAttribute)||HenshinRuleAnalysisUtilEx.isChangingAttribute(keyAttribute)){
			if(HenshinRuleAnalysisUtilEx.isLHSAttribute(keyAttribute)){
				return HenshinRuleAnalysisUtilEx.getRemoteAttribute(keyAttribute);
			}
			return keyAttribute;
		}
		return editRuleNode.getAttribute(keyAttribute.getType());
	}
	
	/**
	 * 
	 * @return
	 */
	private Set<Attribute> getAllLHSAttributes() {
		Set<Attribute> attributes = new HashSet<>();
		for(Node n : nodeOccurencesA.keySet()){
			attributes.addAll(n.getAttributes());
		}
		return attributes;
	}
	
	/**
	 * 
	 * @return
	 */
	private Set<Attribute> getAllRHSAttributes() {
		Set<Attribute> attributes = new HashSet<>();
		for(Node n : nodeOccurencesB.keySet()){
			if(HenshinRuleAnalysisUtilEx.isLHSNode(n)){
				attributes.addAll(HenshinRuleAnalysisUtilEx.findCorrespondingNodeInRHS(n).getAttributes());
			}
		}
		return attributes;
	}

	@Override
	public Set<NacMatch> getNacOccurrences() {
		// TODO Implement this method!?
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<EObject> getForbidNodeOccurenceA(Node forbidNode) {
		// TODO Implement this method!?
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Link> getForbidEdgeOccurenceA(Edge forbidEdge) {
		// TODO Implement this method!?
		throw new UnsupportedOperationException();
	}

	@Override
	public IRecognitionRuleMatch getRecognitionRuleMatch() {
		// TODO Implement this method!?
		throw new UnsupportedOperationException();
	}
}
