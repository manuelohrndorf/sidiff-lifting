package org.sidiff.remote.common.commands;

import java.io.File;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

/**
 * 
 * @author cpietsch
 *
 */
public class UpdateSubModelReply extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1937702746093229781L;

	/**
	 * 
	 * @param session
	 * @param attachment
	 * 				the zipped rulebased slice (edit script)
	 */
	public UpdateSubModelReply(Session session, File attachment) {
		super(session, attachment);
		this.eCommand = ECommand.CHECKOUT_SUB_MODEL_REPLY;
	}

}
