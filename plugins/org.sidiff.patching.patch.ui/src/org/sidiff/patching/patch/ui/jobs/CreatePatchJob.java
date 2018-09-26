package org.sidiff.patching.patch.ui.jobs;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.logging.StatusWrapper;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.api.PatchingFacade;
import org.sidiff.patching.api.settings.PatchingSettings;

public class CreatePatchJob extends Job {

	private InputModels inputModels;
	private PatchingSettings settings;
	
	public CreatePatchJob(InputModels inputModels, PatchingSettings settings) {
		super("Creating Patch");
		this.inputModels = inputModels;
		this.settings = settings;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		/*
		 * Create patch:
		 */
		return StatusWrapper.wrap(() -> {
			// Print report:
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
			LogUtil.log(LogEvent.NOTICE, "---------------------- Create Patch Bundle -----------------");
			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
			
			PatchingFacade.createPatch(inputModels, settings, inputModels.getFiles().get(0).getParent().getLocation().toOSString(), null);

			LogUtil.log(LogEvent.NOTICE, "done...");
			
			/*
			 * Update workspace UI
			 */

			UIUtil.runAsyncSafe(() -> {
				inputModels.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				UIUtil.openEditor(PatchingFacade.getPatchPath());	
			});
			return Status.OK_STATUS;
		});
	}

}
