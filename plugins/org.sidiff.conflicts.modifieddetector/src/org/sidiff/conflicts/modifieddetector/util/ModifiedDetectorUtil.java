package org.sidiff.conflicts.modifieddetector.util;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;

public class ModifiedDetectorUtil {

	/**
	 * Returns the available modified detector for the given documentType.
	 * If no convenient detector is found, null will be returned.
	 * 
	 * @param documentType
	 * @return a modified detector found for this document type
	 */
	public static IModifiedDetector getAvailableModifiedDetector(String documentType){
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(IModifiedDetector.EXTENSION_POINT_ID)) {
			try {
				IModifiedDetector modDetectExtension = (IModifiedDetector) configurationElement.createExecutableExtension(IModifiedDetector.EXECUTABLE);
				if (documentType.equals(configurationElement.getAttribute(IModifiedDetector.DOCUMENT_TYPE))) {
					return modDetectExtension;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}	

}
