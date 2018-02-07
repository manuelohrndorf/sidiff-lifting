package org.sidiff.slicer.configuration.project.runtime;

import java.util.List;
import java.util.Set;

import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.ISlicingConfiguration;

public interface ISlicingConfigurationProject {

	public static final String EXTENSION_POINT_ID = "org.sidiff.slicer.configuration.project.runtime.extension_point";
	public static final String EXTENSION_POINT_ATTRIBUTE = "class";
	
	public ISlicer getSlicer();
	
	public String getName();
	
	public Set<String> getDocumentTypes();
	
	public List<ISlicingConfiguration> getSlicingConfigurations();
}
