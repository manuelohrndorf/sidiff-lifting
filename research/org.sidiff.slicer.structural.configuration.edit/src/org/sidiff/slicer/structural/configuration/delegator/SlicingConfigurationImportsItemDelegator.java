package org.sidiff.slicer.structural.configuration.delegator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;

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
				return "(unresolved) " + EcoreUtil.getURI(ePackage);
			}
			return ePackage.getName() + " (" + ePackage.getNsURI() + ")";
		}
		return super.getText(object);
	}
}
