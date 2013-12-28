package org.sidiff.patching.validation.emf;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.sidiff.patching.validation.IValidationError;
import org.sidiff.patching.validation.IValidator;

/**
 * Delegates the validation to the EMF validation framework. Currently the only
 * implementation of an IValidator.
 * 
 * @author kehrer
 */
public class EMFValidator implements IValidator {

	@Override
	public Collection<IValidationError> validate(Resource resource) {
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(resource.getContents().get(0));

		Collection<IValidationError> res = new ArrayList<IValidationError>();
		for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
			switch (childDiagnostic.getSeverity()) {
			case Diagnostic.ERROR:
				res.add(new EMFDiagnosticAdapter(childDiagnostic));
				break;
			}
		}
		return res;
	}

}
