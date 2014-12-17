package org.sidiff.profileapplicator.ui;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;
import org.sidiff.profileapplicator.ProfileApplicator;
import org.sidiff.profileapplicator.settings.ProfileApplicatorSettings;

public class ApplyProfilesWizard extends Wizard {

	protected ApplyProfilesWizardPage0 pg0;
	private final ProfileApplicatorSettings settings;

	public ApplyProfilesWizard(IFolder inputFolder) {
		super();
		String inputFolderPath = (inputFolder != null ? inputFolder
				.getLocation().toOSString() : "");
		this.settings = new ProfileApplicatorSettings("", "", inputFolderPath);
		setNeedsProgressMonitor(false);
	}

	@Override
	public String getWindowTitle() {
		return "Apply profiles";
	}

	@Override
	public void addPages() {
		pg0 = new ApplyProfilesWizardPage0(settings);
		addPage(pg0);
	}

	@Override
	public boolean performFinish() {
		Job job = new Job("ProfileApplicator") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Appling profile(s)", 1000);
				try {
					ProfileApplicator profileApplicator = new ProfileApplicator(
							settings, new SubProgressMonitor(monitor, 1000));
					if (profileApplicator.applyProfile()){
						return new Status(IStatus.OK, "unkown", "Profile(s) sucessfully applied");//TODO Plugin ID
					} else {
						return new Status(IStatus.CANCEL, "unkown", "Applying profiles canceled");//TODO Plugin ID
					}
				} catch (Exception e) {
					String message = (e.getMessage() != null ? e.getMessage()
									: "[null]");
					return new Status(IStatus.ERROR, "unkown", message);//TODO Plugin ID
				} finally {
					monitor.done();
				}
			}
		};
		job.setPriority(Job.BUILD);
		job.setUser(true);
		job.schedule();
		return true;
	}

}
