package org.sidiff.integration.preferences.lifting.valueconverters;

import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * @author Robert Müller
 *
 */
public class RecognitionRuleSorterValueConverter implements IPreferenceValueConverter<IRecognitionRuleSorter> {

	@Override
	public String getValue(IRecognitionRuleSorter value) {
		return value.getKey();
	}

	@Override
	public String getLabel(IRecognitionRuleSorter value) {
		return value.getName();
	}
}
