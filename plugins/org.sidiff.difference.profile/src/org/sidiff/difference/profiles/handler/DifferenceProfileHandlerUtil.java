package org.sidiff.difference.profiles.handler;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;


public class DifferenceProfileHandlerUtil {
	
	public static IDifferenceProfileHandler getDefaultDifferenceProfileHandler(String documentType){
	
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(IDifferenceProfileHandler.extensionPointID)) {
			try {
				IDifferenceProfileHandler dphExtension = (IDifferenceProfileHandler) configurationElement.createExecutableExtension("profile_handler");
				if (documentType.equals(dphExtension.getBaseType())) {
					return dphExtension;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
