package org.sidiff.remote.application.ui.connector.model;

/**
 * 
 * @author cpietsch
 *
 */
public class AdaptableTreeRoot extends AdaptableTreeNode {

	public AdaptableTreeRoot() {
		super("", "root", "na", false);
	}
	
	@Override
	public int getSize() {
		int size = 0;
		for(AdaptableTreeNode child : children) {
			size += child.getSize();
		}
		return size;
	}
}
