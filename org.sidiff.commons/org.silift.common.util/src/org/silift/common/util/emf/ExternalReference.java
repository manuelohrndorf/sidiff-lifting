package org.silift.common.util.emf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

public class ExternalReference {

	// TODO (TK): Encapsualte fields
	
	/**
	 * Träger-Objekt der externen Referenz.
	 */
	public EObject eObject;
	
	/**
	 * Typ der externen Referenz.
	 */
	public EStructuralFeature eStructuralFeature;

	/**
	 * Ziel der externen Referenz.
	 */
	public EObject eStructuralFeatureValue;

	/**
	 * 
	 * 
	 * @param eObject
	 * @param eStructuralFeature
	 * @param eStructuralFeatureValue
	 */
	public ExternalReference(EObject eObject, EStructuralFeature eStructuralFeature, EObject eStructuralFeatureValue) {
		super();
		this.eObject = eObject;
		this.eStructuralFeature = eStructuralFeature;
		this.eStructuralFeatureValue = eStructuralFeatureValue;
	}

	/**
	 * Get the Resource of the external EClassifier
	 * 
	 * @return
	 */
	public Resource getExternalResource() {
		return eStructuralFeatureValue.eResource();
	}

}
