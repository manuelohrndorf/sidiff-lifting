package org.sidiff.patching.validation;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Encapsulates a concrete validation framework.
 * 
 * @author kehrer
 */
public interface IValidationUnit {

	public Collection<IValidationError> validate(Resource resource);
	
}
