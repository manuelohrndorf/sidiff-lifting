package org.sidiff.vcmsintegration.structureview.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;
import org.sidiff.difference.asymmetric.OperationInvocation;

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

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IPropertySource.class && adaptableObject instanceof OperationInvocation) {
			return new OperationInvocationPropertySource((OperationInvocation) adaptableObject);
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] { IPropertySource.class };
	}

}
