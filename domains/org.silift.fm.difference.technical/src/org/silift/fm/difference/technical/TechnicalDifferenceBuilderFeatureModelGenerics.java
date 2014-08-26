package org.silift.fm.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.technical.TechnicalDifferenceBuilder;
import org.silift.common.util.access.EMFModelAccessEx;

import de.imotep.featuremodel.variability.metamodel.FeatureModel.FeatureModelPackage;

/**
 * Filters technical stuff...
 * 23.01.2014 : Does currently not filter anything at all
 * 21.05.2014 : Filters Comments and their reference
 * 
 * @author dreuling 
 */
public class TechnicalDifferenceBuilderFeatureModelGenerics extends TechnicalDifferenceBuilder {

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = new HashSet<EClass>();
		
		unconsideredNodeTypes.add(FeatureModelPackage.eINSTANCE.getComment());

		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = new HashSet<EReference>();
		
		unconsideredEdgeTypes.add(FeatureModelPackage.eINSTANCE.getComment_Element());		

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
