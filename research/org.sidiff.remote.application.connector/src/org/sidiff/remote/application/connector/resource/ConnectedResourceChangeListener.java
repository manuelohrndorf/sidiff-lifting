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
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.ModelNotVersionedException;
import org.sidiff.remote.application.connector.session.Session;
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
	        
	        final Set<String> removedModel_paths = new HashSet<String>();
	        
	        final Set<String> changedModel_paths = new HashSet<String>();
	        
	        final Set<String> revertedModel_paths = new HashSet<String>();
	        
	        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
	
				@Override
				public boolean visit(IResourceDelta delta) throws CoreException {
					// only interested in changed or removed resources (not added)
					if (delta.getKind() != IResourceDelta.CHANGED || delta.getKind() != IResourceDelta.REMOVED) {
						return true;
					}
					IResource resource = delta.getResource();
					
					if (resource.getType() == IResource.FILE) {
						String local_model_path = resource.getLocation().toOSString()
								.replace(resource.getWorkspace().getRoot().getLocation().toOSString() + File.separator, "");
						if (session.isVersioned(local_model_path)) {
							if(delta.getKind() == IResourceDelta.CHANGED) {
								String checksum;
								try {
									checksum = ChecksumUtil.getFileChecksum(new File(resource.getLocation().toOSString()));
									if (!session.getModelChecksum(local_model_path)
											.equals(checksum)) {
										changedModel_paths.add(local_model_path);
									}else {
										revertedModel_paths.add(local_model_path);
									}
								} catch (NoSuchAlgorithmException | IOException | ModelNotVersionedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}else if(delta.getKind() == IResourceDelta.REMOVED) {
								removedModel_paths.add(local_model_path);
							}
						}
					}
					return true;
				}
	
			};
			
		
			resourceDelta.accept(visitor);
			for(String changedModel_path : changedModel_paths) {
				session.setModified(changedModel_path, true);
				
				
			}
			
			for(String revertedModel_path : revertedModel_paths) {
				session.setModified(revertedModel_path, false);
			}
			
			for(String removedModel_path : removedModel_paths) {
				session.removeModel(removedModel_path);
			}
			
			ConnectorFacade.saveSession();
		} catch (CoreException | ModelNotVersionedException | InvalidSessionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
