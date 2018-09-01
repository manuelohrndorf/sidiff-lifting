package org.sidiff.slicer;

import java.util.Collection;
import java.util.stream.Collectors;

import org.sidiff.common.extension.TypedExtensionManager;

/**
 * Extension manager for {@link ISlicer}s.
 * @author Robert Müller
 *
 */
public class SlicerManager extends TypedExtensionManager<ISlicer> {

	SlicerManager() {
		super(ISlicer.DESCRIPTION);
	}

	/**
	 * Returns all slicers that can handle the given configuration.
	 * @param config the slicing configuration
	 * @return collection of slicers that can handle the configuration
	 */
	public Collection<ISlicer> getSlicers(ISlicingConfiguration config) {
		return getExtensions().stream()
				.filter(slicer -> slicer.canHandleConfiguration(config))
				.collect(Collectors.toList());
	}
}
