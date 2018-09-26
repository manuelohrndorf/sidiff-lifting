package org.sidiff.patching.patch.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.ui.jobs.CreateAsymDiffJob;

public class CreateAsymDiffWizard extends Wizard {

	private CreatePatchPage01 createPatchPage01;
	private CreatePatchPage02 createPatchPage02;

	private InputModels inputModels;
	private PatchingSettings settings;

	public CreateAsymDiffWizard(IFile fileA, IFile fileB) {
		this.setWindowTitle("New Asymmetric Difference Wizard");

		inputModels = new InputModels(fileA, fileB);
		settings = new PatchingSettings(inputModels.getDocumentTypes());
		settings.setRecognitionEngineMode(RecognitionEngineMode.LIFTING_AND_POST_PROCESSING);
		settings.setCalculateEditRuleMatch(true);
	}

	@Override
	public void addPages() {
		createPatchPage01 = new CreatePatchPage01(
				inputModels, "CreateDifferencePage01", "Create Asymmetric Difference",
				null, settings, Mode.ASYMMETRIC_DIFFERENCE);
		addPage(createPatchPage01);

		createPatchPage02 = new CreatePatchPage02(
				inputModels, "CreateDifferencePage02", "Create Asymmetric Difference",
				null, settings, Mode.ASYMMETRIC_DIFFERENCE, createPatchPage01);
		addPage(createPatchPage02);
	}

	@Override
	public boolean canFinish() {
		return createPatchPage01.isPageComplete() && createPatchPage02.isPageComplete();
	}
	

	@Override
	public boolean performFinish() {
		Job job = new CreateAsymDiffJob(inputModels, settings);
		job.schedule();
		return true;
	}

}
