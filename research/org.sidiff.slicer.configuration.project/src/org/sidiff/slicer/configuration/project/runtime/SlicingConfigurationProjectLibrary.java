package org.sidiff.slicer.configuration.project.runtime;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class SlicingConfigurationProjectLibrary {

	public static List<ISlicingConfigurationProject> getAllSlicingConfigurationProjects() {
		List<ISlicingConfigurationProject> slicingConfigurationProjects = new ArrayList<ISlicingConfigurationProject>();
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(ISlicingConfigurationProject.EXTENSION_POINT_ID)) {
			try {
				ISlicingConfigurationProject slicingConfigurationProject = (ISlicingConfigurationProject) configurationElement
						.createExecutableExtension(ISlicingConfigurationProject.EXTENSION_POINT_ATTRIBUTE);
				slicingConfigurationProjects.add(slicingConfigurationProject);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return slicingConfigurationProjects;
	}
}
