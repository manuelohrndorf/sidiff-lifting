package org.sidiff.slicer.structural.configuration.descriptor;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.sidiff.slicer.structural.configuration.IConstraintInterpreter;

public class SlicingConfigurationConstraintInterpreterIDPropertyDescriptor extends ItemPropertyDescriptor {

	public SlicingConfigurationConstraintInterpreterIDPropertyDescriptor(AdapterFactory adapterFactory, ResourceLocator resourceLocator,
			String displayName, String description, EStructuralFeature feature, boolean isSettable, boolean multiLine,
			boolean sortChoices, Object staticImage, String category, String[] filterFlags) {
		super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices,
				staticImage, category, filterFlags);
	}

	@Override
	public Collection<String> getChoiceOfValues(Object object) {
		return IConstraintInterpreter.MANAGER.getExtensions().stream()
				.map(IConstraintInterpreter::getKey)
				.collect(Collectors.toList());
	}
}
