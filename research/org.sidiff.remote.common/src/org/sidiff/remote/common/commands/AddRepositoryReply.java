package org.sidiff.remote.common.commands;

import java.io.File;

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

	/**
	 * 
	 * @param attachment
	 */
	public AddRepositoryReply(File attachment) {
		super(attachment);
		this.eCommand = ECommand.ADD_REPOSITORY_REPLY;
	}

}
