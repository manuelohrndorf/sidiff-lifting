package org.sidiff.patching.validation;

import java.util.Collection;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchEngine.ValidationMode;
import org.sidiff.patching.validation.emf.EMFValidationTestUnit;

public class ValidationManager {
	
	private ValidationMode validationMode;
	private IValidationUnit validator;
	private Resource targetResource;
	
	public ValidationManager(ValidationMode validationMode, Resource targetResource) {
		super();
		this.validationMode = validationMode;
		this.targetResource = targetResource;
		this.validator = new EMFValidationTestUnit();
	}
	
	public ValidationMode getValidationMode() {
		return validationMode;
	}

	public void setValidationMode(ValidationMode validationMode) {
		this.validationMode = validationMode;
	}
	
	public Collection<IValidationError> validateTargetModel(){
		return validator.validate(targetResource);
	}
	
}
