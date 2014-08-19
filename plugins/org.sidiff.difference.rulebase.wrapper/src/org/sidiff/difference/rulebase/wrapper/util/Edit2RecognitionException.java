package org.sidiff.difference.rulebase.wrapper.util;

import org.eclipse.core.runtime.IStatus;

public class Edit2RecognitionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private IStatus status = null;

	public Edit2RecognitionException(Throwable t, IStatus status) {
		super(t);
		
		this.status = status;
	}
	
	public IStatus getStatus(){
		return status;
	}

}
