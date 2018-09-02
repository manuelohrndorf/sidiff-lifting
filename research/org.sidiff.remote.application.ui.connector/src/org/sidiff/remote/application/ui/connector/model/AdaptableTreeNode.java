package org.sidiff.remote.application.ui.connector.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertySource;
import org.sidiff.remote.application.ui.connector.model.property.TreeNodePropertySource;

/**
 * 
 * @author cpietsch
 *
 */
public class AdaptableTreeNode implements IAdaptable {

	
	protected TreeNodePropertySource treeNodePropertySource;

	protected String label;
	
	protected String id;
	
	protected String type;
	
	protected boolean selected;
	
	protected AdaptableTreeNode parent;
		
	protected List<AdaptableTreeNode> children;
	
	protected Map<String, TreeNodeProperty> properties;
	
	protected boolean leaf;
	
	public AdaptableTreeNode(String label, String id, String type, boolean leaf) {
		this.label = label;
		this.id = id;
		this.type = type;
		this.leaf = leaf;
		this.selected = false;
		this.parent = null;
		this.children = new ArrayList<AdaptableTreeNode>();
		this.properties = new HashMap<String, TreeNodeProperty>();;
	}
	
	public AdaptableTreeNode(String label, String id, String type, boolean leaf, AdaptableTreeNode parent) {
		this(label, id, type, leaf);
		this.parent = parent;
		this.parent.addChildren(this);
	}
	
	public boolean hasChildren() {
		return this.children.size() > 0;
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
	
	
	public boolean isLeaf() {
		return leaf;
	}
	

	public boolean isSelected() {
		return selected;
	}
	
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public AdaptableTreeNode getParent() {
		return parent;
	}
	
	public void setParent(AdaptableTreeNode parent) {
		if(this.parent != parent) {
			AdaptableTreeNode oldParent = this.parent;
			this.parent = parent;
			if(oldParent != null) {
				oldParent.removeChildren(this);
			}
			if(this.parent != null) {
				this.parent.addChildren(this);
			}
		}
	}
	
	public List<AdaptableTreeNode> getChildren() {
		return Collections.unmodifiableList(children);
	}
		
	public void addChildren(AdaptableTreeNode child) {
		if(!this.children.contains(child)) {
			this.children.add(child);
			child.setParent(this);
			this.leaf = false;
		}
	}
	
	public void removeChildren(AdaptableTreeNode child) {
		if(this.children.contains(child)) {
			this.children.remove(child);
			child.setParent(null);
		}
	}
	
	public void addAllChildren(List<AdaptableTreeNode> children) {
		for(AdaptableTreeNode child : children) {
			addChildren(child);
		}
	}
	
	public void removeAllChildren(List<AdaptableTreeNode> children) {
		for(AdaptableTreeNode child: children) {
			removeChildren(child);
		}
	}
	
	public List<TreeNodeProperty> getProperties() {
		return Collections.unmodifiableList(new ArrayList<TreeNodeProperty>(properties.values()));
	}

	public void addProperty(TreeNodeProperty property) {
		this.properties.put(property.getName(), property);
	}
	
	
	public TreeNodeProperty getProperty(String id) {
		return this.properties.get(id);
	}
	
	public String getPath() {
		String path = label;
		if(parent != null) {
			path = parent.getPath() + "/" + path;
		}
		return path;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySource.class) {
			if (treeNodePropertySource == null) {
				treeNodePropertySource = new TreeNodePropertySource(this);
			}
			return treeNodePropertySource;
		 }
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AdaptableTreeNode) {
			return id.equals(((AdaptableTreeNode)obj).getId());
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
