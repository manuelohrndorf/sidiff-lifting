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
	
	private final String url;
	
	private final int port;
	
	private final String session_id;
	
	private final String user;
	
	private final String password;

	public Credentials(String url, int port, String session_id, String user, String password) {
		this.url = url;
		this.port = port;
		this.user = user;
		this.password = password;
		this.session_id = session_id;
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
	
	public String getPassword() {
		return password;
	}
	
	public String getSessionID() {
		return session_id;
	}

}
