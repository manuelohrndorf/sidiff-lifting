package org.sidiff.remote.common.commands;

import java.util.List;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.ProxyObject;

/**
 * 
 * @author cpietsch
 *
 */
public class GetRequestedModelFileReply extends ReplyCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3678236069606016169L;
	
	/**
	 * a tree based representation of a remote model
	 */
	private List<ProxyObject> proxyObjects;
	
	/**
	 * 
	 * @param session
	 * @param proxyObjects
	 *            a tree based representation of a remote model
	 */
	public GetRequestedModelFileReply(List<ProxyObject> proxyObjects) {
		super(null);
		this.eCommand = ECommand.GET_REQUESTED_MODEL_FILE_REPLY;
		this.proxyObjects = proxyObjects;
	}
	
	/**
	 * a tree based representation of a remote model
	 * 
	 * @return a tree based representation of a remote model
	 */
	public List<ProxyObject> getProxyObjects() {
		return proxyObjects;
	}
}
