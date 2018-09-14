package org.sidiff.remote.application.adapters;

/**
 * 
 * @author cpietsch
 *
 */
public class RepositoryInfo {
	
	private String url;
	
	private int port;
	
	private String path;
	
	private String revision;
	
	private String author;

	public RepositoryInfo(String url, int port, String path, String revision, String author) {
		super();
		this.url = url;
		this.port = port;
		this.path = path;
		this.revision = revision;
		this.author = author;
	}

	public String getUrl() {
		return url;
	}
	
	public int getPort() {
		return port;
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
