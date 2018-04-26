package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;
import org.sidiff.remote.common.tree.TreeModel;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseModelFilesReply extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3945228607427626334L;

	/**
	 * a tree based representation of the remote model files
	 */
	private TreeModel modelFiles;
	
	/**
	 * 
	 * @param session
	 * @param modelFiles
	 * 				a tree based representation of the remote model files
	 */
	public BrowseModelFilesReply(Session session, TreeModel modelFiles) {
		super(session, null);
		this.eCommand = ECommand.BROWSE_MODEL_FILES_REPLY;
		this.modelFiles = modelFiles;
	}

	/**
	 * a tree based representation of the remote model files
	 * @return
	 * 		a tree based representation of the remote model files
	 */
	public TreeModel getModelFiles() {
		return modelFiles;
	}
}
