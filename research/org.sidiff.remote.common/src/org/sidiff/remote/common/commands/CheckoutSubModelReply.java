package org.sidiff.remote.common.commands;

import java.io.File;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

public class CheckoutSubModelReply extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1937702746093229781L;

	public CheckoutSubModelReply(Session session, File attachment) {
		super(session, attachment);
		this.eCommand = ECommand.CHECKOUT_SUB_MODEL_REPLY;
		// TODO Auto-generated constructor stub
	}

}
