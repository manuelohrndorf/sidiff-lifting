package org.sidiff.remote.common.commands;

import java.util.List;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.ProxyObject;

/**
 * 
 * @author cpietsch
 *
 */
public class ListRepositoryContentReply extends ReplyCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8264373009186289395L;

	private String host;
	
	private List<ProxyObject> proxyObjects;
	
	/**
	 * 
	 * @param host
	 * @param root_folder
	 */
	public ListRepositoryContentReply(String host, List<ProxyObject> proxyObjects) {
		super(null);
		this.eCommand = ECommand.LIST_REPOSITORY_CONTENT_REPLY;
		this.host = host;
		this.proxyObjects = proxyObjects;
	}

	public String getHost() {
		return host;
	}

	public List<ProxyObject> getProxyObjects() {
		return proxyObjects;
	}
}
