package org.sidiff.editrule.generator.serge.ui.wizards;

import java.io.IOException;

import javax.swing.JFrame;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;
import org.sidiff.common.emf.exceptions.EPackageNotFoundException;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.Serge;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;

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
				monitor.beginTask("Generating Edit Rules", 100);
				
				monitor.subTask("Initializing SERGe");
				Serge serge = new Serge();
				serge.init(settings);
				monitor.worked(25);
				
				if (monitor.isCanceled()){
					return Status.CANCEL_STATUS;
				}
				
				monitor.subTask("Generating CPEOs");
				
				try {
					serge.generateEditRules(monitor);
				} catch (IOException | EPackageNotFoundException
						| OperationTypeNotImplementedException e1) {
					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "An Error occurred during generation",
							e1.getMessage());
								}
				
				monitor.done();
								
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