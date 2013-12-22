package org.sidiff.patching.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.sidiff.difference.asymmetric.OperationInvocation;

/**
 * Basically a list of patch report entries.
 * 
 */
public class PatchReport {

	public enum Status {
		PASSED, SKIPPED, FAILED, WARNING;
	}

	public enum Type {
		PARAMETER("Parameter"), EXECUTION("Execution"), VALIDATION("Validation"), MODIFICATION("Modification");

		private String type;

		Type(String type) {
			this.type = type;
		}

		public String toString() {
			return type;
		}
	}

	private Collection<ReportEntry> parameterEntries;
	private Collection<ReportEntry> executionEntries;
	private Collection<ReportEntry> validationEntries;

	/**
	 * 
	 */
	public PatchReport() {
		parameterEntries = new ArrayList<ReportEntry>();
		executionEntries = new ArrayList<ReportEntry>();
		validationEntries = new ArrayList<ReportEntry>();
	}

	public void addParameterEntry(ReportEntry reportEntry) {
		parameterEntries.add(reportEntry);
	}
	
	public void addExecutionEntry(ReportEntry reportEntry) {
		parameterEntries.add(reportEntry);
	}
	
	public void addValidationEntry(ReportEntry reportEntry) {
		parameterEntries.add(reportEntry);
	}
	
	public List<ReportEntry> getEntries() {
		ArrayList<ReportEntry> res = new ArrayList<ReportEntry>();

		res.addAll(parameterEntries);
		res.addAll(executionEntries);
		res.addAll(validationEntries);

		return res;
	}

	public List<ReportEntry> getEntries(Type type) {
		List<ReportEntry> res = new ArrayList<ReportEntry>();

		for (ReportEntry entry : getEntries()) {
			if (entry.getType() == type) {
				res.add(entry);
			}
		}

		return res;
	}

	public List<ReportEntry> getEntries(Type type, Status status) {
		List<ReportEntry> res = new ArrayList<ReportEntry>();

		for (ReportEntry entry : getEntries()) {
			if (entry.getType() == type && entry.getStatus() == status) {
				res.add(entry);
			}
		}

		return res;
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
