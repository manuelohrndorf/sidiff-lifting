package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public class CheckoutRepositoryContentReply extends ReplyCommand {

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
	public CheckoutRepositoryContentReply(String host, String root_folder) {
		super(null);
		this.eCommand = ECommand.CHECKOUT_REPOSITORY_CONTENT_REPLY;
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
