package org.sidiff.remote.application.connector.meta;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import org.sidiff.remote.common.util.ChecksumUtil;

public class ModelInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2379314679219934237L;

	/**
	 * path relative to the workspace
	 */
	private String relative_local_path;
	
	/**
	 * path relative to the remote user folder
	 */
	private String relative_remote_path;
	
	/**
	 * The versioned {@link File}
	 */
	private File file;
	
	/**
	 * md5 hash
	 */
	private String checksum;
	
	/**
	 * state of the versioned model
	 */
	private boolean modified;

	public ModelInfo(String relative_local_path, String relative_remote_path, File file) throws NoSuchAlgorithmException, IOException {
		this.relative_local_path = relative_local_path;
		this.relative_remote_path = relative_remote_path;
		this.file = file;
		this.checksum = ChecksumUtil.getFileChecksum(file);;
		this.modified = false;
	}
	
	public String getRelativeLocalPath() {
		return relative_local_path;
	}
	
	public String getRelativeRemotePath() {
		return relative_remote_path;
	}
	
	public File getFile() {
		return file;
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
	
	public boolean validate() {
		return this.file.exists();
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Model info:\n");
		stringBuilder.append("\t local path: " + relative_local_path + "\n"); 
		stringBuilder.append("\t remote path: " + relative_remote_path + "\n");
		stringBuilder.append("\t checksum: " + checksum + "\n");
		stringBuilder.append("\t is modified: " + modified + "\n");
		
		return stringBuilder.toString();
	}
}
