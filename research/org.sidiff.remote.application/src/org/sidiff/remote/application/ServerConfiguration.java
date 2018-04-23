package org.sidiff.remote.application;

public class ServerConfiguration {

	public static final String METADATA = ".metadata";
	
	public final int PORT;
	
	public ServerConfiguration(int port) {
		this.PORT = port;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Configuration:\n");
		stringBuilder.append("\t*port: "+ PORT);
		return stringBuilder.toString();
	}
}
