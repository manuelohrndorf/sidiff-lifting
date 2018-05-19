package org.sidiff.remote.application.connector.exception;

import org.sidiff.remote.common.ErrorReport;

public class RemoteApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6145943630625167725L;
	
	private ErrorReport errorReport;
	
	public RemoteApplicationException(ErrorReport errorReport) {
		this.errorReport = errorReport;
	}
	
	@Override
	public String getMessage() {
		return this.errorReport.getMessage();
	}

}
