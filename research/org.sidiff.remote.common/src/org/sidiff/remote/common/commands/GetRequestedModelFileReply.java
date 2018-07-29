package org.sidiff.remote.common.commands;

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
	private ProxyObject proxyObject;
	
	/**
	 * 
	 * @param session
	 * @param proxyObject
	 * 			a tree based representation of a remote model
	 */
	public GetRequestedModelFileReply(ProxyObject proxyObject) {
		super(null);
		this.eCommand = ECommand.GET_REQUESTED_MODEL_FILE_REPLY;
		this.proxyObject = proxyObject;
	}
	
	/**
	 * a tree based representation of a remote model
	 * @return
	 * 		a tree based representation of a remote model
	 */
	public ProxyObject getProxyObject() {
		return proxyObject;
	}
}
