package org.sidiff.matcher.signature_based.fm;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.sidiff.difference.matcher.SignatureBasedMatcher;

import de.imotep.featuremodel.variability.metamodel.FeatureModel.ExcludeConstraint;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.FeatureModel;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.FeatureModelPackage;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.RequireConstraint;

public class FeatureModelMatcher extends SignatureBasedMatcher {

	private static final String KEY = "SignatureBasedFeatureModelMatcher";

	private static final String NAME = "Signature Based Feature Model Matcher";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public boolean canComputeReliability() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int calculateSignature(EObject eObject) {
		// Check for @link{FeatureModel} element
		if (eObject instanceof FeatureModel) {
			return ((FeatureModel)eObject).getName().hashCode();
		}

		// Check for @link{RequireConstraint} element
		if (eObject instanceof RequireConstraint) {
			RequireConstraint rc = (RequireConstraint) eObject;
			return (rc.getName()+rc.getFeature().getName()+rc.getRequiredFeature()).hashCode();
		}

		// Check for @link{ExcludeConstraint} element
		if (eObject instanceof ExcludeConstraint) {
			ExcludeConstraint ec = (ExcludeConstraint) eObject;
			return (ec.getName()+ec.getExcludedFeatureA().getName()+ec.getExcludedFeatureB().getName()).hashCode();
		}
		
		// Check for attribute "name" and its values equality
		EStructuralFeature attrName = eObject.eClass().getEStructuralFeature("name");
		if (attrName != null && attrName instanceof EAttribute) {
			Object name = eObject.eGet(attrName);
			return name.hashCode();
		}
		return 0;
	}

	@Override
	public String getDocumentType() {
		return FeatureModelPackage.eNS_URI;
	}

	@Override
	public Map<String, Object> getConfigurationOptions() {
		// TODO Auto-generated method stub
		return null;
	}

}
