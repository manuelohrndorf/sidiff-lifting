package org.sidiff.remote.application.ui.connector.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.sidiff.remote.application.connector.exception.InvalidProjectInfoException;
import org.sidiff.remote.application.connector.meta.ProjectInfo;
import org.sidiff.remote.application.ui.connector.ConnectorUIPlugin;

public class ProjectDecorator implements ILightweightLabelDecorator {

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
	
		try {
			IProject project = (IProject) element;
			ProjectInfo.readProjectInfo(project.getName());
			// if no exception is thrown the project is connected to the remote application
			decoration.addSuffix(" [sidiff]");
			decoration.addOverlay(ConnectorUIPlugin.IMG_OVR_VERSION_CONTROLLED, IDecoration.BOTTOM_RIGHT);
			
		} catch (InvalidProjectInfoException e) {
			e.printStackTrace();
		}
	}
}
