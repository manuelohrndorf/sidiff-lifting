package org.sidiff.uml2v4.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.sidiff.difference.technical.TechnicalDifferenceBuilder;

public class TechnicalDifferenceBuilderUMLForDELTA extends TechnicalDifferenceBuilder {

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = new HashSet<EClass>();

		// add EAnnotation stuff
		unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getEAnnotation());
		unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getEStringToStringMapEntry());
		
		// Delta specific stuff
		unconsideredNodeTypes.add(UMLPackage.eINSTANCE.getComment());

		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = new HashSet<EReference>();

		// add EAnnotation stuff
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEAnnotation_EModelElement());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEModelElement_EAnnotations());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEAnnotation_Details());
		
		// Delta specific stuff
		unconsideredEdgeTypes.add(UMLPackage.eINSTANCE.getComment_AnnotatedElement());
		unconsideredEdgeTypes.add(UMLPackage.eINSTANCE.getElement_OwnedComment());

		return unconsideredEdgeTypes;
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<EAttribute>();

		// add EAnnotation stuff
		unconsideredAttributeTypes.add(EcorePackage.eINSTANCE.getEAnnotation_Source());

		return unconsideredAttributeTypes;
	}

	@Override
	protected String getObjectName(EObject obj) {
		if (obj instanceof NamedElement) {
			return ((NamedElement) obj).getQualifiedName();
		}
		return obj.toString();
	}

	@Override
	public String getDocumentType() {
		return UMLPackage.eNS_URI;
	}
	
	@Override
	public String getName(){
		
		return "TechnicalDifference Builder for UML (DELTA)";
	}
}
