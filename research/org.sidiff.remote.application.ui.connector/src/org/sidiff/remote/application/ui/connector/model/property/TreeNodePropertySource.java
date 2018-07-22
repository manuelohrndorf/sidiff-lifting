package org.sidiff.remote.application.ui.connector.model.property;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeNode;
import org.sidiff.remote.application.ui.connector.model.TreeNodeProperty;

public class TreeNodePropertySource implements IPropertySource {

	private AdaptableTreeNode treeNode;
	
	private IPropertyDescriptor[] propertyDescriptors;
	
	public TreeNodePropertySource(AdaptableTreeNode adaptableTreeNode) {
		this.treeNode = adaptableTreeNode;
	}

	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if(this.propertyDescriptors == null) {
			List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
			for(TreeNodeProperty property : treeNode.getProperties()) {
				IPropertyDescriptor propertyDescriptor = new TextPropertyDescriptor(property.getName(), property.getName());
				descriptors.add(propertyDescriptor);
			}
			this.propertyDescriptors = descriptors.toArray(new IPropertyDescriptor[] {});
		}
		return this.propertyDescriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
		return treeNode.getProperty((String) id).getValue();
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub

	}

}
