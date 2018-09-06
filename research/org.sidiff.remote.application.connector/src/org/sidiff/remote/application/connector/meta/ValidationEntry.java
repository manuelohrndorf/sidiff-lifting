package org.sidiff.remote.application.connector.meta;

public class ValidationEntry {

	private ESeverity severity;
	
	private String message;
	
	private Object violator;

	public ValidationEntry(ESeverity severity, String message, Object violator) {
		super();
		this.severity = severity;
		this.message = message;
		this.violator = violator;
	}
	
	public ESeverity getSeverity() {
		return severity;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Object getViolator() {
		return violator;
	}
	
}
