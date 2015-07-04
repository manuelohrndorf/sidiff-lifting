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

public class TechnicalDifferenceBuilderUMLProfileApplication extends TechnicalDifferenceBuilderUML {

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		
		Set<EClass> unconsideredNodeTypes = new HashSet<EClass>();
		unconsideredNodeTypes.addAll(super.getUnconsideredNodeTypes());
		
		//Filter UML ProfileApplication
		unconsideredNodeTypes.add(UMLPackage.eINSTANCE.getProfileApplication());
		
		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		
		Set<EReference> unconsideredEdgeTypes = new HashSet<EReference>();
		unconsideredEdgeTypes.addAll(super.getUnconsideredEdgeTypes());
		
		return unconsideredEdgeTypes;
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<EAttribute>();
		unconsideredAttributeTypes.addAll(super.getUnconsideredAttributeTypes());
		
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
		return "TechnicalDifference Builder for UML (without ProfileApplication)";
	}
	
	
}
