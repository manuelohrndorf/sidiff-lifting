package org.sidiff.slicer.model;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * 
 * @author cpietsch
 *
 */
public class SlicedReference {
	
	private List<EObject> tgts;
	
	private EReference type;
	
	private boolean inverse;
	
	private boolean boundary;
	
	public SlicedReference(EReference type, List<EObject> tgts, boolean inverse, boolean boundary){
		this.tgts = tgts;
		this.type = type;
		this.inverse = inverse;
		this.boundary = boundary;
	}

	public List<EObject> getTgts() {
		return tgts;
	}

	public EReference getType() {
		return type;
	}
	
	public boolean isInverse() {
		return inverse;
	}
	
	public boolean isBoundary() {
		return boundary;
	}
}
