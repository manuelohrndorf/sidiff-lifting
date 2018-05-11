package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.ErrorReport;

/**
 * 
 * @author cpietsch
 *
 */
public class ErrorReply extends ReplyCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 463645131459187975L;

	/**
	 * 
	 */
	private ErrorReport error_report;
	
	/**
	 * 
	 * @param error_msg
	 */
	public ErrorReply(ErrorReport error_report) {
		super(null);
		this.error_report = error_report;
	}
	
	/**
	 * 
	 * @return
	 */
	public ErrorReport getErrorReport() {
		return this.error_report;
	}
}
