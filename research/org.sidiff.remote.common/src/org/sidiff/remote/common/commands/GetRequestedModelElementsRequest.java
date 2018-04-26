package org.sidiff.remote.common.commands;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

/**
 * 
 * @author cpietsch
 *
 */
public class GetRequestedModelElementsRequest extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810529056867944138L;
	
	/**
	 * project relative model path
	 */
	private String local_model_path;

	/**
	 * 
	 * @param session
	 * @param local_model_path
	 * 				absolute local location path that will be converted into a project relative path
	 */
	public GetRequestedModelElementsRequest(Session session, String local_model_path) {
		super(session, null);
		this.eCommand = ECommand.GET_REQUESTED_MODEL_ELEMENTS_REQUEST;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		this.local_model_path = local_model_path.replace(workspace.getRoot().getLocation().toOSString() + File.separator, "");
	}

	/**
	 * project relative model path
	 * @return
	 * 		project relative model path
	 */
	public String getLocalModelPath() {
		return local_model_path;
	}
}
