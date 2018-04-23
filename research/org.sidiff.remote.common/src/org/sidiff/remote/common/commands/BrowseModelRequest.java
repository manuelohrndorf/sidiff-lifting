package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

public class BrowseModelRequest extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -593012555492766304L;
	
	/**
	 * 
	 */
	private String remote_model_path;

	/**
	 * 
	 * @param session
	 * @param remote_model_path
	 * @param attachment
	 */
	public BrowseModelRequest(Session session, String remote_model_path) {
		super(session, null);
		this.eCommand = ECommand.BROWSE_MODEL_REQUEST;
		this.remote_model_path = remote_model_path;
	}

	/**
	 * 
	 * @return
	 */
	public String getRemoteModelPath() {
		return remote_model_path;
	}
}
