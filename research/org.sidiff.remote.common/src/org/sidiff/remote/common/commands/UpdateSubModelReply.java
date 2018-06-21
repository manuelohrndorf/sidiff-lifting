package org.sidiff.remote.common.commands;

import java.io.File;

import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public class UpdateSubModelReply extends ReplyCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1937702746093229781L;

	/**
	 * 
	 * @param attachment
	 * 				the zipped rulebased slice (edit script)
	 */
	public UpdateSubModelReply(File attachment) {
		super(attachment);
		this.eCommand = ECommand.CHECKOUT_SUB_MODEL_REPLY;
	}

}
