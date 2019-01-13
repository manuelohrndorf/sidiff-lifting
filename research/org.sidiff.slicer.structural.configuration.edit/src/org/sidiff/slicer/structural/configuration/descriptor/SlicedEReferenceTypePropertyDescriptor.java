package org.sidiff.slicer.structural.configuration.descriptor;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicedEReference;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.delegator.SlicedEReferenceTypeItemDelegator;
import org.sidiff.slicer.structural.configuration.util.ConfigurationUtil;

public class SlicedEReferenceTypePropertyDescriptor extends ItemPropertyDescriptor {

	public SlicedEReferenceTypePropertyDescriptor(AdapterFactory adapterFactory, ResourceLocator resourceLocator,
			String displayName, String description, EStructuralFeature feature, boolean isSettable, boolean multiLine,
			boolean sortChoices, Object staticImage, String category, String[] filterFlags) {
		super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices,
				staticImage, category, filterFlags);
		itemDelegator = new SlicedEReferenceTypeItemDelegator(adapterFactory);
	}

	@Override
	public Collection<EReference> getChoiceOfValues(Object object) {
		Collection<EReference> eReferences = new ArrayList<EReference>();
		if(object instanceof SlicedEReference){
			
			SlicedEReference slicedEReference = (SlicedEReference) object;
			SlicedEClass slicedEClass = slicedEReference.getSlicedEClass();
			SlicingConfiguration config = slicedEClass.getSlicingConfiguration();
			if(slicedEClass.getType()!=null){
				for (EReference eReference : slicedEReference.getSlicedEClass().getType().getEAllReferences()) {
					if (ConfigurationUtil.isSliceable(eReference)
							&& (slicedEReference.getType() != null && slicedEReference.getType().equals(eReference)
									|| !slicedEClass.getOppositeSlicedEReferenceType().containsKey(eReference))) {

						for(EClass eClass : config.getOppositeSlicedEClassType().keySet()){
							if(eReference.getEType().equals(eClass) || eReference.getEType() instanceof EClass
									&& eClass.getEAllSuperTypes().contains(eReference.getEType())){
								eReferences.add(eReference);
								break;
							}
						}
					}
				}
			}
		}
		return eReferences;
	}

}
