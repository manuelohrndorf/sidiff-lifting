package org.sidiff.slicer.structural.configuration.delegator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;

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
				return "(unresolved) " + EcoreUtil.getURI(eReference);
			}
			// check if containing class is proxy
			String containingClassName;
			if(eReference.getEContainingClass().eIsProxy())
			{
				containingClassName = EcoreUtil.getURI(eReference.getEContainingClass()).toString();
			}
			else
			{
				containingClassName = eReference.getEContainingClass().getName().toString();
			}
			
			// check if type is proxy
			String typeName;
			if(eReference.getEType().eIsProxy())
			{
				typeName = EcoreUtil.getURI(eReference.getEType()).toString();
			}
			else
			{
				typeName = eReference.getEType().getName();
			}

			return containingClassName + "." + eReference.getName() + ": " + typeName;
		}
		return super.getText(object);
	}
}
