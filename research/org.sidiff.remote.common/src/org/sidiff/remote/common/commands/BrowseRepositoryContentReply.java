package org.sidiff.remote.common.commands;

import java.util.List;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.ProxyObject;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseRepositoryContentReply extends ReplyCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8264373009186289395L;
	
	private List<ProxyObject> proxyObjects;
	
	/**
	 * 
	 * @param host
	 * @param root_folder
	 */
	public BrowseRepositoryContentReply(List<ProxyObject> proxyObjects) {
		super(null);
		this.eCommand = ECommand.BROWSE_REPOSITORY_CONTENT_REPLY;
		this.proxyObjects = proxyObjects;
	}

	public List<ProxyObject> getProxyObjects() {
		return proxyObjects;
	}
}
