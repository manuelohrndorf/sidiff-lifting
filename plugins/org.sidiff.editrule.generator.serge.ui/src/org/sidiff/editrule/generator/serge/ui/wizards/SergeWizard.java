package org.sidiff.editrule.generator.serge.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.sidiff.editrule.generator.serge.ui.jobs.EditRuleGenerationJob;

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

	private SergeSettings settings;

	/**
	 * Constructor for SergeNewWizard.
	 */
	public SergeWizard(IFile configFile) {
		String outputPath = configFile.getProject().getLocationURI().getSchemeSpecificPart() + "/editrules/generated";
		settings = new SergeSettings(outputPath, configFile.getLocation().toOSString(), true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */
	@Override
	public void addPages() {
		addPage(new SergeWizardPage1(settings));
		addPage(new SergeWizardPage2(settings));
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	@Override
	public boolean performFinish() {
		Job job = new EditRuleGenerationJob(settings);
		job.setUser(true);
		job.schedule();
		return true;
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}
}