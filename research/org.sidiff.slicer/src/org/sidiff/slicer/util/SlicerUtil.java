package org.sidiff.slicer.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.slicer.ISlicer;

/**
 * 
 * @author cpietsch
 *
 */
public class SlicerUtil {
	
	/**
	 * Get all slicers from the extension registry.
	 * 
	 * @return
	 */
	public static Set<ISlicer> getAllAvailableSlicers() {
		Set<ISlicer> availableSlicers = new HashSet<ISlicer>();
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				ISlicer.EXTENSION_POINT_ID)) {
			try {
				availableSlicers.add((ISlicer) configurationElement.createExecutableExtension("class"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return availableSlicers;
	}

	public static URI generateSaveURI(URI loadURI){
		String savePath = loadURI.path();
		savePath = savePath.replace(loadURI.lastSegment(), "slice" + File.separator + loadURI.lastSegment());
		
		return EMFStorage.pathToUri(savePath);
	}
}
