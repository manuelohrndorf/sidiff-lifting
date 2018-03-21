package org.sidiff.remote.common.tree;

import java.util.ArrayList;

/**
 * 
 * @author cpietsch
 *
 */
public class TreeLeaf extends TreeNode {


	public TreeLeaf(String label, String id, String type) {
		super(label, id, type);
	}
	
	public TreeLeaf(String label, String id, String type, TreeNode parent) {
		super(label, id, type, parent, new ArrayList<TreeNode>());
	}
	
}
