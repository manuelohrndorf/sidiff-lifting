package org.sidiff.remote.common.settings;

import java.io.Serializable;

public class ValidationProperties implements Serializable {

	public static final String VALIDATE_MODELS = "Validate Models";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1658037404228195830L;

	private SingleSelectionRemoteApplicationProperty<Boolean> validateModels;
	
	public ValidationProperties(SingleSelectionRemoteApplicationProperty<Boolean> validateModels) {
		super();
		this.validateModels = validateModels;
	}

	public SingleSelectionRemoteApplicationProperty<Boolean> getValidateModels() {
		return validateModels;
	}

	@Override
	public String toString() {
		return validateModels.toString();
	}
}
