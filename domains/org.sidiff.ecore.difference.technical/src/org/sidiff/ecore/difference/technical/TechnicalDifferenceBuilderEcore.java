package org.sidiff.ecore.difference.technical;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * Filters all technical stuff (just calls supper class methods) and
 * additionally filters all types related to Ecore Generics.
 * 
 * @author kehrer
 */
public class TechnicalDifferenceBuilderEcore extends TechnicalDifferenceBuilderEcoreGenerics {

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = super.getUnconsideredNodeTypes();

		// add generics
		unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getETypeParameter());
		unconsideredNodeTypes.add(EcorePackage.eINSTANCE.getEGenericType());

		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = super.getUnconsideredEdgeTypes();

		// add generics
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEClassifier_ETypeParameters());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEOperation_ETypeParameters());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEOperation_EGenericExceptions());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getEClass_EGenericSuperTypes());
		unconsideredEdgeTypes.add(EcorePackage.eINSTANCE.getETypedElement_EGenericType());

		return unconsideredEdgeTypes;
	}

	@Override
	public String getName() {
		return "Ecore Technical Difference Builder";
	}
}
