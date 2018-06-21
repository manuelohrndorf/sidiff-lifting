package org.sidiff.slicer.structural.configuration.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;

/**
 * This action opens the Properties view.
 * @author rmueller
 *
 */
public class ShowPropertiesViewAction extends Action
{
	public ShowPropertiesViewAction()
	{
		super(ConfigurationEditorPlugin.getSubstitutedString("_UI_ShowPropertiesView_menu_item"), //$NON-NLS-1$
				ConfigurationEditorPlugin.getImageDescriptor(ConfigurationEditorPlugin.IMAGE_PROPERTIES));
	}

	@Override
	public void run()
	{
		try
		{
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.showView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-1$
		}
		catch(PartInitException exception)
		{
			ConfigurationEditorPlugin.INSTANCE.log(exception);
		}
	}
}