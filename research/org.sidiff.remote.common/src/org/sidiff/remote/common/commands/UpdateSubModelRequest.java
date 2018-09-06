package org.sidiff.remote.common.commands;

import java.util.Set;

import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.settings.RemotePreferences;

/**
 * 
 * @author cpietsch
 *
 */
public class UpdateSubModelRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5030169672146475191L;

	/**
	 * path of a requested remote model file relative to the current user directory
	 */
	private String relative_remote_model_path;
	
	/**
	 * path of the local model file starting with the project name
	 */
	private String relative_local_model_path;
	
	private Set<String> elementIDs;
	
	RemotePreferences preferences;
	
	/**
	 * 
	 * @param credentials
	 *            The current {@link Credentials}
	 * @param relative_remote_model_path
	 *            path of a requested remote model file relative to the current user directory
	 * @param relative_local_model_path
	 *            path of the local model file starting with the project name
	 * @param elementIDs
	 *            the IDs of a requested model elements
	 */
	public UpdateSubModelRequest(Credentials credentials, String relative_remote_model_path, String relative_local_model_path, Set<String> elementIds2, RemotePreferences preferences) {
		super(credentials, null);
		this.eCommand = ECommand.UPDATE_SUBMODEL_REQUEST;
		this.relative_remote_model_path = relative_remote_model_path;
		this.relative_local_model_path = relative_local_model_path;
		this.elementIDs = elementIds2;
		this.preferences = preferences;
	}
	
	/**
	 * Path of a requested remote model file relative to the current user directory.
	 *  
	 * @return
	 * 		 path of a requested remote model file relative to the current user directory
	 */
	public String getRelativeRemoteModelPath() {
		return relative_remote_model_path;
	}

	/**
	 * Path of a requested remote file relative to the current user directory.
	 *  
	 * @return
	 * 		path of the local model file starting with the project name
	 */
	public String getRelativeLocalModelPath() {
		return relative_local_model_path;
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getElementIDs() {
		return elementIDs;
	}
	
	/**
	 * 
	 * @return
	 */
	public RemotePreferences getPreferences() {
		return preferences;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(super.toString());
		stringBuilder.append("local model path: " + relative_local_model_path);
		stringBuilder.append("element IDs: " + elementIDs);
		return stringBuilder.toString();
	}
}
