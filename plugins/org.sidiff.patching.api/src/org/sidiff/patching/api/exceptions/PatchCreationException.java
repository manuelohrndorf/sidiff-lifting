package org.sidiff.patching.api.exceptions;

import org.sidiff.common.exceptions.SiDiffException;

public class PatchCreationException extends SiDiffException {

	private static final long serialVersionUID = -4080237202046466620L;

	public PatchCreationException(String message) {
		this(message, null);
	}
	
	public PatchCreationException(Throwable exception) {
		super(exception);
	}

	public PatchCreationException(String message, Throwable exception) {
		super(message, "Failed to create patch", exception);
	}
}
