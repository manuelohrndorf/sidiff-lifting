package org.sidiff.vcmsintegration.structureview.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.sidiff.difference.asymmetric.OperationInvocation;

/**
 * INFORMATION: This class is part of a feature that is not complete working.
 * Most of the code below is copy-pasted from online articles, WIP, for
 * debugging purposes and not productive.
 * 
 * @author Adrian Bingener & Felix Breitweiser
 *
 */
public class OperationInvocationPropertySource implements IPropertySource {

	private final OperationInvocation operationInvocation;

	public OperationInvocationPropertySource(OperationInvocation operationInvocation) {
		this.operationInvocation = operationInvocation;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		descriptors.add(new PropertyDescriptor("description", "Description"));
		descriptors.add(new PropertyDescriptor("name", "Name"));
		descriptors.add(new PropertyDescriptor("status", "Status"));
		descriptors.add(new PropertyDescriptor("dependencies", "Dependencies"));
		return descriptors.toArray(new IPropertyDescriptor[descriptors.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		OperationInvocation op = operationInvocation;
		String output = "";
		if (id.equals("description")) {
			output = op.getChangeSet().getDescription();
		} else if (id.equals("name")) {
			output = op.getChangeSet().getName();
		} else if (id.equals("status")) {
			// TODO: implement?
			output = "not implemented";
		} else if (id.equals("dependencies")) {
			for (OperationInvocation pre : op.getPredecessors()) {
				output += pre.getChangeSet().getName() + " ";
			}
		}
		return output;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {

	}

	@Override
	public void setPropertyValue(Object id, Object value) {

	}

}
