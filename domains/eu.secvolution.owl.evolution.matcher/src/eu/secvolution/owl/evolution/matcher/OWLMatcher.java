package eu.secvolution.owl.evolution.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.matcher.BaseMatcher;

import carisma.modeltype.owl2.model.owl.AnnotationByConstant;
import carisma.modeltype.owl2.model.owl.ClassAssertion;
import carisma.modeltype.owl2.model.owl.Constant;
import carisma.modeltype.owl2.model.owl.Declaration;
import carisma.modeltype.owl2.model.owl.Entity;
import carisma.modeltype.owl2.model.owl.EntityAnnotation;
import carisma.modeltype.owl2.model.owl.EquivalentClasses;
import carisma.modeltype.owl2.model.owl.InverseObjectProperties;
import carisma.modeltype.owl2.model.owl.ObjectComplementOf;
import carisma.modeltype.owl2.model.owl.ObjectIntersectionOf;
import carisma.modeltype.owl2.model.owl.ObjectPropertyDomain;
import carisma.modeltype.owl2.model.owl.ObjectPropertyRange;
import carisma.modeltype.owl2.model.owl.ObjectSomeValuesFrom;
import carisma.modeltype.owl2.model.owl.ObjectUnionOf;
import carisma.modeltype.owl2.model.owl.Ontology;
import carisma.modeltype.owl2.model.owl.OwlPackage;
import carisma.modeltype.owl2.model.owl.SubClassOf;
import carisma.modeltype.owl2.model.owl.SubObjectPropertyOf;
import carisma.modeltype.owl2.model.owl.TransitiveObjectProperty;
import carisma.modeltype.owl2.model.owl.URI;

public class OWLMatcher extends BaseMatcher {

	private Map<EObject, Set<EObject>> correspondences = new HashMap<EObject, Set<EObject>>();
	
	@Override
	public String getDocumentType() {
		return OwlPackage.eNS_URI;
	}

	@Override
	public String getName() {
		return "OWL Matcher";
	}

	@Override
	public String getDescription() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected void match() {
		//We use the "first" ValueMap as starting point.
		
		Resource resourceA = getModels().iterator().next();
		Resource resourceB = getModels().iterator().next();
		
		for (Iterator<EObject> iteratorA = resourceA.getAllContents(); iteratorA.hasNext();) {
			EObject elementA = iteratorA.next();
			correspondences.put(elementA, new HashSet<EObject>());
			for (Iterator<EObject> iteratorB = resourceB.getAllContents(); iteratorB.hasNext();) {
				EObject elementB = iteratorB.next();
				
				if(isCorrespondingWith(elementA, elementB, false)){
					correspondences.get(elementA).add(elementB);
				}
			}
			
		}
		
		for(EObject key : correspondences.keySet()){
			if(correspondences.get(key).size() > 0){
				assert (correspondences.get(key).size()==1): "There are multiple correspondence elements for element " + key;
				List<EObject> eObjects= new ArrayList<EObject>();
				eObjects.add(key);
				eObjects.addAll(correspondences.get(key));
				getCorrespondencesService().addCorrespondence(eObjects.toArray(new EObject[]{}));
			}
		}
		
	}

