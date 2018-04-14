package org.sidiff.slicer.rulebased.exceptions;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public class ExtendedSlicingCriteriaIntersectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1910568766857802517L;
	
	private Set<EObject> intersection;
	
	public ExtendedSlicingCriteriaIntersectionException(Set<EObject> intersection){
		this.intersection = intersection;
	}

	public Set<EObject> getIntersection() {
		return intersection;
	}
	
}
