package org.sidiff.slicer.structural.configuration.descriptor;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.sidiff.slicer.structural.configuration.delegator.SlicingConfigurationOppositeSlicedEClassTypeItemDelegator;

public class SlicingConfigurationOppositeSlicedEClassTypePropertyDescriptor extends ItemPropertyDescriptor {

	public SlicingConfigurationOppositeSlicedEClassTypePropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName, String description, EStructuralFeature feature,
			boolean isSettable, boolean multiLine, boolean sortChoices, Object staticImage, String category,
			String[] filterFlags) {
		super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices,
				staticImage, category, filterFlags);
		itemDelegator = new SlicingConfigurationOppositeSlicedEClassTypeItemDelegator(adapterFactory);
	}

}
