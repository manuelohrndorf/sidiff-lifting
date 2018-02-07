package org.sidiff.slicer.structural.configuration.delegator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.sidiff.common.emf.EMFUtil;

public class SlicingConfigurationImportsItemDelegator extends AdapterFactoryItemDelegator {

	public SlicingConfigurationImportsItemDelegator(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public String getText(Object object) {
		if(object instanceof EPackage){
			EPackage ePackage = (EPackage)object;
			if(ePackage.eIsProxy())
			{
				return "(unresolved) " + EMFUtil.getEObjectURI(ePackage);
			}
			else
			{
				return ePackage.getName() + " (" + ePackage.getNsURI() + ")";
			}
		}
		return super.getText(object);
	}
}
