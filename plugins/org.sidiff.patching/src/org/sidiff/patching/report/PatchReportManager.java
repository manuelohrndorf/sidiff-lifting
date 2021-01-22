package org.sidiff.patching.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.patching.validation.IValidationError;
import org.sidiff.patching.validation.ValidationMode;

public class PatchReportManager {

	private List<IPatchReportListener> listeners;
	private List<PatchReport> reports;
	private ValidationMode validationMode;

	public PatchReportManager(ValidationMode mode) {
		reports = new ArrayList<>();
		listeners = new ArrayList<>();
		validationMode = mode;
	}

	public void startPatchApplication() {
		reports.add(new PatchReport());
		//notifyReportChanged();
	}

	public void cancelPatchApplication(){
		reports.remove(reports.size()-1);
	}

	public void finishPatchApplication() {
		int index = reports.size()-1;
		PatchReport patchReport = reports.get(index);
		if(validationMode == ValidationMode.NO_VALIDATION && patchReport.getEntries().size() > 0
				|| validationMode != ValidationMode.NO_VALIDATION && patchReport.getEntries().size() > 1
				|| validationMode != ValidationMode.NO_VALIDATION && patchReport.updateValidationEntries(patchReport.getLastValidationEntry().getCurrentValidationErrors())) {
			notifyPushReport(index);
		} else {
			reports.remove(index);
		}
		notifyReportChanged();
	}

	public void addPatchReportListener(IPatchReportListener listener) {
		listeners.add(listener);
	}

	public void removePatchReportListener(IPatchReportListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Returns the patch report of the last patch application. If the patch has
	 * not been applied yet, this method returns null.
	 *
	 * @return
	 */
	public PatchReport getLastReport() {
		if (!reports.isEmpty()) {
			return reports.get(reports.size() - 1);
		}
		return null;
	}

	public PatchReport get(int i){
		return reports.get(i);
	}

	public List<PatchReport> getReports() {
		return reports;
	}

	public void operationPassed(OperationInvocation op, Map<ParameterBinding, Object> inArgs, Map<ParameterBinding, Object> outArgs) {
		getLastReport().operationPassed(op, inArgs, outArgs);
		notifyReportChanged();
	}

	public void operationExecFailed(OperationInvocation op, Map<ParameterBinding, Object> inArgs, Exception error) {
		getLastReport().operationFailed(op, inArgs, error);
		notifyReportChanged();
	}

	public void operationReverted(OperationInvocation op) {
		getLastReport().operationReverted(op);
		notifyReportChanged();
	}

	public void operationRevertFailed(OperationInvocation op, Exception error) {
		getLastReport().operationRevertFailed(op, error);
		notifyReportChanged();
	}

	/**
	 *
	 * @param validationErrors
	 *            the current validation errors.
	 * @return true, if the number of validation errors changed.
	 */
	public boolean updateValidationEntries(Collection<IValidationError> validationErrors) {
		boolean res = getLastReport().updateValidationEntries(validationErrors);
		if (res){
			notifyReportChanged();
		}
		return res;
	}

	private void notifyReportChanged() {
		for (IPatchReportListener listener : listeners) {
			listener.reportChanged();
		}
	}

	private void notifyPushReport(int index){
		for(IPatchReportListener listener : listeners) {
			listener.pushReport(index);
		}
	}
}
