package org.sidiff.slicer.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.EMFUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class SlicedElement {

	private EObject origin;
	
	private EObject copy;
	
	private Map<EReference, SlicedReference> slicedReferences;
	
	public SlicedElement(EObject origin, boolean boundary){
		this.origin = origin;
		this.copy = EMFUtil.copyWithoutReferences(origin);
		this.slicedReferences = new HashMap<EReference, SlicedReference>();
	}

	public SlicedElement(EObject origin, EObject copy, boolean boundary){
		this.origin = origin;
		this.copy = copy;
		this.slicedReferences = new HashMap<EReference, SlicedReference>();
	}
	
	public EObject getOrigin() {
		return this.origin;
	}

	public EObject getCopy() {
		return this.copy;
	}
	
	public void addSlicedReference(SlicedReference slicedReference){
		this.slicedReferences.put(slicedReference.getType(), slicedReference);
	}
	
	public void removeSlicedReference(EReference type){
		this.slicedReferences.remove(type);
	}
	
	public boolean contains(EReference type){
		return this.slicedReferences.containsKey(type);
	}
	
	public SlicedReference getSlicedReference(EReference type){
		return this.slicedReferences.get(type);
	}
	
	public Set<SlicedReference> getSlicedReferences() throws UnsupportedOperationException {
		return Collections.unmodifiableSet(new HashSet<SlicedReference>(this.slicedReferences.values()));
	}
	
	public EClass getType(){
		return this.origin != null ? this.origin.eClass() : null;
	}	
}
