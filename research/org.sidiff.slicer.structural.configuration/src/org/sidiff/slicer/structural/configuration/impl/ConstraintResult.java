package org.sidiff.slicer.structural.configuration.impl;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.slicer.structural.configuration.IConstraintResult;

public class ConstraintResult implements IConstraintResult{

	private boolean b;
	
	private Collection<EObject> eObjects;
	
	public ConstraintResult(Boolean b, Collection<EObject> eObjects) {
		this.b = b;
		this.eObjects = eObjects;
	}
	
	@Override
	public boolean getBoolean() {
		return b;	
	}

	@Override
	public Collection<EObject> getEObjects() {
		return eObjects;
	}

}
