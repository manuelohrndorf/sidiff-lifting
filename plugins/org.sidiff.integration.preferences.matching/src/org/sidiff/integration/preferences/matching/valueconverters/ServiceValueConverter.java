package org.sidiff.integration.preferences.matching.valueconverters;

import org.sidiff.common.util.RegExUtil;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.service.IService;

/**
 * 
 * @author Robert Müller
 *
 */
public class ServiceValueConverter implements IPreferenceValueConverter<IService> {

	@Override
	public String getValue(IService object) {
		return object.getServiceID();
	}

	@Override
	public String getLabel(IService object) {
		// Split the CamelCase class name
		return RegExUtil.Patterns.SPLIT_CAMEL_CASE.get().matcher(object.getClass().getSimpleName()).replaceAll(" ");
	}

	@Override
	public String getDescription(IService object) {
		return object.getDescription();
	}
}
	