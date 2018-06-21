package org.sidiff.remote.common;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.sidiff.remote.common.exceptions.InvalidSessionException;
import org.sidiff.remote.common.exceptions.ModelNotVersionedException;
import org.sidiff.remote.common.util.ChecksumUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class Session implements Serializable {
	
	/**
	 * the file extension of the serialized session object
	 */
	public static final String SESSION_EXT = ".session";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String sessionID;
	
	/**
	 * 
	 */
	private Set<String> repositories;
	
	/**
	 * 
	 */
	private Set<ModelInfo> modelInfos;
	
	/**
	 * 
	 */
	public Session() {
		this.sessionID = UUID.randomUUID().toString();
		this.repositories = new HashSet<String>();
		this.modelInfos = new HashSet<Session.ModelInfo>();
	}

	/**
	 * 
	 * @param repository_url
	 * 				the url of the cms
	 */
	public void addRepository(String repository_url) {
		this.repositories.add(repository_url);
	}
	
	/**
	 * 
	 * @param local_path
	 * 				project relative path
	 * @param remote_path
	 * 				session based relative model path
	 * @param file
	 * @throws InvalidSessionException 
	 */
	public void addModel(String local_path, String remote_path, File file) throws InvalidSessionException {
		try {
			String checksum = ChecksumUtil.getFileChecksum(file);
			this.modelInfos.add(new ModelInfo(local_path, remote_path, checksum));
		} catch (NoSuchAlgorithmException | IOException e) {
			throw new InvalidSessionException(e);
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSessionID() {
		return sessionID;
	}

	
	/**
	 * 
	 * @return
	 */
	public Set<String> getRepositories() {
		return repositories;
	}
	
	/**
	 * 
	 * @param local_path
	 * 				project relative path
	 * @return
	 */
	public boolean isVersioned(String local_path) {
		for(ModelInfo modelInfo : modelInfos) {
			if(modelInfo.getLocalPath().equals(local_path)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param local_path
	 * 				project relative path
	 * @return
	 * @throws ModelNotVersionedException
	 */
	public String getRemoteModelPath(String local_path) throws ModelNotVersionedException {
		for(ModelInfo modelInfo : modelInfos) {
			if(modelInfo.getLocalPath().equals(local_path)) {
				return modelInfo.getRemotePath();
			}
		}
		throw new ModelNotVersionedException(local_path);
	}
	
	/**
	 * 
	 * @param local_path
	 * 				project relative path
	 * @return
	 * @throws ModelNotVersionedException
	 */
	public String getModelChecksum(String local_path) throws ModelNotVersionedException {
		for(ModelInfo modelInfo : modelInfos) {
			if(modelInfo.getLocalPath().equals(local_path)) {
				return modelInfo.checksum;
			}
		}
		throw new ModelNotVersionedException(local_path);
	}
	
	/**
	 * 
	 * @param local_path
	 * 				project relative path
	 * @return
	 * @throws ModelNotVersionedException
	 */
	public boolean isModified(String local_path) throws ModelNotVersionedException {
		for(ModelInfo modelInfo : modelInfos) {
			if(modelInfo.getLocalPath().equals(local_path)) {
				return modelInfo.isModified();
			}
		}
		throw new ModelNotVersionedException(local_path);
	}
	
	/**
	 * 
	 * @param local_path
	 * 				project relative path
	 * @param modified
	 * 			<code>true|false</code>
	 * @throws ModelNotVersionedException
	 */
	public void setModified(String local_path, boolean modified) throws ModelNotVersionedException {
		for(ModelInfo modelInfo : modelInfos) {
			if(modelInfo.getLocalPath().equals(local_path)) {
				modelInfo.setModified(modified);
			}
		}
		throw new ModelNotVersionedException(local_path);
	}
	
	
	public Set<String> getLocalModelPaths(){
		Set<String> paths = new HashSet<String>();
		for(ModelInfo modelInfo : modelInfos) {
			paths.add(modelInfo.getLocalPath());
		}
		return paths;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("session: " + sessionID + "\n");
		stringBuilder.append("local -> remote models:\n");
		for(ModelInfo modelInfo : modelInfos) {
			stringBuilder.append(modelInfo.toString() + ")\n");
		}
		return stringBuilder.toString();
	}
	
	public class ModelInfo implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2379314679219934237L;

		/**
		 * project relative path
		 */
		private String local_path;
		
		/**
		 * session based relative model path
		 */
		private String remote_path;
		
		/**
		 * md5 hash
		 */
		private String checksum;
		
		/**
		 * state of the versioned model
		 */
		private boolean modified;

		public ModelInfo(String local_path, String remote_path, String checksum) {
			this.local_path = local_path;
			this.remote_path = remote_path;
			this.checksum = checksum;
			this.modified = false;
		}
		
		public String getLocalPath() {
			return local_path;
		}
		
		public String getRemotePath() {
			return remote_path;
		}
		
		public String getChecksum() {
			return checksum;
		}
		
		public boolean isModified() {
			return modified;
		}
		
		public void setModified(boolean modified) {
			this.modified = modified;
		}
		
		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Model info:\n");
			stringBuilder.append("\t local path: " + local_path + "\n"); 
			stringBuilder.append("\t remote path: " + remote_path + "\n");
			stringBuilder.append("\t checksum: " + checksum + "\n");
			stringBuilder.append("\t is modified: " + modified + "\n");
			
			return stringBuilder.toString();
		}
	}
	
	public boolean validate() {
		boolean b_sessionID = this.sessionID != null && !this.sessionID.isEmpty();
		
		return b_sessionID;
	}
}
