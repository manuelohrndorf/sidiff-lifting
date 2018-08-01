package org.sidiff.remote.application.ui.connector.decorators;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.ModelNotVersionedException;

public class DirtyResourceDecorator implements ILightweightLabelDecorator {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		
		IResource resource = (IResource) element;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String local_model_path = resource.getLocation().toOSString().replace(workspace.getRoot().getLocation().toOSString() + File.separator, "");
		try {
			if(ConnectorFacade.getSession().isVersioned(local_model_path) && ConnectorFacade.getSession().isModified(local_model_path) || containsModifedResource(resource)){
				decoration.addPrefix("> ");
			}
		} catch (ModelNotVersionedException | InvalidSessionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	private boolean containsModifedResource(IResource resource) throws ModelNotVersionedException, InvalidSessionException {
		if(resource instanceof IProject) {
			
		}else if(resource instanceof IFolder) {
			IFolder folder = (IFolder) resource;
			for(String local_model_path : ConnectorFacade.getSession().getLocalModelPaths()) {
				if(ConnectorFacade.getSession().isModified(local_model_path) && local_model_path.startsWith(folder.getFullPath().toOSString().substring(1))) {
					return true;
				}
			}
		}
		return false;
	}

}
