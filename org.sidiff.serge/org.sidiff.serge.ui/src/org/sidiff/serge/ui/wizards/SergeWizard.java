package org.sidiff.serge.ui.wizards;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.progress.UIJob;
import org.sidiff.common.emf.exceptions.EPackageNotFoundException;
import org.sidiff.serge.Serge;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.sidiff.serge.settings.SergeSettings;
import org.sidiff.serge.ui.Activator;
import org.sidiff.serge.ui.dialogs.EPackageNotFoundDialog;

/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "mpe". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class SergeWizard extends Wizard implements INewWizard {
	private SergeWizardPage1 page;
	

	private SergeWizardPage2 page2;
	private ISelection selection;
	
	SergeSettings settings;
	

	/**
	 * Constructor for SergeNewWizard.
	 */
	public SergeWizard(IFile configFile) {		

		settings = new SergeSettings(null, configFile.getLocation().toOSString());
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new SergeWizardPage1(selection,settings);
		page2 = new SergeWizardPage2(selection,settings);
		addPage(page);
		addPage(page2);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		UIJob job = new UIJob("Generating CPEOs") {

			//TODO implement this in the right way!
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					Serge serge = new Serge(settings);
					serge.generate();
				}catch (IOException e) {
					//TODO dialog for problems while accessing harddisk
					e.printStackTrace();
				} catch (EPackageNotFoundException e) {
					EPackageNotFoundDialog.openErrorDialog(Activator.PLUGIN_ID, e);
					return Status.CANCEL_STATUS;
				} catch (OperationTypeNotImplementedException e) {
					e.printStackTrace();
				} 					
				
				return Status.OK_STATUS;
			}
			
		};		
		job.schedule();
		return true;
	}
	
	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	public void setPage(SergeWizardPage1 page) {
		this.page = page;
	}

	public void setPage2(SergeWizardPage2 page2) {
		this.page2 = page2;
	}

	public void setSelection(ISelection selection) {
		this.selection = selection;
	}

	
}