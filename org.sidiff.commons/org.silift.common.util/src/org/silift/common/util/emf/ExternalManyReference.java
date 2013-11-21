package org.silift.common.util.emf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ExternalManyReference extends ExternalReference {

	//TODO (TK) Encapsualte fields
	
	/**
	 * the list index where eStructuralFeatureValue ist located.
	 */
	public int position;
	
	public ExternalManyReference(EObject eObject, EStructuralFeature eStructuralFeature, EObject eStructuralFeatureValue, int position) {
		super(eObject, eStructuralFeature, eStructuralFeatureValue);
		this.position = position;
	}

}
