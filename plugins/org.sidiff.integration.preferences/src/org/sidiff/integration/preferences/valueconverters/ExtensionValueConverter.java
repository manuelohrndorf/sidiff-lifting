package org.sidiff.integration.preferences.valueconverters;

import org.sidiff.common.extension.IExtension;

public class ExtensionValueConverter implements IPreferenceValueConverter<IExtension> {

	private static IPreferenceValueConverter<IExtension> instance;
	
	private ExtensionValueConverter() {
	}
	
	@Override
	public String getValue(IExtension object) {
		return object.getKey();
	}

	@Override
	public String getLabel(IExtension object) {
		return object.getName();
	}

	@Override
	public String getDescription(IExtension object) {
		return object.getDescription().orElse(null);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IExtension> IPreferenceValueConverter<T> getInstance() {
		if(instance == null) {
			instance = new ExtensionValueConverter();
		}
		return (IPreferenceValueConverter<T>)instance;
	}
}
