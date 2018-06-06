package org.sidiff.integration.preferences.valueconverters;

/**
 * Converts complex types to string values, that can be stored in a preference store,
 * and provides labels for the complex types.
 * @author Robert Müller
 * @param <T> the (complex) type of preference value that this value converter handles
 */
public interface IPreferenceValueConverter<T> {
	String getValue(T value);
	String getLabel(T value);
}
