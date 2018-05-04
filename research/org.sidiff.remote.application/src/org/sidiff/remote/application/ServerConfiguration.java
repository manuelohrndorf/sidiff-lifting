package org.sidiff.remote.application;

public class ServerConfiguration {
	
	public final String URL;
	
	public final int PORT;
	
	public ServerConfiguration(String url, int port) {
		this.URL = url;
		this.PORT = port;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Configuration:\n");
		stringBuilder.append("\t*port: " + URL);
		stringBuilder.append("\t*port: "+ PORT);
		return stringBuilder.toString();
	}
}
