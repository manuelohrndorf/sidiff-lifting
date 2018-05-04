package org.sidiff.remote.common.commands;

import java.io.File;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

public class AddRepositoryReply extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8264373009186289395L;

	public AddRepositoryReply(Session session, File attachment) {
		super(session, attachment);
		this.eCommand = ECommand.ADD_REPOSITORY_REPLY;
	}

}
