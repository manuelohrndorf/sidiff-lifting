package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public class AddRepositoryReply extends ReplyCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8264373009186289395L;

	private String host;
	
	private String root_folder;
	
	/**
	 * 
	 * @param host
	 * @param root_folder
	 */
	public AddRepositoryReply(String host, String root_folder) {
		super(null);
		this.eCommand = ECommand.ADD_REPOSITORY_REPLY;
		this.host = host;
		this.root_folder = root_folder;
	}

	public String getHost() {
		return host;
	}
	
	public String getRootFolder() {
		return root_folder;
	}
}
