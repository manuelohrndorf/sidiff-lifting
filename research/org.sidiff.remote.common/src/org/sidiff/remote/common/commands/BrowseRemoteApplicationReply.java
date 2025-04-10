package org.sidiff.remote.common.commands;

import java.util.List;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.ProxyObject;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseRemoteApplicationReply extends ReplyCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3945228607427626334L;

	/**
	 * list of {@link ProxyObject}s
	 */
	private List<ProxyObject> proxyObjects;

	/**
	 * 
	 * @param treeNodes
	 *            list of {@link ProxyObject}s
	 */
	public BrowseRemoteApplicationReply(List<ProxyObject> treeNodes) {
		super(null);
		this.eCommand = ECommand.BROWSE_REMOTE_APPLICATION_CONTENT_REPLY;
		this.proxyObjects = treeNodes;
	}

	/**
	 * List of {@link ProxyObject}s
	 * 
	 * @return list of {@link ProxyObject}s
	 */
	public List<ProxyObject> getProxyObjects() {
		return proxyObjects;
	}
}
