package org.sidiff.patching.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.patching.PatchMode;
import org.sidiff.patching.api.input.PatchingInputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.ui.jobs.ApplyPatchJob;

public class ApplyPatchWizard extends Wizard {

	private ApplyPatchPage01 applyPatchPage01;
	private ApplyPatchPage02 applyPatchPage02;

	private Patch patch;
	private IFile file;
	private PatchingSettings settings;

	public ApplyPatchWizard(Patch patch, IFile file) {
		this.setWindowTitle("Apply Patch Wizard");
		this.patch = patch;
		this.file = file;
		this.settings = new PatchingSettings();
		this.settings.setPatchMode(PatchMode.PATCHING); // required for ApplyPatchJob
	}

	@Override
	public void addPages() {
		PatchingInputModels inputModels = PatchingInputModels.forDifference(patch.getAsymmetricDifference());
		
		applyPatchPage01 = new ApplyPatchPage01(inputModels, "Apply Patch: " + file.getName(), settings);
		addPage(applyPatchPage01);

		applyPatchPage02 = new ApplyPatchPage02(patch, "Apply Patch: " + file.getName(), settings, applyPatchPage01);
		addPage(applyPatchPage02);
	}

	@Override
	public boolean canFinish() {
		return applyPatchPage01.isPageComplete()
				&& applyPatchPage02.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		Job job = new ApplyPatchJob(patch, applyPatchPage01.getTargetModel(), settings);
		job.setUser(true);
		job.schedule();
		return true;
	}
}
