package org.sidiff.remote.application.ui.connector.model;

/**
 * 
 * @author cpietsch
 *
 */
public class TreeNodeProperty {

	public String name;
	
	public String value;
	
	public TreeNodeProperty(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	};
}
