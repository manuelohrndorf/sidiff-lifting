package org.sidiff.patching.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.patching.PatchMode;
import org.sidiff.patching.api.input.PatchingInputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.ui.jobs.ApplyAsymmetricDifferenceJob;

public class ApplyAsymmetricDifferenceWizard extends Wizard {

	private ApplyAsymmetricDifferencePage01 applyAsymmetricDifferencePage01;
	private ApplyAsymmetricDifferencePage02 applyAsymmetricDifferencePage02;

	private IFile diffFile;
	private PatchingSettings settings;
	private PatchingInputModels inputModels;

	public ApplyAsymmetricDifferenceWizard(IFile diffFile) {
		this.setWindowTitle("Apply Patch Wizard");
		this.diffFile = diffFile;
		this.settings = new PatchingSettings();
		this.settings.setPatchMode(PatchMode.PATCHING); // required for the ApplyAsymmetricDifferenceJob
		this.inputModels = new PatchingInputModels(diffFile);
	}

	@Override
	public void addPages() {
		String pageTitle = "Apply Patch: " + diffFile.getName();
		
		applyAsymmetricDifferencePage01 = new ApplyAsymmetricDifferencePage01(
				inputModels,  pageTitle, settings);
		addPage(applyAsymmetricDifferencePage01);

		applyAsymmetricDifferencePage02 = new ApplyAsymmetricDifferencePage02(
				inputModels, pageTitle, settings, applyAsymmetricDifferencePage01);
		addPage(applyAsymmetricDifferencePage02);
	}

	@Override
	public boolean canFinish() {
		return applyAsymmetricDifferencePage01.isPageComplete()
				&& applyAsymmetricDifferencePage02.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		Job job = new ApplyAsymmetricDifferenceJob(inputModels.getAsymmetricDifference(),
				this.applyAsymmetricDifferencePage01.getTargetModel(), settings);
		job.setUser(true);
		job.schedule();
		return true;
	}
}
