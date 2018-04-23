package org.sidiff.remote.common.commands;

import java.util.List;

import org.sidiff.remote.common.Session;

public class UpdateSubModelRequest extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5030169672146475191L;

	private String local_model_path;
	
	private List<String> elementIds;
	
	public UpdateSubModelRequest(Session session, String local_model_path, List<String> elementIds) {
		super(session, null);
		this.local_model_path = local_model_path;
		this.elementIds = elementIds;
	}

	public String getLocalModelPath() {
		return local_model_path;
	}
	
	public List<String> getElementIds() {
		return elementIds;
	}
}
