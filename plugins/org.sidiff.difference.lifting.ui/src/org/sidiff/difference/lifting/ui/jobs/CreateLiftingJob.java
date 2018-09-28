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
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matching.input.InputModels;

public class CreateLiftingJob extends Job {

	private IFile differenceFile;
	private SymmetricDifference symmetricDiff;
	private LiftingSettings settings;
	private InputModels inputModels;

	public CreateLiftingJob(IFile differenceFile) {
		super("Lifting technical Difference");
		this.differenceFile = differenceFile;
		this.symmetricDiff = LiftingFacade.loadLiftedDifference(differenceFile.getLocation().toOSString());
		this.inputModels = new InputModels(symmetricDiff.getModelA(), symmetricDiff.getModelB());
		this.settings = new LiftingSettings(inputModels.getDocumentTypes());
	}

	public InputModels getInputModels() {
		return inputModels;
	}

	public LiftingSettings getSettings() {
		return settings;
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

			String diffSavePath = differenceFile.getParent().getLocation().toOSString();
			LiftingFacade.serializeLiftedDifference(symmetricDiff, diffSavePath,
			PipelineUtils.generateDifferenceFileName(symmetricDiff.eResource(), settings));

			/*
			 * Update workspace UI
			 */
			
			final String diffPath = EMFStorage.uriToPath(symmetricDiff.eResource().getURI());
			
			Display.getDefault().asyncExec(() -> Exceptions.log(() -> {
				differenceFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				UIUtil.openEditor(diffPath);
				return Status.OK_STATUS;
			}));
			return Status.OK_STATUS;
		});
	}
}
