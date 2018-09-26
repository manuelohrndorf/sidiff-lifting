package org.sidiff.difference.lifting.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.ui.jobs.CreateLiftingJob;
import org.sidiff.difference.lifting.ui.pages.CreateLiftingPage;

public class CreateLiftingWizard extends Wizard{

	private CreateLiftingPage createLiftingPage;

	private IFile differenceFile;
	private LiftingSettings settings;
	private CreateLiftingJob job;

	public CreateLiftingWizard(IFile differenceFile) {
		this.setWindowTitle("Difference Lift Up Wizard");
		this.differenceFile = differenceFile;
		job = new CreateLiftingJob(differenceFile, settings);
		settings = new LiftingSettings(job.getInputModels().getDocumentTypes());
	}

	@Override
	public void addPages() {
		createLiftingPage = new CreateLiftingPage(differenceFile, job.getInputModels(), settings);
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
