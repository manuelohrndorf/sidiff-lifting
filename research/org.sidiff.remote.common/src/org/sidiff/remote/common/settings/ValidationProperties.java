package org.sidiff.remote.common.settings;

import java.io.Serializable;

public class ValidationProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1658037404228195830L;

	private RemoteApplicationProperty<Boolean> validateModels;
	
	public ValidationProperties(RemoteApplicationProperty<Boolean> validateModels) {
		super();
		this.validateModels = validateModels;
	}

	public RemoteApplicationProperty<Boolean> getValidateModels() {
		return validateModels;
	}
}
