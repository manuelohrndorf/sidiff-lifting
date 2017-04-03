package org.sidiff.slicer.model;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * 
 * @author cpietsch
 *
 */
public class SlicedReference {
	
	private Set<EObject> tgts;
	
	private EReference type;
	
	private boolean inverse;
	
	private boolean boundary;
	
	public SlicedReference(EReference type, Set<EObject> tgts, boolean inverse, boolean boundary){
		this.tgts = tgts;
		this.type = type;
		this.inverse = inverse;
		this.boundary = boundary;
	}

	public Set<EObject> getTgts() {
		return this.tgts;
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
