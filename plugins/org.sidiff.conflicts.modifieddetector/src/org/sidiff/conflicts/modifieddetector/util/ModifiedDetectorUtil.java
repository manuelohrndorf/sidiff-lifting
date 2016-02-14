package org.sidiff.conflicts.modifieddetector.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;

public class ModifiedDetectorUtil {

	public static Set<IModifiedDetector> getAvailableModifiedDetectors(String documentType){
		Set<IModifiedDetector> modifiedDetectors = new HashSet<IModifiedDetector>();
		for(IModifiedDetector iModifiedDetector : getAllAvailableModifiedDetectors()){
			if(iModifiedDetector.canHandle(documentType)){
				modifiedDetectors.add(iModifiedDetector);
			}
		}
		return modifiedDetectors;
	}
	
	public static IModifiedDetector getDefaultAvailableModifiedDetector(String documentType){
		Set<IModifiedDetector> modifiedDetectors = getAvailableModifiedDetectors(documentType);
		assert(!modifiedDetectors.isEmpty()) : "No modified detector found for document type " + documentType;
		IModifiedDetector modifiedDetector = null;
		for(IModifiedDetector iModifiedDetector : modifiedDetectors){
			modifiedDetector = iModifiedDetector;
			break;
		}
		if(modifiedDetector == null){
			modifiedDetector = getGenericModifiedDetector();
		}
		return modifiedDetector;
	}
	
	public static IModifiedDetector getGenericModifiedDetector(){
		return null; //TODO new GenericModifiedDetector;
	}

	public static IModifiedDetector getModifiedDetector(String key){
		IModifiedDetector modifiedDetector= null;
		for(IModifiedDetector iModifiedDetector : getAllAvailableModifiedDetectors()){
			if(iModifiedDetector.getKey().equals(key)){
				modifiedDetector = iModifiedDetector;
				break;
			}
		}
		return modifiedDetector;
	}
	
	private static Set<IModifiedDetector> getAllAvailableModifiedDetectors(){
		Set<IModifiedDetector> availableModifiedDetectors = new HashSet<IModifiedDetector>();
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(IModifiedDetector.EXTENSION_POINT_ID)) {
			try {
				availableModifiedDetectors.add((IModifiedDetector) configurationElement.createExecutableExtension(IModifiedDetector.EXECUTABLE));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return availableModifiedDetectors;
	}
}
