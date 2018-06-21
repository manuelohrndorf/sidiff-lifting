package org.sidiff.slicer.structural.configuration.delegator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.sidiff.common.emf.EMFUtil;

public class SlicedEReferenceTypeItemDelegator extends AdapterFactoryItemDelegator {

	public SlicedEReferenceTypeItemDelegator(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public String getText(Object object) {
		if(object instanceof EReference) {
			EReference eReference = (EReference) object;
			
			// check if reference is proxy
			if(eReference.eIsProxy())
			{
				return "(unresolved) " + EMFUtil.getEObjectURI(eReference);
			}
			else
			{
				// check if containing class is proxy
				String containingClassName;
				if(eReference.getEContainingClass().eIsProxy())
				{
					containingClassName = EMFUtil.getEObjectURI(eReference.getEContainingClass());
				}
				else
				{
					containingClassName = eReference.getEContainingClass().getName();
				}
				
				// check if type is proxy
				String typeName;
				if(eReference.getEType().eIsProxy())
				{
					typeName = EMFUtil.getEObjectURI(eReference.getEType());
				}
				else
				{
					typeName = eReference.getEType().getName();
				}

				return containingClassName + "." + eReference.getName() + ": " + typeName;
			}
		}
		return super.getText(object);
	}
}
