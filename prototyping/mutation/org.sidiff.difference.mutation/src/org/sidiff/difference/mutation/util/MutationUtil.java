package org.sidiff.difference.mutation.util;

import java.io.File;

import org.eclipse.emf.ecore.resource.Resource;
import org.silift.common.util.emf.EMFStorage;

public class MutationUtil {
	
	public static String getResourceName(Resource model){
		//Try to resolve absolute path
		String modelFile = model.getURI().toFileString();
		
		//In case of relative uri
		if(modelFile == null)
			modelFile = EMFStorage.uriToPath(model.getURI());
		
		String modelName = modelFile.substring(modelFile.lastIndexOf(File.separator) + 1, modelFile.length());
		return modelName;		
	}

}
