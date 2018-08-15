package org.sidiff.remote.application.adapters;

public abstract class RepositoryOperationResult {

	protected String url;
	protected int port;
	protected String message;
	protected boolean succeeded;
	
	
	
	public RepositoryOperationResult(String url, int port, String message, boolean succeeded) {
		super();
		this.url = url;
		this.port = port;
		this.message = message;
		this.succeeded = succeeded;
	}

	public String getUrl() {
		return url;
	}

	public int getPort() {
		return port;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean isSucceeded() {
		return succeeded;
	}
}
