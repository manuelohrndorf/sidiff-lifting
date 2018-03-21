package org.sidiff.remote.application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sidiff.remote.common.Session;

/**
 * 
 * @author cpietsch
 *
 */
public class ModelIndexer {
	
	private Session session;
	
	private File session_folder;
	
	private Set<String> file_ext;
	
	private List<File> model_files;
	
	public ModelIndexer(Session session, File session_folder) {
		this.session = session;
		this.session_folder = session_folder;
		this.file_ext = new HashSet<String>();
		this.file_ext.add("ecore");
		this.file_ext.add("uml");
		this.model_files = new ArrayList<File>();
	}
	
	public void index() {
		this.model_files.clear();
		this.model_files.addAll(searchModelFiles(session_folder));
	}
	
	private List<File> searchModelFiles(File parent){
		List<File> files = new ArrayList<File>();
		for(File file : parent.listFiles()) {
			if(file.isDirectory()) {
				files.addAll(searchModelFiles(file));
			}else if(file_ext.contains(getFileExtension(file))) {
				files.add(file);
			}
		}
		
		return files;
	}
	
	private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
        	return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        else return "";
    }
	
	public List<File> getModel_files() {
		return model_files;
	}
}
