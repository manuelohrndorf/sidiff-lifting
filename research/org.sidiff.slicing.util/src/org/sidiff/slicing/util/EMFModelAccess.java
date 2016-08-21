package org.sidiff.slicing.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class EMFModelAccess {
	
	@SuppressWarnings("unchecked")
	public static List<EObject> getEReferenceTargets(EObject context, EReference eReference, boolean mandatoryOnly){
		List<EObject> nextContextElements = new ArrayList<EObject>();
		if(!eReference.isDerived()){
			if(mandatoryOnly && !(eReference.getLowerBound() > 0)){
				return nextContextElements;
			}
			if(eReference.isMany()){
				nextContextElements.addAll((EList<EObject>) context.eGet(eReference));
			}else{
				nextContextElements.add((EObject) context.eGet(eReference));
			}
		}
		return nextContextElements;
	}
}
