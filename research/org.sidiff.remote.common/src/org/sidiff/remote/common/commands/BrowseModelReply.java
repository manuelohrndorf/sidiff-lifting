package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.tree.TreeModel;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseModelReply extends ReplyCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5691413751211948004L;
	
	/**
	 * a tree based representation of a remote model
	 */
	private TreeModel model;
	
	/**
	 * 
	 * @param model
	 * 			a tree based representation of a remote model
	 */
	public BrowseModelReply(TreeModel model) {
		super(null);
		this.model = model;
	}

	/**
	 * a tree based representation of a remote model
	 * @return
	 * 		a tree based representation of a remote model
	 */
	public TreeModel getModel() {
		return model;
	}
}
