package org.sidiff.remote.application.connector.meta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.sidiff.remote.application.connector.exception.InvalidProjectInfoException;
import org.sidiff.remote.application.connector.exception.ModelNotVersionedException;

/**
 * 
 * @author cpietsch
 *
 */
public class ProjectInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6517018944191750899L;
	
	public static final String META_FOLDER_NAME = ".SiDiff";
	
	public static final String META_EXT = ".meta";

	/**
	 * 
	 */
	public final String ID;
	
	/**
	 * 
	 */
	private Set<ModelInfo> modelInfos;
	
	/**
	 * 
	 * @param id
	 */
	public ProjectInfo(String id) {
		this.ID = id;
		this.modelInfos = new HashSet<ModelInfo>();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return this.modelInfos.size() > 0;
	}
	
	/**
	 * 
	 * @param relative_local_model_path
	 *            local model path relative to the workspace
	 * @param relative_remote_model_path
	 *            remote model path relative to the user folder
	 * @param file
	 *            the local model file
	 * @throws InvalidProjectInfoException
	 */
	public void addModel(String relative_local_model_path, String relative_remote_model_path, File file) throws InvalidProjectInfoException {
		try {
			removeModel(relative_local_model_path);
			this.modelInfos.add(new ModelInfo(relative_local_model_path, relative_remote_model_path, file));
		} catch (NoSuchAlgorithmException | IOException e) {
			throw new InvalidProjectInfoException(e);
		}
	}
	
	/**
	 * 
	 * @param relative_local_model_path
	 *            local model path relative to the workspace
	 */
	public void removeModel(String relative_local_model_path) {
		for (Iterator<ModelInfo> iterator = modelInfos.iterator(); iterator.hasNext();) {
			ModelInfo modelInfo = iterator.next();
			if(modelInfo.getRelativeLocalPath().equals(relative_local_model_path)) {
				iterator.remove();
			}
		}
	}
	
	/**
	 * 
	 * @param relative_local_model_path
	 *            local model path relative to the workspace
	 * @return <code>true</code> if the model file is connected to the remote applicatoin, <code>false</code> otherwise
	 */
	public boolean isVersioned(String relative_local_model_path) {
		for(ModelInfo modelInfo : modelInfos) {
			if(modelInfo.getRelativeLocalPath().equals(relative_local_model_path)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param relative_local_model_path
	 *            local model path relative to the workspace
	 * @return
	 * @throws ModelNotVersionedException
	 */
	public String getModelChecksum(String relative_local_model_path) throws ModelNotVersionedException {
		for(ModelInfo modelInfo : modelInfos) {
			if(modelInfo.getRelativeLocalPath().equals(relative_local_model_path)) {
				return modelInfo.getChecksum();
			}
		}
		throw new ModelNotVersionedException(relative_local_model_path);
	}
	
	/**
	 * 
	 * @param relative_local_model_path
	 *            local model path relative to the workspace
	 * @return
	 * @throws ModelNotVersionedException
	 */
	public String getRemoteModelPath(String relative_local_model_path) throws ModelNotVersionedException {
		for(ModelInfo modelInfo : modelInfos) {
			if(modelInfo.getRelativeLocalPath().equals(relative_local_model_path)) {
				return modelInfo.getRelativeRemotePath();
			}
		}
		throw new ModelNotVersionedException(relative_local_model_path);
	}
	
	public Set<ModelInfo> getModelInfos(){
		return Collections.unmodifiableSet(modelInfos);
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Project: " + ID + "\n");
		for(ModelInfo modelInfo : modelInfos) {
			stringBuilder.append(modelInfo.toString() + "\n");
		}
		return stringBuilder.toString();
	}
	
	/**
	 * Reads the version meta data from project and cleans up the entries.
	 * 
	 * @return the {@link ProjectInfo} of the project identified by the name
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidProjectInfoException
	 */
	public static ProjectInfo readProjectInfo(String id) throws InvalidProjectInfoException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(id);
		String projectInfo_path = project.getLocation().toOSString() + File.separator + META_FOLDER_NAME + File.separator + META_EXT;
		ProjectInfo projectInfo = null;
		try {
			File file = new File(projectInfo_path);
			if(file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				projectInfo = (ProjectInfo) ois.readObject();
				projectInfo.cleanUp();
				ois.close();
			}else {
				projectInfo = new ProjectInfo(id);
			}
		} catch (IOException | ClassNotFoundException e) {
			throw new InvalidProjectInfoException(e);
		}

		return projectInfo;
	}
	
	/**
	 * Writes the given project info to project directory
	 * 
	 * @param projectInfo
	 *            the project info to be serialized
	 * @throws InvalidProjectInfoException
	 */
	public static void writeProjectInfo(ProjectInfo projectInfo) throws InvalidProjectInfoException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(projectInfo.ID);
		String projectInfo_path = project.getLocation().toOSString() + File.separator + META_FOLDER_NAME
				+ File.separator + META_EXT;
		try {
			File file = new File(projectInfo_path);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(projectInfo);
			oos.close();

		} catch (IOException e) {
			throw new InvalidProjectInfoException(e);
		}
	}
	
	public void cleanUp() {
		for(ModelInfo modelInfo : getModelInfos()) {
			if(!modelInfo.getFile().exists()) {
				modelInfos.remove(modelInfo);
			}
		}
	}
}
