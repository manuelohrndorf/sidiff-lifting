package org.sidiff.integration.preferences.matching.valueconverters;

import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.similarities.ISimilarities;

/**
 * 
 * @author Robert Müller
 *
 */
public class SimilaritiesValueConverter implements IPreferenceValueConverter<ISimilarities> {

	@Override
	public String getValue(ISimilarities value) {
		return value.getSimilaritiesServiceID();
	}

	@Override
	public String getLabel(ISimilarities value) {
		return value.getDescription();
	}
}
