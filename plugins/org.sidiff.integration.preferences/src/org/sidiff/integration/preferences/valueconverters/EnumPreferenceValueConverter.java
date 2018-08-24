package org.sidiff.integration.preferences.valueconverters;

/**
 * A {@link IPreferenceValueConverter preference value converter} for enum types
 * that uses {@link Enum#name()} as value and {@link Object#toString()} as label.
 * @author Robert Müller
 * @param <E> the enum type that this value converter handles
 */
public class EnumPreferenceValueConverter<E extends Enum<E>> implements IPreferenceValueConverter<E> {

	@Override
	public String getValue(E value) {
		return value.name();
	}

	@Override
	public String getLabel(E value) {
		return value.toString();
	}
}
