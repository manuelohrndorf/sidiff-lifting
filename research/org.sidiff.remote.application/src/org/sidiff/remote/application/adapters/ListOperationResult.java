package org.sidiff.remote.application.adapters;

import java.util.List;

import org.sidiff.remote.common.ProxyObject;

/**
 * 
 * @author cpietsch
 *
 */
public class ListOperationResult extends RepositoryOperationResult {
	
	private String sourcePath;
	
	private List<ProxyObject> proxyObjects;

	public ListOperationResult(String url, int port, String sourcePath, List<ProxyObject> proxyObjects, String message) {
		super(url, port);
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
