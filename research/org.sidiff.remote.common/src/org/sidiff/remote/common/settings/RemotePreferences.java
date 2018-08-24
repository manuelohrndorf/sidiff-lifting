package org.sidiff.remote.common.settings;

import java.io.Serializable;

public class RemotePreferences implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -856118407232390881L;
	
	private GeneralProperties generalProperties;
	
	private ExtractionProperties extractionProperties;
	
	private ValidationProperties validationProperties;

	public RemotePreferences(GeneralProperties generalProperties, ExtractionProperties extractionProperties,
			ValidationProperties validationProperties) {
		super();
		this.generalProperties = generalProperties;
		this.extractionProperties = extractionProperties;
		this.validationProperties = validationProperties;
	}

	public GeneralProperties getGeneralProperties() {
		return generalProperties;
	}

	public ExtractionProperties getExtractionProperties() {
		return extractionProperties;
	}

	public ValidationProperties getValidationProperties() {
		return validationProperties;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("RemotePreferences[");
		b.append(generalProperties.toString()).append(", ");
		b.append(extractionProperties.toString()).append(", ");
		b.append(validationProperties.toString()).append("]");
		return b.toString();
	}
}
