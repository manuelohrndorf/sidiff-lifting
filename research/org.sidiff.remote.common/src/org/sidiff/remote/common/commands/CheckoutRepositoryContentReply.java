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

	/**
	 * 
	 * @param host
	 * @param root_folder
	 */
	public CheckoutRepositoryContentReply() {
		super(null);
		this.eCommand = ECommand.CHECKOUT_REPOSITORY_CONTENT_REPLY;
	}
}
