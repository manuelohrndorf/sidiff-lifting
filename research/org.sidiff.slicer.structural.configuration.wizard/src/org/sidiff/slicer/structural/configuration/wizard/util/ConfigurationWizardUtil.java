package org.sidiff.slicer.structural.configuration.wizard.util;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ISetSelectionTarget;

public class ConfigurationWizardUtil
{
	public static void revealSelection(IWorkbench workbench, ISelection targetSelection)
	{
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();
		final IWorkbenchPart activePart = page.getActivePart();
		if(activePart instanceof ISetSelectionTarget)
		{
			workbench.getActiveWorkbenchWindow().getShell().getDisplay().asyncExec(new Runnable()
			{
				public void run()
				{
					((ISetSelectionTarget)activePart).selectReveal(targetSelection);
				}
			});
		}
	}
}
