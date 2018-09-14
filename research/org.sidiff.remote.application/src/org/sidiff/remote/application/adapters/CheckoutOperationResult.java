package org.sidiff.remote.application.adapters;

public class CheckoutOperationResult extends RepositoryOperationResult {

	private String sourcePath;
	private String targetPath;
	
	public CheckoutOperationResult(String url, int port, String sourcePath, String targetPath, boolean succeeded) {
		super(url, port, succeeded);
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
