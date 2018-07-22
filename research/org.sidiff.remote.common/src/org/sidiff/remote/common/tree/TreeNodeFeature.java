package org.sidiff.remote.common.tree;

import java.io.Serializable;

public class TreeNodeFeature implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8234068136115110727L;

	public String name;
	
	public String value;
	
	public TreeNodeFeature(String name, String value) {
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
