package org.sidiff.profileapplicator.ui;

import org.eclipse.jface.wizard.Wizard;
import org.sidiff.profileapplicator.settings.ProfileApplicatorSettings;

public class ApplyProfilesWizard extends Wizard {

	protected ApplyProfilesWizardPage0 pg0;
	private final ProfileApplicatorSettings settings;

	public ApplyProfilesWizard(String defaultInputFolderPath) {
		super();
		this.settings=new ProfileApplicatorSettings("", "", defaultInputFolderPath);
		setNeedsProgressMonitor(true);
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
		// TODO Auto-generated method stub
		return true;
	}

}
