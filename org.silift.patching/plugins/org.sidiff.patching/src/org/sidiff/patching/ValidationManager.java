package org.sidiff.patching;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.patching.PatchEngine.ValidationMode;
import org.sidiff.patching.test.EMFValidationTestUnit;

public class ValidationManager {
	
	private ValidationMode validationMode;
	private IValidationUnit validator;
	private Resource targetResource;
	
	private Collection<Diagnostic> initialErrors;
	private Collection<Diagnostic> previousErrors;
	private Collection<Diagnostic> currentErrors;
	
	public ValidationManager(ValidationMode validationMode, Resource targetResource) {
		super();
		this.validationMode = validationMode;
		this.targetResource = targetResource;
		this.validator = new EMFValidationTestUnit();
		
		initialErrors = new ArrayList<Diagnostic>();
		initialErrors.addAll(validator.getErrors(validator.validate(targetResource)));
		previousErrors = new ArrayList<Diagnostic>();
		previousErrors.addAll(initialErrors);
	}
	
	public Collection<Diagnostic> performIncrementalValidation(){
		assert (validationMode == ValidationMode.ITERATIVE);
	
		currentErrors = new ArrayList<Diagnostic>();
		currentErrors.addAll(validator.getErrors(validator.validate(targetResource)));
		
		// get new errors
		Collection<Diagnostic> newErrors = getNewDiagnostics(currentErrors, previousErrors);
		
		// remember current errors
		previousErrors = currentErrors;
		
		// return the new errors
		return newErrors;
	}

	public void performCompleteValidation(){
		assert (validationMode != ValidationMode.ITERATIVE && validationMode != ValidationMode.NO);
		
		currentErrors = new ArrayList<Diagnostic>();
		currentErrors.addAll(validator.getErrors(validator.validate(targetResource)));
	}
	
	public ValidationMode getValidationMode() {
		return validationMode;
	}

	public void setValidationMode(ValidationMode validationMode) {
		this.validationMode = validationMode;
	}
	
	private Collection<Diagnostic> getNewDiagnostics(Collection<Diagnostic> newD, Collection<Diagnostic> oldD){
		Collection<Diagnostic> res = new ArrayList<Diagnostic>();
		for (Diagnostic d : newD) {
			if (!oldD.contains(d)) {
				res.add(d);
			}
		}
		
		return res;
	}
	
}
