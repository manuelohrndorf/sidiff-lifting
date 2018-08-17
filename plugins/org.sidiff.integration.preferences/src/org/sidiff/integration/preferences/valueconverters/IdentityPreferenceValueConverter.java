package org.sidiff.integration.preferences.valueconverters;

/**
 * A {@link IPreferenceValueConverter preference value converter} for strings,
 * that directly returns the input as both value and label.
 * @author Robert Müller
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
