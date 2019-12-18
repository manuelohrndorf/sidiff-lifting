package org.sidiff.patching.ui.view;


import java.util.stream.Collectors;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.patching.operation.OperationInvocationWrapper;

public class OperationInvocationWrapperPropertySource implements IPropertySource {

	private final OperationInvocationWrapper operationInvocationWrapper;

	public OperationInvocationWrapperPropertySource(OperationInvocationWrapper operationInvocationWrapper) {
		this.operationInvocationWrapper = operationInvocationWrapper;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
			new PropertyDescriptor("description", "Description"),
			new PropertyDescriptor("name", "Name"),
			new PropertyDescriptor("status", "Status"),
			new PropertyDescriptor("dependencies", "Dependencies")
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		OperationInvocation op = operationInvocationWrapper.getOperationInvocation();
		if (id.equals("description")) {
			return op.getChangeSet().getDescription();
		} else if (id.equals("name")){
			return op.getChangeSet().getName();
		} else if (id.equals("status")){
			return String.valueOf(operationInvocationWrapper.getStatus());
		} else if(id.equals("dependencies")) {
			return op.getPredecessors().stream()
					.map(OperationInvocation::getChangeSet)
					.map(SemanticChangeSet::getName)
					.collect(Collectors.joining(" "));
		}
		return null;
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
