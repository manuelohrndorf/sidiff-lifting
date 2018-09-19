package org.sidiff.integration.preferences.valueconverters;

/**
 * <p>Converts complex types to string values, that can be stored in a preference store,
 * and provides labels and optionally descriptions for the user interface.</p>
 * @author Robert Müller
 * @param <T> the (complex) type of preference value that this value converter handles
 */
public interface IPreferenceValueConverter<T> {

	/**
	 * <p>Returns a serializable value for the object that can be stored in a preference store.</p>
	 * <p>If the parameter is <code>null</code>, a serializable value for the empty value should be returned.</p>
	 * @param object the object, may be <code>null</code> if the empty value is allowed
	 * @return serializable value of the object, not <code>null</code>
	 */
	String getValue(T object);

	/**
	 * <p>Returns a label to show in the user interface for the object.</p>
	 * <p>If the parameter is <code>null</code>, a label for the empty value should be returned.</p>
	 * @param object the object, may be <code>null</code> if the empty value is allowed
	 * @return label for the object, not <code>null</code>
	 */
	String getLabel(T object);

	/**
	 * <p>Returns a description to show in the user interface for the object.</p>
	 * <p>If the parameter is <code>null</code>, a description of the empty value should be returned.</p>
	 * <p>The default implementation of this method returns <code>null</code>.</p>
	 * @param object the object, may be <code>null</code> if the empty value is allowed
	 * @return description for the object, may be <code>null</code> if none is available
	 */
	default String getDescription(T object) { return null; };
}
