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
	
	private Map<EReference, Set<SlicedReference>> slicedReferences;
	
	public SlicedElement(EObject origin, boolean boundary){
		this.origin = origin;
		this.copy = EMFUtil.copyWithoutReferences(origin);
		this.slicedReferences = new HashMap<EReference, Set<SlicedReference>>();
	}
	
	public EObject getOrigin() {
		return this.origin;
	}

	public EObject getCopy() {
		return this.copy;
	}
	
	public void addSlicedReference(SlicedReference slicedReference){
		if(!this.slicedReferences.containsKey(slicedReference.getType())){
			this.slicedReferences.put(slicedReference.getType(), new HashSet<SlicedReference>());
		}
		this.slicedReferences.get(slicedReference.getType()).add(slicedReference);
	}
	
	public void removeSlicedReference(SlicedReference slicedReference){
		this.slicedReferences.get(slicedReference.getType()).remove(slicedReference);
	}
	
	public void removeAllSlicedReferences(EReference type){
		this.slicedReferences.remove(type);
	}
	
	public boolean contains(EReference type, EObject tgt){
		return this.slicedReferences.containsKey(type) && this.slicedReferences.get(type).contains(tgt);
	}
	
	public Set<SlicedReference> getSlicedReference(EReference type){
		if(this.slicedReferences.get(type) != null){
			return Collections.unmodifiableSet(this.slicedReferences.get(type));
		}else {
			return Collections.unmodifiableSet(Collections.emptySet());
		}
	}
	public Set<SlicedReference> getSlicedReferences(){
		Set<SlicedReference> slicedReferences = new HashSet<SlicedReference>();
		for(EReference eReference : this.slicedReferences.keySet()){
			slicedReferences.addAll(this.slicedReferences.get(eReference));
		}
		return slicedReferences;
	}
	
	public EClass getType(){
		return this.origin.eClass();
	}	
}
