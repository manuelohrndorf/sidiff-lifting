package org.sidiff.fm.matcher.signaturebased;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.sidiff.matcher.LocalSignatureMatcher;

import de.imotep.featuremodel.variability.metamodel.FeatureModel.ExcludeConstraint;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.FeatureModel;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.RequireConstraint;

public class FeatureModelMatcher extends LocalSignatureMatcher {

	private static final String KEY = "FeatureModelMatcher";

	private static final String NAME = "Feature Model Matcher";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getKey() {
		return KEY;
	}


	@Override
	protected String getElementSignature(EObject element) {
		// Check for @link{FeatureModel} element
		if (element instanceof FeatureModel) {
			return (((FeatureModel)element).getName());
		}

		// Check for @link{RequireConstraint} element
		if (element instanceof RequireConstraint) {
			RequireConstraint rc = (RequireConstraint) element;
			return (rc.getName()+rc.getFeature().getName()+rc.getRequiredFeature());
		}

		// Check for @link{ExcludeConstraint} element
		if (element instanceof ExcludeConstraint) {
			ExcludeConstraint ec = (ExcludeConstraint) element;
			return (ec.getName()+ec.getExcludedFeatureA().getName()+ec.getExcludedFeatureB().getName());
		}

		// Check for attribute "name" and its values equality
		EStructuralFeature attrName = element.eClass().getEStructuralFeature("name");
		if (attrName != null && attrName instanceof EAttribute) {
			Object name = element.eGet(attrName);
			return (String) name;
		}
		return null;
	}

	@Override
	protected boolean considerCandidatesOnly() {
		return false;
	}

	@Override
	public String getDescription() {
		return "This matcher matches EMF represented FeatureModels";
	}

	@Override
	public String getServiceID() {
		return KEY;
	}


}
