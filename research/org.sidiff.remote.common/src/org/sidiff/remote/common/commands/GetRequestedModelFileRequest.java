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
	 * project relative model path
	 */
	private String session_path;

	/**
	 * 
	 * @param credentials
	 * @param session_path
	 * 				a session based location path
	 */
	public GetRequestedModelFileRequest(Credentials credentials, String session_path) {
		super(credentials, null);
		this.eCommand = ECommand.GET_REQUESTED_MODEL_FILE_REQUEST;
		this.session_path = session_path;
	}

	/**
	 * session based location path, can be <code>null</code>
	 * @return
	 * 		session based location path or <code>null</code>
	 */
	public String getSessionPath() {
		return session_path;
	}
}
