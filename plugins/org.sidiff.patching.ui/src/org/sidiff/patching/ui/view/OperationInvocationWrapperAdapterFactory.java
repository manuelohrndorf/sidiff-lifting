package org.sidiff.patching.ui.view;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;
import org.sidiff.patching.operation.OperationInvocationWrapper;

public class OperationInvocationWrapperAdapterFactory implements IAdapterFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType== IPropertySource.class && adaptableObject instanceof OperationInvocationWrapper){
		      return new OperationInvocationWrapperPropertySource((OperationInvocationWrapper) adaptableObject);
		    }
		    return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class[] getAdapterList() {
		return new Class[] { IPropertySource.class };
	}

}
