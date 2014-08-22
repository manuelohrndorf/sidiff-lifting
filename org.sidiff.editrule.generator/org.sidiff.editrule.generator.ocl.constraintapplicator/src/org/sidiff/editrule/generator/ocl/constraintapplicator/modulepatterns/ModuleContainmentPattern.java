package org.sidiff.editrule.generator.ocl.constraintapplicator.modulepatterns;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;

/**
 * An OCLExpressionPattern can refer to such a ModuleContainmentPattern.
 * A ModuleContainmentPattern can be created to define expected
 * occurrences of "create" EAttribute/EReference/EClassifier types
 * in a corresponding OCLExpressionPattern.
 * 
 * @author mrindt
 *
 */
public class ModuleContainmentPattern extends ModuleMatchPattern {

	/**
	 * List of expected EAttributes that are expected to occur as <<create>>
	 */
	private List<EAttribute> containedCreateEAttributes;
	/**
	 * List of expected EReferences that are expected to occur as <<create>>
	 */
	private List<EReference> containedCreateEReferences;
	/**
	 * List of expected EClassifiers that are expected to occur as <<create>>
	 */
	private List<EClassifier> containedCreateEClassifier;
	
	
	/**
	 * Adds an EAttribute to the list of expected create EAttributes.
	 * @param ea
	 */
	public void addExpectedCreateEAttribute(EAttribute ea) {
		containedCreateEAttributes.add(ea);
	}
	
	/*
	 * Adds an EReference to the list of expected create EReference.
	 */
	public void addExpectedCreateEReference(EReference eRef) {
		containedCreateEReferences.add(eRef);
	}
	
	/*
	 * Adds an EClassifier to the list of expected create EClassifer.
	 */
	public void addExpectedCreateEClassifier(EClassifier eClassifier) {
		containedCreateEClassifier.add(eClassifier);
	}
	
	/**
	 * @return a list of all expected create EAttributes
	 */
	public List<EAttribute> getExpectedCreateEAttributes() {
		return containedCreateEAttributes;
	}
	
	/**
	 * @return a list of all expected create EReferences
	 */	
	public List<EReference> getExpectedCreateEReferences() {
		return containedCreateEReferences;
	}
	
	/**
	 * @return a list of all expected create EClassifiers
	 */
	public List<EClassifier> getExpectedCreateEClassifier() {
		return containedCreateEClassifier;
	}
}
