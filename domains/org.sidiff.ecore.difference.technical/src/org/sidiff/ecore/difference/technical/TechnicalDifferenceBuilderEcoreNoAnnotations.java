package org.sidiff.ecore.difference.technical;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * Filters all technical stuff (just calls supper class methods) and
 * additionally filters all types related to Ecore EAnnotation.
 * 
 * @author cpietsch
 */
public class TechnicalDifferenceBuilderEcoreNoAnnotations extends TechnicalDifferenceBuilderEcoreGenerics {

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = super.getUnconsideredNodeTypes();

		// add EAnnotation
		unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getEAnnotation());
		unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getEStringToStringMapEntry());

		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = super.getUnconsideredEdgeTypes();

		// add EAnnotation
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEAnnotation_Contents());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEModelElement_EAnnotations());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEAnnotation_Details());

		return unconsideredEdgeTypes;
	}

	@Override
	public String getName() {
		return "Ecore Technical Difference Builder (with Generics and without Annotations)";
	}
}
