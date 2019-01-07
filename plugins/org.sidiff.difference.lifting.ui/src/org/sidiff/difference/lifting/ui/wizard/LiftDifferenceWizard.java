package org.sidiff.difference.lifting.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.difference.lifting.ui.jobs.LiftDifferenceJob;
import org.sidiff.difference.lifting.ui.pages.CreateLiftingPage;

public class LiftDifferenceWizard extends Wizard{

	private CreateLiftingPage createLiftingPage;

	private IFile differenceFile;
	private LiftDifferenceJob job;

	public LiftDifferenceWizard(IFile differenceFile) {
		this.setWindowTitle("Difference Lift Up Wizard");
		this.differenceFile = differenceFile;
		job = new LiftDifferenceJob(differenceFile);
	}

	@Override
	public void addPages() {
		createLiftingPage = new CreateLiftingPage(differenceFile, job.getInputModels(), job.getSettings());
		addPage(createLiftingPage);
	}

	@Override
	public boolean canFinish() {
		return createLiftingPage.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		job.schedule();
		return true;
	}
}
