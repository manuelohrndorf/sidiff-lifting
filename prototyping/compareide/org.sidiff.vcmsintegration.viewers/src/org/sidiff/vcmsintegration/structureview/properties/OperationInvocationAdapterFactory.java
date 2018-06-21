package org.sidiff.vcmsintegration.structureview.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;
import org.sidiff.difference.asymmetric.OperationInvocation;

// TODO: replace this with the adapter factory, the properties view does not work, maybe the selection is not propagated properly
/**
 * INFORMATION: This class is part of a feature that is not complete working.
 * <br>
 * A factory that creates an {@link IPropertySource} for
 * {@link OperationInvocation}s. These property sources provide methods to show
 * display attributes of complex objects in a propertiy view.
 * 
 * @author Adrian Bingener & Felix Breitweiser
 *
 */
public class OperationInvocationAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adapterType == IPropertySource.class && adaptableObject instanceof OperationInvocation) {
			return (T)new OperationInvocationPropertySource((OperationInvocation) adaptableObject);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { IPropertySource.class };
	}

}
