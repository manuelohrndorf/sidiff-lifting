package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.tree.TreeModel;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseModelFilesReply extends ReplyCommand {

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
	 * @param modelFiles
	 * 				a tree based representation of the remote model files
	 */
	public BrowseModelFilesReply(TreeModel modelFiles) {
		super(null);
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
