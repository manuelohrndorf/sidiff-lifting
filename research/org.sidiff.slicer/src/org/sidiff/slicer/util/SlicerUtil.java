package org.sidiff.slicer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.modelstorage.EMFStorage;

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
	
	public static void serializeSlicedModel(Collection<EObject> modelSlice, URI uri, boolean multiResources) throws IOException{
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);
		
		for(EObject slicedElement : modelSlice){
			while(slicedElement.eContainer() != null){
				slicedElement = slicedElement.eContainer();
			}
			if(!resource.getContents().contains(slicedElement)){
				resource.getContents().add(slicedElement);
			}
		}
		
		resource.save(Collections.EMPTY_MAP);
	}
	
	public static URI generateSaveURI(URI loadURI){
		String savePath = loadURI.path();
		savePath = savePath.replace(loadURI.lastSegment(), "slice" + File.separator + loadURI.lastSegment());
		
		return EMFStorage.pathToUri(savePath);
	}
}
