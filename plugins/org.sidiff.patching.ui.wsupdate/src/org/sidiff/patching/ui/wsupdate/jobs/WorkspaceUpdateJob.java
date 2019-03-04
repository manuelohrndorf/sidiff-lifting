package org.sidiff.patching.ui.wsupdate.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.patch.ui.jobs.CreateAsymmetricDifferenceJob;
import org.sidiff.patching.ui.jobs.ApplyAsymmetricDifferenceJob;
import org.sidiff.patching.ui.wsupdate.util.WSUModels;

public class WorkspaceUpdateJob extends Job {

	private WSUModels mergeModels;
	private PatchingSettings settings;

	public WorkspaceUpdateJob(WSUModels mergeModels, PatchingSettings settings) {
		super("Merging Models");
		this.mergeModels = mergeModels;
		this.settings = settings;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		// First create a patch between BASE<->THEIRS
		InputModels patchInputModels = new InputModels(mergeModels.getResourceBase(), mergeModels.getResourceTheirs());
		CreateAsymmetricDifferenceJob createJob =
			new CreateAsymmetricDifferenceJob(patchInputModels, settings, false);
		createJob.setUser(true);
		createJob.schedule();
		try {
			createJob.join();
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		}
		
		// Now apply that patch onto MINE
		ApplyAsymmetricDifferenceJob applyJob = new ApplyAsymmetricDifferenceJob(
				createJob.getDifference().getAsymmetric(), mergeModels.getResourceMine().getURI(), settings);
		applyJob.setUser(true);
		applyJob.schedule();
		return Status.OK_STATUS;
	}
}
