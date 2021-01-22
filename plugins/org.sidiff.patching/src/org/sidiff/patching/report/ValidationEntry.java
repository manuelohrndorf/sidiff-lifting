package org.sidiff.patching.report;

import java.util.ArrayList;
import java.util.Collection;

import org.sidiff.patching.validation.IValidationError;

public class ValidationEntry extends ReportEntry {

	private Collection<IValidationError> previousErrors;
	private Collection<IValidationError> currentErrors;

	public ValidationEntry(Collection<IValidationError> previousDiagnostics, Collection<IValidationError> currentDiagnostics) {
		super();

		this.previousErrors = previousDiagnostics;
		this.currentErrors = currentDiagnostics;
	}

	@Override
	public String getDescription() {
		if (previousErrors == null){
			return "No. of validation errors: " + currentErrors.size();
		}
		return "No. of validation errors: " + previousErrors.size() + " -> " + currentErrors.size();
	}

	public Collection<IValidationError> getCurrentValidationErrors() {
		return currentErrors;
	}

	public Collection<IValidationError> getNewValidationErrors() {
		Collection<IValidationError> res = new ArrayList<>();
		if(currentErrors != null){
			for (IValidationError e : currentErrors) {
				if (previousErrors != null && !previousErrors.contains(e)) {
					res.add(e);
				}
			}
		}
		return res;
	}

	public Collection<IValidationError> getRemovedValidationErrors() {
		Collection<IValidationError> res = new ArrayList<>();
		if(previousErrors != null){
			for (IValidationError e : previousErrors) {
				if (!currentErrors.contains(e)) {
					res.add(e);
				}
			}
		}

		return res;
	}
}
