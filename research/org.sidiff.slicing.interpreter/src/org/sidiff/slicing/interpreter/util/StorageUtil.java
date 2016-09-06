package org.sidiff.slicing.interpreter.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.slicing.configuration.SlicingConfiguration;
import org.sidiff.slicing.slicingmodel.Slicing;

public class StorageUtil {

	public static void serializeSlicedModel(Slicing slicing, URI uri, boolean multiResources) throws IOException{
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);
		
		for(EObject slicedContextElement : slicing.getSlicedContextElements()){
			while(slicedContextElement.eContainer() != null){
				slicedContextElement = slicedContextElement.eContainer();
			}
			if(!resource.getContents().contains(slicedContextElement)){
				resource.getContents().add(slicedContextElement);
			}
		}
		
		resource.save(Collections.EMPTY_MAP);
	}
	
	public static URI generateSaveURI(URI loadURI, SlicingConfiguration config){
		String savePath = loadURI.path();
		savePath = savePath.replace(loadURI.lastSegment(), config.getName() + "_" + config.getSlicingMode() + File.separator + loadURI.lastSegment());
		
		return EMFStorage.pathToUri(savePath);
	}
}
