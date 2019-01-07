package org.sidiff.difference.lifting.ui.jobs;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.StatusWrapper;
import org.sidiff.common.ui.util.Exceptions;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.ui.validation.ValidateDialog;
import org.sidiff.matching.input.InputModels;

public class CreateLiftedDifferenceJob extends Job {

	private InputModels inputModels;
	private LiftingSettings settings;
	
	public CreateLiftedDifferenceJob(InputModels inputModels, LiftingSettings settings) {
		super("Creating Symmetric Difference");
		this.inputModels = inputModels;
		this.settings = settings;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		/*
		 * Semantic Lifting
		 */
		return StatusWrapper.wrap(() -> {
			Resource resourceA = inputModels.getResources().get(0);
			Resource resourceB = inputModels.getResources().get(1);
			SymmetricDifference symmetricDiff = calculateDifference(resourceA, resourceB);

			/*
			 * Serialize lifted symmetricDiff
			 */

			String fileName = PipelineUtils.generateDifferenceFileName(resourceA, resourceB, settings);
			URI outputDir = EMFStorage.toFileURI(inputModels.getFiles().get(0).getParent());
			LiftingFacade.serializeLiftedDifference(symmetricDiff, outputDir, fileName);

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

	private SymmetricDifference calculateDifference(Resource resourceA, Resource resourceB) throws NoCorrespondencesException, InvalidModelException {
		SymmetricDifference symmetricDiff = null;
		
		try {
			symmetricDiff = LiftingFacade.liftTechnicalDifference(resourceA, resourceB, settings);
		} catch(InvalidModelException e){
			boolean skipValidation = ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
			if (skipValidation) {
				// Retry without validation:
				settings.setValidate(false);
				symmetricDiff = calculateDifference(resourceA, resourceB);
			} else {
				throw e;
			}
		}
		PipelineUtils.sortDifference(symmetricDiff);
		return symmetricDiff;
	}
}
