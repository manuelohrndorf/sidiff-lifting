package org.sidiff.remote.application.ui.connector.decorators;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.sidiff.remote.application.connector.exception.InvalidProjectInfoException;
import org.sidiff.remote.application.connector.meta.ModelInfo;
import org.sidiff.remote.application.connector.meta.ProjectInfo;

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
		try {
			if(containsModifedResource(resource)){
				decoration.addPrefix("> ");
			}
		} catch (InvalidProjectInfoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	private boolean containsModifedResource(IResource resource) throws InvalidProjectInfoException {

		IProject project = null;
		if(resource instanceof IProject) {
			project = (IProject) resource;
		}else if(resource instanceof IFolder) {
			project = ((IFolder)resource).getProject();
		}else if(resource instanceof IFile) {
			project = ((IFile)resource).getProject();
		}
		
		if(project != null && project.isOpen()) {
			ProjectInfo projectInfo = ProjectInfo.readProjectInfo(project.getName());
			for (ModelInfo modelInfo : projectInfo.getModelInfos()) {
				if(modelInfo.isModified() && modelInfo.getRelativeLocalPath().startsWith(resource.getFullPath().toOSString().substring(1))) {
					return true;
				}
			}
		}
		return false;
	}

}
