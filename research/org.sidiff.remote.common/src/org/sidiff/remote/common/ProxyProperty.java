package org.sidiff.remote.common;

import java.io.Serializable;

/**
 * 
 * @author cpietsch
 *
 */
public class ProxyProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3864313784580124674L;

	private String name;
	
	private String value;
	
	public ProxyProperty(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return name + ": " + value;
	}
	
}
