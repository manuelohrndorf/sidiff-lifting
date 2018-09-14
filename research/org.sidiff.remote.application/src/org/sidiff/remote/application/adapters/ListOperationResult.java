package org.sidiff.remote.application.adapters;

import java.util.List;

import org.sidiff.remote.common.ProxyObject;

public class ListOperationResult extends RepositoryOperationResult {
	
	private String sourcePath;
	
	private List<ProxyObject> proxyObjects;

	public ListOperationResult(String url, int port, String sourcePath, List<ProxyObject> proxyObjects, String message, boolean succeeded) {
		super(url, port, succeeded);
		this.sourcePath = sourcePath;
		this.proxyObjects = proxyObjects;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public List<ProxyObject> getProxyObjects() {
		return proxyObjects;
	}

}
