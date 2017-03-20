package org.sidiff.slicer.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * 
 * @author cpietsch
 *
 */
public class SlicedReference {

	
	private EObject tgt;
	
	private EReference type;
	
	private boolean inverse;
	
	private boolean boundary;
	
	public SlicedReference(EReference type, EObject tgt, boolean inverse, boolean boundary){
		this.tgt = tgt;
		this.type = type;
		this.inverse = inverse;
		this.boundary = boundary;
	}

	public EObject getTgt() {
		return tgt;
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
