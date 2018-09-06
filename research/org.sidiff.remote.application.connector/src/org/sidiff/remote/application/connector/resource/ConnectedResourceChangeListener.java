package org.sidiff.remote.application.connector.resource;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.sidiff.remote.application.connector.exception.InvalidProjectInfoException;
import org.sidiff.remote.application.connector.exception.ModelNotVersionedException;
import org.sidiff.remote.application.connector.meta.ProjectInfo;
import org.sidiff.remote.common.util.ChecksumUtil;

public class ConnectedResourceChangeListener implements IResourceChangeListener {

	
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
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
				if (delta.getKind() != IResourceDelta.CHANGED && delta.getKind() != IResourceDelta.REMOVED) {
					return true;
				}
				IResource resource = delta.getResource();
				if (resource.getType() == IResource.FILE) {
					IFile file = (IFile) resource;
					IProject project = file.getProject();
					ProjectInfo projectInfo;
					try {
						projectInfo = ProjectInfo.readProjectInfo(project.getName());
						String local_model_path = resource.getLocation().toOSString()
								.replace(resource.getWorkspace().getRoot().getLocation().toOSString() + File.separator, "");
						if (projectInfo.isVersioned(local_model_path)) {
							if(delta.getKind() == IResourceDelta.CHANGED) {
								String checksum;
								try {
									checksum = ChecksumUtil.getFileChecksum(new File(resource.getLocation().toOSString()));
									if (!projectInfo.getModelChecksum(local_model_path)
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
					} catch (InvalidProjectInfoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				return true;
			}

		};
		

		try {
			resourceDelta.accept(visitor);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
