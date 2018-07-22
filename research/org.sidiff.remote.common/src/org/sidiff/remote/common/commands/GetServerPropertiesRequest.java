package org.sidiff.remote.common.commands;

import java.io.File;

import org.sidiff.remote.common.Credentials;

public class GetServerPropertiesRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7215586692740421427L;

	public GetServerPropertiesRequest(Credentials credentials, File attachment) {
		super(credentials, null);
		// TODO Auto-generated constructor stub
	}

}
