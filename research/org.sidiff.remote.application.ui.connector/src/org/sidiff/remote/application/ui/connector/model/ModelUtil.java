package org.sidiff.remote.application.ui.connector.model;

import java.util.ArrayList;
import java.util.List;

import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.ProxyProperty;

/**
 * 
 * @author cpietsch
 *
 */
public class ModelUtil {

	public static AdaptableTreeNode transform(ProxyObject obj) {
		AdaptableTreeNode adaptableTreeNode = null;

		adaptableTreeNode = new AdaptableTreeNode(obj.getLabel(), obj.getId(), obj.getType(), !obj.isContainer());

		for(ProxyProperty proxyProperty : obj.getProperties()) {
			TreeNodeProperty treeNodeProperty = new TreeNodeProperty(proxyProperty.getName(), proxyProperty.getValue());
			adaptableTreeNode.addProperty(treeNodeProperty);
		}
		
		return adaptableTreeNode;
		
	}
	
	public static List<AdaptableTreeNode> transform(List<ProxyObject> objs){
		List<AdaptableTreeNode> adaptableTreeNodes = new ArrayList<AdaptableTreeNode>();
		for(ProxyObject obj : objs) {
			adaptableTreeNodes.add(transform(obj));
		}
		return adaptableTreeNodes;
	}
}