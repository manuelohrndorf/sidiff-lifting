package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1057205512420394166L;
	
	/**
	 * a session based location path;
	 */
	private String session_path;

	/**
	 * ID of a model element
	 */
	private String element_id;
	
	/**
	 * 
	 * @param credentials
	 * 			The current {@link Session}
	 * @param session_path
	 * 			a session based location path
	 */
	public BrowseRequest(Credentials credentials, String session_path, String element_id) {
		super(credentials, null);
		this.eCommand = ECommand.BROWSE_REQUEST;
		
		this.session_path = session_path;
		this.element_id = element_id;
	}
	
	/**
	 * session based location path, can be <code>null</code>
	 * @return
	 * 		session based location path or <code>null</code>
	 */
	public String getSessionPath() {
		return session_path;
	}
	
	/**
	 * ID of a model element
	 * @return
	 * 		ID of a model element
	 */
	public String getElementID() {
		return element_id;
	}
}
