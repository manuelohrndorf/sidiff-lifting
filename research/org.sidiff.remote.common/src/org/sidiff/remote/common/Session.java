package org.sidiff.remote.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 
 * @author cpietsch
 *
 */
public class Session implements Serializable{
	
	public static final String SESSION_EXT = ".session";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sessionID;
	
	private String url;
	
	private int port;
	
	private String user;
	
	private Set<String> repositories;
	
	private Map<String, String> models;
	
	public Session(String url, int port, String user) {
		this.sessionID = UUID.randomUUID().toString();
		this.url = url;
		this.port = port;
		this.user = user;
		this.repositories = new HashSet<String>();
		this.models = new HashMap<String, String>();
	}

	public void addRepository(String repository) {
		this.repositories.add(repository);
	}
	
	public void addModel(String localPath, String remotePath) {
		this.models.put(localPath, remotePath);
	}

	public String getSessionID() {
		return sessionID;
	}

	public String getUrl() {
		return url;
	}

	public int getPort() {
		return port;
	}
	
	public String getUser() {
		return user;
	}

	public Set<String> getRepositories() {
		return repositories;
	}

	public Map<String, String> getModels() {
		return models;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("user: " + user + "\n");
		stringBuilder.append("session: " + sessionID + "\n");
		stringBuilder.append("local -> remote models:\n");
		for(String local : models.keySet()) {
			stringBuilder.append("\t" + local + " -> " + models.get(local) + "\n");
		}
		return stringBuilder.toString();
	}
}
