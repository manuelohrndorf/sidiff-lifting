package org.sidiff.remote.application.adapters;

/**
 * 
 * @author cpietsch
 *
 */
public class CheckoutOperationResult extends RepositoryOperationResult {

	private String sourcePath;
	
	private String targetPath;
	
	public CheckoutOperationResult(String url, int port, String sourcePath, String targetPath) {
		super(url, port);
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
