package org.sidiff.remote.application.ui.connector.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

/**
 * 
 * @author cpietsch
 *
 */
public class AdaptableTreeModel implements IAdaptable {

	private AdaptableTreeRoot root;
	
	public AdaptableTreeModel() {
		this.root = new AdaptableTreeRoot();
	}
	
	public AdaptableTreeNode getTreeNode(String id) {
		return getTreeNode(id, this.root);
	}
	
	private AdaptableTreeNode getTreeNode(String id, AdaptableTreeNode node) {
		AdaptableTreeNode result = null;
		if(node.getId().equals(id)) {
			result = node;
		}else {
			for(AdaptableTreeNode children : node.getChildren()) {
				result = getTreeNode(id, children);
				if(result != null) {
					break;
				}
			}
		}
		return result;
	}
	
	public AdaptableTreeNode[] getSelectedElements() {
		List<AdaptableTreeNode> selectedElements = new ArrayList<AdaptableTreeNode>();
		for(AdaptableTreeNode treeNode : getAllElements()) {
			if(treeNode.isSelected()) {
				selectedElements.add(treeNode);
			}
		}
		return selectedElements.toArray(new AdaptableTreeNode[] {});
	}
	
	public AdaptableTreeNode[] getAllElements() {
		List<AdaptableTreeNode> elements = new ArrayList<AdaptableTreeNode>();
		for(AdaptableTreeNode children : this.getRoot().getChildren()) {
			elements.addAll(getAllElements(children));
		}
		return elements.toArray(new AdaptableTreeNode[] {});
	}
	
	private List<AdaptableTreeNode> getAllElements(AdaptableTreeNode treeNode) {
		List<AdaptableTreeNode> elements = new ArrayList<AdaptableTreeNode>();
		elements.add(treeNode);
		for(AdaptableTreeNode children : treeNode.getChildren()) {
			elements.addAll(getAllElements(children));
		}
		return elements;
	}
	public AdaptableTreeRoot getRoot() {
		return root;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public int getSize() {
		return root.getSize();
	}
}
