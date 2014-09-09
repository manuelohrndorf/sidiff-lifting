package org.sidiff.difference.matcher.featuremodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.matcher.BaseMatcher;
import org.silift.common.util.access.EMFModelAccessEx;

import de.imotep.featuremodel.variability.metamodel.FeatureModel.ExcludeConstraint;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.FeatureModel;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.FeatureModelPackage;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.RequireConstraint;

public class FeatureModelMatcher extends BaseMatcher {
	
	public static final String KEY = "FeatureModelMatcher";


	@Override
	public String getName() {
		return "FeatureModel Matcher";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		
		// Get FeatureModel URI
		String fmUri = FeatureModelPackage.eNS_URI;
		
		//Can only handle FeatureModel models, therefore both 
		//models must conform to the corresponding fmUri.
		return EMFModelAccessEx.getCharacteristicDocumentType(modelA).equals(fmUri) && 
				EMFModelAccessEx.getCharacteristicDocumentType(modelB).equals(fmUri);

	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}

	@Override
	protected boolean isCorresponding(EObject elementA, EObject elementB) {
		assert (elementA != null && elementB != null) : "One of the elements to check for correspondence is null!";		
		
		// None of the elements must be already in a correspondence
		if (isCorresponding(elementA) || isCorresponding(elementB)) {
			return false;
		}
		
		// Elements must have the same type
		if (elementA.eClass() != elementB.eClass()){
			return false;
		}
		
		// Check for @link{FeatureModel} element
		if(elementA instanceof FeatureModel && elementB instanceof FeatureModel){
			// As there shall be only one FeatureModel, they are always corresponding
			return true;
		}
		
		// Check for @link{RequireConstraint} element
		// If both RequireConstraints are referencing the same objects
		// they are corresponding.
		if(elementA instanceof RequireConstraint && elementB instanceof RequireConstraint){
			RequireConstraint rcA = (RequireConstraint) elementA;
			RequireConstraint rcB = (RequireConstraint) elementB;
			
			// Note:This could be done better through ordered comparisons
			// 		as we then could ask isCorresponding(). But 
			// 		we can not guarantee that the Features have been matched beforehand.
			if(isSameEcoreID(rcA.getFeature(),rcB.getFeature())
					&& isSameEcoreID(rcA.getRequiredFeature(),rcB.getRequiredFeature())){
				return true;				
			}
		}
		
		// Check for @link{ExcludeConstraint} element
		// If both ExcludeConstraint are referencing the same objects
		// they are corresponding.
		if(elementA instanceof ExcludeConstraint && elementB instanceof ExcludeConstraint){
			ExcludeConstraint ecA = (ExcludeConstraint) elementA;
			ExcludeConstraint ecB = (ExcludeConstraint) elementB;

			// Note:This could be done better through ordered comparisons
			// 		as we then could ask isCorresponding(). But 
			// 		we can not guarantee that the Features have been matched beforehand.
			if(isSameEcoreID(ecA.getExcludedFeatureA(), ecB.getExcludedFeatureA()) 
					&& isSameEcoreID(ecA.getExcludedFeatureB(),	ecB.getExcludedFeatureB())){
				return true;				
			}
		}
		

		// Match based on EcoreID otherwise
		return isSameEcoreID(elementA, elementB);
	}

	/**
	 * Check whether two elements have got the same EcoreID
	 * 
	 * @param elementA
	 * @param elementB
	 * @return
	 */
	private boolean isSameEcoreID(EObject elementA, EObject elementB) {
		// Check for ID attribute
		EAttribute idAttrA = elementA.eClass().getEIDAttribute();
		EAttribute idAttrB = elementB.eClass().getEIDAttribute();
		if(idAttrA == null || idAttrB == null){
			return false;
		}
		else{
			Object idA = elementA.eGet(idAttrA);
			Object idB = elementB.eGet(idAttrB);

			if(idA == null || idB == null){
				return false;
			}
			else{
				return idA.equals(idB);
			}
		}
	}
}
