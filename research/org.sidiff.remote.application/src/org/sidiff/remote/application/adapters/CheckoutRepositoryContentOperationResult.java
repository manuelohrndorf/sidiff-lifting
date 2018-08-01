package org.sidiff.remote.application.adapters;

public class CheckoutRepositoryContentOperationResult extends RepositoryOperationResult {

	private String sourcePath;
	private String targetPath;
	
	public CheckoutRepositoryContentOperationResult(String url, String host, int port, String sourcePath, String targetPath, String message, boolean succeeded) {
		super(url, host, port, message, succeeded);
		this.sourcePath = sourcePath;
		this.targetPath = targetPath;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public String getTargetPath() {
		return targetPath;
	}
	
	
}
