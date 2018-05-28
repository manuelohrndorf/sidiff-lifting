package org.sidiff.integration.preferences.patching.valueconverters;

import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

/**
 * 
 * @author Robert Müller
 *
 */
public class SymbolicLinkHandlerValueConverter implements IPreferenceValueConverter<ISymbolicLinkHandler> {

	@Override
	public String getValue(ISymbolicLinkHandler value) {
		if(value == null) {
			return "";
		}
		return value.getKey();
	}

	@Override
	public String getLabel(ISymbolicLinkHandler value) {
		if(value == null) {
			return "Disable symbolic links";
		}
		return value.getName();
	}
}
