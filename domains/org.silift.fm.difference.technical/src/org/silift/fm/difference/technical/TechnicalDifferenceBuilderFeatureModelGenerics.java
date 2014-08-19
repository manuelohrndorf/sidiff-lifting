package org.silift.fm.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.featuremodel.FeatureModelPackage;
import org.sidiff.difference.technical.TechnicalDifferenceBuilder;
import org.silift.common.util.access.EMFModelAccessEx;

/**
 * Filters technical stuff but does not filter types related to Ecore Generics.
 * 23.01.2014 : Does currently not filter anything at all
 * 
 * @author dreuling 
 */
public class TechnicalDifferenceBuilderFeatureModelGenerics extends TechnicalDifferenceBuilder {

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = new HashSet<EClass>();

		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = new HashSet<EReference>();

		return unconsideredEdgeTypes;
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<EAttribute>();

		return unconsideredAttributeTypes;
	}

	@Override
	protected void checkDocumentType(Resource model) {
		String docType = EMFModelAccessEx.getCharacteristicDocumentType(model);
		assert (docType == FeatureModelPackage.eNS_URI) : "Wrong document type: Expected " + FeatureModelPackage.eNS_URI + " but got " + docType;
	}

	@Override
	protected String getObjectName(EObject obj) {		
		return obj.toString();
	}

	@Override
	public String getDocumentType() {
		return FeatureModelPackage.eNS_URI;
	}

	@Override
	public String getName() {
		return "Feature Model Technical Difference Builder (with Generics)";
	}
}
