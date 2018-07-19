package org.sidiff.remote.application.ui.connector.providers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeNode;

/**
 * 
 * @author cpietsch
 *
 */
public class TreeModelLabelProvider extends LabelProvider {

	private static final Image FOLDER_IMG = ConnectorUIPlugin.getImageDescriptor("full/obj16/folder_obj.gif").createImage();
	private static final Image FILE_IMG = ConnectorUIPlugin.getImageDescriptor("full/obj16/file_obj.gif").createImage();
	
	private AdapterFactoryLabelProvider adapterFactoryLabelProvider;
	
	private Map<EClass, EObject> eClassInstances;
	
	public TreeModelLabelProvider(AdapterFactory adapterFactory) {
		this.adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(adapterFactory);
		this.eClassInstances = new HashMap<EClass, EObject>();
	}
	
	@Override
	public Image getImage(Object element) {
		AdaptableTreeNode treeNode = (AdaptableTreeNode) element;
		if(treeNode.getType().equals("File")) {
			return FILE_IMG;
		}else if(treeNode.getType().equals("Folder")) {
			return FOLDER_IMG;
		}else {
			String [] nsURI2Type = treeNode.getType().split("#//");
			EFactory eFactory = EPackage.Registry.INSTANCE.getEPackage(nsURI2Type[0]).getEFactoryInstance();
			if(eFactory != null) {
				EClass eClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(nsURI2Type[0]).getEClassifier(nsURI2Type[1]);
				if(this.eClassInstances.containsKey(eClass)) {
					return this.adapterFactoryLabelProvider.getImage(this.eClassInstances.get(eClass));
				}else {
					EObject eObject = eFactory.create(eClass);
					this.eClassInstances.put(eClass, eObject);
					return this.adapterFactoryLabelProvider.getImage(eObject);
				}
			}
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		AdaptableTreeNode treeNode = (AdaptableTreeNode) element;
		String text = treeNode.getLabel();
		if(treeNode.isLeaf()) {
			text = text + " [" + treeNode.getId() + "]";
		}
		return text;
	}

}
