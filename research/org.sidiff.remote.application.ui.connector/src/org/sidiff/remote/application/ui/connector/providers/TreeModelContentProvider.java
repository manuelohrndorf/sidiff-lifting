package org.sidiff.remote.application.ui.connector.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeModel;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeNode;

/**
 * 
 * @author cpietsch
 *
 */
public class TreeModelContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof AdaptableTreeModel) {
			AdaptableTreeModel treeModel = (AdaptableTreeModel) parentElement;
			return treeModel.getRoot().getChildren().toArray();
		}
		if(parentElement instanceof AdaptableTreeNode) {
			return ((AdaptableTreeNode)parentElement).getChildren().toArray();
		}
		return new Object[] {};
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof AdaptableTreeNode) {
			((AdaptableTreeNode)element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

}
