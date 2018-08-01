package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseRepositoryContentRequest extends RequestCommand {

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
	private String repository_path;
	
	/**
	 * 
	 */
	private String repository_user_name;
	
	/**
	 * 
	 */
	private char[] repository_password;

	/**
	 * 
	 * @param credentials
	 * @param repository_url
	 * @param repository_port
	 * @param repository_path
	 * @param repository_user_name
	 * @param repository_password
	 */
	public BrowseRepositoryContentRequest(Credentials credentials, String repository_url, int repository_port, String repository_path, String repository_user_name, char[] repository_password) {
		super(credentials, null);
		this.eCommand = ECommand.BROWSE_REPOSITORY_CONTENT_REQUEST;
		this.repository_url = repository_url;
		this.repository_port = repository_port;
		this.repository_path = repository_path;
		this.repository_user_name = repository_user_name;
		this.repository_password = repository_password;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRepositoryUrl() {
		return repository_url;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRepositoryPort() {
		return repository_port;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRepositoryPath() {
		return repository_path;
	}
	/**
	 * 
	 * @return
	 */
	public String getRepositoryUserName() {
		return repository_user_name;
	}
	
	/**
	 * 
	 * @return
	 */
	public char[] getRepositoryPassword() {
		return repository_password;
	}
}
