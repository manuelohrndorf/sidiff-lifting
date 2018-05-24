package org.sidiff.integration.preferences.difference.valueconverters;

import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * @author Robert Müller
 *
 */
public class TechnicalDifferenceBuilderValueConverter implements IPreferenceValueConverter<ITechnicalDifferenceBuilder> {

	@Override
	public String getValue(ITechnicalDifferenceBuilder value) {
		return value.getKey();
	}

	@Override
	public String getLabel(ITechnicalDifferenceBuilder value) {
		return value.getName();
	}
}