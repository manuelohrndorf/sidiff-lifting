package org.sidiff.slicer.structural.configuration.delegator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.sidiff.common.emf.EMFUtil;

public class SlicedEClassTypeItemDelegator extends AdapterFactoryItemDelegator
{
	public SlicedEClassTypeItemDelegator(AdapterFactory adapterFactory)
	{
		super(adapterFactory);
	}

	@Override
	public String getText(Object object)
	{
		if(object instanceof EClass)
		{
			EClass eClass = (EClass)object;
			
			// check if class is proxy
			if(eClass.eIsProxy())
			{
				return "(unresolved) " + EMFUtil.getEObjectURI(eClass);
			}
		}
		
		return super.getText(object);
	}
}
