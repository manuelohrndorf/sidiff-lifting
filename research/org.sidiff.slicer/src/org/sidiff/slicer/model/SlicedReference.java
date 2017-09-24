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
		return this.tgt;
	}

	public EReference getType() {
		return this.type;
	}
	
	public boolean isInverse() {
		return this.inverse;
	}
	
	public boolean isBoundary() {
		return this.boundary;
	}
}
