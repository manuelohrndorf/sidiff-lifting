package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.tree.TreeModel;

/**
 * 
 * @author cpietsch
 *
 */
public class GetRequestedModelElementsReply extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3678236069606016169L;
	
	/**
	 * a tree based representation of a remote model
	 */
	private TreeModel model;
	
	/**
	 * 
	 * @param session
	 * @param model
	 * 			a tree based representation of a remote model
	 */
	public GetRequestedModelElementsReply(TreeModel model) {
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
