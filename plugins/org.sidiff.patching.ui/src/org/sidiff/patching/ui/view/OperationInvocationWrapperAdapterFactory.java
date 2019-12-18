package org.sidiff.patching.ui.view;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;
import org.sidiff.patching.operation.OperationInvocationWrapper;

public class OperationInvocationWrapperAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adapterType == IPropertySource.class && adaptableObject instanceof OperationInvocationWrapper) {
			return adapterType.cast(new OperationInvocationWrapperPropertySource((OperationInvocationWrapper)adaptableObject));
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IPropertySource.class };
	}
}
