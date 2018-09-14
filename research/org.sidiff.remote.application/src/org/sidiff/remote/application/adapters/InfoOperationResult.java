package org.sidiff.remote.application.adapters;

public class InfoOperationResult extends RepositoryOperationResult {

	private String path;
	private String revision;
	private String author;
	
	public InfoOperationResult(String url, int port, String path, String revision, String author, boolean succeeded) {
		super(url, port, succeeded);
		this.path = path;
		this.revision = revision;
		this.author = author;
	}

	public String getPath() {
		return path;
	}
	
	public String getRevision() {
		return revision;
	}
	
	public String getAuthor() {
		return author;
	}
}
