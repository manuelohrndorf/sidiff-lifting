package org.sidiff.remote.common.tree;

/**
 * 
 * @author cpietsch
 *
 */
public class TreeLeaf extends TreeNode {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6722722252164512990L;

	public TreeLeaf(String label, String id, String type) {
		super(label, id, type);
	}
	
	public TreeLeaf(String label, String id, String type, TreeNode parent) {
		super(label, id, type, parent);
	}
	
}
