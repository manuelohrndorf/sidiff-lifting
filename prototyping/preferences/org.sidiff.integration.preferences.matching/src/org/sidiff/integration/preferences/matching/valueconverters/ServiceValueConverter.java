package org.sidiff.integration.preferences.matching.valueconverters;

import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.service.IService;

/**
 * 
 * @author Robert Müller
 *
 */
public class ServiceValueConverter implements IPreferenceValueConverter<IService> {

	@Override
	public String getValue(IService value) {
		return value.getServiceID();
	}

	@Override
	public String getLabel(IService value) {
		return value.getClass().getSimpleName();
	}
}
