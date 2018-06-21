package org.sidiff.slicer.structural;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.util.SlicerUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class StructureBasedSlicerUtil extends SlicerUtil{
	
	public static URI generateSaveURI(URI loadURI, SlicingConfiguration config){
		String savePath = loadURI.path();
		savePath = savePath.replace(loadURI.lastSegment(), config.getName() + "_" + config.getSlicingMode() + File.separator + loadURI.lastSegment());
		
		return EMFStorage.pathToUri(savePath);
	}
}
