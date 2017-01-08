package org.sidiff.ecore.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.sidiff.difference.technical.AbstractTechnicalDifferenceBuilder;

/**
 * Filters technical stuff but does not filter types related to Ecore Generics.
 * 
 * @author kehrer
 */
public class TechnicalDifferenceBuilderEcoreGenerics extends AbstractTechnicalDifferenceBuilder {

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = new HashSet<EClass>();

		// technical
		unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getEFactory());

		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = new HashSet<EReference>();

		// technical
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEFactory_EPackage());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEPackage_EFactoryInstance());

		return unconsideredEdgeTypes;
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<EAttribute>();

		// technical
		unconsideredAttributeTypes.add(EcorePackage.eINSTANCE.getEClassifier_InstanceClassName());
		unconsideredAttributeTypes.add(EcorePackage.eINSTANCE.getEClassifier_InstanceTypeName());

		return unconsideredAttributeTypes;
	}

	@Override
	protected String getObjectName(EObject obj) {		
		return obj.toString();
	}


	@Override
	public String getName() {
		return "Ecore Technical Difference Builder (with Generics)";
	}

	@Override
	public Set<String> getDocumentTypes() {
		Set<String> docTypes = new HashSet<String>();
		docTypes.add(EcorePackage.eNS_URI);
		return docTypes;
	}
}
