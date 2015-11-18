package org.sidiff.editrule.generator.serge.ui.wizards;

import javax.swing.JFrame;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.sidiff.editrule.generator.exceptions.EditRuleGenerationException;

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
	
	private SergeSettings settings;
	private JFrame frame;
	private Job job;
	
	private Boolean cancelable = false;
	
	/**
	 * Constructor for SergeNewWizard.
	 */
	public SergeWizard(IFile configFile) {		

		settings = new SergeSettings(null, configFile.getLocation().toOSString(), true);
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
	
		job = new Job("EditRuleGeneration") {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IWorkbenchWindow currentWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				monitor.beginTask("Generating Edit Rules", 100);		
				
				Serge serge = new Serge();
				try {					
					serge.init(settings, new SubProgressMonitor(monitor, 20));
					serge.generateEditRules(new SubProgressMonitor(monitor, 80));
				} catch (EditRuleGenerationException e) {
					//FIXME: the ActiveWorkbenchWindow gets lost with non-UI calls
					MessageDialog.openError(currentWindow.getShell(), "An Error occurred during generation",
							e.getMessage());
				}
				finally{
					monitor.done();
				}
				
				if (monitor.isCanceled()){
					return Status.CANCEL_STATUS;
				}
				
								
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
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

	public Boolean getCancelable() {
		return cancelable;
	}

	public void setCancelable(Boolean cancelable) {
		this.cancelable = cancelable;
	}	
}