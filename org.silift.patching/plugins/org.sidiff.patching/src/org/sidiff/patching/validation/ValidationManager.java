package org.sidiff.patching.validation;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.patching.validation.emf.EMFValidator;
import org.silift.settings.PatchingSettings.ValidationMode;

public class ValidationManager {
	
	private ValidationMode validationMode;
	private IValidator validator;
	private Resource targetResource;
	
	public ValidationManager(ValidationMode validationMode, Resource targetResource) {
		super();
		this.validationMode = validationMode;
		this.targetResource = targetResource;
		this.validator = new EMFValidator();
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
