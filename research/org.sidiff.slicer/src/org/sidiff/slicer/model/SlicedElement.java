package org.sidiff.slicer.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.EMFUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class SlicedElement {

	private EObject origin;
	
	private EObject copy;
	
	private Set<SlicedReference> slicedReferences;
	
	public SlicedElement(EObject origin, boolean boundary){
		this.origin = origin;
		this.copy = EMFUtil.copyWithoutReferences(origin);
		this.slicedReferences = new HashSet<SlicedReference>();
	}

	public SlicedElement(EObject origin, EObject copy, boolean boundary){
		this.origin = origin;
		this.copy = copy;
		this.slicedReferences = new HashSet<SlicedReference>();
	}
	
	public EObject getOrigin() {
		return origin;
	}

	public EObject getCopy() {
		return copy;
	}
	
	public Set<SlicedReference> getSlicedReferences() {
		return this.slicedReferences;
	}
	
	public EClass getType(){
		return origin != null ? origin.eClass() : null;
	}	
}
