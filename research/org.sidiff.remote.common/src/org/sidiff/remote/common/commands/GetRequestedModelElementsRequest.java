package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public class GetRequestedModelElementsRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810529056867944138L;
	
	/**
	 * path for the requested remote model relative to the current user directory
	 */
	private String relative_remote_model_path;
	
	/**
	 * path of the local model file starting with the project name
	 */
	private String relative_local_model_path;

	/**
	 * 
	 * @param credentials
	 * @param relative_local_model_path
	 * 				absolute local location path that will be converted into a project relative path
	 */
	public GetRequestedModelElementsRequest(Credentials credentials, String relative_remote_model_path, String relative_local_model_path) {
		super(credentials, null);
		this.eCommand = ECommand.GET_REQUESTED_MODEL_ELEMENTS_REQUEST;
		this.relative_remote_model_path = relative_remote_model_path;
		this.relative_local_model_path = relative_local_model_path;
	}

	/**
	 * Path for the requested remote model file relative to the current user directory.
	 * 
	 * @return path for the requested remote model file relative to the current user directory
	 */
	public String getRelativeRemoteModelPath() {
		return relative_remote_model_path;
	}
	
	/**
	 * Path of the local model file starting with the project name.
	 * 
	 * @return
	 * 		path of the local model file starting with the project name
	 */
	public String getRelativeLocalModelPath() {
		return relative_local_model_path;
	}
}
