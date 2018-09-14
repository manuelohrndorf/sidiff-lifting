package org.sidiff.remote.application.adapters;

public class InitBranchResult extends RepositoryOperationResult {
	
	String path;
	
	public InitBranchResult(String url, int port, String path, boolean succeeded) {
		super(url, port, succeeded);
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
