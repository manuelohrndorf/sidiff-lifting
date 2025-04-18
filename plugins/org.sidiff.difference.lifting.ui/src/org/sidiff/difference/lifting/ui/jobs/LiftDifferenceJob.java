package org.sidiff.difference.lifting.ui.jobs;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.input.InputModels;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.StatusWrapper;
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class LiftDifferenceJob extends Job {

	private SymmetricDifference symmetricDiff;
	private LiftingSettings settings;
	private InputModels inputModels;

	public LiftDifferenceJob(IFile differenceFile) {
		super("Lifting technical Difference");
		this.symmetricDiff = LiftingFacade.loadLiftedDifference(EMFStorage.toPlatformURI(differenceFile));
		this.inputModels = InputModels.builder().addModel(symmetricDiff.getModelA()).addModel(symmetricDiff.getModelB()).build();
		this.settings = LiftingSettings.defaultSettings(inputModels.getDocumentTypes());
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
			LiftingFacade.serializeLiftedDifference(symmetricDiff, symmetricDiff.eResource().getURI().trimSegments(1),
					PipelineUtils.generateDifferenceFileName(symmetricDiff.eResource(), settings));

			/*
			 * Update workspace UI
			 */
			Display.getDefault().asyncExec(() -> Exceptions.log(() -> {
				inputModels.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				UIUtil.openEditor(EMFStorage.toFile(symmetricDiff.eResource().getURI()));
				return Status.OK_STATUS;
			}));
			return Status.OK_STATUS;
		});
	}
}
