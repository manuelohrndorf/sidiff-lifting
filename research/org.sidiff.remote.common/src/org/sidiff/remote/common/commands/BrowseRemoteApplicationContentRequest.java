package org.sidiff.remote.common.commands;

import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseRemoteApplicationContentRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1057205512420394166L;
	
	/**
	 * path of a requested remote file relative to the current user directory
	 */
	private String relative_remote_file_path;

	/**
	 * ID of a model element
	 */
	private String elementID;
	
	/**
	 * 
	 * @param credentials
	 *            The current {@link Credentials}
	 * @param relative_remote_file_path
	 *            path of a requested remote file relative to the current user
	 *            directory
	 * @param elementID
	 *            the ID of a requested model element (only used if the requested
	 *            file is a model file)
	 */
	public BrowseRemoteApplicationContentRequest(Credentials credentials, String relative_remote_file_path, String elementID) {
		super(credentials, null);
		this.eCommand = ECommand.BROWSE_REMOTE_APPLICATION_CONTENT_REQUEST;
		this.relative_remote_file_path = relative_remote_file_path;
		this.elementID = elementID;
	}
	
	/**
	 * Path of a requested remote file relative to the current user directory.
	 * 
	 * @return path of a requested remote file relative to the current user
	 *         directory
	 */
	public String getRelativeRemoteFilePath() {
		return relative_remote_file_path;
	}
	
	/**
	 * ID of a model element.
	 * 
	 * @return
	 * 		ID of a model element
	 */
	public String getElementID() {
		return elementID;
	}
}
