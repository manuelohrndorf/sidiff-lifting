package org.sidiff.fm.matcher.signaturebased;

import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.sidiff.matcher.LocalSignatureMatcher;

import de.imotep.featuremodel.variability.metamodel.FeatureModel.ExcludeConstraint;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.FeatureModel;
import de.imotep.featuremodel.variability.metamodel.FeatureModel.RequireConstraint;

public class FeatureModelMatcher extends LocalSignatureMatcher {


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
	public Optional<String> getDescription() {
		return Optional.of("This matcher matches EMF represented FeatureModels");
	}

	@Override
	public String getName() {
		return "Feature Model Matcher";
	}
}
