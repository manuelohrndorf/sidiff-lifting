package org.sidiff.patching.ui.wsupdate.wizard;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.patching.PatchMode;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.ui.wsupdate.jobs.WorkspaceUpdateJob;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;
import org.sidiff.patching.validation.ValidationMode;

public class WorkspaceUpdateWizard extends Wizard {

	private WorkspaceUpdatePage01 threeWayMergePage01;
	private WorkspaceUpdatePage02 threeWayMergePage02;

	private WSUModels mergeModels;
	private PatchingSettings settings;

	public WorkspaceUpdateWizard(WSUModels mergeModels) {
		this.setWindowTitle("Workspace Update Wizard");
		this.mergeModels = mergeModels;
		this.settings = new PatchingSettings();
		settings.setPatchMode(PatchMode.MERGING); // required for the ApplyAsymmetricDifferenceJob
		// Init the lifting settings from the patching settings:
		settings.setValidate(settings.getValidationMode() != ValidationMode.NO_VALIDATION);
		settings.initDefaults(mergeModels.getDocumentTypes());
	}

	@Override
	public void addPages() {
		threeWayMergePage01 = new WorkspaceUpdatePage01(mergeModels, settings);
		addPage(threeWayMergePage01);

		threeWayMergePage02 = new WorkspaceUpdatePage02(mergeModels, settings, threeWayMergePage01);
		addPage(threeWayMergePage02);
	}

	@Override
	public boolean canFinish() {
		return threeWayMergePage01.isPageComplete()
				&& threeWayMergePage02.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		Job job = new WorkspaceUpdateJob(mergeModels, settings);
		job.setUser(true);
		job.schedule();
		return true;
	}
}
