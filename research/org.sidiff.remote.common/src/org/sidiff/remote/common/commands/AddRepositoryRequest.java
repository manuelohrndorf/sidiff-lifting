package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

public class AddRepositoryRequest extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6051012278234141804L;
	
	/**
	 * 
	 */
	private String repository_url;
	
	/**
	 * 
	 */
	private int repository_port;
	
	/**
	 * 
	 */
	private String repository_user_name;
	
	/**
	 * 
	 */
	private char[] repository_password;

	

	public AddRepositoryRequest(Session session, String repository_url, int repository_port, String repository_user_name, char[] repository_password) {
		super(session, null);
		this.eCommand = ECommand.ADD_REPOSITORY_REQUEST;
		this.repository_url = repository_url;
		this.repository_port = repository_port;
		this.repository_user_name = repository_user_name;
		this.repository_password = repository_password;
	}
	
	public String getRepositoryUrl() {
		return repository_url;
	}
	
	public int getRepositoryPort() {
		return repository_port;
	}
	
	public String getRepositoryUserName() {
		return repository_user_name;
	}
	
	public char[] getRepositoryPassword() {
		return repository_password;
	}

}
