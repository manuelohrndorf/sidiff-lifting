package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public class GetRequestedModelFileRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810529056867944138L;
	
	/**
	 * path for the requested remote model relative to the current user directory
	 */
	private String relative_remote_file_path;

	/**
	 * 
	 * @param credentials
	 * @param relative_remote_file_path
	 * 				path for the requested remote model relative to the current user directory
	 */
	public GetRequestedModelFileRequest(Credentials credentials, String relative_remote_file_path) {
		super(credentials, null);
		this.eCommand = ECommand.GET_REQUESTED_MODEL_FILE_REQUEST;
		this.relative_remote_file_path = relative_remote_file_path;
	}

	/**
	 * Path for the requested remote model relative to the current user directory, can be <code>null</code>.
	 * 
	 * @return
	 * 		path for the requested remote model relative to the current user directory or <code>null</code>
	 */
	public String getRelativeRemoteFilePath() {
		return relative_remote_file_path;
	}
}
