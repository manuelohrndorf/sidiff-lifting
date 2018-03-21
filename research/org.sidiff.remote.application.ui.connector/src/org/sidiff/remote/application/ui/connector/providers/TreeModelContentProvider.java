package org.sidiff.remote.application.ui.connector.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.sidiff.remote.common.tree.TreeModel;
import org.sidiff.remote.common.tree.TreeNode;

public class TreeModelContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		TreeModel treeModel = (TreeModel) inputElement;
		return treeModel.getRoot().getChildren().toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof TreeNode) {
			return ((TreeNode)parentElement).getChildren().toArray();
		}
		return new Object[] {};
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof TreeNode) {
			((TreeNode)element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

}
