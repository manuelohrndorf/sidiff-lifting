package org.sidiff.remote.application.adapters;

public abstract class RepositoryOperationResult {

	protected String url;
	protected int port;
	protected boolean succeeded;
	
	
	
	public RepositoryOperationResult(String url, int port, boolean succeeded) {
		super();
		this.url = url;
		this.port = port;
		this.succeeded = succeeded;
	}

	public String getUrl() {
		return url;
	}

	public int getPort() {
		return port;
	}
	
	public boolean isSucceeded() {
		return succeeded;
	}
}
