package org.sidiff.difference.lifting.ui.jobs;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.StatusWrapper;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.input.InputModels;

public class CreateLiftingJob extends Job {

	private SymmetricDifference symmetricDiff;
	private String diffSavePath;
	private InputModels inputModels;
	private LiftingSettings settings;

	public CreateLiftingJob(IFile differenceFile, LiftingSettings settings) {
		super("Lifting technical Difference");
		this.settings = settings;
		this.symmetricDiff = LiftingFacade.loadLiftedDifference(differenceFile.getLocation().toOSString());
		this.diffSavePath = differenceFile.getParent().getLocation().toOSString();
		this.inputModels = new InputModels(symmetricDiff.getModelA(), symmetricDiff.getModelB());
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		/*
		 * Semantic Lifting
		 */
		return StatusWrapper.wrap(() -> {
			symmetricDiff = LiftingFacade.liftTechnicalDifference(symmetricDiff, settings);
			PipelineUtils.sortDifference(symmetricDiff);

			/*
			 * Serialize lifted symmetricDiff
			 */

			LiftingFacade.serializeLiftedDifference(symmetricDiff, diffSavePath,
			PipelineUtils.generateDifferenceFileName(symmetricDiff.eResource(), settings));

			/*
			 * Update workspace UI
			 */
			
			final String diffPath = EMFStorage.uriToPath(symmetricDiff.eResource().getURI());
			
			Display.getDefault().asyncExec(() -> {
				try {
					inputModels.getFiles().get(0).getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					UIUtil.openEditor(diffPath);	
				} catch(Exception e) {
					MessageDialogUtil.showExceptionDialog(e);
				}
			});
			return Status.OK_STATUS;
		});
	}

	public InputModels getInputModels() {
		return inputModels;
	}
}
