package org.sidiff.integration.preferences.valueconverters;

/**
 * A generic {@link IPreferenceValueConverter preference value converter}
 * that uses the {@link Object#toString()} method to calculate value and label.
 * @author Robert Müller
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
