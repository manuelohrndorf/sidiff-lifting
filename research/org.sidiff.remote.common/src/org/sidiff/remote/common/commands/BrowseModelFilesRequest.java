package org.sidiff.remote.common.commands;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

/**
 * 
 * @author cpietsch
 *
 */
public class BrowseModelFilesRequest extends RequestCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1057205512420394166L;

	/**
	 * absolute os-based location path of the model file
	 */
	private String local_model_path;
	
	/**
	 * 
	 * @param credentials
	 * 			The current {@link Session}
	 * @param local_model_path
	 * 			absolute os-based location path of the model file that will be converted into a project relative path, can be <code>null</code>
	 */
	public BrowseModelFilesRequest(Credentials credentials, String local_model_path) {
		super(credentials, null);
		this.eCommand = ECommand.BROWSE_MODEL_FILES_REQUEST;
		if(local_model_path != null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			this.local_model_path = local_model_path.replace(workspace.getRoot().getLocation().toOSString() + File.separator, "");
		}
	}
	
	/**
	 * project relative path of the model file, can be <code>null</code>
	 * @return
	 * 		project relative path of the model file or  <code>null</code>
	 */
	public String getLocalModelPath() {
		return local_model_path;
	}
}
