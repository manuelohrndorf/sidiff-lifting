package org.sidiff.remote.common.commands;

import java.io.File;
import java.util.Set;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

/**
 * 
 * @author cpietsch
 *
 */
public class UpdateSubModelRequest extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5030169672146475191L;

	/**
	 * project relative model path
	 */
	private String local_model_path;
	
	private Set<String> elementIds;
	
	/**
	 * 
	 * @param session
	 * @param local_model_path
	 * 				absolute local location path that will be converted into a project relative path
	 * @param elementIds2
	 */
	public UpdateSubModelRequest(Session session, String local_model_path, Set<String> elementIds2) {
		super(session, null);
		this.eCommand = ECommand.UPDATE_SUBMODEL_REQUEST;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		this.local_model_path = local_model_path.replace(workspace.getRoot().getLocation().toOSString() + File.separator, "");
		this.elementIds = elementIds2;
	}

	/**
	 * project relative model path
	 * @return
	 */
	public String getLocalModelPath() {
		return local_model_path;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<String> getElementIds() {
		return elementIds;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(super.toString());
		stringBuilder.append("local model path: " + local_model_path);
		stringBuilder.append("element IDs: " + elementIds);
		return stringBuilder.toString();
	}
}
