package org.sidiff.integration.preferences.valueconverters;

import org.sidiff.common.extension.IExtension;

/**
 * A preference value converter for {@link IExtension}s.
 * @author rmueller
 */
public class ExtensionValueConverter implements IPreferenceValueConverter<IExtension> {

	private static IPreferenceValueConverter<IExtension> instance;

	private ExtensionValueConverter() {
	}

	@Override
	public String getValue(IExtension object) {
		if(object == null) {
			return "";
		}
		return object.getKey();
	}

	@Override
	public String getLabel(IExtension object) {
		if(object == null) {
			return "None";
		}
		return object.getName();
	}

	@Override
	public String getDescription(IExtension object) {
		if(object == null) {
			return null;
		}
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
