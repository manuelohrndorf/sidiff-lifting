package org.sidiff.slicer.structural.configuration;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

public interface IConstraintResult {

	public boolean getBoolean();
	
	public Collection<EObject> getEObjects();
}
