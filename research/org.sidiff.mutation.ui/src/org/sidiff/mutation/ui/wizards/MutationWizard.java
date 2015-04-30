package org.sidiff.mutation.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.sidiff.mutation.Mutator;
import org.sidiff.mutation.config.MutationConfig;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the
 * provided container. If the container resource (a folder or a project) is
 * selected in the workspace when the wizard is opened, it will accept it as the
 * target container. The wizard creates one file with the extension "mpe". If a
 * sample multi-page editor (also available as a template) is registered for the
 * same extension, it will be able to open it.
 */
public class MutationWizard extends Wizard implements INewWizard {

	private MutationWizardPage page;

	private ISelection selection;

	private Resource targetModel;

	private Job job;

	private boolean cancelable = true;

	/**
	 * Creates a Wizard with a generic resource as target model
	 */
	public MutationWizard(Resource targetModel) {
		this.targetModel = targetModel;
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new MutationWizardPage(targetModel);
		addPage(page);
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {

		final MutationConfig config = new MutationConfig();
		page.updateConfiguration(config);

		job = new Job("Model Mutation") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				// Do the job
				Mutator mutator = new Mutator(config);
				mutator.mutate(monitor);

				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}

				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.LONG);
		job.setUser(true);
		job.schedule();
		return true;

	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	public void setPage(MutationWizardPage page) {
		this.page = page;
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