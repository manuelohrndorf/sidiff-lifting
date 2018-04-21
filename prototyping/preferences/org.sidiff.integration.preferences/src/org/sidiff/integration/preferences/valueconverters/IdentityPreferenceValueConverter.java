package org.sidiff.integration.preferences.valueconverters;

/**
 * 
 * @author Robert Müller
 *
 */
public class IdentityPreferenceValueConverter implements IPreferenceValueConverter<String> {

	@Override
	public String getValue(String value) {
		return value;
	}

	@Override
	public String getLabel(String value) {
		return value;
	}
}
