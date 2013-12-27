package org.sidiff.patching.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.patching.report.OperationExecutionEntry.OperationExecutionKind;
import org.sidiff.patching.validation.IValidationError;

/**
 * Basically a list of patch report entries.
 * 
 */
public class PatchReport {

	/**
	 * Total set of report entries.
	 */
	private List<ReportEntry> entries;

	/**
	 * 
	 */
	PatchReport() {
		entries = new ArrayList<ReportEntry>();
	}

	void operationPassed(OperationInvocation op, Map<ParameterBinding, Object> args) {
		entries.add(new OperationExecutionEntry(op, OperationExecutionKind.PASSED, args));
		// TODO (TK): check for modified args and set state to WARNING
	}

	void operationFailed(OperationInvocation op, Map<ParameterBinding, Object> args, Exception error) {
		entries.add(new OperationExecutionEntry(op, OperationExecutionKind.EXEC_FAILED, args, error));
	}

	void operationSkipped(OperationInvocation op) {
		entries.add(new OperationExecutionEntry(op, OperationExecutionKind.SKIPPED));
	}

	void operationReverted(OperationInvocation op) {
		entries.add(new OperationExecutionEntry(op, OperationExecutionKind.REVERTED));
	}

	void operationRevertFailed(OperationInvocation op, Exception error) {
		entries.add(new OperationExecutionEntry(op, OperationExecutionKind.REVERT_FAILED, error));
	}

	/**
	 * 
	 * @param validationErrors
	 *            the current validation errors.
	 * @return true, if the number of validation errors changed.
	 */
	boolean updateValidationEntries(Collection<IValidationError> validationErrors) {
		ValidationEntry lastValidation = getLastValidationEntry();
		if (lastValidation != null) {
			Collection<IValidationError> previousErrors = getLastValidationEntry().getCurrentValidationErrors();
			if (!previousErrors.equals(validationErrors)) {
				entries.add(new ValidationEntry(previousErrors, validationErrors));
				return true;
			}
		} else {
			entries.add(new ValidationEntry(null, validationErrors));
			return true;
		}

		return false;
	}

	public List<ReportEntry> getEntries() {
		return entries;
	}

	public List<OperationExecutionEntry> getExecutionEntries() {
		ArrayList<OperationExecutionEntry> res = new ArrayList<OperationExecutionEntry>();
		for (ReportEntry entry : entries) {
			if (entry instanceof OperationExecutionEntry) {
				res.add((OperationExecutionEntry) entry);
			}
		}

		return res;
	}

	public List<ValidationEntry> getValidationEntries() {
		ArrayList<ValidationEntry> res = new ArrayList<ValidationEntry>();
		for (ReportEntry entry : entries) {
			if (entry instanceof ValidationEntry) {
				res.add((ValidationEntry) entry);
			}
		}

		return res;
	}

	public ValidationEntry getLastValidationEntry() {
		List<ValidationEntry> validationEntries = getValidationEntries();
		if (!validationEntries.isEmpty()) {
			return validationEntries.get(validationEntries.size() - 1);
		}

		return null;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		for (ReportEntry report : getEntries()) {
			stringBuffer.append(report + "\n");
		}
		return stringBuffer.toString();
	}

}
