package org.sidiff.serge.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.sidiff.serge.app.SergeApp;

public class ContextMenuAction implements IObjectActionDelegate{

	private Shell shell;
	
	/**
	 * Constructor
	 */
	public ContextMenuAction() {
		super();
	}
	
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();	
	}
	
	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		
		SergeApp sergeApp = new SergeApp();
//		sergeApp.run(pathToConfig);
		
		MessageDialog.openInformation(
				shell,
				"Notice",
				"CPEOs have been generated (..hopefully)");
		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		
		if(selection instanceof IFile) {
			IFile configFile = (IFile) selection;
		}
		
	}

	


}
