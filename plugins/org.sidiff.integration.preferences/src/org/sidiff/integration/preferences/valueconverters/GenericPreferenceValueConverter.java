package org.sidiff.integration.preferences.valueconverters;

/**
 * 
 * @author Robert Müller
 *
 */
public class GenericPreferenceValueConverter implements IPreferenceValueConverter<Object> {

	@Override
	public String getValue(Object value) {
		return value.toString();
	}

	@Override
	public String getLabel(Object value) {
		return value.toString();
	}
}
