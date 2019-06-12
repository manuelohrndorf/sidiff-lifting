package org.sidiff.remote.application.connector.settings;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.core.runtime.MultiStatus;
import org.sidiff.common.emf.settings.AbstractSettings;

public class RepositorySettings extends AbstractSettings {

	
	private String repository_url = "";
	
	private int repository_port = -1;
	
	private String repository_path = "";
	
	private String user_name = "";
	
	private char[] password = new char[] {};
	
	public String getRepositoryURL() {
		return repository_url;
	}

	public void setRepositoryURL(String repository_url) {
		if( repository_url == null || ! repository_url.equals(this.repository_url)) {
			this.repository_url =  repository_url;
			notifyListeners(RepositorySettingsItem.REP_URL);
		}
	}

	public int getRepositoryPort() {
		return repository_port;
	}
	
	public void setRepositoryPort(int repository_port) {
		if(repository_port > 0 || repository_port != this.repository_port) {
			this.repository_port = repository_port;
			notifyListeners(RepositorySettingsItem.REP_PORT);
		}
	}

	public String getRepositoryPath() {
		return repository_path;
	}
	
	public void setRepositoryPath(String repository_path) {
		if(repository_path == null || !repository_path.equals(this.repository_path)) {
			this.repository_path = repository_path;
			notifyListeners(RepositorySettingsItem.REP_PATH);
		}
		this.repository_path = repository_path;
	}
	
	public String getUserName() {
		return user_name;
	}

	public void setUserName(String user_name) {
		if(user_name == null || !user_name.equals(this.user_name)) {
			this.user_name = user_name;
			notifyListeners(RepositorySettingsItem.USER_NAME);
		}
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		if(password == null || !Arrays.equals(password, this.password)) {
			this.password = password;
			notifyListeners(RepositorySettingsItem.PASSWORD);
		}
	}


	@Override
	protected void validate(MultiStatus multiStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDefaults(Set<String> documentTypes) {
		// TODO Auto-generated method stub
		
	}

}
