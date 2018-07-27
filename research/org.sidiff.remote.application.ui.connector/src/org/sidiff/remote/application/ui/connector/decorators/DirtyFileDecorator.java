package org.sidiff.remote.application.ui.connector.decorators;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.ModelNotVersionedException;

public class DirtyFileDecorator implements ILightweightLabelDecorator {

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
		
		IFile file = (IFile) element;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String local_model_path = file.getLocation().toOSString().replace(workspace.getRoot().getLocation().toOSString() + File.separator, "");
		
		try {
			if(ConnectorFacade.getSession().isVersioned(local_model_path) && ConnectorFacade.getSession().isModified(local_model_path)){
				decoration.addPrefix("> ");
			}
		} catch (ModelNotVersionedException | InvalidSessionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
				
	}

}
