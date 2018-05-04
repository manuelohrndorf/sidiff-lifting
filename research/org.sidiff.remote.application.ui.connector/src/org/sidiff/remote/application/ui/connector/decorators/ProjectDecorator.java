package org.sidiff.remote.application.ui.connector.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.common.Session;
import org.sidiff.remote.common.exceptions.InvalidSessionException;

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
			Session session = ConnectorFacade.getSession();
			IProject project = (IProject) element;
			for (String model : session.getLocalModelPaths()) {
				if (model.startsWith(project.getName())) {
					decoration.addSuffix(" [sidiff]");
					break;
				}
			}
		} catch (InvalidSessionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
