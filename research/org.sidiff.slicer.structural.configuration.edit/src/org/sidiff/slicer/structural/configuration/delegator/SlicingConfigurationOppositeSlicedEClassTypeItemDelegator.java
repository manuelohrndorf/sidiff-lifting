package org.sidiff.slicer.structural.configuration.delegator;

import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.sidiff.slicer.structural.configuration.SlicedEClass;

public class SlicingConfigurationOppositeSlicedEClassTypeItemDelegator extends AdapterFactoryItemDelegator {

	public SlicingConfigurationOppositeSlicedEClassTypeItemDelegator(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getText(Object object) {
		String text = "";
		if(object instanceof Map<?, ?>){
			Map<EClass,SlicedEClass> oppositeSlicedEClassType = (Map<EClass,SlicedEClass>)object;
			for(EClass key : oppositeSlicedEClassType.keySet()){
				if(key.eIsProxy())
				{
					text += EcoreUtil.getURI(key);
				}
				else
				{
					text += key.getName();
				}
				
				text += " : " + oppositeSlicedEClassType.get(key).eClass().getName() + ", ";
			}
			if(text.contains(",")){
				text = text.substring(0, text.length()-2);
			}
			return text;
		}
		return super.getText(object);
	}
	
	

}
