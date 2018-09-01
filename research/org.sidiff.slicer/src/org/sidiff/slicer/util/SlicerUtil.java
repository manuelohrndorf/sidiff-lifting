package org.sidiff.slicer.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

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

	public static URI generateSaveURI(URI loadURI){
		String savePath = loadURI.path();
		savePath = savePath.replace(loadURI.lastSegment(), "slice" + File.separator + loadURI.lastSegment());
		return EMFStorage.pathToUri(savePath);
	}

	public static void serializeModelSlice(URI outputURI, Collection<EObject> modelSlice) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(outputURI);
		resource.getContents().addAll(modelSlice);
		resource.save(Collections.EMPTY_MAP);
	}
}
