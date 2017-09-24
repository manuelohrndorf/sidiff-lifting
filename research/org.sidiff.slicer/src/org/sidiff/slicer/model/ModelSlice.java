package org.sidiff.slicer.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

/**
 * 
 * @author cpietsch
 *
 */
public class ModelSlice {

	
	private Map<EObject,SlicedElement> slicedElements;
	
	
	public ModelSlice(){
		this.slicedElements = new HashMap<EObject,SlicedElement>();
	}
	
	public void addSlicedElement(SlicedElement slicedElement){
		this.slicedElements.put(slicedElement.getOrigin(), slicedElement);
	}
	
	public void removeSlicedElement(SlicedElement slicedElement){
		this.slicedElements.remove(slicedElement.getOrigin());
	}
	
	public boolean contains(EObject obj){
		return this.slicedElements.containsKey(obj);
	}
	
	public SlicedElement getSlicedElement(EObject obj){
		return this.slicedElements.get(obj);
	}

	public Set<SlicedElement> getSlicedElements() throws UnsupportedOperationException {
		return Collections.unmodifiableSet(new HashSet<SlicedElement>(this.slicedElements.values()));
	}
	
	@SuppressWarnings("unchecked")
	public Collection<EObject> export(){
		for(SlicedElement slicedElement : this.slicedElements.values()){
			for(SlicedReference slicedReference : slicedElement.getSlicedReferences()){
				if (slicedReference.getType().isMany()) {
					if (slicedReference.isInverse()) {
						((Collection<EObject>) getSlicedElement(slicedReference.getTgt()).getCopy()
							.eGet(slicedReference.getType())).add(slicedElement.getCopy());
					} else {
						((Collection<EObject>) slicedElement.getCopy().eGet(slicedReference.getType()))
							.add(this.slicedElements.get(slicedReference.getTgt()).getCopy());
					}
				} else {
					if (slicedReference.isInverse()) {
						this.slicedElements.get(slicedReference.getTgt()).getCopy().eSet(slicedReference.getType(),
								slicedElement.getCopy());
					} else {
						slicedElement.getCopy().eSet(slicedReference.getType(),
								this.slicedElements.get(slicedReference.getTgt()).getCopy());
					}
				}
			}
		}			
		Set<EObject> set = new HashSet<EObject>();
		for(SlicedElement slicedElement : this.slicedElements.values()){
			set.add(slicedElement.getCopy());
		}
		return set;
	}
}
