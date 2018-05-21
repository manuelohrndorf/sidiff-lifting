package org.sidiff.remote.application.adapters;

public abstract class RepositoryOperationResult {

	protected String url;
	protected String host;
	protected int port;
	protected String message;
	protected boolean succeeded;
	
	
	
	public RepositoryOperationResult(String url, String host, int port, String message, boolean succeeded) {
		super();
		this.url = url;
		this.host = host;
		this.port = port;
		this.message = message;
		this.succeeded = succeeded;
	}

	public String getUrl() {
		return url;
	}

	public String getHost() {
		return host;
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
