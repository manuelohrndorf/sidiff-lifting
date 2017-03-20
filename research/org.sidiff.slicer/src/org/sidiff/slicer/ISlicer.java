package org.sidiff.slicer;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
public interface ISlicer {

	String EXTENSION_POINT_ID = "org.sidiff.slicer.extensionpoint";
	
	public String getKey();
	
	public String getName();
	
	public Set<String> getDocumentTypes();

	public boolean canHandleDocTypes(Set<String> documentTypes);
	
	public boolean canHandleModels(Collection<Resource> models);
	
	public void init(ISlicingConfiguration config) throws Exception;
	
	public void slice(Collection<EObject> input);
	
	public Collection<EObject> getModelSlice();
}
