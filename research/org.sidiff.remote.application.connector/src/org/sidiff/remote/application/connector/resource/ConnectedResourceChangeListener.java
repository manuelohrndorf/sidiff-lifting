package org.sidiff.remote.application.connector.resource;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.Session;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.ModelNotVersionedException;
import org.sidiff.remote.common.util.ChecksumUtil;

public class ConnectedResourceChangeListener implements IResourceChangeListener {

	
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			Session session = ConnectorFacade.getSession();
	
		
			 //we are only interested in POST_CHANGE events
	        if (event.getType() != IResourceChangeEvent.POST_CHANGE)
	           return;
	        
	        IResourceDelta resourceDelta = event.getDelta();
	        
	        final Set<String> changedModel_paths = new HashSet<String>();
	        
	        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
	
				@Override
				public boolean visit(IResourceDelta delta) throws CoreException {
					// only interested in changed resources (not added or removed)
					if (delta.getKind() != IResourceDelta.CHANGED)
						return true;
					// only interested in content changes
					if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)
						return true;
					IResource resource = delta.getResource();
					
					if (resource.getType() == IResource.FILE) {
						String local_model_path = resource.getLocation().toOSString()
								.replace(resource.getWorkspace().getRoot().getLocation().toOSString() + File.separator, "");
						if (session.isVersioned(local_model_path)) {
							String checksum;
							try {
								checksum = ChecksumUtil.getFileChecksum(new File(resource.getLocation().toOSString()));
								if (session.getModelChecksum(local_model_path)
										.equals(checksum)) {
									changedModel_paths.add(local_model_path);
								}
							} catch (NoSuchAlgorithmException | IOException | ModelNotVersionedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					return true;
				}
	
			};
			
		
			resourceDelta.accept(visitor);
			for(String changedModel_path : changedModel_paths) {
				session.setModified(changedModel_path, true);
				ConnectorFacade.saveSession();
				
			}
		} catch (CoreException | ModelNotVersionedException | InvalidSessionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
