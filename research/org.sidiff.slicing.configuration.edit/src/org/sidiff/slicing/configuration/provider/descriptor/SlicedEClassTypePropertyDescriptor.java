package org.sidiff.slicing.configuration.provider.descriptor;

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
import org.sidiff.slicing.configuration.SlicedEClass;
import org.sidiff.slicing.configuration.SlicingConfiguration;

public class SlicedEClassTypePropertyDescriptor extends ItemPropertyDescriptor {

	public SlicedEClassTypePropertyDescriptor(AdapterFactory adapterFactory, ResourceLocator resourceLocator,
			String displayName, String description, EStructuralFeature feature, boolean isSettable, boolean multiLine,
			boolean sortChoices, Object staticImage, String category, String[] filterFlags) {
		super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices,
				staticImage, category, filterFlags);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Collection<?> getComboBoxObjects(Object object) {
		if(object instanceof SlicedEClass){
			Collection<EClass> eClasses = new ArrayList<EClass>();
			SlicedEClass slicedEClass = (SlicedEClass)object;
			SlicingConfiguration slicingConfiguration = slicedEClass.getSlicingConfiguration();
			for(EPackage ePackage : slicingConfiguration.getImports()){
				for (Iterator<EObject> iterator = ePackage.eAllContents(); iterator.hasNext();) {
					EObject eObject = iterator.next();
					if(eObject instanceof EClass && (slicedEClass.getType()!=null && slicedEClass.getType().equals(eObject) || !slicingConfiguration.getOppositeSlicedEClassType().keySet().contains(eObject))){
						eClasses.add((EClass)eObject);
					}
				}
			}
			return eClasses;
		}
		return super.getComboBoxObjects(object);
	}

}
