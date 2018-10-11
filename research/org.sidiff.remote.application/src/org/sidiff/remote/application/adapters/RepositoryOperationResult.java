package org.sidiff.remote.application.adapters;

/**
 * 
 * @author cpietsch
 *
 */
public abstract class RepositoryOperationResult {

	protected String url;
	
	protected int port;
	
	public RepositoryOperationResult(String url, int port) {
		super();
		this.url = url;
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public int getPort() {
		return port;
	}
}
