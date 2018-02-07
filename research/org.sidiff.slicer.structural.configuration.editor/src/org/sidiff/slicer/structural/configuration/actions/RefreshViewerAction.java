package org.sidiff.slicer.structural.configuration.actions;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;

/**
 * This action refreshes the viewer of the current editor if the editor
 * implements {@link org.eclipse.emf.common.ui.viewer.IViewerProvider}.
 * @author rmueller
 *
 */
public class RefreshViewerAction extends Action
{
	public RefreshViewerAction()
	{
		super(ConfigurationEditorPlugin.getSubstitutedString("_UI_RefreshViewer_menu_item"), //$NON-NLS-1$
				ConfigurationEditorPlugin.getImageDescriptor(ConfigurationEditorPlugin.IMAGE_REFRESH));
	}

	@Override
	public boolean isEnabled()
	{
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor() instanceof IViewerProvider;
	}

	@Override
	public void run()
	{
		IEditorPart activeEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(activeEditorPart instanceof IViewerProvider)
		{
			Viewer viewer = ((IViewerProvider)activeEditorPart).getViewer();
			if(viewer != null)
			{
				viewer.refresh();
			}
		}
	}
}