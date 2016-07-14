package org.sidiff.difference.testcase.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * 
 * @author cpietsch
 *
 */
public class StorageUtil {

	public static Module loadHenshinModule(String path){
		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet();

		// Load the module:
		Module module = resourceSet.getModule(path, false);
		
		return module;
	}
	
	public static void serializeModel(String path, Collection<EObject> eObjects){
		 ResourceSet resSet = new ResourceSetImpl();
		
		 Resource resource = resSet.createResource(URI.createFileURI(path));
		 
		 resource.getContents().addAll(eObjects);
		 try {
			resource.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<String> getFiles(String root){
		List<String> files = new LinkedList<String>();
		File file = new File(root);
		if(file.isDirectory()){
			for(File nestedFile : file.listFiles()){
				files.addAll(getFiles(nestedFile.getPath()));
			}
		}else{
			files.add(root);
		}
		return files;
	}
}
