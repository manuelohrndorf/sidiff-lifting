package org.sidiff.remote.common.commands;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

public class CheckoutSubModelRequest extends Command{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6012399804807240975L;

	private String remote_model_path;
	
	private String local_model_path;
	
	private Set<String> elementIds;

	
	public CheckoutSubModelRequest(Session session, String remote_model_path, String local_model_path, Set<String> elementIds2) {
		super(session, null);
		this.eCommand = ECommand.CHECKOUT_SUB_MODEL_REQUEST;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		this.local_model_path = local_model_path.replace(workspace.getRoot().getLocation().toOSString() + File.separator, "");
		this.remote_model_path = remote_model_path;
		this.elementIds = elementIds2;
	}

	public String getRemoteModelPath() {
		return remote_model_path;
	}

	public String getLocalModelPath() {
		return local_model_path;
	}

	public Set<String> getElementIds() {
		return elementIds;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(super.toString());
		stringBuilder.append("local model path: " + local_model_path);
		stringBuilder.append("remote model path: " + remote_model_path);
		stringBuilder.append("element IDs: " + elementIds);
		return stringBuilder.toString();
	}
}
