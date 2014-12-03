package org.sidiff.profileapplicator.ui;

import org.eclipse.jface.wizard.Wizard;

public class ApplyProfilesWizard extends Wizard {

	protected ApplyProfilesWizardPage0 pg0;
	private final String defaultInputFolder;

	public ApplyProfilesWizard(String defaultInputFolder) {
		super();
		this.defaultInputFolder=defaultInputFolder;
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getWindowTitle() {
		return "Apply profiles";
	}

	@Override
	public void addPages() {
		pg0 = new ApplyProfilesWizardPage0(defaultInputFolder);
		addPage(pg0);
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return true;
	}

}
