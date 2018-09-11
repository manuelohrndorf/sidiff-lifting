package org.sidiff.remote.application.adapters;

/**
 * 
 * @author cpietsch
 *
 */
public class RepositoryInfo {
	
	private String url;
	
	private String path;
	
	private String revision;
	
	private String author;

	public RepositoryInfo(String url, String path, String revision, String author) {
		super();
		this.url = url;
		this.path = path;
		this.revision = revision;
		this.author = author;
	}

	public String getUrl() {
		return url;
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
