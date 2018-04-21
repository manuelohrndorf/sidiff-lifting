package org.sidiff.integration.preferences.valueconverters;

/**
 * 
 * @author Robert M�ller
 *
 */
public class EnumPreferenceValueConverter implements IPreferenceValueConverter<Enum<?>> {

	@Override
	public String getValue(Enum<?> value) {
		return value.name();
	}

	@Override
	public String getLabel(Enum<?> value) {
		return value.toString();
	}
}
