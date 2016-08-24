package org.sidiff.slicing.configuration.provider.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.sidiff.slicing.configuration.SlicedBoundaryEReference;
import org.sidiff.slicing.configuration.SlicedEClass;

public class SlicedBoundaryEReferenceTypePropertyDescriptor extends ItemPropertyDescriptor {

	public SlicedBoundaryEReferenceTypePropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName, String description, EStructuralFeature feature,
			boolean isSettable, boolean multiLine, boolean sortChoices, Object staticImage, String category,
			String[] filterFlags) {
		super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices,
				staticImage, category, filterFlags);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Collection<?> getComboBoxObjects(Object object) {
		if(object instanceof SlicedBoundaryEReference){
			Collection<EReference> eReferences = new ArrayList<EReference>();
			SlicedBoundaryEReference slicedBoundaryEReference = (SlicedBoundaryEReference) object;
			if(slicedBoundaryEReference.getSource() != null){
				SlicedEClass srcSlicedEClass = slicedBoundaryEReference.getSource();
				EClass srcSlicedEClassType = srcSlicedEClass.getType();
				if(srcSlicedEClassType != null){
					for(EReference eReference : srcSlicedEClassType.getEAllReferences()){
						if(!srcSlicedEClass.getSlicingConfiguration().getOppositeSlicedEClassType().keySet().contains(eReference.getEType())){
							eReferences.add(eReference);
						}
					}
				}
			}else if(slicedBoundaryEReference.getTarget() != null){
				SlicedEClass tgtSlicedEClass = slicedBoundaryEReference.getTarget();
				EClass tgtSlicedEClassType = tgtSlicedEClass.getType();
				if(tgtSlicedEClassType != null){
					for(EPackage ePackage : tgtSlicedEClass.getSlicingConfiguration().getImports()){
						for (Iterator<EObject> iterator = ePackage.eAllContents(); iterator.hasNext();) {
							EObject eObject = iterator.next();
							if(eObject instanceof EClass){
								for(EReference eReference : ((EClass)eObject).getEAllReferences()){
									if(eReference.getEType().equals(tgtSlicedEClassType) && !tgtSlicedEClass.getSlicingConfiguration().getOppositeSlicedEClassType().keySet().contains(eObject)){
										eReferences.add(eReference);
									}
								}
							}
						}
					}
				}
			}
			return eReferences;
		}
		return super.getComboBoxObjects(object);
	}

}
