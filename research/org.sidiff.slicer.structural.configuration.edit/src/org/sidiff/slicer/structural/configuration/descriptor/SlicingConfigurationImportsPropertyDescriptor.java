package org.sidiff.slicer.structural.configuration.descriptor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.delegator.SlicingConfigurationImportsItemDelegator;

public class SlicingConfigurationImportsPropertyDescriptor extends ItemPropertyDescriptor {

	public SlicingConfigurationImportsPropertyDescriptor(AdapterFactory adapterFactory, ResourceLocator resourceLocator,
			String displayName, String description, EStructuralFeature feature, boolean isSettable, boolean multiLine,
			boolean sortChoices, Object staticImage, String category, String[] filterFlags) {
		super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices,
				staticImage, category, filterFlags);
		itemDelegator = new SlicingConfigurationImportsItemDelegator(adapterFactory);
	}

	@Override
	public Collection<EPackage> getChoiceOfValues(Object object) {
		Set<EPackage> ePackages = new HashSet<EPackage>();
		if(object instanceof SlicingConfiguration){
			SlicingConfiguration slicingConfiguration = (SlicingConfiguration) object;
			Map<String, URI> nsURIMap = EcorePlugin.getEPackageNsURIToGenModelLocationMap(true);
			
			for(String nsURI : nsURIMap.keySet()){
				EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsURI);
				if(ePackage != null){
					ePackages.add(ePackage);
					slicingConfiguration.eResource().getResourceSet().getPackageRegistry().put(nsURI, ePackage);
				}
			}
		}
		return ePackages;
	}
}
