package org.sidiff.remote.application;

import java.util.Map;

/**
 * 
 * @author cpietsch
 *
 */
public class ServerConfiguration {
	
	public final String URL;
	
	public final int PORT;
	
	public final Map<String, String> users;
	
	public ServerConfiguration(String url, int port, Map<String, String> users) {
		this.URL = url;
		this.PORT = port;
		this.users = users;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Server Configuration\n");
		stringBuilder.append("\t-url: " + URL + "\n");
		stringBuilder.append("\t-port: "+ PORT);
		return stringBuilder.toString();
	}
}
