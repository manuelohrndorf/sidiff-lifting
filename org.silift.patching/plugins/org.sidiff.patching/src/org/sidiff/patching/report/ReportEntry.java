package org.sidiff.patching.report;

import org.eclipse.emf.common.util.Diagnostic;
import org.sidiff.patching.report.PatchReport.Status;
import org.sidiff.patching.report.PatchReport.Type;

public class ReportEntry {
	private Status status;
	private Type type;
	private String description;
	private Exception exception;
	private Diagnostic diagnostic;

	public ReportEntry(Status status, Type type, String description) {
		this.status = status;
		this.type = type;
		this.description = description;
	}
	
	public ReportEntry(Status status, Type type, Exception exception) {
		this(status, type, exception.getMessage());
		this.exception = exception;
	}
	
	public ReportEntry(Status status, Type type, Diagnostic diagnostic) {
		this(status, type, diagnostic.getMessage());
		this.diagnostic = diagnostic;
	}

	/**
	 * The status of the test
	 * 
	 * @return
	 */
	public Status getStatus() {
		return status;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public Diagnostic getDiagnostic() {
		return diagnostic;
	}

	@Override
	public String toString() {
		return "ReportEntry [status=" + status + ", type=" + type + ", description=" + description + "]";
	}
}