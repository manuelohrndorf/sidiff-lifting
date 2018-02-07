package org.sidiff.slicer.structural.configuration.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.delegator.SlicedEClassTypeItemDelegator;

public class SlicedEClassTypePropertyDescriptor extends ItemPropertyDescriptor {

	public SlicedEClassTypePropertyDescriptor(AdapterFactory adapterFactory, ResourceLocator resourceLocator,
			String displayName, String description, EStructuralFeature feature, boolean isSettable, boolean multiLine,
			boolean sortChoices, Object staticImage, String category, String[] filterFlags) {
		super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices,
				staticImage, category, filterFlags);
		itemDelegator = new SlicedEClassTypeItemDelegator(adapterFactory);
	}

	@Override
	protected Collection<EClass> getComboBoxObjects(Object object) {
		Collection<EClass> eClasses = new ArrayList<EClass>();
		if(object instanceof SlicedEClass){
			SlicedEClass slicedEClass = (SlicedEClass)object;
			for(EPackage ePackage : slicedEClass.getSlicingConfiguration().getImports()){
				for (Iterator<EObject> iterator = ePackage.eAllContents(); iterator.hasNext();) {
					EObject eObject = iterator.next();
					if (eObject instanceof EClass
							&& (slicedEClass.getType() != null && slicedEClass.getType().equals(eObject)
									|| !slicedEClass.getSlicingConfiguration().getOppositeSlicedEClassType()
											.containsKey(eObject))) {
						eClasses.add((EClass)eObject);
					}
				}
			}
		}
		return eClasses;
	}

}
