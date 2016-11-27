package org.sidiff.repair.casestudy.gmf.validation;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Encapsulates a concrete validation framework.
 * 
 * @author kehrer
 */
public interface IValidator {

	public Collection<IValidationError> validate(Resource resource);
	
}
