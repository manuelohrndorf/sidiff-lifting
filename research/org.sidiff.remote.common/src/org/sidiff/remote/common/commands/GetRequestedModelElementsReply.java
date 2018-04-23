package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.Session;
import org.sidiff.remote.common.tree.TreeModel;

public class GetRequestedModelElementsReply extends Command {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3678236069606016169L;
	
	private TreeModel model;
	
	public GetRequestedModelElementsReply(Session session, TreeModel model) {
		super(session, null);
		this.model = model;
	}
	
	public TreeModel getModel() {
		return model;
	}
}
