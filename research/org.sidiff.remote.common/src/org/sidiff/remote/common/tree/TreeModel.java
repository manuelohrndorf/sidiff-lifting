package org.sidiff.remote.common.tree;

import java.io.Serializable;

/**
 * 
 * @author cpietsch
 *
 */
public class TreeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2924037200371387692L;
	private TreeRoot root;
	
	public TreeModel() {
		this.root = new TreeRoot();
	}
	
	public TreeRoot getRoot() {
		return root;
	}
}
