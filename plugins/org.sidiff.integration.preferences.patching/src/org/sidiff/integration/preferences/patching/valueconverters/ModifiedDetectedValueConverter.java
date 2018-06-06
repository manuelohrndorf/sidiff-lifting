package org.sidiff.integration.preferences.patching.valueconverters;

import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * @author Robert Müller
 *
 */
public class ModifiedDetectedValueConverter implements IPreferenceValueConverter<IModifiedDetector> {

	@Override
	public String getValue(IModifiedDetector value) {
		if(value == null) {
			return "";
		}
		return value.getKey();
	}

	@Override
	public String getLabel(IModifiedDetector value) {
		if(value == null) {
			return "None";
		}
		return value.getName();
	}
}
