package org.sidiff.slicer.slice.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AttributeDependency;
import org.sidiff.difference.asymmetric.Dependency;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.EdgeDependency;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.NodeDependency;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.EObjectSet;
import org.sidiff.difference.symmetric.EditRuleMatch;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.entities.Annotation;
import org.sidiff.entities.Attribute;
import org.sidiff.entities.Element;
import org.sidiff.entities.Entity;
import org.sidiff.entities.Reference;
import org.sidiff.entities.util.EntitiesImporter;
import org.sidiff.entities.util.ImportFailedException;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.Matching;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.slice.SliceFactory;
import org.sidiff.slicer.slice.SlicedElement;

/**
 * 
 * @author cpietsch
 *
 */
public class AsymmetricDifferenceImporter extends EntitiesImporter {
	
	private SliceFactory factory;
	
	private ModelSlice modelSlice;
	
	private Annotation annotationOrigin;
	
	private Annotation annotationChanged;
	
	private Set<EObject> mandatoryEObjects;
	
	//TODO solution for derived EGenerics objects
	private Set<EClass> unconsideredNodeTypes;
	private Set<EReference> unconsideredEdgeTypes;
	private Set<EAttribute> unconsideredAttributeTypes;

	public AsymmetricDifferenceImporter() {
		this.unconsideredNodeTypes = new HashSet<EClass>();
		// add generics
		this.unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getETypeParameter());
		this.unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getEGenericType());

		this.unconsideredEdgeTypes = new HashSet<EReference>();
		// add generics
		this.unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEClassifier_ETypeParameters());
		this.unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEOperation_ETypeParameters());
		this.unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEOperation_EGenericExceptions());
		this.unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEClass_EGenericSuperTypes());
		this.unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getETypedElement_EGenericType());

		this.unconsideredAttributeTypes = new HashSet<EAttribute>();
	}
		
	/**
	 * 
	 * @param modelSlice
	 * @param signatureCalculator
	 */
	public void init(ModelSlice modelSlice) {
		this.modelSlice = modelSlice;
		this.uuidIndex = new HashMap<String, Element>();
		this.signatureIndex = new HashMap<String, Set<Entity>>();
		this.eObject2Element = new HashMap<EObject, Element>();
		for (SlicedElement element : this.modelSlice.getSlicedElements()) {
			this.uuidIndex.put(element.getUuid(), element);
			if (this.signatureIndex.get(element.getSignature()) == null) {
				this.signatureIndex.put(element.getSignature(), new HashSet<Entity>());
			}
			this.signatureIndex.get(element.getSignature()).add(element);
		}
	}
	
	/**
	 * 
	 * @param asymmetricDifference
	 * @param eObject2element
	 * @throws ImportFailedException
	 */
	public void importAsymmetricDifference(AsymmetricDifference asymmetricDifference, String name, Map<EObject, SlicedElement> eObject2element) throws ImportFailedException{
						
		this.eObject2Element.putAll(eObject2element);
		
		mandatoryEObjects = new HashSet<EObject>();
		for(SemanticChangeSet scs : asymmetricDifference.getSymmetricDifference().getChangeSets()) {
			EditRuleMatch erMatch = scs.getEditRuleMatch();
			for(EObjectSet eObjectSet : erMatch.getNodeOccurrencesA().values()) {
					mandatoryEObjects.addAll(eObjectSet.getElements());
			}
			for(EObjectSet eObjectSet : erMatch.getNodeOccurrencesB().values()) {
				mandatoryEObjects.addAll(eObjectSet.getElements());
			}
		}
		
		importSymmetricDifference(asymmetricDifference.getSymmetricDifference());
		
		for(OperationInvocation opInv : asymmetricDifference.getOperationInvocations()){
			importOperationInvocation(opInv);
		}
		
		for(DependencyContainer depCon : asymmetricDifference.getDepContainers()){
			importDependencyContainer(depCon);
		}
	}
	
	/**
	 * 
	 * @param operationInvocation
	 * @throws ImportFailedException 
	 */
	private void importOperationInvocation(OperationInvocation operationInvocation) throws ImportFailedException{
		for(ParameterBinding binding : operationInvocation.getParameterBindings()){
			
			if(binding instanceof ObjectParameterBinding){
				
				ObjectParameterBinding opb = (ObjectParameterBinding) binding;
				importObjectParameterBinding(opb);
				
			}else if(binding instanceof MultiParameterBinding){
				
				MultiParameterBinding mpb = (MultiParameterBinding) binding;
				
				for(ParameterBinding binding_of_mpb : mpb.getParameterBindings()){
					
					if(binding_of_mpb instanceof ObjectParameterBinding){
						
						ObjectParameterBinding opb = (ObjectParameterBinding) binding_of_mpb;
						importObjectParameterBinding(opb);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param objectParameterBinding
	 * @throws ImportFailedException 
	 */
	private void importObjectParameterBinding(ObjectParameterBinding objectParameterBinding) throws ImportFailedException{
		
		if(objectParameterBinding.getActualA()!= null){
			Element elementA = importEObject(objectParameterBinding.getActualA());
			objectParameterBinding.setActualA(elementA);
		}
		
		if(objectParameterBinding.getActualB()!=null){
			Element elementB= importEObject(objectParameterBinding.getActualB());
			objectParameterBinding.setActualB(elementB);
		}
	}
	
	/**
	 * 
	 * @param dependencyContainer
	 * @throws ImportFailedException
	 */
	private void importDependencyContainer(DependencyContainer dependencyContainer) throws ImportFailedException{
		for(Dependency dependency : dependencyContainer.getDependencies()){
			
			// NodeDependency
			if(dependency instanceof NodeDependency){
				
				NodeDependency nodeDependency = (NodeDependency) dependency;
				Element element = importEObject(nodeDependency.getObject());
				nodeDependency.setObject(element);
				
			// EdgeDependency
			}else if(dependency instanceof EdgeDependency){
				
				EdgeDependency edgeDependency = (EdgeDependency) dependency;
				Reference reference = importEReference(edgeDependency.getType(), edgeDependency.getSrcObject(), edgeDependency.getTgtObject());
				edgeDependency.setSrcObject(reference.getSource());
				edgeDependency.setTgtObject(reference.getTarget());
				
			// AttributeDependency
			}else if(dependency instanceof AttributeDependency){
				
				AttributeDependency attributeDependency = (AttributeDependency) dependency;
				Attribute attribute = importEAttribute(attributeDependency.getType(), attributeDependency.getObject());
				attributeDependency.setObject(attribute.eContainer());
			}
		}
	}
	
	/**
	 * 
	 * @param symmetricDifference
	 * @throws ImportFailedException
	 */
	private void importSymmetricDifference(SymmetricDifference symmetricDifference) throws ImportFailedException{
		importMatching(symmetricDifference.getMatching());
		for(SemanticChangeSet scs : symmetricDifference.getChangeSets()){
			for(Change c : scs.getChanges()){
				this.importChange(c);
			}
			this.importEditRuleMatch(scs.getEditRuleMatch());		
		}
	}
	
	/**
	 * 
	 * @param matching
	 * @throws ImportFailedException
	 */
	private void importMatching(Matching matching) throws ImportFailedException{
		// Correspondence
		for(Correspondence c : matching.getCorrespondences()){
			if(mandatoryEObjects.contains(c.getMatchedA()) && mandatoryEObjects.contains(c.getMatchedB())){
				Element elementA = importEObject(c.getMatchedA());
				c.setMatchedA(elementA);
				
				Element elementB = importEObject(c.getMatchedB());
				c.setMatchedB(elementB);
			}
		}
		
		// Unmatched A
		Set<EObject> unmatchedA = new HashSet<EObject>(matching.getUnmatchedA());
		for(EObject eObject : unmatchedA){
			if(mandatoryEObjects.contains(eObject)) {
				Element element = importEObject(eObject);
				matching.getUnmatchedA().add(element);
				matching.getUnmatchedA().remove(eObject);
			}
		}
		
		// Unmatched B
		Set<EObject> unmatchedB = new HashSet<EObject>(matching.getUnmatchedB());
		for(EObject eObject : unmatchedB){
			if(mandatoryEObjects.contains(eObject)) {
				Element element = importEObject(eObject);
				matching.getUnmatchedB().add(element);
				matching.getUnmatchedB().remove(eObject);
			}
		}
	}
	
	/**
	 * 
	 * @param editRuleMatch
	 * @throws ImportFailedException 
	 */
	private void importEditRuleMatch(EditRuleMatch editRuleMatch) throws ImportFailedException {
		// node occurrences in a
		for (String nodeOccurrenceA_key : editRuleMatch.getNodeOccurrencesA().keySet()) {

			EObjectSet eObjectSet = SymmetricFactory.eINSTANCE.createEObjectSet();

			for (EObject srcObject : editRuleMatch.getNodeOccurrencesA().get(nodeOccurrenceA_key).getElements()) {

				Element elementA = importEObject(srcObject);
				elementA.getAnnotations().add(annotationOrigin);
				eObjectSet.addElement(elementA);

			}
			editRuleMatch.getNodeOccurrencesA().put(nodeOccurrenceA_key, eObjectSet);
		}

		// node occurrences in b
		for (String nodeOccurrenceB_key : editRuleMatch.getNodeOccurrencesB().keySet()) {

			EObjectSet eObjectSet = SymmetricFactory.eINSTANCE.createEObjectSet();

			for (EObject srcObject : editRuleMatch.getNodeOccurrencesB().get(nodeOccurrenceB_key).getElements()) {

				Element elementB = importEObject(srcObject);
				elementB.getAnnotations().add(annotationChanged);
				eObjectSet.addElement(elementB);

			}
			editRuleMatch.getNodeOccurrencesB().put(nodeOccurrenceB_key, eObjectSet);
		}
	}


	/**
	 * 
	 * @param change
	 * @throws ImportFailedException
	 */
	private void importChange(Change change) throws ImportFailedException{
		// RemoveObject
		if (change instanceof RemoveObject) {

			RemoveObject removeObject = (RemoveObject) change;
			EObject eObject = removeObject.getObj();
			
			Element elementA = importEObject(eObject);
			removeObject.setObj(elementA);
			
			for(EAttribute eAttribute : eObject.eClass().getEAllAttributes()) {
				if (!eAttribute.isDerived()) {
					Attribute attribute = this.importEAttribute(eAttribute, eObject);
					attribute.getAnnotations().add(annotationOrigin);
				}
			}
		}else
	
		// RemoveReference
		if (change instanceof RemoveReference) {

			RemoveReference removeReference = (RemoveReference) change;

			Reference referenceA = importEReference(removeReference.getType(), removeReference.getSrc(), removeReference.getTgt());
			removeReference.setSrc(referenceA.getSource());
			removeReference.setTgt(referenceA.getTarget());
			
			referenceA.getAnnotations().add(annotationOrigin);
		}else
		
		// AddObject
		if (change instanceof AddObject) {

			AddObject addObject = (AddObject) change;
			EObject eObject = addObject.getObj();
			
			Element elementB = importEObject(eObject);
			addObject.setObj(elementB);
			
			for(EAttribute eAttribute : eObject.eClass().getEAllAttributes()) {
				if (!eAttribute.isDerived()) {
					Attribute attribute = this.importEAttribute(eAttribute, eObject);
					attribute.getAnnotations().add(annotationChanged);
				}
			}
		}else
		
		// AddReference
		if (change instanceof AddReference) {

			AddReference addReference = (AddReference) change;
			
			Reference referenceB = importEReference(addReference.getType(), addReference.getSrc(), addReference.getTgt());
			addReference.setSrc(referenceB.getSource());
			addReference.setTgt(referenceB.getTarget());
			
			referenceB.getAnnotations().add(annotationChanged);
		}else
		
		// AttributeValueChange
		if (change instanceof AttributeValueChange) {

			AttributeValueChange attributeValueChange = (AttributeValueChange) change;
			
			Element elementA = importEObject(attributeValueChange.getObjA());
			Attribute attributeA = this.importEAttribute(attributeValueChange.getType(), attributeValueChange.getObjA());
			attributeA.getAnnotations().add(annotationOrigin);
			
			Element elementB = importEObject(attributeValueChange.getObjB());
			Attribute attributeB = this.importEAttribute(attributeValueChange.getType(), attributeValueChange.getObjA());
			attributeB.getAnnotations().add(annotationChanged);
			
			assert elementA.equals(elementB): "should be the same";
		
			attributeValueChange.setObjA(elementA);
			attributeValueChange.setObjB(elementB);
		}
	}

	@Override
	protected String computeSignature(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Element createElement() {
		return factory.createSlicedElement();
	}
}
