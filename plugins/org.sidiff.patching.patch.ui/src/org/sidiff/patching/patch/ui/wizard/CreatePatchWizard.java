package org.sidiff.patching.patch.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.ui.Activator;
import org.sidiff.patching.patch.ui.jobs.CreatePatchJob;

public class CreatePatchWizard extends Wizard {

	private CreatePatchPage01 createPatchPage01;
	private CreatePatchPage02 createPatchPage02;

	private InputModels inputModels;
	private PatchingSettings settings;

	public CreatePatchWizard(IFile fileA, IFile fileB) {
		this.setWindowTitle("New Patch Wizard");

		inputModels = new InputModels(fileA, fileB);
		settings = new PatchingSettings(inputModels.getDocumentTypes());
		settings.setRecognitionEngineMode(RecognitionEngineMode.LIFTING_AND_POST_PROCESSING);
		settings.setCalculateEditRuleMatch(true);
	}

	@Override
	public void addPages() {
		createPatchPage01 = new CreatePatchPage01(
				inputModels, "CreateDifferencePage01", "Create a Patch",
				getImageDescriptor("icon.png"), settings, Mode.PATCH);
		addPage(createPatchPage01);

		createPatchPage02 = new CreatePatchPage02(
				inputModels, "CreateDifferencePage02", "Create a Patch",
				getImageDescriptor("icon.png"), settings, Mode.PATCH, createPatchPage01);
		addPage(createPatchPage02);
	}

	@Override
	public boolean canFinish() {
		return createPatchPage01.isPageComplete() && createPatchPage02.isPageComplete();
	}
	

	@Override
	public boolean performFinish() {
		Job job = new CreatePatchJob(inputModels, settings);
		job.schedule();
		return true;
	}

	protected ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
