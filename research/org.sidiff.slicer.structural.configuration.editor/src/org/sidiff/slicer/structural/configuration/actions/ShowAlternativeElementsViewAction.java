package org.sidiff.slicer.structural.configuration.actions;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditor;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;
import org.sidiff.slicer.structural.configuration.views.AlternativeElementsView;

/**
 * This action opens the Alternative Elements view if the current editor is the {@link ConfigurationEditor}
 * and the configuration tab is not active and the selected object is an {@link EReference}.
 * @author rmueller
 *
 */
public class ShowAlternativeElementsViewAction extends Action
{
	public ShowAlternativeElementsViewAction()
	{
		super(ConfigurationEditorPlugin.getSubstitutedString("_UI_ShowAlternativeElements_menu_item"), //$NON-NLS-1$
				ConfigurationEditorPlugin.getImageDescriptor(ConfigurationEditorPlugin.IMAGE_ALTERNATIVE_ELEMENTS));
	}

	@Override
	public boolean isEnabled()
	{
		IEditorPart activeEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(!(activeEditorPart instanceof ConfigurationEditor))
			return false;
		ConfigurationEditor confEditor = (ConfigurationEditor)activeEditorPart;
		if(confEditor.isConfigurationTabActive())
			return false;
		ISelection selection = confEditor.getSelection();
		if(selection.isEmpty() || !(selection instanceof IStructuredSelection))
			return false;
		return ((IStructuredSelection)selection).getFirstElement() instanceof EReference;
	}

	@Override
	public void run()
	{
		try
		{
			// show alternative elements view
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AlternativeElementsView.ID);
			
			// activate editor to show the selection in the alternative elements view
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.activate(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor());
		}
		catch(PartInitException exception)
		{
			ConfigurationEditorPlugin.INSTANCE.log(exception);
		}
	}
}