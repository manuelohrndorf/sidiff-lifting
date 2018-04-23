package org.sidiff.remote.common.commands;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

public class BrowseModelFilesRequest extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1057205512420394166L;

	private String local_model_path;
	
	/**
	 * 
	 * @param session
	 * @param eCommand
	 * @param attachment
	 */
	public BrowseModelFilesRequest(Session session, String local_model_path) {
		super(session, null);
		this.eCommand = ECommand.BROWSE_MODEL_FILES_REQUEST;
		if(local_model_path != null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			this.local_model_path = local_model_path.replace(workspace.getRoot().getLocation().toOSString() + File.separator, "");
		}
	}
	
	public String getLocalModelPath() {
		return local_model_path;
	}
}
