package org.sidiff.serge;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;

public interface SergeService {
	
	public void generate(Class<?> service, String outputFolder, Resource metaModel, String config, String workspace_loc);
}
