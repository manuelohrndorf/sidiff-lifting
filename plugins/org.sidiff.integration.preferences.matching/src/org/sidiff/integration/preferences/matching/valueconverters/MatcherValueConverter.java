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
	public String getValue(IMatcher object) {
		return object.getKey();
	}

	@Override
	public String getLabel(IMatcher object) {
		return object.getName();
	}

	@Override
	public String getDescription(IMatcher object) {
		return object.getDescription();
	}
}
