package org.sidiff.remote.application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.sidiff.common.emf.access.EMFGenModelAccess;
import org.sidiff.difference.lifting.api.util.PipelineUtils;

/**
 * 
 * @author cpietsch
 *
 */
public class ModelIndexer {
	
	/**
	 * All supported file extensions, i.e. all file extensions of model files for which a rule base is available
	 */
	private static final Set<String> SUPPORTED_FILE_EXTENSIONS = PipelineUtils.getAllAvailableRulebases().stream()
			.flatMap(liftingRuleBase -> liftingRuleBase.getDocumentTypes().stream())
			.flatMap(documentType -> EMFGenModelAccess.getFileExtensionFromDocumentType(documentType).stream())
			.collect(Collectors.toSet());
	
	/**
	 * Filter regex for (hidden) files and directories
	 */
	private static final String FILTER_REGEX = "\\.\\S*";
	
	/**
	 * The root folder
	 */
	private File root_folder;
	
	/**
	 * absolute file path to file
	 */
	private Map<String, File> files;
	
	/**
	 * absolute file path to file
	 */
	private Map<String, File> filteredFiles;
	
	/**
	 * 
	 * @param root_folder
	 */
	public ModelIndexer(File root_folder) {
		this.root_folder = root_folder;
		this.files = new HashMap<String, File>();
		this.filteredFiles = new HashMap<String, File>();
	}
	
	/**
	 * builds an index of all files (and directories) contained in {@link #root_folder}
	 */
	public void index() {
		this.files.clear();
		this.files.put(this.root_folder.getAbsolutePath(), root_folder);
		this.files.putAll(searchModelFiles(root_folder));			
	}
	
	/**
	 * searches all model files contained in the parent folder or any of its
	 * subfolder
	 * 
	 * @param parent
	 *            the parent folder
	 * @return all model files contained in the parent folder or any of its
	 *         subfolder
	 */
	private Map<String, File> searchModelFiles(File parent){
		Map<String, File> files = new HashMap<String, File>();
		for(File file : parent.listFiles()) {
			if(file.isDirectory()) {
				if(!file.getName().matches(FILTER_REGEX)) {
					files.putAll(searchModelFiles(file));
				}else {
					filteredFiles.put(file.getAbsolutePath(), file);
				}
			}else if(SUPPORTED_FILE_EXTENSIONS.contains(getFileExtension(file))) {
				files.put(file.getAbsolutePath(), file);
				File p = file.getParentFile();
				while(!p.equals(root_folder)) {
					files.put(p.getAbsolutePath(), p);
					p = p.getParentFile();
				}
			}else {
				filteredFiles.put(file.getAbsolutePath(), file);
			}
		}
		
		return files;
	}
	
	/**
	 * Returns the root folder for which an index is built.
	 * 
	 * @return the {@link #root_folder}
	 */
	public File getRootFolder() {
		return root_folder;
	}
	
	/**
	 * Returns the file for the given path.
	 * The path must be relative to the root folder
	 * 
	 * @param relative_path
	 *  		path of a file relative to the root folder
	 * @return a {@link File} for the given path or <code>null</code>
	 * 
	 * @see {@link #getRootFolder()}
	 */
	public File getFile(String relative_path) {
		String absolute_path = root_folder.getAbsolutePath()
				+ (!relative_path.isEmpty() ? File.separator + relative_path : "");
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
	
	public Map<String, File> getFilteredFiles() {
		return filteredFiles;
	}
	
	/**
	 * Returns the file extension of the given file
	 * 
	 * @param file
	 *            the {@link IFile} for which the file extension should be returned
	 * @return the file extension of the given {@link IFile}
	 */
	private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
        	return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        else return "";
    }
}
