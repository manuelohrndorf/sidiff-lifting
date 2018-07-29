package org.sidiff.remote.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cpietsch
 *
 */
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
	
	protected ProxyObject parent;
	
	protected List<ProxyObject> children;

	public ProxyObject(String label, String id, String type, List<ProxyProperty> properties, boolean container) {
		this.label = label;
		this.id = id;
		this.type = type;
		this.properties = properties;
		this.container = container;
		this.children = new ArrayList<ProxyObject>();
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

	public ProxyObject getParent() {
		return parent;
	}

	public void setParent(ProxyObject parent) {
		this.parent = parent;
		this.parent.getChildren().add(this);
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}
	public List<ProxyObject> getChildren() {
		return children;
	}
	
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Label: " + label + "\n");
		stringBuffer.append("ID: " + id + "\n");
		stringBuffer.append("Type: " + type + "\n");
		stringBuffer.append("Properties: {\n");
		for(ProxyProperty property : properties) {
			stringBuffer.append("\t" + property + "\n");
		}
		stringBuffer.append("}\n");
		stringBuffer.append("Children: {\n");
		for(ProxyObject child : children) {
			stringBuffer.append("\t" + child + "\n");
		}
		stringBuffer.append("}\n");
		return stringBuffer.toString();
	}
}
