package org.sidiff.integration.preferences.matching.valueconverters;

import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.matcher.IMatcher;

/**
 * 
 * @author Robert Müller
 *
 */
public class MatcherValueConverter implements IPreferenceValueConverter<IMatcher> {

	@Override
	public String getValue(IMatcher value) {
		return value.getKey();
	}

	@Override
	public String getLabel(IMatcher value) {
		return value.getName();
	}
}
