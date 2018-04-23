package org.sidiff.remote.common.commands;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

public class GetRequestedModelElementsRequest extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810529056867944138L;
	
	private String local_model_path;

	public GetRequestedModelElementsRequest(Session session, String local_model_path) {
		super(session, null);
		this.eCommand = ECommand.GET_REQUESTED_MODEL_ELEMENTS_REQUEST;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		this.local_model_path = local_model_path.replace(workspace.getRoot().getLocation().toOSString() + File.separator, "");
	}

	public String getLocalModelPath() {
		return local_model_path;
	}
}
