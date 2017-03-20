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
	
	private boolean boundary;
		
	private Set<SlicedReference> slicedReferences;
	
	public SlicedElement(EObject origin, boolean boundary){
		this.origin = origin;
		this.copy = EMFUtil.copyWithoutReferences(origin);
		this.boundary = boundary;
		this.slicedReferences = new HashSet<SlicedReference>();
		
	}

	public EObject getOrigin() {
		return origin;
	}

	public EObject getCopy() {
		return copy;
	}
	
	public boolean isBoundary(){
		return boundary;
	}

	public Set<SlicedReference> getSlicedReferences() {
		return slicedReferences;
	}
	
	public EClass getType(){
		return origin != null ? origin.eClass() : null;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SlicedElement){
			SlicedElement slicedElement = (SlicedElement) obj;
			return (origin.equals(slicedElement.getOrigin()));
		}
		return super.equals(obj);
	}
	

	
}
