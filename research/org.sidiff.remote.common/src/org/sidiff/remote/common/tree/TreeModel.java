package org.sidiff.remote.common.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	
	public TreeNode getTreeNode(String id) {
		return getTreeNode(id, this.root);
	}
	
	private TreeNode getTreeNode(String id, TreeNode node) {
		TreeNode result = null;
		if(node.getId().equals(id)) {
			result = node;
		}else {
			for(TreeNode children : node.getChildren()) {
				result = getTreeNode(id, children);
				if(result != null) {
					break;
				}
			}
		}
		return result;
	}
	
	public TreeNode[] getSelectedElements() {
		List<TreeNode> selectedElements = new ArrayList<TreeNode>();
		for(TreeNode treeNode : getAllElements()) {
			if(treeNode.isSelected()) {
				selectedElements.add(treeNode);
			}
		}
		return selectedElements.toArray(new TreeNode[] {});
	}
	
	public TreeNode[] getAllElements() {
		List<TreeNode> elements = new ArrayList<TreeNode>();
		for(TreeNode children : this.getRoot().getChildren()) {
			elements.addAll(getAllElements(children));
		}
		return elements.toArray(new TreeNode[] {});
	}
	
	private List<TreeNode> getAllElements(TreeNode treeNode) {
		List<TreeNode> elements = new ArrayList<TreeNode>();
		elements.add(treeNode);
		for(TreeNode children : treeNode.getChildren()) {
			elements.addAll(getAllElements(children));
		}
		return elements;
	}
	public TreeRoot getRoot() {
		return root;
	}	
}
