package org.sidiff.remote.common.commands;

import java.io.File;

import org.sidiff.remote.common.Session;
import org.sidiff.remote.common.tree.TreeModel;

public class BrowseModelReply extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5691413751211948004L;
	
	private TreeModel model;
	
	public BrowseModelReply(Session session, TreeModel model, File attachment) {
		super(session, attachment);
		this.model = model;
	}

	public TreeModel getModel() {
		return model;
	}
}
