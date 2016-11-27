package org.sidiff.repair.casestudy.gmf.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;

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
			if ((childDiagnostic.getSeverity() == Diagnostic.ERROR)
					|| (childDiagnostic.getSeverity() == Diagnostic.WARNING)) {

				res.add(new EMFDiagnosticAdapter(childDiagnostic));
			}
		}

		// Generische Multiplicity Constraint Validierung
		// (wird von der EMF Validierung nicht überprüft):
		for (Iterator<EObject> iterator = resource.getAllContents(); iterator.hasNext();) {
			EObject object = iterator.next();
			for (EReference referenceType : object.eClass().getEAllReferences()) {
				if (referenceType.isMany()) {
					Collection<?> refObjs = (Collection<?>) object.eGet(referenceType);
					if (referenceType.getLowerBound() > 0 && refObjs.size() < referenceType.getLowerBound()) {
						ValidationError error = new ValidationError(null, "Lower bound violation for referenceType "
								+ referenceType.getName() + ": " + refObjs.size() + " < "
								+ referenceType.getLowerBound(), object.toString(), IValidationError.ERROR);
						res.add(error);
					}
					if (referenceType.getUpperBound() > 0 && refObjs.size() > referenceType.getUpperBound()) {
						ValidationError error = new ValidationError(null, "Upper bound violation for referenceType "
								+ referenceType.getName() + ": " + refObjs.size() + " > "
								+ referenceType.getUpperBound(), object.toString(), IValidationError.ERROR);
						res.add(error);
					}
				}
			}
		}

		return res;
	}
}
