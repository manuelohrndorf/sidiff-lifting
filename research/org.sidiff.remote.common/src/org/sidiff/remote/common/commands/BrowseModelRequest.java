package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseModelRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -593012555492766304L;
	
	/**
	 * session based relative model path
	 */
	private String remote_model_path;

	/**
	 * 
	 * @param credentials
	 * @param remote_model_path
	 * 				session based relative model path
	 * @param attachment
	 */
	public BrowseModelRequest(Credentials credentials, String remote_model_path) {
		super(credentials, null);
		this.eCommand = ECommand.BROWSE_MODEL_REQUEST;
		this.remote_model_path = remote_model_path;
	}

	/**
	 * session based relative path
	 * @return
	 * 		session based relative model path
	 */
	public String getRemoteModelPath() {
		return remote_model_path;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(super.toString());
		stringBuilder.append("remote model path: " + this.remote_model_path);
		return stringBuilder.toString();
	}
}
