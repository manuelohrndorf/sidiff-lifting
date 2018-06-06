package org.sidiff.integration.preferences.valueconverters;

/**
 * 
 * @author Robert M�ller
 *
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
