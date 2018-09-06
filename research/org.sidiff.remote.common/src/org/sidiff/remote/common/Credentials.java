package org.sidiff.remote.common;

import java.io.Serializable;

/**
 * 
 * @author cpietsch
 *
 */
public class Credentials implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4145279537130783752L;
	
	private String url;
	
	private int port;
	
	private String user;
	
	private String password;

	public Credentials(String url, int port, String user, String password) {
		this.url = url;
		this.port = port;
		this.user = user;
		this.password = password;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
}
