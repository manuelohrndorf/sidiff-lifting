package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.settings.RemotePreferences;

public class GetServerPropertiesReply extends ReplyCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4281645003890450139L;
	
	private RemotePreferences remotePreferences;

	public GetServerPropertiesReply(RemotePreferences remotePreferences) {
		super(null);
		this.eCommand = ECommand.GET_SERVER_PROPERTIES_REPLY;
		this.remotePreferences = remotePreferences;
	}
	
	public RemotePreferences getRemotePreferences() {
		return remotePreferences;
	}

}
