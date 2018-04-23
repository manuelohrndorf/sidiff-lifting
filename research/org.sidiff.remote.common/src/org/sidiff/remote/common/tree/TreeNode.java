package org.sidiff.remote.common.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cpietsch
 *
 */
public class TreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7346986002681957935L;

	protected String label;
	
	protected String id;
	
	protected String type;
	
	protected boolean selected;
	
	protected TreeNode parent;
		
	protected List<TreeNode> children;
	
	public TreeNode(String label, String id, String type) {
		this.label = label;
		this.id = id;
		this.type = type;
		this.selected = false;
		this.parent = null;
		this.children = new ArrayList<TreeNode>();
	}
	
	public TreeNode(String label, String id, String type, TreeNode parent) {
		this(label, id, type);
		this.parent = parent;
		this.parent.getChildren().add(this);
	}
	
	public boolean hasChildren() {
		return this.children.size() > 0;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	public List<TreeNode> getChildren() {
		return children;
	}
	
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public String getPath() {
		String path = label;
		if(parent != null) {
			path = parent.getPath() + "/" + path;
		}
		return path;
	}
}
