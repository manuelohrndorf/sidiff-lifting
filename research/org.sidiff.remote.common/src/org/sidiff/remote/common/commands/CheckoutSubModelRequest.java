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
public class CheckoutSubModelRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6012399804807240975L;

	/**
	 * path of a requested remote model file relative to the current user directory
	 */
	private String relative_remote_model_path;
	
	/**
	 * path of the local model file relative to the workspace
	 */
	private String relative_local_model_path;
	
	/**
	 * IDs of the requested model elements
	 */
	private Set<String> elementIDs;
	
	/**
	 * Preferences to be used for extracting the submodel
	 */
	private RemotePreferences preferences;

	/**
	 * 
	 * @param credentials
	 *            The current {@link Credentials}
	 * @param relative_remote_model_path
	 *            path of a requested remote model file relative to the current user directory
	 * @param relative_local_model_path
	 *            path of the local model file relative to the workspace
	 * @param elementIDs
	 *            the IDs of a requested model elements
	 */
	public CheckoutSubModelRequest(Credentials credentials, String relative_remote_model_path, String relative_local_model_path, Set<String> elementIDs, RemotePreferences preferences) {
		super(credentials, null);
		this.eCommand = ECommand.CHECKOUT_SUB_MODEL_REQUEST;
		this.relative_remote_model_path = relative_remote_model_path;
		this.relative_local_model_path = relative_local_model_path;
		this.elementIDs = elementIDs;
		this.preferences = preferences;
	}

	/**
	 * Path of a requested remote model file relative to the current user directory.
	 * 
	 * @return path of a requested remote model file relative to the current user
	 *         directory
	 */
	public String getRelativeRemoteModelPath() {
		return relative_remote_model_path;
	}

	/**
	 * Path of a requested remote file relative to the current user directory.
	 * 
	 * @return path of the local model file relative to the workspace
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
		stringBuilder.append("remote model path: " + relative_remote_model_path);
		stringBuilder.append("element IDs: " + elementIDs);
		return stringBuilder.toString();
	}
}
