package org.sidiff.remote.common.commands;

import java.io.File;
import java.util.List;

import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.Session;

public class CheckoutSubModelRequest extends Command{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6012399804807240975L;

	private String remote_model_path;
	
	private String local_model_path;
	
	private List<String> elementIds;

	
	public CheckoutSubModelRequest(Session session, String remote_model_path, String local_model_path, List<String> elementIds, File attachment) {
		super(session, attachment);
		this.eCommand = ECommand.CHECKOUT_SUB_MODEL_REQUEST;
		this.remote_model_path = remote_model_path;
		this.local_model_path = local_model_path;
		this.elementIds = elementIds;
	}

	public String getRemoteModelPath() {
		return remote_model_path;
	}

	public String getLocalModelPath() {
		return local_model_path;
	}

	public List<String> getElementIds() {
		return elementIds;
	}
}
