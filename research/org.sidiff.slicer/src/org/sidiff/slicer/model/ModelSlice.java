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
		return slicedElements.containsKey(obj);
	}
	
	public SlicedElement getSlicedElement(EObject obj){
		return slicedElements.get(obj);
	}

	public final Set<SlicedElement> getSlicedElements() {
		return Collections.unmodifiableSet(new HashSet<SlicedElement>(slicedElements.values()));
	}
	
	@SuppressWarnings("unchecked")
	public Collection<EObject> export(){
		for(SlicedElement slicedElement : slicedElements.values()){
			for(SlicedReference slicedReference : slicedElement.getSlicedReferences()){
				if (slicedReference.getType().isMany()) {
					for(EObject tgt : slicedReference.getTgts()){
						if (slicedReference.isInverse()) {
							((Collection<EObject>) getSlicedElement(tgt).getCopy()
								.eGet(slicedReference.getType())).add(slicedElement.getCopy());
						} else {
							((Collection<EObject>) slicedElement.getCopy().eGet(slicedReference.getType()))
								.add(slicedElements.get(tgt).getCopy());
						}
					}
				} else {
					if (slicedReference.isInverse()) {
						slicedElements.get(slicedReference.getTgts().get(0)).getCopy().eSet(slicedReference.getType(),
								slicedElement.getCopy());
					} else {
						slicedElement.getCopy().eSet(slicedReference.getType(),
								slicedElements.get(slicedReference.getTgts().get(0)).getCopy());
					}
				}
			}
		}			
		Set<EObject> set = new HashSet<EObject>();
		for(SlicedElement slicedElement : slicedElements.values()){
			set.add(slicedElement.getCopy());
		}
		return set;
	}
}
