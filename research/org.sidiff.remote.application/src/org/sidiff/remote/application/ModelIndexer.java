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
	
	private static final Set<String> SUPPORTED_FILE_EXTENSIONS = PipelineUtils.getAllAvailableRulebases().stream()
			.flatMap(liftingRuleBase -> liftingRuleBase.getDocumentTypes().stream())
			.flatMap(documentType -> EMFGenModelAccess.getFileExtensionFromDocumentType(documentType).stream())
			.collect(Collectors.toSet());
	
	private static final String FILTER_REGEX = "\\.\\S*";
	
	private File user_folder;
	
	private File session_folder;
	
	private Map<String, File> files;
	
	private Map<String, File> filteredFiles;
	
	public ModelIndexer(File session_folder) {
		this.user_folder = session_folder.getParentFile();
		this.session_folder = session_folder;
		this.files = new HashMap<String, File>();
		this.filteredFiles = new HashMap<String, File>();
	}
	
	/**
	 * indexes all supported model files
	 */
	public void index() {
		this.files.clear();
		this.files.putAll(searchModelFiles(session_folder));
		if(this.files.isEmpty()) {
			this.files.put(this.session_folder.getAbsolutePath(), session_folder);
		}
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
				while(!p.equals(user_folder)) {
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
	 * Gets the file extension of the given file
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
	
	public Map<String, File> getFilteredFiles() {
		return filteredFiles;
	}
}
