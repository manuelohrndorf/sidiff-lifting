package org.sidiff.remote.common.commands;

import java.io.File;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;
import org.sidiff.remote.common.tree.TreeModel;

public class BrowseModelFilesReply extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3945228607427626334L;

	private TreeModel modelFiles;
	
	public BrowseModelFilesReply(Session session, TreeModel modelFiles, File attachment) {
		super(session, attachment);
		this.eCommand = ECommand.BROWSE_MODEL_FILES_REPLY;
		this.modelFiles = modelFiles;
	}

	public TreeModel getModelFiles() {
		return modelFiles;
	}
}
