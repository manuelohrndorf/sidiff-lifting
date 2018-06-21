package org.sidiff.remote.application.ui.connector.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;
import org.sidiff.remote.common.tree.TreeLeaf;
import org.sidiff.remote.common.tree.TreeNode;

public class TreeModelLabelProvider extends LabelProvider {

	private static final Image FOLDER_IMG = ConnectorUIPlugin.getImageDescriptor("full/obj16/folder_obj.gif").createImage();
	private static final Image FILE_IMG = ConnectorUIPlugin.getImageDescriptor("full/obj16/file_obj.gif").createImage();
	
	private AdapterFactoryLabelProvider adapterFactoryLabelProvider;

	
	public TreeModelLabelProvider(AdapterFactory adapterFactory) {
		this.adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(adapterFactory);
	}
	
	@Override
	public Image getImage(Object element) {
		TreeNode treeNode = (TreeNode) element;
		if(treeNode.getType().equals("File")) {
			return FILE_IMG;
		}else if(treeNode.getType().equals("Folder")) {
			return FOLDER_IMG;
		}else {
			String [] nsURI2Type = treeNode.getType().split("#//");
			EFactory eFactory = EPackage.Registry.INSTANCE.getEPackage(nsURI2Type[0]).getEFactoryInstance();
			if(eFactory != null) {
				EObject eObject = eFactory.create((EClass) EPackage.Registry.INSTANCE.getEPackage(nsURI2Type[0]).getEClassifier(nsURI2Type[1]));
				return this.adapterFactoryLabelProvider.getImage(eObject);
			}
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		TreeNode treaNode = (TreeNode) element;
		String text = treaNode.getLabel();
		if(treaNode instanceof TreeLeaf) {
			TreeLeaf treeLeaf = (TreeLeaf) element;
			text = text + " [" + treeLeaf.getId() + "]";
		}
		return text;
	}

}
