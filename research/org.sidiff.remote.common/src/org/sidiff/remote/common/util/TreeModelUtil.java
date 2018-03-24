package org.sidiff.remote.common.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.remote.common.tree.TreeLeaf;
import org.sidiff.remote.common.tree.TreeModel;
import org.sidiff.remote.common.tree.TreeNode;

public class TreeModelUtil {

	public static TreeModel convertEMFResource(UUIDResource resource) {
		Map<EObject, TreeNode> nodes = new HashMap<EObject, TreeNode>();
		TreeModel treeModel = new TreeModel();
		for (Iterator<EObject> iterator = resource.getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			String label = getLabel(eObject);
			String id = resource.getID(eObject);
			String type = eObject.eClass().getEPackage().getNsURI() + "#//" + eObject.eClass().getName();
			TreeNode parent = eObject.eContainer() != null ? nodes.get(eObject.eContainer()) : treeModel.getRoot();
			if(eObject.eContents().isEmpty()) {
				nodes.put(eObject, new TreeLeaf(label, id, type, parent));
			}else {
				nodes.put(eObject, new TreeNode(label, id, type, parent));
			}
		}
		return treeModel;
	}
	
	public static TreeModel convertFileList(List<File> files, String rootName) {
		TreeModel treeModel = new TreeModel();
		Map<File, TreeNode> nodes = new HashMap<File, TreeNode>();
		for(File file : files) {
			transform(file, nodes, rootName, treeModel);
		}
		return treeModel;
	}
	
	private static TreeNode transform(File file, Map<File, TreeNode> nodes, String rootName, TreeModel treeModel) {
		String label = file.getName();
		String id = file.getPath().toString().substring(file.getPath().toString().indexOf(rootName));
		String type = file.isDirectory() ? "Folder" : "File";
		if(nodes.containsKey(file)) {
			return nodes.get(file);
		}
		else if(file.isDirectory()) {
			if(file.getName().equals(rootName)) {
				TreeNode treeNode = new TreeNode(label, id, type, treeModel.getRoot());
				nodes.put(file, treeNode);
				return treeNode;
			}else {
				TreeNode parentNode = transform(file.getParentFile(), nodes, rootName, treeModel);
				TreeNode treeNode = new TreeNode(label, id, type, parentNode);
				nodes.put(file, treeNode);
				return treeNode;
			}
		}else {
			TreeNode parentNode = transform(file.getParentFile(), nodes, rootName, treeModel);
			TreeLeaf treeLeaf= new TreeLeaf(label, id, type, parentNode);
			nodes.put(file, treeLeaf);
			return treeLeaf;
		}
	}

	
	private static String getLabel(EObject eObject) {
		
		String label = "";
		boolean hasName = false;
		EStructuralFeature nameFeature = eObject.eClass().getEStructuralFeature("name");
		if (nameFeature != null) {
			Object nameValue = eObject.eGet(nameFeature);
			
			if (nameValue != null && nameValue instanceof String && !((String)nameValue).isEmpty()) {
				label += (String) nameValue;
				hasName = true;
			}
		}
		
		if(!hasName){
			// Remove Object ID if present:
			label += eObject.toString().replaceFirst("@.*?\\s", "").replaceAll("\\(.*\\)", "").replaceAll("\\s", "");
		}
				
		return label;
	}
}
