package org.sidiff.remote.application;

import java.util.Map;

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
		stringBuilder.append("Configuration\n");
		stringBuilder.append("\t*port: " + URL + "\n");
		stringBuilder.append("\t*port: "+ PORT);
		return stringBuilder.toString();
	}
}