	/**
	 * Checks, whether two Ontology-Objects are the same by matching their URI
	 * or toString values.
	 * 
	 * @param elementA
	 *            The first object, element of Ontology A
	 * @param elementB
	 *            The second objact, element of Ontology B
	 * @param checkPrevious
	 *            If set to true, the method will return false if one of the
	 *            objects is already matched. Useful for efficiency, but may not
	 *            be wanted.
	 * @return true, if the match was successful
	 */
	private boolean isCorrespondingWith(EObject elementA, EObject elementB,
			boolean checkPrevious) {
		assert (elementA != null && elementB != null) : "One of the elements to check for correspondence is null!";

		// type should be identical in order to correspond
		if (elementA.eClass() != elementB.eClass()) {
			return false;
		}

		if (checkPrevious) {
			// None of the elements must be already in a correspondence
			if (correspondences.get(elementA).size()>0) {
				return false;
			}
		}

		// "Singleton" elements
		if (elementA instanceof Ontology) {
			return true;
		}

		// SubClassOf: check, whether both Subclass and Superclass are equal.
		if (elementA instanceof SubClassOf) {
			SubClassOf subclassofA = (SubClassOf) elementA;
			SubClassOf subclassofB = (SubClassOf) elementB;
			return (isCorrespondingWith(subclassofA.getSubClassExpression(),
					subclassofB.getSubClassExpression(), false) && isCorrespondingWith(
					subclassofA.getSuperClassExpression(),
					subclassofB.getSuperClassExpression(), false));
		}

		if (elementA instanceof SubObjectPropertyOf) {
			SubObjectPropertyOf subObjPropA = (SubObjectPropertyOf) elementA;
			SubObjectPropertyOf subObjPropB = (SubObjectPropertyOf) elementB;
			return (isCorrespondingWith(
					subObjPropA.getSubObjectPropertyExpressions(),
					subObjPropB.getSubObjectPropertyExpressions(), false) && isCorrespondingWith(
					subObjPropA.getSuperObjectPropertyExpression(),
					subObjPropB.getSuperObjectPropertyExpression(), false));
		}

		if (elementA instanceof InverseObjectProperties) {
			InverseObjectProperties invObPropA = (InverseObjectProperties) elementA;
			InverseObjectProperties invObPropB = (InverseObjectProperties) elementB;
			boolean same = true;
			for (EObject eObA : invObPropA.getInverseObjectProperties()) {
				boolean foundPair = false;
				for (EObject eObB : invObPropB.getInverseObjectProperties()) {
					foundPair = (foundPair || isCorrespondingWith(eObA, eObB,
							false));
				}
				same = (same && foundPair);
			}
			return same;
		}

		if (elementA instanceof TransitiveObjectProperty) {
			TransitiveObjectProperty transPropA = (TransitiveObjectProperty) elementA;
			TransitiveObjectProperty transPropB = (TransitiveObjectProperty) elementB;
			return isCorrespondingWith(
					transPropA.getObjectPropertyExpression(),
					transPropB.getObjectPropertyExpression(), false);
		}

		if (elementA instanceof ObjectPropertyRange) {
			ObjectPropertyRange objPropRangeA = (ObjectPropertyRange) elementA;
			ObjectPropertyRange objPropRangeB = (ObjectPropertyRange) elementB;
			return (isCorrespondingWith(
					objPropRangeA.getObjectPropertyExpression(),
					objPropRangeB.getObjectPropertyExpression(), false) && isCorrespondingWith(
					objPropRangeA.getRange(), objPropRangeB.getRange(), false));
		}

		if (elementA instanceof ClassAssertion) {
			ClassAssertion classAssA = (ClassAssertion) elementA;
			ClassAssertion classAssB = (ClassAssertion) elementB;
			return isCorrespondingWith(classAssA.getClassExpression(),
					classAssB.getClassExpression(), false);
		}

		if (elementA instanceof EquivalentClasses) {
			EquivalentClasses eqClassesA = (EquivalentClasses) elementA;
			EquivalentClasses eqClassesB = (EquivalentClasses) elementB;
			boolean same = true;
			for (EObject eObA : eqClassesA.getEquivalentClassExpressions()) {
				boolean foundPair = false;
				for (EObject eObB : eqClassesB.getEquivalentClassExpressions()) {
					foundPair = (foundPair || isCorrespondingWith(eObA, eObB,
							false));
				}
				same = (same && foundPair);
			}
			return same;
		}

		// ObjectUnionOf: check, whether all elements find a pair.
		// TODO: possibly wrong?
		if (elementA instanceof ObjectUnionOf) {
			ObjectUnionOf objUnionA = (ObjectUnionOf) elementA;
			ObjectUnionOf objUnionB = (ObjectUnionOf) elementB;
			boolean same = true;
			for (EObject eObA : objUnionA.getClassExpressions()) {
				boolean foundPair = false;
				for (EObject eObB : objUnionB.getClassExpressions()) {
					foundPair = (foundPair || isCorrespondingWith(eObA, eObB,
							false));
				}
				same = (same && foundPair);
			}
			return same;
		}

		if (elementA instanceof ObjectIntersectionOf) {
			ObjectIntersectionOf objIntA = (ObjectIntersectionOf) elementA;
			ObjectIntersectionOf objIntB = (ObjectIntersectionOf) elementB;
			boolean same = true;
			for (EObject eObA : objIntA.getClassExpressions()) {
				boolean foundPair = false;
				for (EObject eObB : objIntB.getClassExpressions()) {
					foundPair = (foundPair || isCorrespondingWith(eObA, eObB,
							false));
				}
				same = (same && foundPair);
			}
			return same;
		}

		String uriA = getUri(elementA);
		String uriB = getUri(elementB);
		if (!uriA.equals("") && !uriB.equals("") && uriA.equals(uriB)) {
			return true;
		}

		// Named Elements
		return equalNames(elementA, elementB);
	}

	/**
	 * Returns the URI (or another individually unique String-value)
	 * 
	 * @param eOb
	 *            Object, of which the URI should be found
	 * @return The URI, or unique String
	 */
	private String getUri(EObject eOb) {
		if (eOb instanceof Entity) {
			Entity ent = (Entity) eOb;
			String[] uri = ent.getEntityURI().getValue().split("/");
			return uri[uri.length - 1];
		}
		if (eOb instanceof Declaration) {
			Declaration dec = (Declaration) eOb;
			return getUri(dec.getEntity());
		}
		if (eOb instanceof URI) {
			URI urii = (URI) eOb;
			return urii.getValue();
		}
		if (eOb instanceof Constant) {
			Constant constI = (Constant) eOb;
			return constI.getLexicalValue();
		}
		if (eOb instanceof AnnotationByConstant) {
			AnnotationByConstant ann = (AnnotationByConstant) eOb;
			return ann.getAnnotationValue().getLexicalValue();
		}
		if (eOb instanceof ObjectComplementOf) {
			ObjectComplementOf objComp = (ObjectComplementOf) eOb;
			return getUri((Entity) objComp.getClassExpression());
		}
		if (eOb instanceof ObjectSomeValuesFrom) {
			ObjectSomeValuesFrom objSomeVal = (ObjectSomeValuesFrom) eOb;
			return getUri((Entity) objSomeVal.getClassExpression());
		}
		if (eOb instanceof EntityAnnotation) {
			EntityAnnotation entAn = (EntityAnnotation) eOb;
			return getUri(entAn.getEntity());
		}
		if(eOb instanceof ObjectPropertyDomain){
			ObjectPropertyDomain objPropDom=(ObjectPropertyDomain)eOb;
			return getUri(objPropDom.getDomain());
		}
		return "";
	}

	private boolean equalNames(EObject elementA, EObject elementB) {
		if (elementA == null || elementB == null) {
			return false;
		}

		// Check for attribute "name" and its values equality
		EStructuralFeature attrName = elementA.eClass().getEStructuralFeature(
				"name");
		if (attrName != null && attrName instanceof EAttribute) {
			Object nameA = elementA.eGet(attrName);
			Object nameB = elementB.eGet(attrName);

			if (nameA != null && nameB != null) {
				return nameA.equals(nameB);
			}
		}
		return false;
	}

	
}
