package org.sidiff.patching.report;

import java.util.*;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.patching.validation.IValidationError;

/**
 * Basically a list of patch report entries.
 *
 */
public class PatchReport {

	/**
	 * Total set of report entries.
	 */
	private List<ReportEntry> entries = new ArrayList<>();

	void operationPassed(OperationInvocation op, Map<ParameterBinding, Object> inArgs, Map<ParameterBinding, Object> outArgs) {
		entries.add(new OperationExecutionEntry(op, OperationExecutionKind.PASSED, inArgs, outArgs));
		// TODO (TK): check for modified args and set state to WARNING
	}

	void operationFailed(OperationInvocation op, Map<ParameterBinding, Object> inArgs, Exception error) {
		entries.add(new OperationExecutionEntry(op, OperationExecutionKind.EXEC_FAILED, inArgs, error));
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
		return Collections.unmodifiableList(entries);
	}

	public List<OperationExecutionEntry> getExecutionEntries() {
		List<OperationExecutionEntry> res = new ArrayList<>();
		for (ReportEntry entry : entries) {
			if (entry instanceof OperationExecutionEntry) {
				res.add((OperationExecutionEntry) entry);
			}
		}
		return res;
	}

	public List<ValidationEntry> getValidationEntries() {
		List<ValidationEntry> res = new ArrayList<>();
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
		StringBuilder builder = new StringBuilder();
		for (ReportEntry report : entries) {
			builder.append(report).append("\n");
		}
		return builder.toString();
	}
}
