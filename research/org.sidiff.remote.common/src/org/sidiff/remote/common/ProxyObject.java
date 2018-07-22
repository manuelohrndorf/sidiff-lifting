package org.sidiff.remote.common;

import java.io.Serializable;
import java.util.List;

public class ProxyObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String label;
	
	protected String id;
	
	protected String type;
	
	protected List<ProxyProperty> properties;
	
	protected boolean container;

	public ProxyObject(String label, String id, String type, List<ProxyProperty> properties, boolean container) {
		this.label = label;
		this.id = id;
		this.type = type;
		this.properties = properties;
		this.container = container;
	}

	public String getLabel() {
		return label;
	}
	
	public String getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}
	
	public List<ProxyProperty> getProperties() {
		return properties;
	}
	
	public boolean isContainer() {
		return container;
	}
}
