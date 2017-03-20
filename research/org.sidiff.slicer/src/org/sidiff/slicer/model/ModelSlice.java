package org.sidiff.slicer;

import java.util.Collection;
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

	private Set<SlicedElement> slicedElements;
	
	private Map<EObject,SlicedElement> eObject2SliceElement;
	
	
	public ModelSlice(){
		this.slicedElements = new HashSet<SlicedElement>();
		this.eObject2SliceElement = new HashMap<EObject, SlicedElement>();
	}
	
	public void addSlicedElement(SlicedElement slicedElement){
		slicedElements.add(slicedElement);
		this.eObject2SliceElement.put(slicedElement.getOrigin(), slicedElement);
	}
	
	public boolean contains(EObject origin){
		return eObject2SliceElement.containsKey(origin);
	}
	
	public SlicedElement getSlicedElement(EObject origin){
		return eObject2SliceElement.get(origin);
	}

	public Set<SlicedElement> getSlicedElements() {
		return slicedElements;
	}
	
	public Collection<EObject> export(){
		Set<EObject> set = new HashSet<EObject>();
		for(SlicedElement slicedElement : slicedElements){
			set.add(slicedElement.getCopy());
		}
		return set;
	}
}
