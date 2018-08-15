package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;

public class GetServerPropertiesRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7215586692740421427L;

	public GetServerPropertiesRequest(Credentials credentials) {
		super(credentials, null);
		this.eCommand = ECommand.GET_SERVER_PROPERTIES_REQUEST;
	}

}
