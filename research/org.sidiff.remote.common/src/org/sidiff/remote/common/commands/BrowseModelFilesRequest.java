package org.sidiff.remote.common.commands;

import java.io.File;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

public class BrowseModelFilesRequest extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1057205512420394166L;

	/**
	 * 
	 * @param session
	 * @param eCommand
	 * @param attachment
	 */
	public BrowseModelFilesRequest(Session session, File attachment) {
		super(session, attachment);
		this.eCommand = ECommand.BROWSE_MODEL_FILES_REQUEST;
	}
}
