package org.sidiff.remote.application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author cpietsch
 *
 */
public class ModelIndexer {
	
	private File user_folder;
	
	private Set<String> file_ext;
	
	private Map<String, File> files;
	
	public ModelIndexer(File user_folder) {
		this.user_folder = user_folder;
		this.file_ext = new HashSet<String>();
		this.file_ext.add("ecore");
		this.file_ext.add("uml");
		this.files = new HashMap<String, File>();
	}
	
	public void index() {
		this.files.clear();
		this.files.putAll(searchModelFiles(user_folder));
	}
	
	private Map<String, File> searchModelFiles(File parent){
		Map<String, File> files = new HashMap<String, File>();
		for(File file : parent.listFiles()) {
			if(file.isDirectory()) {
				if(!file.getName().equals(".sidiff"))
				files.putAll(searchModelFiles(file));
			}else if(file_ext.contains(getFileExtension(file))) {
				files.put(file.getAbsolutePath(), file);
				File p = file.getParentFile();
				while(!p.equals(user_folder)) {
					files.put(p.getAbsolutePath(), p);
					p = p.getParentFile();
				}
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
	
	public File getFile(String session_path) {
		String absolute_path = user_folder.getAbsolutePath() + File.separator + session_path;
		return files.get(absolute_path);
	}
	
	public List<File> getChildren(File file){
		List<File> children = new ArrayList<File>();
		for(File child : file.listFiles()) {
			if(files.containsKey(child.getAbsolutePath())) {
				children.add(child);
			}
		}
		return children;
	}
}
