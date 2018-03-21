package org.sidiff.remote.common.tree;

/**
 * 
 * @author cpietsch
 *
 */
public class TreeModel {

	private TreeRoot root;
	
	public TreeModel() {
		this.root = new TreeRoot();
	}
	
	public TreeRoot getRoot() {
		return root;
	}
}
